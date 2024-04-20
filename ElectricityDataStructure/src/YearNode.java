
public class YearNode {
	private Year element;
	private YearNode next;

	public YearNode(Year element) {
		this.element = element;
	}

	public Year getElement() {
		return element;
	}

	public void setElement(Year element) {
		this.element = element;
	}

	public YearNode getNext() {
		return next;
	}

	public void setNext(YearNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return element.toString();
	}
}