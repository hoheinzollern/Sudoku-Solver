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
	
	private var sudoku = new Sudoku
	private var solver = new solvers.GenericSolver
	solver.setPropagationAlgorithm(new solvers.ForwardChecking)
	solver.setSearchAlgorithm(new solvers.BacktrackingSearch)
	
	private val view = new View(sudoku)
	private val generateButton = new JButton("Generate!")
	private val resolveButton = new JButton("Resolve!")
	private var level = 0

	override def init = {
		add(new MainPanel)
	}
}