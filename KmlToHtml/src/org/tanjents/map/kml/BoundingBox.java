package org.tanjents.map.kml;

public class BoundingBox {

	private GpsCoord minimum;
	private GpsCoord maximum;
	private boolean spansIDT = false;
	private boolean nearIDTwest = false;
	private boolean nearIDTeast = false;
	
	final static double EAST_NEAR_IDT = -178.0;
	final static double WEST_NEAR_IDT = 178.0;
	final static double IDT = 180.0;
	
	final static double ALASKA_IDT_MAX = 60.0;
	final static double ALASKA_IDT_MIN = 45.0;

	
	public BoundingBox() {
	}
	
	public BoundingBox(BoundingBox copy) {
		setMinimum(copy.getMinimum());
		setMaximum(copy.getMaximum());
		spansIDT = copy.spansIDT();
		nearIDTwest = copy.isNearIDTwest();
		nearIDTeast = copy.isNearIDTeast();
	}
	
	
	// Maintains the bounding box for this coordline, by tracking a minimum and maximum coordinate point
	// Minimum is defined as "most northwestern coordinates," maximum as "most southeastern coordinates".
	//
	// Latitude ranges from 90 (North Pole) through 0 (equator) to -90 (South pole)
	// Longitude ranges from -179.999 (just west Int'l date line) through the Americas to 0 (Greenwich) 
	//          through Africa/Europe/Asia to 179.999 (far east Int'l date line)
	//
	// Note: this isn't nearly as simple as it would seem, due to two complications:
	//    (a) a box that crosses the Intl Date Line (eg, bounding Alaska's Aleutian islands) 
	//    (b) a box that spans a pole (eg, bounding antarctica) -- THIS CODE DOES NOT DEAL WITH THIS CASE
	public void updateBox(GpsCoord newCoord) {
		
		if (newCoord == null) return;
		
		// Check dateline area.
		if (newCoord.longitude < EAST_NEAR_IDT)
			nearIDTeast = true;
		else if (newCoord.longitude > WEST_NEAR_IDT)
			nearIDTwest = true;
	
		if (nearIDTeast && nearIDTwest) 
			spansIDT = true;
		
		if (maximum == null) {
			// we haven't defined a bounding box yet, because this is the first coord added to
			// the coordline.  So, initialize both min and max to this point.
			maximum = new GpsCoord();
			maximum.setLatitude(newCoord.getLatitude());
			maximum.setLongitude(newCoord.getLongitude());
			minimum = new GpsCoord();
			minimum.setLatitude(newCoord.getLatitude());
			minimum.setLongitude(newCoord.getLongitude());

		} else {

			// if new coord is more northernly
			if (newCoord.getLatitude() > minimum.getLatitude()) 
				minimum.setLatitude(newCoord.getLatitude());
		
			// if new coord is more southernly
			if (newCoord.getLatitude() < maximum.getLatitude()) 
				maximum.setLatitude(newCoord.getLatitude());
			
			if (this.spansIDT) { 
			
				// We have coordinates near east and/or west IDT.  Assume that means we have a space that crosses IDT,
				// rather than a worldwide band.
				//
				// If latitude is between 60 and 45, will consider large positive longitude values as westernmost 
				// (this covers Alaska's Aleutian islands), otherwise, we consider large negative longitude values 
				// as easternmost (for Russia, Fiji, Kiribati, or New Zealand).
				if ((newCoord.getLatitude() < ALASKA_IDT_MAX) && (newCoord.getLatitude() > ALASKA_IDT_MIN)) {

					// Alaska - move MIN west across IDT
					double minLong = minimum.getLongitude();
					double newLong = newCoord.getLongitude();
					
					// if new coord is first point across IDT
					//   178  new  IDT  min  -178
					if ((newLong < EAST_NEAR_IDT) && (minLong > WEST_NEAR_IDT)) {
						minimum.setLongitude(newLong);
						this.spansIDT = true;
					}

					// if new coord is more easternly but hasn't crossed IDT yet
					//    178   IDT   new   min  -178
					if ((minLong < WEST_NEAR_IDT) && (newLong < minLong))
						minimum.setLongitude(newLong);
					
					// if new coord is farther across IDT than current min, it's the new westernmost point
					//     new   min   IDT   -178
					if ((newLong > 0) && (newLong < minLong))
						minimum.setLongitude(newLong);
										
				} else {
					// Everywhere else w/ IDT issues, where we want to move the MAX east across the IDT
					double maxLong = maximum.getLongitude();
					double newLong = newCoord.getLongitude();
				
					// if new coord is first point across IDT
					//    178  max  IDT  new  -178
					if ((newLong < EAST_NEAR_IDT) && (maxLong > WEST_NEAR_IDT)) {
						maximum.setLongitude(newLong);
						this.spansIDT = true;
					}
						
					// if new coord is more easternly but we haven't yet crossed IDT 
					//    178  max  new  IDT  -178 
					if ((maxLong > WEST_NEAR_IDT) && (newLong > maxLong))
						maximum.setLongitude(newLong);
				
					
					// if new coord is farther across IDT than current, it's the new easternmost
					//    178   IDT   max   new      0
					if ((maxLong < 0) && (newLong > maxLong))
						maximum.setLongitude(newCoord.getLongitude());
				
				}
				
			} else {
				// Places not near IDT... aka the normal state we'll use 99.9% of the time.
				
				// if new coord is more westernly
				if (newCoord.getLongitude() < minimum.getLongitude())
					minimum.setLongitude(newCoord.getLongitude());
				
				// if new coord is more easternly
				if (newCoord.getLongitude() > maximum.getLongitude())
					maximum.setLongitude(newCoord.getLongitude());
			}
		}
	}
	
	
	
	public GpsCoord getMinimum() { return minimum; }
	public void setMinimum(GpsCoord newmin) { this.minimum = newmin; }
	public GpsCoord getMaximum() { return maximum; }
	public void setMaximum(GpsCoord newmax) { this.maximum = newmax; }
	
	public boolean spansIDT() { return spansIDT; }
	public boolean isNearIDTwest() { return nearIDTwest; }
	public boolean isNearIDTeast() { return nearIDTeast; }

	// Make sure this box is big enough to also contain the passed in box B.  Do this by ensuring our box contains 
	// both B's minimum and maximum points.
	public void extendToContain(BoundingBox b) {		
		updateBox(b.getMaximum());
		updateBox(b.getMinimum());
	}
	
	public double getWidth() {
		double w;
		
		if (spansIDT)
			w = (180 + minimum.getLongitude()) + (180 - maximum.getLongitude());
		else
			w = Math.abs(maximum.getLongitude() - minimum.getLongitude());
			
		return w;
	}
	
	public double getHeight() { return Math.abs(maximum.getLatitude() - minimum.getLatitude()); }
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if ((minimum != null) && (maximum != null))
			sb.append("[BB:" + minimum.toString() + " , " + maximum.toString() + "] ");
		return sb.toString();
	}
}
