/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.db.Database;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;

import com.codename1.ui.events.ActionListener;
import entities.Order;
import entities.OrderDetails;
import entities.Panier;
import entities.Product;
import utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author MY HP
 */
public class ServiceProduit {

    //singleton
    public Database db;
    public static ServiceProduit instance = null;

    public static boolean resultOK = true;
    ArrayList resultpanier = new ArrayList();
   
    public static ArrayList<Panier> resultpa = new ArrayList<>();
    //Initialisation connection request
    private ConnectionRequest req;
    

    public static ServiceProduit getInstance() {
        if (instance == null) {
            instance = new ServiceProduit();
        }
        return instance;
    }

    public ServiceProduit() {

        req = new ConnectionRequest();

    }

    //Ajout
    public void ajouterProduit(Product product) {

        String url = Statics.BASE_URL + "/AddProdJSON/new?name=" + product.getName() + "&image=" + product.getImage() + "&subtitle=" + product.getSubtitle() + "&prix=" + product.getPrix() + "&description=" + product.getDescription() + "&lat=" + product.getLat() + "&lon=" + product.getLon();

        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("Data ==" + str);

        });

        NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ

    }

    //Affichage
    public ArrayList<Product> affichageProduits() {
        ArrayList<Product> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/AllProdsJSON";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapProduits = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMap = (List<Map<String, Object>>) mapProduits.get("root");

                    for (Map<String, Object> obj : listOfMap) {
                        Product pr = new Product();

                        float id = Float.parseFloat(obj.get("id").toString());
                        pr.setId((int) Float.parseFloat(obj.get("id").toString()));
                        pr.setName(obj.get("name").toString());

                        String image = (String) obj.get("image");
                        pr.setImage(image);
                        pr.setSubtitle(obj.get("subtitle").toString());
                        pr.setDescription(obj.get("description").toString());
                        pr.setPrix((int) Float.parseFloat(obj.get("prix").toString()));
                        pr.setLat((int) Float.parseFloat(obj.get("lat").toString()));
                        pr.setLon((int) Float.parseFloat(obj.get("lon").toString()));

                        //InsertData into ArrayList result
                        result.add(pr);

                    }

                } catch (Exception ex) {
                    System.out.println("Error houni : " + ex.getMessage());
                }
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ
        return result;

    }

    //Detail Produit
    public Product DetailProduit(int id, Product product) {

        String url = Statics.BASE_URL + " /AllProdIdJSON/" + id;
        req.setUrl(url);
        String str = new String(req.getResponseData());

        req.addResponseListener(((evt) -> {

            JSONParser jsonp = new JSONParser();
            try {
                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));

                product.setName(obj.get("name").toString());
                String image = (String) obj.get("image");
                product.setImage(image);
                product.setSubtitle(obj.get("subtitle").toString());
                product.setDescription(obj.get("description").toString());
                product.setPrix((int) Float.parseFloat(obj.get("prix").toString()));
                product.setLat((int) Float.parseFloat(obj.get("lat").toString()));
                product.setLon((int) Float.parseFloat(obj.get("lon").toString()));

            } catch (IOException ex) {
                System.out.println("error related to SQL: " + ex.getMessage());

            }

            System.out.println("data =======" + str);

        }));
        NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ
        return product;
    }

    //DELETE
    public boolean deleteProduct(int id) {
        String url = Statics.BASE_URL + "/deleteProdJSON/" + id;

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);

            }

        });

        NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ
        return resultOK;
    }

    //UPDATE
    public boolean modifierProduct(Product product) {

        String url = Statics.BASE_URL + "/updateProdJSON/" + product.getId() + "?name=" + product.getName() + "&image=" + product.getImage() + "&subtitle=" + product.getSubtitle() + "&prix=" + product.getPrix() + "&description=" + product.getDescription() + "&lat=" + product.getLat() + "&lon=" + product.getLon();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                resultOK = req.getResponseCode() == 200; //code 200 si c bon
                req.removeResponseListener(this);
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    //ADD_TO_CART
    public void AjouterProductPanier(int id,int qt,double prix ) {
 Panier mm=new Panier();
       // String url = Statics.BASE_URL + "/cartJSON/add/" + id;
        //req.setUrl(url);

        //req.addResponseListener((e) -> {
            mm.setQuant(qt);
            mm.setId(id);
            //mm.setTotale(qt*prix
            double ol=qt*prix;
           // mm.totale=mm.incremanterTotal(ol);
            double totalee= mm.incremanterTotal(ol);
            mm.setTotale(totalee);
            
        System.out.println("tot    !"+totalee);
          resultpa.add(mm);
          
            System.out.println("mm "+mm);
          
           // String str = new String(req.getResponseData());
            //System.out.println("Data Ajouté panier ==" + str);
            //resultpanier.add(str);
            //System.out.println("Panier ==" + resultpanier);

      //  });

       // NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ

    }

    //AffichagePanier
    /*   public ArrayList<OrderDetails> affichagePanierProduits() {
        ArrayList<OrderDetails> resultp = new ArrayList<>();

        String url = Statics.BASE_URL + "/mon-panierJSONN";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapP = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    System.out.println("1");
                    List<Map<String, Object>> listtOfMaps = (List<Map<String, Object>>) mapP.get("root");
                    System.out.println("2");

                    for (Map<String, Object> objj : listtOfMaps) {
                        OrderDetails pr = new OrderDetails();
                        System.out.println("3");

                        float id = Float.parseFloat(objj.get("id").toString());
                        System.out.println("4");

                        pr.setId((int) Float.parseFloat(objj.get("id").toString()));
                        System.out.println("4");

                        pr.setQuantity(((int) Float.parseFloat(objj.get("quantity").toString())));
                        System.out.println("5");

                        pr.setIdProduct(((int) Float.parseFloat(objj.get("idProduct").toString())));
                        System.out.println("4");

                        pr.setIdOrder(((int) Float.parseFloat(objj.get("idOrder").toString())));
                                                System.out.println("4");

                        pr.setTotal((int) Float.parseFloat(objj.get("total").toString()));
                        System.out.println("4");
                       
                        // product.set((int) Float.parseFloat(obj.get("lon").toString()));
                        //pr.setQuantity((int)obj.get("quantity"));
                        //InsertData into ArrayList result
                        resultp.add(pr);
                        System.out.println("pp" + resultp);
                        NetworkManager.getInstance().addToQueueAndWait(req);
                    }

                } catch (Exception ex) {
                    System.out.println("Error panier affichage : " + ex.getMessage());
                }
            }

        });

        return resultp;

    }
     */
    //Affichage
    public ArrayList<Panier> affichagePanier() {
        ArrayList<Panier> result = new ArrayList<>();

        String url = Statics.BASE_URL + "/mon-panierJSONN";
        req.setUrl(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

              //  JSONParser jsonp;
                //jsonp = new JSONParser();
/*
                try {
                    Map<String, Object> mapPanier = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));

                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapPanier.get("root");

                    for (Map<String, Object> obj : listOfMaps) {
                        Panier pr = new Panier();
                        Product p = new Product();

                         // float id = Float.parseFloat(obj.get("id").toString());
                        /* pr.setId((int) Float.parseFloat(obj.get("id").toString()));
                         */
                       // pr.setId((int) Float.parseFloat(obj.get("id").toString()));
                       // p.setName((String) (obj.get("name")));
                     //   p.setImage((String) (obj.get("image")));
                       // p.setSubtitle(obj.get("subtitle").toString());
                        //p.setPrix((int) Float.parseFloat(obj.get("prix").toString()));
                        //p.setDescription(obj.get("description").toString());
                        //  p.setIdCategory((int) Float.parseFloat(obj.get("idcategory").toString()));
                        //p.setLat((int) Float.parseFloat(obj.get("lat").toString()));
                        //p.setLon((int) Float.parseFloat(obj.get("lon").toString()));

                       // p.setSubtitle(subtitle);
                        //pr.setProduct((int) id);
                       // pr.setId()
                        //pr.setQuant((int) Float.parseFloat(obj.get("quantity").toString()));

                        // pr.setProduct(p.setId((int) Float.parseFloat(obj.get("id").toString())));
                        /* pr.setName(obj.get("name").toString());

                        String image = (String) obj.get("image");
                        pr.setImage(image);
                        pr.setSubtitle(obj.get("subtitle").toString());
                        pr.setDescription(obj.get("description").toString());
                        pr.setPrix((int) Float.parseFloat(obj.get("prix").toString()));
                        pr.setLat((int) Float.parseFloat(obj.get("lat").toString()));
                        pr.setLon((int) Float.parseFloat(obj.get("lon").toString()));
                         
                        //InsertData into ArrayList result
                        result.add(pr);

                    }

                } catch (Exception ex) {
                    System.out.println("Error houni : " + ex.getMessage());
                }*/
            }

        });
      //  NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ
        return resultpa;

    }

    //DELETEFROMCART
    public boolean deleteCartProduct(int id) {
        String url = Statics.BASE_URL + "/cart/deleteJSON/" + id;

        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);

            }

        });

        NetworkManager.getInstance().addToQueueAndWait(req);   //Excecution REQ
        return resultOK;
    }

    //AffichagePanier
    public ArrayList affichagePanierProduitsv2() {

        resultpanier.toArray();
        return resultpanier;

    }
    
    public void viderPanier(){
        
         resultpa.clear();
         
         System.out.println("cleared");
         //return resultpa;
        
    }
    
    public void Orderr(){
        
        try {
            db= Database.openOrCreate("databasepi");
            System.out.println("Connected to DB malek");
           // db.execute("create table if not exists commande(id INT,idUser INT, description TEXT, total DOUBLE,status TEXT) ");
            db.execute("INSERT INTO`commande`(`idUser`,`description`,`total`) VALUES (5,'777',12.02)");
            System.out.println("Order succeeded");

        } catch (IOException ex) {
            System.out.println("Erreur de la bd: "+ex.getMessage());        }
    }
    
    
    
   /* public int Totalprix(){
        
        return 
    }*/

}
