import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Controller {

    public final static String SET_A = "a_example.in";
    public final static String SET_B = "b_little_bit_of_everything.in";
    public final static String SET_C = "c_many_ingredients.in";
    public final static String SET_D = "d_many_pizzas.in";
    public final static String SET_E = "e_many_teams.in";

    public final static int SET_A_SEED = 4;
    public final static int SET_B_SEED = 30375584;
    public final static int SET_C_SEED = 253867;
    public final static int SET_D_SEED = 507661;
    public final static int SET_E_SEED = 45086;

    private String set;
    private int seed;
    private int M;
    private int T2;
    private int T3;
    private int T4;

    private Graph graph;
    public HashMap<Integer, HashSet<String>> teams; //<Team, HashSet<Pizza>>
    private boolean[] pizzaAllocated;

    public Controller(String file) throws FileNotFoundException {
        set = file;
        graph = fileToGraph("datasets/"+file);
        resetTeams();
        switch(set) {
            case SET_A: seed = SET_A_SEED;break;
            case SET_B: seed = SET_B_SEED;break;
            case SET_C: seed = SET_C_SEED;break;
            case SET_D: seed = SET_D_SEED;break;
            case SET_E: seed = SET_E_SEED;break;
        }

    }

    public void resetTeams() {
        teams = new HashMap<Integer, HashSet<String>>();
    }
    public static void main(String[] args) {
        try {
            String set = "a";
            switch(args[0]) {
                case "a": set = Controller.SET_A;break;
                case "b": set = Controller.SET_B;break;
                case "c": set = Controller.SET_C;break;
                case "d": set = Controller.SET_D;break;
                case "e": set = Controller.SET_E;break;
            }
//            Controller[] datasets = new Controller[5];
//            datasets[0] = new Controller(Controller.SET_A);
//            datasets[1] = new Controller(Controller.SET_B);
//            datasets[2] = new Controller(Controller.SET_C);
//            datasets[3] = new Controller(Controller.SET_D);
//            datasets[4] = new Controller(Controller.SET_E);
//
//            for (Controller c : datasets) {
//                c.randomlyAllocate(c.seed);
//                c.writeToFile("submission/"+c.set);
//            }
            Controller p = new Controller(set);

            int seed = 0;
            int max_score = 0;
            Timer t = new Timer();
            System.out.println("Set "+set);
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

    public void writeToFile(String file) {
        try {
            File myObj = new File(file);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(teams.size() + "\n");
            Iterable<Integer> team_keys = teams.keySet();
            for (Integer k : team_keys) {
                String line = "";
                line += teams.get(k).size();
                for(String p : teams.get(k)) {
                    line += " "+p;
                }
                myWriter.write(line+ "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
