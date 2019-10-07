package fmi.meetup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fmi.meetup.recources.Event;
import fmi.meetup.recources.Photo;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


public class MeetupClient {

    private static final String API_URL = "https://api.meetup.com";

    private HttpClient client;
    private String apiKey;

    public MeetupClient(HttpClient client, String apiKey) {
             this.client=client;
             this.apiKey=apiKey;
    }

    public List<Event> getEventsNearby() {
       String URL=API_URL + "/find/events?key=" + apiKey;
        HttpRequest request= HttpRequest.newBuilder().uri(URI.create(URL)).build();

        try {
            String jsonResponse = client.send(request, BodyHandlers.ofString()).body();
            Gson gson=new Gson();
            return gson.fromJson(jsonResponse,new TypeToken<List<Event>>() {
            }.getType());


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public List<Event> getEventsWithVenueName(String venueName) {
        return getEventsNearby().stream().filter(e->e.getVenue()!=null && e.getVenue().getName().equalsIgnoreCase(venueName)).collect(Collectors.toList());
    }


    public List<Event> getEventsWithKeywords(Collection<String> keywords) {
        return getEventsNearby().stream().filter(e -> e.getDescription() != null
                && keywords.stream().map(String::toLowerCase).allMatch(e.getDescription().toLowerCase()::contains))
                .collect(Collectors.toList());
    }
    public Event getEventWithMaxDuration() {
       return getEventsNearby().stream().max(Comparator.comparing(Event::getDuration)).orElse(null);
    }
    public Event getEvent(String urlname, String id) {
        String URL = API_URL + "/" + urlname + "/events/" + id + "?key=" + apiKey;
     HttpRequest request= HttpRequest.newBuilder().uri(URI.create(URL)).build();

        try {
            HttpResponse<String> response= client.send(request,BodyHandlers.ofString());
            if(response.statusCode()==404){
                return null;
            }

            String jsonResponce=response.body();
            Gson gson=new Gson();
            return gson.fromJson(jsonResponce,Event.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<Photo> getAlbumPhotos(String urlname, String id) {
        String URL = API_URL + "/" + urlname + "/events/" + id + "?key=" + apiKey;
      HttpRequest request= HttpRequest.newBuilder().uri(URI.create(URL)).build();
        try{
            HttpResponse<String> responce=client.send(request,BodyHandlers.ofString());
            if(responce.statusCode()==404){
                return null;
            }
            String jsonResponce=responce.body();
            Gson gson=new Gson();
            return gson.fromJson(jsonResponce,new TypeToken<List<Photo>>(){}.getType());

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    public void downloadPhotoAlbum(String urlname, String albumId, Path target) throws IOException {
        List<Photo> photos=getAlbumPhotos(urlname,albumId);
        List<CompletableFuture<HttpResponse<Path>>> futures=new ArrayList<>();

        for(Photo photo:photos){
            HttpRequest request= HttpRequest.newBuilder().uri(URI.create(photo.getPhoto_link())).build();
            Path path= Paths.get(target.toString(),albumId,photo.getId() + ".jpeg");
            File photoFile=path.toFile();
            photoFile.getParentFile().mkdirs();
            futures.add(client.sendAsync(request,BodyHandlers.ofFile(path)));

        }

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

    }
}