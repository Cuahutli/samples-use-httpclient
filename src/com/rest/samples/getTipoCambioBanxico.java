/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import samples.use.httpclient.SamplesUseHttpclient;


/**
 *
 * @author cuahutli
 * clase para obtener el tipo de cambio publicado en banxico utilizando la libreria
 * Jsoup para parsear el HTML
 */
public class getTipoCambioBanxico {
    public static void main(String[] args) {
     String url = "http://www.banxico.org.mx/tipcamb/llenarTiposCambioAction.do?idioma=sp";
        try {
            HttpClient hc = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent","Mozilla/5.0" );
            request.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            
            HttpResponse res = hc.execute(request);
            if (res.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException("Failed : HTTP eror code: " + res.getStatusLine().getStatusCode());
            }
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent())); 
            StringBuffer result = new StringBuffer();
            String line = "";
            while((line = rd.readLine()) != null){
                result.append(line);
            }
            Document doc = Jsoup.parse(result.toString());
            Element tipoCambioFix = doc.getElementById("FIX_DATO");
            System.out.println(tipoCambioFix.text());
            
        } catch (IOException ex) {
            Logger.getLogger(SamplesUseHttpclient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
