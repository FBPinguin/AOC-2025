import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class Graph<E> {
    private List<List<E>> graph;
    private List<Direction> directions;
    private DistanceCalculator distanceCalculator;
    private final int boundRow;
    private final int boundCol;

    /**
     * Construct a 2-d graph.
     *
     * @param graph the 2-d graph of type E
     */
    public Graph(List<List<E>> graph) {
        this.graph = graph;
        this.boundCol = graph.get(0).size();
        this.boundRow = graph.size();
        this.directions = List.of(Direction.values());
    }

    enum Direction {
        UP(-1,0),
        DOWN(1,0),
        LEFT(0,-1),
        RIGHT(0,1);
        
        int row;
        int col;

        Direction(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        Position add(Position pos) {
            return new Position(pos.row + this.row, pos.col + this.col);
        }
    }
    
    interface DistanceCalculator {
        <E> long distance(long currentDistance, E goTo);
    }

    record Position(int row, int col) {
        public boolean isValid(int boundRow, int boundCol) {
            if (row < 0 || row >= boundRow) {
                return false;
            }
            if (col < 0 || col >= boundCol) {
                return false;
            }
            return true;
        }
    }

    private E getNode(Position position) {
        return graph.get(position.row).get(position.row);
    }


    public void setDistanceCalculator(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }


    record PosDist(Position position, long distance) {
    }

    public long distanceFromTo(Position startPos, Position endPos) {


        PriorityQueue<PosDist> priorityQueue =
                new PriorityQueue<>((o1, o2) -> (int) (o1.distance() - o2.distance()));

        priorityQueue.add(new PosDist(startPos, distanceCalculator.distance(0, getNode(startPos))));

        HashSet<Position> seen = new HashSet<>(List.of(startPos));

        long shortedDistance = findShortestRoute(priorityQueue, seen, endPos);


        return shortedDistance;
    }

    private long findShortestRoute(PriorityQueue<PosDist> priorityQueue, HashSet<Position> seen, Position endPos) {
        while (!priorityQueue.isEmpty()) {
            var smallestPos = priorityQueue.remove();
            for (Direction direction : directions) {
                var newPos = direction.add(smallestPos.position);

                if (seen.contains(newPos)) {
                    continue;
                }

                var newDistance = distanceCalculator.distance(smallestPos.distance, newPos);

                if (endPos.equals(newPos)) {
                    return newDistance;
                }

                var newPosDir = new PosDist(newPos, newDistance);
                priorityQueue.add(newPosDir);
            }
        }
        return -1;
    }


}
