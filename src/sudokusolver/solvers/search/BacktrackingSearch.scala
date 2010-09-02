package sudokusolver.solvers

class BacktrackingSearch extends SearchAlgorithm {
	override def solve() = {
		var startItem = new sudokusolver.utilities.Couple(0,0)
		if (!recursiveBacktrack(startItem, getProblem.getDomains.clone, false)) {
			throw new sudokusolver.exceptions.SolutionNotFoundException
		}
		getProblem
	}
	
	def recursiveBacktrack(inputItem : sudokusolver.utilities.Couple, domains: sudokusolver.utilities.DomainContainer, inputSuccess : Boolean) : Boolean = {
		var item = inputItem
		var success = inputSuccess
		while (this.getProblem.get(item.getX, item.getY) != 0 && !item.isLatest) {
			item = item.next
		}
		if (item.isLatest && this.getProblem.get(item) != 0) {
				success = true
		}
		while(!domains.get(item).isEmpty && !success) {
			println("While con " + item.printCouple)
			println(domains.get(item).getStatus)
			var elementValue = domains.get(item).extractValue()
			println("Estratto " + elementValue)
			if (checkConsistency(item, elementValue)) {
				println("Il valore è consistente! Provo!")
				this.getProblem.set(item, elementValue, "BAU!")
				if (item.isLatest) {
					this.getProblem.setDomains(domains.clone)
					success = true
				}
				else success = recursiveBacktrack(item.clone.next, domains.clone, success)
				if (!success) {
					this.getProblem.back
					sudokusolver.Core.log("Backtrack su " + item.printCouple)
				}
			}
		}
		if (domains.get(item).isEmpty) {
			println("Il dominio di " + item.printCouple + " è diventato vuoto!")
			success = false
		}
		success
	}
	
	def checkConsistency(item : sudokusolver.utilities.Couple, element : Int) : Boolean = {
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
		while(i < panelFirstX+3 && consistent) {
			while(j < panelFirstY+3 && consistent) {
				if (!(i == item.getX && j == item.getY) && board.getValue(i, j) == element) consistent = false
				j = j + 1
			}
			i = i + 1
		}
		
		consistent
	}
}