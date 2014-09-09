package org.tanjents.map.svg;

import java.util.ArrayList;

public class SvgPolygon {

	private String name;
	private ArrayList<SvgCoord> poly;
	
	
	public SvgPolygon() {
		this.poly = new ArrayList<SvgCoord>();
	}
	
	
	String getName() {
		return this.name;
	}
	void setName(String s) {
		this.name = s;
	}


	public ArrayList<SvgCoord> getPoly() {
		return poly;
	}
	public void setPoly(ArrayList<SvgCoord> poly) {
		this.poly = poly;
	}
	
}
