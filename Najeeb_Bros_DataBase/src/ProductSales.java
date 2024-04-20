
public class ProductSales {

	private String productName;
	private int totalQuantitySold;

	public ProductSales(String productName, int totalQuantitySold) {

		this.productName = productName;
		this.totalQuantitySold = totalQuantitySold;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getTotalQuantitySold() {
		return totalQuantitySold;
	}

	public void setTotalQuantitySold(int totalQuantitySold) {
		this.totalQuantitySold = totalQuantitySold;
	}

	@Override
	public String toString() {
		return ", productName='" + productName + '\'' + ", totalQuantitySold=" + totalQuantitySold + '}';
	}

}
