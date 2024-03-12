import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class Fenetre extends JFrame {
    private GrilleTaquin grille;

    public Fenetre(){
        Container pane = getContentPane();
        BoxLayout layout = new BoxLayout (pane, BoxLayout.Y_AXIS);

        pane.setLayout(layout);

        grille = new GrilleTaquin(3);
        
        pane.add(grille);
        
        setSize(450,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Taquin");
        setResizable(false);
        setVisible(true);
    }
}
