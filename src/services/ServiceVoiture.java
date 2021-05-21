/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import com.mycompany.gui.ListVoitureForm;
import entities.Voiture;
import entities.VoitureLouee;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author HP
 */
public class ServiceVoiture {

    public ArrayList<Voiture> voitures;
    public ArrayList<VoitureLouee> voituresl;
    public static ServiceVoiture instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceVoiture() {
        req = new ConnectionRequest();
    }

    public static ServiceVoiture getInstance() {
        if (instance == null) {
            instance = new ServiceVoiture();
        }
        return instance;
    }

    public boolean addVoiture(Voiture v) {
        String url = Statics.BASE_URL + "/addcarsjson/new?marque=" + v.getMarque() + "&model=" + v.getModel() + "&couleur=" + v.getCouleur() + "&serie=" + v.getSerie() + "&image=" + v.getImage() + "&prix=" + v.getPrix();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Voiture> parseVoitures(String jsonText) {
        try {
            voitures = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Voiture v = new Voiture();
                float id = Float.parseFloat(obj.get("id").toString());
                v.setId((int) id);
                v.setMarque(obj.get("marque").toString());
                v.setModel(obj.get("model").toString());

                String couleur = (String) obj.get("couleur");
                v.setCouleur(couleur);
                if (obj.get("diponibilite") == "true") {
                    v.setDiponibilite(1);
                } else {
                    v.setDiponibilite(0);
                }

                String serie = (String) obj.get("serie");
                v.setSerie(serie);

                v.setPrix((int) Float.parseFloat(obj.get("prix").toString()));

                String image = (String) obj.get("image");
                v.setImage(image);
                System.out.println("hani houni 1");
                voitures.add(v);
            }
        } catch (IOException ex) {

        }
        return voitures;
    }

    public ArrayList<Voiture> getAllVoitures() {
        System.out.println("hani houni 1");
        String url = Statics.BASE_URL + "/cars";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("hani houni 2");
                voitures = parseVoitures(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println("hani houni 2");
        return voitures;
    }

    //****************************************************************************************
    /* **************************methodes des voitures louee ******************************* */
    //****************************************************************************************
    public Voiture FindCar(String Serie){
        Resources res = null;
        ListVoitureForm a = new ListVoitureForm(res);
        Voiture v = new Voiture();
        v.setId(1);
        v.setMarque("a");
        v.setModel("a");
        v.setSerie("aaa");
        v.setPrix(10);
        System.out.println("la1");
        
        for (int i = 0; i < voitures.size(); i++) {
            System.out.println("la2");
            if (Serie.equals(voitures.get(i).getSerie())) {
                System.out.println("la3");
                System.out.println(voitures.get(i));
                v=voitures.get(i);
            }
        }
        
        return v;
    }
    public ArrayList<VoitureLouee> parseVoitureslouee(String jsonText) {
        try {
            voituresl = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            Voiture vv = new Voiture();
            VoitureLouee v = new VoitureLouee();
            //v.setVoitureid(FindCar(v.getSerie()).getId());
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                //VoitureLouee v = new VoitureLouee();
                float id = Float.parseFloat(obj.get("id").toString());
                v.setId((int) id);
                
                v.setDatedebut(obj.get("datedebut").toString());
                v.setDatefin(obj.get("datefin").toString());
                
                String serie = (String) obj.get("serie");
                v.setSerie(serie);
                
                
                //v.setVoitureid(FindCar(v.getSerie()).getId());
                //float prix = Float.parseFloat(obj.get("prix").toString());
                //v.setPrix((int) prix);

                voituresl.add(v);
            }
        } catch (IOException ex) {

        }
        return voituresl;
    }

    public ArrayList<VoitureLouee> getAllVoitureslouee() {
        String url = Statics.BASE_URL + "/carslocation";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                voituresl = parseVoitureslouee(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return voituresl;
    }

    // /addcarlocationjson/new?voiture=48&datedebut=15.05.2021&datefin=16.05.2021
    public boolean addVoitureLocation(VoitureLouee v) {
        String url = Statics.BASE_URL + "/addcarlocationjson/new?voiture=" + v.getVoitureid() + "&datedebut=" + v.getDatedebut() + "&datefin=" + v.getDatefin();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean SupprimerVoitureLouee(int id,int idv) {
        String url = Statics.BASE_URL +"/SupprimerVoiturVLouee/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
               }});
         NetworkManager.getInstance().addToQueueAndWait(req);
         return resultOK;
    }

}
