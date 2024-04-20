import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class DaysList {
	private DayNode first;
	private DayNode last;
	private int count;

	DaysList() {
		this.last = null;
		this.first = null;
		this.count = 0;
	}

	public Day getFirst() {
		if (first != null) {
			return first.getElement();
		} else {
			return null;
		}
	}

	public Day getLast() {
		if (count == 0) {
			return null;
		} else {
			return last.getElement();
		}
	}

	public Day getNext() {
		if (first != null && first.getNext() != null) {
			return first.getNext().getElement();
		} else {
			return null;
		}
	}

	public int getCount() {
		return count;
	}

	public void addFirst(Day element) {
		DayNode newNode = new DayNode(element);
		newNode.setNext(first);
		first = newNode;
		count++;
		if (last == null)
			last = first;
	}

	public void addLast(Day element) {
		DayNode newNode = new DayNode(element);

		if (last == null)
			last = first = newNode;
		else {
			last.setNext(newNode);
			last = newNode;
		}

		count++;
	}

	public void add(Day element) {
		add(element, getCount());
	}

	public void add(Day element, int index) {
		if (index == 0)
			addFirst(element);
		else if (index >= count)
			addLast(element);
		else {
			DayNode current = first;
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			DayNode temp = current.getNext();
			DayNode newNode = new DayNode(element);
			newNode = current.getNext();
			temp = newNode.getNext();
			count++;
		}
	}

	public void insertSorted4(Day data) {
		DayNode newNode = new DayNode(data);

		if (first == null || first.getElement().compareTo(data) > 0) {
			// case 1: empty list or insert at the head
			newNode.setNext(first);
			first = newNode;
		} else {
			// case 2: insert in the middle or at the end
			DayNode previousNode = null;
			DayNode currentNode = first;

			while (currentNode != null && currentNode.getElement().compareTo(data) <= 0) {
				previousNode = currentNode;
				currentNode = currentNode.getNext();
			}

			newNode.setNext(currentNode);
			previousNode.setNext(newNode);
		}
	}

//	public void insertSorted4(Day data) {
//		DayNode newNode = new DayNode(data);
//
//		if (first == null || first.getElement().compareTo(data) > 0) {
//			// case 1: empty list or insert at the head
//			newNode.setNext(first);
//			first = newNode;
//		} else {
//			// case 2: insert in the middle or at the end
//			DayNode previousNode = null;
//			DayNode currentNode = first;
//
//			while (currentNode != null && currentNode.getElement().compareTo(data) <= 0) {
//				previousNode = currentNode;
//				currentNode = currentNode.getNext();
//			}
//
//			newNode.setNext(currentNode);
//			previousNode.setNext(newNode);
//		}
//	}

	public boolean removeFirst() {
		if (count < 0)
			return false;
		if (count == 0)
			first = last = null;
		else
			// Node temp = first;
			first = first.getNext();
		// temp.setNext(null);
		count--;
		return true;
	}

	public boolean removeLast() {
		if (count <= 0)
			return false; // List is empty, nothing to remove

		if (count == 1) {
			first = last = null;
		} else {
			DayNode current = first;
			while (current.getNext() != last) {
				current = current.getNext();
			}

			last = current;
			last.setNext(null);
		}

		count--;
		return true;
	}

	public boolean remove(int index) {
		if (index == 0) {
			return removeFirst();
		} else if (index == count) {
			return removeLast();
		} else if (index < 0 || index > count) {
			return false; // Index out of bounds
		} else {
			DayNode current = first;
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			DayNode temp = current.getNext();
			current.setNext(temp.getNext());
			temp.setNext(null);
			count--;
			return true;
		}
	}

//	public boolean remove(Electricity element) {
//		if (first == null) {
//			return false; // List is empty, nothing to remove
//		}
//
//		if (element.equals(first1.getElement())) {
//			if (first == last) {
//				first = last = null;
//			} else {
//				DayNode temp = first;
//				first = first.getNext();
//				temp.setNext(null);
//			}
//			count--;
//			return true;
//		}
//
//		ElectricityNode previous = first1;
//		ElectricityNode current = first1.getNext();
//
//		while (current != null) {
//			if (element.equals(current.getElement())) {
//				previous.setNext(current.getNext());
//				current.setNext(null);
//				count--;
//				return true;
//			}
//
//			previous = current;
//			current = current.getNext();
//		}
//
//		return false; // Element not found in the list
//	}

	public boolean remove(Day element) {
		if (first == null) {
			return false; // List is empty, nothing to remove
		}

		if (element.equals(first.getElement())) {
			if (first == last) {
				first = last = null;
			} else {
				DayNode temp = first;
				first = first.getNext();
				temp.setNext(null);
			}
			count--;
			return true;
		}

		DayNode previous = first;
		DayNode current = first.getNext();

		while (current != null) {
			if (element.equals(current.getElement())) {
				previous.setNext(current.getNext());
				current.setNext(null);
				count--;
				return true;
			}

			previous = current;
			current = current.getNext();
		}

		return false; // Element not found in the list
	}

	public boolean remove(Electricity element) {
		if (first == null) {
			return false; // List is empty, nothing to remove
		}

		if (element.equals(first.getElement())) {
			if (first == last) {
				first = last = null;
			} else {
				DayNode temp = first;
				first = first.getNext();
				temp.setNext(null);
			}
			count--;
			return true;
		}

		DayNode previous = first;
		DayNode current = first.getNext();

		while (current != null) {
			if (element.equals(current.getElement())) {
				previous.setNext(current.getNext());
				current.setNext(null);
				count--;
				return true;
			}

			previous = current;
			current = current.getNext();
		}

		return false;

	}

	public Object findSorted(Day data) {
		DayNode curr = this.first;
		while (curr != null && curr.getElement().compareTo((Day) data) <= 0) {
			if (curr.getElement().equals(data))
				return curr.getElement();
			curr = curr.getNext();
		}
		return null;

	}

//	public Object findSorted(Electricity data) {
//		ElectricityNode curr = this.first;
//		while (curr != null && ((Electricity) curr.getElement()).compareTo((Electricity) data) <= 0) {
//			if (curr.getElement().equals(data))
//				return curr.getElement();
//			curr = curr.getNext();
//		}
//		return null;
//
//	}

//	public Object findSorted1(Electricity data) {
//		ElectricityNode curr = this.first1;
//
//		while (curr != null) {
//			Electricity currentYear = (Electricity) curr.getElement();
//			int comparisonResult = currentYear.compareTo(data);
//
//			if (comparisonResult == 0) {
//				// Found a matching element
//				return currentYear;
//			} else if (comparisonResult > 0) {
//				// Current element is greater than the target, stop searching
//				break;
//			}
//
//			curr = curr.getNext();
//		}
//
//		return null;
//	}

	public Day findDayByValue(int dayValue) {
		DayNode current = first;

		while (current != null) {
			Day day = current.getElement();
			if (day.getDay() == dayValue) {
				return day;
			}

			current = current.getNext();
		}
		// Year not found in the list
		return null;
	}

	public Object findSorted1(Day data) {
		DayNode curr = this.first;

		while (curr != null) {
			Day currentYear = curr.getElement();
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

//	public Object get(Electricity data) {
//		ElectricityNode curr = first1.getNext();
//		while (curr != null) {
//			if (curr.getElement().compareTo(data) == 0) {
//				return curr.getElement();
//			}
//			curr = curr.getNext();
//		}
//		return null;
//	}

	public void print() {
		DayNode current = first;
		while (current != null) {
			System.out.println(current.getElement());
			current = current.getNext();
		}
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Head-->\n");
		DayNode curr = first;

		while (curr != null) {
			res.append(" ").append(curr).append("-->\n");
			curr = curr.getNext();
		}

		return res.append("NULL").toString();
	}

//	public void insertSorted1(Electricity data) {
//		ElectricityNode newNode2 = new ElectricityNode(data);
//		ElectricityNode prev = first1, curr = first1;
//		while (curr != null && curr.getElement().compareTo(data) <= 0) {
//			prev = curr;
//			curr = curr.getNext();
//		}
//		if (first1 == null)// case 1: empty list
//			first1 = newNode2;
//		else if (curr == prev) { // case 2: insert at head
//			newNode2.setNext(first1);
//			first1 = newNode2;
//		} else { // case 3 and 4:insert between or at last
//			newNode2.setNext(curr);
//			prev.setNext(newNode2);
//		}
//
//	}

}
