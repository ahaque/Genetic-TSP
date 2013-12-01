import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GeneticManager {
	
	// The input file is a SPACE delimited distance matrix
	// For this implementation, the triangle inequality does not need to be satisfied
	public static final String INPUT_FILE = "input1.txt";
	
	// Input data
	public static int[][] matrix;
	public static int numCities;
	
	// Debugging
	public static boolean printNewChildren = false;
	
	// Genetic Algorithm Paramaters
	public static final int POPULATION_SIZE = 200;
	public static final int NUM_EVOLUTION_ITERATIONS = 1000;
	// The best individual will always survive to the next population
	public static boolean keepBestFit = true;
	// When selecting two parents, we want the "fittest" parents to reproduce
	// This is done by randomly selecting X individuals in the population and 
	// selecting the top two from this sub-population. The size of the sub-population is tournament size
	// This must be less than POPULATION_SIZE
	public static int TOURNAMENT_SIZE = 10; 
	// The probability a specific individual undergoes a single mutation
	public static double MUTATION_RATE = 0.05;
	// Probability of skippnig crossover and using the best parent
	public static double CLONE_RATE = 0.0;
	
	
	public static void main(String[] args) {
		checkParameterErrors();
		GeneticManager gm = new GeneticManager();
		try {
			gm.readDistanceMatrix();
		} catch (IOException e) {
			System.err.println(e);
		}
		Population pop = new Population(numCities);
		pop.initializePopulationRandomly(POPULATION_SIZE);
		for (int i = 0; i < NUM_EVOLUTION_ITERATIONS; i++) {
			System.out.println("ITERATION " + i + ". AVERAGE FITNESS: " + pop.averageFitness());
			pop = pop.evolve();
		}
		
	}
		
	public GeneticManager() {
		
	}
	
	public void initializePopulation() {
		
	}
	
	public void readDistanceMatrix() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
		
		StringBuilder build = new StringBuilder();
		// Find out how many cities there are in the file
		numCities = 0;
		while (!build.append(br.readLine()).toString().equalsIgnoreCase("null")) {
			numCities++;
			build.setLength(0); // Clears the buffer
		}
		matrix = new int[numCities][numCities];
		// Reset reader to the start of the file
		br = new BufferedReader(new FileReader(INPUT_FILE));
		// Populate the distance matrix
		int currentCity = 0;
		build = new StringBuilder();
		while (!build.append(br.readLine()).toString().equalsIgnoreCase("null")) {
			String[] tokens = build.toString().split(" ");
			for (int i = 0; i < numCities; i++) {
				matrix[currentCity][i] = Integer.parseInt(tokens[i]);
			}
			currentCity++;
			build.setLength(0); // Clears the buffer
		}
	}
	
	public void printMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void checkParameterErrors() {
		boolean error = false;
		if (POPULATION_SIZE < TOURNAMENT_SIZE) {
			System.err.println("ERROR: Tournament size must be less than population size.");
			error = true;
		}
		if (POPULATION_SIZE < 0) {
			System.err.println("ERROR: Population size must be greater than zero");
			error = true;
		}
		if (TOURNAMENT_SIZE < 0) {
			System.err.println("ERROR: Tournament size must be greater than zero");
			error = true;
		}
		if (error == true) {
			System.exit(1);
		}
	}
}
