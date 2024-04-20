
public class LinkedStack<T extends Comparable<T>> {
	private SNode<T> topNode;

	LinkedStack() {

	}

	public void push(T data) {
		SNode<T> newNode = new SNode<T>(data);
		newNode.setNext(topNode);
		topNode = newNode;
	}

	public SNode<T> pop() {
		SNode<T> node = topNode;
		if (topNode != null)
			topNode = topNode.getNext();
		return node;
	}

	public SNode<T> peek() {
		return topNode;
	}

	public int length() {
		int length = 0;
		SNode<T> curr = topNode;
		while (curr != null) {
			length++;
			curr = curr.getNext();
		}
		return length;
	}

	public void print() {
		if (!isEmpty()) {
			SNode<T> current = topNode;
			while (current != null) {
				System.out.println(current.getData());
				current = current.getNext();
			}
		}

	}

	public boolean isEmpty() {
		return (topNode == null);
	}

	public void clear() {
		topNode = null;
	}
}