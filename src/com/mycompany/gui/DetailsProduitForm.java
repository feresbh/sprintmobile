/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import entities.Order;
import entities.Panier;
import entities.Product;
import services.ServiceProduit;

/**
 *
 * @author MY HP
 */
public class DetailsProduitForm extends BaseForm {
     Form current;
    // Panier pp;
    public DetailsProduitForm(Resources res, Product p) {

        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Produit");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        TextField id = new TextField(String.valueOf(p.getId()), "Name", 0, TextField.UNEDITABLE);
        TextField name = new TextField(p.getName(), "Name", 0, TextField.UNEDITABLE);
        TextField image = new TextField(p.getImage(), "Image", 0, TextField.UNEDITABLE);
        TextField subtitle = new TextField(p.getSubtitle(), "Subtitle", 0, TextField.UNEDITABLE);
        TextField prix = new TextField(String.valueOf(p.getPrix()), "Prix", 0, TextField.UNEDITABLE);
        TextField description = new TextField(p.getDescription(), "Description", 0, TextField.UNEDITABLE);
        TextField lat = new TextField(String.valueOf(p.getLat()), "Lat", 0, TextField.UNEDITABLE);
        TextField lon = new TextField(String.valueOf(p.getLon()), "Lon", 0, TextField.UNEDITABLE);
        TextField qt = new TextField(String.valueOf(p.getQuantity()), "qt", 20, TextField.ANY);
        //TextField total = new TextField(String.valueOf(p.getTotal()), "tot", 20, TextField.ANY);

        id.setUIID("NewsTopLine");
        name.setUIID("NewsTopLine");
        image.setUIID("NewsTopLine");
        subtitle.setUIID("NewsTopLine");
        prix.setUIID("NewsTopLine");
        description.setUIID("NewsTopLine");
        lat.setUIID("NewsTopLine");
        lon.setUIID("NewsTopLine");
        qt.setUIID("NewsTopLine");

        
        id.setEditable(false);
        
         name.setEditable(false);

        image.setEditable(false);

        subtitle.setEditable(false);

                prix.setEditable(false);

                description.setEditable(false);

                lat.setEditable(false);

                lon.setEditable(false);

        qt.setSingleLineTextArea(true);

        Button btnModifier = new Button("Reserver");
        btnModifier.setUIID("Button");

        //OnClick Button Reserver
        btnModifier.addPointerPressedListener(l -> {
if(Integer.parseInt(qt.getText())!=0){
            System.out.println("RESERVER PRODUIT");
            //int total= Integer.parseInt(qt.getText())*Integer.parseInt(prix.getText());
                                          // double tot=Integer.parseInt(qt.getText())*(p.getQuantity());

             ServiceProduit.getInstance().AjouterProductPanier(Integer.parseInt(id.getText()),Integer.parseInt(qt.getText()),Float.parseFloat(prix.getText()));
             System.out.println("l id pris est "+Integer.parseInt(id.getText()));
             

                 new PanierProduitsForm(res).show();
                  

                 System.out.println("DONE");
} else{ 
    System.out.println("quantity error ");

 Dialog dig= new Dialog("Quantity problem");
          
          dig.show("Failed","Quantity doit etre >0  ","","OK");
              
              dig.dispose();

}

          });
        
         Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {

             new UserListProductForm(res).show();

        });

        Label l2 = new Label("");
        Label l3 = new Label("");
        Label l4 = new Label("");
        Label l5 = new Label("");

        Label l1 = new Label();

        Container content = BoxLayout.encloseY(
                l1, l2,
                   new FloatingHint(id),
                createLineSeparator(),
                
                new FloatingHint(name),
                createLineSeparator(),
                
                new FloatingHint(image),
                createLineSeparator(),
                
                 new FloatingHint(subtitle),
                createLineSeparator(),
                
                 new FloatingHint(prix),
                createLineSeparator(),
                
                 new FloatingHint(description),
                createLineSeparator(),
                
                 new FloatingHint(lat),
                createLineSeparator(),
                
                 new FloatingHint(lon),
                createLineSeparator(),
                
                new FloatingHint(qt),
                createLineSeparator(),
            
                btnModifier,
                btnAnnuler
               
                
        );
                        
    

add(content);
show();
                       
        
    }
    
}