package bnsit.ms.arq.library;

import java.util.Date;

public class SingleRating {

    private int rating;

    private Date when;

    public SingleRating(int rating)
    {
        this.rating = rating;
        this.when = new Date();
    }

    public int getRating() {
        return rating;
    }
}
