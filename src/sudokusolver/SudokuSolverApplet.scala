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
	private val view = new View
	private val button = new JButton("Test me!")

	override def init = {
		val layout = new GroupLayout(getContentPane)
		setLayout(layout)
		
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup
                .addContainerGap
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(view)
                    .addComponent(button))
                .addContainerGap)
        );
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup
				.addContainerGap
				.addComponent(view)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(button)
				.addContainerGap)
		)
	}
	button.addActionListener(new ActionListener {
			def actionPerformed(evt: ActionEvent) {
				println("Hell-o world")
				var dominio = new utilities.Domain()
				var lista = dominio.getValues()
				println(lista)
				println("Ora elimino il 5...")
				dominio.deleteValue(5)
				lista = dominio.getValues()
				println(lista)
				println("Il primo elemento e' " + dominio.extractValue())
				println("Il dominio ha ora " + dominio.cardinality + " elementi")
				println("E per vedere se davvero l'ha estratto... ci riprovo... " + dominio.extractValue())
				println("E ora ne ha " + dominio.cardinality)
			}
		})
}