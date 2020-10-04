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

/**
 *
 * @author monsemps
 */
public abstract class Backup_jeu {

    private Env env;
    private Room room;
    private Profil profil;
    private Tux tux;
    private Dico dico;
    private ArrayList<Letter> mot;

    public Backup_jeu() {

        // Crée un nouvel environnement
        dico = new Dico("src/game/dico.xml");
        env = new Env();
        // Instancie une Room
        room = new Room("textures/skybox/snow/top.png", "textures/skybox/snow/north.png", "textures/skybox/snow/east.png", "textures/skybox/snow/west.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        //profil = new Profil();

    }

    public void execute() {

        // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
        
        String s = dico.getMotDepuisListeNiveaux(1).toLowerCase();
        mot = new ArrayList<Letter>();
        for (int i = 0; i < s.length(); i++) {
            mot.add(new Letter(s.charAt(i),5 + Math.random() * 90 , 5 + Math.random() * 90));
        }
        joue(new Partie(this.dateCourant(), s, 1));

        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }

    public void joue(Partie partie) {

        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);

        // Instancie un Tux
        tux = new Tux(env, room);
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
            if (env.getKey() == 1) {
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

    protected void démarrePartie(Partie partie) {

    }

    protected void appliqueRegles(Partie partie) {

    }

    protected void terminePartie(Partie partie) {

    }

    public Env getEnv() {
        return env;
    }

    public Room getRoom() {
        return room;
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
}
