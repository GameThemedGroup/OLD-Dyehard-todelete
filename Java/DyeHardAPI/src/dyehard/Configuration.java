package dyehard;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Engine.BaseCode;

/**
 * Adapted from the "Parse an XML File using the DOM Parser" example by
 * Sotirios-Efstathios Maneas located here:
 * http://examples.javacodegeeks.com/core-java/xml/java-xml-parser-tutorial/
 */

public class Configuration {
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;

    // Hero variables
    public static float heroWidth;
    public static float heroHeight;
    public static float heroJetSpeed;
    public static float heroSpeedLimit;
    public static float heroDrag;

    // Overheat weapon variables
    public static float overheatFiringRate;
    public static float overheatCooldownRate;
    public static float overheatHeatLimit;

    // Limited ammo weapon variables
    public static float limitedFiringRate;
    public static int limitedReloadAmount;
    public static int limitedMaxAmmo;

    // World variables
    public static float worldEnemyFrequency;
    public static int worldPowerUpCount;
    public static int worldDyePackCount;
    public static int worldDebrisCount;
    public static float worldMapLength;
    public static float worldGameSpeed;

    // Dye pack variables
    public static float dyePackWidth;
    public static float dyePackHeight;
    public static float dyePackSpeed;

    // Power up variables
    public static float powerUpWidth;
    public static float powerUpHeight;
    public static float powerUpSpeed;

    public static class PowerUpData {
        public float duration;
        public float magnitude;
    }

    public enum PowerUpType {
        PU_GHOST, PU_GRAVITY, PU_INVINCIBILITY, PU_MAGNETISM, PU_OVERLOAD, PU_SLOWDOWN, PU_SPEEDUP, PU_UNARMED,
    }

    private static Map<PowerUpType, PowerUpData> powerUps = new HashMap<PowerUpType, PowerUpData>();

    public static class EnemyData {
        public float width;
        public float height;
        public float sleepTimer;
        public float speed;
        public NodeList uniqueAttributes;
    }

    public enum EnemyType {
        PORTAL_ENEMY, PORTAL_SPAWN, SHOOTING_ENEMY, COLLECTOR_ENEMY
    }

    private static Map<EnemyType, EnemyData> enemies = new HashMap<EnemyType, EnemyData>();

    public Configuration() throws Exception {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();

        parseEnemyData();
        parseHeroData();
        parseOverheatData();
        parseLimitedAmmoData();
        parseWorldData();
        parseDyePackData();
        parsePowerUpData();
    }

    private float parseFloat(Element elem, String tag) {
        if (elem.getElementsByTagName(tag).item(0) != null) {
            return Float.parseFloat(elem.getElementsByTagName(tag).item(0)
                    .getChildNodes().item(0).getNodeValue());
        }

        return 0;
    }

    private int parseInt(Element elem, String tag) {
        if (elem.getElementsByTagName(tag).item(0) != null) {
            return Integer.parseInt(elem.getElementsByTagName(tag).item(0)
                    .getChildNodes().item(0).getNodeValue());
        }

        return 0;
    }

    private InputStream loadExternalFile(String path) {
        String basePath = BaseCode.resources.basePath;
        URL url;

        try {
            url = new URL(basePath + path);
            URLConnection in = url.openConnection();
            if (in.getContentLengthLong() > 0) {
                return url.openStream();
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private NodeList createNodeList(String file) throws Exception {

        String filePath = "resources/" + file + ".xml";

        InputStream is = null;

        if (is == null) {
            is = loadExternalFile(filePath);
        }

        if (is == null) {
            is = ClassLoader.getSystemResourceAsStream(filePath);
        }

        Document document = builder.parse(is);

        return document.getDocumentElement().getChildNodes();
    }

    private void parseEnemyData() throws Exception {
        NodeList nodeList = createNodeList("Enemies");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                EnemyType type = EnemyType.valueOf(node.getAttributes()
                        .getNamedItem("type").getNodeValue());

                EnemyData value = new EnemyData();
                value.width = parseFloat(elem, "width");
                value.height = parseFloat(elem, "height");
                value.sleepTimer = parseFloat(elem, "sleepTimer");
                value.speed = parseFloat(elem, "speed");
                value.uniqueAttributes = elem
                        .getElementsByTagName("uniqueAttributes");

                enemies.put(type, value);
            }
        }
    }

    public static EnemyData getEnemyData(EnemyType type) {
        return enemies.get(type);
    }

    private void parseHeroData() throws Exception {
        NodeList nodeList = createNodeList("Hero");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                heroWidth = parseFloat(elem, "width");
                heroHeight = parseFloat(elem, "height");
                heroJetSpeed = parseFloat(elem, "jetSpeed");
                heroSpeedLimit = parseFloat(elem, "speedLimit");
                heroDrag = parseFloat(elem, "drag");
            }
        }
    }

    private void parseOverheatData() throws Exception {
        NodeList nodeList = createNodeList("OverheatWeapon");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                overheatFiringRate = parseFloat(elem, "firingRate");
                overheatCooldownRate = parseFloat(elem, "cooldownRate");
                overheatHeatLimit = parseFloat(elem, "heatLimit");
            }
        }
    }

    private void parseLimitedAmmoData() throws Exception {
        NodeList nodeList = createNodeList("LimitedAmmoWeapon");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                limitedFiringRate = parseFloat(elem, "firingRate");
                limitedMaxAmmo = parseInt(elem, "maxAmmo");
                limitedReloadAmount = parseInt(elem, "reloadAmount");
            }
        }
    }

    private void parseWorldData() throws Exception {
        NodeList nodeList = createNodeList("World");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                worldEnemyFrequency = parseFloat(elem, "enemySpawnTimer");
                worldPowerUpCount = parseInt(elem, "powerUpCount");
                worldDyePackCount = parseInt(elem, "dyePackCount");
                worldDebrisCount = parseInt(elem, "debrisCount");
                worldMapLength = parseFloat(elem, "mapLength");
                worldGameSpeed = parseFloat(elem, "gameSpeed");
            }
        }
    }

    private void parseDyePackData() throws Exception {
        NodeList nodeList = createNodeList("DyePacks");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                dyePackWidth = parseFloat(elem, "width");
                dyePackHeight = parseFloat(elem, "height");
                dyePackSpeed = parseFloat(elem, "speed");
            }
        }
    }

    private void parsePowerUpData() throws Exception {
        NodeList nodeList = createNodeList("PowerUps");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                if (node.getAttributes().getNamedItem("type").getNodeValue()
                        .equals("Shared")) {
                    powerUpWidth = parseFloat(elem, "width");
                    powerUpHeight = parseFloat(elem, "height");
                    powerUpSpeed = parseFloat(elem, "speed");
                } else {
                    PowerUpType type = PowerUpType.valueOf(node.getAttributes()
                            .getNamedItem("type").getNodeValue());

                    PowerUpData value = new PowerUpData();
                    value.duration = parseFloat(elem, "duration");
                    value.magnitude = parseFloat(elem, "magnitude");

                    powerUps.put(type, value);
                }
            }
        }
    }

    public static PowerUpData getPowerUpData(PowerUpType type) {
        return powerUps.get(type);
    }
}
