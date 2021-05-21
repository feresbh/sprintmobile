/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Voiture;
import entities.VoitureLouee;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.ServiceVoiture;

/**
 *
 * @author HP
 */
public class ListVoitureLouee extends BaseForm{
    private Resources theme;
    Form current;
    public ListVoitureLouee(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        this.theme = res;

        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("container");
        setTitle("liste des voitures");
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
        RadioButton liste = RadioButton.createToggle("voitures louee", barGroup);
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

        liste.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(liste, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();
            ListVoitureLouee a = new ListVoitureLouee(res);
            a.show();
            refreshTheme();
        });

        //
        TextField search = new TextField("", "search");
        Button btnPdf = new Button("Pdf");
        this.add(btnPdf);
        search.setUIID("TextFieldBlack");
        addStringValue("search", search);
        final Container lbs = this.addGuis(res);
        
        btnPdf.addActionListener((ActionEvent e)->{
            pdf();
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

        /*SpanLabel sp = new SpanLabel();
        sp.setText(ServiceVoiture.getInstance().getAllVoitureslouee().toString());
        add(sp);*/
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }
    public Container addGuis(Resources c) {
        Container lbs = new Container(BoxLayout.y());
        ArrayList<VoitureLouee> list = ServiceVoiture.getInstance().getAllVoitureslouee();
        for (int i = 0; i < list.size(); i++) {
            lbs.add(item(list.get(i),c));
            /*int dispo = list.get(i).getDiponibilite();
            if (dispo == 1) {
                lbs.add(item(list.get(i),c));
            }*/
        }
        return lbs;
    }
    public Container addGuis2(String s,Resources c) {
        Container lbs = new Container(BoxLayout.y());
        ArrayList<VoitureLouee> list = ServiceVoiture.getInstance().getAllVoitureslouee();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSerie().equals(s)) {
                    lbs.add(item(list.get(i),c));
                }
        }
        return lbs;
    }
    private Container item(VoitureLouee v ,Resources c) {
        Container lbs = new Container(BoxLayout.y());

        Label id = new Label("Id : "+Integer.toString(v.getId()));
        Label serie = new Label("Serie : "+v.getSerie());
        Label prix = new Label("Prix : "+Integer.toString(v.getPrix()) + "TND");
        Label dated = new Label("Date Debut : "+v.getDatedebut());
        Label datef = new Label("Date Fin : "+v.getDatefin());
        Button btnSupp = new Button("Supprimer");
        
        btnSupp.addActionListener((e)->{
            System.out.println(v.getVoitureid());
            ServiceVoiture.getInstance().SupprimerVoitureLouee(v.getId(),v.getVoitureid());
        });
        
        
        //marque.addPointerPressedListener(e -> new AddlocationVoitureForm(v,c).show());
        lbs.addAll(id,serie,prix,dated,datef,btnSupp );
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
    
    public void pdf(){
        try {
            String file_name = "C:\\Users\\HP\\Desktop\\WorkShops-CodenameOne-master\\WorkShops-CodenameOne-master\\tablevoitures.pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.open();
            Paragraph para = new Paragraph("Liste des voitures", new Font(FontFamily.HELVETICA, 18, Font.BOLDITALIC, new BaseColor(0, 0, 153)));
            para.setAlignment(ALIGN_CENTER);
            document.add(para);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            document.add(new Paragraph("  "));
            //creation du tableau 
            PdfPTable tablepdf = new PdfPTable(7);
            PdfPCell c1 = new PdfPCell(new Phrase("id", new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, new BaseColor(240, 0, 0))));
            tablepdf.addCell(c1);

            c1 = new PdfPCell(new Phrase("date debut", new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, new BaseColor(240, 0, 0))));
            tablepdf.addCell(c1);

            c1 = new PdfPCell(new Phrase("date fin", new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, new BaseColor(240, 0, 0))));
            tablepdf.addCell(c1);

            c1 = new PdfPCell(new Phrase("seire", new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, new BaseColor(240, 0, 0))));
            tablepdf.addCell(c1);

            c1 = new PdfPCell(new Phrase("id client", new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, new BaseColor(240, 0, 0))));
            tablepdf.addCell(c1);

            c1 = new PdfPCell(new Phrase("id voiture", new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, new BaseColor(240, 0, 0))));
            tablepdf.addCell(c1);
            c1 = new PdfPCell(new Phrase("prix", new Font(FontFamily.HELVETICA, 12, Font.BOLDITALIC, new BaseColor(240, 0, 0))));
            tablepdf.addCell(c1);
            tablepdf.setHeaderRows(1);
            // filling the table
            ArrayList<VoitureLouee> list = ServiceVoiture.getInstance().getAllVoitureslouee();
            for (int i = 0; i < list.size(); i++){
                tablepdf.addCell(String.valueOf(list.get(i).getId()));
                tablepdf.addCell(list.get(i).getDatedebut());
                tablepdf.addCell(list.get(i).getDatefin());
                tablepdf.addCell(list.get(i).getSerie());
                tablepdf.addCell(String.valueOf(list.get(i).getClientid()));
                tablepdf.addCell(String.valueOf(list.get(i).getVoitureid()));
                tablepdf.addCell(String.valueOf(list.get(i).getPrix()));
            }
            //adding the table to the pdf
            document.add(tablepdf);
            document.close();
            File pdffile = new File("C:\\Users\\HP\\Desktop\\WorkShops-CodenameOne-master\\WorkShops-CodenameOne-master\\tablevoitures.pdf");
            Desktop desktop = Desktop.getDesktop();
            desktop.open(pdffile);
            System.err.println("pdf exported succesfully");

            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}
