package sudokusolver.utilities

class Domain {
	private var values : List[Int] = List(1,2,3,4,5,6,7,8,9)
  
	def addValue(value: Int) {
	  this.values = value :: this.values
	}
 
 	def extractValue(value: Int) {
 	  this.values.head
 	}
  
  	def getValues() {
  	  this.values
  	}
   
   	def deleteValue(value: Int) {
   	  //Da sistemare... vorrei eliminare l'elemento 'value' dalla lista ma non riesco a capire come si fa
   	  //this.values = this.values.filterNot(value)
   	}
}
