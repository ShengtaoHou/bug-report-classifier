import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class CreateMulanCsv {

    public static void main(String[] args) {
        BuildDeveloperTable ba=new BuildDeveloperTable();
        HashMap<String,Integer> devmap=new HashMap<String,Integer>();
        HashMap<Integer,String> devmap2=new HashMap<Integer,String>();
        int bugtotal=0;
        devmap=ba.build();
        devmap2=ba.build2();

        String FILE = "C:\\code\\idea\\BugReportClassifier\\data\\train_data_active_developer.xml";
        String FILE2 = "C:\\code\\idea\\BugReportClassifier\\data\\train_data_mulan.xml";
        String csvName="C:\\code\\idea\\BugReportClassifier\\data\\train_data.csv";
/*
        String FILE = "C:\\code\\idea\\BugReportClassifier\\data\\test_data_active_developer.xml";
        String FILE2 = "C:\\code\\idea\\BugReportClassifier\\data\\test_data_mulan.xml";
        String csvName="C:\\code\\idea\\BugReportClassifier\\data\\test_data.csv";
*/
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

                    String str_product = eElement.getElementsByTagName("product").item(0).getTextContent();
                    String str_component = eElement.getElementsByTagName("component").item(0).getTextContent();
                    String str_short_desc= eElement.getElementsByTagName("short_desc").item(0).getTextContent();
                    String str_desp= eElement.getElementsByTagName("thetext").item(0).getTextContent();

                    Element text = doc2.createElement("text");
                    String str_text=str_product+" "+str_component+" "+str_short_desc+" "+str_desp;

                    str_text=str_text.replaceAll("[\r\n]+", " ");
                    str_text=str_text.replaceAll("[^a-zA-Z\\s]", " ");
                    str_text=str_text.replaceAll("( )+", " ");
                    if(str_text.length()>1000){
                        str_text=str_text.substring(0,1000);
                    }

                    text.appendChild(doc2.createTextNode(str_text));
                    report.appendChild(text);

                    for(Integer number:devmap2.keySet()){
                        //int number=devmap.get(str);
                        Element tempElement = doc2.createElement("developer"+number);
                        tempElement.appendChild(doc2.createTextNode("0"));
                        report.appendChild(tempElement);
                    }

                    NodeList devList = eElement.getElementsByTagName("developer");

                    for (int devnum = 0; devnum < devList.getLength(); devnum++) {
                        Node devNode = devList.item(devnum);
                        if (devNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element devElement = (Element) devNode;
                            String name = devElement.getTextContent();
                            int num=devmap.get(name);
                            String devloperid="developer"+num;
                            report.getElementsByTagName(devloperid).item(0).setTextContent("1");
                        }
                    }
                    rootElement.appendChild(report);
                    bugtotal++;
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
//



            File stylesheet = new File("C:\\code\\idea\\BugReportClassifier\\data\\style18.xsl");
            StreamSource stylesource = new StreamSource(stylesheet);
            Transformer transformer3 = TransformerFactory.newInstance().newTransformer(stylesource);

            File xmlSource = new File(FILE2);
            Document doc3 = dBuilder.parse(xmlSource);
            Source source3 = new DOMSource(doc3);
            javax.xml.transform.Result outputTarget = new StreamResult(new File(csvName));
            transformer3.transform(source3, outputTarget);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


