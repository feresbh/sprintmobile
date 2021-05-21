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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.events.ActionListener;
import entities.Materiels;

import utils.Statics;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author SIHEM
 */
public class ServiceMateriels {

    public ArrayList<Materiels> mats;

    public static ServiceMateriels instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceMateriels() {
        req = new ConnectionRequest();
    }

    public static ServiceMateriels getInstance() {
        if (instance == null) {
            instance = new ServiceMateriels();
        }
        return instance;
    }

    public boolean addMateriels(Materiels t) {
        String url = Statics.BASE_URL + "/materiels/mat/add?type=" + t.getType() + "&description=" + t.getDescription() + "&prix_jour=" + t.getPrix_jour() + "&disponibilite=" + t.isDisponibilite() + "&image=" + t.getImage() + "&dateDebut=" + t.getDateDebut() + "&dateFin=" + t.getDateFin(); //création de l'URL
        req.setUrl(url);
        System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean editMateriels(Materiels t) {
        String url = Statics.BASE_URL + "/materiels/mat/edit?id=" + t.getId() + "&type=" + t.getType() + "&description=" + t.getDescription() + "&prix_jour=" + t.getPrix_jour() + "&disponibilite=" + t.isDisponibilite() + "&image=" + t.getImage() + "&dateDebut=" + t.getDateDebut() + "&dateFin=" + t.getDateFin(); //création de l'URL
        req.setUrl(url);
        System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteMateriels(Materiels t) {
        String url = Statics.BASE_URL + "/materiels/mat/del?id=" + t.getId();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Materiels> parseMateriels(String jsonText) {
        try {
            mats = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Materiels a = new Materiels();
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setType(obj.get("type").toString());
                a.setDescription(obj.get("description").toString());
                if (obj.get("disponibilite").toString().equals("true")) {
                    a.setDisponibilite(true);
                } else {
                    a.setDisponibilite(false);
                }

                a.setImage(obj.get("image").toString());
                float prix = Float.parseFloat(obj.get("prix_jour").toString());
                a.setPrix_jour(prix);
                String test = obj.get("dateDebut").toString();
                test = test.substring(0, 10);
                String test1 = obj.get("dateFin").toString();
                test1 = test1.substring(0, 10);
                System.out.println(test);
                System.out.println(test1);
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(test);
                    Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(test1);
                    a.setDateDebut(date1);
                    a.setDateFin(date2);
                } catch (ParseException ex) {
                }

                mats.add(a);
                //     t.setDateabsence((Date) obj.get("dateabsence"));

            }

        } catch (IOException ex) {

        }
        return mats;
    }

    public ArrayList<Materiels> getAllMaterielss() {
        String url = Statics.BASE_URL + "/materiels/affmatmob";
        System.out.println(url);

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                mats = parseMateriels(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return mats;
    }

    public ArrayList<Materiels> getMateriels(int id) {
        String url = Statics.BASE_URL + "/materiels/velo/findMaterielsMob/?id=" + id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                mats = parseMateriels(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return mats;
    }

    public void sendMail() {

        Message m = new Message("<html><body>Check out <a href=\"https://www.codenameone.com/\">Codename One</a></body></html>");
        m.setMimeType(Message.MIME_HTML);

// notice that we provide a plain text alternative as well in the send method
        boolean success = m.sendMessageViaCloudSync("Codename One", "br.rassil@gmail.com", "Name Of User", "Message Subject",
                "Check out Codename One at https://www.codenameone.com/");
        System.out.println("success: " + success);
    }

}
