import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashSet;
import java.util.HashMap;
/**
 * Created by 29952 on 2018/4/10.
 */
public class Network {
    public HashSet<Edge> networkEgde=new HashSet<Edge>();        //存储所有边
    public HashMap<String,Vertex> networkVertex=new HashMap<String,Vertex>();  //存储所有节点，并用节点id作为索引
    public void build() {
        try {

            int edgeNum=0;
            File fXmlFile = new File("C:\\code\\idea\\BugReportClassifier\\data\\train_data_active_developer.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();


            NodeList nList = doc.getElementsByTagName("bug");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;


                    String bugid = eElement.getElementsByTagName("bug_id").item(0).getTextContent();
                    //String s2 = eElement.getElementsByTagName("component").item(0).getTextContent();

                    //创建bug节点，肯定是独一无二的，不需要判断是否重复
                    Vertex bugVertex=new Vertex(bugid,Vertex.BUGREPORT,bugid);

                    this.networkVertex.put(bugid,bugVertex);
                    NodeList devList = eElement.getElementsByTagName("developer");
                    for (int devnum = 0; devnum < devList.getLength(); devnum++) {
                        Node devNode = devList.item(devnum);
                        if (devNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element devElement = (Element) devNode;
                            String devid = devElement.getAttribute("developer_id");
                            String devname=devElement.getTextContent();

                            if(!this.networkVertex.containsKey(devid)){
                                //如果开发者节点之前未被创建，那么就创建一个新的开发者节点
                                Vertex devVertex=new Vertex(devid,Vertex.DEVELOPER,devname);
                                //创建边
                                Edge e=new Edge(String.valueOf(edgeNum++),devVertex,bugVertex);
                                //将边加入点的边集
                                devVertex.addEdge(e);
                                bugVertex.addEdge(e);
                                //将边加入网络
                                this.networkEgde.add(e);
                                //将点加入网络
                                this.networkVertex.put(devid,devVertex);
                            }else{
                                //如果已经被创建，那么从网络中取出该节点
                                Vertex devVertex=this.networkVertex.get(devid);
                                //创建边
                                Edge e=new Edge(String.valueOf(edgeNum++),devVertex,bugVertex);
                                //将边加入点的边集
                                devVertex.addEdge(e);
                                bugVertex.addEdge(e);
                                //将边加入网络
                                this.networkEgde.add(e);
                                //将点加入网络
                                this.networkVertex.put(devid,devVertex);
                            }
                        }
                    }

                }
            }
            /*
            for(String str:this.networkVertex.keySet()){
                if(this.networkVertex.get(str).type==2)
                    System.out.println("id:"+this.networkVertex.get(str).id+"  type:"+this.networkVertex.get(str).type+"  name:"+this.networkVertex.get(str).name);
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
