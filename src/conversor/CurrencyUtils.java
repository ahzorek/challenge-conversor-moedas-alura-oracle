package conversor;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyUtils {
    private static final Locale locale = new Locale("pt", "BR");

    public static String getSymbol(String currCode){
        Currency currency = Currency.getInstance(currCode);

        return currency.getSymbol(locale);
    }

    public static String getName(String currCode){
        Currency currency = Currency.getInstance(currCode);

        return currency.getDisplayName(locale);
    }

    public static String formatCurrency(String currCode, float amount) {
        Currency currency = Currency.getInstance(currCode);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        currencyFormat.setCurrency(currency);
        return currencyFormat.format(amount);
    }
}

