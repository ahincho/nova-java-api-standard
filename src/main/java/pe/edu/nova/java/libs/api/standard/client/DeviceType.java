package pe.edu.nova.java.libs.api.standard.client;

/**
 * Enumeración de tipos de dispositivo del cliente.
 * Cada constante tiene un nombre legible para presentación.
 */
public enum DeviceType {

    /** Dispositivo móvil (teléfono). */
    MOBILE("Mobile"),

    /** Dispositivo tipo tableta. */
    TABLET("Tablet"),

    /** Computadora de escritorio o portátil. */
    DESKTOP("Desktop"),

    /** Bot o rastreador web. */
    BOT("Bot"),

    /** Tipo de dispositivo desconocido. */
    UNKNOWN("Unknown");

    /** Nombre legible del tipo de dispositivo. */
    private final String displayName;

    /**
     * Constructor del enum con nombre legible.
     *
     * @param displayName nombre legible del tipo de dispositivo
     */
    DeviceType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retorna el nombre legible del tipo de dispositivo.
     *
     * @return nombre legible (e.g., "Mobile")
     */
    public String getDisplayName() {
        return displayName;
    }
}
