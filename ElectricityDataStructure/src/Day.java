
public class Day implements Comparable<Day> {
	Integer day;
	Electricity electric;
	ElectricityList electricity;

	Day(Integer day, Electricity electric, ElectricityList electricity) {
		this.day = day;
		this.electricity = electricity;
		this.electric = electric;
	}

	Day(Integer day, Electricity electric) {
		this.day = day;
		this.electric = electric;
	}

	Day(Integer day, ElectricityList electricity) {
		this.day = day;
		this.electricity = electricity;
	}

	public Electricity getElectric() {
		return electric;
	}

	public void setElectric(Electricity electric) {
		this.electric = electric;
	}

	public Day(Integer day) {
		this.day = day;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public ElectricityList getElectricity() {
		return electricity;
	}

	public void setElectricity(ElectricityList electricity) {
		this.electricity = electricity;
	}

	@Override
	public String toString() {
		return "Day:  " + day + " electricity: " + electric;

	}

	@Override
//	public int compareTo(Day o) {
//		return this.day.compareTo(o.getDay());
//
//	}

	public int compareTo(Day other) {
		// Compare based on the year attribute
		return Integer.compare(this.day, other.getDay());
	}

	public int compareTo(Electricity data) {
		return data.compareTo(data);
	}

}
