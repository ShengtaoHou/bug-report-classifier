
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
import java.util.HashMap;

public class ExtractDeveloper{
    public static HashMap<String,Integer> map=new HashMap<>();
    public static void main(String[] args){
            String DATA_FILE="C:\\code\\idea\\BugReportClassifier\\data\\train_data.xml";
            String DEV_FILE="C:\\code\\idea\\BugReportClassifier\\data\\developer.xml";
            int devnum=0;
            try {
                File dataFile = new File(DATA_FILE);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(dataFile);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("developer");

                Document doc2 = dBuilder.newDocument();  //筛选后的存入的xml文件
                Element rootElement = doc2.createElement("labels");
                doc2.appendChild(rootElement);

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String name=eElement.getTextContent();
                        map.put(name,map.getOrDefault(name,0)+1);
                    }
                }
                for(String str:map.keySet()){
                    if(map.get(str)>=240){
                        Element label= doc2.createElement("label");
                        label.setAttribute("name",str);
                        rootElement.appendChild(label);
                        devnum++;
                    }
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc2);
                //System.out.println(outputPath);
                StreamResult result1 = new StreamResult(new File(DEV_FILE));
                transformer.transform(source, result1);
                System.out.println("devnum: "+ devnum);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }//main
}//class