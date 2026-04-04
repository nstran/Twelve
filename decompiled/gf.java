/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class gf
extends au {
    private Object[] i;
    private int j;
    private String k;
    private int l;
    private int m;
    private int n;
    private d o = ca.d;
    private Image p;
    private bk q;
    private int r;

    public final void a(d d2) {
        this.o = d2;
    }

    public gf(String string) {
        this.k = string;
        this.p = f.d("/info/increase");
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.m = this.l = n2 + n4 / 2;
    }

    public final int a() {
        return this.j;
    }

    public final Object q() {
        return this.i[this.j];
    }

    public final void a(Object[] objectArray) {
        this.i = objectArray;
        this.j = 0;
    }

    private void i(int n2) {
        this.j = n2;
        if (this.q != null) {
            gf gf2 = this;
            this.q.a(this.b(), gf2.r, this);
        }
    }

    public final boolean f(int n2) {
        if (this.i.length > 1) {
            if (n2 == 97) {
                this.i((this.j - 1 + this.i.length) % this.i.length);
                this.m = this.c() - this.o.a(this.i[this.j].toString()) / 2;
                return true;
            }
            if (n2 == 96) {
                this.i((this.j + 1) % this.i.length);
                this.m = this.c() + this.e() + this.o.a(this.i[this.j].toString()) / 2;
                return true;
            }
        }
        return false;
    }

    public final boolean c(int n2, int n3) {
        if (n2 >= this.c() && n2 < this.c() + this.e() && n3 >= this.d() && n3 <= this.d() + this.f()) {
            if (this.g) {
                if (n2 <= this.c() + this.e() / 2) {
                    this.f(97);
                } else {
                    this.f(96);
                }
            }
            return true;
        }
        return false;
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        if (!bl2) {
            this.m = this.l;
            return;
        }
        this.n = 0;
    }

    public final void n() {
        if (this.g) {
            if (this.m != this.l) {
                int n2 = this.l - this.m;
                if (Math.abs(n2) > 3) {
                    n2 /= 3;
                }
                this.m += n2;
            }
            if (this.n < 6) {
                ++this.n;
                return;
            }
            this.n = 0;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = this.m + n2;
        oz.a(graphics, n2 += this.c(), n3 += this.d(), this.e(), this.f(), this.m());
        cz.a(graphics);
        if (this.i != null) {
            cz.a(graphics, n2, n3, this.e(), this.f());
            this.o.a(graphics, this.i[this.j].toString(), n4, n3 + (this.f() - this.o.a() >> 1) + 1, 1);
            cz.b(graphics);
        }
        if (this.k != null) {
            ca.d.c(true);
            ca.d.a(graphics, this.k, n2, n3 - ca.d.a(), 0);
            ca.d.c();
        }
        if (this.g && this.i != null && this.i.length > 1) {
            graphics.drawRegion(this.p, 0, 0, this.p.getHeight(), this.p.getWidth(), 4, n2 + 4 - this.n / 2, n3 += this.f() - this.p.getWidth() >> 1, 20);
            graphics.drawRegion(this.p, 0, 0, this.p.getHeight(), this.p.getWidth(), 7, n2 + this.e() - 4 + this.n / 2, n3, 24);
        }
    }

    public final void a(bk bk2) {
        this.q = bk2;
    }

    public final void h(int n2) {
        this.r = n2;
    }
}

