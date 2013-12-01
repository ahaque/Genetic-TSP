import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GeneticManager {
	
	public static final String INPUT_FILE = "input1.txt";
	public int[][] matrix;
	
	public static void main(String[] args) {
		GeneticManager gm = new GeneticManager();
		try {
			gm.readDistanceMatrix();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
		
	public GeneticManager() {
		
	}
	
	public void readDistanceMatrix() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
		
		StringBuilder build = new StringBuilder();
		// Find out how many cities there are in the file
		int numCities = 0;
		while (!build.append(br.readLine()).toString().equalsIgnoreCase("null")) {
			numCities++;
			build.setLength(0); // Clears the buffer
		}
		matrix = new int[numCities][numCities];
		// Reset reader to the start of the file
		br = new BufferedReader(new FileReader(INPUT_FILE));
		// Populate the distance matrix
		while (!build.append(br.readLine()).toString().equalsIgnoreCase("null")) {
			
			build.setLength(0); // Clears the buffer
		}
	}
}
