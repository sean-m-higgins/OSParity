import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.lang.Math.*;

public class ParityTest {

    public static byte[] fileToByteArray(String inputFile) throws Exception{
        byte[] fileContent = null;
        FileInputStream fis = new FileInputStream(inputFile);
        Path path = Paths.get(inputFile);
        fileContent = Files.readAllBytes(path);
        fis.close();
        return fileContent;
    }

    public static void byteArrayToFile(byte[] bFile, String fileDest) throws Exception {
        String str = new String(bFile);
        stringToFile(str, fileDest);
    }

    public static void stringToFile(String str, String fileDest) throws Exception {
        FileOutputStream fileOutputStream;
        fileOutputStream = new FileOutputStream(fileDest);
        fileOutputStream.write(str.getBytes());
        fileOutputStream.close();
    }

    // both arrays are of the same size
    public static byte[] sameXOR(byte[] b1, byte[] b2) {
        byte[] b3 = new byte[b1.length];
        for(int i = 0; i < b1.length; i++) {
            int new_b = b1[i] ^ b2[i];
            b3[i] = (byte) new_b;
        }
        return b3;
    }

    // arrays are of different size
    public static byte[] diffXOR(byte[] b_lower, byte[] b_higher) {
        byte[] new_b = new byte[b_higher.length];
        for(int i = 0; i < b_higher.length; i++) {
            if( i < b_lower.length) {
                new_b[i] = b_lower[i];
            }
            else {
                new_b[i] = (byte) 0;
            }
        }
        return sameXOR(new_b, b_higher);
    }

    public static byte[] XOR(byte[] b1, byte[] b2) {
        if(b1.length == b2.length) {
            return sameXOR(b1, b2);
        }
        if(b1.length < b2.length) {
            return diffXOR(b1, b2);
        }
        return diffXOR(b2, b1);
    }


    public static void main(String[] args) throws Exception{
        // create files and print string to files
        File file1 = new File("f1.txt");
        File file2 = new File("f2.txt");
        String f1= "file1 test";
        String f2 = "file2";
        stringToFile(f1, "f1.txt");
        stringToFile(f2, "f2.txt");

        // generate parity of f1 and f2
        byte[] parityB = XOR(fileToByteArray("f1.txt"),fileToByteArray("f2.txt"));
        byteArrayToFile(parityB,"parity.txt");

        //recover f1 after deleting it
        file1.delete();
        byte[] parityB1 = XOR(fileToByteArray("parity.txt"),fileToByteArray("f2.txt"));
        byteArrayToFile(parityB1,"f1.txt");

        //recover f2 after deleting it
        file2.delete();
        byte[] parityB2 = XOR(fileToByteArray("parity.txt"),fileToByteArray("f1.txt"));
        byteArrayToFile(parityB2,"f2.txt");

    } // end of main


} // end of ParityTest
