package sudokusolver.solvers

import sudokusolver.utilities

abstract class PropagationAlgorithm {
	protected var domains : utilities.DomainContainer = null
	protected var board : utilities.Board = null
	protected var problem : sudokusolver.Sudoku = null
 
	def setProblem(problem : sudokusolver.Sudoku) {
		this.problem = problem
		this.domains = problem.getDomains
		this.board = problem.getBoard
	}
	
	/**
	* This is the "revise" method that is the basic idea of arc consistency.
	* 
	* The idea is to check if between 2 couple of variables bounded by a
	* constraint, for every value of the first variable exists almost 
	* one value of the second variable that respect the constraint. 
	*/
	def revise(xi : utilities.Couple, xj : utilities.Couple) = {  
		// Check and remove invalid values from k corresponding to values of other cells
		if (!this.board.isNotNull(xj)) {
			println("Eseguo la revise tra " + xi.printCouple + " e " + xj.printCouple)
		
			// If items are on the same row
			if (xi.getX == xj.getX) {
				println("Le 2 caselle stanno sulla stessa RIGA... allora propago sulla riga " + xi.getX)
				// Propagate row
				for (j <- 0 to xi.getY) {
					if (this.board.isNotNull(xi.getX, j)) this.domains.get(xj).deleteValue(this.board.getValue(xi.getX, j))
				}
			}
			
			// If items are on the same column
			if (xi.getY == xj.getY) {
				println("Le 2 caselle stanno sulla stessa COLONNA... allora propago sulla colonne " + xi.getY)
				// Propagate column
				for (i <- 0 to xi.getX) {
					if (this.board.isNotNull(i, xi.getY)) this.domains.get(xj).deleteValue(this.board.getValue(i, xi.getY))
				}
			}
	  
			// If items are on the same panel
			val panelFirstXByXi = xi.getX - xi.getX%3
			val panelFirstYByXi = xi.getY - xi.getY%3
			val panelFirstXByXj = xj.getX - xj.getX%3
			val panelFirstYByXj = xj.getY - xj.getY%3
			if (panelFirstXByXi == panelFirstXByXj && panelFirstYByXi == panelFirstYByXj) {
				println("Le 2 caselle stanno nello stesso pannello!")
				for (i <- panelFirstXByXi to panelFirstXByXi+2) {
					for (j <- panelFirstYByXi to panelFirstYByXi+2) {
						if (!(i > xj.getX && j > xj.getY)) {
							if (this.board.isNotNull(i, j)) this.domains.get(xj).deleteValue(this.board.getValue(i, j))
						}
					}
				}
			}
		}
	}


 	/**
 	* This is the main method of the propagation mechanism.
 	* 
 	* The idea is to get the variable to load and the universe of the domains.
 	*/
	def prop(item: utilities.Couple) : Boolean
 
 	/**
 	* This return the specific domain of a variable 
 	*/
 	def getDomains = this.domains
}
