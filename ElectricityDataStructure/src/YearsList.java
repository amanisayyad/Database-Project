import java.util.function.Consumer;

public class YearsList {
	private YearNode first;
	private YearNode last;
	private int count;

	YearsList() {
		this.last = null;
		this.first = null;
		this.count = 0;
	}

	public Year getFirst() {
		if (first != null) {
			return first.getElement();
		} else {
			return null;
		}
	}

	public Year getLast() {
		if (count == 0) {
			return null;
		} else {
			return last.getElement();
		}
	}

	public Year getNext() {
		if (first != null && first.getNext() != null) {
			return first.getNext().getElement();
		} else {
			return null;
		}
	}

	public int getCount() {
		return count;
	}

	public void addFirst(Year element) {
		YearNode newNode = new YearNode(element);

		newNode.setNext(first);
		first = newNode;
		count++;

		if (last == null) {
			last = first; // Update last only if it's the first element
		}
	}

	public void addLast(Year element) {
		YearNode newNode = new YearNode(element);

		if (last == null)
			last = first = newNode;
		else
			last.setNext(newNode);
		last = newNode;
		last.setNext(first);
		count++;

		// If you want to maintain a circular linked list, connect the last node to the
		// first node.
		// last.setNext(first);
	}

	public void add(Year element, int index) {
		if (index <= 0)
			addFirst(element);
		else if (index > count)
			addLast(element);
		else {
			YearNode current = first;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();
			YearNode temp = current.getNext();
			YearNode newNode = new YearNode(element);
			newNode.setNext(current.getNext());
			temp.setNext(newNode.getNext());
			count++;

		}
	}

	public void insertSorted4(Year data) {
		YearNode newNode = new YearNode(data);

		if (first == null || first.getElement().compareTo(data) > 0) {
			// case 1: empty list or insert at the head
			newNode.setNext(first);
			first = newNode;
		} else {
			// case 2: insert in the middle or at the end
			YearNode previousNode = null;
			YearNode currentNode = first;

			while (currentNode != null && currentNode.getElement().compareTo(data) <= 0) {
				previousNode = currentNode;
				currentNode = currentNode.getNext();
			}

			newNode.setNext(currentNode);
			previousNode.setNext(newNode);
		}
	}

	public void insertSorted(Year data) { // O(n)
		YearNode newNode = new YearNode(data);

		if (first == null) {
			// case 1: empty list
			newNode.setNext(newNode); // Point the new node to itself
			first = newNode; // Set the head to the new node
		} else if (first.getElement().compareTo(data) > 0) {
			// case 2: insert at the beginning
			YearNode lastNode = findLastNode();
			newNode.setNext(first);
			lastNode.setNext(newNode);
			first = newNode; // Update the head to the new node
		} else {
			// case 3: insert in the middle or at the end
			YearNode previousNode = null;
			YearNode currentNode = first;

			do {
				previousNode = currentNode;
				currentNode = currentNode.getNext();
			} while (currentNode != first && currentNode.getElement().compareTo(data) <= 0);

			newNode.setNext(currentNode);
			previousNode.setNext(newNode);
		}
	}

	// Helper method to find the last node in the circular list
	private YearNode findLastNode() {
		YearNode currentNode = first;

		while (currentNode.getNext() != first) {
			currentNode = currentNode.getNext();
		}

		return currentNode;
	}

	public void add(Year element) {
		add(element, getCount());
	}

	public boolean removeFirst() {
		if (count < 0)
			return false;
		if (count == 0)
			first = last = null;
		else
			first = first.getNext();
		count--;
		return true;
	}

	public boolean removeLast() {
		if (count < 0)
			return false;
		if (count == 0)
			first = last = null;
		else {
			YearNode current = first;
			for (int i = 0; i < count - 1; i++)
				current = current.getNext();
			last = current;
			last.setNext(first);
			// last.getNext() = first;
			// **
		}
		count--;
		return true;
	}

	public boolean remove(int index) {
		if (index == 0)
			return removeFirst();
		else if (index == count)
			return removeLast();
		else if (index < 0 || index > count)
			return false;
		else {
			YearNode current = first;
			for (int i = 0; i < index; i++)
				current = current.getNext();
			YearNode temp = current.getNext();
			current.getNext().setNext(current.getNext().getNext());
			// current.getNext() = current.getNext().getNext();
			current.setNext(temp.getNext());
			// current.getNext() = temp.getNext();
			temp.setNext(null);
			// temp.getNext() = null;
			count--;
			return true;
		}
	}

	public boolean remove(Year element) {
		if (first == null) {
			return false; // List is empty, nothing to remove
		}

		if (element.equals(first.getElement())) {
			if (first == last) {
				first = last = null;
			} else {
				YearNode temp = first;
				first = first.getNext();
				last.setNext(first); // Update the last node's next reference
				temp.setNext(null);
			}
			count--;
			return true;
		}

		YearNode previous = first;
		YearNode current = first.getNext();

		while (current != first) { // Check if we have traversed the entire circular list
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

	public Object findSorted(Year data) {
		YearNode curr = this.first;
		while (curr != null && ((Comparable<Year>) curr.getElement()).compareTo((Year) data) <= 0) {
			if (curr.getElement().equals(data))
				return curr.getElement();
			curr = curr.getNext();
		}
		return null;

	}

	public Object findSorted1(Year data) {
		YearNode curr = this.first;

		while (curr != null) {
			Year currentYear = curr.getElement();
			int comparisonResult = ((Comparable<Year>) currentYear).compareTo(data);

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

	public Year findYearByValue(int yearValue) {
		YearNode current = first;

		while (current != null) {
			Year year = current.getElement();

			// Assuming Year class has a getYear() method
			if (year.getYear() == yearValue) {
				return year;
			}

			current = current.getNext();
		}

		// Year not found in the list
		return null;
	}

	public Electricity getDayRecord(Day day) {
		Year currentYearNode = this.getFirst();
		Electricity record = null;
		while (currentYearNode != null) {
			record = ((Day) currentYearNode.getMonthlist().getFirst().getDays().findSorted1(day)).getElectric();
			// currentYearNode = currentYearNode.getMonthlist().get
		}
		return record;
	}

	public void print() {
		YearNode current = first;
		do {
			System.out.println(current.getElement());
			current = current.getNext();
		} while (current != null && current != first);
	}

	public String toString() {
		StringBuilder res = new StringBuilder("Head-->\n");
		YearNode curr = first;

		while (curr != null) {
			res.append(" ").append(curr).append("-->\n");
			curr = curr.getNext();

			if (curr == first) {
				break; // Stop the loop if we've reached the head again in a circular list
			}
		}

		return res.append("NULL").toString();
	}

}
