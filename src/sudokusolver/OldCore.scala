package sudokusolver

import java.lang.Math.random

class OldCore {
	private var loadedModules = List[sudokusolver.utilities.Module]()
	private var scheme = new utilities.SchemeContainer
	private var domains = new utilities.DomainContainer
 
	def generate(difficulty : Int) = {
		var startCouple = new utilities.Couple
		scheme = new utilities.SchemeContainer
		domains = new utilities.DomainContainer
		generateScheme(startCouple)
		scheme.get
	}
	
	def generateScheme(currentCouple : utilities.Couple) {
		println("-------------------------------------------------------------")
		println("Coppia (" + currentCouple.getX + "," + currentCouple.getY + ")")
		println("-------------------------------------------------------------")
		var randomPosition : Int = 0
		while(!domains.get(currentCouple.getX, currentCouple.getY).isEmpty) {
			var domainTotalElements = domains.get(currentCouple.getX, currentCouple.getY).cardinality
			randomPosition = (Math.random * domainTotalElements).asInstanceOf[Int]
			println("Random vale " + randomPosition + " mentre gli elementi sono : " + domains.get(currentCouple.getX, currentCouple.getY).getValues)
			var element = domains.get(currentCouple.getX, currentCouple.getY).getValue(randomPosition)
			println("Provo " + element)
			domains.get(currentCouple.getX, currentCouple.getY).deleteValue(element)
			domains.addOperation(new utilities.Operation("remove", currentCouple.getX, currentCouple.getY, element))
			println("Ho rimosso " + element + " dal dominio " + domains.get(currentCouple.getX, currentCouple.getY).getName)
			if (cons(scheme, currentCouple, element)) {
				scheme.set(element, currentCouple.getX, currentCouple.getY)
				println("Assegnato " + element + " alla casella (" + currentCouple.getX + "," + currentCouple.getY + ")")
				println("Quindi....")
				println("SCHEME:")
				for (h<-0 to 8) {
					for (k<-0 to 8) {
						print(scheme.get(h, k))
					}
					println(" ")
				}
				if (currentCouple.isLatest) {
					println("Siamo in fondo! Chiudo tutto!")
					//scheme
				}	
				else {
					println("Prima della ricorsione il dominio ")
					if (domains.get(currentCouple.getX, currentCouple.getY).isEmpty) println(" .. è vuoto!")
					else println("ha elementi " + domains.get(currentCouple.getX, currentCouple.getY).getValues)
					
					generateScheme(currentCouple.next)
					domains = domains.back
					//scheme = scheme.back
					
					println("Sono tornato! La coppia corrente è " + currentCouple.getX + "," + currentCouple.getY + " e la situazione è")
					println("SCHEME:")
					for (h<-0 to 8) {
						for (k<-0 to 8) {
							print(scheme.get(h, k))
						}
						println(" ")
					}
				}
			}
			else {
				println("Il valore " + element + " non è consistente! Provo con il prossimo...")
			}
		}
		println("Il dominio di " + domains.get(currentCouple.getX, currentCouple.getY).getName + " era vuoto... per cui esco!")
	}

	private def cons(scheme : utilities.SchemeContainer, latestCouple : utilities.Couple, value : Int) = {
		println("Verifico " + value)
		println("La sua casella ha il valore " + scheme.get(latestCouple.getX, latestCouple.getY))
		/*println("SCHEME:")
		for (h<-0 to 8) {
			for (k<-0 to 8) {
				print(scheme(h)(k))
			}
			println(" ")
		}*/
		var answer = false
		var check1 = false
		var check2 = false
		var check3 = false
		if (checkRow(scheme, latestCouple.getX, latestCouple.getY, value)) {
			check1 = true
		}
		if (checkColumn(scheme, latestCouple.getX, latestCouple.getY, value)) {
			check2 = true
		}
		if (checkBox(scheme, latestCouple.getX, latestCouple.getY, value)) {
			check3 = true
		}

		answer = check1 && check2 && check3
		answer
	}
	
	private def checkRow(scheme : utilities.SchemeContainer, row : Int, column : Int, value : Int) = {
		var result = true
		for (i<-0 to 8) {
			if (scheme.get(row, i) == value) {
				println("Passaggio : " + row + "," + i + " vale " + scheme.get(row, i))
				result = false
				println("Scattato ROW con (" + row + "," + column + ") e valore " + value)
			}
		}
		result
	}
	
	private def checkColumn(scheme : utilities.SchemeContainer, row : Int, column : Int, value : Int) = {
		var result = true
		for (i<-0 to 8) {
			if (scheme.get(i, column) == value) {
				result = false
				println("Scattato COLUMN con (" + row + "," + column + ") e valore " + value)
			}
		}
		result
	}
	
	private def checkBox(scheme : utilities.SchemeContainer, row : Int, column : Int, value : Int) = {
		var result = true
		var minX = 0
		var maxX = 8
		var minY = 0
		var maxY = 8
		if (row <= 2) {
			maxX = 2
		}
		else if (row <= 5) {
			minX = 3
			maxX = 5
		}
		else {
			minX = 6
		}
		if (column <= 2) {
			maxY = 2
		}
		else if (column <= 5) {
			minY = 3
			maxY = 5
		}
		else {
			minY = 6
		}
		for (i<-minX to maxX) {
			for (j<-minY to maxY)
				if (scheme.get(i, j) == value) {
					result = false
					println("Scattato BOX con (" + row + "," + column + ") e valore " + value)
				}
		}
		result
	}
}
