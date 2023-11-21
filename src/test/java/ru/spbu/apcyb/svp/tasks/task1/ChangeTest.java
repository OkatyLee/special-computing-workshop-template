package ru.spbu.apcyb.svp.tasks.task1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.spbu.apcyb.svp.tasks.task1.ChangeCombinations.calculateCombinations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    Set<List<Integer>> actual = calculateCombinations(targetAmount, denomination);
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
    Set<List<Integer>> actual = calculateCombinations(targetAmount, denomination);
    assertEquals(expected, actual);
  }

  @Test
  void test3() {
    int targetAmount = 4;
    int[] denomination = {2, 1};
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
    Set<List<Integer>> actual = calculateCombinations(targetAmount, denomination);
    assertEquals(expected, actual);
  }

  @Test
  void test4() {
    int targetAmount = 1000;
    int[] denomination = {1000};
    Set<List<Integer>> expected = new HashSet<>();
    List<Integer> comb = new ArrayList<>();
    comb.add(1);
    expected.add(comb);
    Set<List<Integer>> actual = calculateCombinations(targetAmount, denomination);
    assertEquals(expected, actual);
  }

  @Test
  void test5() {
    int targetAmount = 5;
    int[] denomination = {10, 6};
    Set<List<Integer>> expected = new HashSet<>();
    Set<List<Integer>> actual = calculateCombinations(targetAmount, denomination);
    assertEquals(expected, actual);
  }

  @Test
  void test6() {
    int targetAmount = 5;
    int[] denomination = {1, 1};
    Set<List<Integer>> expected = new HashSet<>();
    List<Integer> comb = new ArrayList<>();
    comb.add(5);
    expected.add(comb);
    Set<List<Integer>> actual = calculateCombinations(targetAmount, denomination);
    assertEquals(expected, actual);
  }


}
