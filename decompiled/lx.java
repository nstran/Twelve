/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class lx
extends ax {
    private int a;
    private int b;
    private int[] c;
    private int[] d;
    private byte e = (byte)10;
    private int f = 30;

    public lx(int n2, int n3, int n4, byte by2) {
        this.o = n2;
        this.p = n3;
        this.f = 30;
        this.e = (byte)10;
        lx lx2 = this;
        n3 = lx2.o / 2;
        n4 = (lx2.p + lx2.f) / 2 - lx2.f + lx2.e;
        by2 = (byte)(lx2.f / 2);
        int n5 = -90;
        lx2.c = new int[9];
        lx2.d = new int[9];
        int n6 = 0;
        while (n6 < 9) {
            lx2.c[n6] = n3 + (by2 * o.b(n5) >> 14);
            lx2.d[n6] = n4 + (by2 * o.a(n5) >> 14);
            n5 += 40;
            ++n6;
        }
    }

    public final void i() {
        if (!this.r) {
            return;
        }
        this.b = this.a;
        this.a = (this.a + 1) % this.c.length;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        n2 += this.m;
        n3 += this.n;
        int n4 = oz.b.getWidth() / 3;
        int n5 = oz.b.getHeight();
        int n6 = (this.b - 2 + this.c.length) % this.c.length;
        int n7 = 0;
        while (n7 < 2) {
            int n8 = (n6 + n7 + this.c.length) % this.c.length;
            cz.a(graphics, oz.b, 0, 0, n4, n5, this.c[n8] + n2, this.d[n8] + n3, 3);
            ++n7;
        }
        cz.a(graphics, oz.b, n4, 0, n4, n5, this.c[this.b] + n2, this.d[this.b] + n3, 3);
        cz.a(graphics, oz.b, n4 + n4, 0, n4, n5, this.c[this.a] + n2, this.d[this.a] + n3, 3);
    }
}

