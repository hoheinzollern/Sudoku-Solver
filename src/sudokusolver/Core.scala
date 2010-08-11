package sudokusolver

import java.lang.Math.random

class Core {
	private var loadedModules = List[sudokusolver.utilities.Module]()
	private var scheme = new Array[Array[Int]](9,9)
 
	def generate(difficulty : Int) = {
		scheme = new Array[Array[Int]](9,9)
		var assignedFields = 0
		while(assignedFields <= 20+difficulty*6) {
			var randomPositionX : Int = (Math.random * 9).asInstanceOf[Int]
			var randomPositionY : Int = (Math.random * 9).asInstanceOf[Int]
			var randomValue : Int = (Math.random * 8 + 1).asInstanceOf[Int]
			if (scheme(randomPositionX)(randomPositionY) == 0) {
				if (checkRow(randomValue, randomPositionX)) {
					if (checkColumn(randomValue, randomPositionY)) {
						if (checkBox(randomValue, randomPositionX, randomPositionY)) {
							scheme(randomPositionX)(randomPositionY) = randomValue
							assignedFields = assignedFields+1
						}
					}
				}
			}
		}
		println("Assegnate " + assignedFields + " caselle")
		scheme
	}
		
	private def checkRow(i : Int, row : Int) = {
		var result = true
		for (j<-0 to 8) {
			if (scheme(row)(j) == i) result = false
		}
		result
	}
	
	private def checkColumn(i : Int, column : Int) = {
		var result = true
		for (j<-0 to 8) {
			if (scheme(j)(column) == i) result = false
		}
		result
	}
	
	private def checkBox(i : Int, row : Int, column : Int) = {
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
			
		for (xi<-minX to maxX) {
			for (yi<-minY to maxY)
				if (scheme(xi)(yi) == i) result = false
		}
		result
	}
}
