package pl.lodz.uni.math.app.model.domain;

public class OperationCategory {
	
	private int id;
	
	private String name = null;

	public OperationCategory() {
	}

	public OperationCategory(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
