import java.text.ParseException;
import java.time.format.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Scanner;

public class Electricity implements Comparable<Electricity> {
	private LocalDate date;
	private double israeli_lines;
	private double gaza_power_plant;
	private double egyptian_lines;
	private double total_daily_supply;
	private double overall_demand;
	private double power_cuts;
	private double temp;

	Electricity(LocalDate date, double israeli_lines, double gaza_power_plant, double egyptian_lines,
			double total_daily_supply, double overall_demand, double power_cuts, double temp) {
		this.date = date;
		this.israeli_lines = israeli_lines;
		this.gaza_power_plant = gaza_power_plant;
		this.egyptian_lines = egyptian_lines;
		this.total_daily_supply = total_daily_supply;
		this.overall_demand = overall_demand;
		this.power_cuts = power_cuts;
		this.temp = temp;
	}

	public Electricity(String line) {
		Scanner in = new Scanner(line);
		in.useDelimiter(",");
		String dateString = in.next();
		if (!dateString.equals("Date")) {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			try {
				date = LocalDate.parse(dateString, format);
			} catch (Exception e) {
				System.out.println("date parsing");
			}

			try {
				israeli_lines = Double.parseDouble(in.next());
				gaza_power_plant = Double.parseDouble(in.next());
				egyptian_lines = Double.parseDouble(in.next());
				total_daily_supply = Double.parseDouble(in.next());
				overall_demand = Double.parseDouble(in.next());
				power_cuts = Double.parseDouble(in.next());
				temp = Double.parseDouble(in.next());

			} catch (NumberFormatException e) {
				System.out.println(" number format 1");
			}
			String[] elecLine = line.trim().split(",");

			// Check if there are enough elements in the array
			if (elecLine.length >= 8) {

				String dateString1 = elecLine[0].trim();
				// String[] dateLine = dateString.trim().split("/");

				if (!dateString1.equals("Date")) {
					DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					try {
						date = LocalDate.parse(dateString1, format1);
						// System.out.println("Parsed Date: " + date);
					} catch (Exception e) {
						System.out.println("date parsing 2 ");
					}
					String[] dateline = dateString1.split("-");
					int year = Integer.parseInt(dateline[0]);
					int month = Integer.parseInt(dateline[1]);
					int day = Integer.parseInt(dateline[2]);
					year = date.getYear();
					month = date.getMonthValue();
					day = date.getDayOfMonth();
					try {
						israeli_lines = Double.parseDouble(elecLine[1].trim());
						gaza_power_plant = Double.parseDouble(elecLine[2].trim());
						egyptian_lines = Double.parseDouble(elecLine[3].trim());
						total_daily_supply = Double.parseDouble(elecLine[4].trim());
						overall_demand = Double.parseDouble(elecLine[5].trim());
						power_cuts = Double.parseDouble(elecLine[6].trim());
						temp = Double.parseDouble(elecLine[7].trim());
						// System.out.println(date);
					} catch (NumberFormatException e) {
						System.out.println("number format 2 ");
					}
				}
			}
		}
	}

	public Electricity() {
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getIsraeli_lines() {
		return israeli_lines;
	}

	public void setIsraeli_lines(double israeli_lines) {
		this.israeli_lines = israeli_lines;
	}

	public double getGaza_power_plant() {
		return gaza_power_plant;
	}

	public void setGaza_power_plant(double gaza_power_plant) {
		this.gaza_power_plant = gaza_power_plant;
	}

	public double getEgyptian_lines() {
		return egyptian_lines;
	}

	public void setEgyptian_lines(double egyptian_lines) {
		this.egyptian_lines = egyptian_lines;
	}

	public double getTotal_daily_supply() {
		return total_daily_supply;
	}

	public void setTotal_daily_supply(double total_daily_supply) {
		this.total_daily_supply = total_daily_supply;
	}

	public double getOverall_demand() {
		return overall_demand;
	}

	public void setOverall_demand(double overall_demand) {
		this.overall_demand = overall_demand;
	}

	public double getPower_cuts() {
		return power_cuts;
	}

	public void setPower_cuts(double power_cuts) {
		this.power_cuts = power_cuts;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public Statistics getStatistics() {
		Statistics statistics = new Statistics();
		double israeli = getIsraeli_lines();
		double gaza = getGaza_power_plant();
		double egypt = getEgyptian_lines();
		double total = getTotal_daily_supply();
		double demand = getOverall_demand();
		double cuts = getPower_cuts();
		double temp = getTemp();
		statistics.update(israeli);
		statistics.update(gaza);
		statistics.update(egypt);
		statistics.update(total);
		statistics.update(demand);
		statistics.update(cuts);
		statistics.update(temp);
		return statistics;
	}

	public Statistics getIsraelStat() {
		Statistics statistics = new Statistics();
		double israeli = getIsraeli_lines();
		statistics.update(israeli);
		return statistics;
	}

	public Statistics getGazaStat() {
		Statistics statistics = new Statistics();
		double gaza = getGaza_power_plant();
		statistics.update(gaza);
		return statistics;
	}

	public Statistics getEgyptStat() {
		Statistics statistics = new Statistics();
		double egypt = getEgyptian_lines();
		statistics.update(egypt);
		return statistics;
	}

	public Statistics getTotalStat() {
		Statistics statistics = new Statistics();
		double total = getTotal_daily_supply();
		statistics.update(total);
		return statistics;
	}

	public Statistics getDemandStat() {
		Statistics statistics = new Statistics();
		double demand = getOverall_demand();
		statistics.update(demand);
		return statistics;
	}

	public Statistics getCutsStat() {
		Statistics statistics = new Statistics();
		double cuts = getPower_cuts();
		statistics.update(cuts);
		return statistics;

	}

	public Statistics getTempStat() {
		Statistics statistics = new Statistics();
		double temp = getTemp();
		statistics.update(temp);
		return statistics;
	}

	@Override
	public String toString() {

		return "Date: " + date + " Israeli_lines =" + israeli_lines + ", Gaza_power_plant =" + gaza_power_plant
				+ ", Egyptian_lines=" + egyptian_lines + ", Total_daily_supply=" + total_daily_supply
				+ ", Overall_demand =" + overall_demand + ", Power_cuts =" + power_cuts + ", Temp =" + temp;
	}

	@Override
	public int compareTo(Electricity o) {
		return date.compareTo(o.getDate());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true; // Same object reference
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false; // Not the same class or null
		}

		Electricity other = (Electricity) obj;
		// Compare relevant fields for equality
		return Objects.equals(date, other.date) && Double.compare(israeli_lines, other.israeli_lines) == 0
				&& Double.compare(gaza_power_plant, other.gaza_power_plant) == 0
				&& Double.compare(egyptian_lines, other.egyptian_lines) == 0
				&& Double.compare(total_daily_supply, other.total_daily_supply) == 0
				&& Double.compare(overall_demand, other.overall_demand) == 0
				&& Double.compare(power_cuts, other.power_cuts) == 0 && Double.compare(temp, other.temp) == 0;
	}

}
