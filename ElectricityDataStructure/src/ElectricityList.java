import java.time.LocalDate;

public class ElectricityList {
	private ElectricityNode first;
	private ElectricityNode last;
	private ElectricityNode next;
	private int count;

	ElectricityList() {
		this.last = null;
		this.first = null;
		this.count = 0;
	}

	public Electricity getFirst() {
		if (first != null) {
			return first.getElement();
		} else {
			return null;
		}
	}

	public Electricity getNextt() {
		if (next != null) {
			return next.getElement();
		} else {
			return null;
		}
	}

	public Electricity getNext() {
		if (first != null && first.getNext() != null) {
			return first.getNext().getElement();
		} else {
			return null;
		}
	}

	public int getCount() {
		return count;
	}

	public void insertSorted4(Electricity data) { //O(n)
		ElectricityNode newNode = new ElectricityNode(data);

		if (first == null || first.getElement().compareTo(data) > 0) {
			// case 1: empty list or insert at the head
			newNode.setNext(first);
			first = newNode;
		} else {
			// case 2: insert in the middle or at the end
			ElectricityNode previousNode = null;
			ElectricityNode currentNode = first;

			while (currentNode != null && currentNode.getElement().compareTo(data) <= 0) {
				previousNode = currentNode;
				currentNode = currentNode.getNext();
			}

			newNode.setNext(currentNode);
			previousNode.setNext(newNode);
		}
	}

	public Object findSorted1(Electricity data) {
		ElectricityNode curr = this.first;

		while (curr != null) {
			Electricity currentYear = curr.getElement();
			int comparisonResult = currentYear.compareTo(data);

			if (comparisonResult == 0) {
				// Found a matching element
				return currentYear;
			} else if (comparisonResult > 0) {
				// Current element is greater than the target, stop searching
				break;
			}

			curr = curr.getNext();
		}

		return null;
	}

	public Object findSorted1(int data) { //O(n)
		ElectricityNode curr = this.first;

		while (curr != null) {
			Electricity currentYear = (Electricity) curr.getElement();
			int comparisonResult = currentYear.compareTo(currentYear); // Replace with the appropriate method

			if (comparisonResult == data) {
				// Found a matching element
				return currentYear;
			} else if (comparisonResult > data) {
				// Current element is greater than the target, stop searching
				break;
			}

			curr = curr.getNext();
		}

		return null;
	}

	public Electricity findElectricityByDate(LocalDate targetDate) {
		ElectricityNode current = first;

		while (current != null) {
			Electricity electricity = current.getElement();

			if (electricity.getDate().equals(targetDate)) {
				return electricity; // Found the record
			}

			current = current.getNext();
		}

		return null; // Record not found
	}

	public void print() {
		ElectricityNode current = first;
		while (current != null) {
			System.out.println(current.getElement());
			current = current.getNext();
		}
	}

	public String toString() {
		String res = "Head-->";
		ElectricityNode curr = first;
		while (curr != null) {
			res += " " + curr + "-->";
			curr = curr.getNext();
		}
		return res + "NULL";
	}

}
