package bearmaps;

import java.util.List;

public class KDTree implements PointSet {

    private Node base;
    private boolean checker;

    private class Node {
        Node leftBranch;
        Node rightBranch;
        Point point;

        Node(Point p) {
            this.point = p;
            leftBranch = null;
            rightBranch = null;
        }
    }

    public KDTree(List<Point> points) {

        checker = true;

        for (int i = 0; i < points.size(); i++) {
            base = insert(points.get(i), base, checker);
        }
    }

    public Point nearest(double x, double y) {

        Point desired = new Point(x, y);
        double dist = getDistance(base.point, desired);
        return nearestPoint(base, base.point, desired, dist, checker);
    }

    private Node insert(Point p, Node n, boolean b) {

        if (n == null) {
            return new Node(p);
        } else if (n.point.equals(p)) {
            return n;
        }

        if (b) {
            if (!compareTo(p, n, b)) {
                n.leftBranch = insert(p, n.leftBranch, false);
            } else {
                n.rightBranch = insert(p, n.rightBranch, false);
            }
        } else {
            if (!compareTo(p, n, b)) {
                n.leftBranch = insert(p, n.leftBranch, true);
            } else {
                n.rightBranch = insert(p, n.rightBranch, true);
            }
        }
        return n;
    }

    private double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    private boolean compareTo(Point p1, Node n, boolean b) {
        if (b) {
            if (p1.getX() - n.point.getX() < 0) {
                return false;
            }
            return true;
        } else {
            if (p1.getY() - n.point.getY() < 0) {
                return false;
            }
            return true;
        }
    }

    private Point nearestPoint(Node n, Point best, Point desired, double dist, boolean b) {

        if (n == null) {
            return best;
        }
        if (n.point.equals(desired)) {
            return desired;
        }
        if (getDistance(n.point, desired) < getDistance(best, desired)) {
            best = n.point;
            dist = getDistance(n.point, desired);
        }

        double distance;
        if (b) {
            distance = desired.getX() - n.point.getX();
        } else {
            distance = desired.getY() - n.point.getY();
        }
        if (distance < 0) {
            best = nearestPoint(n.leftBranch, best, desired, dist, !b);

            if (n.rightBranch != null && getDistance(best, desired) > Math.abs(distance)) {
                best = nearestPoint(n.rightBranch, best, desired, dist, !b);
            }
        } else {
            best = nearestPoint(n.rightBranch, best, desired, dist, !b);

            if (n.leftBranch != null && getDistance(best, desired) > Math.abs(distance)) {
                best = nearestPoint(n.leftBranch, best, desired, dist, !b);
            }
        }
/*
        if (b == true) {
            if (compareTo(desired, n, b) == false) {
                best = nearestPoint(n.leftBranch, best, desired, dist, !b);

                if (getDistance(desired, best) >= (desired.getX() - n.point.getX())) {
                    best = nearestPoint(n.rightBranch, best, desired, dist, !b);
                }
            } else {
                best = nearestPoint(n.rightBranch, best, desired, dist, !b);
                if (getDistance(desired, best) >= (desired.getX() - n.point.getX())) {
                    best = nearestPoint(n.leftBranch, best, desired, dist, !b);
                }
            }
        } else {
            if (desired.getY() < n.point.getY()) {
                best = nearestPoint(n.leftBranch, best, desired, dist, !b);
                if (getDistance(desired, best) >= (desired.getY() - n.point.getY())){
                    best = nearestPoint(n.rightBranch, best, desired, dist, !b);

                }
            } else {
                best = nearestPoint(n.rightBranch, best, desired, dist, !b);
                if (getDistance(desired, best) >= (desired.getY() - n.point.getY())){
                    best = nearestPoint(n.leftBranch, best, desired, dist, !b);

                }
            }
        }*/
        return best;
    }
}
