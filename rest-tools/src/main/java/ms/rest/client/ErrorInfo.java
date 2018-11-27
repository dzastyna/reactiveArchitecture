package ms.rest.client;

public class ErrorInfo {
    private String errorId;
    private String errorType;
    private String message;

    ErrorInfo() {}

    public String getMessage() {
        return message;
    }

    public String getErrorId() {
        return errorId;
    }

    public String getErrorType() {
        return errorType;
    }

    @Override
    public String toString() {
        return String.format("errorId: %s, message: %s", errorId, message);
    }
}

