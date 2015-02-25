public class EightPuzzle implements Comparable<EightPuzzle>
{

    static int node_count = 0;
    private int[] puzzle = new int[9];

    // one eight puzzle can have maximum 4 neighbours
    // (a max of four moves is available from one state)
    private EightPuzzle[] neighbours = new EightPuzzle[4];
    private int h_value;
    private int g_value;
    private int f_value;

    public EightPuzzle(int[] p, int g){
        puzzle = p;
        g_value = g;

        h_value = h_manhattan(puzzle);
        //h_value = h_displaced(puzzle);

        f_value = h_value + g_value;

        node_count++;
    }

    public int get_f_value(){
        return f_value;
    }

    public int get_h_value(){
        return h_value;
    }
    public void print_neighbours(){
        for (int i=0; i<=3; i++){
            if (neighbours[i] != null){
                neighbours[i].printPuzzle();
            }
        }
    }
    public void generate_neighbours(){

        // first thing to do is find out which of the squares is 
        // blank / has a zero
        int pos = -1;
        int row = -1;
        int col = -1;
        int cur_neighbour = 0;
        for (int i=0; i<puzzle.length; i++){
            if (puzzle[i] == 0){
                row = i / 3;
                col = i % 3;
                pos = i;
            }
        }

        // now we should have the row and column that has a zero in it,
        // from here we can figure out the neighbours that this state has
        // there might be a better way than this, but I think this is okay

        // with a row of 0, we are guaranteed an upshift is possible.
        if (row == 0){
            neighbours[cur_neighbour] = shiftUp(pos);
            cur_neighbour++;
        }

        // with a row of 1 we are guaranteed a downshift and upshift is possible
        if (row == 1){
            neighbours[cur_neighbour] = shiftUp(pos);
            cur_neighbour++;
            neighbours[cur_neighbour] = shiftDown(pos);
            cur_neighbour++;
        }

        // with a row of 2 we are guaranteed a downshift is possible
        if (row == 2){
            neighbours[cur_neighbour] = shiftDown(pos);
            cur_neighbour++;
        }

        // with a column of 0 we are guaranteed a leftshift is possible
        if (col == 0){
            neighbours[cur_neighbour] = shiftLeft(pos);
            cur_neighbour++;
        }
        // with a column of 1 we are guaranteed a leftshift and right is possible
        if (col == 1){
            neighbours[cur_neighbour] = shiftLeft(pos);
            cur_neighbour++;
            neighbours[cur_neighbour] = shiftRight(pos);
            cur_neighbour++;

        }
        // with a column of 2 we are guaranteed a rightshift is possible
        if (col == 2){
            neighbours[cur_neighbour] = shiftRight(pos);
            cur_neighbour++;
        }


    }

    public EightPuzzle[] get_neighbours(){
        if (neighbours[0] == null){
            generate_neighbours();
        }
        return neighbours;
    }
    // heuristic based on how many tiles are displaced
    public int h_displaced(int[] p){
        
        int count = 0;
        // loop through the entire list, if the value
        // doesnt match the index increment counter+1
        for (int i=0; i < p.length ; i++){
            if (p[i] != i+1 && p[i] != 0){
                count++;
            }
        }

        return count;
    }

    // heuristic based on the manhattan distance of each tile from the goal position
    // for that tile.
    public int h_manhattan(int[] p){
        // I'm using a single dimensional array to store my puzzle, so
        // that means I need to do a little more work to figure out which
        // row and column each number is in.
        int cur_row, cur_column;
        int dest_row, dest_column;
        int md = 0;
        int result = 0;
        
        for (int i = 0; i < p.length; i++){
            md = 0;

            // calculate the current row and column
            cur_row = i / 3;
            cur_column = i % 3;

            if (p[i] != 0){
                // calculate the destination row and column
                dest_row = (p[i] -1) / 3;
                dest_column = (p[i]-1) % 3;
            }else{
                // using 0 as a blank makes things a bit confusing
                dest_row = 2;
                dest_column = 2;
            }
            // manhattan distance is abs value of destination - current on row
            // and column
            md = Math.abs(dest_row - cur_row) + Math.abs(dest_column - cur_column);

            result += md;
        }

        return result;
    }

    public int[] get_puzzle(){
        return puzzle;
    }

    // used to sort states by the f_value
    public int compareTo(EightPuzzle puzzle2){
        int val = 0;
        if (f_value < puzzle2.get_f_value()){
            val =  1;
        } else if (f_value == puzzle2.get_f_value()){
            val = 0;
        } else if (f_value > puzzle2.get_f_value()){
            val = -1;
        }
        return val;
    }

    // looks to see if two states are exactly equal
    public boolean equalTo(EightPuzzle puzzle2){
        int[] p2 = puzzle2.get_puzzle();
        for (int i = 0; i<puzzle.length; i++){
            if (puzzle[i] != p2[i])
                return false;
        }
        //System.out.println("YES EQUAL");
        return true;
    }

    // prints out the puzzle, mostly for testing purposes I guess.
    public void printPuzzle(){

        System.out.println(puzzle[0] + " " + puzzle[1] + " " + puzzle[2]);
        System.out.println(puzzle[3] + " " + puzzle[4] + " " + puzzle[5]);
        System.out.println(puzzle[6] + " " + puzzle[7] + " " + puzzle[8]);

    }

    // returns the eightpuzzle that occurs from an upshift at the given row and column
    private EightPuzzle shiftUp(int pos){
        // an upshift is the same as swapping the current element with the one that is three ahead
        int[] new_puzzle = puzzle.clone();
        int temp_val;

        temp_val = new_puzzle[pos];
        new_puzzle[pos] = new_puzzle[pos+3];
        new_puzzle[pos+3] = temp_val;

        return new EightPuzzle(new_puzzle, g_value+1);
    }
    
    // returns the eightpuzzle that occurs from a downshift at the given row and column
    private EightPuzzle shiftDown(int pos){
        // a downshift is the same as swapping hte current elemeent with the one that is three behind
        int[] new_puzzle = puzzle.clone();
        int temp_val;

        temp_val = new_puzzle[pos];
        new_puzzle[pos] = new_puzzle[pos-3];
        new_puzzle[pos-3] = temp_val;

        return new EightPuzzle(new_puzzle, g_value+1);
    }

    // returns the eightpuzzle that occurs from a leftshift at the given row and column
    private EightPuzzle shiftLeft(int pos){
        // a leftshift is the same as swapping with the element immediately after
        int[] new_puzzle = puzzle.clone();
        int temp_val;

        temp_val = new_puzzle[pos];
        new_puzzle[pos] = new_puzzle[pos+1];
        new_puzzle[pos+1] = temp_val;

        return new EightPuzzle(new_puzzle, g_value+1);
    }
    
    // returns the eightpuzzle that occurs from a rightshift at the given row and column
    private EightPuzzle shiftRight(int pos){
        // a rightshift is the same as swapping with the element immediately before
        int[] new_puzzle = puzzle.clone();
        int temp_val;

        temp_val = new_puzzle[pos];
        new_puzzle[pos] = new_puzzle[pos-1];
        new_puzzle[pos-1] = temp_val;

        return new EightPuzzle(new_puzzle, g_value+1);
    }

    // stringify the puzzle so we can use it in our hashmap
    public String stringify(){
        String val = "";
        int count = 0;
        for(int i : puzzle){
            val += i;
            count++;

          // put newlines in so it's more readable later on
            if (count % 3 == 0)
                val += "\n\r";
        }

  

        return val;
    }

    public int node_count(){
        return node_count;
    }
}
