import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day04 {

    List<String> map = new ArrayList<>();
    int mapHeight;
    int mapWidth;

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
                    map.add(scanner.nextLine());
                }
            }
            this.mapHeight = this.map.size();
            this.mapWidth = this.map.get(0).length();
            isGoneMap = new boolean[mapHeight][mapWidth];

        } catch (FileNotFoundException e) {
            System.out.println("Failed to find file");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    private long solveSolution1() {
        long acc = 0;
        for (int row = 0; row < this.mapHeight; row++) {
            for (int col = 0; col < this.mapWidth; col++) {
                if (numOfAdjecent(row, col) < 4 && map.get(row).charAt(col) == '@') {
                    acc++;
                }
            }
        }
        return acc;
    }


    private int numOfAdjecent(int row, int col) {
        int acc = 0;
        for (int offsetWidth = -1; offsetWidth < 2; offsetWidth++) {
            for (int offsetHeight = -1; offsetHeight < 2; offsetHeight++) {
                int newRow = row + offsetWidth;
                int newCol = col + offsetHeight;
                if (offsetWidth == offsetHeight && offsetWidth == 0) {
                    continue;
                }
                if (isOutofBounds(newRow, newCol)) {
                    continue;
                }
                if (map.get(newRow).charAt(newCol) == '@' && !isGoneMap[newRow][newCol]) {
                    acc++;
                }
            }
        }
        return acc;
    }

    boolean[][] isGoneMap;


    boolean isOutofBounds(int row, int col) {
        if (row < 0 || row >= mapHeight){
            return true;
        }
        if (col < 0 || col >= mapWidth) {
            return true;
        }
        return false;
    }

    void checkPos(int row, int col) {
        if (isOutofBounds(row, col)) {
            return;
        }
        if (map.get(row).charAt(col) != '@' || isGoneMap[row][col]) {
            return;
        }

        if (numOfAdjecent(row, col) < 4 && map.get(row).charAt(col) == '@') {
            isGoneMap[row][col] = true;
            for (int offsetHeight = -1; offsetHeight < 2; offsetHeight++) {
                for (int offsetWidth = -1; offsetWidth < 2; offsetWidth++) {
                    if (offsetHeight == offsetWidth && offsetHeight == 0) {
                        continue;
                    }
                    checkPos(row + offsetHeight, col + offsetWidth);
                }
            }
        }
    }

    private long solveSolution2() {
        long acc = 0;
        for (int row = 0; row < this.mapHeight; row++) {
            for (int col = 0; col < this.mapWidth; col++) {
                checkPos(row, col);
            }
        }
        for (int row = 0; row < this.mapHeight; row++) {
            for (int col = 0; col < this.mapWidth; col++) {
               if (isGoneMap[row][col]) {
                   acc++;
               }
            }
        }
        return acc;

    }

}
