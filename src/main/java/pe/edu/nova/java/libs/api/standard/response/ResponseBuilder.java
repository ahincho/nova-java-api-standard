package pe.edu.nova.java.libs.api.standard.response;

import pe.edu.nova.java.libs.api.standard.error.ApiError;
import pe.edu.nova.java.libs.api.standard.link.ApiLink;
import pe.edu.nova.java.libs.api.standard.metadata.ApiMetadata;
import pe.edu.nova.java.libs.api.standard.page.PageInfo;
import pe.edu.nova.java.libs.api.standard.ratelimit.RateLimitInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder fluido para construir instancias inmutables de {@link ApiResponse}.
 * <p>
 * En {@link #build()}:
 * <ul>
 *   <li>Valida que status esté en el rango 100-599</li>
 *   <li>Determina success = errors.isEmpty()</li>
 *   <li>Si no se estableció status: 200 para exitosas, 500 para errores</li>
 *   <li>Realiza copias defensivas de listas</li>
 * </ul>
 *
 * @param <T> tipo del dato contenido en la respuesta
 */
public final class ResponseBuilder<T> {

    /** Dato contenido en la respuesta. */
    private T data;

    /** Código de estado HTTP (puede ser null para auto-resolución). */
    private Integer status;

    /** Lista mutable de errores. */
    private final List<ApiError> errors = new ArrayList<>();

    /** Metadatos de la respuesta. */
    private ApiMetadata metadata;

    /** Lista mutable de enlaces HATEOAS. */
    private final List<ApiLink> links = new ArrayList<>();

    /** Información de rate limiting. */
    private RateLimitInfo rateLimitInfo;

    /** Información de paginación. */
    private PageInfo pageInfo;

    /**
     * Constructor con visibilidad de paquete. Usado por {@link ApiResponse#builder()}.
     */
    ResponseBuilder() {
    }

    /**
     * Establece el dato contenido en la respuesta.
     *
     * @param data dato a incluir
     * @return este builder
     */
    public ResponseBuilder<T> data(T data) {
        this.data = data;
        return this;
    }

    /**
     * Establece el código de estado HTTP.
     *
     * @param status código de estado HTTP
     * @return este builder
     */
    public ResponseBuilder<T> status(int status) {
        this.status = status;
        return this;
    }

    /**
     * Agrega un error a la lista interna.
     *
     * @param error error a agregar
     * @return este builder
     */
    public ResponseBuilder<T> error(ApiError error) {
        this.errors.add(error);
        return this;
    }

    /**
     * Reemplaza la lista interna de errores.
     *
     * @param errors nueva lista de errores
     * @return este builder
     */
    public ResponseBuilder<T> errors(List<ApiError> errors) {
        this.errors.clear();
        if (errors != null) {
            this.errors.addAll(errors);
        }
        return this;
    }

    /**
     * Establece los metadatos de la respuesta.
     *
     * @param metadata metadatos a incluir
     * @return este builder
     */
    public ResponseBuilder<T> metadata(ApiMetadata metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Agrega un enlace a la lista interna.
     *
     * @param link enlace a agregar
     * @return este builder
     */
    public ResponseBuilder<T> link(ApiLink link) {
        this.links.add(link);
        return this;
    }

    /**
     * Reemplaza la lista interna de enlaces.
     *
     * @param links nueva lista de enlaces
     * @return este builder
     */
    public ResponseBuilder<T> links(List<ApiLink> links) {
        this.links.clear();
        if (links != null) {
            this.links.addAll(links);
        }
        return this;
    }

    /**
     * Establece la información de rate limiting.
     *
     * @param rateLimitInfo información de rate limiting
     * @return este builder
     */
    public ResponseBuilder<T> rateLimitInfo(RateLimitInfo rateLimitInfo) {
        this.rateLimitInfo = rateLimitInfo;
        return this;
    }

    /**
     * Establece la información de paginación.
     *
     * @param pageInfo información de paginación
     * @return este builder
     */
    public ResponseBuilder<T> page(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }

    /**
     * Construye la instancia inmutable de ApiResponse.
     * <p>
     * Si no se estableció status: 200 para exitosas, 500 para errores.
     *
     * @return nueva instancia de ApiResponse
     * @throws IllegalArgumentException si status fuera del rango 100-599
     */
    public ApiResponse<T> build() {
        boolean success = errors.isEmpty();
        int resolvedStatus;

        if (this.status != null) {
            resolvedStatus = this.status;
        } else {
            resolvedStatus = success ? 200 : 500;
        }

        if (resolvedStatus < 100 || resolvedStatus > 599) {
            throw new IllegalArgumentException(
                    "status debe estar en el rango 100-599, recibido: " + resolvedStatus);
        }

        List<ApiError> defensiveErrors = errors.isEmpty()
                ? List.of()
                : Collections.unmodifiableList(new ArrayList<>(errors));

        List<ApiLink> defensiveLinks = links.isEmpty()
                ? List.of()
                : Collections.unmodifiableList(new ArrayList<>(links));

        return new ApiResponse<>(success, resolvedStatus, data, defensiveErrors,
                metadata, defensiveLinks, rateLimitInfo, pageInfo);
    }
}
