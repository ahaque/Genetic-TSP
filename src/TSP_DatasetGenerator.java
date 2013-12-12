import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * Generates a TSP dataset that satisfies the triangle inequality
 * Generates n points in the X-Y plane
 * Calculates the Euclidean distance to all other points
 * @author Albert Haque
 * @date November 2013
 */
public class TSP_DatasetGenerator {
	
	private final int NUM_CITIES = 10000; // Number of cities to generate
	private ArrayList<Point> cities; // List of city coordinates
	public int[][] matrix; // Distance matrix
	public final int MAX_EUCLID = 10000; // Coordinate space max X and max Y
	Random rand; // Random numbers
	private final String OUTPUT_FILENAME = "Albert_TSP1000.txt";
	
	public static void main(String[] args) {
		TSP_DatasetGenerator tsp = new TSP_DatasetGenerator();
		tsp.generateCoordinates();
		tsp.calculateDistanceMatrix();
		tsp.writeMatrixToFile();
		System.out.println(tsp.matrix[tsp.NUM_CITIES-1][tsp.NUM_CITIES-2]);
	}
	
	public TSP_DatasetGenerator() {
		cities = new ArrayList<Point>();
		matrix = new int[NUM_CITIES][NUM_CITIES];
		rand = new Random();
	}
	
	public void generateCoordinates() {
		for (int i = 0; i < NUM_CITIES; i++) {
			Point p = new Point(rand.nextInt(MAX_EUCLID),rand.nextInt(MAX_EUCLID));
			cities.add(p);
		}
	}
	
	public void calculateDistanceMatrix() {
		for (int i = 0; i < NUM_CITIES; i++) {
			for (int j = 0; j < NUM_CITIES; j++) {
				if (i == j) {
					matrix[i][j] = 0;
				}
				else {
					int a_x = Math.abs(cities.get(i).getX() - cities.get(j).getX());
					int b_y = Math.abs(cities.get(i).getY() - cities.get(j).getY());
					matrix[i][j] = (int) Math.sqrt(Math.pow(a_x, 2)+Math.pow(b_y,2));
				}
			}
		}
	}
	
	public void writeMatrixToFile() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(OUTPUT_FILENAME));
			for (int i = 0; i < NUM_CITIES; i++) {
				for (int j = 0; j < NUM_CITIES-1; j++) {
					bw.write(matrix[i][j] + " ");
				}
				bw.write(matrix[i][NUM_CITIES - 1] + "\n");
			}
		} catch (IOException e) {
			System.err.println("\tERROR: Input file not found");
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class Point {
	private int x;
	private int y;
	
	public Point() {
		x = -1;
		y = -1;
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public int getX() { return x; }
	public int getY() { return y; }
}