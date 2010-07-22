package sudokusolver;

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
	private var sudoku = null
	private val view = new View
	private val button = new JButton("Hello world")

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
}