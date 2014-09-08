package dyehard;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
    }

    public enum EnemyType {
        EN_PORTAL, EN_SHOOTING, EN_COLLECTOR
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
        return Float.parseFloat(elem.getElementsByTagName(tag).item(0)
                .getChildNodes().item(0).getNodeValue());
    }

    private int parseInt(Element elem, String tag) {
        return Integer.parseInt(elem.getElementsByTagName(tag).item(0)
                .getChildNodes().item(0).getNodeValue());
    }

    private void parseEnemyData() throws Exception {
        Document document = builder.parse(ClassLoader
                .getSystemResourceAsStream("resources/Enemies.xml"));

        NodeList nodeList = document.getDocumentElement().getChildNodes();

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

                enemies.put(type, value);
            }
        }
    }

    public static EnemyData getEnemyData(EnemyType type) {
        return enemies.get(type);
    }

    private void parseHeroData() throws Exception {
        Document document = builder.parse(ClassLoader
                .getSystemResourceAsStream("resources/Hero.xml"));

        NodeList nodeList = document.getDocumentElement().getChildNodes();

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
        Document document = builder.parse(ClassLoader
                .getSystemResourceAsStream("resources/OverheatWeapon.xml"));

        NodeList nodeList = document.getDocumentElement().getChildNodes();

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
        Document document = builder.parse(ClassLoader
                .getSystemResourceAsStream("resources/LimitedAmmoWeapon.xml"));

        NodeList nodeList = document.getDocumentElement().getChildNodes();

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
        Document document = builder.parse(ClassLoader
                .getSystemResourceAsStream("resources/World.xml"));

        NodeList nodeList = document.getDocumentElement().getChildNodes();

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
        Document document = builder.parse(ClassLoader
                .getSystemResourceAsStream("resources/DyePacks.xml"));

        NodeList nodeList = document.getDocumentElement().getChildNodes();

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
        Document document = builder.parse(ClassLoader
                .getSystemResourceAsStream("resources/PowerUps.xml"));

        NodeList nodeList = document.getDocumentElement().getChildNodes();

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
