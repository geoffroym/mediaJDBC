import dto.Score;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
    @Override
    public int compare(Score o1, Score o2) {
        if(o1.score()!=o2.score()){
            return o2.score() - o1.score();
        }
        return o2.time().compareTo(o1.time());
    }
}
