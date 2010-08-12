package sudokusolver.utilities

class SchemeContainer(private var scheme : Array[Array[Int]] = new Array[Array[Int]](9,9)) {
	private var schemeStepList : List[Operation] = Nil
	
	def get(x : Int, y : Int) = {
		scheme(x)(y)
	}
	
	def get() = {
		scheme
	}
	
	def set(value : Int, x : Int, y : Int) {
		this.schemeStepList = new Operation("add", x, y, scheme(x)(y)) :: this.schemeStepList
		scheme(x)(y) = value
	}
	
	def back() = {
		if (this.schemeStepList != Nil) {
			this.schemeStepList.head.restore(scheme)
			this.schemeStepList = this.schemeStepList.tail
		}
		clone
	}
	
	override def clone() = {
		new SchemeContainer(scheme.clone)
	}
}