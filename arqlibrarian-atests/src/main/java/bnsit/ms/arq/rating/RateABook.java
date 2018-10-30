package bnsit.ms.arq.rating;

import bnsit.ms.arq.library.LibraryFixture;
import org.junit.Before;
import org.junit.Test;

public class RateABook {
    private LibraryFixture fixture = null;

    @Before
    public void setupFixture()
    {
        fixture = new LibraryFixture();
    }

    @Test
    public void shouldRateABook()
    {
        fixture.applicationStarted();
        fixture.hasSampleBooks();
        fixture.hasBook("Ogniem i mieczem", "Henryk Sienkiewicz", "978-83-08-06015-5", "Wydawnictwo Literackie", 2016, "Podręczniki i lektury szkolne");
        long id = fixture.bookByTitle("Ogniem i mieczem");
        final int RATING = 4;

        //when
        fixture.userEnters(String.format("rate %d %d", id, RATING));
        fixture.userEnters("search ogniem i mieczem");

        fixture.then();
        fixture.systemShows(String.format("Ogniem i mieczem rated: %d", RATING));
        fixture.systemShows(String.format("Ogniem i mieczem* rating: %d", RATING));
    }

    @Test
    public void shouldRateABookMultipleTimes()
    {
        fixture.applicationStarted();
        fixture.hasSampleBooks();
        fixture.hasBook("Ogniem i mieczem", "Henryk Sienkiewicz", "978-83-08-06015-5", "Wydawnictwo Literackie", 2016, "Podręczniki i lektury szkolne");
        long id = fixture.bookByTitle("Ogniem i mieczem");
        final int FIRST_RATING = 4;
        fixture.userEnters(String.format("rate %d %d", id, FIRST_RATING));

        //when
        final int SECOND_RATING = 2;
        fixture.userEnters(String.format("rate %d %d", id, SECOND_RATING));
        fixture.userEnters("search ogniem i mieczem");

        fixture.then();
        final double EXPECTED_RATING = 3;
        fixture.systemShows(String.format("Ogniem i mieczem rated* total rating: %1.1f", EXPECTED_RATING));
        fixture.systemShows(String.format("\"Ogniem i mieczem\"* rating: %1.1f", EXPECTED_RATING));
    }

}
