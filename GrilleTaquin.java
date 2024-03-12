import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class GrilleTaquin extends JPanel {
    private Taquin taquin;
    private JPanel center;
    private JButton[][] cases; 
    private Grille grid;
    private JPanel buttonPanel;
    private JPanel textPane;
    private JLabel moves;
    private JLabel times;
    private Timer solveTimer;
    private Timer shuffleTimer;

    public GrilleTaquin(int n){
       BoxLayout layout = new BoxLayout (this, BoxLayout.Y_AXIS);
       BoxLayout textLayout;    

       ImageIcon shuffleIcon = new ImageIcon("C:\\Users\\PRODIGY-217\\Documents\\Mr Tsinjo Fanou\\Taquin\\Icons\\shuffle.png");
       ImageIcon playIcon = new ImageIcon("C:\\Users\\PRODIGY-217\\Documents\\Mr Tsinjo Fanou\\Taquin\\Icons\\play.png");

       JPanel pane = new JPanel();
       JButton solve = new JButton(playIcon);
       JButton shuffle = new JButton(shuffleIcon);
    
       ArrayList<Grille> initialGrid = Grille.solutionGrid(n)
                                              .shuffle();

       buttonPanel = new JPanel();
 
       this.setLayout(layout);
        
       grid = initialGrid.get(initialGrid.size()-1);

       taquin = new Taquin(n);
       center = new JPanel(new GridLayout(n,n));
       textPane = new JPanel();
       cases = new JButton[n][n];
       times = new JLabel("");
       moves = new JLabel("Moves: 0");
        
        
       textLayout = new BoxLayout(textPane, BoxLayout.Y_AXIS);
       textPane.setLayout(textLayout);

        center.setPreferredSize(new Dimension(300,300));

        for(int i=0; i<grid.size() ; i++){
            for(int j=0; j < grid.size(); j++){
                JButton button = new JButton(grid.get(i,j) == 0 ? " " : String.valueOf(grid.get(i,j)));
                
                button.setBackground(grid.get(i,j) == 0 ? Color.WHITE : new Color(110, 153, 233));
                button.setSize(100, 100);
                
                button.setBorder(BorderFactory.createLineBorder(new Color(43, 43, 43)));

                button.addActionListener(e -> {
                    int numClicked = Integer.valueOf(((JButton) e.getSource()).getText() == "" ? "0" : ((JButton)e.getSource()).getText() );
                    int[] pos = getIndexesNum(numClicked) , deplacements = {-1,1};
                    
                    if(!grid.equals(Grille.solutionGrid(n))){
                        for(int d : deplacements){
                            if(pos[0]+d >=0 && pos[0]+d < grid.size()){
                                if(grid.get(pos[0]+d,pos[1]) == 0){
                                    cases[pos[0]][pos[1]].setText("");
                                    cases[pos[0]+d][pos[1]].setText(String.valueOf(numClicked));
        
                                    cases[pos[0]][pos[1]].setBackground(Color.WHITE);
                                    cases[pos[0]+d][pos[1]].setBackground(new Color(110,153,233));
        
                                    grid.set(pos[0],pos[1],0);
                                    grid.set(pos[0]+d,pos[1],numClicked);
                                    
                                    moves.setText(String.format("Moves: %d",Integer.valueOf(moves.getText().substring(7))+1));
                                    
                                    if(grid.equals(Grille.solutionGrid(n)))
                                        JOptionPane.showMessageDialog(this, String.format("Vous avez résolu la grille en %s coups!", moves.getText().substring(7)));
                                    return;
                                }
                            }
                            if(pos[1]+d >=0 && pos[1]+d < grid.size()){
                                if(grid.get(pos[0],pos[1]+d) == 0){
                                    cases[pos[0]][pos[1]].setText("");
                                    cases[pos[0]][pos[1]+d].setText(String.valueOf(numClicked));
        
                                    cases[pos[0]][pos[1]].setBackground(Color.WHITE);
                                    cases[pos[0]][pos[1]+d].setBackground(new Color(110,153,233));
        
                                    grid.set(pos[0],pos[1],0);
                                    grid.set(pos[0],pos[1]+d,numClicked);
    
                                    moves.setText(String.format("Moves: %d",Integer.valueOf(moves.getText().substring(7))+1));
    
                                    if(grid.equals(Grille.solutionGrid(n)))
                                        JOptionPane.showMessageDialog(this, String.format("Vous avez résolu la grille en %s coups!", moves.getText().substring(7)));
                                    return;
                                }
                            }
                        }
                    }
                });

                center.add(button);
                cases[i][j] = button;
            }
        }
        
        solve.setSize(30,30);

        shuffle.setSize(30,30);
        shuffle.setBackground(Color.yellow);

        pane.setLayout(new FlowLayout(FlowLayout.CENTER,30,100));
        
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));
        
        solve.addActionListener(e -> {
            long startTime = System.currentTimeMillis();
            ArrayList<Grille> minMoves = taquin.solve(grid);
            long endTime = System.currentTimeMillis();
             
            final int[] currentMoveIndex = new int[]{0};
            
            moves.setText(String.format("Moves: %d",0));

            ActionListener timerAction = evt -> {
                if (currentMoveIndex[0] < minMoves.size()) {
                    Grille move = minMoves.get(currentMoveIndex[0]++);
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            grid.set(i,j,move.get(i,j));
                            cases[i][j].setText(move.get(i, j) != 0 ? String.valueOf(move.get(i, j)) : "");
                            cases[i][j].setBackground(move.get(i, j) == 0 ? Color.WHITE : new Color(110, 153, 233));
                        }
                    }
                    moves.setText(String.format("Moves: %d",Integer.valueOf(moves.getText().substring(7))+1));
                } 
                else 
                    ((Timer) evt.getSource()).stop();
            };

            times.setText(String.format("Times: %dms",endTime - startTime));
                    
            solveTimer = new Timer(600, timerAction);
            solveTimer.start();
        });

        shuffle.addActionListener(e ->{ 
            ArrayList<Grille> steps = grid.shuffle();
            final int[] currentMoveIndex = new int[]{0};
            
            ActionListener timerAction = evt -> {
                if (currentMoveIndex[0] < steps.size()) {
                    Grille move = steps.get(currentMoveIndex[0]++);
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            grid.set(i,j,move.get(i,j));
                            cases[i][j].setText(move.get(i, j) != 0 ? String.valueOf(move.get(i, j)) : "");
                            cases[i][j].setBackground(move.get(i, j) == 0 ? Color.WHITE : new Color(110, 153, 233));
                        }
                    }
                    moves.setText("Moves: 0");
                    times.setText("Times: ");
                } else 
                    ((Timer) evt.getSource()).stop();
            };
            
            shuffleTimer = new Timer(400, timerAction);
            shuffleTimer.start();
        });

        buttonPanel.add(shuffle);
        buttonPanel.add(solve);
        pane.add(center);
        
        textPane.add(moves);
        textPane.add(times);
        
        this.add(pane);
        this.add(textPane);
        this.add(buttonPanel);
    }

    private int[] getIndexesNum(int num){
        int[] pos = new int[2];

    row:for(int i=0; i < grid.size(); i++){
            for(int j=0; j< grid.size(); j++){
                if(grid.get(i,j) == num){
                    pos[0] = i;
                    pos[1] = j;
                    break row;
                }
            }
        }

        return pos;
    }
}
