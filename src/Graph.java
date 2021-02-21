import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {

    HashMap<String, HashSet<String>> hashmap;

    public Graph(){
        hashmap = new HashMap<>();
    }

    public void addEdge(String v, String w){
        if(!hashmap.containsKey(v)){
            HashSet<String> set = new HashSet<>();
            set.add(w);
            hashmap.put(v, set);
        }else{
            hashmap.get(v).add(w);
        }
        if(!hashmap.containsKey(w)){
            HashSet<String> set = new HashSet<>();
            set.add(v);
            hashmap.put(w, set);
        }else{
            hashmap.get(w).add(v);
        }
    }

    public int V(){
        return hashmap.size();
    }

    public int E(){
        int edges = 0;
        int loops = 0;
        Set<String> keys = hashmap.keySet();
        for(String key : keys){
            for(String value : hashmap.get(key)){
                if(!key.equals(value)) edges++;
                else loops++;
            }
        }
        return edges/2 + loops;
    }

    public void removeVertex(String v){
        HashSet<String> vertexData = hashmap.get(v);
        for(String w : vertexData){
            hashmap.get(w).remove(v);
        }
        hashmap.remove(v);
    }

    public Iterable<String> vertices(){
        return hashmap.keySet();
    }

    public Iterable<String> adjacentTo(String v){
        return hashmap.get(v);
    }

    public int degree(String v){
        return hashmap.get(v).size();
    }

    public boolean hasVertex(String v){
        return hashmap.containsKey(v);
    }

    public boolean hasEdge(String v, String w){
        return hashmap.get(v).contains(w);
    }

    public void testGraph(){
        Graph graph = this;

//        System.out.printf("Vertices: \n %s\n", graph.vertices());
//        for(String vertex : graph.vertices()){
//            System.out.printf("Vertex: %s,\t\t Neighbours: %s\n",vertex,graph.adjacentTo(vertex));
//        }
        System.out.println("\nTESTING GRAPH");

        System.out.printf("Number of edges: %s\nNumber of vertices: %s \n", graph.E(), graph.V());
        while(true){
            System.out.print("Query Database: ");
            Scanner sc = new Scanner(System.in);
            String query = sc.next();
            if(query.equals("EXIT")){
                return;
            }
            System.out.printf("\n Entries for %s:\n\n",query);
            int numNeighbours = 0;
            for(String neighbour :graph.adjacentTo(query)){
                System.out.printf("* %s\n", neighbour);
                numNeighbours++;
            }
            System.out.printf("\n\tNum entries: %s\n\n",numNeighbours);
        }

    }


}
