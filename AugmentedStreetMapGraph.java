package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import java.util.*;
import edu.princeton.cs.algs4.TST;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    WeirdPointSet path;
    HashMap<Point, Node> graph = new HashMap<>();

    TST loc;
    HashMap<String, List<Node>> name = new HashMap<>();
    List<Node> emp;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);

        List<Node> nodes = this.getNodes();
        List<Point> answer = new ArrayList<>();

        emp = new ArrayList<>();
        loc = new TST<>();

        for (Node i : nodes) {
            if (!neighbors(i.id()).isEmpty()) {
                Point temp = new Point(i.lon(), i.lat());
                graph.put(temp, i);
                answer.add(temp);
            }
            if (i.name() != null && cleanString(i.name()).length() >= 1) {

                loc.put(cleanString(i.name()), cleanString(i.name()));

                if (name.containsKey(cleanString(i.name()))) {

                    name.get(cleanString(i.name())).add(i);
                } else {

                    List <Node> list = new ArrayList<>();
                    list.add(i);
                    name.put(cleanString(i.name()), list);
                }
            } else if (i.name() != null && cleanString(i.name()).equals("") &&
                    i.name().length() > 0) {
                emp.add(i);
            }
        }
        path = new WeirdPointSet(answer);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {

        Point temp = path.nearest(lon, lat);
        Node answer = graph.get(temp);
        return answer.id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> answer = new ArrayList<>();

        Iterable<String> keys = loc.keysWithPrefix(cleanString(prefix));

        for (String i : keys) {
            for (Node j : name.get(i)) {
                answer.add(j.name());
            }
        }
        return answer;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List answer = new ArrayList();
        if (locationName.length() > 0) {
            Iterable<String> keys = loc.keysThatMatch(cleanString(locationName));
            for (String key : keys) {
                for (Node i : name.get(key)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", i.id());
                    map.put("name", i.name());
                    map.put("lat", i.lat());
                    map.put("lon", i.lon());
                    answer.add(map);
                }
            }
        } else if (cleanString(locationName).equals("")) {
            for (Node i : emp) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", i.id());
                map.put("name", i.name());
                map.put("lat", i.lat());
                map.put("lon", i.lon());
                answer.add(map);
            }
        }
        return answer;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {

        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
