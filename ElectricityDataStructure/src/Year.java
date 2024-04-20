
public class Year implements Comparable<Year> {
	Integer year;
	MonthsList monthlist;

	Year(Integer year, MonthsList monthlist) {

		this.year = year;
		this.monthlist = monthlist;
	}

	Year(Integer year) {
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public MonthsList getMonthlist() {
		return monthlist;
	}

	public void setMonthlist(MonthsList monthlist) {
		this.monthlist = monthlist;
	}

	@Override
	public String toString() {
		return "Year : " + year + " Month List: " + monthlist;
	}

	public int compareTo(Year other) {
		// Compare based on the year attribute
		return Integer.compare(this.year, other.getYear());
	}

}
