package ru.spbu.apcyb.svp.tasks.task2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Array-based list.
 */
public class MyList<T> implements List<T> {

  private static final String IOOB = "Index is out of bounds";
  private Object[] array;
  private int size;

  public MyList() {
    this.array = new Object[10];
    this.size = 0;
  }

  /**
   * Constructor.
   */

  public MyList(Object[] list) {
    this.size = list.length;
    this.array = increaseCapacity();
    System.arraycopy(list, 0, this.array, 0, list.length);

  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public T get(int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(IOOB);
    }
    return (T) array[index];
  }

  @Override
  public T set(int index, Object element) {
    if (index > size || index < 0) {
      throw new IndexOutOfBoundsException(IOOB);
    }
    Object obj = array[index];
    array[index] = element;
    return (T) obj;
  }

  @Override
  public boolean add(Object element) {
    if (size == array.length) {
      Object[] newArray = increaseCapacity();
      System.arraycopy(array, 0, newArray, 0, size);
      array = newArray;
    }
    array[size++] = element;
    return true;
  }

  private Object[] increaseCapacity() {
    if (size >= Integer.MAX_VALUE / 2) {
      return new Object[Integer.MAX_VALUE];
    }
    return new Object[size * 2];
  }

  @Override
  public void add(int index, Object element) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(IOOB);
    }
    Object[] newArray;
    if (array.length == size) {
      newArray = increaseCapacity();
    } else {
      newArray = new Object[array.length];
    }
    System.arraycopy(array, 0, newArray, 0, index);
    newArray[index] = element;
    System.arraycopy(array, index, newArray, index + 1, (size++) - index);
    array = newArray;
  }

  @Override
  public boolean remove(Object o) {
    if (contains(o)) {
      remove(indexOf(o));
      return true;
    }
    return false;
  }

  @Override
  public T remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(IOOB);
    }
    Object removedElement = array[index];
    System.arraycopy(array, index + 1, array, index, size - index - 1);
    array[--size] = null;
    return (T) removedElement;
  }

  @Override
  public boolean addAll(Collection c) {
    for (Object obj : c) {
      add(obj);
    }
    return true;
  }

  @Override
  public boolean addAll(int index, Collection c) {
    throw new UnsupportedOperationException("'addAll' is unsupported operation");
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int indexOf(Object o) {
    for (int i = 0; i < size; ++i) {
      if (o.equals(array[i])) {
        return i;
      }
    }
    return -1;
  }


  @Override
  public int lastIndexOf(Object o) {
    for (int i = size - 1; i > -1; --i) {
      if (o.equals(array[i])) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void clear() {
    array = new Object[10];
    size = 0;
  }

  @Override
  public boolean retainAll(Collection c) {
    throw new UnsupportedOperationException("'retainAll' is unsupported operation");
  }

  @Override
  public boolean removeAll(Collection c) {
    for (Object obj : c) {
      remove(obj);
    }
    return true;
  }


  @Override
  public boolean containsAll(Collection c) {
    for (Object obj : c) {
      if (!contains(obj)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean contains(Object o) {
    for (int i = 0; i < size; ++i) {
      if (o.equals(array[i])) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    throw new UnsupportedOperationException("'iterator' is unsupported operation");
  }

  @Override
  public Object[] toArray() {
    return array;
  }

  @Override
  public T[] toArray(Object[] a) {
    throw new UnsupportedOperationException("'toArray(Object[] a)' is unsupported operation");
  }

  @Override
  public ListIterator<T> listIterator() {
    throw new UnsupportedOperationException("'listIterator' is unsupported operation");
  }

  @Override
  public ListIterator<T> listIterator(int index) {
    throw new UnsupportedOperationException("'listIterator' is unsupported operation");
  }

  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException("'subList' is unsupported operation");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    MyList<T> myList = (MyList<T>) o;
    return size == myList.size
        && Arrays.equals(
            Arrays.copyOf(array, size),
        Arrays.copyOf(myList.array, myList.size));
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(super.hashCode(), size);
    result = 31 * result + Arrays.hashCode(Arrays.copyOf(array, size));
    return result;
  }
}
