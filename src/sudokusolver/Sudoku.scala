package sudokusolver

import scala.collection.mutable.LinkedList

class Sudoku {
	private var numberFields = new Array[Array[Int]](9,9)
    private var stepList: List[utilities.Step] = List[utilities.Step]()
    private var views: List[View] = List[View]()

    def addStep(couple: utilities.Couple, value: Int) {
      var step : utilities.Step = new utilities.Step()
      this.stepList ::= step
    }
    
    def extractStep = this.stepList.head
    
    def notifyView {
    	for (view <- views)
    		view.update
    }
    
    def getBoard = numberFields
}
