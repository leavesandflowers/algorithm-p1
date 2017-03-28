import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int count;
	private int capacity;
	private int end;
	private Item[] a;

	private class RandomInterator implements Iterator<Item> {
		private Item[] t;
		private int rear = 0;

		public RandomInterator(Item[] old) {
			t = (Item[]) new Object[count];
			for (Item i : old) {
				if (i != null) {
					t[rear] = i;
					rear++;
				}
			}
			StdRandom.shuffle(t);
		}

		@Override
		public boolean hasNext() {
			return rear != 0;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			// int i = StdRandom.uniform(rear);
			Item r = t[--rear];
			// Item r = t[i];
			// Item temp = t[i];
			// t[i] = t[rear - 1];
			// t[rear - 1] = temp;
			// rear--;
			return r;

		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public RandomizedQueue() {
		// construct an empty randomized queue
		end = 0;
		count = 0;
		capacity = 2;
		a = (Item[]) new Object[capacity];
	}

	public boolean isEmpty() {
		// is the queue empty?
		return count == 0;
	}

	public int size() {
		// return the number of items on the queue
		return count;
	}

	public void enqueue(Item item) {
		// add the item
		if (item == null)
			throw new NullPointerException();
		if (end >= capacity)
			resize();
		a[end] = item;
		end++;
		count++;
		assert check();
	}

	private void resize() {
		capacity = 2;
		while (capacity / 2 < count) {
			capacity *= 2;
		}
		Item[] old = a;
		a = (Item[]) new Object[capacity];
		end = 0;
		count = 0;
		for (Item i : old) {
			if (i != null) {
				a[end] = i;
				end++;
				count++;
			}
		}
		old = null;

	}

	public Item dequeue() {
		// remove and return a random item
		if (isEmpty())
			throw new NoSuchElementException();
		if (count <= capacity / 4)
			resize();
		int random = StdRandom.uniform(end);
		while (a[random] == null) {
			random = StdRandom.uniform(end);
		}
		Item r = a[random];
		a[random] = null;
		count--;
		assert check();
		return r;
	}

	public Item sample() {
		// return (but do not remove) a random item
		if (isEmpty())
			throw new NoSuchElementException();
		int random = StdRandom.uniform(end);
		while (a[random] == null) {
			random = StdRandom.uniform(end);
		}
		assert check();
		return a[random];
	}

	public Iterator<Item> iterator() {
		// return an independent iterator over items in random order
		return new RandomInterator(a);
	}

	private boolean check() {
		if (count < 0)
			return false;
		else if (count == 0) {
			if (capacity != 2)
				return false;
		} else {
			if (end < 0 || end > capacity)
				return false;
			if (count > capacity)
				return false;
			if (count < capacity / 4 - 1)
				return false;
		}
		int n = 0;
		for (Item i : a) {
			if (i != null) {
				n++;
			}
		}
		if (n != count)
			return false;
		return true;
	}

	public static void main(String[] args) {
		// unit testing
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (item.indexOf("+") >= 0) {
				q.enqueue(item.substring(1, item.length()));
			} else if (item.indexOf("-") >= 0) {
				String i = q.dequeue();
				StdOut.println("dequeue:" + i);
			} else if (item.indexOf("?") >= 0) {
				String i = q.sample();
				StdOut.println("sample:" + i);
			}
			StdOut.println(q.size());
			Iterator<String> i = q.iterator();
			while (i.hasNext()) {
				StdOut.print(i.next());
			}
			StdOut.println();
		}

	}
}
