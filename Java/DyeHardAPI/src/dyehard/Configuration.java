package dyehard;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dyehard.Enemies.CollectorEnemy;
import dyehard.Enemies.PortalEnemy;
import dyehard.Enemies.ShootingEnemy;

/**
 * Adapted from the "Parse an XML File using the DOM Parser" example by
 * Sotirios-Efstathios Maneas located here:
 * http://examples.javacodegeeks.com/core-java/xml/java-xml-parser-tutorial/
 */

public class Configuration {
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;

    public Configuration() throws Exception {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(ClassLoader
                .getSystemResourceAsStream("resources/Enemies.xml"));

        parseDocument();
    }

    public void parseDocument() {

        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                float width = Float.parseFloat(elem
                        .getElementsByTagName("width").item(0).getChildNodes()
                        .item(0).getNodeValue());

                float height = Float.parseFloat(elem
                        .getElementsByTagName("height").item(0).getChildNodes()
                        .item(0).getNodeValue());

                float sleepTimer = Float.parseFloat(elem
                        .getElementsByTagName("sleepTimer").item(0)
                        .getChildNodes().item(0).getNodeValue());

                float speed = Float.parseFloat(elem
                        .getElementsByTagName("speed").item(0).getChildNodes()
                        .item(0).getNodeValue());

                if (node.getAttributes().getNamedItem("type").getNodeValue()
                        .equals("Portal")) {
                    PortalEnemy.setAttributes(width, height, sleepTimer, speed);
                } else if (node.getAttributes().getNamedItem("type")
                        .getNodeValue().equals("Shooting")) {
                    ShootingEnemy.setAttributes(width, height, sleepTimer,
                            speed);
                } else if (node.getAttributes().getNamedItem("type")
                        .getNodeValue().equals("Collector")) {
                    CollectorEnemy.setAttributes(width, height, sleepTimer,
                            speed);
                }
            }
        }
    }
}
