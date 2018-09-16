import  java.io.*;

public class Mapping {
    public static void main(String[] args) throws Exception {
        // read file from args[0] in Java 7 style
        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))){
            // read a line
            String data = br.readLine();
            // store the first integer in variable readCount (number of reads)

            int readCount = Integer.parseInt(data);
            int[] mapped = {0, 0};
            //can be mapped or not
            int sequence = 0;
            String[] readsArray = new String[readCount];
            String reference = new String();
            //set the variable


            for (int i = 0; i < readCount; i++) {
                readsArray[i] = (String) br.readLine();
            }
            //read the cheched stream in to program
            reference = (String) br.readLine();
            //read the problem to the program

            br.close();
            //close the bufferreader

            for (int i = 0; i < readCount; i++) {
                //initialize the number of frequency
                sequence = reference.indexOf(readsArray[i]);
                //to chech the word are in the stream or not
                if (sequence == -1)
                    mapped[1]++;
                else {
                    mapped[0]++;
                    //find the word and add the frequency
                }
            }
            
            System.out.println(mapped[0]);
            System.out.println(mapped[1]);
            /*
            File outFile = new File("output.txt");
            FileOutputStream outStream = new FileOutputStream(outFile);
            //build the output.txt

            OutputStreamWriter writer = new OutputStreamWriter(outStream, "UTF-8");
            //create a writer object to outfile the string

            writer.append(Integer.toString(mapped[0]));
            //the number can be mapped
            writer.append("\n");
            writer.append(Integer.toString(mapped[1]));
            //the number can't be mapped

            writer.close();
            outStream.close();
            //close the outstream and finish file writing
            */

            /*  now you can write your own solution to hw0
             *  you can follow the instruction described below:
             *
             *  1. read the rest content of the file
             *  2. store the reads in variable readsArray
             *  3. store the reference sequence in variable reference
             *  4. compare every reads with reference sequence
             *  5. output how many reads can be mapped to the reference sequence
             *
             *  [note]
             *  you can use every data structure in standard Java packages (Java 8 supported)
             *  the packages in stdlib.jar and algs4.jar are also available for you to use
             *
             *  [hint]
             *  1. you can use String.contain() method when comparing strings.
             *  2. you should check whether Java pass the variable by references or by values.
             *  3. some data structure such as HashSet, HashMap, Arrays, ArrayList, Vector are very
             *     useful for solving problems.
             */
        }
    }
}
