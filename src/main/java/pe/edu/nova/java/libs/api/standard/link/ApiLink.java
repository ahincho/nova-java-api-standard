package pe.edu.nova.java.libs.api.standard.link;

/**
 * Enlace HATEOAS inmutable con relación, href y método HTTP opcional.
 *
 * @param rel    relación del enlace (obligatorio)
 * @param href   URL del enlace (obligatorio)
 * @param method método HTTP (opcional, puede ser null)
 */
public record ApiLink(String rel, String href, String method) {

    /**
     * Constructor compacto con validación.
     *
     * @throws IllegalArgumentException si rel es nulo o en blanco
     * @throws IllegalArgumentException si href es nulo o en blanco
     */
    public ApiLink {
        if (rel == null || rel.isBlank()) {
            throw new IllegalArgumentException("rel es obligatorio");
        }
        if (href == null || href.isBlank()) {
            throw new IllegalArgumentException("href es obligatorio");
        }
    }

    /**
     * Crea un ApiLink con relación y href.
     *
     * @param rel  relación del enlace
     * @param href URL del enlace
     * @return nueva instancia de ApiLink
     * @throws IllegalArgumentException si rel o href son nulos o en blanco
     */
    public static ApiLink of(String rel, String href) {
        return new ApiLink(rel, href, null);
    }

    /**
     * Crea un ApiLink con relación, href y método HTTP.
     *
     * @param rel    relación del enlace
     * @param href   URL del enlace
     * @param method método HTTP (e.g., "GET", "POST")
     * @return nueva instancia de ApiLink
     * @throws IllegalArgumentException si rel o href son nulos o en blanco
     */
    public static ApiLink of(String rel, String href, String method) {
        return new ApiLink(rel, href, method);
    }
}
