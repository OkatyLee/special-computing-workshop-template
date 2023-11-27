package ru.spbu.apcyb.svp.tasks.task4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Generator {

  public void generate(String fileName, int num) throws IOException {
    Random random = new Random();
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
