package ai.exemplar.data.fetchers.square;

import ai.exemplar.api.square.SquareApiProvider;
import ai.exemplar.api.square.model.GlobalAddress;
import ai.exemplar.api.square.model.Money;
import ai.exemplar.api.square.model.PaymentItemDetail;
import ai.exemplar.common.LocationsService;
import ai.exemplar.data.fetchers.DataFetcher;
import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.persistence.SquareLocationRepository;
import ai.exemplar.persistence.SquarePaymentsRepository;
import ai.exemplar.persistence.dynamodb.schema.square.*;
import ai.exemplar.persistence.model.OAuthToken;
import ai.exemplar.persistence.model.SquarePayment;
import ai.exemplar.streams.StreamsAppender;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SquareDataFetcher implements DataFetcher {

    static final Logger log = Logger.getLogger(SquareDataFetcher.class);

    public static final String PROVIDER_NAME = "square";

    private final OAuthTokenRepository tokensRepository;

    private final SquareApiProvider api;

    private final LocationsService locationsService;

    private final SquarePaymentsRepository historyRepository;

    private final StreamsAppender streamsAppender;

    @Inject
    public SquareDataFetcher(OAuthTokenRepository tokensRepository, SquareApiProvider api, LocationsService locationsService, SquarePaymentsRepository historyRepository, StreamsAppender streamsAppender) {
        this.tokensRepository = tokensRepository;
        this.api = api;
        this.locationsService = locationsService;
        this.historyRepository = historyRepository;
        this.streamsAppender = streamsAppender;
    }

    @Override
    public void fetchData(OAuthToken token) {
        try {
            locationsService.locations(token).forEach(location ->
                    fetchPayments(location, token)
            );

        } catch (Throwable e) {
            log.error(String.format("failed to fetch square data for key %s", token.getId()), e);
        }
    }

    private void fetchPayments(LocationSchema location, OAuthToken token) {
        LocalDateTime beginTime = Optional.ofNullable(location.getLastFetched())
                .orElse(LocalDateTime.now()
                        .minus(1L, ChronoUnit.DAYS));

        List<SquarePayment> payments = api.listPayments(
                token.getToken(),
                location.getId(),
                beginTime,
                LocalDateTime.now()
        ).stream()
                .map(payment -> new SquarePayment(
                        location.getId(),
                        payment.getId(),
                        payment.getCreated(),
                        payment.deviceName(),
                        payment.getUrl(),
                        Optional.ofNullable(payment
                                .getTax()).map(Money::value).orElse(null),
                        Optional.ofNullable(payment
                                .getTip()).map(Money::value).orElse(null),
                        Optional.ofNullable(payment
                                .getDiscount()).map(Money::value).orElse(null),
                        Optional.ofNullable(payment
                                .getTotalCollected()).map(Money::value).orElse(null),
                        Optional.ofNullable(payment
                                .getProcessingFee()).map(Money::value).orElse(null),
                        Optional.ofNullable(payment
                                .getRefunded()).map(Money::value).orElse(null),
                        Optional.ofNullable(payment.getTender()).map(tenders -> tenders.stream()
                                .map(tender -> new TenderDocumentSchema(
                                        tender.getType(),
                                        tender.getTotal().value(),
                                        tender.getCardBrand(),
                                        tender.getPanSuffix(),
                                        tender.getEntryMethod()
                                )).collect(Collectors.toList())
                        ).orElse(null),
                        Optional.ofNullable(payment.getItemizations()).map(itemizations -> itemizations.stream()
                                .map(itemization -> new ItemDocumentSchema(
                                        itemization.getName(),
                                        itemization.getQuantity(),
                                        itemization.getNotes(),
                                        itemization.getItemVariation(),
                                        Optional.ofNullable(itemization.getDetails())
                                                .map(PaymentItemDetail::getCategory).orElse(null),
                                        Optional.ofNullable(itemization.getDetails())
                                                .map(PaymentItemDetail::getSku).orElse(null),
                                        Optional.ofNullable(itemization.getDetails())
                                                .map(PaymentItemDetail::getId).orElse(null),
                                        Optional.ofNullable(itemization.getDetails())
                                                .map(PaymentItemDetail::getVariationId).orElse(null),
                                        Optional.ofNullable(itemization
                                                .getTotal()).map(Money::value).orElse(null),
                                        Optional.ofNullable(itemization
                                                .getSingleQuantity()).map(Money::value).orElse(null),
                                        Optional.ofNullable(itemization
                                                .getGrossSales()).map(Money::value).orElse(null),
                                        Optional.ofNullable(itemization
                                                .getNetSales()).map(Money::value).orElse(null),
                                        Stream.concat(
                                                Optional.ofNullable(itemization.getDiscounts())
                                                        .map(paymentDiscounts -> paymentDiscounts.stream()
                                                                .map(discount -> new ModifierDocumentSchema(
                                                                        "discount",
                                                                        discount.getDiscount(),
                                                                        discount.getName(),
                                                                        Optional.ofNullable(discount
                                                                                .getApplied()).map(Money::value)
                                                                                .orElse(null)
                                                                ))
                                                        ).orElse(Stream.empty()),
                                                Optional.ofNullable(itemization.getModifiers())
                                                        .map(paymentModifiers -> paymentModifiers.stream()
                                                                .map(modifier -> new ModifierDocumentSchema(
                                                                        "modifier",
                                                                        modifier.getId(),
                                                                        modifier.getName(),
                                                                        Optional.ofNullable(modifier
                                                                                .getApplied()).map(Money::value)
                                                                                .orElse(null)
                                                                ))
                                                        ).orElse(Stream.empty())
                                        ).collect(Collectors.toList())
                                )).collect(Collectors.toList())
                        ).orElse(null)
                )).collect(Collectors.toList());

        log.info(String.format("fetched %d square payments for location %s", payments.size(), location.getId()));

        if (!payments.isEmpty()) {
            historyRepository.batchSave(payments);

            payments.stream()
                    .max(Comparator
                            .comparing(SquarePayment::getTimestamp))
                    .ifPresent(lastItem -> {
                        location.setLastFetched(lastItem
                                .getTimestamp());

                        locationsService
                                .save(location);
                    });

            payments.stream()
                    .filter(payment -> payment.getTimestamp()
                            .isAfter(beginTime))
                    .forEach(streamsAppender::appendPayment);
        }
    }
}
