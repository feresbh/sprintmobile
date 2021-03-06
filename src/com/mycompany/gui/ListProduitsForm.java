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
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Product;
import com.mycompany.services.ServiceProduit;
import java.util.ArrayList;

/**
 *
 * @author MY HP
 */
public class ListProduitsForm extends BaseForm {

    Form current;

    public ListProduitsForm(Resources res) {

        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Produit");
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
     
        
        
        
         
        RadioButton liste = RadioButton.createToggle("Liste BonPlans", barGroup);
        liste.setUIID("SelectBar");
        
        liste.addActionListener(l-> { 
           
          Dialog dig= new Dialog("ListeBonPlans");
          new ListProduitsForm(res).show();
          
           
       });
        
        RadioButton partage = RadioButton.createToggle("Ajouter BonPlan", barGroup);
        partage.setUIID("SelectBar");
        partage.addActionListener(l-> { 
           
          Dialog dig= new Dialog("Ajouter BonPlan");
         
          new AjoutProductForm(res).show();
           
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
                GridLayout.encloseIn(2,liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        liste.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(liste, arrow);
        });
       // bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        //Fonction Affichage
        ArrayList<Product> list = ServiceProduit.getInstance().affichageProduits();

        for (Product pro : list) {
            String urlImage = "back-logo.png";
            
           Image placeHolder= Image.createImage(120,90);
           EncodedImage enc= EncodedImage.createFromImage(placeHolder, false);
           URLImage urlim=URLImage.createToStorage(enc, urlImage, urlImage,URLImage.RESIZE_SCALE);
           
           

           

            
            addButton(urlim, pro,res);

            
            ScaleImageLabel image= new ScaleImageLabel(urlim);
            
            Container container= new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
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

    private void addButton(Image img,  Product pro,Resources res) {
       
        
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
        Label nametxt=new Label("Name: "+pro.getName(),"NewsTopLine2");
        Label descriptiontxt=new Label("Description: "+pro.getDescription(),"NewsTopLine2");
        Label prixtxt=new Label("Prix: "+pro.getPrix(),"NewsTopLine2");
        
        createLineSeparator();
        
        
       //cnt.add(BorderLayout.CENTER, BoxLayout.encloseY((nametxt),BoxLayout.encloseX(descriptiontxt)));
        
       //Supprimer Button
       Label lSupprimer= new Label(" ");
       lSupprimer.setUIID("NewsTopLine");
       Style supprimerStyle= new Style(lSupprimer.getUnselectedStyle());
       supprimerStyle.setFgColor(0xf21f1f);
       
       FontImage supprimerImage= FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
       lSupprimer.setIcon(supprimerImage);
       lSupprimer.setTextPosition(RIGHT);
       
       
       //ONClick delete Icon
       
       lSupprimer.addPointerPressedListener(l-> { 
           
          Dialog dig= new Dialog("Suppression");
          
          if(dig.show("Suppression","Voulez-vous supprimer ce produit? ","Annuler","Oui")){
              
              dig.dispose();
          }else{
              
              dig.dispose();
              //Appel a la methode supprimer
              if(ServiceProduit.getInstance().deleteProduct(pro.getId())){
                  
                  new ListProduitsForm(res).show();
              }
              
              
          }
           
       });
       
       //UPdate ICON
        Label lModifier= new Label(" ");
       lModifier.setUIID("NewsTopLine");
       Style modifierStyle= new Style(lModifier.getUnselectedStyle());
       modifierStyle.setFgColor(0xf7ad02); 
       
       FontImage mFontImage= FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
       lModifier.setIcon(mFontImage);
       lModifier.setTextPosition(LEFT);
       
       
       //ONCLICK UPDATE
    
       
       lModifier.addPointerPressedListener(l-> { 
           
           //System.out.println("Hello UPDATE");
           new ModifierProduitForm(res,pro).show();
           
       });
       
       
       
       //Reserver ICON
       /*
        Label lReserver= new Label(" ");
       lReserver.setUIID("NewsTopLine");
       Style reserverStyle= new Style(lReserver.getUnselectedStyle());
       reserverStyle.setFgColor(0xf7ad02); 
       
       FontImage mmFontImage= FontImage.createMaterial(FontImage.MATERIAL_ADD_PHOTO_ALTERNATE, reserverStyle);
       lReserver.setIcon(mmFontImage);
       lReserver.setTextPosition(LEFT);
       
       
       //ONCLICK UPDATE
    
       
       lReserver.addPointerPressedListener(l-> { 
           
           //System.out.println("RESERVER!");
           new DetailsProduitForm(res,pro).show();
           
       });
       */
       
       
       
       
       cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(
               BoxLayout.encloseX(nametxt),
               BoxLayout.encloseX(descriptiontxt),
               BoxLayout.encloseX(prixtxt,lModifier,lSupprimer)));
       
       
       
       
       add(cnt);

    }
}
