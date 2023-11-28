package ru.spbu.apcyb.svp.tasks.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

class MyListTests {

  @Test
  void addToTailTest() {
    MyList<Integer> lst = new MyList<>(new Integer[] {1, 2, 3});
    lst.add(4);
    MyList<Integer> expectedAns = new MyList<>(new Integer[] {1, 2, 3, 4});
    for (int index = 0; index < expectedAns.size(); index++){
      assertEquals(expectedAns.get(index), lst.get(index));
    }
  }

  @Test
  void addByIndexTest() {

    MyList<String> lst = new MyList<>(new String[] {"One", "Two", "Four"});
    lst.add(2, "Three");
    MyList<String> expectedAns = new MyList<>(new String[] {
        "One", "Two", "Three", "Four"});
    for (int index = 0; index < expectedAns.size(); index++){
      assertEquals(expectedAns.get(index), lst.get(index));
    }
  }

  @Test
  void addByLastIndexTest() {
    MyList<Integer> lst = new MyList<>(new Integer[] {1, 3, 4, 5});
    lst.add(4, -1);
    MyList<Integer> expectedAns = new MyList<>(new Integer[] {1, 3, 4, 5, -1});
    for (int index = 0; index < expectedAns.size(); index++) {
      assertEquals(expectedAns.get(index), lst.get(index));
    }

  }

  @Test
  void isEmptyTest() {
    assertTrue((new MyList<>()).isEmpty());
  }

  @Test
  void addByZeroIndexTest() {
    MyList<Integer> lst = new MyList<>(new Integer[] {1, 3, 4, 5});
    lst.add(0, -1);
    MyList<Integer> expectedAns = new MyList<>(new Integer[] {-1, 1, 3, 4, 5});
    for (int index = 0; index < expectedAns.size(); index++) {
      assertEquals(expectedAns.get(index), lst.get(index));
    }
  }

  @Test
  void deleteByIndexTest() {
    MyList<Integer> lst = new MyList<>(new Integer[] {1, 2, 3, 4, 5});
    lst.remove(1);
    MyList<Integer> expectedAns = new MyList<>(new Integer[] {1, 3, 4, 5});
    for (int index = 0; index < expectedAns.size(); index++){
      assertEquals(expectedAns.get(index), lst.get(index));
    }
  }

  @Test
  void deleteByNonExistingIndexTest() {
    MyList<Integer> lst = new MyList<>(new Integer[] {1, 2, 3, 4, 5});
    assertThrows(IndexOutOfBoundsException.class, () -> lst.remove(5));
  }

  @Test
  void isContainsTest() {
    MyList<Double> lst = new MyList<>(new Double[] {1.3, 2., 3.2});
    assertTrue(lst.contains(1.3));
    assertFalse(lst.contains(3.14));
  }

  @Test
  void getByIndexTest() {
    MyList<Double> lst = new MyList<>(new Double[] {1.3, 2., 3.2, 1.4123, 6.2831});
    assertEquals(6.2831, lst.get(4));
    assertEquals(1.3, lst.get(0));
    assertThrows(IndexOutOfBoundsException.class, () -> lst.get(6));
    assertThrows(IndexOutOfBoundsException.class, () -> lst.get(-1));
  }



}
