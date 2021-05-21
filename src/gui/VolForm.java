/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entities.vol;
import Services.VolService;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class VolForm extends Form {
      private Form current;
      
     public VolForm(Form previous) throws IOException {
       VolService es= new VolService();
       int i;
     ArrayList <vol> listVol =new ArrayList <>();
     listVol=es.afficheVol();
        setTitle("Vol");
        setLayout(BoxLayout.y());
         
        for(i=0;i<listVol.size();i++){
               Label tfnom = new Label("aeroport de depart:"+listVol.get(i).getAero_d());
                
               Label tfdesc = new Label("Date de depart:"+listVol.get(i).getDate_d());
               /*       Image red = Image.createImage("file:///C:\\Users\\Admin\\Desktop\\imagajava\\"+listVol.get(i).getBrochure_filename());
               ImageViewer  image;
               image = new ImageViewer (red);*/
            
        
        
        addAll(tfnom,tfdesc);
        }
           Button btnValider = new Button("Reserver");
        btnValider.addPointerPressedListener(l -> {
             new ReservationForm(current).show();
        });
 
        addAll(btnValider);
     
    }
}
