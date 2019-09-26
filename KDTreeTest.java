package bearmaps;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    @Test
    public void randomTesting() {
        ArrayList<Point> list = new ArrayList<>();

        for (int N = 1; N < 10; N++) {
            for (int i = 0; i < 100000 * N; i++) {
                double rand1 = Math.round(Math.random() * 100000.0);
                double rand2 = Math.round(Math.random() * 100000.0);
                Point temp = new Point(rand1, rand2);
                list.add(temp);
            }
            KDTree test = new KDTree(list);
            NaivePointSet sols = new NaivePointSet(list);

            long begin = System.currentTimeMillis();
            for (int x = 0; x < 100; x++) {
                double randx = Math.round(Math.random() * 100000.0);
                double randy = Math.round(Math.random() * 100000.0);
                test.nearest(randx, randy);
            }
            long end = System.currentTimeMillis();
            System.out.println("Total time elapsed: " + (end - begin) / 1000.0 + "seconds. "+ N);
        }
    }

    @Test
    public void testNaive() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        ret.getX(); // evaluates to 3.3
        ret.getY(); // evaluates to 4.4
    }

    @Test
    public void testConstructor() {
        Point q = new Point(2, 3);
        Point w = new Point(4, 2);
        Point e = new Point(4, 2);
        Point r = new Point(4, 5);
        Point t = new Point(3, 3);
        Point y = new Point(1, 5);
        Point u = new Point(4, 4);

        KDTree test = new KDTree(List.of(q, w, e, r, t, y, u));
    }
}
