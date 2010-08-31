package sudokusolver.solvers

class NoSearch extends SearchAlgorithm {
	override def solve() = {
		var item = new sudokusolver.utilities.Couple(0,0)
		var failure = false
		while(!item.isLatest && !failure) {
			failure = prop(item)
		}
		if (failure) throw new sudokusolver.exceptions.SolutionNotFoundException
		getProblem
	}
}
