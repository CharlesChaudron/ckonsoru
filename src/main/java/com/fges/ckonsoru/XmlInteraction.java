package com.fges.ckonsoru;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlInteraction implements DatabaseInteraction {

    @Override
    public Map<Integer, Map<String, String>> selectElementsFromWhere(String[] tags, String from, String where,
            String equals) {
        try {
            File inputFile = new File("src/main/resources/datas.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;

            dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            //TODO: améliorer système pour choisir la bonne data/table
            if (from == "rendezvous") {
                from = "rdvs/rdv";
            } else if (from == "disponibilite") {
                from = "disponibilites/disponibilite";
            } else {
                System.out.println("Erreur dans XmlInterration : Table inconnue");
            }

            String expression = "/ckonsoru/" + from + "[starts-with(" + where + ",'" + equals + "')]";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            //creation du map retourné qui contiendra tous les elements
            Map<Integer, Map<String, String>> res = new HashMap<Integer, Map<String, String>>();  
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // creation du map par element
                    Map<String, String> element = new HashMap<String, String>();
                    for (int j = 0; j < tags.length; j++) {
                        String tag = eElement.getElementsByTagName(tags[j]).item(0).getTextContent();
                        element.put(tags[j], tag);
                    }
                    res.put(i, element);
                }
            }
            return res;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
