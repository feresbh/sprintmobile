/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import entities.Category;
import services.ServiceCategory;

/**
 *
 * @author MY HP
 */
public class AjoutCategoryForm  extends BaseForm{
   
    Form current;
    public AjoutCategoryForm(Resources res){
        super("Newsfeed",BoxLayout.x());
        //NewsfeedForm
        Toolbar tb= new Toolbar(true);
        current= this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Ajout Category");
        getContentPane().setScrollVisible(false);
        
        
        
         Button btnAjouter= new Button("Ajouter");
        addStringValue("",btnAjouter);
        
        
        TextField name=new TextField("","Entrer nom!");
        name.setUIID("TextFieldBlack");
        addStringValue("name",name);
    
        
        
       
        
        //onclick btn event
        
        btnAjouter.addActionListener((e)-> {
            
           try{
               
               if(name.getText()== " "){
                   Dialog.show("veuillez verifier les inputs", "","Annuler", "OK");
                   
               }
               else {
                   
                   InfiniteProgress ip=new InfiniteProgress();;
                   final Dialog iDialog=ip.showInfiniteBlocking();
                   
                   Category t = new Category();
               
                
                   Category ct=new Category(String.valueOf(name.getText().toString()));
                  // System.out.println("data cat"+ct);
                   
                   ServiceCategory.getInstance().ajoutCategory(ct);
                   iDialog.dispose();
                   refreshTheme();
                   
                           
                   
               }
           } 
           catch(Exception ex){
               System.out.println("exception"+ex.getMessage());
               
           }
        });
    } 

    private void addStringValue(String s, Component v) {
add(BorderLayout.west(new Label(s,"PaddedLabel"))
.add(BorderLayout.CENTER,v)
);



    }
    
}
