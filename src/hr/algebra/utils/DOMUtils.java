/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import hr.algebra.model.Card;
import hr.algebra.model.Player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author IgorKvakan
 */
public class DOMUtils {

    private static final String FILENAME_PLAYER_DECK = "playerDeck.xml";
    private static final String FILENAME_OPPONENT_DECK = "opponentDeck.xml";
    private static final String FILENAME_PLAYER_HAND = "playerHand.xml";
    private static final String FILENAME_OPPONENT_HAND = "opponentHand.xml";
    private static final String FILENAME_FIELD_CARDS = "fieldCards.xml";
    private static final String FILENAME_PLAYER = "players.xml";
    private static final String FILENAME_OPPONENT = "opponents.xml";

    public static void savePlayerDeck(List<Card> cards) {

        try {
            Document document = createDocument("playerDeck");
            cards.forEach(c -> document.getDocumentElement().appendChild(createCardElement(c, document)));
            saveDocument(document, FILENAME_PLAYER_DECK);
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void saveOpponentDeck(List<Card> cards) {
        try {
            Document document = createDocument("opponentDeck");
            cards.forEach(c -> document.getDocumentElement().appendChild(createCardElement(c, document)));
            saveDocument(document, FILENAME_OPPONENT_DECK);
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void savePlayerHand(List<Card> cards) {
        try {
            Document document = createDocument("playerHand");
            cards.forEach(c -> document.getDocumentElement().appendChild(createCardElement(c, document)));
            saveDocument(document, FILENAME_PLAYER_HAND);
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void saveOpponentHand(List<Card> cards) {
        try {
            Document document = createDocument("opponentHand");
            cards.forEach(c -> document.getDocumentElement().appendChild(createCardElement(c, document)));
            saveDocument(document, FILENAME_OPPONENT_HAND);
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void saveFieldCards(List<Card> cards) {
        try {
            Document document = createDocument("fieldCards");
            cards.forEach(c -> document.getDocumentElement().appendChild(createCardElement(c, document)));
            saveDocument(document, FILENAME_FIELD_CARDS);
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void savePlayer(Player player) {
        try {
            Document document = createDocument("players");
            document.getDocumentElement().appendChild(createPlayerOpponentElement(player,document));
            saveDocument(document, FILENAME_PLAYER);
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void saveOpponent(Player opponent) {
        try {
            Document document = createDocument("opponents");
            document.getDocumentElement().appendChild(createPlayerOpponentElement(opponent,document));
            saveDocument(document, FILENAME_OPPONENT);
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private static Element createPlayerOpponentElement(Player player, Document document) {
        Element element=document.createElement("player");
        element.appendChild(createElement(document, "name", player.getName()));
        element.appendChild(createElement(document, "health",String.valueOf(player.getHealth())));
        
        return element;
    }
    
    
    
    

    private static Document createDocument(String root) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImplementation = builder.getDOMImplementation();
        return (Document) domImplementation.createDocument(null, root, null);
    }

    private static void saveDocument(Document document, String fileName) throws TransformerException {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            //transformer.transform(new DOMSource(document), new StreamResult(System.out));
            transformer.transform(new DOMSource((Node) document), new StreamResult(new File(fileName)));
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Element createCardElement(Card card, Document document) { // attr:src(picpath) title ,att,def, colIndex,rowIndex
        Element element = document.createElement("card");
        element.setAttributeNode(createAttribute(document, "src", card.getPicturePath()));
        element.appendChild(createElement(document, "title", card.getTitle()));
        element.appendChild(createElement(document, "attack", String.valueOf(card.getAttack())));
        element.appendChild(createElement(document, "defense", String.valueOf(card.getDefense())));
        element.appendChild(createElement(document, "colIndex", String.valueOf(card.getColumnIndex())));
        element.appendChild(createElement(document, "rowIndex", String.valueOf(card.getRowIndex())));

        return element;
    }

    private static Attr createAttribute(Document document, String name, String value) {
        Attr attr = document.createAttribute(name);
        attr.setValue(value);
        return attr;
    }

    private static Node createElement(Document document, String tagName, String data) {
        Element element = document.createElement(tagName);
        Text text = document.createTextNode(data);
        element.appendChild(text);
        return element;
    }

    
    public static List<Card> loadPlayerDeck(){
        List<Card> cards = new ArrayList<>();
        
        try {
            Document document= createDocument(new File(FILENAME_PLAYER_DECK));
            NodeList nodes=document.getElementsByTagName("card");
            for (int i = 0; i < nodes.getLength(); i++) {
                cards.add(processCardNode((Element)nodes.item(i)));
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cards;
        
    }
    public static List<Card> loadOpponentDeck(){
        List<Card> cards = new ArrayList<>();
        
        try {
            Document document= createDocument(new File(FILENAME_OPPONENT_DECK));
            NodeList nodes=document.getElementsByTagName("card");
            for (int i = 0; i < nodes.getLength(); i++) {
                cards.add(processCardNode((Element)nodes.item(i)));
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cards;
        
    }
    public static List<Card> loadPlayerHand(){
        List<Card> cards = new ArrayList<>();
        
        try {
            Document document= createDocument(new File(FILENAME_PLAYER_HAND));
            NodeList nodes=document.getElementsByTagName("card");
            for (int i = 0; i < nodes.getLength(); i++) {
                cards.add(processCardNode((Element)nodes.item(i)));
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cards;
        
    }
    public static List<Card> loadOpponentHand(){
        List<Card> cards = new ArrayList<>();
        
        try {
            Document document= createDocument(new File(FILENAME_OPPONENT_HAND));
            NodeList nodes=document.getElementsByTagName("card");
            for (int i = 0; i < nodes.getLength(); i++) {
                cards.add(processCardNode((Element)nodes.item(i)));
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cards;
        
    }
    public static List<Card> loadFieldCards(){
        List<Card> cards = new ArrayList<>();
        
        try {
            Document document= createDocument(new File(FILENAME_FIELD_CARDS));
            NodeList nodes=document.getElementsByTagName("card");
            for (int i = 0; i < nodes.getLength(); i++) {
                cards.add(processCardNode((Element)nodes.item(i)));
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cards;
        
    }
    public static Player loadPlayer(){
        
        Player player=new Player();
        try {
            Document document= createDocument(new File(FILENAME_PLAYER));
            NodeList nodes=document.getElementsByTagName("player");
            
            for (int i = 0; i < nodes.getLength(); i++) {
                player=processPlayerNode((Element) nodes.item(i));

            }
            
                
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        
        return player;
        
    }
    public static Player loadOpponent(){
        
        Player player= new Player();
        
        try {
            Document document= createDocument(new File(FILENAME_OPPONENT));
            NodeList nodes=document.getElementsByTagName("player");
            for (int i = 0; i < nodes.getLength(); i++) {
                player=processPlayerNode((Element) nodes.item(i));

            }
                
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DOMUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return player;
        
    }
    
    
    
    
    private static Document createDocument(File fileName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document=builder.parse(fileName);
        return document;
    }

    private static Card processCardNode(Element element) {
        return new Card(
                element.getElementsByTagName("title").item(0).getTextContent(),
                Integer.valueOf(element.getElementsByTagName("attack").item(0).getTextContent()),
                Integer.valueOf(element.getElementsByTagName("defense").item(0).getTextContent()),
                element.getAttribute("src"),
                Integer.valueOf(element.getElementsByTagName("colIndex").item(0).getTextContent()),
                Integer.valueOf(element.getElementsByTagName("rowIndex").item(0).getTextContent())
        
        );
    }

    private static Player processPlayerNode(Element element)  {
        return  new Player(
                element.getElementsByTagName("name").item(0).getTextContent().trim(),
                Integer.valueOf(element.getElementsByTagName("health").item(0).getTextContent().trim())
        
        );
    }

    
    
   
}
