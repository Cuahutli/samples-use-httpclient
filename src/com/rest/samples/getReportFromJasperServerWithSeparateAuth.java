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
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import samples.use.httpclient.SamplesUseHttpclient;


/**
 *
 * @author cuahutli
 * genera una conexión con jasperserver y obtine un reporte que almacena en disco realizando el login utilizando un CredentialsProvider
 * y formateando la librería con el metodo replace de la clase String.
 */
public class getReportFromJasperServerWithSeparateAuth {
    public static void main(String[] args) {
        // TODO code application logic here
        
        String host = "10.49.28.3";
        String port = "8081";
        String reportName = "vencimientos";
        String params = "feini=2016-09-30&fefin=2016-09-30";        
        String url = "http://{host}:{port}/jasperserver/rest_v2/reports/Reportes/{reportName}.pdf?{params}";
        url = url.replace("{host}", host);
        url = url.replace("{port}", port);
        url = url.replace("{reportName}", reportName);
        url = url.replace("{params}", params);
        
        try {
            CredentialsProvider cp = new BasicCredentialsProvider();
            cp.setCredentials(AuthScope.ANY, 
                    new UsernamePasswordCredentials("username","password"));
            CloseableHttpClient hc = HttpClientBuilder.create().setDefaultCredentialsProvider(cp).build();
            
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
