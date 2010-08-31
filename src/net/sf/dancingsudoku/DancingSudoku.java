package net.sf.dancingsudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Dancing Sudoku
 *
 * Copyright (C) 2005 Daniel Seiler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author <a href=mailto:daniel.seiler@truesolutions.ch>Daniel Seiler</a>
 */
public class DancingSudoku implements SolutionHandlerIF {

	// the matrix has the following format
	// _______________________________________________________________________________________________
	//         |           1            |           2            |  |            n           |        |
	//         |r1r2..r9c1c2..c9b1b2..b9|r1r2..r9c1c2..c9b1b2..b9|..|r1r2..r9c1c2..c9b1b2..b9|123..n*n|
	// --------|------------------------|------------------------|--|------------------------|--------|
	// 1 -> 1,1|
	// 1 -> 1,2|
	//
	
	private Header root = null;
	private List solution = new ArrayList();
	private boolean shouldContinue = true;
	private int solutionCounter = 0;
	private int b; // number of blocks per row
	private int n; // number of columns/rows/blocks
	private int threeN;
	private int twoN;
	private int threeNsquare;
	private int fourNsquare;
	private int nSquare;
	private SolutionHandlerIF solutionHandler;
	private int numberOfNonZeros = 0;
	private int searchCount = 0;
	private int[][] puzzle;
	
	public static void main(String[] args) {	
		
		if(args.length == 0) {
			usage();
		}		
		if(args[0].equals("solve")) {		
			List puzzlesToSolve = loadPuzzles("SudokusToSolve.txt");
			for(Iterator it = puzzlesToSolve.iterator(); it.hasNext();) {
				int[][] puzzle = (int[][])it.next();
				DancingSudoku solver = new DancingSudoku((int)Math.sqrt(puzzle.length));
				//int numberOfLowHangigFruits = solver.fillInLowHangingFruits(puzzle);
				//logger.info("filled in low hanging fruits: " + numberOfLowHangigFruits);
				solver.run(puzzle);
			}			
		} else if(args[0].equals("create")) {
			if(args.length == 1) {
				List puzzlesToCreate = loadPuzzles("SudokusToCreate.txt");
				for(Iterator it = puzzlesToCreate.iterator(); it.hasNext();) {
					int[][] puzzle = (int[][])it.next();
					DancingSudoku solver = new DancingSudoku((int)Math.sqrt(puzzle.length));
					//logger.info("trying to create Sudoku from loaded matrix:\n ");
					//logger.info(solver.getMatrixAsString(puzzle));
					int[][] sudoku = solver.createRandomSudoku(puzzle,null,null);
					//logger.info("found final sudoku with number of non zeros: "+solver.getNumberOfNonZeros());
					//logger.info(solver.getMatrixAsString(sudoku));
				}
			} else if(args.length == 2) {
				int dimension = new Integer(args[1]).intValue();
				DancingSudoku solver = new DancingSudoku(dimension);
				int[][] randomMatrix = solver.createRandomFullCoverageMatrix();
				//logger.info("creating sudoku from random matrix:\n ");
				//logger.info(solver.getMatrixAsString(randomMatrix));
				int[][] sudoku = solver.createRandomSudoku(randomMatrix,null,null);
				//logger.info("found final sudoku with number of non zeros: "+solver.getNumberOfNonZeros());
				//logger.info(solver.getMatrixAsString(sudoku));				
			} else {
				usage();							
			}
		} else {
			usage();
		}
		
		// find a sudoku with maximum 23 filled in fields
		/*
		DancingSudoku solver = new DancingSudoku(2);
		int[][] randomMatrix = solver.createRandomFullCoverageMatrix();
		System.out.println(solver.getMatrixAsString(randomMatrix));
		int[][] sudoku = null;
		do {
			sudoku = solver.createRandomSudoku(randomMatrix);
			System.out.println("Number of non zero fields: "+solver.getNumberOfNonZeros());
		} while(solver.getNumberOfNonZeros() > 23);
		System.out.println(solver.getMatrixAsString(sudoku));
		solver.run(sudoku);
		*/
	}
	
