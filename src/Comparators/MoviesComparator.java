package Comparators;

import dto.Movies;

import java.util.Comparator;

public class MoviesComparator implements Comparator<Movies> {

    @Override
    public int compare(Movies o1, Movies o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
