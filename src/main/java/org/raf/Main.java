package org.raf;

import storage.StorageManager;
import storage.storageComponents.AbstractStorage;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        try {
            //Class.forName("raf.gdrive.GoogleDriveStorage");
            Class.forName("storage.StorageImpl");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        AbstractStorage storage = StorageManager.getStorage();
        Scanner scanner = new Scanner(new InputStreamReader(System.in));

        while(true){
            String input = scanner.nextLine();
            InputParser.parse(input, storage);

            if(input.equals("end")) break;
        }
    }
}
