package borelgabriel.com.br.springwebstore.exceptions;

import java.io.Serial;

public class ResourceAlreadyExistsException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
