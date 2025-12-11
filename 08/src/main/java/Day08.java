import java.util.*;

public class Day08 {

    public Day08() {
    }

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();
        var day = new Day08();
        day.loadData();
        System.out.println(day.solveSolution1());
        day = new Day08();
        day.loadData();
        System.out.println(day.solveSolution2());
        var timeDiff = System.currentTimeMillis() - startTime;
        System.out.println(timeDiff + "ms");

    }




    private void loadData() {
        loadData("string.txt");
    }

    record Position(int x, int y, int z) {
        public double distance(Position other) {
            long dx = this.x-other.x;
            long dy = this.y-other.y;
            long dz = this.z-other.z;
            return Math.sqrt(dx*dx+dy*dy+dz*dz);
        }

    };

    List<Position> junctions = new ArrayList<>();

    private void loadData(String fileName) {
        var input = new Solution(2025, 7).readInput("input.txt");
        var inputIter = input.iterator();

        for (Iterator<String> it = inputIter; it.hasNext(); ) {
            String s = it.next();
            String[] numbers = s.split(",");
            int xCoord = Integer.parseInt(numbers[0]);
            int yCoord = Integer.parseInt(numbers[1]);
            int zCoord = Integer.parseInt(numbers[2]);
            junctions.add(new Position(xCoord, yCoord, zCoord));
        }

    }

    record distancePair(double distance, Position[] pair){}

    private long solveSolution1() {
        List<distancePair> distances = getAllDistances(junctions);
//        List<Map.Entry<Position[], Double>> sortedDistances;
        distances = distances.stream().sorted(Comparator.comparingDouble(i -> i.distance)).toList();

        Map<Position, Position> leaders = createLeaderMap(junctions);

        int i = 0;
        for (distancePair doubleEntry : distances) {
            if (i == 1000) break;
            var pos1 = doubleEntry.pair()[0];
            var pos2 = doubleEntry.pair()[1];
            var leader1 = getLeader(leaders, pos1);
            var leader2 = getLeader(leaders, pos2);
            //            if (leader1 == leader2) continue;
            if (leader1 != leader2) leaders.put(leader2, leader1);
            i++;
        }

        Map<Position, Long> unionSizes = createUnionSizes(leaders);

        List<Long> unionSize = unionSizes.values().stream().sorted((val1, val2) -> (int)(val2 - val1)).toList();

        long acc = 1;

        for (int j = 0; j < unionSize.size(); j++) {
            if (j == 3) break;
            acc *= unionSize.get(j);
        }

       return acc;
    }

    private Map<Position, Long> createUnionSizes(Map<Position, Position> leaders) {
        Map<Position, Long> sizeMap = new HashMap<>();
        var leadersEntrySet = leaders.keySet();

        for (Position position : leadersEntrySet) {
            var leader = getLeader(leaders, position);
            if (sizeMap.containsKey(leader)) {
                sizeMap.put(leader, sizeMap.get(leader) + 1);
            }
            else {
                sizeMap.put(leader, 1L);
            }
        }


        return sizeMap;
    }

    private Position getLeader(Map<Position, Position> leaders, Position pos1) {
        var leader = pos1;
        while (leaders.get(leader) != null) {
            leader = leaders.get(leader);
        }
        return leader;
    }

    private Map<Position, Position> createLeaderMap(List<Position> junctions) {
        Map<Position, Position> map = new HashMap<>();
        for (Position junction : junctions) {
            map.put(junction, null);
        }
        return map;
    }

    private Map<Position, List<Position>> createUnionMap(List<Position> junctions) {
        Map<Position, List<Position>> map = new HashMap<>();
        for (Position junction : junctions) {
            map.put(junction, new ArrayList<>());
        }
        return map;
    }

    private List<distancePair> getAllDistances(List<Position> junctions) {
//        var map = new HashMap<Position[], Double>();
        List<distancePair> map = new ArrayList<>();

        for (int i = 0; i < junctions.size()-1; i++) {
            for (int j = i+1; j < junctions.size(); j++) {
                var pos1 = junctions.get(i);
                var pos2 = junctions.get(j);

                map.add(new distancePair(pos1.distance(pos2),new Position[] {pos1, pos2}));
            }
        }

        return map;
    }


    private long solveSolution2() {
        List<distancePair> distances = getAllDistances(junctions);
        //        List<Map.Entry<Position[], Double>> sortedDistances;
        distances = distances.stream().sorted(Comparator.comparingDouble(i -> i.distance)).toList();

        Map<Position, Position> leaders = createLeaderMap(junctions);
        Position last1 = null;
        Position last2 = null;

        for (distancePair doubleEntry : distances) {
            var pos1 = doubleEntry.pair()[0];
            var pos2 = doubleEntry.pair()[1];
            var leader1 = getLeader(leaders, pos1);
            var leader2 = getLeader(leaders, pos2);
            if (leader1 != leader2) {
                leaders.put(leader2, leader1);
                last1 = pos1;
                last2 = pos2;
            }

        }

        Map<Position, Long> unionSizes = createUnionSizes(leaders);

        List<Long> unionSize = unionSizes.values().stream().sorted((val1, val2) -> (int)(val2 - val1)).toList();

        long acc = 1;

        for (int j = 0; j < unionSize.size(); j++) {
            if (j == 3) break;
            acc *= unionSize.get(j);
        }

        return (long)last1.x * last2.x;


    }




}
