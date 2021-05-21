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
import entities.Hotel;
import utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lenovo
 */
public class ServiceHotel {
    
    //singleton
    public static ServiceHotel instance = null;
    public static boolean resultok = true;
    
    // initialisation connection request
    private ConnectionRequest req;
    
    
    
    public static ServiceHotel getInstance()
    {
        if(instance == null)
            instance = new ServiceHotel();
        return instance;
    }
    
    public ServiceHotel()
            {
                req = new ConnectionRequest();
            }
            
    public void ajouterHotel(Hotel hotel)
    {
        String url =Statics.BASE_URL+"/addhotel?Nomhotel="+hotel.getNomhotel()+"&SiteWeb="+hotel.getSiteWeb()+"&Email="+hotel.getEmail()+"&Adresse="+hotel.getAdresse()+"&Telephone="+hotel.getTelephone()+"&Description="+hotel.getDescription();
          
        req.setUrl(url);
        req.addExceptionListener((e) -> {
            
            String str = new String(req.getResponseData());  //reponse json dans le navigateur
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req); //execution du request
        
        
    
    }
    
    public ArrayList<Hotel>affichageHotel()
    {
        ArrayList<Hotel>result = new ArrayList<>();
        String url =Statics.BASE_URL+"/displayhotel";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try
                {
                    Map<String,Object>mapHotels = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listofMaps = (List<Map<String,Object>>) mapHotels.get("root");
                    
                    for(Map<String,Object> obj : listofMaps){
                        Hotel ho = new Hotel();
                        
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String Nomhotel = obj.get("Nomhotel").toString();
                        String SiteWeb = obj.get("SiteWeb").toString();
                        String Email = obj.get("Email").toString();
                        String Telephone = obj.get("Telephone").toString();
                        String Adresse = obj.get("Adresse").toString();
                        String Description = obj.get("Description").toString();
                        
                        ho.setId((int)id);
                        ho.setNomhotel(Nomhotel);
                        ho.setSiteWeb(SiteWeb);
                        ho.setEmail(Email);
                        ho.setTelephone(Telephone);
                        ho.setAdresse(Adresse);
                        ho.setDescription(Description);
                        
                        result.add(ho);
                    }
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req); //execution du request
        
        return result;
    }
    
    public boolean deleteHotel(int id)
    {
        String url = Statics.BASE_URL+"/deletehotel?id="+id;   
        
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
              req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
                return resultok;
    }
    
    
    
    public boolean modifierHotel (Hotel hotel)
    {
              String url =Statics.BASE_URL+"/addhotel?Nomhotel="+hotel.getNomhotel()+"&SiteWeb="+hotel.getSiteWeb()+"&Email="+hotel.getEmail()+"&Adresse="+hotel.getAdresse()+"&Telephone="+hotel.getTelephone()+"&Description="+hotel.getDescription();
              req.setUrl(url);
              
              req.addResponseListener(new ActionListener<NetworkEvent>() {
                  @Override
                  public void actionPerformed(NetworkEvent evt) {
                      resultok = req.getResponseCode() == 200; //code response http
                      req.removeResponseListener(this);
                  }
              });
              
                NetworkManager.getInstance().addToQueueAndWait(req);
                return resultok;
    }
    
}
