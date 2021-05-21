/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shelfie.daos;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import entities.Order;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Amal
 */
public class BookDAO {
        
    private ConnectionRequest connectionRequest;
    public static Form listOfBooks;
    public void addBook(Order order){
        connectionRequest=new ConnectionRequest(){
            @Override
            protected void postResponse() {
            Dialog d = new Dialog("Add Order");
           
            }
        };
        connectionRequest.setUrl("http://localhost/insert.php?idUser=" + order.getIdUser() + "&description=" + order.getDescription()+"&total="+order.getTotal());
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }
   
   
   
    
}
