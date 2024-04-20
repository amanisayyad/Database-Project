
public class CLinkedList<T extends Comparable<T>> {
	private DNode<T> head;
	private DNode<T> last;
	private int count;

	public CLinkedList() {
		DNode<T> dummy = new DNode(null);
		head = dummy;
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

//	public void insertSorted(T data) {
//		DNode<T> newNode = new DNode<>(data);
//		DNode<T> current = head;
//
//		while (current.getNext() != null && current.getNext().getData().compareTo(data) < 0)
//			current = current.getNext();
//
//		if (current.getNext() == null) {
//			newNode.setPrev(current);
//			current.setNext(newNode);
//		} else {
//			newNode.setNext(current.getNext());
//			newNode.setPrev(current);
//			current.getNext().setPrev(newNode);
//			current.setNext(newNode);
//		}
//	}
	public void insertSorted(T data) {// adds element at its right location in the CDLL O(n)

		DNode<T> temp = new DNode<>(data);
		DNode<T> prev = head, curr = head;

		if (count == 0) {// empty list (adds at beginning O(1))
			head = last = temp;
			last.setNext(head);
			head.setPrev(last);
		} else {// list is not empty

			// looping the CDLL until reaching a node with an element that is greater
			for (int i = 1; i <= count && curr.getData().compareTo(data) <= 0; i++) {
				prev = curr;
				curr = curr.getNext();
			}

			// insert at head (loop wasn't entered O(1))
			if (curr == prev && temp.getData().compareTo(curr.getData()) < 0) {
				temp.setNext(head);
				head.setPrev(temp);
				temp.setPrev(last);
				head = temp;
				last.setNext(head);

			} else if (prev.equals(last)) {// insert at end (this element is the largest O(n))
				temp.setNext(head);
				temp.setPrev(prev);
				prev.setNext(temp);
				last = temp;
				head.setPrev(last);
			} else { // insert in between O(n)
				curr.setPrev(temp);
				temp.setNext(curr);
				prev.setNext(temp);
				temp.setPrev(prev);
			}
		}
		count++;
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

	public T findSorted(T data) {
		DNode<T> curr = head;
		while (curr != null && curr.getData().compareTo(data) <= 0) {
			if (curr.getData().equals(data))
				return curr.getData();
			curr = curr.getNext();
		}
		return null;

	}

	private T get(Node<T> sList) {
		DNode<T> curr = head.getNext();
		while (curr != null) {
			if (curr.getData().compareTo((T) sList) == 0) {
				return curr.getData();
			}
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

	public int getLength(DNode<T> head) {
		int length = 0;
		DNode<T> current = head.getNext();

		while (current != null) {
			length++;
			current = current.getNext();
		}

		return length;
	}
}
