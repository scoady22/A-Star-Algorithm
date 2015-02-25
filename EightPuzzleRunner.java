import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;


public class EightPuzzleRunner
{
    public static void main(String[] args){

        // take in a command line string and load it into the puzzle
        // array
        // I'm not doing any exception handling, so hopefully we got
        // everything right in the command line
        int j = 0;
        int[] puzzle = new int[9];
        for (char c : args[0].toCharArray())
            puzzle[j++] = Character.getNumericValue(c);

        // initialize the problem
        int sol[] = new int[] {1,2,3,4,5,6,7,8,0};
        
        EightPuzzle sol_puzzle = new EightPuzzle(sol, 0);
        EightPuzzle e_puzzle = new EightPuzzle(puzzle, 0);
        PriorityQueue<EightPuzzle> open = new PriorityQueue(10, new EightPuzzleComparator());
        ArrayList<EightPuzzle> closed = new ArrayList();

        HashMap<String, String> path = new HashMap();

        open.add(e_puzzle);
        EightPuzzle current;
        // while we have elements in the open set
        while (open.size() != 0){
            EightPuzzle[] neighbours;
            current = open.poll();

            // WE FOUND THE SOLUTION
            if (current.equalTo(sol_puzzle)){
                // prints the path from the solution to the initial puzzle 
                // that we were given
                make_path(sol_puzzle.stringify(), path);
                System.out.println("Number of Nodes:" + current.node_count());
                System.exit(0);
            }
            
            closed.add(current);
            neighbours = current.get_neighbours();
            for (int i=0; i<4; i++){
                if (neighbours[i] != null){

                    //neighbours[i].printPuzzle();

                    // only do the following stuff if we haven't already evaluated this
                    // state
                    if (!closed_check(closed, neighbours[i])){
                        // add the element to the open set if it's not in there
                        if (!open_check(open, neighbours[i])){
                            path.put(neighbours[i].stringify(), current.stringify());
                            open.add(neighbours[i]);
                        }
                    }


                }
            }

        }
        System.exit(1);

    }

    // check to see if a given element is in the closed set
    public static boolean closed_check(ArrayList<EightPuzzle> closed, EightPuzzle state){
        // loop through all the puzzle states in the closed set
        for (EightPuzzle p : closed){
            if (p.equalTo(state))
                return true;
        }
        return false;
    }

    // check to see if a given element is in the open sen
    public static boolean open_check(PriorityQueue<EightPuzzle> open, EightPuzzle state){
        // loop through all the puzzle states in the closed set
        for (EightPuzzle p : open){
            if (p.equalTo(state))
                return true;
        }
        return false;
    }

    public static void printOpen(PriorityQueue<EightPuzzle> open){
        for (EightPuzzle p : open){
            p.printPuzzle();
            System.out.println("----");
        }
    }

    public static void printClosed(ArrayList<EightPuzzle> closed){
        for (EightPuzzle p: closed){
            p.printPuzzle();
            System.out.println("----");
        }
    }

    // this will traceback to find the solution path
    public static void make_path(String s, HashMap<String, String> path){
        if (path.get(s) != null){
            String next = path.get(s);

            // print out the current step
            System.out.println();
            System.out.println(s);


            make_path(next, path);
        }
    }
}


