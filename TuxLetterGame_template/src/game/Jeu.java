/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author gladen
 */
public abstract class Jeu {

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }
    protected int nbLettresRestantes;
    private final Env env;
    private ArrayList<Letter> mot;
    private Tux tux;
    private final Room mainRoom;
    private final Room menuRoom;
    private Letter letter;
    private Profil profil;
    private final Dico dico;
    protected EnvTextMap menuText;                         //text (affichage des texte du jeu)

    public Jeu() {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        mainRoom = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        //profil = new Profil();

        // Dictionnaire
        dico = new Dico("src/game/dico.xml");

        // instancie le menuText
        menuText = new EnvTextMap(env);

        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);

        menuText.addText("Choisir un niveau  ?", "textNiveauQuestion", 200, 300);
        menuText.addText("1. Niveau 1", "textNiveau1", 250, 280);
        menuText.addText("2. Niveau 2", "textNiveau2", 250, 260);
        menuText.addText("3. Niveau 3", "textNiveau3", 250, 240);
        menuText.addText("4. Niveau 4", "textNiveau4", 250, 220);
        menuText.addText("5. Niveau 5", "textNiveau5", 250, 200);
        
        
        menuText.addText("Ce joueur n'existe pas", "existePas", 200, 300);
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {

        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }

    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }

    // fourni, à compléter
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            try {
                // restaure la room du menu
                env.setRoom(menuRoom);
                // affiche menu
                menuText.getText("Question").display();
                menuText.getText("Jeu1").display();
                menuText.getText("Jeu2").display();
                menuText.getText("Jeu3").display();
                menuText.getText("Jeu4").display();
                
                // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
                int touche = 0;
                while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                    touche = env.getKey();
                    env.advanceOneFrame();
                }
                
                // nettoie l'environnement du texte
                menuText.getText("Question").clean();
                menuText.getText("Jeu1").clean();
                menuText.getText("Jeu2").clean();
                menuText.getText("Jeu3").clean();
                menuText.getText("Jeu4").clean();
                
                // restaure la room du jeu
                env.setRoom(mainRoom);
                
                // et décide quoi faire en fonction de la touche pressée
                switch (touche) {
                    // -----------------------------------------
                    // Touche 1 : Commencer une nouvelle partie
                    // -----------------------------------------
                    case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                        int niveau = menuNiveau();
                        
                        String s = dico.getMotDepuisListeNiveaux(niveau).toLowerCase();
                        mot = new ArrayList<Letter>();
                        for (int i = 0; i < s.length(); i++) {
                            mot.add(new Letter(s.charAt(i), 5 + Math.random() * 90, 5 + Math.random() * 90));
                        }
                        String date = dateCourant();
                        
                        menuText.addText(s, s, 200, 300);
                        menuText.getText(s).display();                        
                        env.advanceOneFrame();
                        TimeUnit.SECONDS.sleep(5);
                        menuText.getText(s).clean();                        
                        env.advanceOneFrame();
                        
                        // .......... dico ...........
                        // crée un nouvelle partie
                        partie = new Partie(date, s, niveau);
                        
                        // joue
                        joue(partie);
                        // enregistre la partie dans le profil --> enregistre le profil
                        // .......... profil .........
                        playTheGame = MENU_VAL.MENU_JOUE;
                        break;
                        /*
                        // -----------------------------------------
                        // Touche 2 : Charger une partie existante
                        // -----------------------------------------
                        case Keyboard.KEY_2: // charge une partie existante
                        // demander de fournir une date
                        // ..........
                        // tenter de trouver une partie à cette date
                        partie = new Partie(//?!!#??);
                        // .......
                        // Si partie trouvée, recupère le mot de la partie existante, sinon ???
                        // ..........
                        // ..........
                        // joue
                        joue(partie);
                        // enregistre la partie dans le profil --> enregistre le profil
                        // .......... profil ........
                        playTheGame = MENU_VAL.MENU_JOUE;
                        break;
                        */
                        
                        // -----------------------------------------
                        // Touche 3 : Sortie de ce jeu
                        // -----------------------------------------
                    case Keyboard.KEY_3:
                        playTheGame = MENU_VAL.MENU_CONTINUE;
                        break;
                        
                        // -----------------------------------------
                        // Touche 4 : Quitter le jeu
                        // -----------------------------------------
                    case Keyboard.KEY_4:
                        playTheGame = MENU_VAL.MENU_SORTIE;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    private MENU_VAL menuPrincipal() {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu      
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();

        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible                
                this.profil = new Profil("src/game/"+nomJoueur+".xml");
                  if (profil.charge(nomJoueur)) {
                    choix = menuJeu();
                } else {               
            try {
                menuText.getText("existePas").display();
                env.advanceOneFrame();
                TimeUnit.SECONDS.sleep(1);
                menuText.getText("existePas").clean();
                env.advanceOneFrame();
                
                choix = MENU_VAL.MENU_CONTINUE;//CONTINUE;
            } catch (InterruptedException ex) {
                Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur,dateCourant());
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    public void joue(Partie partie) {
        // Instancie un Tux
        env.setRoom(this.mainRoom);
        tux = new Tux(env, mainRoom);
        env.addObject(tux);
        mot.forEach((l) -> env.addObject(l));

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);

        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (nbLettresRestantes == 0) {
                finished = true;
            }
            tux.déplace();
            // Contrôles des déplacements de Tux (gauche, droite, ...)
            // ... (sera complété plus tard) ...
            // Ici, on applique les regles
            appliqueRegles(partie);

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);

    }

    protected abstract void démarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);

    public Env getEnv() {
        return env;
    }

    public Room getRoom() {
        return mainRoom;
    }

    public Profil getProfil() {
        return profil;
    }

    public Tux getTux() {
        return tux;
    }

    public Dico getDico() {
        return dico;
    }

    public ArrayList<Letter> getMot() {
        return mot;
    }

    private String dateCourant() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private int menuNiveau() {
        int niveau = 0;

        // restaure la room du menu
        env.setRoom(menuRoom);

        // affiche le menu des niveaux
        menuText.getText("textNiveauQuestion").display();
        menuText.getText("textNiveau1").display();
        menuText.getText("textNiveau2").display();
        menuText.getText("textNiveau3").display();
        menuText.getText("textNiveau4").display();
        menuText.getText("textNiveau5").display();

        // vérifie qu'une touche 1, 2, 3, 4 ou 5 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2
                || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4
                || touche == Keyboard.KEY_5)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        // efface le menu des niveaux
        menuText.getText("textNiveauQuestion").clean();
        menuText.getText("textNiveau1").clean();
        menuText.getText("textNiveau2").clean();
        menuText.getText("textNiveau3").clean();
        menuText.getText("textNiveau4").clean();
        menuText.getText("textNiveau5").clean();

        // restaure la room du jeu
        switch (touche) {
            case Keyboard.KEY_1:
                niveau = 1;
                break;
            case Keyboard.KEY_2:
                niveau = 2;
                break;
            case Keyboard.KEY_3:
                niveau = 3;
                break;
            case Keyboard.KEY_4:
                niveau = 4;
                break;
            case Keyboard.KEY_5:
                niveau = 5;
                break;
        }
        return niveau;
    }

}
