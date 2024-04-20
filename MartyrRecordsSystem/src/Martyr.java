import java.util.Objects;
import java.util.Scanner;

public class Martyr implements Comparable<Martyr> {
	private String name;
	private int age;
	private String location;
	private String date;
	private char gender;

	public Martyr(String name, int age, String date, char gender) {
		this.name = name;
		this.age = age;
		this.date = date;
		this.gender = gender;
	}

	public Martyr(String line) {
		Scanner in = new Scanner(line);
		in.useDelimiter(",");
		//name = in.next();
		try {
			age = Integer.parseInt(in.next());
		} catch (NumberFormatException e) {
			System.out.println(" ");
			age = 0;
		}
		location = in.next();
		date = in.next();
		try {
			char gender = in.next().charAt(10);
			System.out.println(gender);
		} catch (StringIndexOutOfBoundsException e) {
			System.out.println(" ");
		}
		String[] MartyrLine = line.trim().split(",");
		if (MartyrLine.length >= 5) {
			name = MartyrLine[0].trim();
			try {
				age = Integer.parseInt(MartyrLine[1].trim());
			} catch (NumberFormatException e) {
				System.out.println(" ");
				age = 0;
			}
			location = MartyrLine[2].trim();
			date = MartyrLine[3].trim();
			try {
				gender = MartyrLine[4].trim().charAt(0);
				System.out.println(gender);
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println(" ");
			}
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getLoc() {
		return location;
	}

	public void setLoc(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		if (gender == 'M' || gender == 'm' || gender == 'F' || gender == 'f' || gender == 'O' || gender == 'o')
			this.gender = gender;
		else
			System.out.println("invalid gender");
	}

	@Override
	public String toString() {
		return "Martyr: name=" + name + ", age=" + age + ", date=" + date + ", gender=" + gender;

	}

	@Override
	public int compareTo(Martyr o) {
		return name.compareTo(o.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
