import mulan.classifier.MultiLabelOutput;
import mulan.classifier.lazy.BRkNN;
import mulan.classifier.lazy.MLkNN;
import mulan.classifier.meta.RAkEL;
import mulan.classifier.transformation.BinaryRelevance;
import mulan.classifier.transformation.ClassifierChain;
import mulan.classifier.transformation.LabelPowerset;
import mulan.data.MultiLabelInstances;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;


public class MultiLabelsPredict {

    public static void main(String[] args) throws Exception {
        String trainFilename = "C:\\code\\idea\\BugReportClassifier\\data\\train_data.arff"; // e.g. -arff emotions.arff
        String xmlFilename = "C:\\code\\idea\\BugReportClassifier\\data\\developer.xml"; // e.g. -xml emotions.xml
        String testFilename = "C:\\code\\idea\\BugReportClassifier\\data\\test_data.arff"; // e.g. -arff emotions.arff
        String resultFile="C:\\code\\idea\\BugReportClassifier\\data\\predict_result.txt";

        //MultiLabelInstances datatrain = new MultiLabelInstances(trainFilename, xmlFilename);
/*
        RAkEL model = new RAkEL(new LabelPowerset(new J48()));
        model.build(datatrain);
        weka.core.SerializationHelper.write("C:\\code\\idea\\BugReportClassifier\\data\\RAkEL.model", model);
*/
        //BinaryRelevance model = (BinaryRelevance) weka.core.SerializationHelper.read("C:\\code\\idea\\BugReportClassifier\\data\\BR.model");
        //RAkEL model = (RAkEL) weka.core.SerializationHelper.read("C:\\code\\idea\\BugReportClassifier\\data\\RAKEL.model");
        //LabelPowerset model = (LabelPowerset) weka.core.SerializationHelper.read("C:\\code\\idea\\BugReportClassifier\\data\\LP.model");
        MLkNN model = (MLkNN) weka.core.SerializationHelper.read("C:\\code\\idea\\BugReportClassifier\\data\\MLkNN.model");
        //BRkNN model = (BRkNN) weka.core.SerializationHelper.read("C:\\code\\idea\\BugReportClassifier\\data\\BRkNN.model");
        //ClassifierChain model = (ClassifierChain) weka.core.SerializationHelper.read("C:\\code\\idea\\BugReportClassifier\\data\\CC.model");
        //LabelPowerset model = (LabelPowerset) weka.core.SerializationHelper.read("C:\\code\\idea\\BugReportClassifier\\data\\LP.model");

        FileReader reader = new FileReader(testFilename);
        Instances datatest = new Instances(reader);
        int numInstances = datatest.numInstances();


        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(resultFile), "utf-8"))){

            //evaluator.evaluate(model,unlabeledData,);
            for(int index=0;index<numInstances;index++){
                Instance instance = datatest.instance(index);
                MultiLabelOutput output = model.makePrediction(instance);
                double[] rank = output.getConfidences();

                for (int i = 0; i < rank.length; i++) {
                    writer.write(rank[i]+" ");
                }
                writer.newLine();
            }
            writer.flush();
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}