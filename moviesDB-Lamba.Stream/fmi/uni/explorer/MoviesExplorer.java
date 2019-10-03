package fmi.uni.explorer;

import fmi.uni.movies.model.Actor;
import fmi.uni.movies.model.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class MoviesExplorer {

  private List<Movie> movies;

    public MoviesExplorer(InputStream dataInput) {
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(dataInput))){
                    movies=reader.lines().map(Movie::createMovie).collect(Collectors.toList());

                    System.out.println("Movies loaded:: "+movies.size());
        }catch(IOException e){
            throw new IllegalArgumentException("Dataset could not be loaded");
        }

    }

    public Collection<Movie> getMovies() {
        return Collections.unmodifiableList(movies);
    }

    public int countMoviesReleasedIn(int year){
       return (int)movies.stream().filter(m->m.getYear()==year).count();
    }
    public Movie findFirstMovieWithTitle(String title){
        return movies.stream().filter(m->m.getTitle().contains(title)).findFirst().orElseThrow(IllegalArgumentException::new);
    }
    public Collection<Actor> getAllActors(){
        return movies.stream().flatMap(m->m.getActors().stream()).collect(Collectors.toSet());
    }
    public int getFirstYear(){
        return movies.stream().mapToInt(Movie::getYear).min().getAsInt();
    }
    public Collection<Movie> getAllMoviesBy(Actor actor){
        return movies.stream().filter(m->m.getActors().stream().anyMatch(a->a.equals(actor))).collect(Collectors.toList());
    }
    public Collection<Movie> getMoviesSortedByReleaseYear(){
        return movies.stream().sorted(Comparator.comparing(Movie::getYear)).collect(Collectors.toList());
    }
    public int findYearWithLeastNumberOfReleasedMovies(){
       Map <Integer,Long> result=movies.stream().collect(Collectors.groupingBy(Movie::getYear,Collectors.counting()));

       return result.entrySet().stream().min(Comparator.comparing(x->x.getValue())).get().getKey();
    }
    public Movie findMovieWithGreatestNumberOfActors(){
        return movies.stream().max(Comparator.comparing(x->x.getActors().size())).get();
    }


}