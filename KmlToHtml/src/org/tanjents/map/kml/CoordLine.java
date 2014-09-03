package org.tanjents.map.kml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CoordLine implements Iterable<GpsCoord> {

	List<GpsCoord> coordList;
	BoundingBox bbox = null;
	GpsCoord minimum = null;
	GpsCoord maximum = null;
	boolean spansIDT = false;

	
	public CoordLine() {
		coordList = new ArrayList<GpsCoord>();
		bbox = new BoundingBox();
	}


	@Override
	public Iterator<GpsCoord> iterator() {
		return coordList.iterator();
	}
	
	
	public void add(GpsCoord newCoord) {
		coordList.add(newCoord);
		bbox.updateBox(newCoord);
	}
	
	public List<GpsCoord> get() {
		return coordList;
	}
	
	public int getSize() {
		if (coordList == null) 
			return -1;
		else
			return coordList.size();
	}

	public BoundingBox getBoundingBox() {
		return this.bbox;
	}
	
	public GpsCoord getMinimum() {
		return this.bbox.getMinimum();
	}
	
	public GpsCoord getMaximum() {
		return this.bbox.getMaximum();
	}
	
	public String toString() {
		
		GpsCoord first = coordList.get(0);
		GpsCoord last = coordList.get(coordList.size()-1);
		
		StringBuilder sb = new StringBuilder();
		if (!first.equals(last))
			sb.append("[UNCLOSED] ");
		sb.append(bbox.toString());
		for (GpsCoord gc : coordList) {
			sb.append(gc.toString() + " ");
		}
		
		return sb.toString();
	}
	
	
	
	
}
