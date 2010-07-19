package sudokusolver.utilities

class Step {
	private var couple: Couple = null
	private var value: Int = 0
	private var message: String = ""
 
	def Step(couple : Couple, value : Int, message : String) {
		this.couple = couple
		this.value = value
		this.message = message
	}
 
 	def getCouple() = {
 	  this.couple
 	}
  
 	def getMessage() = {
 	  this.message
 	}
  
 	def getValue() = {
 	  this.value
 	}
	 
 	
 
}
