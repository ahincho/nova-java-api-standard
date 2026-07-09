package pe.edu.nova.java.libs.api.standard.client;

/**
 * Enumeración de navegadores web conocidos.
 * Cada constante tiene un nombre legible para presentación.
 */
public enum Browser {

    /** Navegador Google Chrome. */
    CHROME("Chrome"),

    /** Navegador Mozilla Firefox. */
    FIREFOX("Firefox"),

    /** Navegador Apple Safari. */
    SAFARI("Safari"),

    /** Navegador Microsoft Edge. */
    EDGE("Edge"),

    /** Navegador Opera. */
    OPERA("Opera"),

    /** Navegador Internet Explorer. */
    IE("Internet Explorer"),

    /** Navegador Samsung Internet. */
    SAMSUNG_INTERNET("Samsung Internet"),

    /** Navegador desconocido. */
    UNKNOWN("Unknown");

    /** Nombre legible del navegador. */
    private final String displayName;

    /**
     * Constructor del enum con nombre legible.
     *
     * @param displayName nombre legible del navegador
     */
    Browser(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retorna el nombre legible del navegador.
     *
     * @return nombre legible (e.g., "Chrome")
     */
    public String getDisplayName() {
        return displayName;
    }
}
