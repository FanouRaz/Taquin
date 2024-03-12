import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Taquin{
    private Grille solution;

    public Taquin(int n){
        ArrayList<ArrayList<Integer>> solution = new ArrayList<>();
        int c=1;

        for(int i=0; i<n ; i++){
            ArrayList<Integer> row = new ArrayList<>();
            
            for(int j=0; j<n; j++)
                row.add(c++ % (n*n));

            solution.add(row);
        }

        this.solution = new Grille(solution);
    }

    /*
     * Résoudre en un nombre de coup minimal le jeu, partant d'un état initial
     */
    public ArrayList<Grille> solve(Grille startState){
        HashMap<Grille, Grille> prev = new HashMap<>();
        ArrayDeque<Grille> toVisit = new ArrayDeque<>();
        ArrayList<Grille> minimumMoves = new ArrayList<>();

        System.out.println("Grille Initial: ");
        startState.afficher();
        System.out.println("Passons à la résolution: ");

        prev.put(startState, null);
        toVisit.addLast(startState);

    BFS:while(!toVisit.isEmpty()){
            Grille current = toVisit.removeFirst();
            ArrayList<Grille> neighbors = current.permutations();

            for(Grille neighbor : neighbors){
                if(!prev.containsKey(neighbor)){
                    prev.put(neighbor, current);

                    if(neighbor.equals(solution)){
                        prev.put(solution,current);
                        break BFS;
                    }

                    toVisit.addLast(neighbor);
                }   
            }
        }

        if(prev.containsKey(solution)){
            minimumMoves.add(solution);

            do{
                if(prev.get(minimumMoves.get(0)) != null)
                    minimumMoves.add(0,prev.get(minimumMoves.get(0)));
            } while(!minimumMoves.contains(startState));
        }  

        else
            System.out.println("La grille initiale est insoluble!");
        
        return minimumMoves;
    }
}
