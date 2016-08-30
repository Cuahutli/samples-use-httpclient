/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.samples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import samples.use.httpclient.SamplesUseHttpclient;

/**
 *
 * @author cuahutli
 * Obtiene desde una api_rest una imagen y la almacena en un archivo en disco con extensión .png
 */
public class getImage {
     public static void main(String[] args) {
        // TODO code application logic here
        String url = "https://api.adorable.io/avatars/eyes1";
        try {
            HttpClient hc = HttpClientBuilder.create().build();
            HttpGet getMethod = new HttpGet(url);
            getMethod.addHeader("accept", "application/png");
            HttpResponse res = hc.execute(getMethod);
            if (res.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException("Failed : HTTP eror code: " + res.getStatusLine().getStatusCode());
            }
            
            InputStream is = res.getEntity().getContent();
            
            OutputStream os = new FileOutputStream(new File("img.png"));
            int read = 0;
            byte[] bytes = new byte[2048];
            while((read = is.read(bytes)) != -1){
                os.write(bytes, 0, read);
            }
            is.close();
            os.close();
        } catch (IOException ex) {
            Logger.getLogger(SamplesUseHttpclient.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
}
