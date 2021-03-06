/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.samples;

import java.awt.Desktop;
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
 * genera una conexión con jasperserver y obtine un reporte que almacena en disco realizando el login desde la misma url
 */
public class getReportFromJasperServer {
    public static void main(String[] args) {
        // TODO code application logic here
        String url = "http://username:password@10.49.28.3:8081/jasperserver/rest_v2/reports/Reportes/vencimientos.pdf?feini=2016-09-30&fefin=2016-09-30";
        try {
            HttpClient hc = HttpClientBuilder.create().build();
            HttpGet getMethod = new HttpGet(url);
            getMethod.addHeader("accept", "application/pdf");
            HttpResponse res = hc.execute(getMethod);
            if (res.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException("Failed : HTTP eror code: " + res.getStatusLine().getStatusCode());
            }
            
            InputStream is = res.getEntity().getContent();
            OutputStream os = new FileOutputStream(new File("vencimientos.pdf"));
            int read = 0;
            byte[] bytes = new byte[2048];
            
            while((read = is.read(bytes)) != -1){
                os.write(bytes, 0, read);
            }
            is.close();
            os.close();
            
            if (Desktop.isDesktopSupported()){
                File pdfFile = new File("vencimientos.pdf");
                Desktop.getDesktop().open(pdfFile);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SamplesUseHttpclient.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
}
