package ms.arqlib.borrowing;

import ms.arqlib.library.LibraryFixture;
import org.junit.Before;
import org.junit.Test;

public class IssueABook {

    private LibraryFixture fixture = null;

    @Before
    public void setupFixture()
    {
        fixture = new LibraryFixture();
    }

    @Test
    public void shouldIssueABook()
    {
        fixture.applicationStarted();
        fixture.hasSampleBooks();
        fixture.hasSampleUsers();

        fixture.hasBook("Ogniem i mieczem", "Henryk Sienkiewicz", "978-83-08-06015-5", "Wydawnictwo Literackie", 2016, "Podręczniki i lektury szkolne");
        long id = fixture.bookIdByTitle("Ogniem i mieczem");
        long userId = 1;

        //when
        fixture.userEnters(String.format("issue %d %d", userId, id));
        fixture.userEnters(String.format("status %d", id));

        fixture.then();
        fixture.systemShows("[issued]*Ogniem i mieczem");
    }

}
