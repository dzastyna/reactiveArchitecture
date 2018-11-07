package ms.arqlib.issues.ports;

// INFO interface segregation
public interface BooksService {
    String findDescription(Long bookId);
}
