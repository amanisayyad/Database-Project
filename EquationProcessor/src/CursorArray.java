
public class CursorArray {
	CursorNode[] ca;

	public CursorArray(int size) {
		ca = new CursorNode[size];
		initialization();
	}

	public CursorNode[] getCursorArray() {
		return ca;
	}

	public int initialization() {
		for (int i = 0; i < ca.length - 1; i++)
			ca[i] = new CursorNode(null, i + 1);
		ca[ca.length - 1] = new CursorNode(null, 0);
		return 0;
	}

	public int malloc() {
		int p = ca[0].next;
		ca[0].next = ca[p].next;
		ca[p].next = 0;
		return p;
	}

	public void free(int p) {
		ca[p] = new CursorNode(null, ca[0].next);
		ca[0].next = p;
	}

	public boolean isNull(int l) {
		return ca[l] == null;
	}

	public boolean isEmpty(int l) {
		return ca[l].next == 0;
	}

	public boolean isLast(int p) {
		return ca[p].next == 0;
	}

	public boolean hasFree() {
		return ca[0].next != 0;
	}

	public int createList() {
		int l = malloc();
		if (l == 0)
			System.out.println("Error: Out of space!!!");
		else
			ca[l] = new CursorNode("-", 0);
		return l;
	}

	public Object getNext(int position) {
		if (!isNull(position) && !isLast(position)) {
			int nextPosition = ca[position].next;
			if (nextPosition != 0) {
				return ca[nextPosition].data;
			}
		}
		return null;
	}

	public void insertAtHead(Object data, int l) {
		if (isNull(l)) // list not created
			return;
		int p = malloc();
		if (p != 0) {
			int nextPointer = ca[l].next;
			ca[p].setData(data);
			ca[p].setNext(nextPointer);
			ca[l].setNext(p);
		} else {
			System.out.println("Error: Out of space!!!");
		}
	}

	public void print(int l) {
		System.out.print("list_" + l + "-->");
		while (!isNull(l) && !isEmpty(l)) {
			l = ca[l].next;
			System.out.print(ca[l] + "-->");
		}
		System.out.println("null");
	}

	public int get(Object data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			l = ca[l].next;
			if (ca[l].data.equals(data))
				return l;
		}
		return -1; // not found
	}

	public int find(Object data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			l = ca[l].next;
			if (ca[l].data.equals(data))
				return l;
		}
		return -1; // not found
	}

	public int findPrevious(Object data, int l) {
		int prev = 0;
		while (!isNull(l) && !isEmpty(l)) {
			if (ca[l].data.equals(data)) {
				return prev;
			}
			prev = l;
			l = ca[l].next;
		}
		return -1; // not found
	}

	public CursorNode delete(Object data, int l) {
		int p = findPrevious(data, l);
		if (p != -1) {
			int c = ca[p].next;
			CursorNode temp = ca[c];
			ca[p].next = temp.next;
			free(c);
		}
		return null;
	}

	public void insertAtLast(Object data, int l) {
		int p = malloc();
		if (p != 0) {
			while (ca[l].next != 0) {
				l = ca[l].next;
			}
			ca[l].next = p;
			ca[p] = new CursorNode(data, 0);

		}
	}

	public Object deleteAtlast(int l) {
		int p = 1;
		while (!isNull(l) && !isEmpty(l)) {
			p = l;
			l = ca[1].next;
		}
		if (l == p) // empty list
			return null;
		Object temp = ca[l].data;
		ca[p].next = 0;
		free(l);
		return temp;

	}

	public void length(int l) {
		int counter = 0;
		while (!isNull(l) && !isLast(l)) {
			l = ca[l].next;
			counter++;
		}
		System.out.println(counter);

	}

	public int lengthCount(int l) {
		int counter = 0;
		while (!isNull(l) && !isLast(l)) {
			l = ca[l].next;
			counter++;
		}
		return counter;

	}

	public int lengthRec(int l, int counter) {

		if (isNull(l))
			return 0;
		if (isLast(l))
			return counter;
		else {

			return lengthRec(ca[l].next, ++counter);
		}

	}

	public Object getHead(int l) {
		Object temp = null;
		if (!isEmpty(l)) {
			int c = ca[l].next;
			temp = ca[c].data;
		}
		return temp;
	}

	public Object deleteAtHead(int l) {
		Object temp = null;
		if (!isEmpty(l)) {
			int c = ca[l].next;
			temp = ca[c].data;
			ca[l].next = ca[c].next;
			free(c);
		}
		return temp;
	}

	public void removeList(int l) {
		while (!isEmpty(l))
			deleteAtHead(l);
		free(l);
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < ca.length; i++) {
			s += i + ":" + ca[i] + "\n";
		}

		return s;
	}

}
