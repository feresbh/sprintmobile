/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Voiture;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import services.ServiceVoiture;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 *
 * @author HP
 */
public class ListVoitureForm extends BaseForm {

    private Resources theme;
    Form current;

    public ListVoitureForm(Resources res) {

        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        this.theme = res;

        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("liste des voitures");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);

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
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
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

        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        //
        //setTitle("liste des voitures");
        //setLayout(BoxLayout.y());
        TextField search = new TextField("", "search");
        Button btnExel = new Button("Exel");
        this.add(btnExel);
        search.setUIID("TextFieldBlack");
        addStringValue("search", search);
        final Container lbs = this.addGuis(res);
        
        btnExel.addActionListener((ActionEvent e)->{
            export_exel();
        });

        this.add(lbs);
        search.addDataChangedListener((i1, i2) -> {
            String t = search.getText();

            if (t.length() > 1) {

                System.out.println("match");
                this.removeComponent(lbs);
                this.add(addGuis2(t,res));
                
            }
        });
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public Container addGuis(Resources c) {
        Container lbs = new Container(BoxLayout.y());
        ArrayList<Voiture> list = ServiceVoiture.getInstance().getAllVoitures();
        for (int i = 0; i < list.size(); i++) {
            int dispo = list.get(i).getDiponibilite();
            if (dispo == 1) {
                lbs.add(item(list.get(i),c));
            }
        }
        return lbs;
    }

    public Container addGuis2(String s,Resources c) {
        Container lbs = new Container(BoxLayout.y());
        ArrayList<Voiture> list = ServiceVoiture.getInstance().getAllVoitures();
        for (int i = 0; i < list.size(); i++) {
            int dispo = list.get(i).getDiponibilite();
            if (dispo == 1) {
                if (list.get(i).getMarque().equals(s)) {
                    lbs.add(item(list.get(i),c));
                }
            }
        }
        return lbs;

    }

    public Container item(Voiture v ,Resources c) {
        //Container global = new Container(BoxLayout.x());
        Container lbs = new Container(BoxLayout.y());

        Label marque = new Label(v.getMarque() + " " + v.getModel());
        Label serie = new Label(v.getSerie());
        Label prix = new Label(Integer.toString(v.getPrix()) + "TND/Jour");

        Label i = new Label();
       // Image img = Image.createImage(120, 90);
        //EncodedImage enc = EncodedImage.createFromImage(img, true);
        //URLImage urlim = URLImage.createToStorage(enc, v.getImage(), "C:\\Users\\HP\\Documents\\school\\SEM2\\PiDev\\SprintWeb\\public\\images\\voiture", URLImage.RESIZE_SCALE);

        ScaleImageLabel image = new ScaleImageLabel();
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Image img = null;
        try {
            
            img = Image.createImage("/" + v.getImage());
        } catch (IOException ex) {
            System.out.println(ex);
        }
        i.setIcon(image.getIcon());
//        Image resizedImage = getImageFromTheme(v.getImage()).scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 10)); //change value as necessary

        ImageViewer flag = new ImageViewer(img);
        flag.setWidth(30);
        flag.setHeight(20);
        marque.addPointerPressedListener(e -> new AddlocationVoitureForm(v,c).show());
        lbs.addAll(flag, marque, prix, serie);
        //global.addAll(flag, lbs);
        createLineSeparator();

        return lbs;
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

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    
    public void export_exel(){
        try {
            FileOutputStream file = null;
            
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("Détails Activités");
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Marque");
            header.createCell(2).setCellValue("Model");
            header.createCell(3).setCellValue("Couleur");
            header.createCell(4).setCellValue("Disponibilite");
            header.createCell(5).setCellValue("Serie");
            header.createCell(6).setCellValue("Prix");
            header.createCell(7).setCellValue("Image");
            ArrayList<Voiture> list = ServiceVoiture.getInstance().getAllVoitures();
            for (int i = 0; i < list.size(); i++){
                Row row = sheet.createRow(i+1);
                
                row.createCell(0).setCellValue(list.get(i).getId());
                row.createCell(1).setCellValue(list.get(i).getMarque());
                row.createCell(2).setCellValue(list.get(i).getModel());
                row.createCell(3).setCellValue(list.get(i).getCouleur());

                row.createCell(4).setCellValue(list.get(i).getDiponibilite());
                row.createCell(5).setCellValue(list.get(i).getSerie());
                row.createCell(6).setCellValue(list.get(i).getPrix());
                row.createCell(7).setCellValue(list.get(i).getImage());
            }
            file = new FileOutputStream("liste_des_voitures.xls");
            try {
                wb.write(file);
                file.close();
                File excelfile = new File("C:\\Users\\HP\\Desktop\\WorkShops-CodenameOne-master\\WorkShops-CodenameOne-master\\liste_des_voitures.xls");
                Desktop desktop = Desktop.getDesktop();
                desktop.open(excelfile);
            } catch (IOException ex) {
                System.out.println("could not execute");
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("could not execute");;
        }
            
    }

}
