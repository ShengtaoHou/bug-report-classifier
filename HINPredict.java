import java.io.*;
import java.util.HashSet;
import java.util.Set;
import mulan.evaluation.measure.AveragePrecision;
import mulan.evaluation.measure.Measure;

/**
 * Created by 29952 on 2018/5/1.
 */
public class HINPredict {
    public static void main(String[] args) throws Exception {
        String line="";
        int linenum=1;
        int DEVNUM=18;
        int KNUM=5;
        int TESTNUM=4895;
        double[][] partnerCoeff=new double[DEVNUM+1][KNUM+1];
        int[][] partnerIndex=new int[DEVNUM+1][KNUM+1];
        double[][] predictResult=new double[TESTNUM+1][DEVNUM+1];
        double[][] predictResult2=new double[TESTNUM+1][DEVNUM+1];

        try{
            BufferedReader coeffReader = new BufferedReader(new FileReader("C:\\code\\idea\\BugReportClassifier\\data\\PartnerCoeff.txt"));
            BufferedReader partnerReader = new BufferedReader(new FileReader("C:\\code\\idea\\BugReportClassifier\\data\\topKPartner.txt"));
            BufferedReader predictReader = new BufferedReader(new FileReader("C:\\code\\idea\\BugReportClassifier\\data\\predict_result.txt"));

            while ((line = coeffReader.readLine()) != null) {
                String[] temp = line.split(" ");
                for(int i=1;i<=5;i++)
                    partnerCoeff[linenum][i]=Double.parseDouble(temp[i-1]);
                linenum++;
            }

            linenum=1;
            while ((line = partnerReader.readLine()) != null) {
                String[] temp = line.split(" ");
                for(int i=1;i<=5;i++)
                    partnerIndex[linenum][i]=Integer.parseInt(temp[i-1]);
                linenum++;
            }

            linenum=1;
            while ((line = predictReader.readLine()) != null) {
                String[] temp = line.split(" ");
                for(int i=1;i<=18;i++){
                    predictResult[linenum][i]=Double.parseDouble(temp[i-1]);
                    predictResult2[linenum][i]=Double.parseDouble(temp[i-1]);
                }
                linenum++;
            }
/*
            for(int i=1;i<predictResult.length;i++){
                for(int j=1;j<predictResult[0].length;j++){
                    System.out.print(predictResult[i][j]+" ");
                }
                System.out.println();
            }
            */

            for(int i=1;i<predictResult.length;i++){
                for(int j=1;j<=18;j++){
                    if(predictResult[i][j]>0.02){
                        for(int k=1;k<=KNUM;k++){
                            int index=partnerIndex[j][k];
                            double coeff=partnerCoeff[j][k];
                            if(predictResult[i][index]<=0.02){
                                predictResult2[i][index]=predictResult[i][j]*coeff;
                            }
                        }
                    }
                }
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\code\\idea\\BugReportClassifier\\data\\predict_result2.txt"), "utf-8"));

            for (int i = 1; i < predictResult2.length; i++) {
                for(int j = 1;j<=18;j++){
                    writer.write(Double.toString(predictResult2[i][j])+" ");
                }
                writer.newLine();
            }


        //evalation part:

        writer.flush();
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
            for(int i=0;i<partnerCoeff.length;i++){
                for(int j=0;j<partnerCoeff[0].length;j++){
                    System.out.print(partnerCoeff[i][j]+" ");
                }
                System.out.println();
            }

            for(int i=0;i<partnerIndex.length;i++){
                for(int j=0;j<partnerIndex[0].length;j++){
                    System.out.print(partnerIndex[i][j]+" ");
                }
                System.out.println();
            }

            for(int i=0;i<predictResult.length;i++){
                for(int j=0;j<predictResult[0].length;j++){
                    System.out.print(predictResult[i][j]+" ");
                }
                System.out.println();
            }
*/
