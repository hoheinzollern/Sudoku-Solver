package sudokusolver

import scala.collection.mutable.LinkedList

/**
 * This class represents the game with the fields of the scheme.
 */

class Sudoku {
	private var numberFields: Array[Int] = new Array[Int](81)
    private var stepList: List[utilities.Step] = List[utilities.Step]()

    def addStep(couple: utilities.Couple, value: Int) {
      var step : utilities.Step = new utilities.Step()
      this.stepList ::= step
    }
    
    def extractStep() {
      this.stepList.head
    }
    
    /**
     * This method receives the stepList of the solution and updates
     * the Array numberFields with the solution calculated
     */
    def compileSolvedScheme(stepList : List[utilities.Step]) {
      this.stepList = stepList
      for (i <- 0 to this.stepList.length by 1) {
        var step = (this.stepList.drop(i)).head
        var field = step.getCouple.convertCouple
        numberFields(field) = step.getValue
      }
    }
    
    def notifyView() {
      //TODO
    }
}
