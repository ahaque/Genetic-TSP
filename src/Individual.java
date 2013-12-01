import java.util.ArrayList;


/*
 * An individual is a particular solution to the TSP problem given the
 * input matrix. It is a sequence of cities which denote the solution path.
 */
public class Individual {
	
	private ArrayList<Integer> tour; // Stores the list of city paths
	private int cost; // The cost of the tour
	
	public Individual(int numCities) {
		tour = new ArrayList<Integer>();
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

}
