package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

public class ArrayHeapMinPQTest<T> {

    @Test
    public void testAdd() {

        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1.0);
        test.add(2, 2.0);
        test.add(3, 3.0);
        test.add(4, 4.0);
        test.add(5, 3.0);
        test.add(6, 2.0);
        //test.add(4, 3.0);
        System.out.println(test.removeSmallest());
        System.out.println(test.removeSmallest());
        System.out.println(test.removeSmallest());
        System.out.println(test.removeSmallest());

        test.add(7, 3.0);
        test.add(8, 2.0);
        test.add(9, 1.0);
        test.add(10, 5.0);
        System.out.println(test.removeSmallest());
        System.out.println(test.removeSmallest());
        System.out.println(test.removeSmallest());
    }

    @Test
    public void contains() {

        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 4);
        test.add(5, 3);
        test.add(6, 2);
        test.add(7, 1);

        assertTrue(test.contains(1));
        assertTrue(test.contains(4));
        assertFalse(test.contains(8));

        System.out.println(test.size());
    }

    @Test
    public void getSmallest() {

        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 5);
        test.add(2, 4);
        System.out.println(test.getSmallest());

        test.add(3, 3);
        test.add(4, 4);
        System.out.println(test.getSmallest());

        test.add(5, 3);
        test.add(6, 2);
        System.out.println(test.getSmallest());
    }

    @Test
    public void removeSmallest() {

        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 4);
        test.add(5, 3);
        test.add(6, 2);

        int answer = test.removeSmallest();
        assertEquals(answer, 1);

        int answer2 = test.removeSmallest();
        assertNotEquals(answer2, 3);
    }

    @Test
    public void changePriority() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(1, 1);
        test.add(2, 2);
        test.add(3, 3);
        test.add(4, 4);
        test.changePriority(1, 4.0);
        test.changePriority(2, 3.0);
        test.changePriority(3, 2.0);
        test.changePriority(4, 1.0);

        System.out.println(test.removeSmallest());
        System.out.println(test.removeSmallest());
        System.out.println(test.removeSmallest());
        System.out.println(test.removeSmallest());
    }

    @Test
    public void runtimeAdd() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            test.add(i, i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for add: " + (end - begin) / 1000.0 + "seconds.");
    }

    @Test
    public void runtimeRemove() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            test.add(i, i);
        }
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            test.removeSmallest();
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for remove: " + (end - begin) / 1000.0 + "seconds.");
    }

    @Test
    public void randomTesting() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> sols = new NaiveMinPQ<>();

        int counter = 0;
        while (counter < 100) {
            double random = StdRandom.uniform();
            if (random < 0.35) {
                int input = (int) StdRandom.uniform() * 1000;
                double priority = StdRandom.uniform() * 1000000;
                if (!test.contains(input) && !sols.contains(input)) {
                    test.add(input, priority);
                    sols.add(input, priority);
                }
            } else if (random >= .35 && random < 0.5) {
                assertEquals(test.size(), sols.size());
                if (test.size() > 0 && sols.size() > 0) {
                    assertEquals(test.getSmallest(), sols.getSmallest());
                }
            } else if (random >= 0.5 && random < 0.75) {
                int item = test.getSmallest();
                double priority = StdRandom.uniform() * 1000;
                if (test.contains(item) && sols.contains(item)) {
                    test.changePriority(item, priority);
                    sols.changePriority(item, priority);
                }
            } else {
                if (test.size() > 0 && sols.size() > 0) {
                    assertEquals(test.removeSmallest(), sols.removeSmallest());
                }
            }
            counter += 1;
        }

    }
}
