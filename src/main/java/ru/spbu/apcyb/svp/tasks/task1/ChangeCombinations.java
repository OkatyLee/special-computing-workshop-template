package ru.spbu.apcyb.svp.tasks.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ChangeMachine.
 */

public class ChangeCombinations {

  private static final Logger logger = Logger.getLogger(ChangeCombinations.class.getName());

  /**
   * Main func.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    logger.info("Input value to change: ");
    try {
      int targetAmount = scanner.nextInt();
      if (targetAmount <= 0) {
        throw new IllegalArgumentException();
      }
      logger.info("Input denominations separated by space: ");
      scanner.nextLine();
      String denominationsInput = scanner.nextLine();
      int[] denominations = Arrays.stream(denominationsInput.split(" "))
          .mapToInt(Integer::parseInt)
          .sorted()
          .toArray();
      if (denominations[0] <= 0) {
        throw new IllegalArgumentException();
      }
      calculateCombinations(targetAmount, denominations);
    } catch (RuntimeException e) {
      throw new IllegalArgumentException("Expected positive integers");
    }
  }

  /**
   * Calculate combination.
   *
   * @param targetAmount sum that we have to change
   * @param denominations used to change sum
   */
  public static Set<List<Integer>> calculateCombinations(int targetAmount, int[] denominations) {
    denominations = Arrays.stream(denominations).distinct().sorted().toArray();
    Set<List<Integer>>[] list = initializeList(targetAmount, denominations);
    fillCombinationsList(list, targetAmount, denominations);
    logResult(list, targetAmount, denominations);
    return list[targetAmount];
  }

  private static Set<List<Integer>>[] initializeList(int targetAmount, int[] denominations) {

    Set<List<Integer>>[] list = new HashSet[targetAmount + 1];
    for (int i = 0; i < targetAmount + 1; i++) {
      list[i] = new HashSet<>();
    }

    for (int i = 0; i < denominations.length; i++) {
      List<Integer> lst = new ArrayList<>((Collections.nCopies(denominations.length, 0)));
      lst.set(i, 1);
      if (denominations[i] <= targetAmount) {
        list[denominations[i]].add(lst);
      }
    }

    return list;
  }

  private static void fillCombinationsList(
      Set<List<Integer>>[] list,
      int targetAmount,
      int[] denominations) {
    for (int currSum = 0; currSum < targetAmount; currSum++) {
      if (!list[currSum].isEmpty()) {
        for (List<Integer> comb : list[currSum]) {
          addCombinationsToList(list, comb, targetAmount, denominations);
        }
      }
    }
  }

  private static void addCombinationsToList(
      Set<List<Integer>>[] list,
      List<Integer> comb,
      int targetAmount,
      int[] denominations
  ) {
    for (int index = 0; index < denominations.length; index++) {
      List<Integer> lst = new ArrayList<>((Collections.nCopies(denominations.length, 0)));
      Collections.copy(lst, comb);
      lst.set(index, comb.get(index) + 1);
      if (sumOfCombination(lst, denominations) <= targetAmount) {
        list[sumOfCombination(lst, denominations)].add(lst);
      }
    }
  }

  private static int sumOfCombination(List<Integer> combination, int[] denominations) {
    int sum = 0;
    for (int i = 0; i < combination.size(); i++) {
      sum += combination.get(i) * denominations[i];
    }
    return sum;
  }

  private static void logResult(Set<List<Integer>>[] list, int targetAmount, int[] denominations) {
    logger.log(Level.INFO, "Total unique combinations: {0}", list[targetAmount].size());
    if (list[targetAmount].isEmpty()) {
      logger.info("Combinations weren't found");
    } else {
      for (List<Integer> combination : list[targetAmount]) {
        logCombination(combination, denominations);
      }
    }
  }

  private static void logCombination(List<Integer> combination, int[] denominations) {
    StringBuilder combinationStr = new StringBuilder();
    for (int i = 0; i < combination.size(); i++) {
      for (int j = 0; j < combination.get(i); j++) {
        combinationStr.append(denominations[i]).append(" ");
      }
    }
    logger.log(Level.INFO, "Combination: {0}", combinationStr.toString().trim());
  }
}
