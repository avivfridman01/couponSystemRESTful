package entities;

public enum Description {
	CUSTOMER_PURCHASE_COUPON("payment for purchase coupon"), COMPANY_CREATE_COUPON("payment for create new coupon"),
	COMPANY_UPDATE_COUPON("payment for update coupon");

	private String description;

	Description(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
