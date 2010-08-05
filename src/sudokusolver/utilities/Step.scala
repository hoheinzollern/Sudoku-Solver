package sudokusolver.utilities

class Step(private var couple: Couple, private var value: Int = 0, private var message: String = "", private var domains: Array[Array[Set[Int]]]) {

  def getCouple() = {
    this.couple
  }

  def getMessage() = {
    this.message
  }

  def getValue() = {
    this.value
  }
  
  def getDomains() = {
	  this.domains
  }

}
