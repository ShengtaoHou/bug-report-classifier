/**
 * Created by 29952 on 2018/4/21.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Result {

    public static void main(String[] args) {
        String csvFile = "C:\\code\\idea\\BugReportClassifier\\data\\classified_bugreports.csv";
        BufferedReader br = null;
        String line = "";
        int total=0,correct=0;

        Map<String,Set<String>> topKPartner=new HashMap<String,Set<String>>();   //top k个合作开发者
        TopKPartner tk=new TopKPartner();
        topKPartner=tk.getTopKPartner();

        Map<String,String> rightAnswer=new HashMap<String,String>();
        RightAnswer ra=new RightAnswer();
        rightAnswer=ra.getRightAnswer();

        Map<String,Set<String>> rightAnswer2=new HashMap<String,Set<String>>();
        rightAnswer2=ra.getRightAnswer2();
        /*
        System.out.println("----test the right answer----");
        for(String i3:rightAnswer2.keySet()){
            System.out.print(i3+" : ");
            Set<String> set=rightAnswer2.get(i3);
            for(String j3:set){
                System.out.print(j3+" ");
            }
            System.out.println();
        }
        */
        try {

            //计算准确率,比较推荐集合和assign to
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                total++;
                String[] report = line.split(",");
                String rightans=rightAnswer.get(report[0]);
                if(rightans.equals(report[3])){
                        //||topKPartner.get(report[3]).contains(rightans)){
                    correct++;
                }
            }

            /*
            //计算准确率,比较推荐集合和感兴趣集合
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                total++;
                String[] report = line.split(",");
                String predictDeveloper=report[3];
                String reportID=report[0];

                Set<String> AnsSet=rightAnswer2.get(reportID);

                /*
                for(String j3:AnsSet){
                    System.out.print(j3+" ");
                }
                */
/*
                Boolean flag=false;
                if(AnsSet!=null){
                    for(String rightans:AnsSet){
                        //String rightans=str;
                        Set<String> partnerSet=topKPartner.get(predictDeveloper);

                        if(rightans.equals(predictDeveloper)
                                ||(partnerSet!=null&&partnerSet.contains(rightans))){
                            flag=true;
                        }
                    }
                }else{
                    System.out.println(report[0]+" tempset null");
                }
                if(flag){
                    correct++;
                }


            }*/

            double pr=1.0*correct/total;
            System.out.println("Total number of reports: "+total);
            System.out.println("Correct prediction times: "+correct);
            System.out.println("Accuracy: "+pr*100+"%");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}