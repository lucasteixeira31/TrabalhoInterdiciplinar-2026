import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/** Cliente de análise de sentimentos usando Azure AI Language. Requer Java 11+. */
public class AnaliseSentimentosAzure {
    private static String jsonEscape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    public static void main(String[] args) throws Exception {
        String endpoint = System.getenv("AZURE_LANGUAGE_ENDPOINT");
        String key = System.getenv("AZURE_LANGUAGE_KEY");
        if (endpoint == null || endpoint.isBlank() || key == null || key.isBlank()) {
            System.err.println("Defina AZURE_LANGUAGE_ENDPOINT e AZURE_LANGUAGE_KEY no ambiente.");
            System.exit(1);
        }
        String text = args.length > 0 ? String.join(" ", args) : "O atendimento foi excelente!";
        String json = "{\"kind\":\"SentimentAnalysis\",\"parameters\":{\"modelVersion\":\"latest\"},"
                + "\"analysisInput\":{\"documents\":[{\"id\":\"1\",\"language\":\"pt\",\"text\":\""
                + jsonEscape(text) + "\"}]}}";
        String url = endpoint.replaceAll("/+$", "") + "/language/:analyze-text?api-version=2024-11-01";
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Ocp-Apim-Subscription-Key", key)
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        System.out.println("HTTP " + response.statusCode());
        System.out.println(response.body());
        if (response.statusCode() != 200) System.exit(2);
    }
}
