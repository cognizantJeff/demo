package com.semanticsquare.io;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IOStreamsDemo {
    static String inFileStr = "src/walden.jfif";
    static String outFileStr = "src/walden-out.jpg";

    public static void fileCopyNoBuffer() {
        System.out.println("\nInside fileCopyNoBuffer ...");

        long startTime, elapsedTime; // for speed benchmarking

        // Print file length
        File fileIn = new File(inFileStr);
        System.out.println("File size is " + fileIn.length() + " bytes");

        try (FileInputStream in = new FileInputStream(inFileStr);
             FileOutputStream out = new FileOutputStream(outFileStr)) {
            startTime = System.nanoTime();
            int byteRead;
            // Read a raw byte, returns an int of 0 to 255.
            while ((byteRead = in.read()) != -1) {
                // Write the least-significant byte of int, drop the upper 3
                // bytes
                out.write(byteRead);
            }
            elapsedTime = System.nanoTime() - startTime;
            System.out.println("Elapsed Time is " + (elapsedTime / 1000000.0) + " msec");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Most common way to read byte streams from a file
    public static void fileCopyWithBufferAndArray() {
        System.out.println("\nInside fileCopyWithBufferAndArray ...");

        long startTime, elapsedTime; // for speed benchmarking
        startTime = System.nanoTime();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFileStr));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFileStr))) {

            byte[] byteBuf = new byte[4000];
            int numBytesRead;
            while ((numBytesRead = in.read(byteBuf)) != -1) {
                out.write(byteBuf, 0, numBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        elapsedTime = System.nanoTime() - startTime;
        System.out.println("fileCopyWithBufferAndArray: " + (elapsedTime / 1000000.0) + " msec");
    }

    private static void readFromStandardInput() {
        System.out.println("\nInside readFromStandardInput ...");
        String data;
		/*
		System.out.print("Enter \"start\" to continue (Using BufferedReader): ");

		try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))){
			while ((data = in.readLine()) != null && !data.equals("start")) {
				System.out.print("\nDid not enter \"start\". Try again: ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Correct!!");
		*/


        System.out.print("\nEnter \"start\" to continue (Using java.util.Scanner): ");
        Scanner scanner = new Scanner(System.in);

        while(!(data = scanner.nextLine()).equals("start")) {
            System.out.print("\nDid not enter \"start\". Try again: ");
        }
        System.out.println("Correct!!");


        System.out.println("Now, enter the start code: ");
        int code = 0; // other methods: nextLong, nextDouble, etc
        try {
            code = scanner.nextInt();
        } catch (InputMismatchException e) {
            e.printStackTrace();

            System.out.println("you didn't enter a number");
        } finally {
            scanner.close();
        }
        System.out.println("Thanks. You entered code: " + code);


        /**
         * Scanner ~ a text scanner for parsing primitives & string
         *         ~ breaks its input into tokens using a delimited pattern (default: whitespace)
         *         ~ when System.in is used, internally constructor uses
         *            an InputStreamReader to read from it
         *         ~ hasXXX & nextXXX can be used together
         *         ~ InputMismatchException is thrown
         *         ~ From Java 5 onwards
         */

        Scanner s1 = new Scanner("Hello, How are you?");
        while(s1.hasNext()) {
            System.out.println(s1.next());
        }
    }

    public static void fileMethodsDemo() {
        System.out.println("\nInside fileMethodsDemo ...");

        File f = new File("C:\\jid\\demo\\src\\..\\walden.jpg"); // "movies\\movies.txt" also works
        //File f = new File("walden.jpg");

        System.out.println("getAbsolutePath(): " + f.getAbsolutePath());
        try {
            System.out.println("getCanonicalPath(): " + f.getCanonicalPath());
            System.out.println("createNewFile(): " + f.createNewFile());
        } catch (IOException e) {}
        System.out.println("separator: " + f.separator);
        System.out.println("separatorChar: " + f.separatorChar);
        System.out.println("getParent(): " + f.getParent());
        System.out.println("lastModified(): " + f.lastModified());
        System.out.println("exists(): " + f.exists());
        System.out.println("isFile(): " + f.isFile());
        System.out.println("isDirectory(): " + f.isDirectory());
        System.out.println("length(): " + f.length());

        System.out.println("My working or user directory: " + System.getProperty("user.dir"));
        System.out.println("new File(\"testdir\").mkdir(): " + new File("testdir").mkdir());
        System.out.println("new File(\"testdir\\test\").mkdir(): " + new File("testdir\\test").mkdir());
        System.out.println("new File(\"testdir\").delete(): " + new File("testdir").delete());
        System.out.println("new File(\"testdir\\test1\\test2\").mkdir(): " + new File("testdir\\test1\\test2").mkdir());
        System.out.println("new File(\"testdir\\test1\\test2\").mkdirs(): " + new File("testdir\\test1\\test2").mkdirs());

        try {
            File f2 = new File("temp.txt");
            System.out.println("f2.createNewFile(): " + f2.createNewFile());
            System.out.println("f2.renameTo(...): " + f2.renameTo(new File("testdir\\temp1.txt"))); // move!!
        } catch (IOException e) {}

    }

    public static void dirFilter(boolean applyFilter) {
        System.out.println("\nInside dirFilter ...");

        File path = new File(".");
        String[] list;

        if(!applyFilter)
            list = path.list();
        else
            list = path.list(new DirFilter());

        //Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for(String dirItem : list)
            System.out.println(dirItem);
    }

    public static void main(String[] args) {
        //fileCopyNoBuffer();
//      fileCopyWithBufferAndArray();
//      System.out.println(System.getProperty("file.encoding"));
//        readFromStandardInput();
        System.out.println(System.getProperty("user.dir"));//can always get root directory in this manner
        fileMethodsDemo();
        dirFilter(true);
    }


}

class DirFilter implements FilenameFilter {
    // Holds filtering criteria
    public boolean accept(File file, String name) {
        return name.endsWith(".txt") || name.endsWith(".JPG");
    }

}
