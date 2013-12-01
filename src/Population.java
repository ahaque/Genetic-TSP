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
			
			System.out.println(ind);
		}
	}
}
