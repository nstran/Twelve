/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mj
extends ax {
    private int a;
    private Image[] b;
    private Image[] c;
    private Image[] d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;

    public mj() {
        mj mj2 = this;
        this.c = mn.a().d;
        mj2.d = mn.a().e;
        mj2.b = new Image[1];
        mj2.b[0] = mn.a().g;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        switch (this.a) {
            case 2: 
            case 3: {
                graphics.drawImage(this.c[this.g], this.m, this.n, 3);
                return;
            }
            case 1: {
                graphics.drawImage(this.c[this.g], this.m, this.n, 3);
                n2 = 14;
                if (this.e >= 3) {
                    ++n2;
                }
                if ((this.f & 1) == 1) {
                    graphics.drawRegion(this.d[this.g], 0, 0, this.d[this.g].getWidth(), this.d[this.g].getHeight(), 6, this.m - n2, this.n, 10);
                }
                if ((this.f & 2) == 2) {
                    graphics.drawRegion(this.d[this.g], 0, 0, this.d[this.g].getWidth(), this.d[this.g].getHeight(), 5, this.m + n2, this.n, 6);
                }
                if ((this.f & 4) == 4) {
                    graphics.drawRegion(this.d[this.g], 0, 0, this.d[this.g].getWidth(), this.d[this.g].getHeight(), 0, this.m, this.n - n2, 33);
                }
                if ((this.f & 8) != 8) break;
                graphics.drawRegion(this.d[this.g], 0, 0, this.d[this.g].getWidth(), this.d[this.g].getHeight(), 3, this.m, this.n + n2, 17);
                return;
            }
            case 4: {
                n2 = this.b[this.g].getWidth() >> 1;
                n3 = 0;
                if (this.j >= 2) {
                    n3 = 1;
                }
                cz.a(graphics, this.b[this.g], n3 * n2, 0, n2, this.b[this.g].getHeight(), this.m, this.n, 3);
            }
        }
    }

    public final void i() {
        switch (this.a) {
            case 4: {
                ++this.j;
                if (this.j < 4) break;
                int n2 = 2;
                mj mj2 = this;
                this.a = 2;
                return;
            }
            case 3: {
                int n3 = Math.abs(this.i - this.n);
                n3 = n3 < 2 ? 2 : (n3 /= 2);
                boolean bl2 = this.f(this.i, n3);
                n3 = Math.abs(this.h - this.m);
                n3 = n3 < 2 ? 2 : (n3 /= 2);
                if (bl2 &= this.e(this.h, n3)) {
                    n3 = 2;
                    mj mj3 = this;
                    this.a = 2;
                }
            }
            case 1: {
                ++this.e;
                if (this.e <= 6) break;
                this.e = 0;
            }
        }
    }

    public final void a() {
        this.a = 0;
    }

    public final void a(int n2) {
        this.g = n2;
    }

    public final void c(int n2, int n3, int n4, int n5) {
        this.a = 3;
        this.h = n4;
        this.i = n5;
        this.c(n2, n3);
    }

    public final void a(int n2, int n3) {
        this.a = 2;
        this.c(n2, n3);
    }

    public final void b(int n2, int n3, int n4) {
        this.a = 1;
        this.f = n2;
        this.c(n3, n4);
    }

    public final void b(int n2, int n3) {
        this.a = 4;
        this.c(n2, n3);
        this.j = 0;
        this.e = 0;
    }
}

