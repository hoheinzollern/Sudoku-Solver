package sudokusolver.solvers

import sudokusolver.exceptions

/**
 * This abstract class represents the structure of a generic solver.
 * The subject of this class is to create the basic framework to obtain 
 * a complete solver with any possible kind of search / propagation methods
 */

class GenericSolver(problem: sudokusolver.Sudoku) {
	protected var searchAlgorithm : SearchAlgorithm = null
	protected var propagationAlgorithm : PropagationAlgorithm = null
 
	def setSearchAlgorithm(searchAlgorithm : SearchAlgorithm) {
	  this.searchAlgorithm = searchAlgorithm
	}
 
	def setPropagationAlgorithm(propagationAlgorithm : PropagationAlgorithm) {
	  this.propagationAlgorithm = propagationAlgorithm
	}
	
	/**
	* This method starts the selected algorithms and returns the solution as
	* a stepList 
	*/
	def solve() {
	  if (this.searchAlgorithm != null && this.propagationAlgorithm != null) {
		  this.searchAlgorithm.execute(this.propagationAlgorithm) 
	  } else throw new exceptions.SolverNotReadyException()
	}
	
	def generateProblem(difficulty : Int) = {
		// FIXME
		//this.problem = this.core.generate(difficulty)
		this.problem
	}
	
	def getProblem() = {
		this.problem
	}
}
