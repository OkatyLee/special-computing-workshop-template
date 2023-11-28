package ru.spbu.apcyb.svp.tasks.task5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SteamAPI {

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      throw new IllegalArgumentException("Usage: <sourceFile> <outputDirectory>");
    }

    String sourceFile = args[0];
    String outputDirectory = args[1];
    Map<String, Long> words = countWords(sourceFile);
    writeToFile(words, outputDirectory);
    writeWordCount(words, outputDirectory);

  }

  public static Map<String, Long> countWords(String fileName) throws IOException {

    try (ExecutorService executor = Executors.newFixedThreadPool(4);
        Stream<String> lines = Files.lines(Path.of(fileName))) {
      CompletableFuture<Map<String, Long>> future = CompletableFuture.supplyAsync(() ->
              lines.parallel()
                  .flatMap(line -> Arrays.stream(line.split("[^a-zA-ZЁёА-я0-9]")))
                  .map(String::toLowerCase)
                  .collect(Collectors
                      .groupingByConcurrent(Function.identity(), Collectors.counting())),
          executor);

      return future.join();
    } catch (IOException e) {
      throw new IOException("Failed reading from file", e);
    }
  }

  public static void writeToFile(Map<String, Long> wordCounts, String outputDir)
      throws IOException {
    Iterable<CharSequence> content = wordCounts.entrySet().stream()
        .map(entry -> entry.getKey() + " " + entry.getValue())
        .collect(Collectors.toList());
    Files.write(Path.of(outputDir + "/counts.txt"), content);

  }

  public static void writeWordCount(Map<String, Long> wordCounts, String outputDirectory) {
    CompletableFuture<?>[] fileFutures = wordCounts.keySet().stream()
        .map(word -> CompletableFuture.runAsync(() -> {
          try {
            String content = word + " " + wordCounts.get(word);
            Files.write(Path.of(outputDirectory + "/" + word + "_.txt"), content.getBytes());
          } catch (IOException e) {
            throw new CompletionException("Wrong output directory or unable writing to file", e);
          }
        }))
        .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(fileFutures).join();
  }

}

