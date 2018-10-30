package bnsit.ms.arq.library;

import org.junit.Before;
import org.junit.Test;

import static bnsit.ms.arq.library.LibraryFixture.author;
import static bnsit.ms.arq.library.LibraryFixture.publisher;
import static bnsit.ms.arq.library.LibraryFixture.title;

public class AddABook {
    private LibraryFixture fixture;

    @Before
    public void setupFixture()
    {
        this.fixture = new LibraryFixture();
    }

    @Test
    public void shouldAddBook()
    {
        fixture.applicationStarted();
        fixture.hasSampleBooks();

        fixture.userEnters("add");
        fixture.userEnters("Ogniem i mieczem");
        fixture.userEnters("Henryk Sienkiewicz");
        fixture.userEnters("978-83-08-06015-5");
        fixture.userEnters("Wydawnictwo Literackie");
        fixture.userEnters("2016");
        fixture.userEnters("Podręczniki i lektury szkolne");

        fixture.userEnters("search");

        fixture.then();
        fixture.systemShows("Ogniem i mieczem*Henryk Sienkiewicz*Podręczniki i lektury szkolne");
    }
}
