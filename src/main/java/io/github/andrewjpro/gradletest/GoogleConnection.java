package io.github.andrewjpro.gradletest;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.discovery.Discovery;
import com.google.api.services.discovery.model.DirectoryList;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class GoogleConnection {

    public static void main(String[] args) throws GeneralSecurityException, IOException {

        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        Discovery discovery = new Discovery.Builder(transport, jsonFactory, null).setApplicationName("Test Application").build();

        DirectoryList apiList = discovery.apis().list().execute();

        List<DirectoryList.Items> items = apiList.getItems();
        for(DirectoryList.Items item : items) {
            System.out.println(item.getName() + " " + item.getVersion()+ " - " + item.getDescription());
        }
    }

}
