package org.tanjents.map.kml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class State implements Iterable<County>, Serializable {
	
	private static final long serialVersionUID = -7326599142704319856L;
	
	String stateName;
	ArrayList<County> counties;
	BoundingBox bbox = new BoundingBox();
	
	public int addCounty(County c) {
		if (counties == null)
			counties = new ArrayList<County>();
		counties.add(c);
		bbox.extendToContain(c.getBoundingBox());
		return counties.indexOf(c);
	}

	public BoundingBox getBoundingBox() { return this.bbox; }
	
	public boolean spansIDT() { return bbox.spansIDT(); }
	
	public County getCounty(int ndx) {
		return counties.get(ndx);
	}
		
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	@Override
	public Iterator<County> iterator() {
		if (counties == null) 
			return null;
		else
			return counties.iterator();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("State:" + this.stateName + ":" + bbox.toString() + "\n");
		for (County c: this.counties) {
			sb.append(" -- " + c.toString() + "\n");
		}
		return sb.toString();
	}
}
