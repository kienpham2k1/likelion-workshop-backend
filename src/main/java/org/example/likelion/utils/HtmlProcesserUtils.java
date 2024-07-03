package org.example.likelion.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlProcesserUtils {
    private HtmlProcesserUtils() {
        throw new IllegalStateException("Html Processer Utils class");
    }

    public static String readHtmlTemplate(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static String replacePlaceholders(String template, String name, String date, String otp) {
        return template
                .replace("{name}", name)
                .replace("{date}", date)
                .replace("{otp}", otp);
    }
}
