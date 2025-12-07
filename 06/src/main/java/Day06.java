import java.sql.Array;
import java.util.*;

public class Day06 {

    public Day06() {
    }

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();
        var day = new Day06();
        day.loadData();
        System.out.println(day.solveSolution1());
        day = new Day06();
        day.loadData();
        System.out.println(day.solveSolution2());
        var timeDiff = System.currentTimeMillis() - startTime;
        System.out.println(timeDiff + "ms");

    }

    List<String> operations = new ArrayList<>();

    List<List<Long>> numbers = new ArrayList<>();

    private void loadData(){
        loadData("string.txt");
    }

    private void loadData(String fileName) {
        var input = new Solution(2025, 5).readInput("input.txt");
        var inputIter = input.iterator();

        for (Iterator<String> it = inputIter; it.hasNext(); ) {
            String s = it.next();
            s = s.trim();
            var splitS = s.split("\\s+");

            if (!it.hasNext()) {
                for (String string : s.split("\\s+")) {
                    operations.add(string);
                }
                break;
            }
            for (int i = 0; i < splitS.length; i++) {
                if (numbers.size() <= i) {
                    numbers.add(new ArrayList<>());
                }
                numbers.get(i).add(Long.valueOf(splitS[i]));
            }


        }



        for (Iterator<String> it = inputIter; it.hasNext(); ) {
            String s = it.next();

        }
    }


    public long operator(char s, long int1, long int2) {
        return switch (s) {
            case '+' -> int1 + int2;
            case '*' -> int1*int2;
            default -> throw new RuntimeException("huh, new operator");
        };

    }
    private long solveSolution1() {
        return getAllOperationValues(numbers);
    }

    private long getAllOperationValues(List<List<Long>> numbers) {
        long acc = 0;
        for (int i = 0; i < numbers.size(); i++) {
            char operation = operations.get(i).charAt(0);
            long innerAcc = operation == '*' ? 1 : 0;
            for (long l : numbers.get(i)) {
                innerAcc = operator(operation, innerAcc, l);
            }
            acc += innerAcc;
        }
        return acc;
    }

    List<List<Long>> verticalNumbers = new ArrayList<>();

    private long solveSolution2() {

        var input = new Solution(1,1).readInput("input.txt");

        List<String> verticalNumbersString = getAllVerticalStrings(input);

        List<List<Long>> verticalNumbers = splitAllVerticalCollumns(verticalNumbersString);

        return getAllOperationValues(verticalNumbers);
    }

    private static List<List<Long>> splitAllVerticalCollumns(
                                  List<String> verticalNumbersString) {
        List<List<Long>> verticalNumbers = new ArrayList<>();
        int row = 0;
        verticalNumbers.add(new ArrayList<>());
        for (String s : verticalNumbersString) {
            if (s.isBlank()) {
                row++;
                verticalNumbers.add(new ArrayList<>());
                continue;
            }
            verticalNumbers.get(row).add(Long.valueOf(s.trim()));
        }
        return verticalNumbers;
    }

    private List<String> getAllVerticalStrings(List<String> input) {
        List<String> allVerticalString = new ArrayList<>();
        for (int row = 0; row < input.size()-1; row++) {
            for (int col = 0; col < input.get(row).length(); col++) {
                var character = input.get(row).charAt(col);
                if (allVerticalString.size() <= col) {
                    allVerticalString.add(String.valueOf(character));
                }
                else {
                    allVerticalString.set(col, allVerticalString.get(col) + character);
                }
            }
        }
        return allVerticalString;
    }

}