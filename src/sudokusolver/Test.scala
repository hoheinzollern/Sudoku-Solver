package sudokusolver

import javax.swing._
import java.awt.Dimension

class Test extends JFrame {
	val view = new View
	
	setMinimumSize(new Dimension(240, 240))
	
	add(view)
}

object Test {
	def main(args: Array[String]) {
		val test = new Test
		test.setVisible(true)
	}
}