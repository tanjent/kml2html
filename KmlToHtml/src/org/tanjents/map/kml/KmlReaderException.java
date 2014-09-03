package org.tanjents.map.kml;

public class KmlReaderException extends Exception {
	private static final long serialVersionUID = 7051504262237943718L;

	public KmlReaderException(String s) {
		super(s);
	}
	
	public KmlReaderException(Throwable t) {
		super(t);
	}
	
	public KmlReaderException(String s, Throwable t) {
		super(s,t);
	}
}
