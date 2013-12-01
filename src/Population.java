import java.util.ArrayList;

public class Population {
	public ArrayList<Individual> individuals = new ArrayList<Individual>();
	
	private int numCities;
	
	public Population(int numCities) {
		this.numCities = numCities;
	}
	
	public void initializePopulationRandomly(int numIndividuals) {
		for (int i = 0; i < numIndividuals; i++) {
			Individual ind = new Individual();
			ind.generateRandomTour(numCities);
			individuals.add(ind);
		}
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		for (Individual ind : individuals) {
			build.append(ind.toString() + "\n");
		}
		return build.toString();
	}
	
	/*
	 * This is where we create the next generation of the population
	 * Step 1: Select the best fit (elitism)
	 * Step 2: Create offspring given two parents (genetic crossover)
	 * Step 3: Add mutations to some of the new children (mutation)
	 */
	public void evolve() {
		int populationSpaceAvailable = individuals.size();
		ArrayList<Individual> nextGeneration = new ArrayList<Individual>();
		if (GeneticManager.keepBestFit == true) {
			nextGeneration.add(getBestIndividual());
			populationSpaceAvailable--;
		}
	}
	
	public Individual getBestIndividual() {
		int minCost = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < individuals.size(); i++) {
			if (individuals.get(i).getCost() < minCost) {
				minIndex = i;
				minCost = individuals.get(i).getCost();
			}
		}
		return individuals.get(minIndex);
	}
}
