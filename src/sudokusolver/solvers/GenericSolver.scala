package sudokusolver.solvers

import sudokusolver.exceptions
import sudokusolver.utilities.Board

/**
 * This abstract class represents the structure of a generic solver.
 * The subject of this class is to create the basic framework to obtain 
 * a complete solver with any possible kind of search / propagation methods
 */

class GenericSolver {
	private var problem: sudokusolver.Sudoku = null
	private var searchAlgorithm : SearchAlgorithm = null
	private var propagationAlgorithm : PropagationAlgorithm = null

	def setSearchAlgorithm(searchAlgorithm : SearchAlgorithm) {
	  	this.searchAlgorithm = searchAlgorithm
	}
	
	def getSearchAlgorithm() = this.searchAlgorithm
 
	def setPropagationAlgorithm(propagationAlgorithm : PropagationAlgorithm) {
		this.propagationAlgorithm = propagationAlgorithm
		this.propagationAlgorithm.setProblem(this.problem)
	}
	
	def getPropagationAlgorithm() = this.propagationAlgorithm
	
	def setProblem(problem: sudokusolver.Sudoku) {
		this.problem = problem
	}
	
	def getProblem() = this.problem
	
	def getVisitCount = searchAlgorithm.getVisitCount
	
	def getTimeElapsed = searchAlgorithm.getTimeElapsed
	
	/**
	 * This method starts the selected algorithms and returns the solution as
	 * a stepList 
	 */
	def start() {
	  if (this.searchAlgorithm != null && this.propagationAlgorithm != null) {
		  this.problem = this.searchAlgorithm.execute(this.problem, this.propagationAlgorithm)
	  } else throw new exceptions.SolverNotReadyException()
	}
}
