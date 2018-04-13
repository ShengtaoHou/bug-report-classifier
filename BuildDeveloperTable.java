/**
 * Created by 29952 on 2018/4/10.
 */
import java.io.*;
import java.util.HashMap;

public class BuildDeveloperTable {
    public HashMap<String, Integer> build() {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        // The name of the file to open.
        String fileName = "C:\\code\\idea\\BugReportClassifier\\data\\developer.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            int id=1;
            while(id<=154&&(line = bufferedReader.readLine()) != null) {
                map.put(line,id++);
            }
            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return map;
    }
}

