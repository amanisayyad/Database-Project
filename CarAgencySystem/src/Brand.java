
public class Brand implements Comparable<Brand> {
	private String brand;
	private SLinkedList<Car> sList = new SLinkedList<>();

	public Brand(String brand) {
		this.brand = brand;

	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public SLinkedList<Car> getsList() {
		return sList;
	}

	public void setsList(SLinkedList<Car> sList) {
		this.sList = sList;
	}

	public String toString() {

		return "  " + this.brand + " " + "  " + getsList();

	}

	@Override
	public int compareTo(Brand o) {
		return brand.compareTo(o.getBrand());
	}

}
