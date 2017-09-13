package ai.exemplar.data.fetchers.square;

import ai.exemplar.api.square.SquareApiProvider;
import ai.exemplar.api.square.model.GlobalAddress;
import ai.exemplar.api.square.model.Money;
import ai.exemplar.api.square.model.PaymentItemDetail;
import ai.exemplar.data.fetchers.DataFetcher;
import ai.exemplar.persistence.OAuthTokenRepository;
import ai.exemplar.persistence.SquareLocationRepository;
import ai.exemplar.persistence.SquarePaymentsRepository;
import ai.exemplar.persistence.dynamodb.schema.square.*;
import ai.exemplar.persistence.model.OAuthToken;
import ai.exemplar.persistence.model.SquarePayment;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SquareDataFetcher implements DataFetcher {

    static final Logger log = Logger.getLogger(SquareDataFetcher.class);

    public static final String PROVIDER_NAME = "square";

    private final OAuthTokenRepository tokensRepository;

    private final SquareApiProvider api;

    private final SquareLocationRepository locationRepository;

    private final SquarePaymentsRepository historyRepository;

    @Inject
    public SquareDataFetcher(OAuthTokenRepository tokensRepository, SquareApiProvider api, SquareLocationRepository locationRepository, SquarePaymentsRepository historyRepository) {
        this.tokensRepository = tokensRepository;
        this.api = api;
        this.locationRepository = locationRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public void fetchData(OAuthToken token) {
        try {
            locations(token).forEach(location ->
                    fetchPayments(location, token)
            );

        } catch (Throwable e) {
            log.error(String.format("failed to fetch square data for key %s", token.getId()), e);
        }
    }

    private List<LocationSchema> locations(OAuthToken token) {
        if (Optional.ofNullable(token.getLastFetched())
                .map(lastFetched -> lastFetched
                        .isBefore(LocalDateTime.now()
                                .minus(1L, ChronoUnit.DAYS)))
                .orElse(true)) {

            return fetchLocations(token);

        } else {

            return locationRepository
                    .list(token.getId());

        }
    }

    private List<LocationSchema> fetchLocations(OAuthToken token) {
        List<LocationSchema> locations = api.listLocations(token.getToken()).stream()
                .map(merchant -> new LocationSchema(
                        token.getId(),
                        merchant.getId(),
                        merchant.getName(),
                        merchant.getBusinessName(),
                        merchant.getBusinessType(),
                        merchant.nickname(),
                        merchant.getEmail(),
                        merchant.phone(),
                        merchant.getCountry(),
                        Optional.ofNullable(merchant.getAddress())
                                .map(GlobalAddress::getLocality).orElse(null),
                        Optional.ofNullable(merchant.getAddress())
                                .map(GlobalAddress::address).orElse(null),
                        null
                )).collect(Collectors.toList());

        log.info(String.format("fetched %d square locations for key %s", locations.size(), token.getId()));

        locationRepository.batchSave(locations);

        tokensRepository
                .save(new OAuthToken(
                        token.getId(),
                        token.getProvider(),
                        token.getToken(),
                        token.getRefreshToken(),
                        token.getCreated(),
                        token.getUpdated(),
                        token.getExpiration(),
                        LocalDateTime.now(),
                        token.getInternalId()
                ));

        return locations;
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

                        locationRepository
                                .save(location);
                    });

        }
    }
}
