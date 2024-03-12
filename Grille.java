import java.util.ArrayList;
import java.util.Random;


public class Grille {
    private ArrayList<ArrayList<Integer>> grid;

    public Grille(ArrayList<ArrayList<Integer>> grid){
        this.grid = new ArrayList<>();

        for(ArrayList<Integer> row : grid){
            ArrayList<Integer> temp = new ArrayList<>();
            
            for(Integer elt : row)
                temp.add(elt);
            
            this.grid.add(temp);
        }
    }

    public static Grille solutionGrid(int n){
        int[][] solution = new int[n][n];
        int c=1;

        for(int i=0; i < n ; i++){
            for(int j=0; j < n ; j++)
                solution[i][j] = (c++) % (n*n);
        }

        return new Grille(solution);
    }

    public Grille(int[][] grid){
        this.grid = new ArrayList<>();

        for(int[] row : grid){
            ArrayList<Integer> temp = new ArrayList<>();
            
            for(int elt : row)
                temp.add(elt);

            this.grid  
                .add(temp);
        }
    }
    
    /*
     * Permet d'obtenir les permutations possibles obtenue en 1 déplacement 
     */
    public ArrayList<Grille> permutations(){
        ArrayList<Grille> perms = new ArrayList<>();
        int[] pos = this.getZeroPosition(), deplacements = {-1,1} ;

        for(int d : deplacements){
            if(pos[0] + d >= 0 && pos[0] + d < grid.size()){
                ArrayList<ArrayList<Integer>> perm = new ArrayList<>();

                for(int i=0 ; i < grid.size() ; i++){
                    ArrayList<Integer> row = new ArrayList<>();
                    for(int j=0; j < grid.size(); j++){
                        if(i==pos[0] && j==pos[1])
                            row.add(this.get(pos[0]+d,pos[1]));
                        else if(i==pos[0]+d  && j==pos[1])
                            row.add(0);
                        else
                            row.add(this.get(i,j));
                    }
                    perm.add(row);
                }
    
                perms.add(new Grille(perm));
            }
            
            if(pos[1] + d >= 0 && pos[1] + d < grid.size()){
                ArrayList<ArrayList<Integer>> perm = new ArrayList<>();
                
                for(int i=0; i < grid.size(); i++){
                    ArrayList<Integer> row = new ArrayList<>();
                    for(int j=0 ; j < grid.size() ; j++){
                        if(i==pos[0] && j==pos[1])
                        row.add(this.get(pos[0],pos[1]+d));
                        else if(i==pos[0]  && j==pos[1]+d)
                        row.add(0);
                        else
                        row.add(this.get(i,j));
                    }
                    perm.add(row);
                }
                
                perms.add(new Grille(perm));
            }
        }
        
        return perms;
    }
    
    /*
    * Recherche la position de la case contenant 0 dans la grille
    */
    public int[] getZeroPosition(){
        int[] pos = new int[2];
        
        row:for(int i=0; i < grid.size(); i++){
            for(int j=0; j < grid.size(); j++){
                if(this.get(i,j) == 0){
                    pos[0] = i;
                    pos[1] = j;
                    break row;
                }
            }
        }
        
        return pos;
    }

    public ArrayList<Grille> shuffle(){
        Grille current = this;
        Random rand = new Random();
        ArrayList<Grille> steps = new ArrayList<>();

        int shuffleNbre = 0, n = this.size();
        
        
        while(shuffleNbre < 3)
            shuffleNbre = rand.nextInt(n*n);

        steps.add(current);

        for(int i=0; i < shuffleNbre ; i++){
            ArrayList<Grille> neighbors = current.permutations(); 
            int rand_idx = rand.nextInt(neighbors.size());

            while(steps.contains(neighbors.get(rand_idx)))
                rand_idx = rand.nextInt(neighbors.size());
            
            current = neighbors.get(rand_idx);
            steps.add(current);
        }

        return steps;
    }
    
    public void afficher(){
        for(ArrayList<Integer> row : grid){
            for(Integer elt : row)
                System.out.printf("%d ",elt);
            
                System.out.println();
        }
    }
    
    public int get(int row, int column){
        return grid.get(row)
                   .get(column);
    }

    public void set(int row, int column, int value){
        grid.get(row)
            .set(column,value);
    }

    public int size(){
        return grid.size();
    }

    @Override
    public String toString(){
        return grid.toString();
    }
    
    @Override 
    public int hashCode(){
        return this.toString()
                   .hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o == null)
            return this == null;
        
        else if(o instanceof Grille){
            ArrayList<ArrayList<Integer>> toCompare = ((Grille) o).grid; 
    
            for(int i=0; i < grid.size() ; i++){
                for(int j=0; j < grid.get(i).size() ; j++){
                    if(grid.get(i).get(j) != toCompare.get(i).get(j))
                        return false;
                }
            }
            return true;
        }
        else return false;
    }
}
