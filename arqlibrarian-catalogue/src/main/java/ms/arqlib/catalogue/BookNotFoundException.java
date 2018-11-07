package ms.arqlib.catalogue;

import ms.strings.S;

public class BookNotFoundException extends CatalogueException {
    private Long id;

    public BookNotFoundException(Long id) {
        super(S.$("Book not found with id = %d", id));
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
