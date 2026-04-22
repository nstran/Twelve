/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class pc
extends cw {
    private static final Image i = f.d("/focusmovechess1");
    public static Image b = f.d("/crystalblue");
    public static Image c = f.d("/notifygtmicon");
    private static Image j = f.d("/tab");
    private static Image k = f.d("/dialog/corner");
    private static Image l = f.d("/corner/2");
    public static Image d = f.d("/hiddendragon");
    public static final Image e = f.d("/m/hand");
    public static final Image f = f.d("/m/arrow");
    private static Image m = null;
    private static int[] n;
    private static int[] o;
    public static final int[] g;
    private static int[] p;
    private static Image q;
    private static Image r;
    public static Image h;
    private static int s;
    private static Image t;
    private static Image u;
    private static int v;
    private static int w;

    static {
        int[] nArray = new int[25];
        nArray[1] = 9;
        nArray[2] = 24;
        nArray[3] = 33;
        nArray[4] = 43;
        nArray[5] = 59;
        nArray[6] = 67;
        nArray[7] = 73;
        nArray[8] = 89;
        nArray[9] = 105;
        nArray[10] = 121;
        nArray[11] = 137;
        nArray[12] = 152;
        nArray[13] = 163;
        nArray[14] = 179;
        nArray[15] = 202;
        nArray[16] = 210;
        nArray[17] = 221;
        nArray[18] = 229;
        nArray[19] = 243;
        nArray[20] = 259;
        nArray[21] = 275;
        nArray[22] = 291;
        nArray[23] = 305;
        nArray[24] = 315;
        n = nArray;
        o = new int[25];
        g = new int[]{9, 15, 9, 10, 16, 8, 6, 16, 16, 16, 16, 15, 11, 16, 23, 8, 11, 8, 14, 16, 16, 16, 14, 10, 6};
        p = new int[]{9, 15, 15, 12, 16, 11, 10, 16, 16, 16, 16, 13, 15, 16, 20, 8, 10, 14, 8, 16, 16, 16, 14, 11, 12};
        q = f.d("/hiddenphoenix");
        r = f.d("/roomicon");
        h = f.d("/elementsicon");
        s = 0;
        t = null;
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, boolean bl2) {
        int n6 = 10323806;
        int n7 = 14273459;
        int n8 = 15722458;
        if (bl2) {
            n6 = 22523;
            n7 = 9287679;
            n8 = 13295359;
        }
        pc.b(graphics, n2, n3, n4, n5, n6, n8, n7);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        cw.a(graphics, j, n4 * 35, 0, 35, 37, n2, n3, 3);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        pc.a(graphics, n2, n3, n4, n5, n6, n8);
        graphics.setColor(n7);
        graphics.fillRect(n2 + 1, n3 + n5 - 2, n4 - 2, 1);
        graphics.fillRect(n2 + n4 - 2, n3 + 1, 1, n5 - 2);
    }

    public static void c(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        cw.b(graphics, 22246, n2, n3, n4, n5);
        graphics.setColor(16579546);
        graphics.drawRect(n2 + 1, n3 + 1, n4 - 3, n5 - 3);
        graphics.setColor(230911);
        graphics.drawRect(n2 + 2, n3 + 2, n4 - 5, n5 - 5);
        if (n6 > 0) {
            graphics.setColor(n6);
            graphics.fillRect(n2 + 3, n3 + 3, n4 - 6, n5 - 6);
        }
        graphics.drawImage(k, n2, n3, 0);
        graphics.drawRegion(k, 0, 0, k.getWidth(), k.getHeight(), 2, n2 + n4, n3, 24);
        graphics.drawRegion(k, 0, 0, k.getWidth(), k.getHeight(), 1, n2, n3 + n5, 36);
        graphics.drawRegion(k, 0, 0, k.getWidth(), k.getHeight(), 3, n2 + n4, n3 + n5, 40);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, boolean bl2) {
        if (n6 >= 0) {
            graphics.setColor(n6);
            graphics.fillRect(n2 + 3, n3 + 4, n4 - 6, n5 - 8);
        }
        if (bl2) {
            graphics.drawImage(d, n2 + n4, n3 + n5 - 4, 40);
        }
        graphics.setColor(51967);
        graphics.drawRect(n2 + 1, n3 + 1, n4 - 3, n5 - 3);
        graphics.setColor(9975807);
        graphics.drawRect(n2 + 2, n3 + 3, n4 - 5, n5 - 7);
        graphics.setColor(8972031);
        graphics.fillRect(n2 + 2, n3 + 2, n4 - 4, 1);
        graphics.fillRect(n2 + 2, n3 + n5 - 3, n4 - 4, 1);
        graphics.setColor(22246);
        graphics.fillRect(n2 + 4, n3, n4 - 8, 1);
        graphics.fillRect(n2 + 4, n3 + n5 - 1, n4 - 8, 1);
        graphics.fillRect(n2, n3 + 4, 1, n5 - 8);
        graphics.fillRect(n2 + n4 - 1, n3 + 4, 1, n5 - 8);
        graphics.drawImage(l, n2, n3, 0);
        graphics.drawRegion(l, 0, 0, l.getWidth(), l.getHeight(), 2, n2 + n4, n3, 24);
        graphics.drawRegion(l, 0, 0, l.getWidth(), l.getHeight(), 1, n2, n3 + n5, 36);
        graphics.drawRegion(l, 0, 0, l.getWidth(), l.getHeight(), 3, n2 + n4, n3 + n5, 40);
    }

    public static void d(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        pc.a(graphics, n2, n3, n4, n5, n6, false);
    }

    public static void c(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setColor(19, 87, 151);
        graphics.drawRect(n2, n3, n4, n5);
        graphics.drawRect(++n2, ++n3, n4 -= 2, n5 -= 2);
        graphics.setColor(32, 165, 222);
        graphics.drawLine(++n2, n3, n2 + (n4 -= 2), n3);
        graphics.drawLine(n2, n3 + n5, n2 + n4, n3 + n5);
        graphics.drawLine(n2, n3 + 1, n2, n3 + 1);
        graphics.drawLine(n2, n3 + n5 - 1, n2 + 1, n3 + n5);
        graphics.drawLine(--n2, ++n3, n2, n3 + (n5 -= 2));
        graphics.drawLine(n2 += n4 + 2, n3, n2, n3 + n5);
        graphics.drawLine(n2 - 1, n3, n2, n3 + 1);
        graphics.drawLine(n2 - 1, n3 + n5, n2, n3 + n5);
    }

    public static void a(Graphics graphics, k k2, int n2, int[] nArray, int n3, int n4) {
        graphics.setColor(nArray[0]);
        graphics.drawLine(k2.a + n3 + 1, k2.b + n4, k2.a + n3 + k2.c - 2, k2.b + n4);
        graphics.drawLine(k2.a + n3 + 1, k2.b + n4 + k2.d - 1, k2.a + n3 + k2.c - 2, k2.b + n4 + k2.d - 1);
        graphics.drawLine(k2.a + n3, k2.b + n4 + 1, k2.a + n3, k2.b + n4 + k2.d - 2);
        graphics.drawLine(k2.a + n3 + k2.c - 1, k2.b + n4 + 1, k2.a + n3 + k2.c - 1, k2.b + n4 + k2.d - 2);
        int n5 = 1;
        if (n2 > 0) {
            int n6 = 1;
            while (n6 < k2.d - 1) {
                if (n6 < nArray.length) {
                    graphics.setColor(nArray[n6]);
                }
                graphics.drawLine(k2.a + n3 + 1, k2.b + n4 + n5, k2.a + n3 + n2, k2.b + n4 + n5);
                ++n5;
                ++n6;
            }
        }
    }

    public static void a(Graphics graphics, k k2, int n2) {
        pc.a(graphics, k2, 0, 0, n2);
    }

    public static void a(Graphics graphics, k k2, int n2, int n3, int n4) {
        pc.e(graphics, k2.a + n2, k2.b + n3, k2.c, k2.d, n4);
    }

    public static void e(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        int n7 = i.getWidth() - 7;
        int n8 = i.getHeight() - 7;
        cw.a(graphics, i, 0, 0, 7, 7, n2 + n6, n3 + n6, 20);
        cw.a(graphics, i, n7, 0, 7, 7, n2 + n4 - n6, n3 + n6, 24);
        cw.a(graphics, i, 0, n8, 7, 7, n2 + n6, n3 + n5 - n6, 36);
        cw.a(graphics, i, n7, n8, 7, 7, n2 + n4 - n6, n3 + n5 - n6, 40);
    }

    public static final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n7 >= 0) {
            graphics.setColor(n7);
            graphics.fillRect(n2 + 1, n3 + 1, n4 - 2, n5 - 2);
        }
        graphics.setColor(n6);
        graphics.fillRect(n2 + 1, n3, n4 - 2, 1);
        graphics.fillRect(n2 + 1, n3 + n5 - 1, n4 - 2, 1);
        graphics.fillRect(n2, n3 + 1, 1, n5 - 2);
        graphics.fillRect(n2 + n4 - 1, n3 + 1, 1, n5 - 2);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6, boolean bl2) {
        graphics.setColor(230911);
        graphics.drawLine(n2 + 2, n3, n2 + n4 - 2, n3);
        graphics.drawLine(n2 + 2, n3 + n5, n2 + n4 - 2, n3 + n5);
        graphics.drawLine(n2, n3 + 2, n2, n3 + n5 - 2);
        graphics.drawLine(n2 + n4, n3 + 2, n2 + n4, n3 + n5 - 2);
        graphics.drawLine(n2 + 1, n3 + 1, n2 + 1, n3 + 1);
        graphics.drawLine(n2 + 1, n3 + n5 - 1, n2 + 1, n3 + n5 - 1);
        graphics.drawLine(n2 + n4 - 1, n3 + 1, n2 + n4 - 1, n3 + 1);
        graphics.drawLine(n2 + n4 - 1, n3 + n5 - 1, n2 + n4 - 1, n3 + n5 - 1);
        graphics.setColor(14612735);
        graphics.drawLine(n2 + 2, n3 + 1, n2 + n4 - 2, n3 + 1);
        graphics.drawLine(n2 + 2, n3 + n5 - 1, n2 + n4 - 2, n3 + n5 - 1);
        graphics.drawLine(n2 + 1, n3 + 2, n2 + 1, n3 + n5 - 2);
        graphics.drawLine(n2 + n4 - 1, n3 + 2, n2 + n4 - 1, n3 + n5 - 2);
        if (bl2) {
            graphics.setColor(0xFFFFFF);
            graphics.fillRect(n2 + 2, n3 + 2, n4 - 3, n5 - 3);
            graphics.setColor(n6);
            graphics.fillRect(n2 + 2, n3 + 2, n4 - 4, n5 - 4);
        }
        graphics.setColor(14612735);
        graphics.drawLine(n2 + 2, n3 + 2, n2 + 2, n3 + 2);
        graphics.drawLine(n2 + 2, n3 + n5 - 2, n2 + 2, n3 + n5 - 2);
        graphics.drawLine(n2 + n4 - 2, n3 + 2, n2 + n4 - 2, n3 + 2);
        graphics.drawLine(n2 + n4 - 2, n3 + n5 - 2, n2 + n4 - 2, n3 + n5 - 2);
    }

    public static void a() {
        if (m == null) {
            m = f.d("/olaicons");
        }
    }

    public static void d(Graphics graphics, int n2, int n3, int n4, int n5) {
        n2 = Math.abs(n2);
        if (m != null) {
            cw.a(graphics, m, n[n2], o[n2], g[n2], p[n2], n3, n4, n5);
        }
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, boolean bl2, int n6) {
        graphics.setColor(16742661);
        graphics.fillRect(n2, n3, n4, 1);
        graphics.fillRect(n2, n3 + n5 - 1, n4, 1);
        graphics.setColor(16167168);
        graphics.fillRect(n2, n3 + 1, n4, 1);
        graphics.fillRect(n2, n3 + n5 - 2, n4, 1);
        graphics.setColor(0xFFFF8B);
        graphics.fillRect(n2, n3 + 2, n4, 2);
        graphics.fillRect(n2, n3 + n5 - 4, n4, 2);
        graphics.setColor(0xFFFFB7);
        graphics.fillRect(n2, n3 + 4, n4, 1);
        graphics.fillRect(n2, n3 + n5 - 5, n4, 1);
        graphics.setColor(n6);
        graphics.fillRect(n2, n3 + 5, n4, n5 - 10);
        if (bl2) {
            if (n5 - 4 < q.getHeight()) {
                cw.a(graphics, q, 0, q.getHeight() - n5 + 4, q.getWidth(), n5 - 4, n2 + n4, n3 + n5 - 2, 40);
                return;
            }
            graphics.drawImage(q, n2 + n4, n3 + n5 - 2, 40);
        }
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5, boolean bl2) {
        pc.a(graphics, n2, n3, n4, n5, bl2, 0xFFFFFF);
    }

    public static void e(Graphics graphics, int n2, int n3, int n4, int n5) {
        pc.b(graphics, n2, n3, n4, n5, true);
    }

    public static void f(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        n2 += (n4 - 7) / 2;
        n3 += (n5 - 7) / 2;
        n3 += 2;
        graphics.setColor(0);
        n4 = n2 + 3;
        while (n2 < n4) {
            graphics.fillRect(n2, n3++, 1, 3);
            ++n2;
        }
        n3 -= 2;
        n4 = n2 + 4;
        while (n2 < n4) {
            graphics.fillRect(n2, n3--, 1, 3);
            ++n2;
        }
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        graphics.setColor(16069679);
        graphics.fillRect(n2 - 1, n3 - 1, 8, 8);
        graphics.setColor(0xFFFFFF);
        graphics.drawLine(n2, n3 + 2, n2 + 5, n3 + 2);
        graphics.drawLine(n2, n3 + 2 + 1, n2 + 5, n3 + 2 + 1);
        graphics.drawLine(n2 + 2, n3, n2 + 2, n3 + 5);
        graphics.drawLine(n2 + 2 + 1, n3, n2 + 2 + 1, n3 + 5);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4) {
        int n5 = n4 / 2;
        graphics.setColor(11383216);
        graphics.drawLine(n2 + n5 - 1, n3, n2 + n5 - 1, n3 - 4);
        graphics.drawLine(n2 + n5 + 1, n3, n2 + n5 + 1, n3 - 4);
        graphics.drawLine(n2 + n5 - 1, n3 - 4, n2 + n5 - 4, n3 - 4);
        graphics.drawLine(n2 + n5 + 1, n3 - 4, n2 + n5 + 4, n3 - 4);
        graphics.drawLine(n2 + n5 - 4, n3 - 4, n2 + n5 - 4, n3 - 2);
        graphics.drawLine(n2 + n5 + 4, n3 - 4, n2 + n5 + 4, n3 - 2);
        graphics.drawLine(n2 + n5 - 4, n3 - 2, n2 + n5 - 3, n3 - 2);
        graphics.drawLine(n2 + n5 + 4, n3 - 2, n2 + n5 + 3, n3 - 2);
        graphics.drawLine(n2 + n5 - 1, n3, n2 + 17, n3);
        graphics.drawLine(n2 + n5 + 1, n3, n2 + n4 - 18, n3);
        graphics.setColor(0xBABABA);
        graphics.drawLine(n2 + 16, n3, n2 + 14, n3);
        graphics.drawLine(n2 + n4 - 17, n3, n2 + n4 - 15, n3);
        graphics.setColor(13026498);
        graphics.drawLine(n2 + 13, n3, n2 + 11, n3);
        graphics.drawLine(n2 + n4 - 14, n3, n2 + n4 - 12, n3);
        graphics.setColor(0xD1D2D2);
        graphics.drawLine(n2 + 10, n3, n2 + 9, n3);
        graphics.drawLine(n2 + n4 - 11, n3, n2 + n4 - 10, n3);
        graphics.setColor(0xCFCCCB);
        graphics.drawLine(n2 + 8, n3, n2 + 8, n3);
        graphics.drawLine(n2 + n4 - 9, n3, n2 + n4 - 9, n3);
        graphics.setColor(0xDFDFDF);
        graphics.drawLine(n2 + 7, n3, n2 + 7, n3);
        graphics.drawLine(n2 + n4 - 8, n3, n2 + n4 - 8, n3);
        graphics.setColor(13687516);
        graphics.drawLine(n2 + 6, n3, n2 + 5, n3);
        graphics.drawLine(n2 + n4 - 7, n3, n2 + n4 - 6, n3);
        graphics.setColor(0xDFDFDF);
        graphics.drawLine(n2 + 4, n3, n2 + 2, n3);
        graphics.drawLine(n2 + n4 - 5, n3, n2 + n4 - 3, n3);
        graphics.setColor(0xE9E9E9);
        graphics.drawLine(n2 + 1, n3, n2, n3);
        graphics.drawLine(n2 + n4 - 2, n3, n2 + n4 - 1, n3);
    }

    public static void a(Graphics graphics, int n2, int n3, byte by2) {
        int n4 = r.getWidth() / 4;
        cw.a(graphics, r, n4 * by2, 0, n4, r.getHeight(), n2, n3, 0);
    }

    public static void b(Graphics graphics, int n2, int n3, int n4) {
        int n5 = h.getWidth() / 4;
        cw.a(graphics, h, n5 * (n4 /= 2), 0, n5, h.getHeight(), n2, n3, 0);
    }

    public static void b() {
        byte[] byArray;
        if (t == null && (byArray = pa.a().a(1000, false, true)) != null) {
            s = m.a(byArray, 0);
            t = f.a(byArray, 4, byArray.length - 4);
        }
    }

    public static void c() {
        s = 0;
        t = null;
    }

    public static boolean a(int n2) {
        return t != null && n2 > 0 && n2 <= s && s > 0;
    }

    public static boolean c(Graphics graphics, int n2, int n3, int n4) {
        if (!pc.a(n4)) {
            return false;
        }
        int n5 = t.getWidth() / s;
        cw.a(graphics, t, n5 * (n4 - 1), 0, n5, t.getHeight(), n2, n3, 0);
        return true;
    }

    public static void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(0);
        graphics.drawLine(n2, n3, n2 + 3, n3);
        graphics.fillTriangle(n2 + 1, n3 + 1, n2 + 3, n3 + 1, n2 + 1, n3 + 3);
        graphics.setColor(0x6E6E6E);
        graphics.fillTriangle(n2 + 1, n3, n2 + 1, n3 - 3, n2 + 3, n3);
    }

    public static void c(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n7 == 0) {
            n4 = n2 + 12 + 1;
            n5 = n3 + 8;
            graphics.setColor(157, 159, 251);
            graphics.fillTriangle(n2, n5, n2 + 19, n3, n4, n5);
            graphics.setColor(85, 90, 248);
            graphics.fillTriangle(n2, n5, n2 + 19, n3 + 17, n4, n5);
            graphics.setColor(857589);
            graphics.drawLine(n2, n5, n2 + 19, n3);
            graphics.drawLine(n4, n5, n2 + 19, n3);
            graphics.drawLine(n2, n5, n2 + 19, n3 + 17);
            graphics.drawLine(n4, n5, n2 + 19, n3 + 17);
            graphics.setColor(109, 113, 249);
            graphics.drawLine(n2 + 1, n5, n4 - 1, n5);
            return;
        }
        if (n7 == 1) {
            n4 = n2 + 6 - 1;
            n5 = n3 + 8;
            graphics.setColor(157, 159, 251);
            graphics.fillTriangle(n2 + 19, n5, n2, n3, n4, n5);
            graphics.setColor(85, 90, 248);
            graphics.fillTriangle(n2 + 19, n5, n2, n3 + 17, n4, n5);
            graphics.setColor(857589);
            graphics.drawLine(n2 + 19, n5, n2, n3);
            graphics.drawLine(n4, n5, n2, n3);
            graphics.drawLine(n2 + 19, n5, n2, n3 + 17);
            graphics.drawLine(n4, n5, n2, n3 + 17);
            graphics.setColor(109, 113, 249);
            graphics.drawLine(n2 + 19 - 1, n5, n4 + 1, n5);
        }
    }

    public static void f(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setColor(13669730);
        graphics.fillRect(n2 - 1, n3 - 1, n4 + 2, n5 + 2);
        pc.a(graphics, n2 - 1, n3 - 1, n4 + 2, n5 + 2, 14593669, -1);
        cw.b(graphics, 15320744, n2 - 2, n3 - 2, n4 + 4, n5 + 4);
    }

    public static void d() {
        if (u == null) {
            u = f.a(pa.a().a(30099, false, false));
            v = u.getHeight() / 32;
            w = u.getWidth() / 32;
        }
    }

    public static void g(Graphics graphics, int n2, int n3, int n4, int n5) {
        n2 = Math.abs(n2);
        int n6 = n2 / w;
        n2 %= w;
        if (u != null && n6 < v) {
            cw.a(graphics, u, n2 << 5, n6 << 5, 32, 32, n3, n4, n5);
        }
    }

    public static final fu a(bf bf2, int n2, int n3, int n4) {
        pc.a();
        fu fu2 = new fu(m, -3);
        fu2.a(bf2);
        fu2.b(n[20], o[20], g[20], p[20]);
        fu2.a(n3, n4, 16, 16);
        return fu2;
    }
}

