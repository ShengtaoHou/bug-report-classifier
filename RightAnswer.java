/**
 * Created by 29952 on 2018/4/21.
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashSet;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RightAnswer{

    public Map<String,String> getRightAnswer( ) {

        String date_all_File = "C:\\code\\idea\\BugReportClassifier\\data\\data_all.csv";
        BufferedReader br = null;
        String line = "";
        Map<String,String> rightAnswer=new HashMap<String,String>();
        try {
            //构建bug id与assign to的开发者之间的map
            br = new BufferedReader(new FileReader(date_all_File));
            while ((line = br.readLine()) != null) {
                String[] report = line.split(",");
                rightAnswer.put(report[0],report[3]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rightAnswer;
    }
    public Map<String,Set<String>> getRightAnswer2( ) {
        Map<String,Set<String>> rightAnswer=new HashMap<String,Set<String>>();
        try {

            File fXmlFile = new File("C:\\code\\idea\\BugReportClassifier\\data\\data_with_text.xml");
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
                    rightAnswer.put(bugid,new HashSet<String>());

                    String assigned = eElement.getElementsByTagName("assigned_to").item(0).getTextContent();
                    rightAnswer.get(bugid).add(assigned);

                    NodeList devList = eElement.getElementsByTagName("developer");
                    for (int devnum = 0; devnum < devList.getLength(); devnum++) {
                        Node devNode = devList.item(devnum);
                        if (devNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element devElement = (Element) devNode;
                            String devname=devElement.getTextContent();
                            rightAnswer.get(bugid).add(devname);
                        }
                    }
                }
            }
            /*
            System.out.println("----test the right answer----");
            for(String i3:rightAnswer.keySet()){
                System.out.print(i3+" : ");
                Set<String> set=rightAnswer.get(i3);
                for(String j3:set){
                    System.out.print(j3+" ");
                }
                System.out.println();
            }
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rightAnswer;
    }

}