
public class Month implements Comparable<Month> {
	Integer month;
	DaysList days;

	Month(Integer month, DaysList days) {
		this.month = month;
		this.days = days;
	}

	public Month(Integer month) {
		this.month = month;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public DaysList getDays() {
		return days;
	}

	public void setDays(DaysList days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "Month: " + this.month + " Days:  " + days;
	}

	@Override
//	public int compareTo(Month o) {
//		return this.month.compareTo(o.getMonth());
//	}
	public int compareTo(Month other) {
		// Compare based on the year attribute
		return Integer.compare(this.month, other.getMonth());
	}

}
