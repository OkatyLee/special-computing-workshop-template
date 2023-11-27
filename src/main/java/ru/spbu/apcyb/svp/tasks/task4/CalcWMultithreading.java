package ru.spbu.apcyb.svp.tasks.task4;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;


public class CalcWMultithreading {

  private static final Logger logger = Logger.getLogger("Task4");

  public static void main(String[] args)
      throws IOException, ExecutionException, InterruptedException {
    if (args.length != 2) {
      throw new IllegalArgumentException("Usage: <source_directory> <output_file>");
    }
    final String sourceFile = args[0];
    final String outputFile = args[1];
    List<Double> list = readDoublesFromFile(sourceFile);

    Instant startTime = Instant.now();
    computeTangentWithMultiThread(list,
        Runtime.getRuntime().availableProcessors());
    logger.info("Performance time with multi thread %d"
        .formatted(Duration.between(startTime, Instant.now()).toMillis()));
    startTime = Instant.now();
    List<Double> tangentList = computeTangentWithSingleThread(list);
    logger.info("Performance time with single thread %d"
        .formatted(Duration.between(startTime, Instant.now()).toMillis()));
    writeDoublesToFile(tangentList, outputFile);

  }

  public static List<Double> computeTangentWithSingleThread(List<Double> list) {
    return list.stream().map(Math::tan).toList();
  }

  public static List<Double> computeTangentWithMultiThread(List<Double> list, int numThreads)
      throws ExecutionException, InterruptedException {

    try (ExecutorService executorService = Executors.newFixedThreadPool(numThreads)) {
      Future<List<Double>> futures = executorService.submit(
          () -> list.parallelStream().map(Math::tan).toList());
      return futures.get();
    } catch (ExecutionException e) {
      throw new ExecutionException("Exception during thread execution", e);
    } catch (InterruptedException e) {
      throw new InterruptedException(e.getMessage());
    }
  }

  public static List<Double> readDoublesFromFile(String fileName) throws IOException {
    List<Double> list = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = br.readLine()) != null) {
        double number = Double.parseDouble(line);
        list.add(number);
      }
    } catch (IOException e) {
      throw new IOException("File doesn't exist", e);
    }

    return list;
  }

  public static void writeDoublesToFile(List<Double> list, String fileName) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      for (Double value : list) {
        writer.write(value.toString());
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IOException("Filename wasn't the file", e);
    }
  }

  public static double performanceCheckSingleThread(List<Double> list) {
    Instant startTime = Instant.now();
    computeTangentWithSingleThread(list);
    return Duration.between(startTime, Instant.now()).toMillis();
  }

  public static double performanceCheckMultiThread(List<Double> list)
      throws ExecutionException, InterruptedException {
    Instant startTime = Instant.now();
    computeTangentWithMultiThread(list, Runtime.getRuntime().availableProcessors());
    return Duration.between(startTime, Instant.now()).toMillis();
  }


}
