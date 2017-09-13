package ai.exemplar.api.square;

import ai.exemplar.api.square.model.Merchant;
import ai.exemplar.api.square.model.Payment;

import java.time.LocalDateTime;
import java.util.List;

public interface SquareApiProvider {

    List<Merchant> listLocations(String bearer);

    List<Payment> listPayments(String bearer, String locationId, LocalDateTime beginTime, LocalDateTime endTime);
}
