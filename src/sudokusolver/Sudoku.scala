package sudokusolver

/**
 * This class represents the game with the fields of the scheme.
 */

class Sudoku (private var solver: solvers.GenericSolver) {
	class InvalidValueException extends Exception
		
	private var numberFields = new Array[Array[Int]](9,9)
	private var domains = new Array[Array[Set[Int]]](9,9)
    private var stepList = List[utilities.Step]()
    private var views = List[View]()
	
	def set(x: Int, y: Int, value: Int, message : String) {
		if (value < 1 || value > 9) throw new InvalidValueException()
		numberFields(x)(y) = value
		val step = new utilities.Step(new utilities.Couple(x, y), value, message, domains.clone)
		stepList = stepList :+ step
				
		notifyView
	}
	
    def get(x: Int, y: Int) = numberFields(x)(y)
    
    def back {
    	val step = stepList.head
    	stepList = stepList - step
    	numberFields(step.getCouple.getX)(step.getCouple.getY) = 0
    	domains = step.getDomains
    }
    
    def notifyView {
    	for (view <- views)
    		view.update
    }
}
