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
        for (int i = 0; i < this.mapHeight; i++) {
            for (int j = 0; j < this.mapWidth; j++) {
                if (numOfAdjecent(i, j) < 4 && map.get(i).charAt(j) == '@') {
                    acc++;
                }
            }
        }
        return acc;
    }


    private int numOfAdjecent(int row, int col) {
        int acc = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (i == j && i == 0) {
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


    boolean isOutofBounds(int i, int j) {
        if (i < 0 || i >= mapHeight){
            return true;
        }
        if (j < 0 || j >= mapWidth) {
            return true;
        }
        return false;
    }

    long checkPos(int i, int j) {
        long acc = 0;
        if (isOutofBounds(i, j)) {
            return 0;
        }
        if (map.get(i).charAt(j) != '@' || isGoneMap[i][j]) {
            return 0;
        }

        if (numOfAdjecent(i, j) < 4 && map.get(i).charAt(j) == '@') {
            acc++;
            isGoneMap[i][j] = true;
            for (int i2 = 0; i2 < this.mapHeight; i2++) {
                for (int j2 = 0; j2 < this.mapWidth; j2++) {
                    if (i2 == j2 && i2 == 0) {
                        continue;
                    }
                    checkPos(i + i2, j + j2);
                }
            }
        }
        return acc;
    }

    private long solveSolution2() {
        long acc = 0;
        for (int i = 0; i < this.mapHeight; i++) {
            for (int j = 0; j < this.mapWidth; j++) {
                checkPos(i, j);
            }
        }
        for (int i = 0; i < this.mapHeight; i++) {
            for (int j = 0; j < this.mapWidth; j++) {
               if (isGoneMap[i][j]) {
                   acc++;
               }
            }
        }
        return acc;

    }

}
