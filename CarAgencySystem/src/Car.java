import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Car implements Comparable<Car> {
	private String brand;
	private String model;
	private Integer year;
	private String color;
	private String price;

	public Car(String brand, String model, Integer year, String color, String price) {
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.color = color;
		this.price = price;
	}

	public Car(String line) {
		Scanner in = new Scanner(line);
		in.useDelimiter(",");
		brand = in.next();
		model = in.next();
		try {
			year = Integer.parseInt(in.next());
		} catch (NumberFormatException e) {
			System.out.println(" ");
		}
		color = in.next();
		price = in.next();
		String[] carLine = line.trim().split(",");
		if (carLine.length >= 5) {
			brand = carLine[0].trim();
			model = carLine[1].trim();
			try {
				year = Integer.parseInt(carLine[2].trim());
			} catch (NumberFormatException e) {
				System.out.println(" ");
			}

			color = carLine[3].trim();
			price = carLine[4].trim();
		}
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String toString() {
		return " Model: " + model + " Year: " + year + " Color: " + color + " Price: " + price;
	}

	@Override
	public int compareTo(Car o) {
		if (year != null && o != null) {
			int compare = year.compareTo(o.getYear());
			return compare;
		}
		return 0;
	}

}
