package org.tanjents.map.svg;

import java.util.ArrayList;

public class SvgShape {

	String name;
	ArrayList<SvgPolygon> shapes;
	
	public SvgShape() {
		this.shapes = new ArrayList<SvgPolygon>(); 
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SvgPolygon> getShapes() {
		return shapes;
	}
	public void setShapes(ArrayList<SvgPolygon> shapes) {
		this.shapes = shapes;
	}
	
	
}
