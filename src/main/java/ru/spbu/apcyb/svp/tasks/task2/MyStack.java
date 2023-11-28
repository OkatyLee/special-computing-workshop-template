package ru.spbu.apcyb.svp.tasks.task2;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Stack based on array-based list.
 */
public class MyStack<T> extends Stack<T> {

  private final transient MyList<T> value;
  private int size;

  /**
   * Default constructor w/o params.
   */
  public MyStack() {
    this.value = new MyList<>();
    this.size = 0;
  }

  /**
   * Construct stack with initial params.

   * @param list init array.
   */
  public MyStack(Object[] list) {
    this.value = new MyList<>(list);
    this.size = this.value.size();
  }

  @Override
  public synchronized int size() {
    return size;
  }

  @Override
  public T push(Object elem) {
    this.value.add(0, elem);
    ++size;
    return (T) elem;
  }

  @Override
  public synchronized T pop() {
    try {
      --size;
      return value.remove(0);
    } catch (RuntimeException e) {
      throw new EmptyStackException();
    }
  }

  @Override
  public synchronized boolean isEmpty() {
    return size == 0;
  }

  @Override
  public synchronized T peek() {
    if (!value.isEmpty()) {
      return value.get(0);
    }
    return null;
  }


}


