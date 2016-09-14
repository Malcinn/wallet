package pl.lodz.uni.math.app.controller.util;

import pl.lodz.uni.math.app.model.domain.Operation;

public class OperationTableView extends Operation {

	private String walletName;

	private String categoryName;

	public OperationTableView(Operation operation) {
		super(operation.getId(), operation.getType(), operation.getDate(), operation.getDescription(),
				operation.getAmount(), operation.getCategory(), operation.getWallet());
		this.walletName = this.getWallet().getName();
		this.categoryName = this.getCategory().getName();
	}

	public String getWalletName() {
		return walletName;
	}

	public void setWalletName(String walletName) {
		this.walletName = walletName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
