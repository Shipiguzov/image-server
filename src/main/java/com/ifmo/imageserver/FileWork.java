package com.ifmo.imageserver;

import com.ifmo.imageserver.entity.Image;

import java.io.*;


public class FileWork {

    public static void saveImageToDisk(Image image) {
        File imageFile = new File("C:\\images\\" + image.getFileName());
        try (FileOutputStream fileOutputStream = new FileOutputStream(imageFile)) {
            fileOutputStream.write(image.getByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readImageFromDisk(Image image) {
        byte[] bytes = new byte[3];
        File imageFile = new File("C:\\images\\" + image.getFileName());
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

    public static void deleteImageFromDisk(Image image) {
        File file = new File("c:\\images\\" + image.getFileName());
        file.delete();
    }
}
