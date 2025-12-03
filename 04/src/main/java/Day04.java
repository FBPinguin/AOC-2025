import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Day04 {


    public Day04() {
    }


    public static void main(String[] args) {
        var day = new Day04();
        day.loadData("input.txt");
        System.out.println(day.solveSolution1());
        day = new Day04();
        day.loadData("input.txt");
        var startTime = System.currentTimeMillis();
        System.out.println(day.solveSolution2());
        var timeDiff = System.currentTimeMillis() - startTime;
        System.out.println(timeDiff + "ms");

    }

    private void loadData(String fileName) {
        try (var reader = new BufferedReader(new FileReader(fileName))){


            String line;
            while ((line = reader.readLine()) != null) {
                var scanner = new Scanner(line);

                while (scanner.hasNext()) {
                    
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Failed to find file");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    private long solveSolution1() {
        return -1;
    }




    private long solveSolution2() {
        return -1;
    }

}
