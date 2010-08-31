package sudokusolver.utilities;

public class JavaMatrix {
	private int[][] matrix = new int[9][];
	public JavaMatrix() {
		for (int i = 0; i < 9; i++) {
			matrix[i] = new int[9];
			for (int j = 0; j < 9; j++) {
				matrix[i][j] = 0;
			}
		}
	}
	
	public void set(int i, int j, int v) {
		matrix[i][j] = v;
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
}
