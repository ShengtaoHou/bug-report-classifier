import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 29952 on 2018/4/10.
 */
public class Metapath {

    public static void main(String[] args){
        Network n=new Network();
        n.build();
        /*
        Vertex dev=n.networkVertex.get("1");  //get the developer's vertex in network
        for(Edge e1:dev.eset){                      //loop all the edge connected to the developer
            System.out.println("bug id:"+e1.v2.id);
        }
        */

        int[][] res=new int[160][160];
        for(int i=1;i<=154;i++){
            for(int j=i+1;j<=154;j++){
                res[i][j]=pathCount(n,String.valueOf(i),String.valueOf(j));
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\code\\idea\\BugReportClassifier\\data\\pathSum.txt"))) {
            for(int i=0;i<=154;i++){
                for(int j=0;j<=154;j++){
                    bw.write(String.valueOf(res[i][j])+" ");
                }
                bw.newLine();
            }
            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        int res=0;
        res=pathCount(n,"2","9");
        System.out.println(res);
*/
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
