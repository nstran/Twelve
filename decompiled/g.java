/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public class g {
    public int a;
    public int b;
    public int c;
    public int d;

    private g() {
    }

    public static Image a(Image object, int n2, int n3) {
        int n4 = object.getWidth();
        int n5 = object.getHeight();
        if (n4 != n2 || n5 != n3) {
            int[] nArray = new int[n4 * n5];
            object.getRGB(nArray, 0, n4, 0, 0, n4, n5);
            Object object2 = object = (Object)new int[n2 * n3];
            int n6 = n5;
            int n7 = n4;
            int n8 = n3;
            n5 = n2;
            int[] nArray2 = nArray;
            int n9 = n7 * 1024 / n5;
            int n10 = n6 * 1024 / n8;
            int n11 = 0;
            int n12 = 0;
            while (n11 < n8) {
                int n13 = (n10 * n11 >> 10) * n7;
                int n14 = 0;
                n6 = 0;
                while (n6 < n5) {
                    object2[n12 + n6] = (Image)nArray2[n13 + (n14 >> 10)];
                    n14 += n9;
                    ++n6;
                }
                n12 += n5;
                ++n11;
            }
            return Image.createRGBImage((int[])object, (int)n2, (int)n3, (boolean)true);
        }
        return object;
    }

    public g(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }
}

