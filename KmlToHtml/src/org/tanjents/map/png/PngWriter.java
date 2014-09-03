package org.tanjents.map.png;

import org.tanjents.map.svg.SvgWriter;

public class PngWriter {

	String filename;
	SvgWriter svg;
	
	public PngWriter(String fname, SvgWriter svg) throws PngWriterException {
		setFilename(fname);
		setSvg(svg);
		
		
		try {
			// do stuff
		} catch(Exception e) {
			throw new PngWriterException("Error writing PNG file", e);
		}
	}
	
	
	
	private void setFilename(String s) {
		this.filename = s;
	}
	
	private void setSvg(SvgWriter s) {
		this.svg = s;
	}
}
