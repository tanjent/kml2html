package org.tanjents.map.svg;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.dom.util.DOMUtilities;
import org.tanjents.map.kml.BoundingBox;
import org.tanjents.map.kml.County;
import org.tanjents.map.kml.GpsCoord;
import org.tanjents.map.kml.MapShape;
import org.tanjents.map.kml.State;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;


public class SvgMap {

    private static final double COORD_FACTOR = 100000.0;
	private static final long MAP_WIDTH = 710;
	private static final long STROKE_WIDTH = ((long)(COORD_FACTOR * 3) / 10000L); 
	
	SVGDocument doc;
	
	public void convert(State state) throws SvgMapException {
	
		try {
			DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
			String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
			doc = (SVGDocument) impl.createDocument(svgNS, "svg", null);
			
			Element svgRoot = doc.getDocumentElement();
			
			svgRoot.setAttribute("id", state.getStateName());
	
			BoundingBox bbox = state.getBoundingBox();
			double width = Math.abs(bbox.getWidth())*COORD_FACTOR;
			double height = Math.abs(bbox.getHeight()) *COORD_FACTOR;
			long widthT = Math.round(width);
			long heightT = Math.round(height);
			StringBuilder sb = new StringBuilder();
			
			sb.append("0 0 " + widthT + " " + heightT);
			svgRoot.setAttribute("viewBox", sb.toString());
			svgRoot.setAttribute("width", String.valueOf(MAP_WIDTH));
			long scaledHeight = Math.round((height/width)*MAP_WIDTH);
			svgRoot.setAttribute("height", String.valueOf(scaledHeight));
			
			
			Element g = doc.createElementNS(svgNS, "g");
			g.setAttribute("id", "countyOutlines");
			g.setAttribute("style", "display:inline");
			g.setAttribute("transform",  "translate(" + writeInvertedCoord(bbox.getMinimum()) + ")");
			svgRoot.appendChild(g);
			
			Iterator<County> countyIter = state.iterator();
			while (countyIter.hasNext()) {
				County c = countyIter.next();
				
				Element path = doc.createElementNS(svgNS,  "path");
				path.setAttribute("style", "opacity:1;fill:#f2f2f2;fill-opacity:1;stroke:#000000;stroke-width:"
											+ STROKE_WIDTH + ";stroke-opacity:1");
				path.setAttribute("id", c.getName());
				StringBuilder pathSB = new StringBuilder();
				Iterator<MapShape> lineIter = c.iterator();
				while (lineIter.hasNext()) {
					pathSB.append("M ");
					MapShape ms = lineIter.next();
					
					Iterator<GpsCoord> coordIter = ms.getExteriorIterator();
					while (coordIter.hasNext()) {
						GpsCoord gc = coordIter.next();
						pathSB.append(writeCoord(gc));
						if (coordIter.hasNext())
							pathSB.append("L ");
						else
							pathSB.append("z ");
					}
					for (int i=0; i < ms.getHoleCount(); i++) {
						Iterator<GpsCoord> holeIter = ms.getHoleIterator(i);
						pathSB.append(" M ");
						while (holeIter.hasNext()) {
							GpsCoord gc = holeIter.next();
							pathSB.append(writeCoord(gc));
							if (holeIter.hasNext())
								pathSB.append("L ");
							else
								pathSB.append("z");
						}
					}
				}
				path.setAttribute("d", pathSB.toString());
				g.appendChild(path);
				
			}
		} catch(Exception e) {
			throw new SvgMapException(e);
		}
	}

	
	
	public void write(String fname) throws SvgMapException {
		try {
			FileWriter outf = new FileWriter(fname);
			DOMUtilities.writeDocument(doc, outf);
			outf.close();
			System.out.println("SVG generation completed.");
		} catch (IOException e) {
			throw new SvgMapException(e);
		}
	}
	
	
	
	private String writeCoord(GpsCoord c) {
	   return "" + Math.round(c.getLongitude()*COORD_FACTOR) + "," + Math.round(c.getLatitude()*-1*COORD_FACTOR) + " ";
	}
	
	
	private String writeInvertedCoord(GpsCoord c) { 
		return "" + Math.round(-1*c.getLongitude()*COORD_FACTOR) + "," + Math.round(c.getLatitude()*COORD_FACTOR);
	}	
	
}
