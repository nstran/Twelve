/*
 * Decompiled with CFR 0.152.
 */
public final class go {
    public static int a = 0;
    public static int b = 0;
    public static int c = 0;
    public static int d = 0;
    public static String e = "";
    public static String f = "";
    public static String g = "";
    public static boolean h = false;
    public static boolean i;
    public static boolean j;
    public static lh k;
    public static ll[] l;
    public static lm[] m;
    public static int n;
    public static int o;
    public static b p;
    public static b q;
    public static lw[] r;
    public static long s;
    public static boolean t;
    public static ll[] u;
    public static lm[] v;
    public static String w;
    public static int x;

    static {
        j = false;
        l = new ll[0];
        m = new lm[0];
        n = 50;
        o = 0;
        p = new gp(0);
        q = new gq(0);
        s = -1L;
        t = true;
        u = null;
        v = null;
    }

    public static void a() {
        k = null;
        r = null;
        l = new ll[0];
        m = new lm[0];
        s = -1L;
    }

    public static void a(ll[] llArray, lm[] lmArray, int n2, int n3) {
        l = llArray;
        m = lmArray;
        n = n2;
        o = n3;
    }

    public static void a(ll ll2) {
        ll[] llArray = new ll[l.length + 1];
        System.arraycopy(l, 0, llArray, 0, l.length);
        llArray[llArray.length - 1] = ll2;
        l = llArray;
    }

    public static void b(ll ll2) {
        ll[] llArray = new ll[l.length - 1];
        int n2 = 0;
        int n3 = 0;
        while (n3 < l.length) {
            if (l[n3] != ll2) {
                llArray[n2] = l[n3];
                ++n2;
            }
            ++n3;
        }
        l = llArray;
    }

    public static void a(lm lm2, int n2) {
        int n3 = 0;
        while (n3 < m.length) {
            if (lm2.a == go.m[n3].a) {
                go.m[n3].g += n2;
                return;
            }
            ++n3;
        }
        lm[] lmArray = new lm[m.length + 1];
        System.arraycopy(m, 0, lmArray, 0, m.length);
        lmArray[lmArray.length - 1] = lm2;
        m = lmArray;
    }

    public static void a(lm lm2) {
        go.b(lm2.a, lm2.g);
    }

    public static void a(int n2, int n3) {
        if (n3 == 0) {
            go.b(n2, Integer.MAX_VALUE);
            return;
        }
        int n4 = 0;
        while (n4 < m.length) {
            if (go.m[n4].a == n2) {
                go.m[n4].g = n3;
                return;
            }
            ++n4;
        }
    }

    public static void b(int n2, int n3) {
        int n4 = 0;
        while (n4 < m.length) {
            if (go.m[n4].a == n2) {
                go.m[n4].g -= n3;
                if (go.m[n4].g <= 0) {
                    lm[] lmArray = new lm[m.length - 1];
                    System.arraycopy(m, 0, lmArray, 0, n4);
                    n3 = n4 + 1;
                    System.arraycopy(m, n3, lmArray, n4, m.length - n3);
                }
                return;
            }
            ++n4;
        }
    }

    public static boolean b() {
        int n2 = l.length;
        n2 -= go.k.D.length;
        int n3 = 0;
        while (n3 < m.length) {
            n2 = go.m[n3].e == 7 ? (n2 += go.m[n3].g) : ++n2;
            ++n3;
        }
        return n2 >= n;
    }
}

