package sudokusolver;

import javax.swing._
import java.awt._
import java.awt.event._

/**
 * Sudoku Solver
 * 
 * Authors: Emanuele Fabbri, Alessandro Bruni
 */


class SudokuSolverApplet extends JApplet {
	var _layout = new GridLayout
	var helloButton = new JButton("Hell-o world")
 
	private var projectName: String = ""

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