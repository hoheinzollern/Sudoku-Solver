package sudokusolver.solvers

import sudokusolver.exceptions

/**
 * This abstract class represents the structure of a generic solver.
 * The subject of this class is to create the basic framework to obtain 
 * a complete solver with any possible kind of search / propagation methods
 */

class GenericSolver {
	protected var problem: sudokusolver.Sudoku = null
	protected var searchAlgorithm : SearchAlgorithm = null
	protected var propagationAlgorithm : PropagationAlgorithm = null
 
	def setSearchAlgorithm(searchAlgorithm : SearchAlgorithm) {
	  	this.searchAlgorithm = searchAlgorithm
	}
 
	def setPropagationAlgorithm(propagationAlgorithm : PropagationAlgorithm) {
		this.propagationAlgorithm = propagationAlgorithm
	}
	
	def setProblem(problem: sudokusolver.Sudoku) {
		this.problem = problem
	}
	
	/**
	 * This method starts the selected algorithms and returns the solution as
	 * a stepList 
	 */
	def solve(): sudokusolver.Sudoku = {
	  if (this.searchAlgorithm != null && this.propagationAlgorithm != null) {
		  this.searchAlgorithm.execute(this.propagationAlgorithm) 
		  return problem
	  } else throw new exceptions.SolverNotReadyException()
	}
}
