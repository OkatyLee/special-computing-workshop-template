package ru.spbu.apcyb.svp.tasks.task3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static ru.spbu.apcyb.svp.tasks.task3.FileTreeWalker.listFiles;
import static ru.spbu.apcyb.svp.tasks.task3.FileTreeWalker.writeToFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileTreeWalkerTest {

  @Test
  void emptyDirTest() throws IOException {
    String directoryPath = "src/test/resources/task3/emptyDir";
    String pathToFile = "src/test/resources/task3/empty_dir_expected.txt";

    Path path = Path.of(directoryPath);
    Path pathToOutput = Path.of("src/test/resources/task3/empty_dir_output.txt");



    List<String> fileList = listFiles(path);
    writeToFile(pathToOutput, fileList);
    Path expectedPathToResult = Path.of(
        Path.of("src/test/resources/task3").toAbsolutePath() + "/empty_dir_output.txt");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(Path.of(pathToFile).toFile()))) {
      writer.write(path.toAbsolutePath().toString());
      writer.newLine();
    }

    if (Files.notExists(expectedPathToResult)) {
      fail("Cannot find result file at expected path.");
    }

    assertEquals(-1, Files.mismatch(Path.of(pathToFile), expectedPathToResult));
  }

  @Test
  void wSubdirTest() throws IOException {
    String directoryPath = "src/test/resources/task3/wSubDir/";
    String expectedPathToFile = "src/test/resources/task3/w_subdir_expected.txt";
    String actualPathToFile = "src/test/resources/task3/w_subdir_actual.txt";

    Path expectedPath = Path.of(expectedPathToFile);
    Path actualPath = Path.of(actualPathToFile);
    Path dirPath = Path.of(directoryPath);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(expectedPath.toFile()))) {
      writer.write(dirPath.toAbsolutePath().toString());
      writer.newLine();
      writer.write("outer.txt");
      writer.newLine();
      writer.write("subdir");
      writer.newLine();
      writer.write("\t\\inner.txt");
      writer.newLine();
    }


    writeToFile(actualPath, listFiles(dirPath));

    if (Files.notExists(expectedPath)) {
      fail("Cannot find result file at expected path.");
    }

    assertEquals(-1, Files.mismatch(actualPath, expectedPath));
  }

  @Test
  void woSubdirTest() throws IOException {
    String directoryPath = "src/test/resources/task3/woSubDirs";
    String pathToExpected = "src/test/resources/task3/wo_subdir_expected.txt";
    String pathToActual = "src/test/resources/task3/wo_subdir_actual.txt";

    Path actualFile = Path.of(pathToActual);
    Path expectedFile = Path.of(pathToExpected);
    Path path = Path.of(directoryPath);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(expectedFile.toFile()))) {
      writer.write(path.toAbsolutePath().toString());
      writer.newLine();
      writer.write("1.txt");
      writer.newLine();
      writer.write("2.txt");
      writer.newLine();
    }


    writeToFile(actualFile, listFiles(path));

    if (Files.notExists(expectedFile)) {
      fail("Cannot find result file at expected path.");
    }
    assertEquals(-1, Files.mismatch(actualFile, expectedFile));
  }

  @Test
  void invalidPathTest() {
    String directoryPath = "invalid_Directory";
    assertThrows(NoSuchFileException.class, () -> listFiles(Path.of(directoryPath)));
  }

}
