package com.tubes.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphMaker;
import org.apache.jena.query.*;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class OutputController {
    @FXML
    private Text txtHasil;

    public void setText(String model){
        String sparqlendpoint = "http://localhost:3030/"+ model +"/query";
        String perintahSPARQL = "select ?s ?p ?o WHERE { ?s ?p ?o }";

        Query query = QueryFactory.create(perintahSPARQL);
        QueryExecution qe = QueryExecutionFactory.sparqlService(sparqlendpoint,query);
        ResultSet hasil = qe.execSelect();
        String oo = ResultSetFormatter.asText(hasil);
        txtHasil.setText(oo);
    }

    public void setStatement(ObservableList<TextField> listPrefix, String s, String p, String o, String model,
                             String statement){
        String prefix = "";
        for(TextField i : listPrefix){
            prefix = prefix + "prefix " + i.getText() + " ";
        }
        String sttmt = "";
        switch (statement){
            case "add":
                sttmt = "insert data";
                break;
            case "delete data":
                sttmt = "delete data";
                break;
            case "delete where":
                sttmt = "delete where";
                break;
        }
        String perintahinsertSPARQL = prefix + sttmt + " { " + s + " " + p + " " + o + " }";
        System.out.println(perintahinsertSPARQL);
        String sparqlupdateendpoint = "http://localhost:3030/"+ model +"/update";

        UpdateRequest req = UpdateFactory.create(perintahinsertSPARQL);
        UpdateProcessor reqexnya = UpdateExecutionFactory.createRemote(req,sparqlupdateendpoint);
        reqexnya.execute();
    }
}
