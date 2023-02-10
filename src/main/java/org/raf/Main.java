package org.raf;

import storage.StorageManager;
import storage.components.AbstractStorage;
import storage.exceptions.ExtensionNotAllowedException;
import storage.exceptions.NotEnoughSpaceException;
import storage.exceptions.TooManyFilesException;

import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        try {
            Class.forName("raf.gdrive.components.GoogleDriveStorage");
            //Class.forName("storage.StorageImpl");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        AbstractStorage storage = StorageManager.getStorage();
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        while(true){
            String input = scanner.nextLine();

            try {
                InputParser.parse(input, storage);
            } catch (ExtensionNotAllowedException | NotEnoughSpaceException | TooManyFilesException e) {
                throw new RuntimeException(e.getMessage());
            }

            if(input.equals("end")) break;
        }
    }
}
