import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;
import java.lang.Math;

public class Jgrip{
    public static void main(String[] args)  {
        if(args.length==2) {
            final String BASE_URL = args[0];
            URI uri = URI.create(BASE_URL);
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            builder.uri(uri);
            builder.GET();
            builder.timeout(Duration.ofMillis(5000));
            HttpRequest request = builder.build();
            HttpClient client = HttpClient.newHttpClient();
            int totalRequests;
            try{
                totalRequests=Integer.parseInt(args[1]);
                if(totalRequests<0)
                {
                    System.out.println("Invalid Number of Requests");
                    return;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Kindly provide a positive integer value or 0 for the number of requests");
                return;
            }
            int attemptedRequests = 0;
            int exceptionCount = 0;
            int[] status = new int[totalRequests];
            long[] latency = new long[totalRequests];
            for (int i = 0; i < totalRequests; i++) {
                long startTime = System.nanoTime();
                long endTime = startTime;
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    endTime = System.nanoTime();
                    status[i] = response.statusCode();
                    attemptedRequests++;
                } catch (IOException e) {
                    endTime = System.nanoTime();
                    exceptionCount++;
                    status[i] = -1;
                    attemptedRequests++;
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                    exceptionCount++;
                    attemptedRequests++;
                    status[i] = -1;
                    endTime = System.nanoTime();
                    System.out.println("Countered an InterruptedException");
                    break;
                } finally {
                    long elapsedTime = endTime - startTime;
                    latency[i] = elapsedTime;
                }
            }
            HashMap<Integer, Integer> frequency = new HashMap<>();
            double sumLatency = 0;
            long max = 0;
            long min = Long.MAX_VALUE;
            for (int j = 0; j < attemptedRequests; j++) {
                if (latency[j] > max)
                    max = latency[j];
                if (latency[j] < min)
                    min = latency[j];
                sumLatency = sumLatency + latency[j];
            }
            double countSuccess = 0;
            double countFailed = 0;
            for (int k = 0; k < attemptedRequests; k++) {
                if (status[k] >= 200 && status[k] < 300) {
                    countSuccess++;
                } else {
                    countFailed++;
                }
            }

            System.out.println("Target URL:"+args[0]);
            System.out.println("Total Requests Planned:" + totalRequests);
            System.out.println("Requests Attempted:" + attemptedRequests);
            System.out.println("\n RESULTS");
            if (attemptedRequests > 0) {
                System.out.println("Successful Requests:" + countSuccess);
                System.out.println("Failed Requests:" + countFailed);
                System.out.println("Exceptions=" + exceptionCount);
                System.out.println("Success Rate=" + countSuccess / attemptedRequests * 100 + "%");
                System.out.println("Error Rate=" + countFailed / attemptedRequests * 100 + "%");
                System.out.println("HTTP STATUS CODES:");
                for (int i: status)
                {
                    frequency.put(i, frequency.getOrDefault(i,0)+1);
                }
                for(Map.Entry<Integer,Integer> entry: frequency.entrySet())
                {
                    System.out.println(entry.getKey() +"="+ entry.getValue());
                }
                System.out.println("LATENCY");
                System.out.println("Average=" + TimeUnit.NANOSECONDS.toMillis((long) (sumLatency / attemptedRequests)) + "ms");
                System.out.println("Minimum=" + TimeUnit.NANOSECONDS.toMillis(min) + "ms");
                System.out.println("Maximum=" + TimeUnit.NANOSECONDS.toMillis(max) + "ms");
                Arrays.sort(latency);
                System.out.println("p50=" + TimeUnit.NANOSECONDS.toMillis(latency[Math.toIntExact(Math.round(attemptedRequests * 0.50)-1)]) + "ms");
                System.out.println("p95=" + TimeUnit.NANOSECONDS.toMillis(latency[Math.toIntExact(Math.round(attemptedRequests * 0.95))-1]) + "ms");
                System.out.println("Throughput="+(attemptedRequests/sumLatency));
            } else {
                System.out.println("no requests were attempted");
            }
        }
        else
            System.out.println("Insufficient Arguments");



    }


}