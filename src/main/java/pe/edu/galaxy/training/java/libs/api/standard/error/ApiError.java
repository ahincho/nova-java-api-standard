package pe.edu.galaxy.training.java.libs.api.standard.error;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Estructura inmutable que representa un error individual dentro de una respuesta de API.
 * Contiene código, mensaje, campo afectado (opcional) y detalles adicionales (opcional).
 *
 * @param code    código identificador del error (obligatorio)
 * @param message mensaje descriptivo del error (obligatorio)
 * @param field   campo afectado (opcional, puede ser null)
 * @param details detalles adicionales del error (copia defensiva inmutable)
 */
public record ApiError(
        String code,
        String message,
        String field,
        Map<String, Object> details
) {

    /**
     * Constructor compacto con validación y copia defensiva.
     *
     * @throws IllegalArgumentException si code es nulo o en blanco
     * @throws IllegalArgumentException si message es nulo o en blanco
     */
    public ApiError {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code es obligatorio");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("message es obligatorio");
        }
        details = details == null
                ? Map.of()
                : Collections.unmodifiableMap(new LinkedHashMap<>(details));
    }

    /**
     * Crea un ApiError con código y mensaje.
     *
     * @param code    código del error
     * @param message mensaje del error
     * @return nueva instancia de ApiError
     * @throws IllegalArgumentException si code o message son nulos o en blanco
     */
    public static ApiError of(String code, String message) {
        return new ApiError(code, message, null, null);
    }

    /**
     * Crea un ApiError con código, mensaje y campo afectado.
     *
     * @param code    código del error
     * @param message mensaje del error
     * @param field   campo afectado
     * @return nueva instancia de ApiError
     * @throws IllegalArgumentException si code o message son nulos o en blanco
     */
    public static ApiError of(String code, String message, String field) {
        return new ApiError(code, message, field, null);
    }

    /**
     * Crea un ApiError de validación con código "VALIDATION_ERROR".
     *
     * @param field   campo que falló la validación
     * @param message mensaje descriptivo
     * @return nueva instancia de ApiError
     * @throws IllegalArgumentException si message es nulo o en blanco
     */
    public static ApiError validationError(String field, String message) {
        return new ApiError("VALIDATION_ERROR", message, field, null);
    }

    /**
     * Retorna un nuevo Builder para construir instancias de ApiError.
     *
     * @return nuevo Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder fluido para construir instancias de ApiError.
     */
    public static final class Builder {

        /** Código identificador del error. */
        private String code;

        /** Mensaje descriptivo del error. */
        private String message;

        /** Campo afectado por el error. */
        private String field;

        /** Detalles adicionales del error. */
        private final Map<String, Object> details = new LinkedHashMap<>();

        private Builder() {
        }

        /**
         * Establece el código del error.
         *
         * @param code código identificador del error
         * @return este builder
         */
        public Builder code(String code) {
            this.code = code;
            return this;
        }

        /**
         * Establece el mensaje del error.
         *
         * @param message mensaje descriptivo del error
         * @return este builder
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Establece el campo afectado.
         *
         * @param field campo afectado por el error
         * @return este builder
         */
        public Builder field(String field) {
            this.field = field;
            return this;
        }

        /**
         * Agrega un detalle adicional al error.
         *
         * @param key   clave del detalle
         * @param value valor del detalle
         * @return este builder
         */
        public Builder detail(String key, Object value) {
            this.details.put(key, value);
            return this;
        }

        /**
         * Construye la instancia inmutable de ApiError.
         *
         * @return nueva instancia de ApiError
         * @throws IllegalArgumentException si code o message son nulos o en blanco
         */
        public ApiError build() {
            return new ApiError(code, message, field, details.isEmpty() ? null : details);
        }
    }
}
