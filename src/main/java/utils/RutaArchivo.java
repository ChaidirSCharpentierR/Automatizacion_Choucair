package utils;

import java.io.File;

public class RutaArchivo {

    /**
     * Metodo para obtener la ruta absoluta de un archivo en resources/Datos
     * @param nombreArchivo Nombre del archivo (ej: "test.txt")
     * @return Ruta completa del archivo como String
     */
    public static String obtenerRutaArchivo(String nombreArchivo) {
        // Obtiene la ruta base del proyecto
        String rutaBase = System.getProperty("user.dir");

        // Construye la ruta completa dentro de resources/Datos
        String rutaArchivo = rutaBase + File.separator + "src" + File.separator + "main" +
                File.separator + "resources" + File.separator + "Datos" + File.separator + nombreArchivo;

        return rutaArchivo;
    }
}