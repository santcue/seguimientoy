package cue.edu.co.Exceptions;

public class ToyStoreException extends Exception {
    public static class ClienteNotFoundException extends Exception {
        public ClienteNotFoundException(String message) {
            super(message);
        }
    }

    public static class EmployeesNotFoundException extends Exception {
        public EmployeesNotFoundException(String message) {
            super(message);
        }
    }

    public static class ToyNotFoundException extends Exception {
        public ToyNotFoundException(String message) {
            super(message);
        }
    }

    public static class SaleNotFoundException extends Exception {
        public SaleNotFoundException(String message) {
            super(message);
        }
    }
}
