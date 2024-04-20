
public class SNode<T> {
	private T data;
	private SNode<T> next;

	public SNode(T data) {
		this.data = data;
	}

	public SNode(Order o) {
		// TODO Auto-generated constructor stub
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public SNode<T> getNext() {
		return next;
	}

	public void setNext(SNode<T> topNode) {
		this.next = topNode;
	}

	@Override
	public String toString() {
		return data.toString();
	}
}
