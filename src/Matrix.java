import java.io.File;
import java.io.FileNotFoundException;
import org.ejml.simple.SimpleMatrix;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Matrix {
    private SimpleMatrix matrix;
    private HashMap<String,Integer> indices;

    public Matrix(String filename) throws FileNotFoundException {
        Scanner scFile = new Scanner(new File(filename));

        indices = new HashMap<>();
        Timer indexing = new Timer();
        int arraySize = calculateIndices(filename)+ 1;
        System.out.printf("Indexing took %s seconds\n",indexing.getElapsedSeconds());
        System.out.printf("Array size is %s by %s\n",arraySize,arraySize);
        Timer arrayInitialization = new Timer();
        matrix = new SimpleMatrix(arraySize, arraySize);
        System.out.printf("Matrix successfully initialized in %s seconds\n",arrayInitialization.getElapsedSeconds());
        loadFile(filename);
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

    private void loadFile(String f) throws FileNotFoundException {
        Scanner scFile = new Scanner(new File(f));
        Scanner firstLine = new Scanner(scFile.nextLine());
        int M = firstLine.nextInt();
        int T2 = firstLine.nextInt();
        int T3 = firstLine.nextInt();
        int T4 = firstLine.nextInt();
        for(int pizzaNum = 0; pizzaNum<M; pizzaNum++) {
            System.out.printf("\r Processing pizza %s", pizzaNum);
            Scanner line = new Scanner(scFile.nextLine());
            int numIngredients = line.nextInt();
            for(int i = 0; i < numIngredients; i++){
                String ingredient = line.next();
                matrix.set(pizzaNum,indices.get(ingredient),1);
                matrix.set(indices.get(ingredient),pizzaNum,1);
            }
        }
        System.out.println("Done Processing");
    }

    public SimpleMatrix square(){
        Timer matrixMult = new Timer();
        SimpleMatrix m = matrix.mult(matrix);
        System.out.printf("Matrix multiplied in %s seconds\n",matrixMult.getElapsedSeconds());
        return m;
    }

    public void print(){
        matrix.print();
    }

}
