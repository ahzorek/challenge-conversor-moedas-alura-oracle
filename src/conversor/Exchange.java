package conversor;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;

public class Exchange {
    private static final Scanner inputReader = new Scanner(System.in);
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final int MAX_HISTORY_SIZE = 10;
    private static final ArrayDeque<ConversionHistory> history = new ArrayDeque<>(MAX_HISTORY_SIZE);

    // moedas disponíveis
    private static final ArrayList<String> availableCurrencies = new ArrayList<>(List.of(
            "USD", "EUR", "BRL", "GBP", "CNY", "JPY", "CAD", "CHF", "ARS", "MXN", "ZAR", "RUB", "KRW", "NZD", "BOB", "CLP", "COP"
    ));

    private static void displayCurrencyMenu(){
        for (int i = 0; i < availableCurrencies.size(); i++) {
            String currSymbol = CurrencyUtils.getSymbol(availableCurrencies.get(i));
            String currName = CurrencyUtils.getName(availableCurrencies.get(i));
            String formattedCurrencyName = currSymbol + " (" + currName + ")";
            System.out.printf("[%d] - %s%n", i, formattedCurrencyName);
        }
    }

    public static CurrencyData getCurrencyData(String sourceCurrencyCode) {
        Request request = new Request();
        HttpResponse<String> response = request.make(sourceCurrencyCode);

        if (response != null && response.statusCode() == 200) {
            // Converte JSON para objeto usando o DTO
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            CurrencyDTO currencyDTO = gson.fromJson(response.body(), CurrencyDTO.class);

            // Cria o DetailedCurrency (ou CurrencyData)
            return new CurrencyData(currencyDTO);
        } else {
            System.out.println("Erro ao obter dados de conversão.");
            return null; // Retorna null em caso de erro
        }
    }


    public static void convert(){
        boolean converting = true;

        while(converting){
            // escolhe a moeda de origem
            System.out.println("Selecione a moeda de origem: ");
            displayCurrencyMenu();

            int sourceIndex = inputReader.nextInt();
            String sourceCurrencyCode = availableCurrencies.get(sourceIndex);
            //System.out.println("Moeda selecionada:: " + sourceCurrencyCode);

            CurrencyData sourceCurrency = getCurrencyData(sourceCurrencyCode);
            if (sourceCurrency == null) return; // Se houver erro, retorna e encerra a conversão

            // escolhe moeda de saída
            System.out.println("Selecione a moeda de destino: ");
            displayCurrencyMenu();
            int targetIndex = inputReader.nextInt();
            String targetCurrencyCode = availableCurrencies.get(targetIndex);

            // define valor da conversão
            System.out.print("Digite o valor a ser convertido: ");
            float amount = inputReader.nextFloat();

            // efetua conversão
            float convertedValue = sourceCurrency.convertTo(targetCurrencyCode, amount);

            ConversionHistory record = new ConversionHistory(
                    sourceCurrencyCode,
                    targetCurrencyCode,
                    amount,
                    convertedValue,
                    sourceCurrency.getRateFor(targetCurrencyCode),
                    new Date()
            );
            addToHistory(record);


            // exibe saida
            displayConversion(
                    amount,
                    sourceCurrencyCode,
                    targetCurrencyCode,
                    sourceCurrency.getRateFor(targetCurrencyCode),
                    convertedValue,
                    sourceCurrency.getLastUpdate()
            );

            // deseja encerrar ou continuar
            System.out.println("Deseja realizar mais uma conversão ou voltar ao menu inicial?");
            System.out.println("[0] - Voltar ao início ");
            System.out.println("[1] - Continuar convertendo ");
            int continues = inputReader.nextInt();
            if(continues == 0){
                converting = false;
                System.out.println("Voltando ao menu inicial...");
            } else {
                System.out.println("Iniciando nova conversão");
            }


        }
    }

    public static void rates() {
        System.out.println("Selecione a moeda para visualizar as taxas de conversão: ");
        displayCurrencyMenu();

        int sourceIndex = inputReader.nextInt();
        String sourceCurrencyCode = availableCurrencies.get(sourceIndex);

        CurrencyData sourceCurrency = getCurrencyData(sourceCurrencyCode);
        if (sourceCurrency == null) return;

        // Exibe a data da última atualização
        Date lastUpdateDate = new Date(sourceCurrency.getLastUpdate() * 1000L);
        System.out.printf(
                "📅 Taxas atualizadas em: %s%n" +
                        "==========================%n",
                dateFormatter.format(lastUpdateDate)
        );

        // itera a lista de moedas disponíveis (- a de origem) e retorna as taxas de conversão
        for (String targetCurrencyCode : availableCurrencies) {
            if (!targetCurrencyCode.equals(sourceCurrencyCode)) {
                float rate = sourceCurrency.getRateFor(targetCurrencyCode);
                String formattedRate = String.format(Locale.US, "%.4f", rate);
                String symbol = CurrencyUtils.getSymbol(targetCurrencyCode);
                String name = CurrencyUtils.getName(targetCurrencyCode);

                System.out.printf("%s (%s): %s%n", symbol, name, formattedRate);
            }
        }
        System.out.println("==========================");

    }

    public static void displayConversion(
            float amount,
            String originCurrency,
            String targetCurrency,
            float rate,
            float convertedValue,
            long lastUpdateUnix
    ) {

        // formata as moedas (entrada e saida)
        String originFormatted = CurrencyUtils.formatCurrency(originCurrency, amount);
        String targetFormatted = CurrencyUtils.formatCurrency(targetCurrency, convertedValue);

        // formata a data unix recebida da api (lastUpdate)
        Date lastUpdateDate = new Date(lastUpdateUnix * 1000L); // Unix time está em segundos

        // saida
        System.out.printf(
                "🎉 Conversão Realizada! 🎉%n" +
                        "==========================%n" +
                        "📅 Atualizado em: %s%n" +
                        "💱 Valor Original: %s%n" +
                        "➡️  Taxa de Conversão: %.4f%n" +
                        "💸 Valor Convertido: %s%n" +
                        "==========================%n",
                dateFormatter.format(lastUpdateDate),
                originFormatted,
                rate,
                targetFormatted
        );
    }

    public static void addToHistory(ConversionHistory record) {
        if (history.size() == MAX_HISTORY_SIZE) {
            history.pollFirst(); // Remove o mais antigo se o limite foi alcançado
        }
        history.addLast(record);
    }

    public static void displayHistory() {
        System.out.println("Histórico de Conversões:");
        if (history.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda.");
        } else {
            history.forEach(System.out::println);
        }
    }
}
