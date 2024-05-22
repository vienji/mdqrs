/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mdqrs.classes;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author Vienji
 */

public class JarDirectory {
    public static File getJarDir(Class<?> clazz) throws URISyntaxException, IOException {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        File jarFile = new File(url.toURI());
        return jarFile.getParentFile();
    }
}

