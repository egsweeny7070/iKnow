package ai.exemplar.persistence;

import ai.exemplar.persistence.model.SquarePayment;

import java.util.List;

public interface SquarePaymentsRepository {

    void batchSave(List<SquarePayment> batch);

    List<SquarePayment> scan();
}
