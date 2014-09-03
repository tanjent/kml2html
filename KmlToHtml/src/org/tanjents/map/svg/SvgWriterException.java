package org.tanjents.map.svg;

public class SvgWriterException extends Exception {
	private static final long serialVersionUID = -6582630315518062064L;

	public SvgWriterException(String s) {
		super(s);
	}
	
	public SvgWriterException(Throwable t) {
		super(t);
	}
	
	public SvgWriterException(String s, Throwable t) {
		super(s,t);
	}
}
