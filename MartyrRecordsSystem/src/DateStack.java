import java.util.Date;

public class DateStack implements Comparable<DateStack> {
	private Date date;
	private LinkedStack<Martyr> martyrStack = new LinkedStack<>();

	DateStack(Date date, LinkedStack<Martyr> martyrStack) {
		super();
		this.setDate(date);
		this.setMartyrStack(martyrStack);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public LinkedStack<Martyr> getMartyrStack() {
		return martyrStack;
	}

	public void setMartyrStack(LinkedStack<Martyr> martyrStack) {
		this.martyrStack = martyrStack;
	}

	@Override
	public int compareTo(DateStack o) {

		return date.compareTo(o.getDate());
	}

}
