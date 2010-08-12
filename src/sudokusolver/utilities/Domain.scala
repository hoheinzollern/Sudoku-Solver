package sudokusolver.utilities

import sudokusolver.exceptions

class Domain(private var associatedCell : String) {
	private var values : List[Int] = List(1,2,3,4,5,6,7,8,9)
	private var totalElements = 9
  
 	def extractValue() = {
 	  var element = this.values.head
 	  this.values = this.values.filter((n) => n != element)
 	  this.totalElements = this.totalElements - 1
 	  element
 	}
  
  	def getValues() = {
  		if(totalElements > 0) this.values
  		else throw new exceptions.EmptyDomain
  	}
  	
  	def getValue(position : Int) = {
  		/* positions are from 0 to 8 */
  		var restOfList = this.values
  		if (position >= 0 && position < this.totalElements) {
  			for (i<-0 to position-1) {
  				restOfList = restOfList.tail
  			}
  		}
  		else throw new exceptions.DomainPositionNotFound
  		restOfList.head
  	}
   
   	def deleteValue(value: Int) {
   		this.values = this.values.filter((n) => n != value)
   		this.totalElements = this.totalElements - 1
   	}
   	
   	def addValue(value : Int) {
   		this.values ::= value
   		this.totalElements = this.totalElements+1
   	}
   	
   	def isEmpty() = {
   		this.totalElements == 0
   	}
   	
   	def cardinality() = {
   		this.totalElements
   	}
   	
   	def getName() = {
   		associatedCell
   	}
}
