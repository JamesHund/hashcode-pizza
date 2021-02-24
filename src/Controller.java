import org.ejml.simple.SimpleMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Controller {

    public final static String SET_A = "datasets/a_example";
    public final static String SET_B = "datasets/b_little_bit_of_everything.in";
    public final static String SET_C = "datasets/c_many_ingredients.in";
    public final static String SET_D = "datasets/d_many_pizzas.in";
    public final static String SET_E = "datasets/e_many_teams.in";

    private int M;
    private int T2;
    private int T3;
    private int T4;

    private Graph graph;
    private Matrix matrix;
    public HashMap<Integer, HashSet<String>> teams; //<Team, HashSet<Pizza>>
    private boolean[] pizzaAllocated;

    public Controller(String file) throws FileNotFoundException {
        graph = fileToGraph(file);
        resetTeams();
//        matrix = new Matrix(file);
//        matrix.square();
//
//        pizzaAllocated = new boolean[M];
//        allocatePizzas();
        //totalScore();
        //g//tPath(graph);
    }

    public void resetTeams() {
        teams = new HashMap<Integer, HashSet<String>>();
    }
    public static void main(String[] args) {
        try {
            Controller p = new Controller(Controller.SET_E);
            int seed = 0;
            int max_score = 0;
            Timer t = new Timer();
            while(true) {
                p.randomlyAllocate(seed);
                int score = p.totalScore();
                if (score > max_score) {
                    max_score = score;
                    System.out.printf("[%s] Score of %d in seed %d.\n", t.getElapsedSeconds(), score, seed);
                }
                p.resetTeams();
                seed++;
            }
//            p.teams.entrySet().forEach(entry->{
//                System.out.println(entry.getKey() + " " + entry.getValue());
//            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        System.out.println();
        System.out.println("Finished processing pizzas");
        return graph;
    }

    public void allocatePizzas(){

        int pizzasToAllocate = M;
        HashSet set = new HashSet<String>();
    }

    public int totalScore() { // Untested and untried
        Iterable<Integer> team_keys = teams.keySet();
        int score = 0;
        for(Integer k : team_keys) {
            HashSet<String> pizzas = teams.get(k);
            HashSet<String> ingredients = new HashSet<String>();
            for(String p : pizzas) {
                ingredients.addAll((Collection<? extends String>) graph.adjacentTo(p));
            }
            score += ingredients.size()*ingredients.size();
        }
        return score;
    }

    public void randomlyAllocate(int seed) {
        int pizzaNum = M;
        // ArrayList<Integer> a = new ArrayList<>(M);
        // for (int i = 0; i <= M; i++) {
        //     a.add(i);
        // }
        Set<String> keys = graph.keySet();
        ArrayList<String> a = new ArrayList<>(keys);
        Collections.sort(a);
        Collections.shuffle(a, new Random(seed));
        for(int i = 0; i < T2 && pizzaNum >= 2; i++) {
            teams.put(i,new HashSet<>(a.subList(pizzaNum-2,pizzaNum)));
            pizzaNum-=2;
        }
        for(int i = T2; i < T2+T3 && pizzaNum >= 3; i++) {
            teams.put(i,new HashSet<>(a.subList(pizzaNum-3,pizzaNum)));
            pizzaNum-=3;
        }
        for(int i = T2+T3; i < T2+T3+T4 && pizzaNum >= 4; i++) {
            teams.put(i,new HashSet<>(a.subList(pizzaNum-4,pizzaNum)));
            pizzaNum-=4;
        }
    }
}
