package org.tanjents.map.png;

import org.tanjents.map.svg.SvgMap;

public class PngConverter {

	String filename;
	SvgMap svg;
	
	public PngConverter(String fname, SvgMap svgmap) throws PngWriterException {
		setFilename(fname);
		setSvg(svgmap);
		
		
		try {
			// do stuff
		} catch(Exception e) {
			throw new PngWriterException("Error writing PNG file", e);
		}
	}
	
	
	
	private void setFilename(String s) {
		this.filename = s;
	}
	
	private void setSvg(SvgMap s) {
		this.svg = s;
	}
}
