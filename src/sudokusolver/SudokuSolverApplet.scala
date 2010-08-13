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
	private var solver = new solvers.GenericSolver(sudoku)
	solver.setPropagationAlgorithm(new solvers.ForwardChecking)
	solver.setSearchAlgorithm(new solvers.BacktrackingSearch)
	
	private val view = new View(sudoku)
	private val generateButton = new JButton("Generate!")
	private val resolveButton = new JButton("Resolve!")
	private var level = 0

	override def init = {
		val layout = new GroupLayout(getContentPane)
		setLayout(layout)
		
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup
                .addContainerGap
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(view)
                    .addComponent(generateButton)
                    .addComponent(resolveButton))
                .addContainerGap)
        );
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup
				.addContainerGap
				.addComponent(view)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(generateButton)
				.addComponent(resolveButton)
				.addContainerGap)
		)
	}
	generateButton.addActionListener(new ActionListener {
		def actionPerformed(evt: ActionEvent) {
			println("Generate!")
			/*var dominio = new utilities.Domain()
			var lista = dominio.getValues()
			println(lista)
			println("Ora elimino il 5...")
			dominio.deleteValue(5)
			lista = dominio.getValues()
			println(lista)
			println("Il primo elemento e' " + dominio.extractValue())
			println("Il dominio ha ora " + dominio.cardinality + " elementi")
			println("E per vedere se davvero l'ha estratto... ci riprovo... " + dominio.extractValue())
			println("E ora ne ha " + dominio.cardinality)*/
			// FIXME: view.generate(level)
			println("Generato nuovo schema con livello " + level + "!")
			level = level + 1
			if (level == 4) level = 0
		}
	})
	resolveButton.addActionListener(new ActionListener {
			def actionPerformed(evt: ActionEvent) {
				println("Resolve!")
				var dominio = new utilities.Domain("Test")
				println("L'elenco degli elementi è " + dominio.getValues)
				println("Voglio il primo: " + dominio.getValue(0))
				println("Voglio il quinto: " + dominio.getValue(4))
				println("Voglio l'ultimo: " + dominio.getValue(8))
				println("Ora ne cancello uno in mezzo")
				dominio.deleteValue(4)
				println("Ho cancellato il 4.. ora riprovo tutto!")
				println("L'elenco degli elementi è " + dominio.getValues)
				println("Voglio il primo: " + dominio.getValue(0))
				println("Voglio il quinto: " + dominio.getValue(4))
				println("Voglio l'ultimo: " + dominio.getValue(7))
			}
	})
}