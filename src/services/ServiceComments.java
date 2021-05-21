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
import com.codename1.ui.events.ActionListener;
import entities.comments;
import utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pc
 */
public class ServiceComments {
      public ArrayList<comments> comments;

    public static ServiceComments instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceComments() {
        req = new ConnectionRequest();
    }
    public static ServiceComments getInstance() {
        if (instance == null) {
            instance = new ServiceComments();
        }
        return instance;
    }
    public ArrayList<comments> parsePosts(String jsonText) {
        try {
            comments = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                comments f = new comments();

                float id = Float.parseFloat(obj.get("id").toString());
                f.setId((int) id);
                f.setAutor(obj.get("author").toString());

                f.setContent(obj.get("content").toString());

                System.out.print(obj);

                comments.add(f);
            }
        } catch (IOException ex) {

        }
        return comments;
    }
     public ArrayList<comments> getPosts(int id) {
        String url = Statics.BASE_URL + "/showForumJSON/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                comments = parsePosts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return comments;
    }
      public boolean addPost(comments p, int id) {
        String url = Statics.BASE_URL + "/addPostJSON/new/" + id + "?author=" + p.getAutor()+ "&content=" + p.getContent(); //création de l'URL
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
          public void deletePost(int id) {
        ConnectionRequest req = new ConnectionRequest();
        String url = Statics.BASE_URL + "/deletePostJSON/" + id;
        req.setUrl(url);

        // req.setPost(false);
        req.addResponseListener((NetworkEvent evt) -> {
            String str = new String(req.getResponseData());

        });
        NetworkManager.getInstance().addToQueueAndWait(req);

    }
             public boolean modifPost(comments p) {
        String url = Statics.BASE_URL+"/updatePostJSON/"+ p.getId() +"?author=" + p.getAutor()+ "&content=" + p.getContent(); //création de l'URL
         System.out.println("modif "+p);     
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
  public void detailPost(int id ) {
        String url = Statics.BASE_URL + "/showPostJSON/"+id; //création de l'URL
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
       
    }
}
