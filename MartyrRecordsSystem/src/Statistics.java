
public class Statistics extends Main {
	private CLinkedList<Location> dList;
	private SLinkedList<Martyr> sList;

	public CLinkedList<Location> getdList() {
		return dList;
	}

	public void setdList(CLinkedList<Location> dList) {
		this.dList = dList;
	}

	public SLinkedList<Martyr> getsList() {
		return sList;
	}

	public void setsList(SLinkedList<Martyr> sList) {
		this.sList = sList;
	}

	public int getAgeCount() {
		return ageCount;
	}

	public void setAgeCount(int ageCount) {
		this.ageCount = ageCount;
	}

	public int getGenderCount() {
		return genderCount;
	}

	public void setGenderCount(int genderCount) {
		this.genderCount = genderCount;
	}

	public double getAverageAge() {
		return averageAge;
	}

	public void setAverageAge(double averageAge) {
		this.averageAge = averageAge;
	}

	int ageCount = ageCount(sList);
	int genderCount = genderCount(sList);
	double averageAge = calculateAvg(sList);

	private int ageCount(SLinkedList<Martyr> sList) {
		int count = 0;

		Node<Martyr> current=null;
		current = current.getNext();
		while (current != null) {
			Martyr martyr = current.getData();
			count = martyr.getAge();
			count++;
		}
		return count;

	}

	private int genderCount(SLinkedList<Martyr> sList) {
		int mc = 0;
		int fc = 0;
		int oc = 0;

		Node<Martyr> current = null;
		current = current.getNext();
		while (current != null) {

			Martyr martyr = current.getData();

			if (martyr.getGender() == 'm' || martyr.getGender() == 'M') {
				mc++;
			} else if (martyr.getGender() == 'f' || martyr.getGender() == 'F') {
				fc++;
			} else if (martyr.getGender() == 'o' || martyr.getGender() == 'O') {
				oc++;
			}

		}
		current = current.getNext();

		return mc + fc + oc;
	}

	private double calculateAvg(SLinkedList<Martyr> sList) {
		double ageSum = 0;
		int count = 0;

		Node<Martyr> current = null;
		current = current.getNext();
		while (current != null) {
			Martyr martyr = current.getData();
			ageSum += martyr.getAge();
			count++;
			current = current.getNext();
		}
		double avg = ageSum / count;

		return avg;
	}
}
