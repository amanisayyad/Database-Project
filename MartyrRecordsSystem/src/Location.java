
public class Location implements Comparable<Location> {
	private String location;
	AVLTree<Martyr> names = new AVLTree<>();
	AVLTree<Martyr> date = new AVLTree<>();

	public Location(String location) {
		this.location = location;

	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public int compareTo(Location o) {

		return location.compareTo(o.getLocation());
	}

	public String toString() {

		return "Location: " + this.location + " " + "Martyr: ";
	}

}
