package bnsit.ms.arq.startup;

import bnsit.ms.arq.library.LibraryFixture;
import org.junit.Before;
import org.junit.Test;

public class ApplicationStartup {

    private LibraryFixture fixture;

    @Before
    public void setupFixture() {
        this.fixture = new LibraryFixture();
    }

    @Test
    public void shouldShowWelcomeAtTheBeginning() {
        //given

        //when
        fixture.applicationStarted();
        fixture.hasSampleBooks();


        fixture.then();
        fixture.systemShows("Welcome to the ArqLibrarian");
    }
}
