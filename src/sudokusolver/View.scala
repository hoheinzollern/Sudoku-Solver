package sudokusolver

import java.awt.Canvas
import java.awt.Graphics
import java.awt.FontMetrics
import java.awt.Dimension

class View extends Canvas {
	var solver = new solvers.GenericSolver(new Core)
	solver.setPropagationAlgorithm(new solvers.ForwardChecking)
	private var sudoku = new Sudoku(solver)
	
	override def paint(g : Graphics) {
		val width = getWidth
		val height = getHeight
		
		val stepX = width / 9
		val stepY = height / 9
		
		for (i <- 1 to 8) {
			if (i%3 != 0) {
				g.drawLine(stepX * i, 0, stepX * i, height)
				g.drawLine(0, i * stepY, width, i * stepY)
			} else {
				g.drawLine(stepX * i-1, 0, stepX * i-1, height)
				g.drawLine(stepX * i+1, 0, stepX * i+1, height)
				g.drawLine(0, i * stepY-1, width, i * stepY-1)
				g.drawLine(0, i * stepY+1, width, i * stepY+1)
			}
		}
		
		val fm = g.getFontMetrics
		
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				val element = sudoku.get(j,i)
				if (element != 0) {
					val text = element.toString
					val lm = fm.getLineMetrics(text, g)
					g.drawString(text,
							i * stepX + (stepX/2.0f + lm.getLeading/2).toInt,
							j * stepY + (stepY/2.0f + lm.getHeight/2).toInt)
				}
			}
		}
	}
	
	def update = repaint

	def generate(difficulty : Int) {
		if (difficulty >= 0 && difficulty <= 3) {
			sudoku.generate(difficulty)
			update
		}
	}
}