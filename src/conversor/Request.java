package conversor;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Request {
    private final HttpClient client = HttpClient.newHttpClient();
    private String apiKey;

    public HttpResponse<String> make(String currency) {
        try {
            Dotenv dotenv = Dotenv.load();
            apiKey = dotenv.get("API_KEY");
            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalArgumentException("API_KEY não encontrada no arquivo .env.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar o arquivo .env ou a API_KEY: " + e.getMessage());
            apiKey = null;
        }

        String url_str = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/";
        URI apiEndpoint = URI.create(url_str + currency);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(apiEndpoint)
                .build();

        try {
            HttpResponse<String> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join();
            if (response.statusCode() == 200) {
                //System.out.println(response.body());
                return response;
            } else {
                System.err.println("Erro na request. Código: " + response.statusCode());
            }
            return response;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
