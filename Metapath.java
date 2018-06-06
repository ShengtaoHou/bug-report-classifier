import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by 29952 on 2018/4/10.
 */
public class Metapath {

    public static void main(String[] args){
        int NUM=18;
        int KNUM=5;
        Network n=new Network();
        n.build();

        int[][] res=new int[NUM+1][NUM+1];
        int[][] res2=new int[NUM+1][NUM+1];

        for(int i=1;i<=NUM;i++){
            for(int j=1;j<=NUM;j++){
                if(i==j)continue;
                res[i][j]=pathCount(n,String.valueOf(i),String.valueOf(j));
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\code\\idea\\BugReportClassifier\\data\\pathSum.txt"))) {
            for(int i=1;i<=NUM;i++){
                for(int j=1;j<=NUM;j++){
                    bw.write(String.valueOf(res[i][j])+" ");
                }
                bw.newLine();
            }
            System.out.println("write the pathsum to file");

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        for(int i=1;i<=NUM;i++){
            for(int j=1;j<=NUM;j++){
                res[i][0]+=res[i][j];
            }
        }
        try (BufferedWriter bw2 = new BufferedWriter(new FileWriter("C:\\code\\idea\\BugReportClassifier\\data\\PartnerCoeff.txt"))) {
            for(int i=1;i<=NUM;i++){
                for(int j=1;j<=NUM;j++){
                    bw2.write(String.valueOf(1.0*res[i][j]/res[i][0])+" ");
                }
                bw2.newLine();
            }
            System.out.println("write the partnerCoff to file");

        }catch (IOException e) {
            e.printStackTrace();
        }
        */
        for(int i2=0;i2<res.length;i2++){
            for(int j2=0;j2<res[0].length;j2++){
                res2[i2][j2]=res[i2][j2];
            }
        }

        Map<Integer,Set<Integer>> topkdev=new HashMap<Integer,Set<Integer>>();


        for(int i=1;i<=NUM;i++){
            Map<Integer, List<Integer>> indexmap = new HashMap<Integer, List<Integer>>();
            for(int j=1;j<=NUM;j++){
                if(!indexmap.containsKey(res[i][j])){
                    indexmap.put(res[i][j],new LinkedList<>());
                    indexmap.get(res[i][j]).add(j);
                }else{
                    indexmap.get(res[i][j]).add(j);
                }
            }
            Arrays.sort(res[i]);


            topkdev.put(i,new HashSet<Integer>());
            int counttopnum=0;
            int lastRes=-1;
            for(int j=NUM;j>=NUM-KNUM+1;j--) {
                //if(topkdev.get(i).size()>=KNUM)break;   //取完top 5个合作者，就不再继续。

                if(res[i][j]!=lastRes){
                    for (int k = 0; k < indexmap.get(res[i][j]).size(); k++) {

                        if (counttopnum < KNUM) {
                            counttopnum++;
                            int index = indexmap.get(res[i][j]).get(k);
                            if (i == 4) {
                                System.out.println(" index " + index);
                            }
                            topkdev.get(i).add(index);
                        }
                    }
                }
                lastRes=res[i][j];
            }

        }

        for(Integer i3:topkdev.keySet()){
            System.out.print(i3+" : ");
            Set<Integer> set=topkdev.get(i3);
            for(Integer j3:set){
                System.out.print(j3+" ");
            }
            System.out.println();
        }
        //写入topK合作者索引
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\code\\idea\\BugReportClassifier\\data\\topKPartner.txt"))) {
            for(Integer i3:topkdev.keySet()){
                Set<Integer> set=topkdev.get(i3);
                for(Integer j3:set){
                    bw.write(String.valueOf(j3)+" ");
                }
                bw.newLine();
            }
            System.out.println("write the topKParter to file");

        } catch (IOException e) {
            e.printStackTrace();
        }
        //写入topK合作者系数
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\code\\idea\\BugReportClassifier\\data\\PartnerCoeff.txt"))) {
            for(Integer i3:topkdev.keySet()){

                Set<Integer> set=topkdev.get(i3);
                for(Integer j3:set){
                    res2[i3][0]+=res2[i3][j3];
                }
                for(Integer j3:set){
                    bw.write(String.valueOf(1.0*res2[i3][j3]/res2[i3][0])+" ");
                }
                bw.newLine();
            }
            System.out.println("write the PartnerCoeff to file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int pathCount(Network n,String devid1,String devid2) {
        int res=0;
        Vertex dev=n.networkVertex.get(devid1);  //get the developer's vertex in network
        if(dev==null)return 0;
        for(Edge e1:dev.eset){                      //loop all the edge connected to the developer
            for(Edge e2:e1.v2.eset) {                                  //e.v2 represent the bug report connected to developer
                //System.out.println("codev id:"+e2.v1.id);
                if(e2.v1.id.equals(devid2)){
                    //System.out.println("bug id:"+e2.v2.id);
                    res++;
                }
            }
        }
        return res;
    }
}
