package borelgabriel.com.br.springwebstore.model.dto;

import java.io.Serial;
import java.io.Serializable;

public class ErrorObjectDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String error;
    private String code;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
