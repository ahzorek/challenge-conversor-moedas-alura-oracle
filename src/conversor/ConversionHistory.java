package conversor;

import java.util.Date;

public class ConversionHistory {
    private final String originCurrency;
    private final String targetCurrency;
    private final float amount;
    private final float convertedValue;
    private final float rate;
    private final Date date;

    public ConversionHistory(String originCurrency, String targetCurrency, float amount, float convertedValue, float rate, Date date) {
        this.originCurrency = originCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.convertedValue = convertedValue;
        this.rate = rate;
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format(
                "Data: %s | De: %s %.2f | Para: %s %.2f | Taxa: %.4f",
                date, originCurrency, amount, targetCurrency, convertedValue, rate
        );
    }
}
