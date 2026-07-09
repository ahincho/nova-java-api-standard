package pe.edu.nova.java.libs.api.standard.http;

/**
 * Categorías de códigos de estado HTTP.
 * Clasifica los códigos HTTP en cinco grupos estándar.
 */
public enum HttpCategory {

    /** Respuestas informativas (1xx). */
    INFORMATIONAL("1xx"),

    /** Respuestas exitosas (2xx). */
    SUCCESS("2xx"),

    /** Respuestas de redirección (3xx). */
    REDIRECTION("3xx"),

    /** Errores del cliente (4xx). */
    CLIENT_ERROR("4xx"),

    /** Errores del servidor (5xx). */
    SERVER_ERROR("5xx");

    /** Nombre legible de la categoría HTTP. */
    private final String displayName;

    /**
     * Constructor del enum con nombre legible.
     *
     * @param displayName nombre legible de la categoría
     */
    HttpCategory(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retorna el nombre legible de la categoría HTTP.
     *
     * @return nombre legible (e.g., "2xx")
     */
    public String getDisplayName() {
        return displayName;
    }
}
