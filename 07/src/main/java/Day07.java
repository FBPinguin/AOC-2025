import java.sql.Array;
import java.util.*;
import javax.swing.text.Position;

public class Day07 {

    public Day07() {
    }

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();
        var day = new Day07();
        day.loadData();
        System.out.println(day.solveSolution1());
        day = new Day07();
        day.loadData();
        System.out.println(day.solveSolution2());
        var timeDiff = System.currentTimeMillis() - startTime;
        System.out.println(timeDiff + "ms");

    }

    record Move(Graph.Position pos, Graph.Direction direction) {
        public Move getNewPos() {
            return new Move(direction.add(pos), direction);
        }
    }

    List<List<String>> map = new ArrayList<>();
    Graph.Position startingSpot;


    private void loadData() {
        loadData("string.txt");
    }

    private void loadData(String fileName) {
        var input = new Solution(2025, 7).readInput("input.txt");
        var inputIter = input.iterator();

        int i = 0;
        for (Iterator<String> it = inputIter; it.hasNext(); ) {
            String s = it.next();
            for (int j = 0; j < s.length(); j++) {
                if (map.size() <= i) {
                    map.add(new ArrayList<>());
                }
                String character = String.valueOf(s.charAt(j));
                if (character.equals("S")) {
                    startingSpot = new Graph.Position(i, j);
                }
                map.get(i).add(character);
            }
            i++;

        }

    }


    private long solveSolution1() {
        return getAcitvatedTeleporters();

    }

    private long getAcitvatedTeleporters() {
        long acc = 0;
        Set<Graph.Position> turnedOnMap = new HashSet<>();
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(new Graph.Position(startingSpot.row(), startingSpot.col()),
                           Graph.Direction.DOWN));

        while (!moves.isEmpty()) {
            Move move = moves.remove(moves.size() - 1);
            Move newMove = move.getNewPos();
            Graph.Position newPos = newMove.pos;

            if (!isValid(newPos)) {
                continue;
            }

            switch (getChar(newPos)) {
                case "." -> moves.add(newMove);
                case "^" -> {
                    if (!turnedOnMap.contains(newPos)) {

                        moves.addAll(getSplitMoves(newMove));
                        turnedOnMap.add(newPos);
                        acc++;
                    }
                }
            }

        }

        return acc;
    }

    private Collection<Move> getSplitMoves(Move newMove) {
        List<Move> splitMoves = new ArrayList<>();
        Move moveRight = new Move(new Graph.Position(newMove.pos.row(), newMove.pos.col() - 1),
                                  newMove.direction());
        Move moveLeft = new Move(new Graph.Position(newMove.pos.row(), newMove.pos.col() + 1),
                                 newMove.direction());
        splitMoves.add(moveRight);
        splitMoves.add(moveLeft);
        splitMoves = splitMoves.stream().filter((i) -> isValid(i.pos())).toList();
        return splitMoves;
    }

    private String getChar(Graph.Position newPos) {
        return map.get(newPos.row()).get(newPos.col());
    }

    private boolean isValid(Graph.Position newPos) {
        if (newPos.row() < 0 || newPos.row() >= map.size()) {
            return false;
        }
        if (newPos.col() < 0 || newPos.col() >= map.get(0).size()) {
            return false;
        }
        return true;
    }


    private long solveSolution2() {
        Graph.Position startPos = new Graph.Position(startingSpot.row(), startingSpot.col());

        return 1 + getTotalTimeLines(getTeleporterBelow(startPos));

    }



    Map<Graph.Position, Long> timeLineMap = new HashMap<>();
    private long getTotalTimeLines(Graph.Position pos) {
        if (timeLineMap.containsKey(pos)) {
            return timeLineMap.get(pos);
        }

        Graph.Position leftBelow = getTeleporterBelow(new Graph.Position(pos.row(), pos.col()-1));
        Graph.Position rightBelow = getTeleporterBelow(new Graph.Position(pos.row(), pos.col()+1));
        long acc = 1;
        if (leftBelow != null) acc += getTotalTimeLines(leftBelow);
        if (rightBelow != null) acc += getTotalTimeLines(rightBelow);

        timeLineMap.put(pos, acc);

        return acc;
    }


    public Graph.Position getTeleporterBelow(Graph.Position pos){
        int row = pos.row();
        int col = pos.col();
        Graph.Position returnPos = null;

        while (true) {
            row++;
            var newPos = new Graph.Position(row, col);
            if (!isValid(newPos)) {
                break;
            }
            if (getChar(newPos).equals("^")) {
                returnPos = newPos;
                break;
            }
        }

        return returnPos;
    }


}
