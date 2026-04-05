/*
 * Decompiled with CFR 0.152.
 */
public final class gr {
    public static int a = 0;
    public static int b = 0;
    public static int c = 0;
    public static int d = 0;
    public static String e = "";
    public static String f = "";
    public static String g = "";
    public static boolean h = false;
    public static boolean i = false;
    public static lf j;
    public static lj[] k;
    public static lk[] l;
    public static int m;
    public static int n;
    public static lu[] o;
    public static long p;
    public static boolean q;
    public static lj[] r;
    public static lk[] s;

    static {
        k = new lj[0];
        l = new lk[0];
        m = 50;
        n = 0;
        p = -1L;
        q = true;
        r = null;
        s = null;
    }

    public static void a() {
        j = null;
        o = null;
        k = new lj[0];
        l = new lk[0];
        p = -1L;
    }

    public static void a(lj lj2) {
        lj[] ljArray = new lj[k.length + 1];
        System.arraycopy(k, 0, ljArray, 0, k.length);
        ljArray[gr.k.length] = lj2;
        k = ljArray;
    }

    public static void b(lj lj2) {
        lj[] ljArray = new lj[k.length - 1];
        int n2 = 0;
        int n3 = 0;
        while (n3 < k.length) {
            if (k[n3] != lj2) {
                ljArray[n2] = k[n3];
                ++n2;
            }
            ++n3;
        }
        k = ljArray;
    }

    public static void a(lk lk2, int n2) {
        int n3 = 0;
        while (n3 < l.length) {
            if (lk2.a == gr.l[n3].a) {
                gr.l[n3].g += n2;
                return;
            }
            ++n3;
        }
        lk[] lkArray = new lk[l.length + 1];
        System.arraycopy(l, 0, lkArray, 0, l.length);
        lkArray[gr.l.length] = lk2;
        l = lkArray;
    }

    public static void a(lk lk2) {
        gr.a(lk2.a, lk2.g);
    }

    public static void a(int n2, int n3) {
        int n4 = 0;
        while (n4 < l.length) {
            if (gr.l[n4].a == n2) {
                gr.l[n4].g -= n3;
                if (gr.l[n4].g <= 0) {
                    lk[] lkArray = new lk[l.length - 1];
                    System.arraycopy(l, 0, lkArray, 0, n4);
                    n3 = n4 + 1;
                    System.arraycopy(l, n3, lkArray, n4, l.length - n3);
                }
                return;
            }
            ++n4;
        }
    }

    public static boolean b() {
        int n2 = k.length;
        n2 -= gr.j.D.length;
        int n3 = 0;
        while (n3 < l.length) {
            n2 = gr.l[n3].e == 7 ? (n2 += gr.l[n3].g) : ++n2;
            ++n3;
        }
        return n2 >= m;
    }
}

