import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Controller {

    private int M;
    private int T2;
    private int T3;
    private int T4;

    private Graph graph;
    private HashMap<String, HashSet<String>> teams; //<Team, HashSet<Pizza>>
    private boolean[] pizzaAllocated;

    public Controller(String file) throws FileNotFoundException {
        //graph = fileToGraph(file);
        Matrix matrix = new Matrix(file);
        pizzaAllocated = new boolean[M];
        allocatePizzas();
        //totalScore();
        //graph.testGraph();
        Pathfinder.testPath(graph);
    }

    public static void main(String[] args) {
        try {
            Controller p = new Controller("datasets/d_many_pizzas.in");
            //Controller p = new Controller("datasets/b_little_bit_of_everything.in");

        } catch(FileNotFoundException e) {

        }

    }

    public Graph fileToGraph(String f) throws FileNotFoundException {
        Scanner scFile = new Scanner(new File(f));

        Scanner firstLine = new Scanner(scFile.nextLine());
        this.M = firstLine.nextInt();
        this.T2 = firstLine.nextInt();
        this.T3 = firstLine.nextInt();
        this.T4 = firstLine.nextInt();

        Graph graph = new Graph();
        for(int pizzaNum = 0; pizzaNum<this.M; pizzaNum++) {
            System.out.printf("\r Processing pizza %s", pizzaNum);
            Scanner line = new Scanner(scFile.nextLine());
            int numIngredients = line.nextInt();
            for(int i = 0; i < numIngredients; i++){
                graph.addEdge(Integer.toString(pizzaNum),line.next());
            }
        }
        return graph;
    }

    public void allocatePizzas(){

        int pizzasToAllocate = M;
        HashSet set = new HashSet<String>();
    }

    private int totalScore() { // Untested and untried
        Iterable<String> team_keys = teams.keySet();
        int score = 0;
        for(String k : team_keys) {
            HashSet<String> pizzas = teams.get(k);
            HashSet<String> ingredients = new HashSet<String>();
            for(String p : pizzas) {
                ingredients.addAll((Collection<? extends String>) graph.adjacentTo(p));
            }
            score += ingredients.size()^2;
        }
        return score;
    }
}