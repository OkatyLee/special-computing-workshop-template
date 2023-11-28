package ru.spbu.apcyb.svp.tasks.task1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.spbu.apcyb.svp.tasks.task1.ChangeCombinations.calculateCombinations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ChangeTest {

  @Test
  void test1() {
    int targetAmount = 5;
    int[] denomination = {3, 2};
    Set<List<Integer>> expected = new HashSet<>();
    List<Integer> comb = new ArrayList<>();
    comb.add(1);
    comb.add(1);
    expected.add(comb);
    Scanner scanner = new Scanner("5\n3 2");
    Set<List<Integer>> actual = calculateCombinations(scanner);
    assertEquals(expected, actual);
  }

  @Test
  void test2() {
    int targetAmount = 4;
    int[] denomination = {1, 2};
    Set<List<Integer>> expected = new HashSet<>();
    List<Integer> comb = new ArrayList<>();
    comb.add(2);
    comb.add(1);
    expected.add(comb);
    comb = new ArrayList<>();
    comb.add(0);
    comb.add(2);
    expected.add(comb);
    comb = new ArrayList<>();
    comb.add(4);
    comb.add(0);
    expected.add(comb);
    Set<List<Integer>> actual = calculateCombinations(new Scanner("4\n1 2"));
    assertEquals(expected, actual);
  }

  @Test
  void test3() {
    Set<List<Integer>> expected = new HashSet<>();
    List<Integer> comb = new ArrayList<>();
    comb.add(2);
    comb.add(1);
    expected.add(comb);
    comb = new ArrayList<>();
    comb.add(0);
    comb.add(2);
    expected.add(comb);
    comb = new ArrayList<>();
    comb.add(4);
    comb.add(0);
    expected.add(comb);
    Set<List<Integer>> actual = calculateCombinations(new Scanner("4\n2 1"));
    assertEquals(expected, actual);
  }

  @Test
  void test4() {
    Set<List<Integer>> expected = new HashSet<>();
    List<Integer> comb = new ArrayList<>();
    comb.add(1);
    expected.add(comb);
    Set<List<Integer>> actual = calculateCombinations(new Scanner("1000\n1000"));
    assertEquals(expected, actual);
  }

  @Test
  void test5() {
    Set<List<Integer>> expected = new HashSet<>();
    Set<List<Integer>> actual = calculateCombinations(new Scanner("5\n10 6"));
    assertEquals(expected, actual);
  }

  @Test
  void test6() {
    Set<List<Integer>> expected = new HashSet<>();
    List<Integer> comb = new ArrayList<>();
    comb.add(5);
    expected.add(comb);
    Set<List<Integer>> actual = calculateCombinations(new Scanner("5\n1 1"));
    assertEquals(expected, actual);
  }


  void throwsTestTemplate(String scannerString) {
    Scanner scanner = new Scanner(scannerString);
    assertThrows(IllegalArgumentException.class, () -> {
      calculateCombinations(scanner);
    });
  }

  @Test
  void stringDenominations() {
    throwsTestTemplate("10\n2 three 4");
  }


  @Test
  void negativeAmount() {
    throwsTestTemplate("-1\n2 3 4");
  }

  @Test
  void negativeDenominations() {
    throwsTestTemplate("4\n3 -2");
  }
}

