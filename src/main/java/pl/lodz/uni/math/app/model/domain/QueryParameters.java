package pl.lodz.uni.math.app.model.domain;

import java.math.BigDecimal;
import java.sql.Date;

public class QueryParameters {

	private Date from = null;

	private Date to = null;

	private OperationType type = null;

	private BigDecimal amountFrom = null;

	private BigDecimal amountTo = null;

	private Category category = null;

	private Wallet wallet = null;

	public QueryParameters(Date from, Date to, OperationType type, BigDecimal amountFrom, BigDecimal amountTo,
			Category category, Wallet wallet) {
		super();
		this.from = from;
		this.to = to;
		this.type = type;
		this.amountFrom = amountFrom;
		this.amountTo = amountTo;
		this.category = category;
		this.wallet = wallet;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public OperationType getType() {
		return type;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

	public BigDecimal getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(BigDecimal amountFrom) {
		this.amountFrom = amountFrom;
	}

	public BigDecimal getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(BigDecimal amountTo) {
		this.amountTo = amountTo;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

}
