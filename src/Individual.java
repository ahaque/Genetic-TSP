import java.util.ArrayList;
import java.util.Random;


/*
 * An individual is a particular solution to the TSP problem given the
 * input matrix. It is a sequence of cities which denote the solution path.
 */
public class Individual {
	
	public ArrayList<Integer> tour; // Stores the list of city paths
	private int cost; // The cost of the tour
	
	public Individual() {
		tour = new ArrayList<Integer>();
	}
	
	public void generateRandomTour(int numCities) {
		// Possible cities to visit next
		Random rand = new Random();
		ArrayList<Integer> nextPossibleCity = new ArrayList<Integer>();
		for (int j = 0; j < numCities; j++) {
			nextPossibleCity.add(j);
		}
		// Select cities to visit by random
		while (!nextPossibleCity.isEmpty()) {
			int index = rand.nextInt(nextPossibleCity.size());
			tour.add(nextPossibleCity.get(index));
			nextPossibleCity.remove(index);
		}
		calculateCost();
	}
	
	public void calculateCost() {
		int start = tour.get(0);
		for (int i = 0; i < tour.size()-1; i++) {
			cost += GeneticManager.matrix[i][i+1];
		}
		cost += GeneticManager.matrix[tour.get(tour.size()-1)][start];
	}
	
	public void addCityToTour(int city) {
		tour.add(city);
	}
	
	public int getCity(int index) {
		return tour.get(index);
	}
	
	public void setCost(int c) {
		cost = c;
	}
	
	public int getCost() {
		return cost;
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("Individual (" + cost + "): ");
		for (int i : tour) {
			build.append(i + " ");
		}
		//build.append("\n");
		return build.toString();
	}
}
