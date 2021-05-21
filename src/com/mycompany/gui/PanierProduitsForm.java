/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import java.util.Properties;
//import com.codename1.io.Properties;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.util.Base64;
import entities.Order;
import entities.OrderDetails;
import entities.Panier;
import entities.Product;
import services.ServiceProduit;
import shelfie.daos.BookDAO;
import com.sun.mail.smtp.SMTPTransport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author MY HP
 */
public class PanierProduitsForm extends BaseForm {

    Form current;
  String accountSID = "ACb79f0b97cadcb593285c929ac05d0357";
    String authToken = "753acfe23069ddeab9b1bc311701beea";
    String fromPhone = "+13203346023";
        String too = "+21654164001";
       static double  sommeee = 0;
         static double  malek = 0;
         
       
       
    public PanierProduitsForm(Resources res) {

        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Welcome to BonPlans List");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e -> {

        });
        super.addSideMenu(res);
        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();
        addTab(swipe, s1, res.getImage("back.png"), "", "", res);
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

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Ma Panier", barGroup);
        mesListes.setUIID("SelectBar");

        mesListes.addActionListener(l -> {

            Dialog dig = new Dialog("Panier");
            new PanierProduitsForm(res).show();

        });

        RadioButton liste = RadioButton.createToggle("Liste BonPlans", barGroup);
        liste.setUIID("SelectBar");

        liste.addActionListener(l -> {

            Dialog dig = new Dialog("ListeBonPlans");
            new UserListProductForm(res).show();

        });

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        /* mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            //  ListReclamationForm a = new ListReclamationForm(res);
            //  a.show();
            refreshTheme();
        });*/
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, liste),
                FlowLayout.encloseBottom(arrow)
        ));

        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(liste, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        // bindButtonSelection(mesListes, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        //Fonction Affichage
        //  ArrayList<Panier> list = ServiceProduit.getInstance().affichagePanier();
        try {
            //System.out.println("na9ra fel affiche panier*********************");
            ArrayList<Panier> list = ServiceProduit.getInstance().affichagePanier();

            for (Panier pro : list) {
                String urlImage = "back-logo.png";

                Image placeHolder = Image.createImage(120, 90);
                EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
                URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

                addButton(urlim, pro, res);

                ScaleImageLabel image = new ScaleImageLabel(urlim);

                Container container = new Container();
                image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            }
        } catch (Exception ex) {

            System.out.println("exception+ " + ex.getMessage());
        }
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {

        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        /*
if(image.getHeight() < size){
    
    //image=image.scaledHeight(size);
}
         */
 /*
if (image.getHeight()> Display.getInstance().getDisplayHeight() / 2){
    
    image= image.scaledHeight(Display.getInstance().getDisplayHeight() /2);
    
}
         */
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        // FlowLayout.encloseIn(null),
                                        spacer
                                )
                        )
                );
        swipe.addTab("", res.getImage("dog.jpg"), page1);

    }

    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);

            }

        });

    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }

    private void addButton(Image img, Panier pro, Resources res) {

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

//Button image= new Button(img.fill(width, height));
        Button imagee = new Button(img.fill(width, height));
        // Button imagee= new Button();
        imagee.setUIID("Label");

        Container cnt = BorderLayout.west(imagee);

        /*
        TextArea ta = new TextArea(name);
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);

        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(ta));
         */
        Label idprodtxt = new Label("id: " + pro.getId(), "NewsTopLine2");

        Label quantxt = new Label("Quantity: " + pro.getQuant(), "NewsTopLine2");

        Label tottxt = new Label("Total: " + pro.getTotale(), "NewsTopLine2");

        
       /* ArrayList listtoot = new ArrayList<String>();
        listtoot.add(pro.getTotale());
        System.out.println("LISTAAAAAAAAAAAAA tottxt"+listtoot);
    */
        List<Double> lista = new ArrayList<Double>();
        malek=pro.getTotale();
        lista.add(malek);
        System.out.println("lista"+lista);
        
        
          
        
    
        
        
           // List<Double> listtt = new ArrayList<>(Arrays.asList(listtoot));       
        //Label quantxt=new Label("quantity: "+pro.getQuantity(),"NewsTopLine2");
        createLineSeparator();

        //cnt.add(BorderLayout.CENTER, BoxLayout.encloseY((nametxt),BoxLayout.encloseX(descriptiontxt)));
        //ViderPanier ICON
        Label lReserver = new Label(" ");
        lReserver.setUIID("NewsTopLine");
        Style reserverStyle = new Style(lReserver.getUnselectedStyle());
        reserverStyle.setFgColor(0xf7ad02);

        FontImage mmFontImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, reserverStyle);
        lReserver.setIcon(mmFontImage);
        lReserver.setTextPosition(LEFT);

        //ONCLICK ViderPanier
        lReserver.addPointerPressedListener(l -> {

            ServiceProduit.getInstance().viderPanier();

            pro.setTotale(0);

            new PanierProduitsForm(res).show();

        });
        
        
        
        
         //OrderICON
        Label lOrder = new Label(" ");
        lOrder.setUIID("NewsTopLine");
        Style orderStyle = new Style(lOrder.getUnselectedStyle());
        orderStyle.setFgColor(0xf7ad02);

        FontImage mmmFontImage = FontImage.createMaterial(FontImage.MATERIAL_ADD, orderStyle);
        lOrder.setIcon(mmmFontImage);
        lOrder.setTextPosition(LEFT);

        //ONCLICK Order
        lOrder.addPointerPressedListener(l -> {

            InfiniteProgress ip= new InfiniteProgress();
            final Dialog ipDialog= ip.showInfiniteBlocking();
            
         double sum = 0;
for(int i = 0; i < lista.size(); i++){
      sum += lista.get(i);
}
        System.out.println("somme elements sommmmmmme"+sum);
             Order order = new Order(5, "", malek);
    


            System.out.println("PRODUIT MALEK PANIER NOM:TOTALE"+pro.totale);
             //Book typedBook = new Book(authorTf.getText(),titleTf.getText(),categoryTf.getText(),Integer.parseInt(isbnTf.getText()));
            

         new  BookDAO().addBook(order);


//orderService.addOrder(order);
    //  ServiceProduit.getInstance().Orderr();
      //      System.out.println("Order fonctionnel");
      
      
      
      
            sendSMS("test");
             sendMail("Votre Reservation de total :"+ tottxt.getText()+"a ete bien confirmé");

            ipDialog.dispose();
            Dialog.show("Success", "Nous avons envoye un mail de confirmation .Veuillez verifier votre boite Mail",new Command("OK"));
            ServiceProduit.getInstance().viderPanier();

            pro.setTotale(0);

            new PanierProduitsForm(res).show();

        });

        
        

        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(idprodtxt),
                BoxLayout.encloseX(quantxt),
                BoxLayout.encloseX(tottxt, lReserver,lOrder)));

        add(cnt);

    }

    //mail
    
      public void sendMail(String txt)
    {
        try {
            Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp"); //SMTP Host
                props.put("mail.smtps.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtps.auth", "true"); //enable authentication

               Session session = Session.getInstance(props,null);
                
               MimeMessage msg = new MimeMessage( session);
                
                msg.setFrom(new InternetAddress("malek.ayadi@esprit.tn"));
                msg.setRecipients(Message.RecipientType.TO,"malek.ayadi@esprit.tn");
                msg.setSubject("traveminers");
                
               // String txt = "malek";
                msg.setText(txt);
          //      String mp = "";
            //    String txt = "salut";
                
                SMTPTransport st = (SMTPTransport)session.getTransport("smtps");
                st.connect("smtp.gmail.com",465,"melek.ayadi420@gmail.com","Mallouka1234");
                st.sendMessage(msg, msg.getAllRecipients());
                
             //   System.out.println("com.mycompany.gui.AjouterHotelForm.sendMail()");
                
                
                
        }catch(Exception e)
                {
                  e.printStackTrace();
                  
                }
    }
 /*   public void sendMail() {
        
      
        
        try {
            System.out.println("TLSEmail Start");
            Properties props = new Properties();

            props.put("mail.transport.protocol", "smtp"); //SMTP Protocol
            props.put("mail.smtps.host", "smtp.gmail.com"); //SMTP Host
            //  props.put("mail.smtps.port", "587"); //TLS Port
            props.put("mail.smtps.auth", "true"); //enable authentication
            //props.put("mail.smtps.starttls.enable", "true"); //enable STARTTLS

            Session session= Session.getInstance(props,null);
            
            
            String addres= "malek.ayadi@esprit.tn";
            String txtm="";
            String txt= "Votre ordre "+txtm+"a ete bien reserver";
            
            MimeMessage msg= new MimeMessage(session);
            msg.setFrom(new InternetAddress("Confirmation panier"));
            msg.setRecipients(Message.RecipientType.TO, addres.toString());
            msg.setSubject("test");
            msg.setSentDate(new Date(System.currentTimeMillis()));    
            msg.setText(txt);
            
            SMTPTransport st=(SMTPTransport)session.getTransport("smtps");
            st.connect("smtp.gmail",465,"melek.ayadi420@gmail.com","Mallouka1234");
            st.sendMessage(msg,msg.getAllRecipients());
              System.out.println("Server Response: "+st.getLastServerResponse() );      
                    
                    
        } catch (Exception ex) {
            System.out.println("probleme mail: " + ex.getMessage());

        }
    }*/
    
    public void sendSMS(String message){
        
        Response <Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
        queryParam("To", too).
        queryParam("From", fromPhone).
        queryParam("Body", message).
        header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
        getAsJsonMap();
        System.out.println(result.getResponseData());
        
        
        if(result.getResponseData() != null) {
            String error = (String)result.getResponseData().get("error_message");
        if(error != null) {
            System.out.println(error);
        }
        } else {
            //ToastBar.showErrorMessage("Error sending SMS: " + result.getResponseCode());
        }
        }
    
    
   

}
