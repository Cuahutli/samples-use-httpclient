/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.samples;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
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
 * clase que obtienen un json desde una api_rest y  lo procesa utilizando json
 */
public class GetJSON {
   public static void main(String[] args) {
        // TODO code application logic here
//        String url = "https://api.adorable.io/avatars/list";
        String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";
        try {
            HttpClient hc = HttpClientBuilder.create().build();
            HttpGet getMethod = new HttpGet(url);
            getMethod.addHeader("accept", "application/json");
            HttpResponse res = hc.execute(getMethod);
            if (res.getStatusLine().getStatusCode() != 200){
                throw new RuntimeException("Failed : HTTP eror code: " + res.getStatusLine().getStatusCode());
            }
            
            InputStream is = res.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            JsonParser parser = new JsonParser();
            JsonElement element= parser.parse(br);
            if (element.isJsonObject()){
                JsonObject jsonObject = element.getAsJsonObject();
                Set<Map.Entry<String,JsonElement>> jsonEntrySet =jsonObject.entrySet();
                for(Map.Entry<String,JsonElement> entry: jsonEntrySet){
                    if (entry.getValue().isJsonArray() && entry.getValue().getAsJsonArray().size()>0){
                        JsonArray jsonArray = entry.getValue().getAsJsonArray();
                        Set<Map.Entry<String,JsonElement>> internalJsonEntrySet = jsonArray.get(0).getAsJsonObject().entrySet();
                        for(Map.Entry<String,JsonElement> entrie: internalJsonEntrySet){
                            System.out.println("--->   " + entrie.getKey() + " --> " + entrie.getValue());

                        }

                    }else{
                        System.out.println(entry.getKey() + " --> " + entry.getValue());
                    }
                }               
            }
            
            
            String output;
            while((output = br.readLine()) != null ){
                System.out.println(output);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SamplesUseHttpclient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

