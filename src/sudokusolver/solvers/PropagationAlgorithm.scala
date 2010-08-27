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

	  // Propagate row
	  for (i <- 0 to 8) {
	 	  if (this.board.isNotNull(i, xj.getY) && i != xj.getY) this.domains.get(xj).deleteValue(this.board.getValue(xj.getX, i))
	  }
	  
	  // Propagate column
	  for (i <- 0 to 8) {
	 	  if (this.board.isNotNull(xj.getX, i) && i != xj.getX) this.domains.get(xj).deleteValue(this.board.getValue(i, xj.getY))
	  }
	  
	  // Propagate panel
	  var minX = 0
	  var maxX = 8
	  var minY = 0
	  var maxY = 8
	  
	  if (xj.getX < 3) {
	 	  maxX = 3
	  }
	  else if (xj.getX < 6) {
	 	  minX = 3
	 	  maxX = 6 
	  }
	  else {
	 	  minX = 6
	  }
	  if (xj.getY < 3) {
	 	  maxY = 3
	  }
	  else if (xj.getY < 6) {
	 	  minY = 3
	 	  maxY = 6 
	  }
	  else {
	 	  minY = 6
	  }
	  
	  for (i <- minX to maxX) {
	 	  	for (j <- minY to maxY) {
	 	  		if (this.board.isNotNull(i, j) && i != xj.getX && j != xj.getY) {
	 	  			this.domains.get(xj).deleteValue(this.board.getValue(i, j))
	 	  		}
	 	  	}
	  }
	}
 
 	/**
 	* This is the main method of the propagation mechanism.
 	* 
 	* The idea is to get the variable to load and the universe of the domains.
 	*/
	def prop(item: utilities.Couple)
 
 	/**
 	* This return the specific domain of a variable 
 	*/
 	def getDomains = {
 	  this.domains
 	}
}
