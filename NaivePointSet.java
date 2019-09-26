package bearmaps;

import java.util.ArrayList;
import java.util.List;


public class NaivePointSet implements PointSet {

    ArrayList<Point> items = new ArrayList<>();

    public NaivePointSet(List<Point> points) {

        for (int i = 0; i < points.size(); i++) {
            items.add(points.get(i));
        }
    }

    public Point nearest(double x, double y) {

        double smallestDistance = distance(items.get(0), x, y);
        Point answer = items.get(0);

        for (int i = 0; i < items.size(); i++) {
            if (distance(items.get(i), x, y) < smallestDistance) {
                answer = items.get(i);
                smallestDistance = distance(items.get(i), x, y);
            }
        }
        return answer;
    }

    private double distance(Point temp, double x, double y) {
        return Math.sqrt(Math.pow((temp.getX() - x), 2) + Math.pow((temp.getY() - y), 2));
    }
}
