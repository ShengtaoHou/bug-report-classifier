/**
 * Created by 29952 on 2018/4/10.
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;

public class BuildDeveloperTable {
    public HashMap<String, Integer> build() {
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        String FILE = "C:\\code\\idea\\BugReportClassifier\\data\\developer.xml";

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(FILE);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("label");
            int devID=1;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String name=eElement.getAttribute("name");
                    map.put(name,devID++);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    public HashMap<Integer,String> build2() {
        HashMap<Integer,String> map=new HashMap<Integer,String>();
        String FILE = "C:\\code\\idea\\BugReportClassifier\\data\\developer.xml";

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(FILE);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("label");
            int devID=1;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String name=eElement.getAttribute("name");
                    map.put(devID++,name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

