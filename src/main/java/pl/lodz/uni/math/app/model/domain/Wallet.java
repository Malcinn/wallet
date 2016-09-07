package pl.lodz.uni.math.app.model.domain;

public class Wallet {
	
	private int id;
	
	private String name = null;

	public Wallet() {
	}

	public Wallet(String name) {
		this.name = name;
	}
	
	public Wallet(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
