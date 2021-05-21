/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Services.ReservationService;
import Services.VolService;
import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import static gui.AffReservation.idExp;
import static gui.VolADMIN.idF;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Admin
 */
public class VolFormADMIN extends Form {
       private Form current;
    
    public VolFormADMIN(Form previous) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
        */
        
        setTitle("Ajouter Vol ADMIN");
        setLayout(BoxLayout.y());
        VolService rs=new VolService();
        TextField tfidvol = new TextField("","id vol");
        TextField tfaeroa = new TextField("","aeroport de depart");
         TextField tfaerod = new TextField("","aeroport d'Arriver");
             TextField place = new TextField("","Place Disponible");
                 TextField image = new TextField("","Image");
                     TextField prix = new TextField("","prix");
        Button btnReserver= new Button("Ajouter");
         Button btnRetourn= new Button("Retour");
           Button btnUpdate= new Button("Update");
        btnReserver.addActionListener(e->{
            rs.AjouterVol(tfidvol, tfaerod, tfaeroa, prix, place, image.getText());
             try {
       new VolADMIN(current).show();
       } catch (IOException ex) {
       }
           
        });
              Label ImageF = new Label();
         Button btnimg= new Button("img");
            btnimg.addActionListener(e->{
                String path=Capture.capturePhoto(Display.getInstance().getDisplayWidth(),-1);
                File f=new File(path);
                 image.setText(f.getName());
              f.renameTo(new File("C:\\Users\\Admin\\Desktop\\aa\\" + image.getText()));
               
                             try {
                Image img= Image.createImage(path);
                ImageF.setIcon(img);
                 revalidate();
            } catch (IOException ex) {
                
            }

       });
        
           btnUpdate.addActionListener(e->{
            rs.UpdateVol(tfidvol, tfaerod, tfaeroa, prix, place, image,idF);
             try {
       new VolADMIN(current).show();
       } catch (IOException ex) {
       }
           
        });
        
         btnRetourn.addActionListener(e->{
          try {
       new VolADMIN(current).show();
       } catch (IOException ex) {
       }
         });
        
        
        
        addAll(tfidvol, tfaeroa, tfaerod,place,image,prix,ImageF,btnimg,btnReserver,btnUpdate,btnRetourn);
    }
    
}
