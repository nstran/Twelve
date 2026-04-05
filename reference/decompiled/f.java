/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import java.io.InputStream;
import javax.microedition.lcdui.Image;

public final class f {
    private static boolean a = false;
    private static String b = ".mg";
    private static final byte[] c = new byte[]{-119, 80, 78, 71};

    public static Image a(String string) {
        string = String.valueOf(string) + b;
        try {
            InputStream inputStream = "".getClass().getResourceAsStream(string);
            byte[] byArray = new byte[c.length];
            inputStream.read(byArray, 0, byArray.length);
            int n2 = p.c(byArray);
            byte[] byArray2 = new byte[n2];
            j.a(inputStream, byArray2, c.length);
            inputStream.close();
            int n3 = 0;
            while (n3 < c.length) {
                byArray2[n3] = c[n3];
                ++n3;
            }
            return f.a(byArray2, 0, n2);
        }
        catch (Exception exception) {
            System.out.println("[ERROR] Create image: " + string);
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] b(String string) {
        return f.c(string);
    }

    public static byte[] c(String string) {
        string = String.valueOf(string) + b;
        return f.a(string, true);
    }

    private static byte[] a(String object, boolean bl2) {
        int n2 = 0;
        byte[] byArray = null;
        try {
            object = "".getClass().getResourceAsStream((String)object);
            if (bl2) {
                byte[] byArray2 = new byte[c.length];
                ((InputStream)object).read(byArray2, 0, byArray2.length);
                int n3 = p.c(byArray2);
                byArray = new byte[n3];
                n3 = 0;
                while (n3 < c.length) {
                    byArray[n3] = c[n3];
                    ++n3;
                }
                n2 = 4;
            } else {
                byArray = new byte[((InputStream)object).available()];
            }
            j.a((InputStream)object, byArray, n2);
            ((InputStream)object).close();
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        return byArray;
    }

    public static Image a(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        return f.a(byArray, 0, byArray.length);
    }

    public static Image a(byte[] object, int n2, int n3) {
        if (object == null) {
            return null;
        }
        try {
            return Image.createImage((byte[])object, (int)n2, (int)n3);
        }
        catch (Throwable throwable) {
            object = throwable;
            throwable.printStackTrace();
            return null;
        }
    }

    public static Image d(String string) {
        return f.a(string);
    }
}

