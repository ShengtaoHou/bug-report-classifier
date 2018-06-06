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

public class PreProccessData {

    public static void main(String[] args) {
        try {
            HashMap<String,Integer> commap=new HashMap<String,Integer>();
            int comid=1;
            int bugtotal=0;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc2 = dBuilder.newDocument();  //筛选后的存入的xml文件
            Element rootElement = doc2.createElement("bugzilla");
            doc2.appendChild(rootElement);
            //int START=73101,END=82001;
            int START=82101,END=93301;
            for (int i = START; i <= END; i += 100){
                int j = i + 99;
                //String urlname = "C:\\code\\idea\\BugReportClassifier\\data\\report2004_train\\bugs0" + i + "-0" + j + ".xml";

                String urlname = "C:\\code\\idea\\BugReportClassifier\\data\\report2004_test\\bugs0" + i + "-0" + j + ".xml";
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

                            String s3= eElement.getElementsByTagName("short_desc").item(0).getTextContent();
                            Element short_desc = doc2.createElement("short_desc");
                            short_desc.appendChild(doc2.createTextNode(s3));
                            report.appendChild(short_desc);


                            String str_classification= eElement.getElementsByTagName("classification").item(0).getTextContent();
                            Element classification = doc2.createElement("classification");
                            if(str_classification==null){
                                //classification.appendChild(doc2.createTextNode("###"));
                                System.out.println("class null"+i);
                                continue;
                            }else if(!"Eclipse".equals(str_classification)) {
                                //classification.appendChild(doc2.createTextNode(str_classification));
                                continue;
                            }else{
                                classification.appendChild(doc2.createTextNode(str_classification));
                            }
                            report.appendChild(classification);

                            String str_product= eElement.getElementsByTagName("product").item(0).getTextContent();
                            Element product = doc2.createElement("product");
                            if(str_product==null){
                                product.appendChild(doc2.createTextNode("###"));
                                System.out.println("product null"+i);
                                continue;
                            }else if(!"Platform".equals(str_product)) {
                                //product.appendChild(doc2.createTextNode(str_product));
                                //continue;
                            }else{
                                product.appendChild(doc2.createTextNode(str_product));
                            }
                            report.appendChild(product);

                            String s2 = eElement.getElementsByTagName("component").item(0).getTextContent();
                            if(!commap.containsKey(s2))commap.put(s2,comid++);
                            Element component = doc2.createElement("component");
                            component.appendChild(doc2.createTextNode(s2));
                            component.setAttribute("component_id",String.valueOf(commap.get(s2)));
                            report.appendChild(component);


                            String str_status= eElement.getElementsByTagName("bug_status").item(0).getTextContent();
                            Element bug_status = doc2.createElement("status");
                            if(str_status==null){
                                bug_status.appendChild(doc2.createTextNode("###"));
                                System.out.println("status null"+i);
                                continue;
                            }else if(!"RESOLVED".equals(str_status)) {
                                bug_status.appendChild(doc2.createTextNode(str_status));
                                //continue;
                            }else{
                                bug_status.appendChild(doc2.createTextNode(str_status));
                            }
                            report.appendChild(bug_status);

                            Element reelement=(Element)eElement.getElementsByTagName("resolution").item(0);
                            Element resolution = doc2.createElement("resolution");
                            if(reelement!=null){
                                String str_resolution= reelement.getTextContent();
                                if(str_resolution==null){
                                    resolution.appendChild(doc2.createTextNode("###"));
                                    System.out.println("resolution null"+i);
                                    continue;
                                }else if(!"FIXED".equals(str_resolution)) {
                                    resolution.appendChild(doc2.createTextNode(str_resolution));
                                    //continue;
                                }else{
                                    resolution.appendChild(doc2.createTextNode(str_resolution));
                                }
                            }else{
                                resolution.appendChild(doc2.createTextNode("###"));
                                continue;
                            }
                            report.appendChild(resolution);
/*
                            String str_resolution= eElement.getElementsByTagName("resolution").item(0).getTextContent();
                            Element resolution = doc2.createElement("status");
                            if(str_resolution==null){
                                resolution.appendChild(doc2.createTextNode("###"));
                                System.out.println("resolution null"+i);
                                continue;
                            }else if(!"FIXED".equals(str_resolution)) {
                                //continue;
                            }else{
                                resolution.appendChild(doc2.createTextNode(str_resolution));
                            }
                            report.appendChild(resolution);
*/

                            String s7=eElement.getElementsByTagName("assigned_to").item(0).getTextContent();
                            Element assigned_to = doc2.createElement("assigned_to");
                            if(s7==null){
                                assigned_to.appendChild(doc2.createTextNode("###"));
                            }else{
                                assigned_to.appendChild(doc2.createTextNode(s7));
                            }
                            report.appendChild(assigned_to);

                            String str_desp= eElement.getElementsByTagName("thetext").item(0).getTextContent();
                            Element thetext = doc2.createElement("thetext");
                            thetext.appendChild(doc2.createTextNode(str_desp));
                            report.appendChild(thetext);

                            NodeList devList = eElement.getElementsByTagName("who");
                            HashSet<String> devset=new HashSet<String>(); //remove dupulicate name
                            //int count=devList.getLength();
                            for (int devnum = 1; devnum < devList.getLength(); devnum++) {
                                Node devNode = devList.item(devnum);
                                if (devNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element devElement = (Element) devNode;
                                    String name = devElement.getTextContent();

                                    Element developer = doc2.createElement("developer");
                                    developer.appendChild(doc2.createTextNode(name));
                                    if(!devset.contains(name)){
                                        report.appendChild(developer);
                                        devset.add(name);
                                    }
                                }
                            }
                            rootElement.appendChild(report);
                            bugtotal++;
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
                StreamResult result1 = new StreamResult(new File("C:\\code\\idea\\BugReportClassifier\\data\\test_data.xml"));
                transformer.transform(source, result1);
                System.out.println("total: "+bugtotal);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

