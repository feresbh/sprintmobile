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
import entities.Category;
import utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MY HP
 */
public class ServiceCategory {
    
    //singleton
    public static ServiceCategory instance= null;
    
    //Init connection request
    private ConnectionRequest req;
    
    
    
    public static ServiceCategory getInstance(){
        if(instance == null)
            instance = new ServiceCategory();
        return instance;
        
        
    }
    
    public ServiceCategory(){
        
        req= new ConnectionRequest();
    }
    
    
    //AJOUT CATEGORY
    public void ajoutCategory(Category category){
        
        String url=Statics.BASE_URL+"/admin/category/ajouterJSON?name="+category.getName();
        req.setUrl(url);
        req.addResponseListener((e)-> {
            
            String str= new String(req.getResponseData());
            System.out.println("Data =="+str);
            
            
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ
        
    }
    
    //AFFICHAGE CATEGORY
    public ArrayList<Category>affichageCategory(){
        ArrayList<Category> result= new ArrayList<>();
       String url=Statics.BASE_URL+"/admin/category/AllCatJSON";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
public void actionPerformed(NetworkEvent evt){
    
JSONParser jsonp;
jsonp= new JSONParser();
try{
    Map<String,Object>mapCategory=jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
    
    List<Map<String,Object>> listOfMaps= (List<Map<String,Object>>)mapCategory.get("root");
    
    for(Map<String,Object> obj : listOfMaps ){
        
        Category re= new Category();
        
        
        float id=Float.parseFloat(obj.get("id").toString());
        String name=obj.get("name").toString();
        
        re.setId((int)id);
        re.setName(name);
        
        //insert data to db
        result.add(re);
        
    }
}catch(Exception ex){
    ex.printStackTrace();
    
    
}
    
}            
            
        });
                NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ

                return result;
        
    }
    
    // Detail Category
    public Category DetailCategory(int id, Category category){
        
        String url=Statics.BASE_URL+"admin/category/CatIdJSON"+id;
        req.setUrl(url);
        String str= new String (req.getResponseData());
        req.addResponseListener(((evt)-> {
            
            JSONParser jsonp= new JSONParser(); 
            try{
       Map<String,Object>obj=jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                category.setName(obj.get("name").toString());
                
            }
            catch(IOException ex){
                System.out.println("error "+ex.getMessage());
                
            }
             
            System.out.println("data    "+str);
            
        }));
                        NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ

    return category;
    }
    
}