	private static void usage() {
		System.out.println("usage: java net.sf.dancingsudoku.DancingSudoku solve|create [dimension]");
		System.exit(0);
	}
	
	private static List loadPuzzles(String fileName) {
		List puzzles = new ArrayList();
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			int[][] currentPuzzle = null;
			int currentPuzzleSize = 0;
			int currentPuzzleRowCount = 0;
			while(br.ready()) {
				String line = br.readLine();
				if(line != null && line.trim().length() > 0 && !line.trim().startsWith("#")) {
					int[] row = createIntArrayFromLine(line);
					if(currentPuzzleRowCount == 0) {
						currentPuzzleSize = row.length;
						currentPuzzle = new int[currentPuzzleSize][currentPuzzleSize];
					}
					currentPuzzle[currentPuzzleRowCount] = row;
					currentPuzzleRowCount++;
					// reset the counter for starting a new puzzle
					if(currentPuzzleRowCount == currentPuzzleSize) {
						puzzles.add(currentPuzzle);
						currentPuzzleRowCount = 0;
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return puzzles;
	}
	
	private static int[] createIntArrayFromLine(String line) {
		StringTokenizer st = new StringTokenizer(line,",");
		int[] result = new int[st.countTokens()];
		for(int i = 0; st.hasMoreTokens(); i++) {
			result[i] = Integer.parseInt(st.nextToken().trim());
		}
		return result;
	}
	
	/*
	public DancingSudoku(int b, SolutionHandler solutionHandler, int[][] puzzle) {
		this.b = b;
		this.solutionHandler = solutionHandler;
		// the puzzle to be solved
		this.puzzle = puzzle;
		init();
	}
	*/

	public DancingSudoku(int b) {
		this.b = b;
		this.solutionHandler = this;
		init();
	}

	public DancingSudoku() {
		this(2);
	}
	
	public void setSolutionHandler(SolutionHandlerIF solutionHandler) {
		this.solutionHandler = solutionHandler;
	}
	
	public void setPuzzleToSolve(int[][] puzzle) {
		this.puzzle = puzzle;
	}
	
	private void init() {
		n = b * b;
		nSquare = n*n;
		twoN = 2*n;
		threeN = 3*n;
		threeNsquare = 3*nSquare;
		fourNsquare = 4*nSquare;
	}
	
	public int[][] run() {
		return run(puzzle);
	}
	
	public int[][] run(int[][] initialMatrix) {
		// reset the counters
		solutionCounter = 0;
		searchCount = 0;
		long start = System.currentTimeMillis();
		List matrix = createEfficientMatrix(initialMatrix);
		//logger.info("size of binary matrix: "+matrix.size());
		createEfficientDoubleLinkedLists(matrix);		
		search(0);
		long end = System.currentTimeMillis();
		//logger.info("finished search! duration [ms]: " + (end-start));
		return this.puzzle;
	}
	
	/** 
	 * Instead of creating the full binary matrix representing the sukoku problem for this initial matrix
	 * containing exactly four 1's in each row, which needs a lot of memory for large n's, an efficient matrix
	 * is created that stores only the indices of the four one's per row. So we get a matrix of four columns
	 * and a maximal number of n*n*n rows for an empty initial matrix
	 */
	private List createEfficientMatrix(int[][] initialMatrix) {
		// create the prefill array
		int[][] prefill = createPrefillArray(initialMatrix);
		
		// the efficient matrix is a list of int arrays with four entries
		// for each possible number in the sudoku an entry in the list is added
		// entry: index of the column position for each of the four 1's in this row: [rowIndex,columnIndex,blockIndex,simpleIndex]
		List matrix = new ArrayList();

		// iterate over all the possible digits d
		for(int d = 0; d < n; d++) {
			// iterate over all the possible rows r
			for(int r = 0; r < n; r++) {
				// iterator over all the possible columns c
				for(int c = 0; c < n; c++) {
					if(!cellIsFilled(d,r,c,prefill)) {
						//int rowIndex = c + (n * r) + (n * n * d);
						// there are four 1's in each row, one for each constraint
						int blockIndex = ((c / b) + ((r / b) * b));
						int colIndexRow = threeN*d+r;
						int colIndexCol = threeN*d+n+c;
						int colIndexBlock = threeN*d+twoN+blockIndex;
						int colIndexSimple = threeNsquare+(c+n*r);
						// store only the indexes
						matrix.add(new int[]{colIndexRow,colIndexCol,colIndexBlock,colIndexSimple});
					}
				}
			}
		}
		return matrix;
	}
	
	/**
	 * check if the cell that should be filled is allready prefilled
	 * with a digit
	 * the prefill two dimension array has the following format:
	 * [{nr}][{digit,row,column}]
	 * @param prefill
	 * @return
	 */
	private boolean cellIsFilled(int digit, int row, int col, int[][] prefill) {
		boolean cellIsFilled = false;
		if(prefill != null) {
			for(int i = 0; i < prefill.length; i++) {
				int d = prefill[i][0]-1;
				int r = prefill[i][1];
				int c = prefill[i][2];
				// calculate the block indices
				int blockStartIndexCol = (c/b)*b;
				int blockEndIndexCol = blockStartIndexCol + b;
				int blockStartIndexRow = (r/b)*b;
				int blockEndIndexRow = blockStartIndexRow + b;
				if(d != digit && row == r && col == c) {
					cellIsFilled = true;
				} else if((d == digit) && (row == r || col == c) && !(row == r && col == c)) {
					cellIsFilled = true;
				} else if((d == digit) && (row > blockStartIndexRow) && (row < blockEndIndexRow) && (col > blockStartIndexCol) && (col < blockEndIndexCol) && !(row == r && col == c)) {
					cellIsFilled = true;
				}
			}
		}
		return cellIsFilled;
	}
	
	/**
	 * helper method for converting a initial sudoku matrix into an array of
	 * arrays containing the description of one initial entry in the form
	 * [value,rowIndex,colIndex]
	 * @param initialMatrix
	 * @return
	 */
	private int[][] createPrefillArray(int[][] initialMatrix) {
		int[][] prefill = null;
		if(initialMatrix != null) {
			// sanitiy check
			if(initialMatrix.length != n) {
				throw new RuntimeException("dimension of initial matrix ("+initialMatrix.length+") does not match the given row/column number: "+n);
			}
			List prefillList = new ArrayList();
			int count = 0;
			for(int r = 0; r < n; r++) {
				for(int c = 0; c < n; c++) {
					if(initialMatrix[r][c] > 0) {
						prefillList.add(new int[]{initialMatrix[r][c],r,c});
						count++;
					}
				}
			}
			prefill = new int[count][];
			for(int i = 0; i < count; i++) {
				prefill[i] = (int[])prefillList.get(i);
			}
		}
		return prefill;
	}
	
	/*
	private Header createDoubleLinkedLists(int[][] matrix) {
		root = createLinkedHeaders();
		// create the headers
		Header currentHeader = null;
		
		// iterate over all the rows
		for(int row = 0; row < nPow3; row++) {
			// iterator over all the columns
			currentHeader = (Header)root.right;
			X lastCreatedElement = null;
			X firstElement = null;
			for(int col = 0; col < fourNsquare; col++) {
				if(matrix[row][col] == 1) {
					// create a new data element and link it
					X colElement = currentHeader;
					while(colElement.down != null) {
						colElement = colElement.down;
					}
					colElement.down = new X();
					if(firstElement == null) {
						firstElement = colElement.down;
					}
					colElement.down.up = colElement;
					colElement.down.left = lastCreatedElement;
					colElement.down.header = currentHeader;
					if(lastCreatedElement != null) {
						colElement.down.left.right = colElement.down;
					}
					lastCreatedElement = colElement.down;
					currentHeader.size++;
				}
				currentHeader = (Header)currentHeader.right;
			}
			// link the first and the last element
			if(lastCreatedElement != null) {
				lastCreatedElement.right = firstElement;
				firstElement.left = lastCreatedElement;
			}
		}
		currentHeader = (Header)root.right;
		// link the last column elements with the coresponding headers
		for(int i = 0; i < fourNsquare; i++) {
			X colElement = currentHeader;
			while(colElement.down != null) {
				colElement = colElement.down;
			}
			colElement.down = currentHeader;
			currentHeader.up = colElement;
			currentHeader = (Header)currentHeader.right;
		}
		return root;
	}*/

	private Header createEfficientDoubleLinkedLists(List matrix) {
		root = createLinkedHeaders();
		// create the headers
		Header currentHeader = null;
		
		// iterate over all the rows
		Iterator it = matrix.iterator();
		//int row = 0;
		while(it.hasNext()) {
			int[] record = (int[])it.next();
			// iterator over all the columns
			currentHeader = (Header)root.right;
			X lastCreatedElement = null;
			X firstElement = null;
			for(int col = 0; col < fourNsquare; col++) {
				if ((record[0] == col) ||
					(record[1] == col) ||
					(record[2] == col) ||
					(record[3] == col)) {
					// create a new data element and link it
					X colElement = currentHeader;
					while(colElement.down != null) {
						colElement = colElement.down;
					}
					colElement.down = new X();
					if(firstElement == null) {
						firstElement = colElement.down;
					}
					colElement.down.up = colElement;
					colElement.down.left = lastCreatedElement;
					colElement.down.header = currentHeader;
					if(lastCreatedElement != null) {
						colElement.down.left.right = colElement.down;
					}
					lastCreatedElement = colElement.down;
					currentHeader.size++;
				}
				currentHeader = (Header)currentHeader.right;
			}
			// link the first and the last element
			if(lastCreatedElement != null) {
				lastCreatedElement.right = firstElement;
				firstElement.left = lastCreatedElement;
			}
		}
		currentHeader = (Header)root.right;
		// link the last column elements with the coresponding headers
		for(int i = 0; i < fourNsquare; i++) {
			X colElement = currentHeader;
			while(colElement.down != null) {
				colElement = colElement.down;
			}
			colElement.down = currentHeader;
			currentHeader.up = colElement;
			currentHeader = (Header)currentHeader.right;
		}
		return root;
	}
	
	private Header createLinkedHeaders() {
		Header headers = new Header();
		// create the headers
		Header currentHeader = headers;
		for(int col = 0; col < fourNsquare; col++) {
			// create the header name
			HeaderInfo info = new HeaderInfo();
			if(col < threeNsquare) {
				// which digit this column covers?
				int digit = (col / (threeN)) + 1;
				info.digit = digit;
				// is it for a row, column or block?
				int index = col-(digit-1)*threeN;
				if(index < n) {
					info.type = HeaderInfo.TYPE_ROW;
					info.position = index;
				} else if(index < twoN) {
					info.type = HeaderInfo.TYPE_COL;
					info.position = index-n;
				} else {
					info.type = HeaderInfo.TYPE_BLOCK;
					info.position = index-twoN;
				}				
			} else {
				info.type = HeaderInfo.TYPE_CELL;
				info.position = col - threeNsquare;
			}
			currentHeader.right = new Header();
			currentHeader.right.left = currentHeader;
			currentHeader = (Header)currentHeader.right;
			currentHeader.info = info;
			currentHeader.header = currentHeader;
		}
		currentHeader.right = headers;
		headers.left = currentHeader;
		return headers;
	}
	
	public int[][] createRandomSudoku(int[][] fullRandomMatrix, SolutionHandlerIF callbackHandler, short[] trialTracker) {
		int[][] sudoku = new int[n][n];
		numberOfNonZeros = 0;

		if(trialTracker == null) {
			trialTracker = new short[nSquare]; // array for capturing the previous trials to remove a number
		}
		// copy the initial full matrix and count the number of non zero values		
		for(int row = 0; row < n; row++) {
			for(int col = 0; col < n; col++) {
				sudoku[row][col] = fullRandomMatrix[row][col];
				if(sudoku[row][col] > 0) {
					numberOfNonZeros++;
				} else {
					trialTracker[n*row+col] = 1;
				}
			}
		}
		// calculate the number of ones in the trialTracker
		int numberOfTriedFields = 0;
		for(int i = 0; i < trialTracker.length; i++) {
			if(trialTracker[i] == 1) {
				numberOfTriedFields++;
			}
		}
		
		
		DancingSudoku solver = new DancingSudoku(b);
		solver.setSolutionHandler(new SolutionHandlerIF() {
			public boolean handleSolution(int[][] solution) {
				if(getSolutionCounter() < 2) {
					return true;
				} else {
					return false;
				}
			}
		});
		int r = -1;
		int c = -1;
		int d = -1;
		boolean toContinue = true;
		while(numberOfTriedFields < nSquare && toContinue) {			
			int field = (int)Math.round(Math.random() * (nSquare-1));
			// find the next field that is not already taken
			while(trialTracker[field] > 0) {
				if(field == (nSquare-1)) {
					field = 0;
				} else {
					field ++;
				}
			}
			// calculate the row and column
			r = field / n;
			c = field % n;
			d = sudoku[r][c];
			trialTracker[field] = 1;
			numberOfTriedFields++;
			sudoku[r][c] = 0;
			numberOfNonZeros--;
			//logger.info("trying field " + field + ", [r][c] = [" + r + "][" + c + "], number of fields left = " + (nSquare - numberOfTriedFields));
			// copy the original sudoku for filling in the low hanging fruits:
			int[][] sudokuCopy = copyMatrix(sudoku);			
			// update the handler
			if(callbackHandler != null) {
				toContinue = callbackHandler.handleSolution(sudokuCopy);
			}
			//logger.info("filling in low hanging fruits: " + solver.fillInLowHangingFruits(sudokuCopy));
			solver.run(sudokuCopy);
			// update the handler
			if(callbackHandler != null) {
				toContinue = callbackHandler.handleSolution(sudokuCopy);
			}
			//logger.info("solver finished... trial tracker: " + getArrayAsString(trialTracker));
			if(solver.getSolutionCounter() > 1) {
				// put back the last number that was removed befor it became ambigous
				sudoku[r][c] = d;
				numberOfNonZeros++;
				//logger.info("solution became ambigous, trying another field ...");
			} else {
				//logger.info("found soduko with number of non zeros: "+numberOfNonZeros);
				//logger.info(getMatrixAsString(sudoku));
			}
		}
		return sudoku;
	}
	
	public int[][] createRandomFullCoverageMatrix() {
		final int[][] fullMatrix = new int[n][n];
		DancingSudoku solver = new DancingSudoku(b);
		solver.setSolutionHandler(new SolutionHandlerIF() {
			public boolean handleSolution(int[][] solMatrix) {
				// copy the matrix
				for(int row = 0; row < n; row++) {
					for(int col = 0; col < n; col++) {
						fullMatrix[row][col] = solMatrix[row][col];
					}
				}
				return false;
			}
		});
		// create random initial matrix filled with numbers 1-n
		int[][] randomInitialMatrix = new int[n][n];
		int[] fields = new int[nSquare];
		for(int d=1; d<=n; d++) {
			int field = (int)Math.round(Math.random() * (nSquare-1));
			// find the next field that is not already taken
			while(fields[field] > 0) {
				if(field == (nSquare-1)) {
					field = 0;
				} else {
					field ++;
				}
			}
			// calculate the row and column
			randomInitialMatrix[field / n][field % n] = d;
			fields[field] = d;
		}
		solver.run(randomInitialMatrix);
		return fullMatrix;
	}
	
	public int fillInLowHangingFruits(int[][] sudoku) {
		int fruitCount = 0;
		int size = sudoku.length;
		int bsize = (int)Math.sqrt(size);
		boolean hasNew = false;
		do {
			hasNew = false;
			for(int row = 0; row < size; row++) {
				for(int col = 0; col < size; col++) {
					if(sudoku[row][col] == 0) {
						short[] coverTracker = new short[n];
						int differentNumberCount = 0;
						// track the same row
						for(int c = 0; c < size; c++) {
							if(c != col && sudoku[row][c] > 0) {
								if(coverTracker[sudoku[row][c]-1] == 0) {
									coverTracker[sudoku[row][c]-1] = 1;
									differentNumberCount++;
								}
							}
						}
						// track the same col
						for(int r = 0; r < size; r++) {
							if(r != row && sudoku[r][col] > 0) {
								if(coverTracker[sudoku[r][col]-1] == 0) {
									coverTracker[sudoku[r][col]-1] = 1;
									differentNumberCount++;
								}
							}
						}						
						// track the same block
						// calculate the block indices						
						int blockStartIndexCol = (col/bsize)*bsize;
						int blockEndIndexCol = blockStartIndexCol + bsize;
						int blockStartIndexRow = (row/bsize)*bsize;
						int blockEndIndexRow = blockStartIndexRow + bsize;
						for(int r = blockStartIndexRow; r < blockEndIndexRow; r++) {
							for(int c = blockStartIndexCol; c < blockEndIndexCol; c++) {
								if(!(r == row && c == col) && sudoku[r][c] > 0) {
									if(coverTracker[sudoku[r][c]-1] == 0) {
										coverTracker[sudoku[r][c]-1] = 1;
										differentNumberCount++;
									}									
								}
							}
						}
						// if we counted n-1 different numbers we found a low hanging fruit :-)
						if(differentNumberCount == size-1) {
							fruitCount++;
							hasNew = true;
							// fill in the unique number
							int index = 0;
							while(coverTracker[index] > 0) {
								index++;
							}
							int value = index+1;
							//logger.info("found low hanging fruit at row: "+row+", col: "+col+", value: "+value);
							sudoku[row][col] = value;
						}
					}
				}
			}
			
		} while (hasNew);
		return fruitCount;
	}
	
	public int getSolutionCounter() {
		return solutionCounter;
	}
	
	public int getNumberOfNonZeros() {
		return numberOfNonZeros;
	}
	
	private int[][] copyMatrix(int[][] originalMatrix) {
		int size = originalMatrix.length;
		int[][] copiedMatrix = new int[size][size];;
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < originalMatrix[row].length; col++) {
				copiedMatrix[row][col] = originalMatrix[row][col];
			}
		}
		return copiedMatrix;
	}
	
	private String getArrayAsString(short[] array) {
		StringBuffer sb = new StringBuffer();
		int l = array.length;
		for(int i = 0; i < l; i++) {
			sb.append(array[i]);
			if(i < l-1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private String getMatrixAsString(int[][] matrix) {
		StringBuffer sb = new StringBuffer("\n");
		for(int row = 0; row < matrix.length; row++) {
			for(int col = 0; col < matrix[row].length; col++) {
				if(col > 0) {
					sb.append(",");
				}
				sb.append(matrix[row][col]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private void search(int k) {
		if(searchCount % 1000000 == 0) {
			System.out.println("");
		}
		if(searchCount % 10000 == 0) {
			System.out.print(".");
		}
		searchCount++;
		// Abbruchbedingung
		if(root.right == root) {
			return;
		}
		Header c = chooseColumn();
		coverColumn(c);
		X r = c.down;
		while(r != c) {
					
			if(k < solution.size()) {
				solution.remove(k);
			}			
			solution.add(k,r);
			
			X j = r.right;
			while(j != r) {
				coverColumn(j.header);
				j = j.right;
			}
			if(shouldContinue) {
				search(k+1);
			}
			
			// are r and c realy overwritten here??
			X r2 = (X)solution.get(k);
			//c = r.header;
			X j2 = r2.left;
			while(j2 != r2) {
				uncoverColumn(j2.header);
				j2 = j2.left;
			}
			r = r.down;
			
			// here we can distinguis the different solutions
			if(k == nSquare-1) {
				solutionCounter++;
				shouldContinue = solutionHandler.handleSolution(createMatrixFromSolution(solution));
			}
		}
		uncoverColumn(c);
	}
	
	private int[][] createMatrixFromSolution(List solution) {
		int[][] result = new int[n][n];
		for(Iterator it = solution.iterator(); it.hasNext(); ) {
			int digit = -1;
			int cell = -1;
			X element = (X)it.next();
			X next = element;
			do {
				if(next.header.info.type == HeaderInfo.TYPE_ROW) {
					digit = next.header.info.digit;
				} else if(next.header.info.type == HeaderInfo.TYPE_CELL) {
					cell = next.header.info.position;
				}
				next = next.right;
			} while(element != next);
			int row = cell / n;
			int col = cell % n;
			result[row][col] = digit;
		}
		return result;
	}
	
	private Header chooseColumn() {
		// its mostly efficient to always choose the column with the smales size		
		Header h = (Header)root.right;
		Header smalest = h;
		while(h.right != root) {
			h = (Header)h.right;
			if(h.size == 1) {
				smalest = h;
				break;
			} else if(h.size < smalest.size) {
				smalest = h;
			}			
		}		
		return smalest;
	}
	
	private void coverColumn(X column) {
		column.right.left = column.left;
		column.left.right = column.right;
		X i = column.down;
		while(i != column) {
			X j = i.right;
			while(j != i) {
				j.down.up = j.up;
				j.up.down = j.down;
				j.header.size--;
				j = j.right;
			}
			i = i.down;
		}
	}

	private void uncoverColumn(X column) {
		X i = column.up;
		while(i != column) {
			X j = i.left;
			while(j != i) {
				j.header.size++;
				j.down.up = j;
				j.up.down = j;
				j = j.left;
			}
			i = i.up;
		}
		column.right.left = column;
		column.left.right = column;
	}
		
	/**
	 * the default solution handler
	 */
	public boolean handleSolution(int[][] solution) {
		//logger.info("Solution: "+solutionCounter);
		//logger.info(getMatrixAsString(solution));
		return true;
	}
	
	/**
	 * Method for handling Dancing Sudoku inside a Thread
	 *
	 */
	/*
	public void run() {
		run(puzzle);
	}
	*/	

	
	class X {
		X left;
		X right;
		X up;
		X down;
		Header header;
	}
	
	class Header extends X {
		int size = 0;
		HeaderInfo info;
	}
	
	class HeaderInfo {
		static final int TYPE_ROW = 0;
		static final int TYPE_COL = 1;
		static final int TYPE_BLOCK = 2;
		static final int TYPE_CELL = 3;
		
		int type = -1;
		int digit = -1;
		int position = -1;
		
		public String toString() {
			// create the header name
			StringBuffer name = new StringBuffer();
			if(type == TYPE_ROW) {
				name.append("Digit ");
				name.append(digit);
				name.append(" in row ");
			} else if(type == TYPE_COL) {
				name.append("Digit ");
				name.append(digit);
				name.append(" in column ");
			} else if(type == TYPE_BLOCK) {
				name.append("Digit ");
				name.append(digit);
				name.append(" in block ");
			} else if(type == TYPE_CELL) {
				name.append("Digit in cell ");
			}
			name.append(position+1);
			return name.toString();
		}
	}
	
	class CoverCheck {
		int[] rows = new int[n];
		int[] columns = new int[n];
		int[] blocks = new int[n];
	}
}