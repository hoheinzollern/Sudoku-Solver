package sudokusolver.solvers

class BacktrackingSearch extends SearchAlgorithm {
	override def solve() = {
		var success = false
		var startItem = new sudokusolver.utilities.Couple(0,0)
		if (!recursiveBacktrack(startItem, getProblem.getDomains.clone)) {
			throw new sudokusolver.exceptions.SolutionNotFoundException
		}
		getProblem
	}
	
	def recursiveBacktrack(inputItem : sudokusolver.utilities.Couple, domains: sudokusolver.utilities.DomainContainer) : Boolean = {
		var item = inputItem
		while (this.getProblem.get(item.getX, item.getY) != 0 && !item.isLatest) {
			println("RecursiveBacktrack! con " + item.printCouple)
			item = item.next
		}
		var success = false
		while(!domains.get(item).isEmpty && !success) {
			println("While con " + item.printCouple)
			println(domains.get(item).getStatus)
			var elementValue = domains.get(item).extractValue()
			println("Estratto " + elementValue)
			if (checkConsistency(item, elementValue)) {
				this.getProblem.set(item, elementValue, "BAU!")
				if (item.isLatest) {
					success = true
				}
				else success = recursiveBacktrack(item.clone.next, domains.clone)
				if (!success) {
					this.getProblem.back
					sudokusolver.Core.log("Backtrack")
				}
			}
			else {
				println("Istanziazione non consistente !!!!")
			}
		}
		success
	}
	
	def checkConsistency(item : sudokusolver.utilities.Couple, element : Int) : Boolean = {
		println("Verifica della consistenza... ho elemento " + item.printCouple + " che voglio a " + element)
		var board = getProblem.getBoard
		//Check along the rows
		var j = 0
		var consistent = true
		while(j < 9 && consistent) {
			if (j != item.getY && board.getValue(item.getX,j) == element) consistent = false
			j = j + 1
		}
		
		//Check along the columns
		var i = 0
		while(i < 9 && consistent) {
			if (i != item.getX && board.getValue(i,item.getY) == element) consistent = false
			i = i + 1
		}
		
		//Check in panels
		val panelFirstX = item.getX - item.getX%3
		val panelFirstY = item.getY - item.getY%3
		i = panelFirstX
		j = panelFirstY
		while(i < panelFirstX+2 && consistent) {
			while(j < panelFirstY+2 && consistent) {
				if (i != item.getX && j != item.getY && board.getValue(i, j) == element) consistent = false
				j = j + 1
			}
			i = i + 1
		}
		
		consistent
	}
}