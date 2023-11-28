package ru.spbu.apcyb.svp.tasks.task3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Задание 3.
 */
public class FileTreeWalker {

  /**
   * MAIN FUNCTION.
   *
   * @param args args[0] = sourceDirectoryPath, args[1] = outputFilePath.
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("Usage: java FileTreeWalker"
          + " <source_directory> <output_file>");
    }

    String sourceDirectoryPath = args[0];
    String outputFile = args[1];

    Path sourceDirectory = Paths.get(sourceDirectoryPath);
    Path outputPath = Paths.get(outputFile);

    try {
      List<String> fileList = listFiles(sourceDirectory);
      writeToFile(outputPath, fileList);
      System.out.println("File list successfully written to " + outputFile);
    } catch (IOException e) {
      throw new InvalidPathException(sourceDirectoryPath, "This directory does not exist");
    }
  }

  /**
   * Creates list of paths of folders and files.

   * @param sourceDirectory directory
   * @return list of sub dirs.
   * @throws IOException
   NoSuchFileException.

   */
  public static List<String> listFiles(Path sourceDirectory) throws IOException {
    List<String> fileList = new ArrayList<>();
    Files.walkFileTree(sourceDirectory, new SimpleFileVisitor<>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String path = sourceDirectory.relativize(file).toString();
        if (!path.isEmpty()) {
          fileList.add("\t".repeat((int) path.chars().filter(ch -> ch == '\\').count())
              + path.substring(Math.max(path.lastIndexOf('\\'), 0)));
        } else {
          fileList.add(file.toAbsolutePath().toString());
        }
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        String path = sourceDirectory.relativize(dir).toString();
        if (!path.isEmpty()) {
          fileList.add("\t".repeat((int) path.chars().filter(ch -> ch == '\\').count())
              + path.substring(Math.max(path.lastIndexOf('\\'), 0)));
        } else {
          fileList.add(dir.toAbsolutePath().toString());
        }
        return FileVisitResult.CONTINUE;
      }
    });
    return fileList;
  }

  /**
   * Write paths to output file.

   * @param outputPath Path to write
   * @param fileList List of paths to write
   * @throws IOException NoSuchFileException

   */
  public static void writeToFile(Path outputPath, List<String> fileList) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath.toFile()))) {
      for (String entry : fileList) {
        writer.write(entry);
        writer.newLine();
      }
    }
  }
}
