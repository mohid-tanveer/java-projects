import java.io.InputStream;
import java.util.*;

public class DijkstrasAlgorithm {
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("What file do you want to read? ");
        String filename = scan.nextLine();
        processFile(filename);
    }

    public static void dijkstra(Graph g, String start, String finish)
    {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriQueue<String, Integer> pq = new PriQueue<String, Integer>(true);

        // Iterate through the vertices of g and put the dist values as the max int value
        //  to represent infinity and prev values as null to represent undefined.
        for (String s : g.getVertices()) {
            dist.put(s, Integer.MAX_VALUE);
            prev.put(s, null);
        }

        // Add the start vertext to the dist map with a dist of 0 and add it to the pq.
        dist.put(start, 0);
        pq.add(start, 0);

        while (!pq.isEmpty()) {
            // Visit the vertex at the top of the pq.
            String u = pq.remove();
            System.out.println();
            System.out.println("Visiting vertex " + u);
            // If the vertex is the finishing vertex break.
            if (u.equals(finish)) {
                break;
            }
            // Iterate through all of the vertexes adjacent to the current vertext
            for (String v : g.getAdjacentVerticesFrom(u)) {
                int alt = dist.get(u) + g.getWeight(u, v);
                // If this new dist is lower than the current one in the dist map replace it
                // and add it to the pq or if it is already in the pq, alter the priority in 
                // the pq to this new value.
                if (alt < dist.get(v)) {
                    String altText;
                    if (dist.get(v) == Integer.MAX_VALUE) {
                        altText = "∞";
                    }
                    else {
                        altText = Integer.toString(dist.get(v));
                    }
                    dist.put(v, alt);
                    System.out.println("    Updating dist[" + v + "] from " + altText + " to " + alt);
                    prev.put(v, u);
                    if (pq.contains(v)) {
                        pq.changePriority(v, alt);
                    }
                    else {
                        pq.add(v, alt);
                    }
                }
            }
        }
        String currVertex = finish;
        String path = "";
        // Iterate from the finish vertex to the start vertex in the prev map to find the path
        // and add these vertexs to a pathReverse ArrayList.
        ArrayList<String> pathReverse = new ArrayList<>();
        while (!currVertex.equals(start)) {
            pathReverse.add(currVertex);
            currVertex = prev.get(currVertex);
        }
        pathReverse.add(start);
        // Decrement through the array list to print the path from start to finish.
        for (int i = pathReverse.size(); i > 0; i--) {
            path += (pathReverse.get(i-1) + " ");
        }
        System.out.println();
        System.out.println("Shortest path is : " + path);
        int finalPathLength = dist.get(finish);
        System.out.println("Distance is: " + finalPathLength);
        System.out.println();

        // Print the final dist map.
        System.out.println("Final dist map:");
        for (String key : dist.keySet()) {
            String value;
            if (dist.get(key) == Integer.MAX_VALUE) {
                value = "∞";
            }
            else {
                value = Integer.toString(dist.get(key));
            }
            System.out.println(key + ": " + value);
        }

        // Print the final prev map
        System.out.println();
        System.out.println("Final prev map:");
        for (String key : prev.keySet()) {
            String value;
            if (prev.get(key) == null) {
                value = "undefined";
            }
            else {
                value = prev.get(key);
            }
            System.out.println(key + ": " + value);
        }
    }

    /**
     * Read the file specified to add proper items to the word frequencies.
     */
    private static void processFile(String filename)
    {
        InputStream is = DijkstrasAlgorithm.class.getResourceAsStream(filename);
        if (is == null) {
            System.err.println("Bad filename: " + filename);
            System.exit(1);
        }
        Scanner scan = new Scanner(is);

        // Make a blank graph.
        Graph g = new Graph();
        // Make a boolean to represent if the graph is directed or not.
        Boolean isDirected = false;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] words = line.split(" ");

            // If statements to check if the graph is directed or not.
            if (words[0].equals("undirected")) {
                isDirected = false;
            }
            else if (words[0].equals("directed")) {
                isDirected = true;
            }
            // Adds the vertexes to the graph.
            else if (words[0].equals("vertex")) {
                g.addVertex(words[1]);
            }
            // Adds the edges. only one way if directed, two ways if undirected.
            else if ((words[0].equals("edge") && isDirected == true)) {
                g.addEdge(words[1], words[2], Integer.parseInt(words[3]));
            }
            else if ((words[0].equals("edge") && isDirected == false)) {
                g.addEdge(words[1], words[2], Integer.parseInt(words[3]));
                g.addEdge(words[2], words[1], Integer.parseInt(words[3]));
            }
            // Runs djiksta's from the first given vertex to the second given vertex.
            else if (words[0].equals("dijkstra")) {
                dijkstra(g, words[1], words[2]);
            }
        }
        scan.close();
    }
}

