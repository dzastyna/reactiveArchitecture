package ms.arqlib.catalogue;

public class BookNotFoundException extends CatalogueException {

    public BookNotFoundException(String message) {
        super(message);
    }
}
