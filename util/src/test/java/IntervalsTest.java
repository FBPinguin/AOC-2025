import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalsTest {
    Intervals intervals;

    @BeforeEach
    public void init() {
        intervals = new Intervals();
    }


    @Test
    public void containsTest() {
        intervals.addInterval(new Intervals.Interval(1, 10));
        assertTrue(intervals.contains(3L) );
        assertTrue(intervals.contains(1L) );
        assertTrue(intervals.contains(10L));
        assertFalse(intervals.contains(0L));
        intervals.addInterval(new Intervals.Interval(9, 12));
        assertTrue(intervals.contains(12L) );
        assertTrue(intervals.contains(1L));
    }


    @Test
    public void testTotalSize() {
        intervals.addInterval(new Intervals.Interval(1, 3));
        intervals.addInterval(new Intervals.Interval(2, 5));
        intervals.addInterval(new Intervals.Interval(6, 10));
        var totalSize = intervals.map((i) -> i.getSize()).stream().reduce((acc, i) -> acc + i).get();
        assertEquals(totalSize, 10);
    }
    @Test
    public void testAddingIntervals() {
        intervals.addInterval(new Intervals.Interval(2, 3));
        intervals.addInterval(new Intervals.Interval(1, 4));
        intervals.addInterval(new Intervals.Interval(5, 7));
        intervals.addInterval(new Intervals.Interval(0, 1));
        intervals.addInterval(new Intervals.Interval(-10, -9));
        intervals.addInterval(new Intervals.Interval(3, 6));
    }

    @Test
    public void testRandomIntervalAddition() {
        for (int i = 0; i < 1000000; i++) {
           long endPoint = (long) (Math.random() * Long.MAX_VALUE/2);
           long startPoint = (long) (endPoint * (Math.random()/10.0));
            intervals.addInterval(new Intervals.Interval(startPoint, endPoint));
            assertTrue(intervals.contains((endPoint+startPoint)/2L));
        }
    }
}