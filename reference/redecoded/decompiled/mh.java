/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mh
extends at
implements mr {
    private static byte[][] b = new byte[][]{{-1}, new byte[1], {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6}, new byte[1], new byte[1], new byte[1], new byte[1]};
    private Image[] c;
    private nd[][] d;
    k a;

    public static void a() {
        b = null;
    }

    public mh() {
        int n2;
        if (b == null) {
            b = new byte[][]{{-1}, new byte[1], {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6}, new byte[1], new byte[1], new byte[1], new byte[1]};
        }
        mh mh2 = this;
        this.c = mp.a().c;
        this.d = new nd[8][8];
        this.a = new k();
        this.o = 224;
        this.p = 224;
        this.a.a(this.m, this.n, this.o, this.p);
        int n3 = 0;
        while (n3 < 8) {
            n2 = 0;
            while (n2 < 8) {
                this.d[n3][n2] = new nd(this.c);
                this.d[n3][n2].j(3);
                this.d[n3][n2].a(b);
                this.d[n3][n2].i();
                ++n2;
            }
            ++n3;
        }
        n3 = 0;
        n2 = 1000;
        int n4 = 3;
        while (n4 >= 0) {
            int n5 = 0;
            int n6 = n2;
            int n7 = 3;
            while (n7 >= 0) {
                this.d[n4][n7].d(n6, 1035);
                this.d[n3 + 4][n7].d(n6, 1035);
                this.d[n4][n5 + 4].d(n6, 1035);
                this.d[n3 + 4][n5 + 4].d(n6, 1035);
                n6 += 2;
                ++n5;
                --n7;
            }
            ++n3;
            n2 += 2;
            --n4;
        }
    }

    public final void c(int n2, int n3) {
        super.c(n2, n3);
        this.a.a(this.m - 14, this.n - 14, this.o, this.p);
    }

    public final void a(int n2, int n3, nj nj2, int n4) {
        this.d[n2 -= 2][n3 -= 2].a(this.m + n3 * 28, this.n + n2 * 28, nj2.g, nj2.f, n4);
    }

    public final void a(int n2, int n3, int n4, int n5, nj nj2, nj nj3) {
        int n6 = this.m + (n3 -= 2) * 28;
        int n7 = this.n + (n2 -= 2) * 28;
        int n8 = this.m + (n5 -= 2) * 28;
        int n9 = this.n + (n4 -= 2) * 28;
        this.d[n2][n3].a(n8, n9, n6, n7, nj3.g, nj3.f);
        this.d[n4][n5].a(n6, n7, n8, n9, nj2.g, nj2.f);
    }

    public final void a(int n2, int n3, int n4, int n5, nj nj2, int n6) {
        try {
            int n7 = this.m + (n3 -= 2) * 28;
            int n8 = this.n + (n2 -= 2) * 28;
            int n9 = this.m + (n5 -= 2) * 28;
            int n10 = this.n + (n4 -= 2) * 28;
            this.d[n4][n5].a(n7, n8, n9, n10, nj2.g, nj2.f, n6);
            return;
        }
        catch (Exception exception) {
            ct.a("[boardview] setfall  " + n2 + "   " + n3 + "     " + n4 + "    " + n5 + nj2 + exception);
            return;
        }
    }

    public final int a(mw mw2) {
        int n2 = mw2.b - 2;
        int n3 = mw2.c - 2;
        int n4 = n3 * 28 + this.m;
        int n5 = n2 * 28 + this.n;
        byte[][] byArray = b;
        int n6 = n3;
        n3 += mw2.d;
        while (n6 < n3) {
            this.d[n2][n6].a(byArray);
            this.d[n2][n6].c(n4, n5, mw2.a.g, mw2.j);
            n4 += 28;
            ++n6;
        }
        return 10 + mw2.j;
    }

    public final int b(mw mw2) {
        int n2 = mw2.e - 2;
        int n3 = mw2.f - 2;
        int n4 = n3 * 28 + this.m;
        int n5 = n2 * 28 + this.n;
        byte[][] byArray = b;
        int n6 = n2;
        n2 += mw2.g;
        while (n6 < n2) {
            this.d[n6][n3].a(byArray);
            this.d[n6][n3].c(n4, n5, mw2.a.g, mw2.j);
            n5 += 28;
            ++n6;
        }
        return 10 + mw2.j;
    }

    public final nd a(int n2, int n3) {
        return this.d[n2][n3];
    }

    public final void a(Graphics graphics, int n2, int n3) {
        n2 = 0;
        while (n2 < 8) {
            n3 = 0;
            while (n3 < 8) {
                this.d[n2][n3].a(graphics);
                ++n3;
            }
            ++n2;
        }
    }

    public final void i() {
        int n2 = 0;
        while (n2 < 8) {
            int n3 = 0;
            while (n3 < 8) {
                this.d[n2][n3].i();
                ++n3;
            }
            ++n2;
        }
    }
}

