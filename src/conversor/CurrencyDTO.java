package conversor;
import java.util.Map;

public record CurrencyDTO(String baseCode, Map<String, Float> conversionRates, long timeLastUpdateUnix ) {
}
