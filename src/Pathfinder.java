import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class Pathfinder {

    Graph graph;
    String source;
    HashMap<String,Integer> level;
    HashMap<String, String> parent;

    public Pathfinder(Graph graph, String source){
        this.graph = graph;
        this.source = source;
        level = new HashMap<>();
        parent = new HashMap<>();
        BFS();
    }

    private void BFS(){
        LinkedList<String> frontier = new LinkedList<>();

        level.put(source, 0);
        parent.put(source, null);
        frontier.add(source);

        int i = 1;

        while(!frontier.isEmpty()) {
            LinkedList<String> next = new LinkedList<>();
            for (String vertex : frontier) {
                for(String neighbour : graph.adjacentTo(vertex)){
                    if(!level.containsKey(neighbour)){
                        level.put(neighbour, i);
                        parent.put(neighbour, vertex);
                        next.add(neighbour);
                    }
                }
            }
            frontier = next;
            i++;
        }

    }

    public int distanceTo(String destination){
        return level.get(destination);
    }

    public Stack<String> pathTo(String destination){
        String currentParent = destination;
        Stack<String> stack = new Stack<>();

        while(currentParent != null){
            stack.push(currentParent);
            currentParent = parent.get(currentParent);
        }

        return stack;
    }

    public static void testPath(Graph graph){

        while(true){
            System.out.print("\nEnter source node: ");

            String input = new Scanner(System.in).next();

            if(graph.hasVertex(input)) {
                Pathfinder p = new Pathfinder(graph, input);
                System.out.print("Enter destination node: ");
                String dest = new Scanner(System.in).next();
                if(graph.hasVertex(dest)) {
                    Stack<String> path = p.pathTo(dest);
                    while(!path.isEmpty()) {
                        String vertex = path.pop();
                            String arrow = "";
                            if (!vertex.equals(dest)) {
                                arrow = "-->";
                            }
                            System.out.printf("%s %s ", vertex, arrow);
                        }
                    System.out.println();
                    System.out.printf("Distance: %s\n",p.distanceTo(dest));
                }else{
                    System.out.println("Not a valid destination");
                }
            }
        }


    }

}
