package utils;

/**
 * Utilitario con constantes para cambiar los colores del texto en la terminal
 *
 * @Alumno: Matias Sebastiao
 * @DNI: 31070095
 * @Legajo: VINF011605
 */
public interface UtilColores {
    String RESET = "\u001B[0m";
    String RED_BOLD_BRIGHT = "\033[1;91m";
    String GREEN_BOLD_BRIGHT = "\033[1;92m";
    String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    String WHITE_BOLD_BRIGHT = "\033[1;97m";
    String WHITE_BACKGROUND = "\033[0;107m" + "\033[1;90m";
    String BLUE_BOLD_BRIGHT = "\033[1;94m";
}
