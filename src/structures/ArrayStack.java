package structures;

import java.util.Arrays;

public /*
 * by Mark Hawes, Alain Njipwo, Jonathan Lee, and Zach Albers
 * 
 * toArray can be included. It provides a full list of what is in the stack in a
 * way that is more secure and easier to read.
 */

/**
 * A class of stacks whose entries are stored in an array.
 * 
 * @author Frank M. Carrano and Timothy M. Henry
 * @version 4.0
 */
class ArrayStack<T> implements StackInterface<T> {
	private T[] stack; // Array of stack entries
	private int topIndex; // Index of top entry
	private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 50;
	private static final int MAX_CAPACITY = 10000;

	public ArrayStack() {
		this(DEFAULT_CAPACITY);
	} // end default constructor

	public ArrayStack(int initialCapacity) {
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[]) new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
		initialized = true;
	} // end constructor

	// < Implementations of the stack operations go here. >
	// < Implementations of the private methods go here; checkCapacity and
	// checkInitialization are analogous to those in Chapter 2. >
	// . . .
	private boolean checkCapacity(int capacity) {
		boolean bool = true;
		if (capacity > MAX_CAPACITY)
			bool = false;
		return bool;
	}

	private void ensureCapacity() {
		if (topIndex == stack.length - 1) // if array is full,
			// double size of array
			stack = Arrays.copyOf(stack, 2 * stack.length);
	} // end ensureCapacity

	@Override
	public void push(T newEntry) {
		ensureCapacity();
		topIndex++;
		stack[topIndex] = newEntry;
	} // end push

	@Override
	public T pop() {
		T top = null;
		if (!isEmpty()) {
			top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
		} // end if
		return top;
	} // end pop

	@Override
	public T peek() {
		T top = null;
		if (!isEmpty())
			top = stack[topIndex];
		return top;
	} // end peek

	@Override
	public boolean isEmpty() {
		return topIndex < 0;
	}

	@Override
	public void clear() {
		stack = (T[]) new Object[DEFAULT_CAPACITY];
		topIndex = -1;
	}

	public T[] toArray() {
		Object[] array = new Object[stack.length];
		int index = 0;
		while (!isEmpty()) {
			array[index] = pop();
		}
		for (int i = array.length - 1; i > 0; i--)
			push((T) array[i]);
		return (T[]) array;
	}
} // end ArrayStack