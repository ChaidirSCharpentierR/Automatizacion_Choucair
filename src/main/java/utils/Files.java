package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Files {

    /**
     * Crea una carpeta principal dentro de "target/logs/" con la fecha actual como nombre.
     */
    public String createFolder() {
        String basePath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "logs";
        String fechaFormateada = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String reportPath = basePath + File.separator + fechaFormateada;
        File carpeta = new File(reportPath);

        if (!carpeta.exists() && carpeta.mkdirs()) {
            System.out.println("Carpeta creada: " + reportPath);
        }

        return reportPath;
    }

}
