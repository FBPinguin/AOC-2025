import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Day02 {


    public static void main(String[] args) {
        var day = new Day02();
        day.loadData("input.txt");
        System.out.println(day.solveSolution1());
        day = new Day02();
        day.loadData("input.txt");
        System.out.println(day.solveSolution2());

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
        return 0;
    }

    private long solveSolution2() {
        return 0;
    }

}
