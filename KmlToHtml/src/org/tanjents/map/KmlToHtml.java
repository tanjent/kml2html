package org.tanjents.map;

import org.tanjents.map.kml.KmlReader;
import org.tanjents.map.kml.KmlReaderException;
import org.tanjents.map.svg.SvgMap;
import org.tanjents.map.svg.SvgMapException;
import org.tanjents.map.png.PngConverter;
import org.tanjents.map.png.PngWriterException;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class KmlToHtml {
	
	private static final String IN_KMLNAME = "data/2010gzcounty_51_VA_cleaned.kml";
	private static final String OUT_SVGNAME = "data/virginia.svg";
	private static final String OUT_PNGNAME = "data/virginia.png";
	
	public static void main(String[] args) {

		KmlReader r;
		SvgMap sm;
		PngConverter p;
		
		try {
			r = new KmlReader(IN_KMLNAME);
			
			System.out.println(r.getState().getStateName());
			
			sm = new SvgMap();
			sm.convert(r.getState());
			sm.write(OUT_SVGNAME);
			
			p = new PngConverter(OUT_PNGNAME, sm);
			
		} catch (KmlReaderException e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
		} catch (SvgMapException e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
		} catch (PngWriterException e) {
			System.out.println(ExceptionUtils.getStackTrace(e));
		}

	}

}
