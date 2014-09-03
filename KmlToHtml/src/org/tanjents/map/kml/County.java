package org.tanjents.map.kml;

import java.util.ArrayList;
import java.util.Iterator;

public class County implements Iterable<MapShape> {
	
	BoundingBox bbox = new BoundingBox();
	ArrayList<MapShape> shapes;
	String name;
	
	public int addShape(MapShape s) {
		if (shapes == null)
			shapes = new ArrayList<MapShape>();
		shapes.add(s);
		bbox.extendToContain(s.getBoundingBox());
		return shapes.indexOf(s);
	}

	public MapShape getShape(int ndx) {
		return shapes.get(ndx);
	}
	
	public BoundingBox getBoundingBox() { return bbox; }
		
	@Override
	public Iterator<MapShape> iterator() {
		if (shapes == null) 
			return null;
		else
			return shapes.iterator();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean spansIDT() { return bbox.spansIDT(); }
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("County:" + this.name + ": " + bbox.toString());
		for (MapShape ms : shapes) {
			sb.append("\n"+ " -- -- " + ms.toString());
		}
		
		return sb.toString();
	}
}
