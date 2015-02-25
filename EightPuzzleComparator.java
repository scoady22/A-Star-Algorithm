import java.util.Comparator;
public class EightPuzzleComparator implements Comparator<EightPuzzle>
{


    public int compare(EightPuzzle x, EightPuzzle y)
    {
        if (x.get_f_value() < y.get_f_value()){
            return -1;
        }

        if (x.get_f_value() > y.get_f_value()){
            return 1;
        }
        
        return 0;
    }

}
