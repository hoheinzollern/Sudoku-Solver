/*
 * To change this template, choose Tool

            @Override
            public boolean accept(File file) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getDescription() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        } | Templates
 * and open the template in the editor.
 */

/*
 * MainPanel.java
 *
 * Created on 13-ago-2010, 14.24.58
 */

package sudokusolver;

import scala.Array;
import sudokusolver.solvers.ForwardChecking;
import sudokusolver.solvers.GenericSolver;
import sudokusolver.utilities.BinaryConstraint;
import sudokusolver.utilities.Board;
import net.sf.dancingsudoku.DancingSudoku;

/**
 *
 * @author alessandro
 */
public class MainPanel extends javax.swing.JPanel {

    private Sudoku sudoku = new Sudoku(Core.getConstraintMatrix());

    /** Creates new form MainPanel */
    public MainPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        levelGroup = new javax.swing.ButtonGroup();
        mediumLevelRadio = new javax.swing.JRadioButton();
        hardLevelRadio = new javax.swing.JRadioButton();
        easyLevelRadio = new javax.swing.JRadioButton();
        sudokuPanel = new javax.swing.JPanel();
        view = new View(new Sudoku(Core.getConstraintMatrix()));
        jScrollPane1 = new javax.swing.JScrollPane();
        eventsLogList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        useSearchCheckBox = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        newGameButton = new javax.swing.JButton();
        usePropagationCheckBox = new javax.swing.JCheckBox();
        resetGameButton = new javax.swing.JButton();
        constraintPropagationCombo = new javax.swing.JComboBox();
        loadGameButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        searchAlgotihmCombo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        saveGameButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();

        levelGroup.add(mediumLevelRadio);
        mediumLevelRadio.setSelected(true);
        mediumLevelRadio.setText("Medium");

        levelGroup.add(hardLevelRadio);
        hardLevelRadio.setText("Hard");

        levelGroup.add(easyLevelRadio);
        easyLevelRadio.setText("Easy");

        sudokuPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout sudokuPanelLayout = new javax.swing.GroupLayout(sudokuPanel);
        sudokuPanel.setLayout(sudokuPanelLayout);
        sudokuPanelLayout.setHorizontalGroup(
            sudokuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
        );
        sudokuPanelLayout.setVerticalGroup(
            sudokuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(view, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(eventsLogList);

        jLabel1.setText("Events log:");

        useSearchCheckBox.setSelected(true);
        useSearchCheckBox.setText("Use Search Algorithm");
        useSearchCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useSearchCheckBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Search algorithm:");

        newGameButton.setText("New Game");
        newGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameButtonActionPerformed(evt);
            }
        });

        usePropagationCheckBox.setText("Use Propagation Algorithm");
        usePropagationCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usePropagationCheckBoxActionPerformed(evt);
            }
        });

        resetGameButton.setText("Reset Game");
        resetGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetGameButtonActionPerformed(evt);
            }
        });

        constraintPropagationCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Forward checking", "Partial look-ahead", "Arc consistency" }));
        constraintPropagationCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                constraintPropagationComboActionPerformed(evt);
            }
        });

        loadGameButton.setText("Load Game");
        loadGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadGameButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Constraint propagation:");

        searchAlgotihmCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Backtracking Search", "Dancing Links" }));
        searchAlgotihmCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAlgotihmComboActionPerformed(evt);
            }
        });

        jLabel3.setText("Algorithms");

        saveGameButton.setText("Save Game");
        saveGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGameButtonActionPerformed(evt);
            }
        });

        startButton.setText("Start solving");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop solving");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(constraintPropagationCombo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchAlgotihmCombo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(useSearchCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(usePropagationCheckBox, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(stopButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(startButton, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(newGameButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loadGameButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resetGameButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveGameButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(newGameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetGameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadGameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveGameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(useSearchCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usePropagationCheckBox)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchAlgotihmCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(constraintPropagationCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton)
                .addContainerGap(258, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addComponent(sudokuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(sudokuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameButtonActionPerformed
        int level;
        if (easyLevelRadio.isSelected())
            level = 1;
        else if (mediumLevelRadio.isSelected())
            level = 2;
        else
            level = 3;
        sudoku = Core.makeSudoku(level);
        ((View)view).setSudoku(sudoku);
    }//GEN-LAST:event_newGameButtonActionPerformed

    private void resetGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetGameButtonActionPerformed
        sudoku.reset();
    }//GEN-LAST:event_resetGameButtonActionPerformed

    private void loadGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadGameButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loadGameButtonActionPerformed

    private void saveGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveGameButtonActionPerformed
    	//Core.saveSudoku("H:\\Desktop\\salvato.sudoku", sudoku);
    }//GEN-LAST:event_saveGameButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
    	/* Alex implementation 
    	DancingSudoku ds = new DancingSudoku(3);
    	int[][] board = sudoku.getBoard().getJavaBoard().getMatrix();
    	ds.setPuzzleToSolve(board);
    	board = ds.run();
    	System.out.println(board);
    	Board b = new Board();
    	for (int i = 0; i < 9; i++)
    		for (int j = 0; j < 9; j++)
    			b.setValue(i, j, board[i][j]);
    	sudoku.setBoard(b);
    	sudoku.notifyView();
		*/
    	Sudoku sudoku = ((View)view).getSudoku();
    	GenericSolver solver = new ForwardChecking(sudoku);
    	solver.execute();
    	//GenericSolver gs = new GenericSolver();
    	//gs.setProblem(sudoku);
    	//gs.setPropagationAlgorithm(new ForwardChecking());
    	//gs.setSearchAlgorithm();
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stopButtonActionPerformed

    private void searchAlgotihmComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchAlgotihmComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchAlgotihmComboActionPerformed

    private void constraintPropagationComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constraintPropagationComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_constraintPropagationComboActionPerformed

    private void useSearchCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useSearchCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_useSearchCheckBoxActionPerformed

    private void usePropagationCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usePropagationCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usePropagationCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox constraintPropagationCombo;
    private javax.swing.JRadioButton easyLevelRadio;
    private javax.swing.JList eventsLogList;
    private javax.swing.JRadioButton hardLevelRadio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.ButtonGroup levelGroup;
    private javax.swing.JButton loadGameButton;
    private javax.swing.JRadioButton mediumLevelRadio;
    private javax.swing.JButton newGameButton;
    private javax.swing.JButton resetGameButton;
    private javax.swing.JButton saveGameButton;
    private javax.swing.JComboBox searchAlgotihmCombo;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JPanel sudokuPanel;
    private javax.swing.JCheckBox usePropagationCheckBox;
    private javax.swing.JCheckBox useSearchCheckBox;
    private java.awt.Canvas view;
    // End of variables declaration//GEN-END:variables

}
