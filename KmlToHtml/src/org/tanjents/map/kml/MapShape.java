package org.tanjents.map.kml;

import java.util.ArrayList;
import java.util.Iterator;

public class MapShape {
	
	private CoordLine exterior = null;
	private ArrayList<CoordLine> holes = null;
	
	public CoordLine getExterior() {
		return exterior;
	}
	
	public Iterator<GpsCoord> getExteriorIterator() {
		return this.exterior.iterator();
	}
	
	public void addToExterior(GpsCoord coord) {
		if (exterior == null)
			exterior = new CoordLine();
		exterior.add(coord);
	}
	
	public int addHole() {
		if (holes == null) {
			holes = new ArrayList<CoordLine>();
		}
		CoordLine cl = new CoordLine();
		holes.add(cl);
		return holes.indexOf(cl);
	}
	
	public void addToHole(GpsCoord coord, int ndx) {
		CoordLine cl = holes.get(ndx);
		cl.add(coord);
	}
	
	public int getHoleCount() {
		if (holes == null)
			return 0;
		else
			return holes.size();
	}
	
	public CoordLine getHole(int ndx) {
		return holes.get(ndx);
	}
	
	public Iterator<GpsCoord> getHoleIterator(int ndx) {
		return holes.get(ndx).iterator();
	}
	
	public BoundingBox getBoundingBox() {
		return exterior.getBoundingBox();
	}
	
	public GpsCoord getMinimum() {
		return exterior.getMinimum();
	}
	
	public GpsCoord getMaximum() {
		return exterior.getMaximum();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Exterior(" + exterior.getSize() + "):" + exterior.toString());
		if (holes != null) {
			for (CoordLine cl : holes) {
				sb.append("\n -- -- -- Hole(" + cl.getSize() + "):" + cl.toString());
			}
		}
		return sb.toString();
	}
}
