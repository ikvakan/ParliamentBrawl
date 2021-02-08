/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.reflection;

import hr.algebra.controller.CardTableController;
import hr.algebra.utils.MessageUtils;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IgorKvakan
 */
public class HandleReflection {

    private static final String DOCUMENTATION_FILENAME = "documentation.txt";
    private static final String CLASS_PATH = "src/hr/algebra/model";
    private static final String CLASSES_PACKAGE
            = CLASS_PATH.substring(CLASS_PATH.indexOf("/") + 1).replace("/", ".").concat(".");

    public static void createDocumentation() {

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(DOCUMENTATION_FILENAME))) {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(CLASS_PATH));

            StringBuilder classAndMemebersInfo = new StringBuilder();
            stream.forEach(file
                    -> {
                String fileName = file.getFileName().toString();
                String className = fileName.substring(0, fileName.indexOf("."));

                classAndMemebersInfo
                        .append("<h1>")
                        .append(className)
                        .append("</h1>")
                        .append(System.lineSeparator());

                try {
                    Class<?> clazz = Class.forName(CLASSES_PACKAGE.concat(className));
                    ReflectionUtils.readClassAndMembersInof(clazz, classAndMemebersInfo);
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, e);
                }

                classAndMemebersInfo
                        .append(System.lineSeparator())
                        .append(System.lineSeparator())
                        .append("</ul>")
                        .append(System.lineSeparator())
                        .append(System.lineSeparator());

            });

            writer.write(classAndMemebersInfo.toString());

            MessageUtils.ShowMessage("Documentation", "Documentation created").showAndWait();

        } catch (IOException e) {
            Logger.getLogger(CardTableController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
