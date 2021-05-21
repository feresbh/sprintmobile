/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Voiture;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import services.ServiceVoiture;

/**
 *
 * @author HP
 */
public class AddVoitureForm extends BaseForm {

    Form current;

    public AddVoitureForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);

        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("Add a new voiture");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();
        Label s1 = new Label();
        Label s2 = new Label();

        //addTab(swipe, s1, res.getImage("back-logo.png"), "", "", res);
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
        RadioButton mesListes = RadioButton.createToggle("liste des voitures", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("locations", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("ajouter voiture", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            AddVoitureForm a = new AddVoitureForm(res);
            a.show();
            refreshTheme();
        });
        
        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            ListVoitureForm a = new ListVoitureForm(res);
            a.show();
            refreshTheme();
        });
        liste.addActionListener((e)->{
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
            ListVoitureLouee a = new ListVoitureLouee(res);
            a.show();
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
        //setLayout(BoxLayout.y());
        // auto complete
        String[] listeMarques = new String[]{"seat", "bmw", "mercedes"};
        AutoCompleteTextField tfMarque = new AutoCompleteTextField(listeMarques);

        TextField tfModel = new TextField("", "model");
        TextField tfPrix = new TextField("", "prix");
        TextField tfcouleur = new TextField("", "couleur");
        TextField tfserie = new TextField("", "serie");
        TextField tfimage = new TextField("", "image");

        Button btnValider = new Button("Add voiture");
        Button img1 = new Button("upload image");

        tfMarque.setUIID("TextFieldBlack");
        tfModel.setUIID("TextFieldBlack");
        tfPrix.setUIID("TextFieldBlack");
        tfcouleur.setUIID("TextFieldBlack");
        tfserie.setUIID("TextFieldBlack");
        tfimage.setUIID("TextFieldBlack");

        addStringValue("Marque", tfMarque);
        addStringValue("Model", tfModel);
        addStringValue("couleur", tfcouleur);
        addStringValue("serie", tfserie);
        addStringValue("Prix", tfPrix);
        addStringValue("Prix", tfimage);
        addStringValue("upload image", img1);
        addStringValue("Add voiture", btnValider);

        img1.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            String filename = f.getName();
            tfimage.setText(filename);
            javafx.scene.image.Image getAbsolutePath = null;
            ImageIcon icon = new ImageIcon(filename);
            InputStream stream = null;

            try {
                stream = new FileInputStream("C:\\Users\\HP\\Documents\\school\\SEM2\\PiDev\\SprintWeb\\public\\images\\voiture\\" + filename + "");
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
            //javafx.scene.image.Image image = new javafx.scene.image.Image(stream);
            //image_update.setImage(image);
        });

        btnValider.addActionListener((ActionEvent e) -> {

            try {
                if (tfMarque.getText().length() == 0 || tfModel.getText().length() == 0 || tfPrix.getText().length() == 0) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    Voiture v = new Voiture(tfMarque.getText(), tfModel.getText(), Integer.parseInt(tfPrix.getText()));
                    v.setCouleur(tfserie.getText());
                    v.setSerie(tfserie.getText());
                    v.setImage(tfimage.getText());
                    ServiceVoiture.getInstance().addVoiture(v);
                    iDialog.dispose();
                    // changing view
                    new ListVoitureForm(res).show();
                    refreshTheme();
                    /*try {
                        Voiture v = new Voiture(tfMarque.getText(), tfModel.getText(), Integer.parseInt(tfPrix.getText()));
                        if (ServiceVoiture.getInstance().addVoiture(v)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "prix must be a number", new Command("OK"));
                    }*/
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
//        addAll(tfMarque,tfModel,tfPrix,btnValider);
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        /*if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        if (image.getHeight() < Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }*/
        ScaleImageLabel imageScale = new ScaleImageLabel();
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overlay = new Label("", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        imageScale,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        FlowLayout.encloseIn(null),
                                        spacer
                                )
                        )
                );
        swipe.addTab("", res.getImage("back-logo.png"), page1);
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

    protected String saveFileToDevice(String hi, String ext) throws IOException, URISyntaxException {
        URI uri;
        try {
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            hi = hi.substring(index + 1);
            return hi;
        } catch (URISyntaxException ex) {
        }
        return "hh";
    }

}
