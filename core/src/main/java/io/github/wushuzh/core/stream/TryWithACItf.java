package io.github.wushuzh.core.stream;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * TryWithACItf
 */
public class TryWithACItf {
    public static void main(String[] args) {
        System.out.println("Nested try catch");
        doTryCatchFinally();
        System.out.println("\nTry with AC resource");
        doTryWithResources();
        System.out.println("\nTry with Multi resources");
        doTryWithMultiResources();
        System.out.println("\nTry with Any AC Obj");
        doCloseThing();
    }

    private static void doCloseThing() {
        try (MyAutoCloseable ac = new MyAutoCloseable()){
            ac.saySomething();
        } catch (IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
            for (Throwable t : e.getSuppressed()) {
                System.out.println("Suppressed: " + t.getMessage());
            }
        }
    }

    private static void doTryWithMultiResources() {
        char[] buff = new char[8];
        int length;
        try (Reader reader = Helper.openReader("file1.txt"); Writer writer = Helper.openWriter("file2.txt")) {
            while ((length = reader.read(buff)) >= 0) {
                System.out.println("\nlength: " + length);
                writer.write(buff, 0, length);
            }
        } catch (IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    private static void doTryWithResources() {
        char[] buff = new char[8];
        int length;
        try (Reader reader = Helper.openReader("file1.txt")) {
            while ((length = reader.read(buff)) >= 0) {
                System.out.println("\nlength: " + length);
                for (int i = 0; i < length; i++) {
                    System.out.print(buff[i]);
                }
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    private static void doTryCatchFinally() {
        char[] buff = new char[8];
        int length;
        Reader reader = null;
        try {
            reader = Helper.openReader("file1.txt");
            while ((length = reader.read(buff)) >= 0) {
                System.out.println("\nlength: " + length);
                for (int i = 0; i < length; i++) {
                    System.out.print(buff[i]);
                }
            }
        } catch (IOException e) {
            System.out.println("Inside catch block: ");
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e2) {
                System.out.println("Inside finally block:");
                System.out.println(e2.getClass().getSimpleName() + " - " + e2.getMessage());
            }
        }
    }
}