/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class no
extends as {
    private int s = 30;
    private int t = 40;
    private int u;
    private int v;
    private int w;
    private k x;
    private boolean y;

    public no() {
        this.q = 3;
    }

    public final void a(int n2, int n3, k k2, Image image, int n4, int n5, int n6, boolean n7) {
        this.y = n7;
        this.x = k2;
        n7 = k2.c >> 1;
        int n8 = k2.d >> 1;
        n7 = n7 <= 0 ? 1 : n7;
        n8 = n8 <= 0 ? 1 : n8;
        int n9 = k2.a + cv.a() % n7;
        int n10 = k2.b + cv.a() % n8;
        n8 = n6;
        n7 = n5;
        n6 = n4;
        Image image2 = image;
        n4 = n10;
        int n11 = n9;
        int n12 = n3 + cv.a() % 13;
        n3 = n2 + cv.a() % 13;
        no no2 = this;
        no2.c(n3, n12);
        no2.h = n11;
        no2.i = n4;
        no2.r = true;
        no2.j = at.a(no2.m, n11, no2.s) + cv.a(4);
        no2.k = at.a(no2.n, n4, no2.t) + cv.a(4);
        no2.b = image2;
        no2.u = n6;
        no2.o = n7;
        no2.p = n8;
    }

    public final void k() {
        if (this.y) {
            if (this.v != this.x.a) {
                this.h = this.x.a + this.x.c / 2 + cv.a() % (this.x.c >> 1);
                this.v = this.x.a;
            }
            if (this.w != this.x.b) {
                this.i = this.x.b + this.x.d / 2 + cv.a() % (this.x.d >> 1);
                this.w = this.x.b;
            }
        }
        if (this.b(this.h, this.i, this.j, this.k)) {
            this.r = false;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.r && this.b != null && this.g >= 0 && this.f[this.e][this.g] >= 0) {
            cw.a(graphics, this.b, this.f[this.e][this.g] * this.o, this.u, this.o, this.p, this.m + n2, this.n + n3, this.q);
        }
    }
}

