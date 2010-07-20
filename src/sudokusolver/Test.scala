package sudokusolver

import javax.swing._

class Test extends JFrame {
	val view = new View
	
	add(view)
}

object Test {
	def main(args: Array[String]) {
		val test = new Test
		test.setVisible(true)
	}
}