import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Jgrip{
    public static void main(String[] args)  {
        final String BASE_URL = "https://jsonplaceholder.typicode.com";
        URI uri = URI.create(BASE_URL);
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(uri);
        builder.GET();
        builder.timeout(Duration.ofMillis(5000));
        HttpRequest request = builder.build();
        HttpClient client = HttpClient.newHttpClient();
        int totalRequests=100;
        int exceptionCount=0;
            int[] status = new int[totalRequests];
            long[] latency = new long[totalRequests];
            for (int i = 0; i < totalRequests; i++) {
                long startTime= System.nanoTime();
                long endTime=startTime;
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    endTime = System.nanoTime();
                    status[i] = response.statusCode();
                }
                catch(IOException | InterruptedException e)
                {
                    endTime=System.nanoTime();
                    exceptionCount++;
                    status[i]= -1;
                }

                finally {
                    long elapsedTime = endTime - startTime;
                    latency[i] = elapsedTime;
                }
            }
            long sumLatency = 0;
            long max = 0;
            long min = latency[0];
            for (int j = 0; j < totalRequests; j++) {
                if (latency[j] > max)
                    max = latency[j];
                if (latency[j] < min)
                    min = latency[j];
                sumLatency = sumLatency + latency[j];
            }
            double countSuccess = 0;
            for (int k = 0; k < totalRequests; k++) {
                if (status[k] >= 200 && status[k] < 300) {
                    countSuccess++;
                }
            }



            System.out.println("Success Rate=" + countSuccess / totalRequests * 100 + "%");
            System.out.println("Error Rate=" + ((totalRequests - countSuccess) / totalRequests * 100 + "%"));
            System.out.println("Exceptions="+exceptionCount);
            System.out.println("Average Latency=" + TimeUnit.NANOSECONDS.toMillis(sumLatency / totalRequests) + "ms");
            System.out.println("Minimum Latency=" + TimeUnit.NANOSECONDS.toMillis(min) + "ms");
            System.out.println("Maximum Latency=" + TimeUnit.NANOSECONDS.toMillis(max) + "ms");


    }


}