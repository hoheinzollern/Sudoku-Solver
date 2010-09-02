/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SudokuSolver.java
 *
 * Created on Sep 2, 2010, 3:46:42 PM
 */

package sudokusolver;

/**
 *
 * @author Alessandro
 */
public class SudokuSolver extends javax.swing.JFrame {

    /** Creates new form SudokuSolver */
    public SudokuSolver() {
    	setTitle("Sudoku Solver");
    	setSize(500, 500);
        add(new MainPanel());
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SudokuSolver().setVisible(true);
            }
        });
    }

}
