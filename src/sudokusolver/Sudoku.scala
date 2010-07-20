package sudokusolver

import scala.collection.mutable.LinkedList

/**
 * This class represents the game with the fields of the scheme.
 */

class Sudoku {
	private var numberFields = new Array[Array[Int]](9,9)
    private var stepList: List[utilities.Step] = List[utilities.Step]()
    private var views: List[View] = List[View]()

    /**
     * This method adds a Step to the stepList
     */
    def addStep(couple: utilities.Couple, value: Int) {
      var step : utilities.Step = new utilities.Step()
      this.stepList ::= step
    }
    
    /**
     * This method extract the first element from stepList
     */
    def extractStep {
      var element = this.stepList.head
      this.stepList = this.stepList.filter((n) => n != element)
 	  element
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
    
    def notifyView {
    	for (view <- views)
    		view.update
    }
    
    def getBoard = numberFields
}
