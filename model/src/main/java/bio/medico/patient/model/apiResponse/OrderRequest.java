package bio.medico.patient.model.apiResponse;

import java.util.List;

public class OrderRequest {


	private List<Ordered_Product> Ordered_Product;
	private String Payment_Method;
	private int Total_Price;
	private String Order_Type;
	private String Longitude;
	private String Latitude;
	private String Road;
	private String Area;
	private String House;
	private String Appartment;
	private String Phone;
	private String Customer_Name;

	public List<Ordered_Product> getOrdered_Product() {
		return Ordered_Product;
	}

	public void setOrdered_Product(List<OrderRequest.Ordered_Product> ordered_Product) {
		Ordered_Product = ordered_Product;
	}

	public void setPayment_Method(String payment_Method) {
		Payment_Method = payment_Method;
	}

	public void setTotal_Price(int total_Price) {
		Total_Price = total_Price;
	}

	public void setOrder_Type(String order_Type) {
		Order_Type = order_Type;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public void setRoad(String road) {
		Road = road;
	}

	public void setArea(String area) {
		Area = area;
	}

	public void setHouse(String house) {
		House = house;
	}

	public void setAppartment(String appartment) {
		Appartment = appartment;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public void setCustomer_Name(String customer_Name) {
		Customer_Name = customer_Name;
	}

	public String getPayment_Method() {
		return Payment_Method;
	}

	public double getTotal_Price() {
		return Total_Price;
	}

	public String getOrder_Type() {
		return Order_Type;
	}

	public String getLongitude() {
		return Longitude;
	}

	public String getLatitude() {
		return Latitude;
	}

	public String getRoad() {
		return Road;
	}

	public String getArea() {
		return Area;
	}

	public String getHouse() {
		return House;
	}

	public String getAppartment() {
		return Appartment;
	}

	public String getPhone() {
		return Phone;
	}

	public String getCustomer_Name() {
		return Customer_Name;
	}

	public static class Ordered_Product {
		private int Quantity;
		private int Product_id;

		public int getQuantity() {
			return Quantity;
		}

		public int getProduct_id() {
			return Product_id;
		}

		public Ordered_Product(int quantity, int product_id) {
			Quantity = quantity;
			Product_id = product_id;
		}
	}

}