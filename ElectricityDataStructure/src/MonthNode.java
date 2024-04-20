
public class MonthNode {
	private Month element;
	private MonthNode next;

	MonthNode(Month element) {
		this.element = element;
	}

	public Month getElement() {
		return element;
	}

	public void setElement(Month element) {
		this.element = element;
	}

	public MonthNode getNext() {
		return next;
	}

	public void setNext(MonthNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return element.toString();
	}
}
