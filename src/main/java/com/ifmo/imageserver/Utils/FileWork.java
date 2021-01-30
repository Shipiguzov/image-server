package com.ifmo.imageserver.Utils;

import com.ifmo.imageserver.entity.Image;

import java.io.*;

/**
 * Class for work with files on server
 */
public class FileWork {

    /**
     * Path where images will be stored
     */
    private final static String PATH_ON_SERVER = "C:\\images\\";

    /**
     * Save image byte array (which have a image file) to disk
     * @param image which must be saved
     */
    public static void saveImageToDisk(Image image) {
        File imageFile = new File(PATH_ON_SERVER + image.getFileName());
        try (FileOutputStream fileOutputStream = new FileOutputStream(imageFile)) {
            fileOutputStream.write(image.getByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read image byte array of necessary Image class from disk
     * @param image which we want to get byte array from file
     * @return byte array which consist necessary file of image
     */
    public static byte[] readImageFromDisk(Image image) {
        byte[] bytes = new byte[3];
        File imageFile = new File(PATH_ON_SERVER + image.getFileName());
        try (FileInputStream fileInputStream = new FileInputStream(imageFile);
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream()) {
            int data;
            while ((data = fileInputStream.read(bytes)) != -1) {
                byteArrayInputStream.write(bytes, 0, data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Delete image file from disk
     * @param image which byte array file will be deleted
     */
    public static void deleteImageFromDisk(Image image) {
        File file = new File(PATH_ON_SERVER + image.getFileName());
        file.delete();
    }
}
