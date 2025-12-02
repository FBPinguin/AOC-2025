import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day02 {

    record ID(long firstID, long lastID) {
    }

    List<ID> list;

    public Day02() {
        list = new ArrayList<>();
    }


    public static void main(String[] args) {
        var day = new Day02();
        day.loadData("input.txt");
        System.out.println(day.solveSolution1());
        day = new Day02();
        day.loadData("input.txt");
        var startTime = System.currentTimeMillis();
        System.out.println(day.solveSolution2());
        var timeDiff = System.currentTimeMillis() - startTime;
        System.out.println(timeDiff);

    }

    private void loadData(String fileName) {
        try (var reader = new BufferedReader(new FileReader(fileName))){


            String line;
            while ((line = reader.readLine()) != null) {
                var scanner = new Scanner(line);
                scanner.useDelimiter("[-,]");

                while (scanner.hasNext()) {
                    long first = scanner.nextLong();
                    long next = scanner.nextLong();
                    list.add(new ID(first, next));
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Failed to find file");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    private long solveSolution1() {
        List<Long> incorrect = new ArrayList<Long>();

        for (ID id : list) {
            for (long i = id.firstID; i <= id.lastID; i++) {
                if (idIncorrect(i)) {
                    incorrect.add(i);
                }
            }
        }

        return incorrect.stream().mapToLong(Long::valueOf).sum();
    }

    private boolean idIncorrect(long id) {
        String s = String.valueOf(id);
        if (s.length() % 2 != 0) {
            return false;
        }
        int middle = s.length() / 2;
        if (s.substring(0,middle).equals(s.substring(middle))){
            return true;
        }

        return false;
    }

    private boolean idIncorrectMultiple(long id) {
        String s = String.valueOf(id);
        outer: for (int i = 2; i <= s.length(); i++) {
            int middle = s.length() / i;
            if ((s.length()/i)*i != s.length()) {
                continue;
            }
            for (int j = 0; j < i-1; j++) {
                String ss1 = s.substring(middle * j, middle * (j + 1));
                String ss2 = s.substring(middle * (j + 1), middle * (j + 2));
                if (!ss1.equals(ss2)){
                    continue outer;
                }
            }
            return true;
        }

        return false;
    }


    private long solveSolution2() {
        List<Long> incorrect = new ArrayList<Long>();

        for (ID id : list) {
            for (long i = id.firstID; i <= id.lastID; i++) {
                if (idIncorrectMultiple(i)) {
                    incorrect.add(i);
                }
            }
        }

        return incorrect.stream().mapToLong(Long::valueOf).sum();

    }

}
