package sudokusolver.solvers.propagation

class NoPropagation extends PropagationAlgorithm {
	override def prop(item: sudokusolver.utilities.Couple) = false
}