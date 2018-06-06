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
import java.util.HashSet;

public class RemoveInactiveDeveloper {

    public static void main(String[] args) {
        BuildDeveloperTable ba=new BuildDeveloperTable();
        HashMap<String,Integer> devmap=new HashMap<String,Integer>();
        HashMap<String,Integer> commap=new HashMap<String,Integer>();
        int comid=1;
        int bugtotal=0;
        devmap=ba.build();

        String FILE = "C:\\code\\idea\\BugReportClassifier\\data\\train_data.xml";
        String FILE2 = "C:\\code\\idea\\BugReportClassifier\\data\\train_data_active_developer.xml";

        //String FILE = "C:\\code\\idea\\BugReportClassifier\\data\\test_data.xml";
        //String FILE2 = "C:\\code\\idea\\BugReportClassifier\\data\\test_data_active_developer.xml";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc2 = dBuilder.newDocument();  //筛选后的存入的xml文件
            Element rootElement = doc2.createElement("bugzilla");
            doc2.appendChild(rootElement);

            File fXmlFile = new File(FILE);
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("bug");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    Element report = doc2.createElement("bug");
                    //rootElement.appendChild(report);

                    String s1 = eElement.getElementsByTagName("bug_id").item(0).getTextContent();
                    Element bug_id = doc2.createElement("bug_id");
                    bug_id.appendChild(doc2.createTextNode(s1));
                    report.appendChild(bug_id);

                    String s3= eElement.getElementsByTagName("short_desc").item(0).getTextContent();
                    Element short_desc = doc2.createElement("short_desc");
                    short_desc.appendChild(doc2.createTextNode(s3));
                    report.appendChild(short_desc);

                    String s4= eElement.getElementsByTagName("product").item(0).getTextContent();
                    Element product = doc2.createElement("product");
                    product.appendChild(doc2.createTextNode(s4));
                    report.appendChild(product);

                    String s2 = eElement.getElementsByTagName("component").item(0).getTextContent();
                    if(!commap.containsKey(s2))commap.put(s2,comid++);
                    Element component = doc2.createElement("component");
                    component.appendChild(doc2.createTextNode(s2));
                    component.setAttribute("component_id",String.valueOf(commap.get(s2)));
                    report.appendChild(component);
/*
                    String str_status= eElement.getElementsByTagName("bug_status").item(0).getTextContent();
                    Element bug_status = doc2.createElement("status");
                    bug_status.appendChild(doc2.createTextNode(str_status));
                    report.appendChild(bug_status);

                    String str_resolution= eElement.getElementsByTagName("resolution").item(0).getTextContent();
                    Element resolution = doc2.createElement("resolution");
                    resolution.appendChild(doc2.createTextNode(str_resolution));
                    report.appendChild(resolution);

                    String s7=eElement.getElementsByTagName("assigned_to").item(0).getTextContent();
                    Element assigned_to = doc2.createElement("assigned_to");
                    assigned_to.appendChild(doc2.createTextNode(s7));
                    report.appendChild(assigned_to);
*/
                    String str_desp= eElement.getElementsByTagName("thetext").item(0).getTextContent();
                    Element thetext = doc2.createElement("thetext");
                    thetext.appendChild(doc2.createTextNode(str_desp));
                    report.appendChild(thetext);

                    NodeList devList = eElement.getElementsByTagName("developer");

                    int count=devList.getLength();
                    for (int devnum = 0; devnum < devList.getLength(); devnum++) {
                        Node devNode = devList.item(devnum);
                        if (devNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element devElement = (Element) devNode;
                            String name = devElement.getTextContent();
                            if(!devmap.containsKey(name)){
                                count--;
                                continue;
                            }
                            Element developer = doc2.createElement("developer");
                            developer.setAttribute("developer_id",String.valueOf(devmap.get(name)));
                            developer.appendChild(doc2.createTextNode(name));
                            report.appendChild(developer);
                        }
                    }
                    if(count>0){
                        rootElement.appendChild(report);
                        bugtotal++;
                    }
                }
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc2);
            //System.out.println(outputPath);
            StreamResult result1 = new StreamResult(new File(FILE2));
            transformer.transform(source, result1);
            System.out.println("total: "+bugtotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


