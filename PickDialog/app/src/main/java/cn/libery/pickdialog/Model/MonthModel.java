package cn.libery.pickdialog.Model;

import java.util.List;

public class MonthModel {
	private String name;
	private List<DayModel> districtList;
	
	public MonthModel() {
		super();
	}

	public MonthModel(String name, List<DayModel> districtList) {
		super();
		this.name = name;
		this.districtList = districtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DayModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DayModel> districtList) {
		this.districtList = districtList;
	}

	@Override
	public String toString() {
		return "CityModel [name=" + name + ", districtList=" + districtList
				+ "]";
	}
	
}
