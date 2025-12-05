import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

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

    List<Long> allIngrediants = new ArrayList<>();
    Intervals intervals = new Intervals();

    private void loadData(String fileName) {
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                var scanner = new Scanner(line);
                scanner.useDelimiter("[-\s]");
                while (scanner.hasNext()) {
                    var begin = scanner.nextLong();
                    var end = scanner.nextLong();
                    intervals.addInterval(new Intervals.Interval(begin, end));
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


    private long solveSolution1() {
        return allIngrediants.stream().filter(i -> intervals.contains(i) != null).count();
    }


    private long solveSolution2() {
        return intervals.map(i -> i.getSize()).stream().reduce((acc, i) -> acc + i).get();
    }

}