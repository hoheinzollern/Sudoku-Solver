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
				var dominio = new utilities.Domain()
				var lista = dominio.getValues()
				println(lista)
				println("Ora elimino il 5...")
				dominio.deleteValue(5)
				lista = dominio.getValues()
				println(lista)
				println("Il primo elemento è " + dominio.extractValue())
				println("E per vedere se davvero l'ha estratto... ci riprovo... " + dominio.extractValue())
			}
		})
	}
}