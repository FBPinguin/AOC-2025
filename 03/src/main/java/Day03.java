import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day03 {

    List<String> banks;

    public Day03() {
        banks = new ArrayList<>();
    }

    public static void main(String[] args) {
        var day = new Day03();
        day.loadData("input.txt");
        System.out.println(day.solveSolution1());
        day = new Day03();
        day.loadData("input.txt");
                var startTime = System.currentTimeMillis();
        System.out.println(day.solveSolution2());
                var timeDiff = System.currentTimeMillis() - startTime;
                System.out.println(timeDiff+ "ms");
    }

    private void loadData(String fileName) {
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                banks.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Failed to find file");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    private long solveSolution1() {
        return banks.stream().map(i -> maxInt(i, 2)).reduce(Long::sum).get();
    }


    // STUPID GREEDY ALGORITHM OFF BY 27 I CAN'T
    //    private long maxInt(String substring, int size) {
    //        var array = new LinkedList<Long>();
    //        for (int i = 0; i < substring.length() && array.size() < size; i++) {
    //            long n = substring.charAt(i) - '0';
    //            int left = substring.length() - i - size + array.size();
    //            if (left <= 0 || array.size() == 0 ||
    //                    array.getLast() >= maxIntSubString(substring.substring(i, i + left + 1))) {
    //                array.add(n);
    //                continue;
    //            }
    //
    //            if (array.size() > 0) {
    //                n = Math.max(array.get(array.size() - 1), n);
    //                array.set(array.size() - 1, n);
    //            }
    //
    //        }
    //        long result = array.stream().reduce(0L, (acc, a) -> acc * 10 + a);
    //        return result;
    //    }

    record StringSize(String s, int size) {
        @Override
        public boolean equals(Object other) {
            if (other instanceof StringSize ss) {
                return Objects.equals(ss.s, this.s) && ss.size == this.size;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return s.hashCode() ^ Integer.hashCode(size);
        }
    }

    HashMap<StringSize, Long> seenMax = new HashMap<>();

    private long maxInt(String substring, int size) {
        StringSize stringSize = new StringSize(substring, size);
        if (seenMax.containsKey(stringSize)) {
            return seenMax.get(stringSize);
        }
        if (size == 1) {
            return maxIntSubString(substring);
        }

        long max = 0L;

        for (int i = 0; i <= substring.length() - size; i++) {
            long frontInt = substring.charAt(i) - '0';
            long maxRest = maxInt(substring.substring(i + 1), size - 1);
            max = Math.max(frontInt * (long) Math.pow(10, size - 1) + maxRest, max);
        }

        seenMax.put(stringSize, max);

        return max;
    }

    private long maxIntSubString(String substring) {
        return substring.chars().asLongStream().map(i -> i - '0').max().getAsLong();
    }


    private long solveSolution2() {
        return banks.stream().map(i -> maxInt(i, 12)).reduce(Long::sum).get();
    }

}
