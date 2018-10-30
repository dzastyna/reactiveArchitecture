package bnsit.ms.arq.searching;

import bnsit.ms.arq.library.LibraryFixture;
import org.junit.Before;
import org.junit.Test;

import static bnsit.ms.arq.library.LibraryFixture.title;

public class SearchByTitle {

    private LibraryFixture fixture = null;

    @Before
    public void setupFixture()
    {
        fixture = new LibraryFixture();
    }

    @Test
    public void shouldSearchByTitle()
    {
        fixture.applicationStarted();
        fixture.hasSampleBooks();

        fixture.hasBook("Ogniem i mieczem", "Henryk Sienkiewicz", "978-83-08-06015-5", "Wydawnictwo Literackie", 2016, "Podręczniki i lektury szkolne");

        //when
        fixture.userEnters("search Ogniem i mieczem");

        fixture.then();
        fixture.systemShows("Found: 'Ogniem i mieczem'");
        fixture.systemShows(title("Ogniem i mieczem"));
    }
}
