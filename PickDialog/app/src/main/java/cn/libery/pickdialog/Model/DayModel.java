package cn.libery.pickdialog.Model;

public class DayModel {
	private String name;

	public DayModel() {
		super();
	}

	public DayModel(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DistrictModel [name=" + name + "]";
	}

}
