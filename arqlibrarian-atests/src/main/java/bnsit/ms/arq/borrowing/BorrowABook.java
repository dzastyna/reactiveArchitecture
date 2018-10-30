package bnsit.ms.arq.borrowing;

import bnsit.ms.arq.library.LibraryFixture;
import org.junit.Before;
import org.junit.Test;

public class BorrowABook {

    private LibraryFixture fixture = null;

    @Before
    public void setupFixture()
    {
        fixture = new LibraryFixture();
    }

    @Test
    public void shouldBorrowABook()
    {
        fixture.applicationStarted();
        fixture.hasSampleBooks();
        fixture.hasSampleUsers();

        fixture.hasBook("Ogniem i mieczem", "Henryk Sienkiewicz", "978-83-08-06015-5", "Wydawnictwo Literackie", 2016, "Podręczniki i lektury szkolne");
        long id = fixture.bookIdByTitle("Ogniem i mieczem");

        //when
        fixture.userEnters(String.format("borrow %d", id));
        fixture.userEnters(String.format("status %d", id));

        fixture.then();
        fixture.systemShows("[borrowed]*Ogniem i mieczem");
    }

}
