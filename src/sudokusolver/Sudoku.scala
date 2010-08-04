package sudokusolver

/**
 * This class represents the game with the fields of the scheme.
 */

class Sudoku (private val propagator: solvers.PropagationAlgorithm) {
	class InvalidValueException extends Exception
	
	class Step (val x: Int, val y: Int, val value: Int, val domains: Array[Array[Set[Int]]])
	
	private var numberFields = new Array[Array[Int]](9,9)
	private var domains = new Array[Array[Set[Int]]](9,9)
    private var stepList = List[Step]()
    private var views = List[View]()
	
	def set(x: Int, y: Int, value: Int) {
		if (value < 1 || value > 9) throw new InvalidValueException()
		numberFields(x)(y) = value
		val step = new Step(x, y, value, domains.clone)
		stepList = stepList :+ step
		propagator.revise(step)
		
		notifyView
	}
	
    def get(x: Int, y: Int) = numberFields(x)(y)
    
    def back {
    	val step = stepList.head
    	stepList = stepList - step
    	numberFields(step.x)(step.y) = 0
    	domains = step.domains
    }
    
    def notifyView {
    	for (view <- views)
    		view.update
    }
}
