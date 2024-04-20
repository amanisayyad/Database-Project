
public class SLinkedList<T extends Comparable<T>> {
	private Node<T> head;
	private Node<T> curr;
	private Node<T> prev;

	public void insertAtHead(T data) {
		Node<T> newNode = new Node<T>(data);
		newNode.setNext(head);
		head = newNode.getNext();
	}

	public void insertAtLast(T data) {
		Node<T> newNode1 = new Node<T>(data);
		if (head == null)
			head = newNode1;
		else {
			Node<T> curr = head;
			while (curr.getNext() != null) {
				curr = curr.getNext();
				curr.setNext(newNode1);
			}
		}

	}

	public void insertSorted(T data) {
		Node<T> newNode2 = new Node<T>(data);
		Node<T> prev = head, curr = head;
		while (curr != null && curr.getData().compareTo(data) < 0) {
			prev = curr;
			curr = curr.getNext();
		}
		if (head == null)// case 1: empty list
			head = newNode2;
		else if (curr == prev) { // case 2: insert at head
			newNode2.setNext(head);
			head = newNode2;
		} else { // case 3 and 4:insert between or at last
			newNode2.setNext(curr);
			prev.setNext(newNode2);
		}

	}

	public T insertRecSorted(T data, Node<T> prev, Node<T> curr) {
		Node<T> newNode = new Node<T>(data);
		if (curr == null)// empty list
			return null;
		if (curr.getData().compareTo(data) == 0) {
			head = newNode.getNext();
			return (T) newNode;
		}
		if (curr.getData().compareTo(data) < 0) {
			Node<T> next = (Node<T>) insertRecSorted(data, head.getNext(), curr);
			next = head.getNext();
		}
		return (T) head;
	}

	public T findUnsorted(T data) {
		Node<T> curr = this.head;
		while (curr != null) {
			if (curr.getData().equals(data))
				return curr.getData();
			curr = curr.getNext();
		}
		return null;

	}

	public T findSorted(T data) {
		Node<T> curr = this.head;
		while (curr != null && curr.getData().compareTo(data) <= 0) {
			if (curr.getData().equals(data))
				return curr.getData();
			curr = curr.getNext();
		}
		return null;

	}

	public T findRecSorted(T data) {
		Node<T> curr = this.head;
		if (curr.getData().equals(data)) {
			return findRecSorted(data);

		}
		return null;
	}

	public T deleteUnsorted(T data) {
		T temp = null;
		Node<T> prev = null, curr = head;

		while (curr != null && !curr.getData().equals(data)) {
			prev = curr;
			curr = curr.getNext();
		}

		if (curr == null) // case 1 : empty list
			return temp;
		else if (prev == null) {// first node
			temp = head.getData();
			head = head.getNext();
		}

		else { // between and end
			temp = curr.getData();
			prev.setNext(curr.getNext());
		}
		return temp;
	}

	public T deleteSorted(T data) {
		T temp = null;
		Node<T> prev = null, curr = head;

		while (curr != null && curr.getData().compareTo(data) < 0) {
			prev = curr;
			curr = curr.getNext();
		}

		if (curr == null) // case 1 : empty list
			return temp;
		else if (prev == null) {// first node
			temp = head.getData();
			head = head.getNext();
		}

		else { // between and end
			temp = curr.getData();
			prev.setNext(curr.getNext());
		}
		return temp;

	}

	public T deleteRecSorted(T data) {
		return deleteRecSorted(data, null, head);
	}

	private T deleteRecSorted(T data, Node<T> prev, Node<T> curr) {
		if (curr == null) // empty or not found
			return null;
		if (curr.getData().compareTo(data) == 0) { // found
			if (prev == null)// the first item to be deleted
				head = curr.getNext();
			else
				prev.setNext(curr.getNext());
			return curr.getData();
		}
		if (curr.getData().compareTo(data) < 0) {
			return deleteRecSorted(data, curr, curr.getNext());

		}
		return null;
	}

	public T deleteRecUnsorted(T data) {
		return deleteRecSorted(data, null, head);
	}

	private T deleteRecUnsorted(T data, Node<T> prev, Node<T> curr) {
		if (curr == null) // empty or not found
			return null;
		if (curr.getData().compareTo(data) == 0) { // found
			if (prev == null)// the first item to be deleted
				head = curr.getNext();
			else
				prev.setNext(curr.getNext());
			return curr.getData();
		}
		return deleteRecSorted(data, curr, curr.getNext());

	}

	@Override
	public String toString() {

		String res = "Head-->";
		Node<T> curr = head;
		while (curr != null) {
			res += " " + curr + "-->";
			curr = curr.getNext();
		}
		return res + "NULL";
	}
}
