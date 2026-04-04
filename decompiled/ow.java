/*
 * Decompiled with CFR 0.152.
 */
public final class ow {
    private static byte[] a = new byte[64];

    public static long a(nh[][] nhArray) {
        e.a();
        int n2 = 0;
        int n3 = 2;
        while (n3 < 10) {
            int n4 = 2;
            while (n4 < 10) {
                byte by2 = nhArray[n3][n4].y;
                ow.a[n2++] = by2;
                ++n4;
            }
            ++n3;
        }
        return e.a(a);
    }
}

