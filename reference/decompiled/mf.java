/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mf
extends ax
implements mp {
    private static byte[][] a = new byte[][]{{-1}, new byte[1], {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6}, new byte[1], new byte[1], new byte[1], new byte[1]};
    private Image[] b;
    private nb[][] c;
    private n d;

    public static void a() {
        a = null;
    }

    public mf() {
        int n2;
        if (a == null) {
            a = new byte[][]{{-1}, new byte[1], {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6}, new byte[1], new byte[1], new byte[1], new byte[1]};
        }
        mf mf2 = this;
        this.b = mn.a().c;
        this.c = new nb[8][8];
        this.d = new n();
        this.o = 224;
        this.p = 224;
        this.d.a(this.m, this.n, this.o, this.p);
        int n3 = 0;
        while (n3 < 8) {
            n2 = 0;
            while (n2 < 8) {
                this.c[n3][n2] = new nb(this.b);
                this.c[n3][n2].j(3);
                this.c[n3][n2].a(a);
                this.c[n3][n2].i();
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
                this.c[n4][n7].d(n6, 1035);
                this.c[n3 + 4][n7].d(n6, 1035);
                this.c[n4][n5 + 4].d(n6, 1035);
                this.c[n3 + 4][n5 + 4].d(n6, 1035);
                n6 += 2;
                ++n5;
                --n7;
            }
            ++n3;
            n2 += 2;
            --n4;
        }
    }

    public final n b() {
        return this.d;
    }

    public final void c(int n2, int n3) {
        super.c(n2, n3);
        this.d.a(this.m - 14, this.n - 14, this.o, this.p);
    }

    public final void a(int n2, int n3, nh nh2) {
        this.c[n2 -= 2][n3 -= 2].a(this.m + n3 * 28, this.n + n2 * 28, (int)nh2.C, nh2.B);
    }

    public final void a(int n2, int n3, nh nh2, int n4) {
        this.c[n2 -= 2][n3 -= 2].a(this.m + n3 * 28, this.n + n2 * 28, nh2.C, nh2.B, n4);
    }

    public final void a(int n2, int n3, int n4, int n5, nh nh2, nh nh3) {
        int n6 = this.m + (n3 -= 2) * 28;
        int n7 = this.n + (n2 -= 2) * 28;
        int n8 = this.m + (n5 -= 2) * 28;
        int n9 = this.n + (n4 -= 2) * 28;
        this.c[n2][n3].a(n8, n9, n6, n7, nh3.C, nh3.B);
        this.c[n4][n5].a(n6, n7, n8, n9, nh2.C, nh2.B);
    }

    public final void a(int n2, int n3, int n4, int n5, nh nh2, int n6) {
        try {
            int n7 = this.m + (n3 -= 2) * 28;
            int n8 = this.n + (n2 -= 2) * 28;
            int n9 = this.m + (n5 -= 2) * 28;
            int n10 = this.n + (n4 -= 2) * 28;
            this.c[n4][n5].a(n7, n8, n9, n10, nh2.C, nh2.B, n6);
            return;
        }
        catch (Exception exception) {
            cw.a("[boardview] setfall  " + n2 + "   " + n3 + "     " + n4 + "    " + n5 + nh2 + exception);
            return;
        }
    }

    public final int a(mu mu2) {
        int n2 = mu2.b - 2;
        int n3 = mu2.c - 2;
        int n4 = n3 * 28 + this.m;
        int n5 = n2 * 28 + this.n;
        byte[][] byArray = a;
        int n6 = n3;
        n3 += mu2.d;
        while (n6 < n3) {
            this.c[n2][n6].a(byArray);
            this.c[n2][n6].c(n4, n5, mu2.a.C, mu2.j);
            n4 += 28;
            ++n6;
        }
        return 10 + mu2.j;
    }

    public final int b(mu mu2) {
        int n2 = mu2.e - 2;
        int n3 = mu2.f - 2;
        int n4 = n3 * 28 + this.m;
        int n5 = n2 * 28 + this.n;
        byte[][] byArray = a;
        int n6 = n2;
        n2 += mu2.g;
        while (n6 < n2) {
            this.c[n6][n3].a(byArray);
            this.c[n6][n3].c(n4, n5, mu2.a.C, mu2.j);
            n5 += 28;
            ++n6;
        }
        return 10 + mu2.j;
    }

    public final nb a(int n2, int n3) {
        return this.c[n2][n3];
    }

    public final void a(Graphics graphics, int n2, int n3) {
        n2 = 0;
        while (n2 < 8) {
            n3 = 0;
            while (n3 < 8) {
                this.c[n2][n3].a(graphics);
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
                this.c[n2][n3].i();
                ++n3;
            }
            ++n2;
        }
    }
}

