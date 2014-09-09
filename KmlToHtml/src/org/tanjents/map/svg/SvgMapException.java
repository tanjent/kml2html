package org.tanjents.map.svg;

public class SvgMapException extends Exception {
	private static final long serialVersionUID = -6582630315518062064L;

	public SvgMapException(String s) {
		super(s);
	}
	
	public SvgMapException(Throwable t) {
		super(t);
	}
	
	public SvgMapException(String s, Throwable t) {
		super(s,t);
	}
}
