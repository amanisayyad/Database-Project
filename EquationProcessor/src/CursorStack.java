
public class CursorStack implements StackInterface {
	static CursorArray ca = new CursorArray(500);
	int list;

	public CursorStack() {
		list = ca.createList();
	}

	@Override
	public void push(Object data) {
		if (ca.hasFree())
			ca.insertAtHead(data, list);
		else
			System.out.println("FULL push ");
	}

	@Override
	public Object pop() {
		Object t;
		if (!ca.isEmpty(list)) {
			t = ca.deleteAtHead(list);
		} else {
			System.out.println("FULL pop ");
			return null;
		}
		return t;
	}

	@Override
	public Object peek() {
		if (!isEmpty()) {
			Object t = ca.getHead(list);
			return t;
		} else
			System.out.println("Stack Overflow");
		return null;
	}

	public boolean contains(Object data) {
		return ca.get(data, list) != -1;
	}

	@Override
	public boolean isEmpty() {
		return ca.isEmpty(list);

	}

	@Override
	public void clear() {
		ca.removeList(list);

	}

	public String toString() {
		return ca.toString();
	}

	@Override
	public void print() {
		int current = ca.getCursorArray()[list].getNext();
		System.out.println("Cursor Stack:");
		while (current != 0) {
			System.out.println(ca.getCursorArray()[current].getData());
			current = ca.getCursorArray()[current].getNext();
		}
	}

}
