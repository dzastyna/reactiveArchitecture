package ms.rest.service;

import java.util.UUID;

public class ErrorInfo {
    private String errorId;
    private String errorType;
    private String message;

    public ErrorInfo(Throwable ex) {
        this.errorId = UUID.randomUUID().toString();
        this.message = ex.getMessage();
        this.errorType = toTypeString(ex);
    }

    private String toTypeString(Throwable ex) {
        return ex.getClass().getSimpleName().replace("Exception", "");
    }

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
