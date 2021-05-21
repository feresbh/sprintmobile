/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.SendMail;
import Entities.reservation;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import utils.DataSource;

/**
 *
 * @author Admin
 */
public class ReservationService {
    private ConnectionRequest req;
         private Form current;
    public ArrayList <reservation> afficheRese (){
        ArrayList <reservation> result =new ArrayList <>();
        req=new ConnectionRequest();
        String url= DataSource.BASE_URL+"/mobile/afficheeR";

        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp=new JSONParser();
                try{
                    Map<String,Object> mapRese=jsonp.parseJSON(new CharArrayReader(new String (req.getResponseData()).toCharArray()));
                    List <Map<String,Object>> listOfMap =(List <Map<String,Object>>) mapRese.get("root");
                    System.out.println(""+listOfMap.toString());
                    for( Map<String,Object> obj :listOfMap){
                        reservation r =new reservation();
                        
                         float id=Float.parseFloat(obj.get("id").toString());
                          float cin=Float.parseFloat(obj.get("cin").toString());
                           float num=Float.parseFloat(obj.get("numP").toString());
                           String email=obj.get("email").toString();
                         
                           
                    
                        
                       String DateCon =obj.get("dateV").toString().substring(0,obj.get("dateV").toString().indexOf("T"));
                        System.out.println("date con"+DateCon);      
                        
                        r.setId((int)id);
                        r.setCin((int)cin);
                        r.setNum_p((int)num);
                        r.setEmail(email);
                       
                        r.setDate_v(DateCon);
                       result.add(r);
                         
                    
                    }
                    
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
        
                    }
    
    public void supprimerRes(int id){
        
        req=new ConnectionRequest();
        String url= DataSource.BASE_URL+"/mobile/suppR?id="+id;

        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp=new JSONParser();
                String Json = new String(req.getResponseData()) + "";
                
                    }
        });
        
          NetworkManager.getInstance().addToQueueAndWait(req);
    }
    public void ajouterRes(TextField email,TextField cin,TextField tel){
         req=new ConnectionRequest();
        String url= DataSource.BASE_URL+"/mobile/reservation/ajouterRes?email="+email.getText().toString()+"&cin="+cin.getText().toString()+"&tel="+tel.getText().toString();

        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp=new JSONParser();
                 SendMail m=new SendMail() ;
                   m.send(email.getText(),"Activation du compte",tel.getText(),"jihenedorgham72@gmail.com","181JMT1043",12);
                
                    }
        });
         NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
        public void UpdateRes(TextField email,TextField cin,TextField tel,int id){
         req=new ConnectionRequest();
        String url= DataSource.BASE_URL+"/mobile/reservation/updateRes?id="+id+"&email="+email.getText().toString()+"&cin="+cin.getText().toString()+"&tel="+tel.getText().toString();

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
