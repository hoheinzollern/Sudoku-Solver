package sudokusolver.utilities

import sudokusolver.exceptions

class Domain(private var associatedCell : String) {
	private var values : List[Int] = List(1,2,3,4,5,6,7,8,9)
  
 	def extractValue() = {
 	  var element = this.values.head
 	  this.values = this.values.filter((n) => n != element)
 	  element
 	}
  
  	def getValues() = {
  		if(this.values.length > 0) this.values
  		else throw new exceptions.EmptyDomain
  	}
  	
  	def getValue(position : Int) = {
  		/* positions are from 0 to 8 */
  		var restOfList = this.values
  		if (position >= 0 && position < this.values.length) {
  			for (i <-0 to position-1) {
  				restOfList = restOfList.tail
  			}
  		}
  		else throw new exceptions.DomainPositionNotFound
  		restOfList.head
  	}
   
   	def deleteValue(value: Int) {
   		if (!isEmpty) {
   			//println("Elimino il valore " + value + " dal dominio di " + getName)
   			this.values = this.values.filter((n) => n != value)
   		}
   	}
   	
   	def addValue(value : Int) {
   		this.values ::= value
   	}
   	
   	def empty() {
   		this.values = Nil
   	}
   	
   	def isEmpty() = {
   		this.values.length == 0
   	}
   	
   	def cardinality() = {
   		this.values.length
   	}
   	
   	def getName() = {
   		associatedCell
   	}
   	
   	def getStatus() = {
   		var iterator = values
   		var output = ""
   		while(iterator != Nil) {
   			output = output + " " + iterator.head
   			iterator = iterator.tail
   		}
   		output
   	}
}
