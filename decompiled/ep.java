/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class ep {
    private static byte[] d = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
    public Image a;
    public String b;
    public short c;

    public static ep[] a(byte[] byArray) {
        int n2 = p.a(byArray[0], byArray[1]);
        ep[] epArray = null;
        if (n2 > 0) {
            epArray = new ep[n2];
            int n3 = 2;
            int n4 = 0;
            while (n4 < n2) {
                int n5;
                epArray[n4] = new ep();
                epArray[n4].c = p.b(byArray[n3++], byArray[n3++]);
                if ((n5 = p.a(byArray[n3++], byArray[n3++], byArray[n3++], byArray[n3++])) > 0) {
                    epArray[n4].b = l.a(byArray, n3, n5);
                    n3 += n5;
                }
                if ((n5 = p.a(byArray[n3++], byArray[n3++], byArray[n3++], byArray[n3++])) > 0) {
                    try {
                        byte by2 = byArray[n3 - 1];
                        byte by3 = byArray[n3 - 2];
                        byte by4 = byArray[n3 - 3];
                        byte by5 = byArray[n3 - 4];
                        byte by6 = byArray[n3 - 5];
                        byte by7 = byArray[n3 - 6];
                        byte by8 = byArray[n3 - 7];
                        byte by9 = byArray[n3 - 8];
                        byArray[n3 - 1] = d[7];
                        byArray[n3 - 2] = d[6];
                        byArray[n3 - 3] = d[5];
                        byArray[n3 - 4] = d[4];
                        byArray[n3 - 5] = d[3];
                        byArray[n3 - 6] = d[2];
                        byArray[n3 - 7] = d[1];
                        byArray[n3 - 8] = d[0];
                        epArray[n4].a = Image.createImage((byte[])byArray, (int)(n3 - d.length), (int)(n5 + d.length));
                        byArray[n3 - 1] = by2;
                        byArray[n3 - 2] = by3;
                        byArray[n3 - 3] = by4;
                        byArray[n3 - 4] = by5;
                        byArray[n3 - 5] = by6;
                        byArray[n3 - 6] = by7;
                        byArray[n3 - 7] = by8;
                        byArray[n3 - 8] = by9;
                    }
                    catch (Exception exception) {}
                    n3 += n5;
                }
                ++n4;
            }
        }
        return epArray;
    }
}

