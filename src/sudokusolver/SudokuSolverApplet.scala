package sudokusolver;
import java.awt.event._
import javax.swing.JButton
import javax.swing.JApplet
import javax.swing.GroupLayout
import javax.swing.LayoutStyle

/**
 * Sudoku Solver
 * 
 * Authors: Emanuele Fabbri, Alessandro Bruni
 */
class SudokuSolverApplet extends JApplet {
	override def init = {
		add(new MainPanel)
	}
}