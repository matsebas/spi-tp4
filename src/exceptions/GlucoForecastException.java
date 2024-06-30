package exceptions;

public class GlucoForecastException extends Exception {
    public GlucoForecastException(String message) {
        super(message);
    }

    public GlucoForecastException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlucoForecastException(Throwable cause) {
        super(cause);
    }
}

