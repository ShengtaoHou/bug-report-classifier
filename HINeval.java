import mulan.classifier.MultiLabelOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by 29952 on 2018/5/18.
 */
public class HINeval {
    public static void main(String[] args) throws Exception {
        String line="";
        int linenum;
        int DEVNUM=18;
        int TESTNUM=4895;
        double thold=-0.1;
        double[][] result=new double[TESTNUM+1][DEVNUM+1];
        int[][] ranking=new int[TESTNUM+1][DEVNUM+1];
        double[][] answer=new double[TESTNUM+1][DEVNUM+1];

        for(int th=1;th<=20;th++) {
            thold+=0.05;
            linenum=1;
            System.out.println();
            System.out.println("thold: " + thold);
            try {
                BufferedReader resultReader = new BufferedReader(new FileReader("C:\\code\\idea\\BugReportClassifier\\data\\predict_result2.txt"));
                BufferedReader answerReader = new BufferedReader(new FileReader("C:\\code\\idea\\BugReportClassifier\\data\\right_answer.txt"));


                while ((line = resultReader.readLine()) != null) {
                    String[] temp = line.split(" ");
                    for (int i = 0; i < 18; i++) {
                        result[linenum][i] = Double.parseDouble(temp[i]);
                        MultiLabelOutput multiLabelOutput = new MultiLabelOutput(result[linenum]);
                        ranking[linenum] = multiLabelOutput.getRanking();
                    }
                    linenum++;
                }

                //System.out.println(result[0].length);

                linenum = 1;
                while ((line = answerReader.readLine()) != null) {
                    String[] temp = line.split(" ");
                    for (int i = 0; i < 18; i++)
                        answer[linenum][i] = Double.parseDouble(temp[i]);
                    linenum++;
                }

                double oneError = 0.0;
                int errorCount = 0;
                for (int i = 1; i < result.length; i++) {
                    int numLabels = 18;
                    for (int topRated = 0; topRated < numLabels; topRated++) {
                        if (ranking[i][topRated] == 1) {
                            //System.out.println(topRated);
                            if (answer[i][topRated] == 0.0) {
                                errorCount++;
                            }
                            break;
                        }
                    }
                /*
                double maxvalue=result[i][0];
                int maxindex=0;
                for(int j=0;j<18;j++){
                    if(result[i][j]>maxvalue){
                        maxvalue=result[i][j];
                        maxindex=j;
                    }
                }
                //System.out.println(answer[i][maxindex]);
                if(answer[i][maxindex]==0.0){
                    errorCount++;
                }
                */
                }
                oneError = 1.0 * errorCount / TESTNUM;
                System.out.println("one error: " + oneError);

                double hammingLoss = 0.0;
                int lossCount = 0;
                for (int i = 1; i < result.length; i++) {
                    for (int j = 0; j < 18; j++) {
                        if (answer[i][j] == 0.0 && result[i][j] > thold) {
                            lossCount++;
                        } else if (answer[i][j] == 1.0 && result[i][j] <= thold) {
                            lossCount++;
                        }
                    }
                }
                hammingLoss = 1.0 * lossCount / (DEVNUM * TESTNUM);
                System.out.println("hamming loss: " + hammingLoss);

                //coverage part
                double coverage = 0.0;
                int covSum = 0;
                for (int i = 1; i < result.length; i++) {
                    int howDeep = 0;
                    int numLabels = 18;
                    for (int rank = numLabels; rank >= 1; rank--) {
                        int indexOfRank;
                        for (indexOfRank = 0; indexOfRank < numLabels; indexOfRank++) {
                            if (ranking[i][indexOfRank] == rank) {
                                break;
                            }
                        }
                        if (answer[i][indexOfRank] == 1.0) {
                            howDeep = rank - 1;
                            break;
                        }
                    }
                    covSum += howDeep;
                }
                coverage = 1.0 * covSum / (TESTNUM);
                System.out.println("coverage: " + coverage);

                // ranking loss part
                double rankingLoss = 0.0;
                for (int i = 1; i < result.length; i++) {
                    ArrayList<Integer> trueIndexes = new ArrayList<Integer>();
                    ArrayList<Integer> falseIndexes = new ArrayList<Integer>();
                    for (int labelIndex = 0; labelIndex < 18; labelIndex++) {
                        if (answer[i][labelIndex] == 1.0) {
                            trueIndexes.add(labelIndex);
                        } else {
                            falseIndexes.add(labelIndex);
                        }
                    }

                    if (!trueIndexes.isEmpty() && !falseIndexes.isEmpty()) {
                        int rolp = 0; // reversed ordered label pairs
                        for (int k : trueIndexes) {
                            for (int l : falseIndexes) {
                                //	if (output[instanceIndex].getConfidences()[trueIndexes.get(k)] <= output[instanceIndex].getConfidences()[falseIndexes.get(l)])
                                if (ranking[i][l] < ranking[i][k]) {
                                    rolp++;
                                }
                            }
                        }
                        rankingLoss += (double) rolp / (trueIndexes.size() * falseIndexes.size());
                    } else {
                        rankingLoss += 0.0;
                    }
                }
                System.out.println("ranking loss: " + rankingLoss / TESTNUM);

                //average precision part
                double averagePrecision = 0.0;
                double apsum = 0.0;

                for (int i = 1; i < result.length; i++) {

                    double avgP = 0;
                    int numLabels = 18;
                    List<Integer> relevant = new ArrayList<>();
                    for (int index = 0; index < numLabels; index++) {
                        if (thold < result[i][index]) {
                            relevant.add(index);
                        }
                    }

                    if (!relevant.isEmpty()) {
                        for (int r : relevant) {
                            double rankedAbove = 0;
                            for (int rr : relevant) {
                                if (ranking[i][rr] <= ranking[i][r]) {
                                    rankedAbove++;
                                }
                            }
                            avgP += (rankedAbove / ranking[i][r]);
                        }
                        avgP /= relevant.size();
                        apsum += avgP;
                    }
                }
                averagePrecision = 1.0 * apsum / TESTNUM;
                System.out.println("averagePrecision: " + averagePrecision);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
