package org.tanjents.map;

import org.tanjents.map.kml.KmlReader;
import org.tanjents.map.kml.KmlReaderException;
import org.tanjents.map.svg.SvgWriter;
import org.tanjents.map.png.PngWriter;
import org.tanjents.map.png.PngWriterException;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class KmlToHtml {
	
	private static final String IN_KMLNAME = "data/2010gzcounty_51_VA_cleaned.kml";
	private static final String OUT_SVGNAME = "data/virginia.svg";
	private static final String OUT_PNGNAME = "data/virginia.png";
	
	public static void main(String[] args) {

		KmlReader r;
		SvgWriter w;
		PngWriter p;
		
		try {
			r = new KmlReader(IN_KMLNAME);
			
			System.out.println(r.getState().getStateName());
			
			w = new SvgWriter(OUT_SVGNAME, r.getState()); 
			p = new PngWriter(OUT_PNGNAME, w);
			
		} catch (KmlReaderException e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
		} catch (PngWriterException e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

	}

}
