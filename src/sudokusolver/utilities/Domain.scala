package sudokusolver.utilities

class Domain {
	private var values : List[Int] = List(1,2,3,4,5,6,7,8,9)
  
	def addValue(value: Int) {
	  this.values = value :: this.values
	}
 
 	def extractValue() = {
 	  var element = this.values.head
 	  this.values = this.values.filter((n) => n != element)
 	  element
 	}
  
  	def getValues() = {
  	  this.values
  	}
   
   	def deleteValue(value: Int) = {
   	  this.values = this.values.filter((n) => n != value)
   	}
}
