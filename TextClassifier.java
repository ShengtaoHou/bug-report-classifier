
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Random;


/**
 * Example class that converts HTML files stored in a directory structure into
 * and ARFF file using the TextDirectoryLoader converter. It then applies the
 * StringToWordVector to the data and feeds a J48 classifier with it.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class TextClassifier{

    /**
     * Expects the first parameter to point to the directory with the text files.
     * In that directory, each sub-directory represents a class and the text
     * files in these sub-directories will be labeled as such.
     *
     * @param args        the commandline arguments
     * @throws Exception  if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        DataSource source=new DataSource("C:\\code\\idea\\BugReportClassifier\\data\\train_data.arff");
        Instances data=source.getDataSet();
        if(data.classIndex()==-1)
            data.setClassIndex(data.numAttributes()-1);

        //log.info("Applying StringToWordVector filter...");
        String[] options = Utils.splitOptions(
                "-R first-last -W 300 -prune-rate -1.0 -T -I -N 0 -L " +
                        "-stemmer weka.core.stemmers.SnowballStemmer " +
                        "-stopwords-handler weka.core.stopwords.Rainbow " +
                        "-M 50 -tokenizer \"weka.core.tokenizers.WordTokenizer " +
                        "-delimiters \\\" \\\\r\\\\n\\\\t.,;:\\\\\\'\\\\\\\"()?!\\\"\"");
        StringToWordVector filter = new StringToWordVector();
        filter.setOptions(options);
        filter.setInputFormat(data);
        data= Filter.useFilter(data, filter);
        //log.info("StringToWordVector Filter applied");
        data.setClass(data.attribute(0));
        //FilterUtil.saveFilter(filter, pathToSaveFilter);
        System.out.println("finish");



        Instances trainInstances =data;
        Instances testInstances =data;
        Classifier scheme = new NaiveBayes();

        Evaluation evaluation = new Evaluation(trainInstances);
        evaluation.evaluateModel(scheme, testInstances);
        System.out.println(evaluation.toSummaryString());

/*
        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(scheme, data, 4, new Random(1));
        System.out.println(Utils.doubleToString(eval.correct(), 12, 4) + "\t " + Utils.doubleToString(eval.pctCorrect(), 12, 4) + "%");
*/
    }
}