package com.fges.ckonsoru;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

    String xmlFilePath;

    public XmlInteraction(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    @Override
    public Map<Integer, Map<String, String>> selectElementsFromWhere(String[] tags, String from, String where,
            String equals) {
        try {
            File inputFile = new File(this.xmlFilePath);
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
    
    @Override
    public String dbMode() {
        return "xml";
    }

    @Override
    public Integer insert(String table, String[] columns, String[] values) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        if (table == "rendezvous") {
            table = "rdvs";
        } else if (table == "disponibilite") {
            table = "disponibilites";
        } else {
            System.err.println("Erreur dans XmlInterration : Table inconnue");
        }
        try {
            // charger le fichier xml
            builder = factory.newDocumentBuilder();
            Document xmldoc = builder.parse(this.xmlFilePath);

            //definit le nom de l'élément à ajouter (disponibilite ou rdv dans notre cas)
            String elementAjoute = table.substring(0, table.length() - 1);
            // cree un element avec les valeurs dans les colonnes
            Element rdv = xmldoc.createElement(elementAjoute);

            for (int i = 0; i < columns.length; i++) {
                Element element = xmldoc.createElement(columns[i]);
                element.appendChild(xmldoc.createTextNode(values[i]));
                rdv.appendChild(element);
            }
            

            // ajout au noeud rdvs
            NodeList nodes = xmldoc.getElementsByTagName(table);
            nodes.item(0).appendChild(rdv);

            // enregistrer le fichier
            DOMSource source = new DOMSource(xmldoc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(this.xmlFilePath);
            transformer.transform(source, result);
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            System.err.println("Erreur à l'ouverture de la bdd xml : " + this.xmlFilePath);
            e.printStackTrace(System.err);
        }

        return 0;
    }
}
