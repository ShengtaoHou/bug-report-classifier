/**
 * Created by 29952 on 2018/4/9.
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;

public class TextData {

    public static void main(String[] args) {
        try {
            BuildDeveloperTable ba=new BuildDeveloperTable();
            HashMap<String,Integer> devmap=new HashMap<String,Integer>();
            HashMap<String,Integer> commap=new HashMap<String,Integer>();
            int comid=1;
            int bugtotal=169401;
            devmap=ba.build();

            for(String str:devmap.keySet()){
                new File("C:\\code\\idea\\BugReportClassifier\\data\\text\\"+str).mkdirs();
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            for (int i = 169401; i <= 212901; i += 100) {
                int j = i + 99;
                String urlname = "C:\\code\\idea\\BugReportClassifier\\data\\bugs" + i + "-" + j + ".xml";
                try {
                    File fXmlFile = new File(urlname);
                    Document doc = dBuilder.parse(fXmlFile);
                    doc.getDocumentElement().normalize();

                    NodeList nList = doc.getElementsByTagName("bug");

                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;


                            String s1= eElement.getElementsByTagName("short_desc").item(0).getTextContent();

                            Element asselement= (Element)eElement.getElementsByTagName("assigned_to").item(0);
                            String s2=asselement.getAttribute("name");

                            String s3= eElement.getElementsByTagName("thetext").item(0).getTextContent();

                            if(devmap.containsKey(s2)){
                                String txtfilename="C:\\code\\idea\\BugReportClassifier\\data\\text\\"+s2+"\\"+bugtotal+".txt";
                                PrintWriter writer = new PrintWriter(txtfilename, "UTF-8");
                                writer.println(s1);
                                writer.println(s3);
                                writer.close();
                            }
                            bugtotal++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

