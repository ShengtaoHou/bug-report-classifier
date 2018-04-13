import java.util.HashSet;

/**
 * Created by 29952 on 2018/4/2.
 */
public class Vertex {
    public String id =null;
    public int type = -1;
    public String name = null;
    public HashSet<Edge> eset=new HashSet<Edge>();
    public static final int BUGREPORT = 1;
    public static final int DEVELOPER = 2;

    public Vertex(String id,int type,String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }
    public void addEdge(Edge e){
        if(!this.eset.contains(e)){
            this.eset.add(e);
        }
    }
}
