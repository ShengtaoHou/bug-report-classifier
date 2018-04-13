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
import java.util.HashMap;
import java.util.HashSet;

public class ParserData {

    public static void main(String[] args) {
        try {
            BuildDeveloperTable ba=new BuildDeveloperTable();
            HashMap<String,Integer> devmap=new HashMap<String,Integer>();
            HashMap<String,Integer> commap=new HashMap<String,Integer>();
            int comid=1;
            int bugtotal=0;
            devmap=ba.build();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc2 = dBuilder.newDocument();  //筛选后的存入的xml文件
            Element rootElement = doc2.createElement("bugzilla");
            doc2.appendChild(rootElement);

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

                            Element report = doc2.createElement("bug");
                            //rootElement.appendChild(report);

                            String s1 = eElement.getElementsByTagName("bug_id").item(0).getTextContent();
                            Element bug_id = doc2.createElement("bug_id");
                            bug_id.appendChild(doc2.createTextNode(s1));
                            report.appendChild(bug_id);

                            String s2 = eElement.getElementsByTagName("component").item(0).getTextContent();
                            if(!commap.containsKey(s2)){
                                commap.put(s2,comid++);
                            }
                            Element component = doc2.createElement("component");
                            component.appendChild(doc2.createTextNode(s2));
                            component.setAttribute("component_id",String.valueOf(commap.get(s2)));
                            report.appendChild(component);

                            NodeList devList = eElement.getElementsByTagName("who");
                            HashSet<String> devset=new HashSet<String>(); //remove dupulicate name
                            int count=devList.getLength();
                            for (int devnum = 0; devnum < devList.getLength(); devnum++) {
                                Node devNode = devList.item(devnum);
                                if (devNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element devElement = (Element) devNode;
                                    String name = devElement.getAttribute("name");
                                    if(!devmap.containsKey(name)){
                                        count--;
                                        continue;
                                    }

                                    Element developer = doc2.createElement("developer");
                                    developer.appendChild(doc2.createTextNode(name));
                                    developer.setAttribute("developer_id",String.valueOf(devmap.get(name)));
                                    if(!devset.contains(name)){
                                        report.appendChild(developer);
                                        devset.add(name);
                                    }
                                }
                            }
                            if(count>0){
                                rootElement.appendChild(report);
                                bugtotal++;
                            }
                        }
                    }


                    // Output to console for testing
                    //StreamResult consoleResult = new StreamResult(System.out);
                    //transformer.transform(source, consoleResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc2);
                //System.out.println(outputPath);
                StreamResult result1 = new StreamResult(new File("C:\\code\\idea\\BugReportClassifier\\data\\data2.xml"));
                transformer.transform(source, result1);
                System.out.println("total: "+bugtotal);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

