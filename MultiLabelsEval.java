import mulan.classifier.lazy.BRkNN;
import mulan.classifier.lazy.MLkNN;
import mulan.classifier.meta.RAkEL;
import mulan.classifier.neural.BPMLL;
import mulan.classifier.transformation.BinaryRelevance;
import mulan.classifier.transformation.ClassifierChain;
import mulan.classifier.transformation.LabelPowerset;
import mulan.data.MultiLabelInstances;
import mulan.evaluation.Evaluation;
import mulan.evaluation.Evaluator;
import mulan.evaluation.measure.*;
import weka.classifiers.trees.J48;

import java.io.*;
import java.util.ArrayList;

public class MultiLabelsEval {

    public static void main(String[] args) throws Exception {
        String trainFilename = "C:\\code\\idea\\BugReportClassifier\\data\\train_data.arff"; // e.g. -arff emotions.arff
        String xmlFilename = "C:\\code\\idea\\BugReportClassifier\\data\\developer.xml"; // e.g. -xml emotions.xml
        String testFilename = "C:\\code\\idea\\BugReportClassifier\\data\\test_data.arff"; // e.g. -arff emotions.arff

        String resultFile="C:\\code\\idea\\BugReportClassifier\\data\\eval_result18_LP.txt";

        MultiLabelInstances datatrain = new MultiLabelInstances(trainFilename, xmlFilename);
        MultiLabelInstances datatest = new MultiLabelInstances(testFilename, xmlFilename);

        //BRkNN model = new BRkNN();
        //MLkNN model = new MLkNN();
        //BPMLL model = new BPMLL();
        //BinaryRelevance model = new BinaryRelevance(new J48());
        //ClassifierChain model=new ClassifierChain();
        LabelPowerset model=new LabelPowerset(new J48());
        model.build(datatrain);
        weka.core.SerializationHelper.write("C:\\code\\idea\\BugReportClassifier\\data\\LP.model", model);

/*
        RAkEL model = (RAkEL) weka.core.SerializationHelper.read("C:\\code\\idea\\BugReportClassifier\\data\\RAkEL.model");
*/

        Evaluator eval = new Evaluator();
       Evaluation results;

        ArrayList<Measure> mes = new ArrayList<Measure>();

        mes.add(new AveragePrecision());
        mes.add(new OneError());
        mes.add(new RankingLoss());
        mes.add(new HammingLoss());
        mes.add(new Coverage());


        results=eval.evaluate(model,datatest,mes);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(resultFile), "utf-8"))){
            writer.write(results.toString());
            writer.flush();
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}