package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;
import de.fhpotsdam.unfolding.providers.Microsoft;

/** An applet that shows airports (and routes)
 * on a world map.
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {

	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
    private HashMap<String, ArrayList<Marker>> airportRoutes;
    private CommonMarker lastSelected;
    private CommonMarker lastClicked;

	public void setup() {
		// setting up PAppler
		size(1280,900, OPENGL);

		// setting up map and default events
		map = new UnfoldingMap(this, 250, 50, 1000, 800, new Microsoft.HybridProvider());
		MapUtils.createDefaultEventDispatcher(this, map);

		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");

		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<>();
        List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");

        HashMap<Integer, Integer> airportTraffic = calculateTraffic(routes);
		// create markers from features
		for(PointFeature feature : features) {
            int id = Integer.parseInt(feature.getId());
			int traffic = airportTraffic.getOrDefault(id, 0);

            if (traffic > 0) {
                AirportMarker m = new AirportMarker(feature);
                m.setRadius(5);
                m.setProperty("traffic", traffic);
                m.setProperty("id", id);
                airportList.add(m);
                //System.out.println("Airport: " + feature.getProperty("name") + "->" + "Traffic: " + traffic);
            }

			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());

		}


		// parse route data
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {

			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));

			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}

			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());

			//System.out.println(sl.getProperties());

			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
            sl.setHidden(true);
			routeList.add(sl);
		}

        airportRoutes = new HashMap<>();
        for (Marker airport : airportList) {
            String airportID = String.valueOf(airport.getProperty("id"));
            airportRoutes.put(airportID, new ArrayList<>());
        }

        for (Marker route : routeList) {
            String source = String.valueOf(route.getProperty("source"));
            String destination = String.valueOf(route.getProperty("destination"));

            if (airportRoutes.containsKey(source)) {
                airportRoutes.get(source).add(route);
            }
            if (airportRoutes.containsKey(destination)) {
                airportRoutes.get(destination).add(route);
            }
        }

		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		map.addMarkers(airportList);
	}

    public HashMap<Integer, Integer> calculateTraffic(List<ShapeFeature> routes) {
        HashMap<Integer, Integer> map = new  HashMap<>();
        for (ShapeFeature route : routes) {
            int source = Integer.parseInt((String)route.getProperty("source"));
            map.put(source, map.getOrDefault(source, 0) + 1);
        }
        return map;
    }

    private void sortAirports() {
        int n  = airportList.size();
        for (int i = 0; i < n; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                int currTraffic = (int) airportList.get(j).getProperty("traffic");
                int maxTraffic = (int) airportList.get(maxIndex).getProperty("traffic");
                if (currTraffic > maxTraffic) {
                    maxIndex = j;
                }
            }
            Marker temp =  airportList.get(i);
            airportList.set(i, airportList.get(maxIndex));
            airportList.set(maxIndex, temp);
        }
    }

	@Override
    public void mouseMoved() {
        if (lastSelected != null) {
            lastSelected.setSelected(false);
            lastSelected = null;
        }
        for (Marker m : airportList) {
            if (m.isInside(map, mouseX, mouseY)) {
                m.setSelected(true);
                lastSelected = (AirportMarker)m;
                break;
            }
        }
    }

    @Override
    public void mouseClicked() {
        if (lastClicked != null) {
            unhideMarkers();
            lastClicked = null;
            return;
        }

        for (Marker marker : airportList) {
            if (marker.isInside(map, mouseX, mouseY)) {
                lastClicked = (CommonMarker) marker;

                for (Marker m : airportList) {
                    if (m != lastClicked) {
                        m.setHidden(true);
                    }
                }

                String clickedID = String.valueOf(lastClicked.getProperty("id"));
                ArrayList<Marker> connectedRoutes = airportRoutes.getOrDefault(clickedID, new ArrayList<>());

                for (Marker r : connectedRoutes) {
                    r.setHidden(false);

                    String source = String.valueOf(r.getProperty("source"));
                    String destination = String.valueOf(r.getProperty("destination"));

                    for (Marker airport : airportList) {
                        String airportID = String.valueOf(airport.getProperty("id"));
                        if (airportID.equals(source) || airportID.equals(destination)) {
                            airport.setHidden(false);
                        }
                    }
                }
                break;
            }
        }
    }

    private void unhideMarkers() {
        for (Marker m : airportList) {
            m.setHidden(false);
        }
        for (Marker r : routeList) {
            r.setHidden(true);
        }
    }

	public void draw() {
		background(0);
		map.draw();
        addKey();
	}

    private void addKey() {
        fill(255, 255, 255);
        rect(10, 50, 240, 500);

        fill(0, 0, 0);
        textAlign(CENTER, CENTER);
        textSize(14);
        text("Legend", 140, 70);

        fill(0, 255, 0);
        ellipse(70, 100, 5, 5);
        fill(0, 0 , 0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("Airports: < 15 routes", 80, 98);

        fill(255, 255, 0);
        ellipse(70, 120, 10, 10);
        fill(0, 0 , 0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("Airports: 15-100 routes", 80, 118);

        fill(255, 0, 0);
        ellipse(70, 140, 15, 15);
        fill(0, 0 , 0);
        textAlign(LEFT, CENTER);
        textSize(12);
        text("Airports: > 100 routes", 80, 138);

        fill(0, 0 , 0);
        textAlign(CENTER, CENTER);
        textSize(14);
        text("Top 10 crowded airports", 140, 200);

        sortAirports();
        for (int i = 0; i < 10; i++) {
            Marker m = airportList.get(i);
            String name = (String)m.getProperty("name");
            String city = (String)m.getProperty("city");
            int traffic = (int)m.getProperty("traffic");
            fill(0, 0, 0);
            textSize(9);
            textAlign(CENTER, CENTER);
            text((i + 1) + ". " + name + "(" + city + ") - " + traffic, 140, 220 + i * 15);
        }
    }

	public static void main (String[] args) {
        PApplet.main("module6.AirportMap");
    }

}
