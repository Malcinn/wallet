package pl.lodz.uni.math.app.model.domain;

import java.math.BigDecimal;
import java.sql.Date;

public class Operation {

	private int id;
	
	private OperationType type = null;
	
	private Date date = null;
	
	private String description = null;
	
	private BigDecimal amount = null;
	
	private Wallet wallet = null;
	
	private OperationCategory category = null;

	public Operation() {
	}

	public Operation(int id, OperationType type, Date date, String description, BigDecimal amount, Wallet wallet,
			OperationCategory category) {
		this.id = id;
		this.type = type;
		this.date = date;
		this.description = description;
		this.amount = amount;
		this.wallet = wallet;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OperationType getType() {
		return type;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public OperationCategory getCategory() {
		return category;
	}

	public void setCategory(OperationCategory category) {
		this.category = category;
	}
		
}
