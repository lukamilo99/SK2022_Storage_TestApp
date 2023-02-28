package org.raf;

import storage.components.AbstractStorage;
import storage.components.FileExtension;
import storage.components.StorageUtils;
import storage.exceptions.ExtensionNotAllowedException;
import storage.exceptions.NotEnoughSpaceException;
import storage.exceptions.TooManyFilesException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class InputParser {

    public void parse(String input, AbstractStorage storage) throws ExtensionNotAllowedException, TooManyFilesException, NotEnoughSpaceException {
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

        try {
            switch (command) {
                case "help" -> {
                    InputStream inputStream = this.getClass()
                            .getClassLoader()
                            .getResourceAsStream("help.TXT");

                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader br = new BufferedReader(isr);

                    String line;
                    while ((line = br.readLine()) != null) {
                            System.out.println(line);
                    }
                    inputStream.close();

                }
                case "getParent" -> {
                    for (int i = 2; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    path = inputParts[1];
                    System.out.println(storage.getParentDirectoryOfFile(path, filter));
                }
                case "getFromInPeriod" -> {
                    for (int i = 6; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    to = inputParts[4] + " " + inputParts[5];
                    from = inputParts[2] + " " + inputParts[3];
                    path = inputParts[1];
                    System.out.println(storage.getFilesInPeriod(path, from, to, filter));
                }
                case "fileWithName" -> {
                    listOfNames.clear();
                    listOfNames.addAll(Arrays.asList(inputParts).subList(2, inputParts.length));
                    path = inputParts[1];
                    System.out.println(storage.containsFileWithName(path, listOfNames));
                }
                case "getFromStr" -> {
                    for (int i = 3; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    extension = inputParts[2];
                    path = inputParts[1];
                    System.out.println(storage.getFilesThatContainsStringFromDirectory(path, extension, filter));
                }
                case "getFromExt" -> {
                    for (int i = 3; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    extension = inputParts[2];
                    path = inputParts[1];
                    System.out.println(storage.getFilesWithExtensionFromDirectory(path, FileExtension.valueOf(extension), filter));
                }
                case "getFromSub" -> {
                    for (int i = 2; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    path = inputParts[1];
                    System.out.println(storage.getFilesFromSubdirectories(path, filter));
                }
                case "getFrom" -> {
                    for (int i = 2; i < inputParts.length; i++) {
                        filter += inputParts[i];
                    }
                    path = inputParts[1];
                    System.out.println(storage.getFilesFromDirectory(path, filter));
                }
                case "rename" -> {
                    path = inputParts[1];
                    name = inputParts[2];
                    storage.renameFile(path, name);
                    System.out.println("Successfully renamed");
                }
                case "download" -> {
                    fileId = inputParts[1];
                    path = inputParts[2];
                    storage.saveFile(fileId, path);
                    System.out.println("Successfully downloaded");
                }
                case "move" -> {
                    fileId = inputParts[1];
                    path = inputParts[2];
                    storage.moveFile(fileId, path);
                    System.out.println("Successfully moved");
                }
                case "upload" -> {
                    listOfFilesForUpload.clear();
                    listOfFilesForUpload.addAll(Arrays.asList(inputParts).subList(1, inputParts.length - 1));
                    path = inputParts[inputParts.length - 1];
                    storage.uploadFile(listOfFilesForUpload, path);
                    System.out.println("Successfully uploaded");
                }
                case "delAll" -> {
                    path = inputParts[1];
                    storage.deleteAllFromDirectory(path);
                    System.out.println("Successfully deleted!");
                }
                case "delAllDirs" -> {
                    path = inputParts[1];
                    storage.deleteAllDirectoriesFromParentDirectory(path);
                    System.out.println("Successfully deleted");
                }
                case "delAllFiles" -> {
                    path = inputParts[1];
                    storage.deleteAllFilesFromDirectory(path);
                    System.out.println("Successfully deleted");
                }
                case "del" -> {
                    path = inputParts[1];
                    storage.deleteFile(path);
                    System.out.println("Successfully deleted");
                }
                case "mkfile" -> {
                    path = inputParts[1];
                    name = inputParts[2];
                    storage.createFile(path, name);
                    System.out.println("Successfully created " + name);
                }
                case "mkdir" -> {
                    path = inputParts[1];
                    name = inputParts[2];
                    numOfFilesInDir = Integer.parseInt(inputParts[3]);
                    storage.createDirectory(path, name, numOfFilesInDir);
                    System.out.println("Successfully created " + name);
                }
                case "mkfiles" -> {
                    path = inputParts[1];
                    numOfFile = Integer.parseInt(inputParts[2]);
                    storage.createFiles(path, numOfFile);
                    System.out.println("Successfully created " + numOfFile + " files");
                }
                case "mkdirs" -> {
                    path = inputParts[1];
                    numOfDir = Integer.parseInt(inputParts[2]);
                    storage.createDirectories(path, numOfDir);
                    System.out.println("Successfully created " + numOfDir + " dirs");
                }
                case "config" -> {
                    sizeOfStorage = Long.parseLong(inputParts[1]);
                    for (int i = 2; i < inputParts.length; i++) {
                        mapOfExtensions.put(StorageUtils.getFileExtension(inputParts[i]), false);
                    }
                    storage.setConfiguration(sizeOfStorage, mapOfExtensions);
                    System.out.println("Successfully updated configuration");
                }
                case "end" -> System.out.println("Goodbye");
                default -> System.out.println("There is no such command!");
            }
        }catch (ExtensionNotAllowedException | NotEnoughSpaceException | TooManyFilesException | IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
