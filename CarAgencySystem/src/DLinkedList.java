import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DLinkedList<T extends Comparable<T>> {
	private DNode<T> head;

	public DLinkedList() {
		DNode<T> dummy = new DNode(null);
		head = dummy;
	}

	public DNode<T> getHead() {
		return head.getNext();
	}

	public void insertAtHead(T data) {
		DNode<T> newNode = new DNode<T>(data);
		if (head.getNext() == null)
			head = newNode;
		else {
			newNode.setNext(head);
			head.setPrev(newNode);
			head = newNode;
		}

	}

	public void insertAtLast(T data) {
		DNode<T> newNode = new DNode<T>(data);
		DNode<T> curr = head.getNext();
		while (curr.getNext() != null) {
			curr = curr.getNext();
			curr = newNode.getPrev();
			newNode = curr.getNext();
		}

	}

	public void insertSorted(T data) {
		DNode<T> newNode = new DNode<>(data);
		DNode<T> current = head;

		while (current.getNext() != null && current.getNext().getData().compareTo(data) < 0)
			current = current.getNext();

		if (current.getNext() == null) {
			newNode.setPrev(current);
			current.setNext(newNode);
		} else {
			newNode.setNext(current.getNext());
			newNode.setPrev(current);
			current.getNext().setPrev(newNode);
			current.setNext(newNode);
		}
	}

	public T deleteSorted(T data) {

		DNode<T> curr = head.getNext();
		while (curr.getNext() != null && curr.getNext().getData().compareTo(data) <= 0) {
			curr = curr.getNext();

			if (curr != null && curr.getData().equals(data)) { // found

				T temp = curr.getData();
				if (curr.getNext() == null) // last item

					curr.getPrev().setNext(null);
				else {
					curr.getPrev().setNext(curr.getNext());
					curr.getNext().setPrev(curr.getPrev());
				}

			}
			return null;
		}
		return data;

	}

	public void traverase() {
		System.out.print("Head -> ");
		DNode<T> current = head.getNext();
		while (current != null) {
			System.out.println(current + " -> ");
			current = current.getNext();
		}
		System.out.println("Null");
	}

//	public String print() {
//		
//		DNode<T> current = head.getNext();
//		while (current != null) {
//			return current + " ";
//		}
//		current = current.getNext();
//		return null;
//	}
	public String print() {
	    StringBuilder sb = new StringBuilder();
	    DNode<T> current = head.getNext();
	    while (current != null) {
	        sb.append(current).append(" ");
	        current = current.getNext();
	    }
	    return sb.toString();
	}

	public T get(T data) {
		DNode<T> curr = head.getNext();
		while (curr != null) {
			if (curr.getData().compareTo(data) == 0) {
				return curr.getData();
			}
			curr = curr.getNext();
		}
		return null;
	}

//	public T gett(T data) {
//		DNode<T> curr = head.getNext();
//		while (curr != null) {
//			if (curr.getData().equals(data)) {
//				return curr.getData();
//			}
//			curr = curr.getNext();
//		}
//		return null;
//	}
//
//	public T gett(Order data) {
//		DNode<T> curr = head.getNext();
//		while (curr != null) {
//			if (curr.getData().equals(data)) {
//				return curr.getData();
//			}
//			curr = curr.getNext();
//		}
//		return null;
//	}

	public T findSorted(T data) {
		DNode<T> curr = head;
		while (curr != null && curr.getData().compareTo(data) <= 0) {
			if (curr.getData().equals(data))
				return curr.getData();
			curr = curr.getNext();
		}
		return null;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DNode<T> current = head.getNext();
		while (current != null) {
			sb.append("Location: ").append(current.toString()).append("\n");
			sb.append(current.getData().toString()).append("\n");
			current = current.getNext();
		}
		return sb.toString();
	}
}
