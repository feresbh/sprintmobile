package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.googlemaps.MapContainer;
import com.codename1.googlemaps.MapContainer.MapObject;
import com.codename1.location.Location;
import com.codename1.location.LocationManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import java.io.IOException;

public class MapCNO extends BaseForm{
    private Resources theme;

    private Form current;
    protected MapObject mapo = new MapObject();
    protected Coord coord = new Coord(40.714353, -74.005973);
  static int x;
    static int y;

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }

        buildForm(theme);
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {

    }

    public void checkGPS(final MapContainer mapc) {
        if (Display.getInstance().getLocationManager().isGPSDetectionSupported()) {
            if (Display.getInstance().getLocationManager().isGPSEnabled()) {
                InfiniteProgress ipr = new InfiniteProgress();
                final Dialog dialog = ipr.showInifiniteBlocking();
                Location loc ;
               //= LocationManager.getLocationManager().getCurrentLocationSync(1);
                dialog.dispose();
               
                   // double lat = loc.getLatitude();
                    //double lng = loc.getLongitude();
                    coord = new Coord(33.892166,9.561555499999997);
                    mapc.setCameraPosition(coord);
                    try {
                        mapc.addMarker(EncodedImage.create("/maps-pin.png"), coord, "marker", "", null);
                    } catch (IOException err) {
                        err.printStackTrace();
                    }
               
            } 
        } else {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog dialog = ip.showInifiniteBlocking();
            Location loc = LocationManager.getLocationManager().getCurrentLocationSync(10000);
            dialog.dispose();
            if (loc != null) {
                double lat = loc.getLatitude();
                double lng = loc.getLongitude();
                coord = new Coord(33.892166,9.561555499999997);
                mapc.setCameraPosition(coord);
                try {
                    mapc.addMarker(EncodedImage.create("/maps-pin.png"), coord, "marker", "", null);
                } catch (IOException err) {
                    err.printStackTrace();
                }
            } else {
                Dialog.show("GPS error", "Your location could not be found, please try going outside for a better GPS signal", "Ok", null);
            }
        }
    }

    public void buildForm(Resources res) {

        final MapContainer mapc = new MapContainer();
        mapc.setTensileDragEnabled(false);
        
        checkGPS(mapc);

        Form form = new Form("MapCNO");
        form.setLayout(new BorderLayout());

        mapc.addTapListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                mapc.clearMapLayers();
                try {
                    coord = new Coord(mapc.getCoordAtPosition(evt.getX(), evt.getY()));
                    mapo = mapc.addMarker(EncodedImage.create("/maps-pin.png"), coord, "", "", (evt1) -> {
                        coord = mapc.getCoordAtPosition(evt.getX(), evt.getY());
                        x=evt.getX();
                        y=evt.getY();
                        System.out.println("selected lat"+x+"selectedlon"+y);
                    });
                } catch (IOException err) {
                    err.printStackTrace();
                }
                form.revalidate();
            }
        });

        TableLayout tl;
        int spanButton = 2;
        if (Display.getInstance().isTablet()) {
            tl = new TableLayout(7, 2);
        } else {
            tl = new TableLayout(14, 1);
            spanButton = 1;
        }
        tl.setGrowHorizontally(true);
        form.setLayout(tl);

        //TextField info = new TextField("", "", 20, TextArea.ANY);

        Button submit = new Button("Valider");

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                
                 Dialog dig= new Dialog("Choisir Succeeded");
          
          dig.show("Les coordonnes sont: ","lat"+x+"lon"+y,"","OK");
              
              dig.dispose();
              new AjoutProductForm(res,x,y).show();
            }
                
        });

        TableLayout.Constraint cn = tl.createConstraint();
        cn.setHorizontalSpan(spanButton);
        cn.setHorizontalAlign(Component.RIGHT);
        form.add("Choisir un lieu :").
                add(cn, submit).add(mapc);

        form.addCommand(new Command("Rony Rodriguez") {
            public void actionPerformed(ActionEvent ev) {
            }
        });

       // info.getAllStyles().setBgTransparency(0xFF);
        //info.getComponentForm().repaint();

        form.show();
    }

}
