/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;


import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Hotel;
import services.ServiceHotel;
import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPSSLTransport;
import com.sun.mail.smtp.SMTPTransport;
//import com.teknikindustries.bulksms.SMS;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javax.mail.Message;


import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import org.controlsfx.control.Notifications;


/**
 *
 * @author lenovo
 */
public class AjouterHotelForm extends BaseForm {
    
    
    Form current;
    public AjouterHotelForm(Resources res)
    {
        super("Newsfeed",BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("AjouterHotel");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e -> {
            
        });
        super.addSideMenu(res);
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
//        addTab(swipe,s1,res.getImage("logo0.png"),"","",res);
        
        //
          swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

//        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Les hotels", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Reclamer", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();

            final Dialog ipDlg = ip.showInifiniteBlocking();
            new ListHotelForm(res).show();
            refreshTheme();
        });
        liste.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();

            final Dialog ipDlg = ip.showInifiniteBlocking();
            new AjouterHotelForm(res).show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

       
        
        //
        
        //Nomhotell
        TextField Nomhotel = new TextField("", "enter Nomhotel");
        Nomhotel.setUIID("TextFieldBlack");
        addStringValue("Nomhotel",Nomhotel);
        
        //SiteWeb
         TextField SiteWeb = new TextField("", "enter SiteWeb");
        SiteWeb.setUIID("TextFieldBlack");
        addStringValue("SiteWeb",SiteWeb);
        
        
        //Email
         TextField Email = new TextField("", "enter Email");
        Email.setUIID("TextFieldBlack");
        addStringValue("Email",Email);
        
        
        //Telephone
         TextField Telephone = new TextField("", "enter Telephone");
        Telephone.setUIID("TextFieldBlack");
        addStringValue("Telephone",Telephone);
        
        
        //Adresse
         TextField Adresse = new TextField("", "enter Adresse");
        Adresse.setUIID("TextFieldBlack");
        addStringValue("Adresse",Adresse);
        
        
        //Description
         TextField Description = new TextField("", "enter Description");
        Description.setUIID("TextFieldBlack");
        addStringValue("Description",Description);
        
        
        Button btnAjouter = new Button("Ajouter hotel");
        addStringValue("", btnAjouter);
        
        //onclick button event
        
        btnAjouter.addActionListener((e) -> {
            try
            {
                if (Nomhotel.getText().equals("")|| SiteWeb.getText().equals("")|| Email.getText().equals("")  || Telephone.getText().equals("aaaa")  || Adresse.getText().equals("")  || Description.getText().equals("") )
                {
                    Dialog.show("verifier les donnes","" ,"Annuler","OK");
                }
                
                else 
                {
                        InfiniteProgress ip = new InfiniteProgress(); //loading after insert data
                        final Dialog iDialog = ip.showInfiniteBlocking();
                        
                        Hotel h = new Hotel(String.valueOf(Nomhotel.getText()).toString(),
                        
                                String.valueOf(SiteWeb.getText()).toString(),
                                String.valueOf(Email.getText()).toString(),
                                String.valueOf(Telephone.getText()).toString(),
                                String.valueOf(Adresse.getText()).toString(),      
                                String.valueOf(Description.getText()).toString()
                                            );
                        System.out.println("data Hotel  == "+h);
                                
                                ServiceHotel.getInstance().ajouterHotel(h);
                                
                                iDialog.dispose();  //pour annuler le loading after ajouter
                                
                                new ListHotelForm(res).show();
                                
                                refreshTheme();
               
                }
            }catch (Exception ex) 
            {
                ex.printStackTrace();
            }
        });
        // mail
        sendMail();
        // sms
        //SMS smsTut = new SMS();
        //smsTut.SendSMS("jribi99", "Jribijribi99", "Hello customer this is Travminers agency, we are pleased to have you in our comunity.", "21650158932", "https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");
    // notification 
       
    }

    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"paddedLabel"))
        .add(BorderLayout.CENTER,v) );
        add(createLineSeparator(0xeeeeee));
        
       
        
        
    }

    private void addTab(Tabs swipe,Label spacer  ,Image image, String string, String text, Resources res) {
     int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
     
     if (image.getHeight() < size)
     {
         image = image.scaledHeight(size);
     }
     
     
     
     if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2)
     {
         image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
     }
     
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        
        imageScale.setUIID("container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay = new Label("","Imageoverlay");
        
        
        Container page1 = 
                LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargewhiteText"),
                                        //FlowLayout.encloseIn(null),
                                        spacer
                                        
                                        
                                )
                        )
                );
        
        swipe.addTab("", res.getImage("logo0.png"),page1);
    }
    
    
    public void bindButtonSelection(Button btn, Label l)
    {
        btn.addActionListener(e -> {
        if(btn.isSelected()) {
         updateArrowPosition(btn,l); 
        }
    });
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX()+ btn.getWidth() /2 - l.getWidth() / 2);
        l.getParent().repaint();
    }
     public void sendMail()
    {
        try {
            Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp"); //SMTP Host
                props.put("mail.smtps.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtps.auth", "true"); //enable authentication

               Session session = Session.getInstance(props,null);
                
               MimeMessage msg = new MimeMessage( session);
                
                msg.setFrom(new InternetAddress("medazizbensmida@gmail.com"));
                msg.setRecipients(Message.RecipientType.TO,"medazizbensmida@gmail.com");
                msg.setSubject("traveminers");
                
                String txt = "L'hotel a ete ajoutter avec succes";
                msg.setText(txt);
          //      String mp = "";
            //    String txt = "salut";
                
                SMTPTransport st = (SMTPTransport)session.getTransport("smtps");
                st.connect("smtp.gmail.com",465,"medazizbensmida@gmail.com","Iwillbeericher1003");
                st.sendMessage(msg, msg.getAllRecipients());
                
             //   System.out.println("com.mycompany.gui.AjouterHotelForm.sendMail()");
                
                
                
        }catch(Exception e)
                {
                  e.printStackTrace();
                  
                }
    }
    
}