package fmi.uni;

import fmi.uni.explorer.MoviesExplorer;
import fmi.uni.movies.model.Actor;
import fmi.uni.movies.model.Movie;
import fmi.uni.utilities.MoviesInitializer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoviesExplorerTest {

    private static List<Movie> movies;
    private static MoviesExplorer explorer;

    @BeforeClass
    public static void setUp() throws FileNotFoundException, IOException {

        InputStream moviesStream = MoviesInitializer.initMovieStream();

        try (InputStreamReader isr = new InputStreamReader(moviesStream);
             BufferedReader reader = new BufferedReader(isr)) {

            movies = reader.lines().map(Movie::createMovie).collect(Collectors.toList());
        }
        InputStream exploerStream = MoviesInitializer.initMovieStream();
        explorer = new MoviesExplorer(exploerStream);

    }

    @org.junit.Test
    public void testIfExistingDatasetIsLoadedCorrectly() {
        String assertMessage = "Number of movies in dataset doesn't correspond to movies in MoviesExplorer. ";
        final int expected = movies.size();
        final int actual = explorer.getMovies().size();

        assertEquals(assertMessage, expected, actual);
    }

    @org.junit.Test
    public void testCountMoviesReleasedInFor2003() {
        String assertMessage = "Number of movies released in 2003 are not correctly counted. ";
        final int expected = 2;
        final int actual = explorer.countMoviesReleasedIn(2003);

        assertEquals(assertMessage, expected, actual);
    }

    @org.junit.Test
    public void testCountMoviesReleasedInForUnknownYear() {
        String assertMessage = "Number of movies for an unknown year should be 0. ";
        final int unknownYear = 2020;
        final int expected = 0;
        final int actual = explorer.countMoviesReleasedIn(unknownYear);

        assertEquals(assertMessage, expected, actual);
    }

    @org.junit.Test
    public void testFindFirstMovieWithTitle() {
        String assertMessage = "First 'Lord of the Rings movie is not computed correctly. ";
        String title = "Lord of the Rings";
        String expected = "Lord of the Rings, The";
        String actual = explorer.findFirstMovieWithTitle(title).getTitle();

        assertEquals(assertMessage, expected, actual);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testIfFindFirstMovieWithTitleForUnknownMovieThrowsException() {
        String title = "Lord o ringz";
        explorer.findFirstMovieWithTitle(title);
    }

    @org.junit.Test
    public void testGetMoviesSortedByReleaseYear() {
        String assertMessage = "Movies are not sorted correctly. ";
        Collection<Movie> sortedMovies = movies.stream().sorted((m1, m2) -> m1.getYear() - m2.getYear()).collect(Collectors.toList());
        List<Movie> expected = new ArrayList<Movie>(sortedMovies);
        List<Movie> actual = new ArrayList<>(explorer.getMoviesSortedByReleaseYear());

        assertTrue(assertMessage, expected.equals(actual));
    }

    @org.junit.Test
    public void testGetMinYear() {
        String assertMessage = "First year in the statistics is not correct. ";
        final int expected = 1978;
        int actual = explorer.getFirstYear();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testGetAllActors() {
        String assertMessage = "Number of actors does not match the real number from the dataset.";
        final int expected = 43;
        int actual = explorer.getAllActors().size();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testIfGetAllMoviesByReturnsCorrectMoviesForUnknownActor() {
        String assertMessage = "Number of movies for a known actor are not computed correctly.";
        Actor actor = new Actor("Kevin", "Spacey");
        final int expected = 0;
        int actual = explorer.getAllMoviesBy(actor).size();

        assertEquals(assertMessage, expected, actual);
    }

    @Test
    public void testFindYearWithLeastNumberOfReleasedMovies() {
        String assertMessage = "Year with least number of released movies is not computed correctly.";
        List<Integer> expectedYear = Arrays.asList(1996, 2004);
        int actual = explorer.findYearWithLeastNumberOfReleasedMovies();

        assertTrue(assertMessage, expectedYear.contains(actual));

    }


    @Test
    public void testMovieWithGreatestNumberOfActors() {
        String assertMessage = "Movie with greatest number of actors not computet correctly.";
        List<String> expectedMovies = Arrays.asList("Lord of the Rings: The Fellowship of the Ring, The",
                "Lord of the Rings: The Return of the King, The", "Lord of the Rings: The Two Towers, The");
        String actual=explorer.findMovieWithGreatestNumberOfActors().getTitle();
        assertTrue(assertMessage,expectedMovies.contains(actual));

    }
}