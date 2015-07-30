package cn.libery.pickdialog.Model;

import java.util.List;

public class YearModel {
	private String name;
	private List<MonthModel> cityList;
	
	public YearModel() {
		super();
	}

	public YearModel(String name, List<MonthModel> cityList) {
		super();
		this.name = name;
		this.cityList = cityList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MonthModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<MonthModel> cityList) {
		this.cityList = cityList;
	}

	@Override
	public String toString() {
		return "ProvinceModel [name=" + name + ", cityList=" + cityList + "]";
	}
	
}
