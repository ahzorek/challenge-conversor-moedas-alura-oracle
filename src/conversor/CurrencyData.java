package conversor;
import java.util.Map;


public class CurrencyData {
    private final long lastUpdate;
    private final Map<String, Float> conversionRates;

    public CurrencyData(CurrencyDTO dto){
        this.conversionRates = dto.conversionRates();
        this.lastUpdate = dto.timeLastUpdateUnix();
    }

    public float convertTo(String targetCode, float amount) {
        Float rate = conversionRates.get(targetCode);
        if (rate == null) {
            throw new IllegalArgumentException("Conversion rate not available for " + targetCode);
        }
        return amount * rate;
    }

    public long getLastUpdate(){
        return this.lastUpdate;
    }

    public float getRateFor(String targetCode){
        return conversionRates.get(targetCode);
    }

}
