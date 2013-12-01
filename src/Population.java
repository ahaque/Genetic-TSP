import java.util.ArrayList;
import java.util.Random;

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
	
	ArrayList<Individual> sorted;
	
	public Population evolve() {
		// STEP 1: Select the best fit (elitism)
		sorted = new ArrayList<Individual>();
		Population nextGenPop = new Population(numCities);
		int populationSpaceAvailable = individuals.size();
		
		for (int i = 0; i < GeneticManager.POPULATION_SIZE; i++) {
			int bestCost = Integer.MAX_VALUE;
			int bestIndex = -1;
			for (int j = 0; j < individuals.size(); j++) {
				if (individuals.get(j).getCost() < bestCost) {
					bestCost = individuals.get(j).getCost();
					bestIndex = j;
				}
			}
			sorted.add(individuals.get(bestIndex));
			individuals.remove(bestIndex);
		}
		// Add "top" individuals to the next generation
		int numElite = (int) (GeneticManager.POPULATION_SIZE * GeneticManager.ELITE_PERCENT);
		for (int i = 0; i < numElite; i++) {
			if (Math.random() < GeneticManager.MUTATION_RATE) {
				nextGenPop.individuals.add(mutate(sorted.get(i)));;
			} else {
				nextGenPop.individuals.add(sorted.get(i));
			}
			populationSpaceAvailable--;
		}
		
		// STEP 2: Select 2 parents from population and generate children
		while (populationSpaceAvailable > 0) {
			// STEP 2: Create offspring given two parents (genetic crossover)
			Individual p1 = selectParentViaTournament();
			Individual p2 = selectParentViaTournament();
			Individual child = crossover(p1, p2);
			
			// STEP 3: Add mutations
			if (Math.random() < GeneticManager.MUTATION_RATE) {
				mutate(child);
			}
			
			child.calculateCost();
			nextGenPop.individuals.add(child);
			populationSpaceAvailable--;
			if (GeneticManager.printNewChildren == true) {
				System.out.println(child);
			}
		}
		return nextGenPop;
	}
	
	public Individual mutate(Individual ind) {
		int index1 = (int)(Math.random()*numCities);
		int index2 = (int)(Math.random()*numCities);
		int storage = ind.getCity(index1);
		//System.out.println("MUTATION: Swapping t["+index1+"]:"+storage+" with t["+index2+"]:"+ind.tour.get(index2));
		ind.tour.set(index1, ind.tour.get(index2));
		ind.tour.set(index2, storage);
		return ind;
	}
	
	public double averageFitness() {
		long sum = 0L;
		for (Individual ind : individuals) {
			sum += ind.getCost();
		}
		return (double) (sum/GeneticManager.POPULATION_SIZE);
	}
	
	public Individual crossover(Individual p1, Individual p2) {
		Individual child = new Individual();
		// Generate a subtour from parent 1
		int index1 = (int)(Math.random()*numCities);
		int index2 = (int)(Math.random()*numCities);
		int start = Math.min(index1, index2);
		int end = Math.max(index1, index2);
		// Add the subtour from parent 1
		for (int i = start; i < end; i++) {
			child.addCityToTour(p1.getCity(i));
		}
		// Add the remaining cities from parent 2
		for (int j = 0; j < numCities; j++) {
			if (!child.tour.contains(p2.getCity(j))) {
				child.addCityToTour(p2.getCity(j));
			}
		}
		// Small chance of cloning the better parent
		if (Math.random() < GeneticManager.CLONE_RATE) {
			if (p1.getCost() < p2.getCost()) {
				return p1;
			}
			else {
				return p2;
			}
		}
		
		return child;
	}

	public Individual selectParentViaTournament() {
		Random rand = new Random();
		// Select individuals randomly from the population
		// WITH a bias towards selecting high fitness individuals
		if (rand.nextDouble() < GeneticManager.ELITE_PARENT_RATE) {
			int numElite = (int) (GeneticManager.ELITE_PARENT_RATE * GeneticManager.POPULATION_SIZE);
			return sorted.get(rand.nextInt(numElite));
		}
		
		// Otherwise select a parent from the general population with a uniform distribution
		ArrayList<Individual> tournamentPopulation = new ArrayList<Individual>();
		for (int i = 0; i < GeneticManager.TOURNAMENT_SIZE; i++) {
			int randIndex = (int) (Math.random()*sorted.size());
			tournamentPopulation.add(sorted.get(randIndex));
		}
		//System.out.println("BEST: "+getBestIndividual(tournamentPopulation));
		return getBestIndividual(tournamentPopulation);
	}
	
	public Individual getBestIndividualInPop() {
		if (sorted != null) {
			return sorted.get(0);
		}
		return getBestIndividual(this.individuals);
	}
	
	public Individual getBestIndividual(ArrayList<Individual> pop) {
		int minCost = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < pop.size(); i++) {
			if (pop.get(i).getCost() < minCost) {
				minIndex = i;
				minCost = pop.get(i).getCost();
			}
		}
		return pop.get(minIndex);
	}
}
