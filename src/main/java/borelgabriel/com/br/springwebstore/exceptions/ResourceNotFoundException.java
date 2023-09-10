package borelgabriel.com.br.springwebstore.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
