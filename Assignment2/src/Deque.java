import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Deque<Item> implements Iterable<Item> {
	private Node front;
	private Node end;
	private int count;

	private class Node {
		private Item item;
		private Node pre;
		private Node next;
	}

	private class DequeInterator implements Iterator<Item> {
		private Node current = front;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public Deque() {
		// construct an empty deque
		front = null;
		end = null;
		count = 0;
	}

	public boolean isEmpty() {
		// is the deque empty?
		return count == 0;
	}

	public int size() {
		// return the number of items on the deque
		return count;
	}

	public void addFirst(Item item) {
		// add the item to the front
		if (item == null)
			throw new NullPointerException();
		if (isEmpty()) {
			front = new Node();
			front.item = item;
			end = front;
		} else {
			Node oldFront = front;
			front = new Node();
			front.item = item;
			front.next = oldFront;
			oldFront.pre = front;
		}
		count++;
		assert checkf();
	}

	public void addLast(Item item) {
		// add the item to the end
		if (item == null)
			throw new NullPointerException();

		if (isEmpty()) {
			end = new Node();
			end.item = item;
			front = end;
		} else {
			Node oldEnd = end;
			end = new Node();
			end.item = item;
			end.pre = oldEnd;
			oldEnd.next = end;
		}
		count++;
		assert checkf();
	}

	public Item removeFirst() {
		// remove and return the item from the front
		if (isEmpty())
			throw new NoSuchElementException();
		Item item = front.item;
		front = front.next;
		if (front != null)
			front.pre = null;
		count--;
		if (isEmpty()) {
			front = null;
			end = null;
		}
		assert checkf();
		return item;
	}

	public Item removeLast() {
		// remove and return the item from the end
		if (isEmpty())
			throw new NoSuchElementException();
		Item item = end.item;
		end = end.pre;
		if (end != null)
			end.next = null;
		count--;
		if (isEmpty()) {
			front = null;
			end = null;
		}
		assert checkf();
		return item;
	}

	public Iterator<Item> iterator() {
		// return an iterator over items in order from front to end
		return new DequeInterator();
	}

	private boolean checkf() {
		// check a few properties of instance variable 'first'
		if (count < 0) {
			return false;
		}
		if (count == 0) {
			if (front != null || end != null)
				return false;
		} else if (count == 1) {
			if (front == null && end == null)
				return false;
			if (front.next != null && end.pre != null)
				return false;
		} else {
			if (front == null || end == null)
				return false;
			if (front.next == null || end.pre == null)
				return false;
		}

		// check internal consistency of instance variable n
		int numberOfNodes = 0;
		for (Node x = front; x != null && numberOfNodes <= count; x = x.next) {
			numberOfNodes++;
		}
		if (numberOfNodes != count)
			return false;

		return true;
	}

	public static void main(String[] args) {
		// unit testing
		Stopwatch s = new Stopwatch();
		Deque<String> q = new Deque<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (item.indexOf("[") >= 0) {
				q.addFirst(item.substring(1, item.length()));
			} else if (item.indexOf("]") >= 0) {
				q.addLast(item.substring(1, item.length()));
			} else if (item.indexOf("<") >= 0) {
				q.removeFirst();
			} else if (item.indexOf(">") >= 0) {
				q.removeLast();
			}
			StdOut.println(q.size());
			Iterator<String> i = q.iterator();
			while (i.hasNext()) {
				StdOut.print(i.next());
			}
			StdOut.println();
			StdOut.println("time:" + s.elapsedTime());
		}
	}

}