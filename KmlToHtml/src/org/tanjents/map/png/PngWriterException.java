package org.tanjents.map.png;

public class PngWriterException extends Exception {
	private static final long serialVersionUID = -5938780671745493989L;

	public PngWriterException(String s) {
		super(s);
	}
	
	public PngWriterException(Throwable t) {
		super(t);
	}
	
	public PngWriterException(String s, Throwable t) {
		super(s,t);
	}
}
