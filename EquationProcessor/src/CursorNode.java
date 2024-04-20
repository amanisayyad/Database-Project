
public class CursorNode {
	Object data;
	int next;

	public CursorNode(Object data, int next) {
		this.data = data;
		this.next = next;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public String toString() {
		return "[" + data + " , " + next + "]";
	}
}
