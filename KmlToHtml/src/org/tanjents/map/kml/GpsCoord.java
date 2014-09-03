package org.tanjents.map.kml;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;

public class GpsCoord {

	double longitude;
	double latitude;

	public GpsCoord() {
	}

	public GpsCoord(Coordinate c) {
		this.setLatitude(c.getLatitude());
		this.setLongitude(c.getLongitude());
	}
	
	public GpsCoord(double a, double b) {
		this.setLatitude(a);
		this.setLongitude(b);
	}
	
	public GpsCoord(GpsCoord copy) {
		this.setLatitude(copy.getLatitude());
		this.setLongitude(copy.getLongitude());
	}
	
	public double getLongitude() { return longitude; }
	public void setLongitude(double longitude) { this.longitude = longitude; }
	
	public double getLatitude() { return latitude; }
	public void setLatitude(double latitude) { this.latitude = latitude; }

	
	public boolean equals(GpsCoord other) {
		if ((this.longitude == other.getLongitude()) && (this.latitude == other.getLatitude()))
			return true;
		else
			return false;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(longitude + "," + latitude);
		return sb.toString();
	}
}
