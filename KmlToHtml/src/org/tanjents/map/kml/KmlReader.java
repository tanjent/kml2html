package org.tanjents.map.kml;

import java.io.File;
import java.util.List;

import org.tanjents.map.kml.County;
import org.tanjents.map.kml.GpsCoord;
import org.tanjents.map.kml.MapShape;
import org.tanjents.map.kml.State;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

// Reads a TIGER data KML file of all counties in a state, parses & converts it to an internal storage format.
public class KmlReader {

	private Kml kmlObj = null; 
	
	private String stateName;
	private State state;
	
	public KmlReader(String filename) throws KmlReaderException {
		
		File f = new File(filename);
		if (!f.exists()) {
			throw new KmlReaderException("Cannot open KML file '" + filename + "'.");
		}
		this.kmlObj = Kml.unmarshal(f);
		parseKml();
	}

	public State getState() {
		return this.state;
	}
	
	private void parseKml() throws KmlReaderException {
		
		this.state = new State();
		
		if (!(kmlObj.getFeature() instanceof Document)) {
			throw new KmlReaderException("Unsupported feature - class " + kmlObj.getFeature().getClass());
		}
		
		Document d = (Document) kmlObj.getFeature();
		state.setStateName(d.getName());
		
		List<Feature> featureList = d.getFeature();
		for (Feature f : featureList) {
			if (f instanceof Placemark) {
				Placemark pm = (Placemark) f;
				String placeName = extractPlaceName(f.getDescription());
		
				County cty = new County();
				cty.setName(placeName);
				
				if (pm.getGeometry() instanceof Polygon) {
					Polygon p = (Polygon) pm.getGeometry();
					MapShape ms = parsePolygon(p);
					cty.addShape(ms);
					
				} else if (pm.getGeometry() instanceof MultiGeometry) {
					MultiGeometry mgeo = (MultiGeometry) pm.getGeometry();
					List<Geometry> geoList = mgeo.getGeometry();
					for (Geometry g : geoList) {
						if (g instanceof Polygon) {
							Polygon p = (Polygon) g;
							MapShape ms = parsePolygon(p);
							cty.addShape(ms);
						}
					}
				} else {
					System.out.println(" ### unexpected element in Placemark: " + pm.getGeometry().getClass());
				}
				state.addCounty(cty);

			} else {
				System.out.println("##### Unexpected feature of class " + f.getClass());
			}
		}
	}
	

	private MapShape parsePolygon(Polygon p) {
		MapShape ms = new MapShape();
		Boundary outer = p.getOuterBoundaryIs();
		for (Coordinate c : outer.getLinearRing().getCoordinates()) {
			GpsCoord gc = new GpsCoord(c);
			ms.addToExterior(gc);
		}


		if (p.getInnerBoundaryIs() != null) {
			List<Boundary> innerList = p.getInnerBoundaryIs();
			for (Boundary b : innerList) {
				int ndx = ms.addHole();
				for (Coordinate c : b.getLinearRing().getCoordinates()) {
					GpsCoord gc = new GpsCoord(c);
					ms.addToHole(gc, ndx);
				}
			}
		}
		return ms;
	}
	
	
	// Pull a county name out of the formatted description used in the TIGER data KML
	private String extractPlaceName(String description) {
		
		String str;
		int ndx1 = description.indexOf("<th>NAME</th>");
		if (ndx1 == -1)
			return null;

		int ndx2 = description.indexOf("<td>", ndx1);
		if (ndx2 == -1)
			return null;
		
		int ndx3 = description.indexOf("</td>", ndx2);
		if (ndx3 == -1)
			return null;
		
		str = description.substring(ndx2+4, ndx3);
		return str;
	}
	

	
	public String getStateName() { return this.stateName; } 
}
