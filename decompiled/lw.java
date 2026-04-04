/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class lw
extends eb {
    private static int[] a = new int[]{4, 3, 4, 4, 5, 4, 4, 4, 4, 4, 5};
    private static int[] b;
    private static Image c;

    static {
        int[] nArray = new int[11];
        nArray[1] = 4;
        nArray[2] = 7;
        nArray[3] = 11;
        nArray[4] = 15;
        nArray[5] = 20;
        nArray[6] = 24;
        nArray[7] = 28;
        nArray[8] = 32;
        nArray[9] = 36;
        nArray[10] = 40;
        b = nArray;
    }

    public static void f() {
        if (c == null) {
            c = f.d("/tinynumber");
        }
    }

    public static void g() {
        c = null;
    }

    public static int b(int n2) {
        return lw.c(String.valueOf(n2));
    }

    public static int c(String string) {
        if (l.b(string)) {
            return 0;
        }
        int n2 = 0;
        int n3 = 0;
        while (n3 < string.length()) {
            char c2 = string.charAt(n3);
            n2 = c2 == '+' ? (n2 += a[10] + 1) : (n2 += a[c2 - 48] + 1);
            ++n3;
        }
        return n2 - 1;
    }

    public static void a(Graphics graphics, String string, int n2, int n3) {
        if (c == null) {
            return;
        }
        int n4 = 0;
        while (n4 < string.length()) {
            char c2 = string.charAt(n4);
            int n5 = 10;
            if (c2 != '+') {
                n5 = c2 - 48;
            }
            cz.a(graphics, c, b[n5], 0, a[n5], 7, n2, n3, 0);
            n2 += a[n5] + 1;
            ++n4;
        }
    }
}

