
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.HashMap;

public class ExtractDeveloper{
    public static HashMap<String,Integer> map=new HashMap<>();
    public static void main(String[] args){
        for(int i=169401;i<=212901;i+=100){
            int j=i+99;
            String urlname="C:\\code\\idea\\BugReportClassifier\\data\\bugs"+i+"-"+j+".xml";
            try {
                //C:\Users\29952\Desktop\final\dataset\bugs000001-000100.xml
                File fXmlFile = new File(urlname);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();

                //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName("who");

                System.out.println(urlname);

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String name=eElement.getAttribute("name");
                        //System.out.println("who : " + name);
                        map.put(name,map.getOrDefault(name,0)+1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(String str:map.keySet()){
            System.out.println(str+": "+map.get(str));
        }
        for(String str:map.keySet()){
            System.out.println(str);
        }
        for(String str:map.keySet()){
            System.out.println(map.get(str));
        }
    }

}