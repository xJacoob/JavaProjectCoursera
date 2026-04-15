package module6;

import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
        int traffic = (int)getProperty("traffic");
        if (traffic < 15) {
            pg.fill(0, 255, 0);
            pg.ellipse(x, y, 5, 5);
        }
        else if (traffic >= 15 && traffic <= 100) {
            pg.fill(255, 255, 0);
            pg.ellipse(x, y, 10, 10);
        }
        else {
            pg.fill(255, 0, 0);
            pg.ellipse(x, y, 15, 15);
        }

	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		// show routes
		String city = (String)getProperty("city");
        String country = (String)getProperty("country");
        String name = (String)getProperty("name");
        String id = (String)getProperty("code");
        int traffic = (int)getProperty("traffic");
        String title = country + ", " + city + ", " + name + ", " + id + ", " + traffic;
        pg.fill(255, 255, 255);
        pg.rect(x, y + 10, title.length() * 6, 20);
        pg.fill(0, 0, 0);
        pg.text(title, x, y + 23);
	}
	
}
