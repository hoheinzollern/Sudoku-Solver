package sudokusolver.exceptions

class SolverNotReadyException extends SudokuSolverException {
	setSudokuSolverMessage("To execute a solver, it must contain at least a 'search algorithm' and/or a 'propagation algorithm'")
}
