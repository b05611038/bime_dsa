import  java.io.FileReader;

import java.io.BufferedReader;

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
            int indexTemp = -1;
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
                indexTemp = reference.indexOf(readsArray[i]);
                if (indexTemp != -1)
                    mapped[0]++;
                    //find the word frequency is not zero
                if (reference.indexOf(readsArray[i], (indexTemp + 1)) != -1)
                    mapped[1]++;
                    //find the word frequency more than one
            }
            
            System.out.println(mapped[0]);
            System.out.print(mapped[1]);
        }
    }
}
