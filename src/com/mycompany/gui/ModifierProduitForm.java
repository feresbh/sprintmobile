/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import entities.Product;
import services.ServiceProduit;

/**
 *
 * @author MY HP
 */
public class ModifierProduitForm extends BaseForm {

    Form current;

    public ModifierProduitForm(Resources res, Product p) {

        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Produit");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        TextField name = new TextField(p.getName(), "Name", 20, TextField.ANY);
        TextField image = new TextField(p.getImage(), "Image", 20, TextField.ANY);
        TextField subtitle = new TextField(p.getSubtitle(), "Subtitle", 20, TextField.ANY);
        TextField prix = new TextField(String.valueOf(p.getPrix()), "Prix", 20, TextField.ANY);
        TextField description = new TextField(p.getDescription(), "Description", 20, TextField.ANY);
        TextField lat = new TextField(String.valueOf(p.getLat()), "Lat", 20, TextField.ANY);
        TextField lon = new TextField(String.valueOf(p.getLon()), "Lon", 20, TextField.ANY);

        name.setUIID("NewsTopLine");
        image.setUIID("NewsTopLine");
        subtitle.setUIID("NewsTopLine");
        prix.setUIID("NewsTopLine");
        description.setUIID("NewsTopLine");
        lat.setUIID("NewsTopLine");
        lon.setUIID("NewsTopLine");

        name.setSingleLineTextArea(true);
        image.setSingleLineTextArea(true);
        subtitle.setSingleLineTextArea(true);
        prix.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);
        lat.setSingleLineTextArea(true);
        lon.setSingleLineTextArea(true);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //OnClick Button
        btnModifier.addPointerPressedListener(l -> {

            p.setName(name.getText());
            p.setImage(image.getText());
            p.setSubtitle(subtitle.getText());
            p.setPrix(Double.valueOf(prix.getText()));
            p.setDescription(description.getText());
            p.setLat(Double.valueOf(lat.getText()));
            p.setLon(Double.valueOf(lon.getText()));

      

        //Appel a la methode UPDATE
        if (ServiceProduit.getInstance().modifierProduct(p)) {
            //If True
            new ListProduitsForm(res).show();

        }
          });
        Button btnAnnuler = new Button("Annuler");
        btnAnnuler.addActionListener(e -> {

            new ListProduitsForm(res).show();

        });

        Label l2 = new Label("");
        Label l3 = new Label("");
        Label l4 = new Label("");
        Label l5 = new Label("");

        Label l1 = new Label();

        Container content = BoxLayout.encloseY(
                l1, l2,
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
            
                btnModifier,
                btnAnnuler
                
        );
                        
    

add(content);
show();
                       
        
    }
    
}
