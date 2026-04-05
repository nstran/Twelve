/*
 * Decompiled with CFR 0.152.
 */
public final class e {
    private static int a = 0;
    private static int[] b;

    static {
        int[] nArray = new int[256];
        int n2 = 0;
        while (n2 < 256) {
            int n3 = n2;
            int n4 = 8;
            while (--n4 >= 0) {
                if ((n3 & 1) != 0) {
                    n3 = 0xEDB88320 ^ n3 >>> 1;
                    continue;
                }
                n3 >>>= 1;
            }
            nArray[n2] = n3;
            ++n2;
        }
        b = nArray;
    }

    private static long b() {
        return (long)a & 0xFFFFFFFFL;
    }

    public static void a() {
        a = 0;
    }

    public static long a(byte[] byArray) {
        int n2 = byArray.length;
        int n3 = 0;
        int n4 = ~a;
        while (--n2 >= 0) {
            n4 = b[(n4 ^ byArray[n3++]) & 0xFF] ^ n4 >>> 8;
        }
        a = ~n4;
        e.b();
        return e.b();
    }
}

