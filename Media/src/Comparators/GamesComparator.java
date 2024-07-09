package Comparators;

import dto.Games;

import java.util.Comparator;

public class GamesComparator implements Comparator<Games> {
    @Override
    public int compare(Games o1, Games o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
