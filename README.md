# J-Grip

**A lightweight HTTP load-testing tool built with Java.**

J-Grip is a work-in-progress project exploring HTTP request execution, latency measurement, error handling, and performance statistics using Java's built-in `HttpClient`.

The goal is to understand what happens when an API receives repeated requests — and how to turn the outcomes into meaningful measurements.

## Why J-Grip?

An HTTP `200 OK` response tells us that a request received a successful status code. It doesn't tell us the entire story.

How long did the request take? How many requests failed? How does latency change under load?

J-Grip is my attempt to explore these questions by building a lightweight HTTP testing tool from the ground up, using Java's standard library.

## Current Features

- **HTTP requests:** Send GET requests using Java's built-in `HttpClient`.
- **Sequential execution:** Execute a configurable number of requests in sequence.
- **Latency measurement:** Measure elapsed request time using `System.nanoTime()`.
- **Outcome tracking:** Record HTTP status codes and exceptions.
- **Performance statistics:** Calculate success rate, error rate, and minimum, maximum, and average latency.
- **Timeout handling:** Configure a request timeout.

> J-Grip currently supports sequential request execution. Concurrent load generation and more advanced performance metrics are planned, not yet implemented.

## How It Works

```text
             Target URL
                 |
                 v
        Build HTTP Request
                 |
                 v
         Java HttpClient
                 |
                 v
       Execute Sequentially
                 |
                 v
       Collect Measurements
       /        |           \
  Status     Latency     Exceptions
       \        |           /
                 v
        Calculate Statistics
                 |
                 v
          Print Summary
```

The tool records the outcome of each request and aggregates the collected measurements into a summary.

## Tech Stack

- **Language:** Java
- **HTTP client:** `java.net.http.HttpClient`
- **Networking:** `java.net.URI`, `HttpRequest`, `HttpResponse`
- **Timing:** `System.nanoTime()`
- **Dependencies:** Java standard library only

## Getting Started

### Prerequisites

- JDK 11 or later
- A network connection to the target API

### Run the program

Clone the repository:

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd <YOUR_REPOSITORY_DIRECTORY>
```

Compile the Java source file:

```bash
javac Jgrip.java
```

Run it:

```bash
java Jgrip
```

The current implementation uses a configured target URL and request count in the source code.

**Note:** Update the URL or request count in the source before running if you want to test a different endpoint or number of requests.

## Example Output

The following illustrates the types of statistics J-Grip is designed to report. Values are placeholders, not measured benchmark results.

```text
Success Rate=98.0%
Error Rate=2.0%
Exceptions=2
Average Latency=145ms
Minimum Latency=92ms
Maximum Latency=510ms
```

Actual output and values depend on the target endpoint, network conditions, and the implementation's current handling of failed or interrupted requests.

## Engineering Concepts

Building J-Grip involves exploring:

- HTTP request and response lifecycles
- URLs and URI representation in Java
- Request timeouts and exception handling
- Elapsed-time measurement with `System.nanoTime()`
- HTTP status codes versus transport failures
- Latency aggregation and success/error calculations
- Thread interruption and execution control

## Roadmap

- [x] Send an HTTP GET request
- [x] Execute repeated sequential requests
- [x] Track response status codes and exceptions
- [x] Measure request latency
- [x] Calculate basic performance statistics
- [ ] Refine latency accounting for failed and interrupted requests
- [ ] Improve interruption handling and test termination
- [ ] Add concurrent request execution
- [ ] Measure throughput and latency percentiles (p50, p95, p99)
- [ ] Add configurable command-line arguments
- [ ] Add automated tests and result validation

## Project Status

J-Grip is actively being developed as a learning-driven engineering project. The current focus is correctness in sequential execution, error handling, and measurement before introducing concurrency.

## Contributing

Suggestions, issues, and discussions are welcome. If you're interested in HTTP internals, Java networking, or performance testing, feel free to explore the code.

---

**Built with Java, one request at a time.**
