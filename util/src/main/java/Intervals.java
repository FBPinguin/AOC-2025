import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Intervals {
    private List<Interval> intervals;

    public Intervals() {
        this.intervals = new ArrayList<>();
    }

    public Intervals(List<Interval> intervals) {
        this.intervals = intervals;
    }

    private List<Interval> popOverlappingIntervals(Interval interval) {
        List<Interval> overlappingIntervals = new ArrayList<>();
        for (Interval interval1 : intervals) {
            if (interval.getOverlap(interval1) != Interval.Overlap.NONE) {
                overlappingIntervals.add(interval1);
            }
        }

        intervals.removeAll(overlappingIntervals);

        return overlappingIntervals;
    }


    private Interval popOverlappingInterval(List<Interval> intervals, Interval interval) {
        for (Interval interval1 : intervals) {
            if (interval.getOverlap(interval1) != Interval.Overlap.NONE) {
                return interval1;
            }
        }

        throw new RuntimeException("There was no interval that was overlapping" + interval);

    }


    public void addInterval(Interval interval) {
        var overlappingIntervals = popOverlappingIntervals(interval);
        overlappingIntervals.add(interval);
        var newInterval = mergeIntervals(overlappingIntervals);
        this.intervals.add(newInterval);

    }

    private Interval mergeIntervals(List<Interval> overlappingIntervals) {
        var mergedInterval = overlappingIntervals.remove(0);
        while (!overlappingIntervals.isEmpty()) {
            Interval overlappingInterval =
                    popOverlappingInterval(overlappingIntervals, mergedInterval);
            overlappingIntervals.remove(overlappingInterval);
            mergedInterval = mergedInterval.merge(overlappingInterval);
        }

        return mergedInterval;
    }

    public <E> List<E> map(Function<Interval, E> map) {
        List<E> mappedList = new ArrayList<>();
        for (Interval interval : intervals) {
            mappedList.add(map.apply(interval));
        }
        return mappedList;
    }

    public boolean contains(Long i) {
        return getInterval(i) != null;
    }

    public Interval getInterval(Long i) {
        for (Interval interval : intervals) {
            if (interval.contains(i)) {
                return interval;
            }
        }
        return null;
    }


    /**
     * Interval representing [a,b].
     */
    public record Interval(long start, long end) {

        public boolean contains(long num) {
            return start <= num && num <= end;
        }

        public enum Overlap {
            START, END, INSIDE, OUTSIDE,TOUCHING_LEFT, TOUCHING_RIGHT, NONE;
        }

        public Long getSize() {
            return end - start + 1;
        }


        public Overlap getOverlap(Interval other) {
            Overlap overlap = Overlap.NONE;
            if (other.start <= this.start && this.start <= other.end) {
                overlap = Overlap.START;
            }
            if (other.start <= this.end && this.end <= other.end) {
                if (overlap == Overlap.START) {
                    overlap = Overlap.INSIDE;
                } else {
                    overlap = Overlap.END;
                }
            }
            if (this.start <= other.start && other.end <= this.end) {
                overlap = Overlap.OUTSIDE;
            }

            if (this.start - other.end == 1) {
                overlap = Overlap.TOUCHING_LEFT;
            }

            if (other.start - this.end  == 1) {
                overlap = Overlap.TOUCHING_RIGHT;
            }

            return overlap;
        }

        public Interval merge(Interval other) {
            return switch (getOverlap(other)) {
                case START -> new Interval(other.start, this.end);
                case END -> new Interval(this.start, other.end);
                case INSIDE -> new Interval(other.start, other.end);
                case OUTSIDE -> new Interval(this.start, this.end);
                case TOUCHING_LEFT -> new Interval(other.start, this.end);
                case TOUCHING_RIGHT -> new Interval(this.start, other.end);
                case NONE -> null;
            };

        }

        @Override
        public String toString() {
            return String.format("Interval(%d,%d)", start, end);
        }
    }

    ;


}
