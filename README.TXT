T Compile

javac EightPuzzle.java EightPuzzleComparator.java EightPuzzleRunner.java

to run a single instance of the program

java EightPuzzleRunner "123456780" -- where 123456780 is the state of a puzzle. 0 Represents a blank space.

the goal state is hard coded as 

123
456
780


Running EightPuzzle.sh will run a predetermined sequence of puzzles

Heuristic is hardcoded into the EightPuzzle class, so you will need to change the code and recompile in order to test out the two different ones 



HammingResult.txt contains the number of nodes and the shortest
path as obtained using the Hamming Heuristic

ManhattanResult.txt contains the number of nodes and the shortest
path as obtained using the Manhattan Heuristic

hardest_8puzzle.txt contains the number of nodes and the shortest
path on a hard puzzle, using the Manahattan Heuristic
