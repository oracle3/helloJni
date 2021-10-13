public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        sayHello();
    }

    private static native void sayHello();

    static{
        System.out.println("library:" + System.getProperty("java.library.path"));
        System.loadLibrary("libhello");
    }
}
