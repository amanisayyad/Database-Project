
public class Statistics {
	private double sum = 0;
	private double average;
	private double min;
	double max;
	int count;

	public Statistics() {
		this.min = Double.MAX_VALUE;
		this.max = Double.MIN_VALUE;
		this.sum = 0;
		this.count = 0;
	}

	Statistics(double sum, double average, double min, double max) {
		super();
		this.sum = sum;
		this.average = average;
		this.min = min;
		this.max = max;
		this.count = count;
	}

	public void update(double value) {
		min = Math.min(min, value);
		max = Math.max(max, value);
		sum += value;
		count++;
		average = getAverage();
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public double getAverage() {
		if (count == 0) {
			return 0; // To avoid division by zero
		}
		return sum / count;
	}

	public double getSum() {
		return sum;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return " Sum= " + sum + "min= " + min + ", max= " + max + " Average: " + getAverage();

	}

}
