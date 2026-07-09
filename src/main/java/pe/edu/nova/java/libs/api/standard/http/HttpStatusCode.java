package pe.edu.nova.java.libs.api.standard.http;

import java.util.Optional;

/**
 * Enumeración de códigos de estado HTTP estándar con categoría y descripción.
 * Permite validar y clasificar códigos de estado sin depender de Spring.
 */
public enum HttpStatusCode {

    /** Respuesta exitosa estándar (200). */
    OK(200, "OK", HttpCategory.SUCCESS),

    /** Recurso creado exitosamente (201). */
    CREATED(201, "Created", HttpCategory.SUCCESS),

    /** Sin contenido en la respuesta (204). */
    NO_CONTENT(204, "No Content", HttpCategory.SUCCESS),

    /** Solicitud mal formada o inválida (400). */
    BAD_REQUEST(400, "Bad Request", HttpCategory.CLIENT_ERROR),

    /** Autenticación requerida o inválida (401). */
    UNAUTHORIZED(401, "Unauthorized", HttpCategory.CLIENT_ERROR),

    /** Acceso denegado al recurso (403). */
    FORBIDDEN(403, "Forbidden", HttpCategory.CLIENT_ERROR),

    /** Recurso no encontrado (404). */
    NOT_FOUND(404, "Not Found", HttpCategory.CLIENT_ERROR),

    /** Conflicto con el estado actual del recurso (409). */
    CONFLICT(409, "Conflict", HttpCategory.CLIENT_ERROR),

    /** Entidad no procesable por errores de validación (422). */
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity", HttpCategory.CLIENT_ERROR),

    /** Demasiadas solicitudes — rate limit excedido (429). */
    TOO_MANY_REQUESTS(429, "Too Many Requests", HttpCategory.CLIENT_ERROR),

    /** Error interno del servidor (500). */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", HttpCategory.SERVER_ERROR),

    /** Respuesta inválida del servidor upstream (502). */
    BAD_GATEWAY(502, "Bad Gateway", HttpCategory.SERVER_ERROR),

    /** Servicio no disponible temporalmente (503). */
    SERVICE_UNAVAILABLE(503, "Service Unavailable", HttpCategory.SERVER_ERROR);

    /** Código numérico HTTP. */
    private final int code;

    /** Frase descriptiva del código HTTP. */
    private final String reasonPhrase;

    /** Categoría del código HTTP. */
    private final HttpCategory category;

    /**
     * Constructor del enum con código, frase descriptiva y categoría.
     *
     * @param code         código numérico HTTP
     * @param reasonPhrase frase descriptiva del código
     * @param category     categoría HTTP
     */
    HttpStatusCode(int code, String reasonPhrase, HttpCategory category) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
        this.category = category;
    }

    /**
     * Retorna el código numérico HTTP.
     *
     * @return código HTTP (e.g., 200)
     */
    public int code() {
        return code;
    }

    /**
     * Retorna la frase descriptiva del código HTTP.
     *
     * @return frase descriptiva (e.g., "OK")
     */
    public String reasonPhrase() {
        return reasonPhrase;
    }

    /**
     * Retorna la categoría del código HTTP.
     *
     * @return categoría HTTP
     */
    public HttpCategory category() {
        return category;
    }

    /**
     * Indica si el código pertenece a la categoría SUCCESS (2xx).
     *
     * @return {@code true} si es un código de éxito
     */
    public boolean isSuccess() {
        return category == HttpCategory.SUCCESS;
    }

    /**
     * Indica si el código pertenece a las categorías CLIENT_ERROR (4xx) o SERVER_ERROR (5xx).
     *
     * @return {@code true} si es un código de error
     */
    public boolean isError() {
        return category == HttpCategory.CLIENT_ERROR || category == HttpCategory.SERVER_ERROR;
    }

    /**
     * Busca un HttpStatusCode por su código numérico.
     *
     * @param code código numérico HTTP
     * @return Optional con el HttpStatusCode correspondiente, o vacío si no se encuentra
     */
    public static Optional<HttpStatusCode> fromCode(int code) {
        for (HttpStatusCode statusCode : values()) {
            if (statusCode.code == code) {
                return Optional.of(statusCode);
            }
        }
        return Optional.empty();
    }
}
