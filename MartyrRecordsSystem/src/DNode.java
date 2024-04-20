
public class DNode<T extends Comparable<T>> {
	private T data;
	private DNode next, prev;

	DNode(T data) {

		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public DNode getNext() {
		return next;
	}

	public void setNext(DNode next) {
		this.next = next;
	}

	public DNode getPrev() {
		return prev;
	}

	public void setPrev(DNode prev) {
		this.prev = prev;
	}

	@Override
	public String toString() {
		return data.toString();
	}

}
