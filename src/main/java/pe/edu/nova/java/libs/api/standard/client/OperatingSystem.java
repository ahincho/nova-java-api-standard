package pe.edu.nova.java.libs.api.standard.client;

/**
 * Enumeración de sistemas operativos conocidos.
 * Cada constante tiene un nombre legible para presentación.
 */
public enum OperatingSystem {

    /** Sistema operativo Microsoft Windows. */
    WINDOWS("Windows"),

    /** Sistema operativo Apple macOS. */
    MACOS("macOS"),

    /** Sistema operativo Linux. */
    LINUX("Linux"),

    /** Sistema operativo Android. */
    ANDROID("Android"),

    /** Sistema operativo Apple iOS. */
    IOS("iOS"),

    /** Sistema operativo Chrome OS. */
    CHROME_OS("Chrome OS"),

    /** Sistema operativo desconocido. */
    UNKNOWN("Unknown");

    /** Nombre legible del sistema operativo. */
    private final String displayName;

    /**
     * Constructor del enum con nombre legible.
     *
     * @param displayName nombre legible del sistema operativo
     */
    OperatingSystem(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retorna el nombre legible del sistema operativo.
     *
     * @return nombre legible (e.g., "Windows")
     */
    public String getDisplayName() {
        return displayName;
    }
}
