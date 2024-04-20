
public class ProductOrderCount {
	private int productID;
	private String productName;
	private int orderCount;

	public ProductOrderCount(int productID, String productName, int orderCount) {
		this.productID = productID;
		this.productName = productName;
		this.orderCount = orderCount;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
}
