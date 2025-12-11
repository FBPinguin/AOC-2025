import java.util.*;

public class Day11 {
    public Day11() {
    }


    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();
        var day = new Day11();
        day.loadData();
        System.out.println(day.solveSolution1());
        day = new Day11();
        day.loadData();
        System.out.println(day.solveSolution2());
        var timeDiff = System.currentTimeMillis() - startTime;
        System.out.println(timeDiff + "ms");

    }

    private void loadData() {
        loadData("string.txt");
    }

    Map<String, List<String>> serverRack = new HashMap<>();


    private void loadData(String fileName) {
        var input = new Solution(2025, 7).readInput("input.txt");
        var inputIter = input.iterator();

        for (Iterator<String> it = inputIter; it.hasNext(); ) {
            String s = it.next();
            var stringSplit = s.split(" ");
            String key = stringSplit[0].substring(0, stringSplit[0].length() - 1);
            List<String> values = new ArrayList<>(stringSplit.length - 1);
            values.addAll(Arrays.asList(stringSplit).subList(1, stringSplit.length));
            serverRack.put(key, values);
        }

    }

    long countSolution1(List<String> branches, String end, Map<String, Long> cache) {
        long acc = 0;
        for (String branch : branches) {
            if (branch.equals(end)) {
                acc++;
            }
            else if (cache.containsKey(branch)) {
                acc += cache.get(branch);
            }
            else {
                if (!branch.equals("out")) {
                    long recAns = countSolution1(serverRack.get(branch), end, cache);
                    cache.put(branch, recAns);
                    acc += recAns;
                }
            }
        }
        return acc;
    }

    private long solveSolution1() {
        return countSolution1(serverRack.get("you"), "out", new HashMap<>());
    }


    long solutionsFrom(String start, String end) {
        return countSolution1(serverRack.get(start), end, new HashMap<>());
    }

    private long solveSolution2() {
        long svrToFft = solutionsFrom("svr", "fft");
        long fftToDac = solutionsFrom("fft", "dac");
        long dacToOut = solutionsFrom("dac", "out");

        return svrToFft * fftToDac * dacToOut;
    }
}
