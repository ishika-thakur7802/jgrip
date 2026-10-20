import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Jgrip{
    public static void main(String[] args) throws Exception
    {
        final String BASE_URL="https://jsonplaceholder.typicode.com";
        URI uri = URI.create(BASE_URL);
        HttpRequest.Builder builder =  HttpRequest.newBuilder();
        builder.uri(uri);
        builder.GET();
        builder.timeout(Duration.ofMillis(5000));
        HttpRequest request = builder.build();

        HttpClient client = HttpClient.newHttpClient();
        int[] status= new int[100];
        long[] latency= new long[100];
        for(int i=0; i<100; i++)
        {
            long startTime = System.nanoTime();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            long endTime = System.nanoTime();

            long elapsedTime= endTime-startTime;
            latency[i]= TimeUnit.NANOSECONDS.toMillis(elapsedTime);
            status[i]= response.statusCode();
        }
        long sumLatency=0;
        long max= 0;
        long min= latency[0];
        for(int j=0; j<100; j++)
        {
            if(latency[j]>max)
                max=latency[j];
            if (latency[j]<min)
                min=latency[j];
          sumLatency=sumLatency+latency[j];
        }
        double countSuccess=0;
        for (int k=0; k<100; k++)
        {
            if(String.valueOf(status[k]).charAt(0)=='2' ||String.valueOf(status[k]).charAt(0)=='3')
            {
                countSuccess++;
            }
        }

        System.out.println("Success Rate="+countSuccess/100);
        System.out.println("Error Rate="+(100-countSuccess)/100);
        System.out.println("Average Latency="+sumLatency/100+"ms");
        System.out.println("Minimum Latency="+min);
        System.out.println("Maximum Latency="+min);

    }


}