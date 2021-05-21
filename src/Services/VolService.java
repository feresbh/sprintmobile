/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.vol;
import com.codename1.io.CharArrayReader;
import java.util.ArrayList;
import utils.DataSource;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.events.ActionListener;
import java.util.List;
import java.util.Map;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.TextField;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class VolService {
    private ConnectionRequest req;
   
    
    public ArrayList <vol> afficheVol (){
        ArrayList <vol> result =new ArrayList <>();
        req=new ConnectionRequest();
        String url= DataSource.BASE_URL+"/mobile/affiche";

        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp=new JSONParser();
                try{
                    Map<String,Object> mapExposee=jsonp.parseJSON(new CharArrayReader(new String (req.getResponseData()).toCharArray()));
                    List <Map<String,Object>> listOfMap =(List <Map<String,Object>>) mapExposee.get("root");
                  
                    for( Map<String,Object> obj :listOfMap){
                        vol e=new vol();
                        float id=Float.parseFloat(obj.get("id").toString());
                        float idv=Float.parseFloat(obj.get("idV").toString());
                        float place=Float.parseFloat(obj.get("placeD").toString());
                        float prix=Float.parseFloat(obj.get("prix").toString());
                        String aeroD= obj.get("aeroD").toString();
                        String aeroA= obj.get("aeroA").toString();
                         String brochureFilename= obj.get("brochureFilename").toString();
                     
                        String DateCon =obj.get("dateD").toString().substring(0,obj.get("dateD").toString().indexOf("T"));
                        System.out.println("date con"+DateCon);                       
                       
                        
                      e.setDate_a(DateCon);
                       
                       String DateCon1 =obj.get("dateA").toString().substring(0,obj.get("dateA").toString().indexOf("T"));
                      
                       
                      
                       
                       e.setDate_d(DateCon1);
                       e.setId((int)id);
                       e.setBrochure_filename(brochureFilename);
                       e.setPlace((int)place);
                       e.setPrix(Double.valueOf(prix));
                       e.setAero_a(aeroA);
                       e.setAero_d(aeroD);
                        System.out.println(""+e.toString());
                       
                   
                       result.add(e);
                       
                        
                    }
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
        
    }
    
      public void AjouterVol(TextField idvol,TextField aerod,TextField aeroa,TextField prix,TextField placed,String image){
         req=new ConnectionRequest();
        String url= DataSource.BASE_URL+"/mobile/Vol/ajouterVol?idvol="+idvol.getText()+"&aerod="+aerod.getText()+"&aeroa="+aeroa.getText().toString()+"&prix="+prix.getText()+"&placed="+placed.getText()+"&image="+image;

        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp=new JSONParser();
           
                
               }});
         NetworkManager.getInstance().addToQueueAndWait(req);
    }
      
        public void SupprimerVol(int id){
         req=new ConnectionRequest();
        String url= DataSource.BASE_URL+"/mobile/suppVol?id="+id;

        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp=new JSONParser();
           
                
               }});
         NetworkManager.getInstance().addToQueueAndWait(req);
    }
        
             public void UpdateVol(TextField idvol,TextField aerod,TextField aeroa,TextField prix,TextField placed,TextField image,int id){
         req=new ConnectionRequest();
        String url= DataSource.BASE_URL+"/mobile/Vol/UpdateVol?id="+id+"&idvol="+idvol.getText()+"&aerod="+aerod.getText()+"&aeroa="+aeroa.getText().toString()+"&prix="+prix.getText()+"&placed="+placed.getText()+"&image="+image.getText();

        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp=new JSONParser();
           
                
               }});
         NetworkManager.getInstance().addToQueueAndWait(req);
    }
        
        
    
}
