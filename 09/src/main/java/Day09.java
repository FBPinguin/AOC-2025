import java.util.*;
import org.jetbrains.annotations.NotNull;

public class Day09 {
    public Day09() {
    }

    record Position(long row, long col) {

        long squareDist(Position other) {
            long dy = Math.abs(this.row - other.row) + 1;
            long dx = Math.abs(this.col - other.col) + 1;
            return dy*dx;
        }
    }

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();
        var day = new Day09();
        day.loadData();
        System.out.println(day.solveSolution1());
        day = new Day09();
        day.loadData();
        System.out.println(day.solveSolution2());
        var timeDiff = System.currentTimeMillis() - startTime;
        System.out.println(timeDiff + "ms");

    }

    private void loadData() {
        loadData("string.txt");
    }

    List<Position> tiles = new ArrayList<>();

    private void loadData(String fileName) {
        var input = new Solution(2025, 7).readInput("input.txt");
        var inputIter = input.iterator();

        for (Iterator<String> it = inputIter; it.hasNext(); ) {
            String s = it.next();
            var numbers = s.split(",");
            var row = Long.parseLong(numbers[0]);
            var col = Long.parseLong(numbers[1]);
            tiles.add(new Position(row, col));
        }

    }

    private long solveSolution1() {
        long square = getMaxSquare(tiles);
       return square;
    }

    private long getMaxSquare(List<Position> tiles) {
        long max = 0;
        for (int i = 0; i < tiles.size()-1; i++) {
            for (int j = i+1; j < tiles.size(); j++) {
                var tile1 = tiles.get(i);
                var tile2 = tiles.get(j);
                max = Math.max(max, tile1.squareDist(tile2));
            }
        }
        return max;
    }


    public class Edge {
        public Direction direction;
        public long x1, y1, x2, y2;
        public enum Direction{UP,SIDE};

        public Edge(long x1, long y1, long x2, long y2) {
            this.x1 = Math.min(x1, x2);
            this.x2 = Math.max(x1, x2);
            this.y1 = Math.min(y1, y2);
            this.y2 = Math.max(y1, y2);
            if (x1 == x2) {
                direction = Direction.UP;
            }
            else if (y1 == y2){
                direction = Direction.SIDE;
            }
            else {
                throw new IllegalArgumentException("This is not correct");
            }
        }

        public Edge(Position pos1, Position pos2) {
            this(pos1.row, pos1.col, pos2.row, pos2.col);
        }

        public boolean intersects(Edge other) {
            if (this.direction == other.direction) {
                return false;
            }
            if (this.direction == Direction.UP) {
//                return other.x1 > this.
            }
            else if (this.direction == Direction.SIDE) {

            }
        }


    }

    private long solveSolution2() {
        List<Edge> edges = getOrderedPositions(tiles);

        long max = 0;
        for (int i = 0; i < tiles.size()-1; i++) {
            for (int j = i+1; j < tiles.size(); j++) {
                var tile1 = tiles.get(i);
                var tile2 = tiles.get(j);
                var interTile = new Position(tile1.row, tile2.col);
                var interTile2 = new Position(tile2.row, tile1.col);


                max = Math.max(max, tile1.squareDist(tile2));
            }
        }

        return -1;
    }

    private List<Edge> getOrderedPositions(List<Position> tiles) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            var tile1 = tiles.get(i);
            var tile2 = tiles.get((i+1) % tiles.size());
            edges.add(new Edge(tile1, tile2));
        }
        return edges;
    }
}
