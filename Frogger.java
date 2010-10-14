// Frogger
// # 534
// tags: dijkstra's, minimax, frogs
// 
// by jamesob

import java.util.*;

public class Frogger {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int numStones = sc.nextInt();
		int scenNum = 1;

		while(numStones != 0) {

			ArrayList<Node> nodes = readCase(sc, numStones);
			System.out.println(String.format("Scenario #%d", scenNum));
			System.out.println(String.format("Frog distance = %.3f\n", 
						                     shortestPath(nodes)));
			numStones = sc.nextInt();
			scenNum++;
		}
	}

	private static ArrayList<Node> readCase(Scanner sc, int numStones) {
		int x; int y;
		ArrayList<Node> nodes = new ArrayList<Node>();

		for(int i=0; i<numStones; i++) {
			x = sc.nextInt();
			y = sc.nextInt();
			nodes.add(new Node(x, y, Float.MAX_VALUE, i));
		}

		nodes.get(0).distFromSource = 0; // this is source
		return nodes;
	}

	private static double shortestPath(ArrayList<Node> nodes) {
		Comparator<Node> comp = new NodeComparator();
		PriorityQueue<Node> pq = new PriorityQueue<Node>(nodes.size(), comp);
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
				newDist = Math.max(w, curr.distFromSource);

				if(newDist < n.distFromSource) {
					pq.remove(n);
					n.distFromSource = newDist;
					pq.add(n);
				}
			}
		}
		return nodes.get(1).distFromSource;
	}

	private static class Node {
		public int x;
		public int y;
		public int id;
		public double distFromSource;

		public Node(int xinit, int yinit, double distS, int ind) {
			x = xinit;
			y = yinit;
			distFromSource = distS;
			id = ind;
		}

		public double getDist(Node other) {
			int xo = other.x;
			int yo = other.y;
			return Math.sqrt((xo-x)*(xo-x) + (yo-y)*(yo-y));
		}
	}

	private static class NodeComparator implements Comparator<Node> {
		public int compare(Node a, Node b) {
			if(a.distFromSource < b.distFromSource) return -1;
			else if(a.distFromSource == b.distFromSource) return 0;
			else return 1;
		}
	}

}
	
