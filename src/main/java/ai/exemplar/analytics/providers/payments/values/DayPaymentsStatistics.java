package ai.exemplar.analytics.providers.payments.values;

import java.time.LocalDate;

public class DayPaymentsStatistics {

    private String date;

    private Double sales;

    private Integer totalItemsPurchased;

    private Double averageSale;

    private Double averageItemsPurchased;

    private Double averageItemPrice;

    public DayPaymentsStatistics(String date, Double sales, Integer totalItemsPurchased, Double averageSale, Double averageItemsPurchased, Double averageItemPrice) {
        this.date = date;
        this.sales = sales;
        this.totalItemsPurchased = totalItemsPurchased;
        this.averageSale = averageSale;
        this.averageItemsPurchased = averageItemsPurchased;
        this.averageItemPrice = averageItemPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getSales() {
        return sales;
    }

    public void setSales(Double sales) {
        this.sales = sales;
    }

    public Integer getTotalItemsPurchased() {
        return totalItemsPurchased;
    }

    public void setTotalItemsPurchased(Integer totalItemsPurchased) {
        this.totalItemsPurchased = totalItemsPurchased;
    }

    public Double getAverageSale() {
        return averageSale;
    }

    public void setAverageSale(Double averageSale) {
        this.averageSale = averageSale;
    }

    public Double getAverageItemsPurchased() {
        return averageItemsPurchased;
    }

    public void setAverageItemsPurchased(Double averageItemsPurchased) {
        this.averageItemsPurchased = averageItemsPurchased;
    }

    public Double getAverageItemPrice() {
        return averageItemPrice;
    }

    public void setAverageItemPrice(Double averageItemPrice) {
        this.averageItemPrice = averageItemPrice;
    }
}
