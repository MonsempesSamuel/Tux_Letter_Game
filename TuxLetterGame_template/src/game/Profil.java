package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.IOException;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class Profil {
    

    private String nom;
    private String dateNaissance;
    private String avatar;
    private ArrayList<Partie> parties;
    private boolean existe = false;

    // Cree un nouveau profil
    public Profil(String nomJoueur, String dateNaissance ) {
        this.nom = nomJoueur;
        this.dateNaissance = dateNaissance;
        this.avatar = "";
        this.parties = new ArrayList<Partie>();
        this.existe = true;
    }
    
    public Profil(String nomFichier ) {
        try{
        DOMParser parser = new DOMParser();
        parser.parse(nomFichier);
        Document doc = parser.getDocument();
        this.nom = doc.getDocumentElement().getElementsByTagName("nom").item(0).getTextContent();
        this.dateNaissance = xmlDateToProfileDate(doc.getDocumentElement().getElementsByTagName("anniversaire").item(0).getTextContent());
        this.avatar = doc.getDocumentElement().getElementsByTagName("avatar").item(0).getTextContent();
        //NodeList partieNodeList = doc.getDocumentElement().getElementsByTagName("partie");
        //this.parties = getParties(partieNodeList);
        this.existe = true;
        }catch(IOException | DOMException | SAXException e){
            System.out.println("Ce joueur n'existe pas");
        }
    }
    
    private ArrayList<Partie> getParties(NodeList partieNodeList)
    {
        ArrayList<Partie> p = new ArrayList<Partie>();
        int taille = partieNodeList.getLength();
        for ( int i = 0 ; i < taille ; i++ )
        {
            p.add( new Partie((Element )partieNodeList.item(i) ) );
        }
        return p;   
    }
    
    public boolean charge(String nomJoueur)
    {
        return this.existe;   
    }

    // Cree un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        Document document = null;
        DOMParser parser = null;
        try {
            parser = new DOMParser();
            parser.parse(nomFichier);
            return document = parser.getDocument();
            //return XMLUtil.DocumentFactory.fromFile(nomFichier);
        }catch(Exception e){
            System.out.println("Erreur création de pointeur fichier");
        }
        /*} catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return null;
    }
    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }

    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)
    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }

    public String toString()
    {
        String strPartie = "Parties\n";
        int nbPartie = this.parties.size();
        int i = 0;
        for ( Partie partie : this.parties )
        {
            i++;
            strPartie += "  Partie " + i + " :\n" 
                        +"        mot : "+partie.getMot()+"\n"
                        +"        niveau : "+partie.getNiveau()+"\n";
        }
        return "nom : " + this.nom  
                + "\n, dateNaissance : " + this.dateNaissance
                + "\n, avatar : "+this.avatar
                + "\n"+ strPartie;
    }
}