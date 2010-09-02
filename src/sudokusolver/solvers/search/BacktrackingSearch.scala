package sudokusolver.solvers

class BacktrackingSearch extends SearchAlgorithm {
	override def solve() = {
		var success = false
		var startItem = new sudokusolver.utilities.Couple(0,0)
		recursiveBacktrack(startItem, success)
	}
	
	def recursiveBacktrack(inputItem : sudokusolver.utilities.Couple, inputSuccess : Boolean) : sudokusolver.Sudoku = {
		var item = inputItem
		println("RecursiveBacktrack! con " + item.printCouple)
		println(" e ha cardinalità " + this.getProblem.getDomain(item).cardinality)
		var outputProblem : sudokusolver.Sudoku = getProblem
		var success = inputSuccess
		while(this.getProblem.getDomain(item).cardinality > 1 && !success) {
			println("While con " + item.printCouple)
			var elementValue = this.getProblem.getDomains.get(item).extractValue()
			if (checkConsistency(item, elementValue)) {
				this.getProblem.set(item, elementValue, "BAU!")
				if (item.isLatest) success = true
				else outputProblem = recursiveBacktrack(item.next, success)
			}
			else println("Istanziazione non consistente !!!!")
			item = item.next
		}
		outputProblem
	}
	
	def checkConsistency(item : sudokusolver.utilities.Couple, element : Int) : Boolean = {
		println("Verifica della consistenza... ho elemento " + item.printCouple + " che voglio a " + element)
		var board = getProblem.getBoard
		//Check along the rows
		var j = 0
		var consistent = true
		while(j != item.getY && consistent) {
			if (board.getValue(item.getX,j) == element) consistent = false
			j = j + 1
		}
		if (!consistent) println("Problema sulla riga!")
		
		//Check along the columns
		var i = 0
		while(i != item.getX && consistent) {
			if (board.getValue(i,item.getY) == element) consistent = false
			j = j + 1
		}
		if (!consistent) println("Problema sulla colonna!")
		
		//Check in panels
		val panelFirstX = item.getX - item.getX%3
		val panelFirstY = item.getY - item.getY%3
		i = panelFirstX
		j = panelFirstY
		while(i < panelFirstX+2 && consistent) {
			while(j < panelFirstY+2 && consistent) {
				if (board.getValue(i, j) == element) consistent = false
				j = j + 1
			}
			i = i + 1
		}
		if (!consistent) println("Problema sul pannello!")
		
		consistent
	}
}