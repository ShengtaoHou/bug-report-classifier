import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TopKPartner{

    public Map<String,Set<String>> getTopKPartner() {
        String partnerFile = "C:\\code\\idea\\BugReportClassifier\\data\\topKParter.txt";
        BufferedReader br = null;
        String line = "";
        BuildDeveloperTable developer=new BuildDeveloperTable();
        Map<Integer,String> developerList=developer.build2();   //开发者列表,id到name
        Map<String,Set<String>> topkdev=new HashMap<String,Set<String>>();   //top k个合作开发者

        try {

            //读取中间文件，获得开发者top k 个合作者
            br = new BufferedReader(new FileReader(partnerFile));
            int linenum=1;
            while ((line = br.readLine()) != null) {
                String[] parter = line.split(" ");
                String devname=developerList.get(linenum);
                //System.out.println("devname:"+devname);
                topkdev.put(devname,new HashSet<String>());

                for(int i=0;i<parter.length;i++){
                    String partername=developerList.get(Integer.parseInt(parter[i]));
                    topkdev.get(devname).add(partername);
                }
                linenum++;
            }
            //test
            for(String i3:topkdev.keySet()){
                System.out.print(i3+" : ");
                Set<String> set=topkdev.get(i3);
                for(String j3:set){
                    System.out.print(j3+" ");
                }
                System.out.println();
            }

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
        return topkdev;
    }
}