package ru.spbu.apcyb.svp.tasks.task4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Implements generate method.
 */

public class Generator {

  private final SecureRandom random = new SecureRandom();

  /**
   * Used to generate random doubles and writing to file.
   *
   * @param fileName file name to write
   * @param num number of doubles to generate
   * @throws IOException wrong path or failed to write to file
   */
  public void generate(String fileName, int num) throws IOException {

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

      for (int i = 0; i < num; i++) {
        double value = random.nextDouble() * 100 - 50;
        writer.write(String.valueOf(value));
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IOException("Filename wasn't the file", e);
    }
  }
}
