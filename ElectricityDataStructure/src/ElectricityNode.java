
public class ElectricityNode {
	private Electricity element;
	private ElectricityNode next;

	ElectricityNode(Electricity element) {
		this.element = element;
	}

//	public ElectricityNode(ElectricityNode data) {
//		// TODO Auto-generated constructor stub
//	}

	public Electricity getElement() {
		return element;
	}

	public void setElement(Electricity element) {
		this.element = element;
	}

	public ElectricityNode getNext() {
		return next;
	}

	public void setNext(ElectricityNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return element.toString();
	}

	public int compareTo(Electricity data) {
		return this.element.compareTo(data);

	}

}
