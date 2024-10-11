package unicap.si.microservicos.api.buscaCep.exception;


public class CepNotFoundException extends Exception {

    public CepNotFoundException(String message) {
        super(message);
    }
}
