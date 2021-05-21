/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import entities.Forum;
import services.ServiceForum;
import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class ListeForumForm extends Form {
      public ArrayList<Forum> forums;
    Form current;
    private Resources theme;
    
    public ListeForumForm() {
         setTitle("Forum");
        setLayout(BoxLayout.y());
  TextField tfName = new TextField("", "ForumName");
  TextField tfEmail = new TextField("", "Email Address");
        TextField tfDescription = new TextField("", "description Forum");
        Button btnValider = new Button("Add Forum");
        Button home = new Button("home");

           btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfDescription.getText().length() == 0) || (tfEmail.getText().length()==0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Forum f = new Forum(tfName.getText(),tfDescription.getText());
                        if (ServiceForum.getInstance().addForum(f)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }

            }
        });
           //home.addActionListener(e-> new MenuFront(theme).show());

  addAll(home,tfName, tfDescription, tfEmail,btnValider);

        forums = ServiceForum.getInstance().getAllForums();
        for (Forum obj : forums) {
            setLayout(BoxLayout.y());

            Button spTitle = new Button();
            SpanLabel sp = new SpanLabel();
            Button Delete = new Button("Delete");
            Button Modif = new Button("Modifier");

            spTitle.setText("Title : " + obj.getDescription());
            spTitle.addActionListener(e -> {
                ServiceForum.getInstance().detailForum(obj.getId());
                System.out.println("heeeere"+obj.getId());
                new ListPostForm(current,obj).show();
                
                        
            });
            sp.setText("Description : " + obj.getDescription());
            Delete.addActionListener(e
                    -> {
                System.out.println(obj.getId());

                ServiceForum.getInstance().deleteForum(obj.getId());
                new ListeForumForm().show();
            });
            Modif.addActionListener((evt) -> {
               new ModifForumForm(current,obj).show();

            });

            addAll(spTitle, Delete, Modif, sp);
        }
        // sp.setText(new ServiceForum().getAllForums().toString());

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> current.showBack());
    }

}

