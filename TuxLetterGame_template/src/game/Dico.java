/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author 33643
 */
public class Dico {

    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    private String cheminFichierDico;

    public Dico(String cheminFichierDico) {
        this.cheminFichierDico = cheminFichierDico;
        listeNiveau1 = new ArrayList<String>();
        listeNiveau2 = new ArrayList<String>();
        listeNiveau3 = new ArrayList<String>();
        listeNiveau4 = new ArrayList<String>();
        listeNiveau5 = new ArrayList<String>();
        this.lireDictionnaireDOM(cheminFichierDico, "dico.xml");
    }

    private String getMotDepuisListe(ArrayList<String> list) {
        int r = (int) (Math.random() * list.size());
        return list.get(r);
    }

    public String getMotDepuisListeNiveaux(int niveau) {
        String res;
        switch (niveau) {
            case 1:
                res = getMotDepuisListe(listeNiveau1);
                break;
            case 2:
                res = getMotDepuisListe(listeNiveau2);
                break;
            case 3:
                res = getMotDepuisListe(listeNiveau3);
                break;
            case 4:
                res = getMotDepuisListe(listeNiveau4);
                break;
            case 5:
                res = getMotDepuisListe(listeNiveau5);
                break;
            default:
                res = getMotDepuisListe(listeNiveau1);
        }
        return res;
    }

    public void ajouteMotADico(int niveau, String mot) {
        switch (niveau) {
            case 1:
                listeNiveau1.add(mot);
                break;
            case 2:
                listeNiveau2.add(mot);
                break;
            case 3:
                listeNiveau3.add(mot);
                break;
            case 4:
                listeNiveau4.add(mot);
                break;
            case 5:
                listeNiveau5.add(mot);
                break;
            default:
                listeNiveau1.add(mot);
        }
    }

    public String getCheminFichier() {
        return cheminFichierDico;
    }

    public void lireDictionnaireDOM(String path, String filename) {
        try {
            DOMParser parser = new DOMParser();
            parser.parse(path);
            Document doc = parser.getDocument();
            NodeList L = doc.getElementsByTagName("mot");
            for (int i = 0; i < L.getLength(); i++) {
                ajouteMotADico(Integer.parseInt(L.item(i).getAttributes().item(0).getTextContent()),L.item(i).getTextContent()); 
            }
        } catch (final SAXException | IOException e) {
        }
    }
}
