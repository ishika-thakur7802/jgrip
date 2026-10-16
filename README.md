# jgrip
The MVP: A command line tool written in Java that sends HTTP requests to a target URL, runs them repeatedly and reports the results.
The CLI's expected behavior:

Core command

jgrip http http://localhost:8080/api/orders \
    --requests 10000 \
    --concurrency 50
    
And it produces something like:
J-GRIP

TARGET
  http://localhost:8080/api/orders

LOAD
  Requests       10,000
  Concurrency         50
  Duration         8.42s

RESPONSES
  2xx              9,812
  3xx                 42
  4xx                101
  5xx                 45

LATENCY
  min                4ms
  p50               18ms
  p95               47ms
  p99              182ms
  max              931ms

THROUGHPUT
  1,187 req/s

ERROR RATE
  1.46%

DIAGNOSTICS
   p99 latency is significantly above p50
   45 server errors detected

RESULT
  Possible service saturation
