package ru.spbu.apcyb.svp.tasks.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.EmptyStackException;
import org.junit.jupiter.api.Test;

class MyStackTest {

  @Test
  void popTest() {
    MyStack<String> stack = new MyStack<>(new String[] {"1st", "2nd", "3rd", "4th"});
    String elem = stack.pop();
    MyStack<String> expectedAns = new MyStack<>(new String[] {"2nd", "3rd", "4th"});
    assertEquals("1st", elem);
    assertEquals(expectedAns.size(), stack.size());
    for (int index = 0; index < expectedAns.size(); index++) {
      assertEquals(expectedAns.pop(), stack.pop());
    }
  }

  @Test
  void isEmptyTest() {
    assertTrue((new MyStack<Integer>()).isEmpty());
    assertFalse((new MyStack<Double>(new Double[]{1.})).isEmpty());
  }

  @Test
  void peekTest() {
    MyStack<Integer> stack = new MyStack<>(new Integer[] {1, 2, 3});
    assertEquals(1, stack.peek());
    assertNull((new MyStack<>()).peek());
  }

  @Test
  void popFromEmptyStackTest() {
    assertThrows(EmptyStackException.class, new MyStack<>()::pop);
  }

  @Test
  void pushTests() {
    MyStack<Integer> stack = new MyStack<>(new Integer[]{1, 2, 3});
    MyStack<Integer> expectedAns = new MyStack<>(new Integer[]{0, 1, 2, 3});
    stack.push(0);
    assertEquals(expectedAns.size(), stack.size());
    for (int index = 0; index < expectedAns.size(); index++) {
      assertEquals(expectedAns.pop(), stack.pop());
    }
  }

  @Test
  void pushToEmptyTest() {
    MyStack<Integer> stack = new MyStack<>();
    MyStack<Integer> expectedAns = new MyStack<>(new Integer[]{0});
    stack.push(0);
    assertEquals(expectedAns.size(), stack.size());
    for (int index = 0; index < expectedAns.size(); index++) {
      assertEquals(expectedAns.pop(), stack.pop());
    }
  }

  @Test
  void multiPushTest() {
    Integer[] arr = new Integer[100];
    for (int index = 0; index < 100; index++) {
      arr[index] = index * 2;
    }
    MyStack<Integer> expectedAns = new MyStack<>(arr);
    MyStack<Integer> actualAns = new MyStack<>();
    for (int index = 99; index > -1; index--) {
      actualAns.push(index * 2);
    }
    assertEquals(expectedAns.size(), actualAns.size());
    for (int index = 0; index < expectedAns.size(); index++) {
      assertEquals(expectedAns.pop(), actualAns.pop());
    }
  }

  @Test
  void multiPopTest() {
    Integer[] arr = new Integer[100];
    for (int index = 0; index < 100; index++) {
      arr[index] = index * 2;
    }
    int index = 0;
    MyStack<Integer> stack = new MyStack<>(arr);
    while (!stack.isEmpty()) {
      assertEquals(arr[index++], stack.pop());
    }
  }
}
