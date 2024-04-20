import java.time.Year;
import java.util.Date;

public class Order {
	private String custName;
	private String custMobile;
	private Date orderDate;
	private String orderStatus;
	private String brand, model, color, year, price;

	public Order(String custName, String custMobile, Date orderDate, String brand, String model, String year,
			String color, String price, String orderStatus) {
		this.custName = custName;
		this.custMobile = custMobile;
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.color = color;
		this.price = price;
	}

	public Order(String custName, String custMobile, Date orderDate, String brand, String model, String year,
			String color, String price) {
		this.custName = custName;
		this.custMobile = custMobile;
		this.orderDate = orderDate;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getBrand() {
		return brand;
	}

	public String toString() {

		return "Customer Name: " + custName + " Customer Mobile: " + custMobile + " Brand: " + brand + " Year: " + year
				+ " Color: " + color + " Price: " + price + " Order Date: " + orderDate + " Order Status: "
				+ orderStatus;
	}
}
