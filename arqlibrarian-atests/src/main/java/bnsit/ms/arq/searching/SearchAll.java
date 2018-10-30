package bnsit.ms.arq.searching;

import bnsit.ms.arq.library.LibraryFixture;
import org.junit.Before;
import org.junit.Test;

import static bnsit.ms.arq.library.LibraryFixture.author;
import static bnsit.ms.arq.library.LibraryFixture.title;

public class SearchAll {

    private LibraryFixture fixture = null;

    @Before
    public void setupFixture()
    {
        fixture = new LibraryFixture();
    }

    @Test
    public void shouldSearchAllBooks()
    {
        //given
        fixture.applicationStarted();
        fixture.hasSampleBooks();

        //when
        fixture.userEnters("search");

        //then
        fixture.then();
        fixture.systemShows(title("Karolcia"));
        fixture.systemShows(author("Maria Kruger"));
        fixture.systemShows(title("Renesans"));
        fixture.systemShows(author("Jerzy Konieczny"));
        fixture.systemShowsAtLeastLines(fixture.startBooksCount());
    }
}
