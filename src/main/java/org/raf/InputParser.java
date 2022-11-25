package org.raf;

import storage.storageComponents.AbstractStorage;
import storage.storageComponents.FileExtension;
import storage.storageComponents.StorageUtils;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class InputParser {

    public static boolean parse(String input, AbstractStorage storage){
        String[] inputParts = input.split(" ");
        String command = inputParts[0];
        String fileId;
        String path;
        String name;
        String filter = "";
        String extension;
        String from;
        String to;
        int numOfFilesInDir;
        int numOfDir;
        int numOfFile;
        long sizeOfStorage;
        List<String> listOfFilesForUpload = new ArrayList<>();
        List<String> listOfNames = new ArrayList<>();
        Map<FileExtension, Boolean> mapOfExtensions = new HashMap<>();

        try{
            switch(command) {
                case "help":
                    File file = new File("C:\\Users\\LUKA\\Desktop\\StorageApp\\src\\main\\java\\org\\raf\\help.TXT");
                    Scanner myReader = new Scanner(file);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        System.out.println(data);
                    }
                    break;
                case "getParent":
                    for (int i = 2; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    path = inputParts[1];
                    System.out.println(storage.getParentDirectoryOfFile(path, filter));
                    break;
                case "getFromInPeriod":
                    for (int i = 6; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    to = inputParts[4] + " " + inputParts[5];
                    from = inputParts[2] + " " + inputParts[3];
                    path = inputParts[1];
                    System.out.println(storage.getFilesInPeriod(path, from, to, filter));
                    break;
                case "fileWithName":
                    listOfNames.clear();
                    for (int i = 2; i < inputParts.length; i++) {
                        listOfNames.add(inputParts[i]);
                    }
                    path = inputParts[1];
                    System.out.println(storage.containsFileWithName(path, listOfNames));
                    break;
                case "getFromStr":
                    for (int i = 3; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    extension = inputParts[2];
                    path = inputParts[1];
                    System.out.println(storage.getFilesThatContainsStringFromDirectory(path, extension, filter));
                    break;
                case "getFromExt":
                    for (int i = 3; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    extension = inputParts[2];
                    path = inputParts[1];
                    System.out.println(storage.getFilesWithExtensionFromDirectory(path, FileExtension.valueOf(extension), filter));
                    break;
                case "getFromSub":
                    for (int i = 2; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    path = inputParts[1];
                    System.out.println(storage.getFilesFromSubdirectories(path, filter));
                    break;
                case "getFrom":
                    for (int i = 2; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    path = inputParts[1];
                    System.out.println(storage.getFilesFromDirectory(path, filter));
                    break;
                case "rename":
                    path = inputParts[1];
                    name = inputParts[2];
                    storage.renameFile(path, name);
                    System.out.println("Successfully renamed");
                    break;
                case "download":
                    fileId = inputParts[1];
                    path = inputParts[2];
                    storage.saveFile(fileId, path);
                    System.out.println("Successfully downloaded");
                    break;
                case "move":
                    fileId = inputParts[1];
                    path = inputParts[2];
                    storage.moveFile(fileId, path);
                    System.out.println("Successfully moved");
                    break;
                case "upload":
                    listOfFilesForUpload.clear();
                    for (int i = 1; i < inputParts.length - 1; i++) {
                        listOfFilesForUpload.add(inputParts[i]);
                    }
                    path = inputParts[inputParts.length - 1];

                    storage.uploadFile(listOfFilesForUpload, path);
                    System.out.println("Successfully uploaded");
                    break;
                case "delAll":
                    path = inputParts[1];
                    storage.deleteAllFromDirectory(path);
                    System.out.println("Successfully deleted!");
                    break;
                case "delAllDirs":
                    path = inputParts[1];
                    storage.deleteAllDirectoriesFromParentDirectory(path);
                    System.out.println("Successfully deleted!");
                    break;
                case "delAllFiles":
                    path = inputParts[1];
                    storage.deleteAllFilesFromDirectory(path);
                    System.out.println("Successfully deleted!");
                    break;
                case "del":
                    path = inputParts[1];
                    storage.deleteFile(path);
                    System.out.println("Successfully deleted!");
                    break;
                case "mkfile":
                    path = inputParts[1];
                    name = inputParts[2];
                    storage.createFile(path, name);
                    System.out.println("Successfully created " + name);
                    break;
                case "mkdir":
                    path = inputParts[1];
                    name = inputParts[2];
                    numOfFilesInDir = Integer.parseInt(inputParts[3]);
                    storage.createDirectory(path, name, numOfFilesInDir);
                    System.out.println("Successfully created " + name);
                    break;
                case "mkfiles":
                    path = inputParts[1];
                    numOfFile = Integer.parseInt(inputParts[2]);
                    storage.createFiles(path, numOfFile);
                    System.out.println("Successfully created " + numOfFile + " files");
                    break;
                case "mkdirs":
                    path = inputParts[1];
                    numOfDir = Integer.parseInt(inputParts[2]);
                    storage.createDirectories(path, numOfDir);
                    System.out.println("Successfully created " + numOfDir + " dirs");
                    break;
                case "config":
                    sizeOfStorage = Long.parseLong(inputParts[1]);
                    for (int i = 2; i < inputParts.length; i++) {
                        mapOfExtensions.put(StorageUtils.getFileExtension(inputParts[i]), false);
                    }
                    storage.setConfiguration(sizeOfStorage, mapOfExtensions);
                    System.out.println("Successfully updated configuration");
                    break;
                case "end":
                    return false;
                default:
                    System.out.println("There is no such command!");
                    return false;
            }
        }catch (Exception exception){
            System.out.println("Check your input!");
        }
        return false;
    }
}
