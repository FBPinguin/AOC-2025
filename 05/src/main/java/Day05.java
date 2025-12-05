import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day05 {

    public Day05() {
    }


    public static void main(String[] args) {
        var day = new Day05();
        day.loadData("input.txt");
        System.out.println(day.solveSolution1());
        day = new Day05();
        day.loadData("input.txt");
        var startTime = System.currentTimeMillis();
        System.out.println(day.solveSolution2());
        var timeDiff = System.currentTimeMillis() - startTime;
        System.out.println(timeDiff + "ms");

    }

    record Range(long start, long end) {
        public boolean contains(long n) {
            return n >= start && n <= end;
        }

        public long getRange() {
            return end - start + 1;
        }

        public boolean hasOverlap(Range other) {
            if (other.start <= this.start && other.end >= this.start) {
                return true;
            }
            if (other.end >= this.end && this.end >= other.start) {
                return true;
            }
            if (other.start > this.start && other.end < this.end) {
                return true;
            }
            return false;
        }

        public Range nonOverlappingRangeLeft(Range other) {
            long start = this.start;
            long end = other.start - 1;
            if (end < start) {
                return null;
            }
            return new Range(start, end);
        }

        public Range nonOverlappingRangeRight(Range other) {
            long start = other.end + 1;
            long end = this.end;
            if (end < start) {
                return null;
            }
            return new Range(start, end);
        }
    }

    List<Long> allIngrediants = new ArrayList<>();
    List<Range> allRanges = new ArrayList<>();

    private void loadData(String fileName) {
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            outer:
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    break outer;
                }
                var scanner = new Scanner(line);
                scanner.useDelimiter("[-\s]");
                while (scanner.hasNext()) {
                    var begin = scanner.nextLong();
                    var end = scanner.nextLong();
                    allRanges.add(new Range(begin, end));


                }

            }
            while ((line = reader.readLine()) != null) {
                var scanner = new Scanner(line);
                allIngrediants.add(scanner.nextLong());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Failed to find file");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }


    boolean isFresh(long i) {
        for (Range range : allRanges) {
            if (range.contains(i)) {
                return true;
            }
        }
        return false;
    }

    private long solveSolution1() {
        long acc = 0;

        for (Long ingredient : allIngrediants) {
            if (isFresh(ingredient)) {
                acc++;
            }
        }
        return acc;
    }

    private Range findOverlappingRange(Range range) {
        for (Range nonOverlappingRange : nonOverlappingRanges) {
            if (range.hasOverlap(nonOverlappingRange)) {
                return nonOverlappingRange;
            }
        }
        return null;
    }


    private void addCutDownRanges(Range range) {
        var overlap = findOverlappingRange(range);
        if (overlap == null) {
            nonOverlappingRanges.add(range);
            return;
        }
        Range newRangeLeft = range.nonOverlappingRangeLeft(overlap);
        Range newRangeRight = range.nonOverlappingRangeRight(overlap);

        if (newRangeLeft != null) {
            addCutDownRanges(newRangeLeft);
        }
        if (newRangeRight != null) {
            addCutDownRanges(newRangeRight);
        }
    }


    List<Range> nonOverlappingRanges = new ArrayList<>();

    private long solveSolution2() {
        long acc = 0;

        for (Range range : allRanges) {
            addCutDownRanges(range);
        }


        for (Range range : nonOverlappingRanges) {
            acc += range.getRange();
        }
        return acc;
    }

}