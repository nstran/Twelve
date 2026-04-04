/*
 * Decompiled with CFR 0.152.
 */
public final class z {
    public static boolean a = true;
    public static boolean b = false;
    public static int[] c = new int[300];
    public static int d = 0;
    public static boolean e = true;
    public static final byte[][] f = new byte[][]{{103, 3, -118, -86}, {-67, -99, -59, -116}, {32, 4, -32, -14}, {7, 108, -51, -109}, {-10, -107, 67, -96}, {116, -102, -114, 108}, {71, 56, -88, -10}, {-128, -87, -17, -45}, {-65, 67, 52, -94}, {50, -72, -110, 61}};
    public static final byte[][] g = new byte[][]{{-125, 12, -1, -80}, {-70, -61, 100, -82}, {-110, -45, -90, -81}, {-14, 55, 7, -104}, {-37, -45, -18, -18}, {119, 86, -37, 35}, {-103, -82, -106, 49}, {-30, 53, 17, -110}, {-86, 23, 98, -98}, {-102, 103, 86, 74}};
    public static final byte[][] h = new byte[][]{{5, -1, -84, 81}, {122, -7, 6, -51}, {-73, 70, 85, -18}, {-83, 104, -66, 81}, {-15, -79, -56, 68}, {35, 24, 49, -17}, {-6, -71, 55, -40}, {124, 98, -53, -34}, {66, 28, 84, 78}, {-62, 32, 35, -48}};
    public static final byte[][] i = new byte[][]{{68, 65, -8, -61}, {-35, 76, 8, 72}, {107, -37, 102, 38}, {-114, -109, 73, 6}, {48, -22, 4, 4}, {11, 67, 89, -56}, {-66, -85, 17, 111}, {-104, 58, 67, -42}, {82, 2, -100, -95}, {29, 69, -46, 16}};
    public static boolean j = true;
    public static byte[] k = new byte[]{-84, -43, -55, -93, -4, -23, 108, -50, 92, -81, -74, -27, 96, 12, -104, -108};
    public static long l = 40L;
    public static boolean m = false;
    public static int n = 6;
    public static int[] o = new int[]{3, 3, 1000, 3, 3, 3};
    public static int p = 176;
    public static int q = 220;
    public static int r = 10;
    public static int s = 10;
    public static int t = 10;
    public static int u = 10;
    public static int v;
    public static int w;
    public static boolean x;
    public static int y;
    public static int z;
    public static int A;
    public static int B;
    public static int C;
    public static int D;
    public static boolean E;
    public static boolean F;
    public static boolean G;
    public static int H;
    public static int I;
    public static int J;
    public static boolean K;
    public static boolean L;
    public static boolean M;
    public static int N;
    public static int O;
    public static boolean P;
    public static int Q;
    public static boolean R;
    public static boolean S;
    public static boolean T;
    public static int U;
    public static boolean V;
    public static int W;
    public static boolean X;
    public static boolean Y;
    public static boolean Z;
    public static boolean aa;
    public static boolean ab;
    public static int ac;
    public static int ad;
    public static int ae;
    public static int af;

    static {
        x = false;
        y = -1;
        z = -1;
        A = -1;
        B = -1;
        C = -1;
        D = -1;
        E = false;
        F = false;
        G = false;
        H = 1;
        I = 1;
        J = 0;
        K = true;
        L = true;
        M = true;
        N = 80;
        O = 100;
        P = true;
        Q = 4;
        R = true;
        S = true;
        T = false;
        U = 0;
        V = false;
        W = 4;
        X = false;
        Y = false;
        Z = false;
        aa = false;
        ab = false;
        ae = 0;
        af = 200038;
        z.a(200038);
    }

    public static boolean a() {
        return J == 1 || J == 0;
    }

    public static boolean b() {
        return ab || J == 2 || x;
    }

    public static void c() {
        int n2 = 0;
        while (n2 < 300) {
            z.c[n2] = 0;
            ++n2;
        }
    }

    public static final String d() {
        return "" + (T ? 1 : 0) + 0 + cl.f + cl.g + cl.h + cl.i + cl.j + 5 + 300000L;
    }

    public static void a(int n2) {
        ac = n2;
        int n3 = n2 >> 16 & 0xFF;
        int n4 = n2 >> 8 & 0xFF;
        ad = (255 - n3) / 6 + n3 << 16 | (255 - n4) / 6 + n4 << 8 | (255 - (n2 &= 0xFF)) / 6 + n2;
    }

    public static final void a(int n2, int n3, boolean bl2) {
        r = n2;
        s = n3;
        if (r > t) {
            r = t;
        }
        if (s > u) {
            s = u;
        }
        if (!bl2) {
            if (r > 240) {
                r = 240;
            }
            if (s > 320) {
                s = 320;
            }
        }
        z.e();
        ak.a().d();
    }

    public static void e() {
        v = (t - r) / 2;
        w = (u - s) / 2;
    }
}

