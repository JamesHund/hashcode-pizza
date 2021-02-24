import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Matrix {
    private boolean[][] array;
    private HashMap<String,Integer> indices;

    public Matrix(String filename) throws FileNotFoundException {
        Scanner scFile = new Scanner(new File(filename));

        indices = new HashMap<>();
        Timer indexing = new Timer();
        int arraySize = calculateIndices(filename) + 1;
        System.out.printf("Indexing took %s seconds\n",indexing.getElapsedSeconds());
        System.out.printf("Array size is %s by %s\n",arraySize,arraySize);
        Timer arrayInitialization = new Timer();
        array = new boolean[arraySize][arraySize];
        System.out.printf("Array successfully initialized in %s seconds\n",arrayInitialization.getElapsedSeconds());

//        Scanner firstLine = new Scanner(scFile.nextLine());
//        int numPizzas = firstLine.nextInt();
//        int currentIndex = numPizzas;
//        Scanner line;
//        for(int pizzaNum = 0; pizzaNum<numPizzas; pizzaNum++) {
//            System.out.printf("\r Processing pizza %s", pizzaNum);
//            line = new Scanner(scFile.nextLine());
//            int numIngredients = line.nextInt();
//            for(int i = 0; i < numIngredients; i++){
//
//            }
//        }
    }

    private int calculateIndices(String filename)throws FileNotFoundException{
        Scanner scFile = new Scanner(new File(filename));

        Scanner firstLine = new Scanner(scFile.nextLine());
        int numPizzas = firstLine.nextInt();

        int currentIndex = numPizzas;
        for(int pizzaNum = 0; pizzaNum<numPizzas; pizzaNum++) {
            Scanner scLine = new Scanner(scFile.nextLine());
            int numIngredients = scLine.nextInt();
            for(int i = 0; i < numIngredients; i++){
                String currentIngredient = scLine.next();
                if(indices.get(currentIngredient) == null){
                    indices.put(currentIngredient, currentIndex);
                    currentIndex++;
                }
            }
        }
        return currentIndex;
    }

    public Matrix booleanSquare(){
        return this;
    }

    //Same as the boolean square but each new entry shows the number of common entries instead of just 1 or 0
    public Matrix weightedBooleanSquare(){
        return this;
    }

}
