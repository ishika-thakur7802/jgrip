import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class Jgrip{
    private static final String BASE_URL="https://www.youtube.com/";
    public static void main(String[] args) throws Exception
    {
        URI uri = URI.create(BASE_URL);
        HttpRequest.Builder builder =  HttpRequest.newBuilder();
        builder.uri(uri);
        builder.GET();
        HttpRequest request = builder.build();

        HttpClient client = HttpClient.newHttpClient();

        long startTime = System.nanoTime();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        long endTime = System.nanoTime();

        long elapsedTime= endTime-startTime;
        System.out.println(response.statusCode());
        System.out.println("Time taken to fetch the response:"+ TimeUnit.NANOSECONDS.toMillis(elapsedTime) + "ms");
    }


}