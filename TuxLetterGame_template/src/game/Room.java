/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;


import org.w3c.dom.Document;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;


/**
 *
 * @author monsemps
 */
public class Room {
    private int depth;
    private int height;
    private int width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private String textureTop;
    private String textureSouth;
    
    
    
    public Room(){
        
        try {
            DOMParser parser = new DOMParser();
            parser.parse("src/game/plateau.xml");
            Document doc = parser.getDocument();
            this.depth = Integer.parseInt( doc.getElementsByTagName("depth").item(0).getTextContent());
            this.height = Integer.parseInt( doc.getElementsByTagName("height").item(0).getTextContent());
            this.width = Integer.parseInt( doc.getElementsByTagName("width").item(0).getTextContent());
            this.textureBottom = doc.getElementsByTagName("textureBottom").item(0).getTextContent();
            this.textureNorth = doc.getElementsByTagName("textureNorth").item(0).getTextContent();
            this.textureEast = doc.getElementsByTagName("textureEast").item(0).getTextContent();
            this.textureWest = doc.getElementsByTagName("textureWest").item(0).getTextContent();
        } catch (SAXException | IOException ex) {
            Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Room(String textureBottom, String textureNorth, String textureEast, String textureWest ){
        this.depth = 100;
        this.height = 60;
        this.width = 100;
        this.textureBottom = textureBottom;
        this.textureNorth = textureNorth;
        this.textureEast = textureEast;
        this.textureWest = textureWest;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }
}
