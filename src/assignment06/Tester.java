package assignment06;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Tester {

    public static void main(String args[]) throws IOException,
            FileNotFoundException, ClassNotFoundException {

        //download
        Summary.main(new String[]{"download", "people.csv"});

        //summary - bin
        Summary.main(new String[]{"summary", "people.csv"});
        System.out.println("-------------------------------");

        //print - csv
        Summary.main(new String[]{"print", "people.csv"});

        //print - csv - various sorters
        System.out.println("N:-------------------------------");
        Summary.main(new String[]{"print", "people.csv", "n"});
        System.out.println("A:-------------------------------");
        Summary.main(new String[]{"print", "people.csv", "a"});
        System.out.println("H:-------------------------------");
        Summary.main(new String[]{"print", "people.csv", "h"});

        //print - csv - various sorters - reversed
        System.out.println("NR:-------------------------------");
        Summary.main(new String[]{"print", "people.csv", "n", "true"});
        System.out.println("AR:-------------------------------");
        Summary.main(new String[]{"print", "people.csv", "a", "true"});
        System.out.println("HR:-------------------------------");
        Summary.main(new String[]{"print", "people.csv", "h", "true"});
    }
}
