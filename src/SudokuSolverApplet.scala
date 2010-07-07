/**
 * Sudoku Solver
 * 
 * Authors: Emanuele Fabbri, Alessandro Bruni
 */

import javax.swing._
import java.awt._
import java.awt.event._

class SudokuSolverApplet extends JApplet {
	var _layout = new GridLayout
	var helloButton = new JButton("Hell-o world")

	override def init = {
		setLayout(_layout)
		add(helloButton)
		
		helloButton.addActionListener(new ActionListener {
			def actionPerformed(evt: ActionEvent) {
				println("Hell-o world")
			}
		})
	}
}