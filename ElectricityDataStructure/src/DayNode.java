
public class DayNode {
	private Day element;
	private DayNode next;

	public DayNode(Day element) {
		this.element = element;
	}

	public Day getElement() {
		return element;
	}

	public void setElement(Day element) {
		this.element = element;
	}

	public DayNode getNext() {
		return next;
	}

	public void setNext(DayNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return element.toString();
	}
}
