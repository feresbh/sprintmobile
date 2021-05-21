/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
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
import entities.Hotel;
import services.ServiceHotel;
import java.util.ArrayList;

/**
 *
 * @author lenovo
 */
public class ListHotelForm extends BaseForm {

    Form current;

    public ListHotelForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("AjouterHotel");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

//        addTab(swipe,s1,res.getImage("logo0.png"),"","",res);
        //
        swipe.setUIID("Container");
//        swipe.getContentPane().setUIID("Container");
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

        ArrayList<Hotel> list = ServiceHotel.getInstance().affichageHotel();

        for (Hotel ho : list) {
            String urlImage = "logo0.png";
            Image placeHolder = Image.createImage(120, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);

            addButton(urlim, ho, res);
            ScaleImageLabel image = new ScaleImageLabel(urlim);

            Container containerImg = new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        }

        //
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }

        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }

        ScaleImageLabel imageScale = new ScaleImageLabel(image);

        imageScale.setUIID("container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("", "Imageoverlay");

        Container page1
                = LayeredLayout.encloseIn(
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

        swipe.addTab("", res.getImage("logo0.png"), page1);
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

    private void addButton(Image img, Hotel ho, Resources res) {

        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);

        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);

        // TextArea ta = new TextArea(nomhotel);
        //ta.setUIID("newtopline");
        //ta.setEditable(false);
        Label nametxt = new Label("Nome hotel :" + ho.getNomhotel(), "NewTopLine2");
        Label siteWebtxt = new Label("siteWeb :" + ho.getSiteWeb(), "NewTopLine2");
        Label emailtxt = new Label("email :" + ho.getEmail(), "NewTopLine2");
        Label adressetxt = new Label("adresse :" + ho.getAdresse(), "NewTopLine2");
        Label telephonetxt = new Label("telephone :" + ho.getTelephone(), "NewTopLine2");
        Label descriptiontxt = new Label("description :" + ho.getDescription(), "NewTopLine2");

        createLineSeparator();

        //supp button
        Label lsupprimer = new Label(" ");
        lsupprimer.setUIID("NewsTopLine");
        Style supprimerStyle = new Style(lsupprimer.getUnselectedStyle());
        supprimerStyle.setFgColor(0xf21f1f);

        FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
        lsupprimer.setIcon(supprimerImage);
        lsupprimer.setTextPosition(RIGHT);

        //quand on clique 
        lsupprimer.addPointerPressedListener(l -> {

            Dialog dig = new Dialog("suppression");
            if (dig.show("suppression", "voulez vous supprimer l'hotel ?", "Annuler", "oui")) {
                dig.dispose();
            } else {
                dig.dispose();
                if (ServiceHotel.getInstance().deleteHotel(ho.getId())) {
                    new ListHotelForm(res).show();
                }
            }
        });

        //update icon
        Label lmodifier = new Label(" ");
        lmodifier.setUIID("NewsTopLine");
        Style modifierStyle = new Style(lmodifier.getUnselectedStyle());
        supprimerStyle.setFgColor(0xf7ad02);

        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lmodifier.setIcon(mFontImage);
        lmodifier.setTextPosition(LEFT);

        lmodifier.addPointerPressedListener(l -> {
            // System.out.println("update");
            new ModifierHotelForm(res, ho).show();
        }
        );

        // cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(nametxt),BoxLayout.encloseX(siteWebtxt),BoxLayout.encloseX(emailtxt),BoxLayout.encloseX(adressetxt),BoxLayout.encloseX(telephonetxt),BoxLayout.encloseX(descriptiontxt)));
        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(BoxLayout.encloseX(nametxt, lsupprimer, lmodifier), BoxLayout.encloseX(siteWebtxt), BoxLayout.encloseX(emailtxt), BoxLayout.encloseX(adressetxt), BoxLayout.encloseX(telephonetxt), BoxLayout.encloseX(descriptiontxt)));
        add(cnt);
    }

}
