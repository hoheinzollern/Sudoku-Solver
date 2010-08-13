package sudokusolver

/**
 * This class represents the game with the fields of the scheme.
 */

class Sudoku {
	class InvalidValueException extends Exception
		
	private var board = new Array[Array[Int]](9,9)
	private var domains = new Array[Array[sudokusolver.utilities.Domain]](9,9)
    private var stepList = List[utilities.Step]()
    private var views = List[View]()
	
    /**
     * Sets a variable in the board, storing a new step in steplist for
     * backtracking purposes.
     * 
     * @param x the row of the board
     * @param y the column of the board
     * @param value the actual value
     * @param message a description of the action
     */
	def set(x: Int, y: Int, value: Int, message : String) {
		if (value < 1 || value > 9) throw new InvalidValueException()
		board(x)(y) = value
		val step = new utilities.Step(new utilities.Couple(x, y), value, message, domains.clone)
		domains(x)(y) = new sudokusolver.utilities.Domain("(" + x + "," + y + ")")
		domains(x)(y).empty
		domains(x)(y).addValue(value)
		stepList = stepList :+ step

		notifyView
	}
	
	/**
	 * Sets the entire board, initializing the domains accordingly
	 * 
	 * @param newBoard a 9x9 matrix of int values representing the board
	 * 		(use 0 for empty cells)
	 */
	def setBoard(newBoard: Array[Array[Int]]) {
		board = newBoard
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				if (board(i)(j) != 0) {
					domains(i)(j).empty()
					domains(i)(j).addValue(board(i)(j))
				}
			}
		}
		
		notifyView
	}
	
	/**
	 * Gets an element from the board
	 */
    def get(x: Int, y: Int) = board(x)(y)
    
    /**
     * Applies backtracking for one step
     */
    def back {
    	val step = stepList.head
    	stepList = stepList - step
    	board(step.getCouple.getX)(step.getCouple.getY) = 0
    	domains = step.getDomains
    	
    	notifyView
    }
    
    /**
     * Returns the domain for a single element of the board
     */
    def getDomain(x: Int, y: Int) = domains(x)(y)
    
    /**
     * Notifies the views attached to this game
     */
    def notifyView {
    	for (view <- views)
    		view.update
    }
}
