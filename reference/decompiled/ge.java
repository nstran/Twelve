/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ge
extends au {
    private Image j = f.d("/info/btinscrease");
    private int k;
    private int l = 0;
    private n m;
    private String n = null;
    protected int i = 0;
    private n o;
    private bm p;
    private int q = 100;
    private int r = 0;

    public ge(String string) {
        int n2 = 0;
        this.n = string;
        if (string != null) {
            n2 = ca.d.a() + 4;
        }
        this.k = this.j.getWidth() / 4;
        this.m = new n(this.k + 4, n2, z.r, this.k);
        this.o = new n(this.m.a, this.m.b, this.k / 2, this.k);
        this.e(n2 + this.m.d + 4);
    }

    public final void d(int n2) {
        super.d(n2);
        this.m.c = this.l = this.e() - (this.k + 4 << 1);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.k()) {
            return;
        }
        n2 += this.c();
        n3 += this.d();
        if (this.n != null) {
            ca.d.a(graphics, this.n, n2, n3, 0);
        }
        cz.a(graphics, this.j, this.m() ? (this.k << 1) + this.k : this.k << 1, 0, this.k, this.k, this.m.a - 4 + n2, this.m.b + n3, 24);
        cz.a(graphics, this.j, this.m() ? this.k : 0, 0, this.k, this.k, this.m.a + this.m.c + 4 + n2, this.m.b + n3, 20);
        int n4 = this.m.b + this.m.d / 2 + n3;
        graphics.setColor(this.m() ? 14210926 : 0xAFAFAF);
        graphics.drawLine(this.m.a + n2, n4, this.m.a + this.m.c + n2, n4);
        graphics.drawLine(this.m.a + n2, n4 + 1, this.m.a + this.m.c + n2, n4 + 1);
        graphics.drawLine(this.m.a + n2 + this.m.c / 2, n4 - 2, this.m.a + n2 + this.m.c / 2, n4 + 3);
        graphics.setColor(this.m() ? 0xFF1010 : 0xAFAF00);
        graphics.fillRect(this.o.a + n2, this.o.b + n3, this.o.c, this.o.d);
        this.c(false);
    }

    public void h(int n2) {
        this.i = n2 > this.q ? this.q : (n2 < 0 ? 0 : n2);
        n2 = this.l;
        this.o.a = this.m.a + this.i * n2 / this.q - this.o.c / 2;
        if (this.p != null) {
            this.p.a(this, this.i);
        }
    }

    public final int a() {
        return this.i;
    }

    public final boolean f(int n2) {
        if (this.b != null) {
            this.b.c(true);
        }
        switch (n2) {
            case 97: {
                this.h(this.i - 10);
                return true;
            }
            case 96: {
                this.h(this.i + 10);
                return true;
            }
            case 98: 
            case 99: {
                return false;
            }
        }
        return true;
    }

    public final boolean c(int n2, int n3) {
        if (new n(this.m.a - 4 - this.k, this.m.b, this.k, this.k).a(n2 -= this.c(), n3 -= this.d())) {
            this.h(this.i - 10);
            return true;
        }
        if (new n(this.m.a + this.m.c + 4, this.m.b, this.k, this.k).a(n2, n3)) {
            this.h(this.i + 10);
            return true;
        }
        if (this.m.a(n2, n3)) {
            this.h((n2 -= this.m.a) * 100 / this.m.c);
            return true;
        }
        return false;
    }

    public final void a(bm bm2) {
        this.p = bm2;
    }
}

