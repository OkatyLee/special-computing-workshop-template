package ru.spbu.apcyb.svp.tasks.task4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.spbu.apcyb.svp.tasks.task4.CalcWMultithreading.computeTangentWithMultiThread;
import static ru.spbu.apcyb.svp.tasks.task4.CalcWMultithreading.computeTangentWithSingleThread;
import static ru.spbu.apcyb.svp.tasks.task4.CalcWMultithreading.readDoublesFromFile;
import static ru.spbu.apcyb.svp.tasks.task4.CalcWMultithreading.writeDoublesToFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MultiThreadTest {
  @Test
  void testGenerateNumbers() throws IOException {
    String filename = "src\\test\\resources\\generatorTest.txt";
    Generator generator = new Generator();
    generator.generate(filename, 100);
    assertTrue(Files.exists(Paths.get(filename)));
  }

  @ParameterizedTest
  @CsvSource ({"1", "100", "1000000"})
  void perfomanceTest(int num) throws IOException, ExecutionException, InterruptedException {
    Logger logger = Logger.getLogger("Test");
    String filename = "src\\test\\resources\\test_%d.txt".formatted(num);
    Generator generator = new Generator();
    generator.generate(filename, num);
    List<Double> list = readDoublesFromFile(filename);
    Instant start = Instant.now();
    computeTangentWithSingleThread(list);
    logger.info("Single thread compute %d values for %d milliseconds"
        .formatted(num, Duration.between(start, Instant.now()).toMillis()));
    start = Instant.now();
    computeTangentWithMultiThread(list, 4);
    logger.info("Multi thread compute %d values for %d milliseconds"
        .formatted(num, Duration.between(start, Instant.now()).toMillis()));
  }

  @Test
  void writeToFileTest() throws IOException {
    String filename = "src\\test\\resources\\writingTest.txt";
    List<Double> list = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      list.add(1. * i);
    }
    writeDoublesToFile(list, filename);
    List<Double> fromFile = readDoublesFromFile(filename);
    assertEquals(list.size(), fromFile.size());

    for (int i = 0; i < fromFile.size(); i++) {
      assertEquals(1. * i, fromFile.get(i));
    }

  }
}
