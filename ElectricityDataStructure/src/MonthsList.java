
public class MonthsList {
	private MonthNode first;
	private MonthNode last;
	private int count;

	public MonthsList() {
		this.last = null;
		this.first = null;
		this.count = 0;
	}

	public Month getFirst() {
		if (first != null) {
			return first.getElement();
		} else {
			return null;
		}
	}

	public Month getLast() {
		if (count == 0) {
			return null;
		} else {
			return last.getElement();
		}
	}

	public Month getNext() {
		if (first != null && first.getNext() != null) {
			return first.getNext().getElement();
		} else {
			return null;
		}
	}

	public int getCount() {
		return count;
	}

	// Add other methods for inserting, deleting, etc., as needed

	public void addFirst(Month element) {
		MonthNode newNode = new MonthNode(element);
		newNode.setNext(first);
		// first = newNode.getNext();
		first = newNode;
		count++;
		if (last == null)
			last = first;

	}

	public void addLast(Month element) {
		MonthNode newNode = new MonthNode(element);

		if (last == null)
			last = first = newNode;
		else
			last.setNext(newNode);
		// newNode = last.getNext();
		last = newNode;
		last.setNext(first);
		// first = last.getNext();
		// **
		count++;
	}

	public void add(Month element) {
		add(element, getCount());
	}

	public void add(Month element, int index) {
		if (index <= 0)
			addFirst(element);
		else if (index > count)
			addLast(element);
		else {
			MonthNode current = first;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();
			MonthNode temp = current.getNext();
			MonthNode newNode = new MonthNode(element);
			newNode.setNext(current.getNext());
			temp.setNext(newNode.getNext());

//			newNode = current.getNext();
//			temp = newNode.getNext();
			count++;
		}
	}

	public void insertSorted4(Month data) {
		MonthNode newNode = new MonthNode(data);

		if (first == null || first.getElement().compareTo(data) > 0) {
			// case 1: empty list or insert at the head
			newNode.setNext(first);
			first = newNode;
		} else {
			// case 2: insert in the middle or at the end
			MonthNode previousNode = null;
			MonthNode currentNode = first;

			while (currentNode != null && currentNode.getElement().compareTo(data) <= 0) {
				previousNode = currentNode;
				currentNode = currentNode.getNext();
			}

			newNode.setNext(currentNode);
			previousNode.setNext(newNode);
		}
	}
	public void insertSorted(Month data) {
		MonthNode newNode = new MonthNode(data);

	    if (first == null) {
	        // case 1: empty list
	        newNode.setNext(newNode); // Point the new node to itself
	        first = newNode; // Set the head to the new node
	    } else if (first.getElement().compareTo(data) > 0) {
	        // case 2: insert at the beginning
	    	MonthNode lastNode = findLastNode();
	        newNode.setNext(first);
	        lastNode.setNext(newNode);
	        first = newNode; // Update the head to the new node
	    } else {
	        // case 3: insert in the middle or at the end
	    	MonthNode previousNode = null;
	    	MonthNode currentNode = first;

	        do {
	            previousNode = currentNode;
	            currentNode = currentNode.getNext();
	        } while (currentNode != first && currentNode.getElement().compareTo(data) <= 0);

	        newNode.setNext(currentNode);
	        previousNode.setNext(newNode);
	    }
	}

	// Helper method to find the last node in the circular list
	private MonthNode findLastNode() {
		MonthNode currentNode = first;

	    while (currentNode.getNext() != first) {
	        currentNode = currentNode.getNext();
	    }

	    return currentNode;
	}

	public Object findSorted(Month data) {
		MonthNode curr = this.first;
		while (curr != null && curr.getElement().compareTo((Month) data) <= 0) {
			if (curr.getElement().equals(data))
				return curr.getElement();
			curr = curr.getNext();
		}
		return null;

	}

	public Object findSorted1(Month data) {
		MonthNode curr = this.first;

		while (curr != null) {
			Month currentYear = curr.getElement();
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

	public Month findMonthByValue(int monthValue) {
		MonthNode current = first;

		while (current != null) {
			Month month = current.getElement();
			if (month.getMonth() == monthValue) {
				return month;
			}

			current = current.getNext();
		}
		// Year not found in the list
		return null;
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
			MonthNode current = first;
			for (int i = 0; i < count - 1; i++)
				current = current.getNext();
			last = current;
			last.setNext(first);
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
			MonthNode current = first;
			for (int i = 0; i < index; i++)
				current = current.getNext();
			MonthNode temp = current.getNext();
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

	public boolean remove(Month element) {
		if (first == null) {
			return false; // List is empty, nothing to remove
		}

		if (element.equals(first.getElement())) {
			if (first == last) {
				first = last = null;
			} else {
				MonthNode temp = first;
				first = first.getNext();
				last.setNext(first); // Update the last node's next reference
				temp.setNext(null);
			}
			count--;
			return true;
		}

		MonthNode previous = first;
		MonthNode current = first.getNext();

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

	public void print() {
	    MonthNode current = first;
	    do {
	        System.out.println(current.getElement());
	        current = current.getNext();
	    } while (current != null && current != first);
	}

	public String toString() {
	    StringBuilder res = new StringBuilder("Head-->\n");
	    MonthNode curr = first;

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
