package pl.com.bottega.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.com.bottega.inventory.cesar.CesarInputStream;
import pl.com.bottega.inventory.cesar.CesarOutputStream;

import java.io.*;
import java.util.Scanner;

public class AppCesar {
    public static void main(String[] args) throws IOException {
        File file = new File("/home/przemek/text.log");
        file.createNewFile();
        CesarOutputStream stream = new CesarOutputStream(new FileOutputStream(file), 3);

        System.out.println("Podaj text do zaszyfrowania: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        stream.write(input.getBytes());
        stream.close();

        File fileInput = new File("/home/przemek/text.log");
        CesarInputStream streamInput = new CesarInputStream(new FileInputStream(fileInput), 3);
        int value;
        while((value = streamInput.read()) != -1) {
            System.out.println("z pliku: " + (char)value);
        }
        streamInput.close();
    }
}
