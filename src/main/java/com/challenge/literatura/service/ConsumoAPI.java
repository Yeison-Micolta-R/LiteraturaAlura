package com.challenge.literatura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String obtenerDatos(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("Pasé por el primer catch");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("Pasé por el segundo catch");
            throw new RuntimeException(e);
        }
        String json = response.body();
        return json;
    }
}
