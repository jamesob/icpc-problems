// Frogger
// # 534
// tags: dijkstra's, minimax, frogs
// 
// by jamesob
//    thanks to Vo for spotting an all-important mistake

import java.util.*;

public class Frogger {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numStones = sc.nextInt();
        int scenNum = 1;

        while(numStones != 0) {
            ArrayList<Node> nodes = readCase(sc, numStones);
            System.out.printf("Scenario #%d\n", scenNum);
            System.out.printf("Frog Distance = %.3f\n\n", shortestPath(nodes));

            numStones = sc.nextInt();
            scenNum++;
        }
    }

    private static ArrayList<Node> readCase(Scanner sc, int numStones) {
        int x, y;
        ArrayList<Node> nodes = new ArrayList<Node>();

        for(int i=0; i<numStones; i++) {
            x = sc.nextInt();
            y = sc.nextInt();
            nodes.add(new Node(x, y, Float.MAX_VALUE, i));
        }

        nodes.get(0).maxLeap = 0; // this is source
        return nodes;
    }

    private static double shortestPath(ArrayList<Node> nodes) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>(nodes);
        Node curr;
        double w;
        double newDist;

        for(Node n : nodes)
            pq.add(n);

        while(pq.size() > 0) { // Dijky's 
            curr = pq.poll();
            for(Node n : nodes) {
                if(n.id == curr.id) // skip curr
                    continue; 
                w       = n.getDist(curr);
                newDist = Math.max(w, curr.maxLeap);

                if(newDist < n.maxLeap) {
                    pq.remove(n);
                    n.maxLeap = newDist;
                    pq.add(n);
                }
            }
        }
        return nodes.get(1).maxLeap;
    }

    private static class Node implements Comparable<Node> {
        public int x, y, id;
        public double maxLeap;

        public Node(int xinit, int yinit, double distS, int ind) {
            x = xinit;
            y = yinit;
            maxLeap = distS;
            id = ind;
        }

        public double getDist(Node other) {
            int xo = other.x;
            int yo = other.y;
            return Math.sqrt((xo-x)*(xo-x) + (yo-y)*(yo-y));
        }

        public int compareTo(Node other) {
            if(maxLeap < other.maxLeap) return -1;
            else if(maxLeap == other.maxLeap) return 0;
            else return 1;
        }
    }
}
    
