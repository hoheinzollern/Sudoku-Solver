package sudokusolver.utilities

class Domain {
	private var values : List[Int] = List(1,2,3,4,5,6,7,8,9)
	private var totalElements = 9
  
 	def extractValue() = {
 	  var element = this.values.head
 	  this.values = this.values.filter((n) => n != element)
 	  this.totalElements = this.totalElements - 1
 	  element
 	}
  
  	def getValues() = {
  	  this.values
  	}
   
   	def deleteValue(value: Int) {
   	  this.values = this.values.filter((n) => n != value)
   	  this.totalElements = this.totalElements - 1
   	}
   	
   	def isEmpty() = {
   		this.totalElements == 0
   	}
   	
   	def cardinality() = {
   		this.totalElements
   	}
}
