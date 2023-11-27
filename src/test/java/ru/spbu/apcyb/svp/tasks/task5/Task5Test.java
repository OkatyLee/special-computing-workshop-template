package ru.spbu.apcyb.svp.tasks.task5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.spbu.apcyb.svp.tasks.task5.SteamAPI.countWords;
import static ru.spbu.apcyb.svp.tasks.task5.SteamAPI.writeToFile;
import static ru.spbu.apcyb.svp.tasks.task5.SteamAPI.writeWordCount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Task5Test {
  @Test
  void shortFileTest() throws IOException {
    String source = "src/test/resources/task5/test.txt";
    String outputDir = "src/test/resources/task5/test";
    Map<String, Long> words = countWords(source);
    writeToFile(words, outputDir);
    writeWordCount(words, outputDir);
    assertEquals(-1, Files.mismatch(
            Path.of("src/test/resources/task5/test_expected.txt"),
            Path.of("src/test/resources/task5/counts.txt")
        )
    );
    assertTrue(Files.exists(Path.of(outputDir)));
  }

  @Test
  void hugeFileTest() throws IOException {
    String source = "src/test/resources/task5/test1.txt";
    String outputDir = "src/test/resources/task5/test1";
    Path outputDirPath = Path.of(outputDir);

    if (!Files.exists(outputDirPath)) {
      Files.createDirectories(outputDirPath);
    }
    Map<String, Long> map = countWords(source);
    writeToFile(map, outputDir);
    writeWordCount(map, outputDir);
    Stream<String> fileStream = Files.lines(Paths.get(outputDir + "/counts.txt"));
    long lines = fileStream.count();
    fileStream.close();
    Stream<Path> files = Files.list(outputDirPath);
    long count = files.filter(Files::isRegularFile).count();
    assertEquals(lines + 1, count);

  }

}
