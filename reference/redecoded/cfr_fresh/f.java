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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Image a(String string) {
        string = String.valueOf(string) + b;
        try {
            InputStream inputStream = "".getClass().getResourceAsStream(string);
            byte[] byArray = new byte[4];
            inputStream.read(byArray, 0, 4);
            int n2 = m.c(byArray);
            byte[] byArray2 = new byte[n2];
            g.a(inputStream, byArray2, 4);
            inputStream.close();
            int n3 = 0;
            while (true) {
                if (n3 >= 4) {
                    return f.a(byArray2, 0, n2);
                }
                byArray2[n3] = c[n3];
                ++n3;
            }
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

    /*
     * Unable to fully structure code
     */
    public static byte[] a(String var0, boolean var1_1) {
        var2_4 = 0;
        var3_5 = null;
        try {
            block4: {
                block3: {
                    var0 = "".getClass().getResourceAsStream((String)var0);
                    if (!var1_1) break block3;
                    var1_2 = new byte[4];
                    var0.read(var1_2, 0, 4);
                    var1_3 = m.c(var1_2);
                    var3_5 = new byte[var1_3];
                    var1_3 = 0;
                    if (true) ** GOTO lbl18
                    do {
                        var3_5[var1_3] = f.c[var1_3];
                        ++var1_3;
lbl18:
                        // 2 sources

                    } while (var1_3 < 4);
                    var2_4 = 4;
                    break block4;
                }
                var3_5 = new byte[var0.available()];
            }
            g.a((InputStream)var0, var3_5, var2_4);
            var0.close();
        }
        catch (Exception v0) {
            var0 = v0;
            v0.printStackTrace();
        }
        return var3_5;
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

