/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class hn
extends ap {
    private int k = 54;
    private int l = 53;
    private int m = 5;
    private int n = 4;
    private int o = 0;
    private int p = -1;
    private int q;
    private byte r;

    public final void e(int n2) {
        this.p = n2;
    }

    public final void f(int n2) {
        this.o = n2;
    }

    public final String t() {
        return t.i[this.o];
    }

    public final void u() {
        this.p = -1;
        this.o = 0;
        this.o();
    }

    public final int v() {
        return this.o;
    }

    public hn(byte by2) {
        super(1);
        this.r = by2;
        if (by2 == 2) {
            this.q = 20;
            this.b(999999223);
        } else {
            this.q = 28;
            this.b(241224);
            this.k = 35;
            this.l = 30;
            this.m = 6;
            t.h();
        }
        if (z.r > z.s) {
            this.n = by2 == 2 ? 5 : 7;
        }
        this.f = this.n * this.k + this.m + this.m;
        this.g = this.q / this.n * this.l + this.m + this.m;
        this.c = (z.r - this.f) / 2;
        this.d = (z.s - this.g - be.a) / 2;
        this.a(new be());
        ga ga2 = new ga(-8881, 2);
        hn hn2 = this;
        hn2.a(ga2, true);
        ga2 = new ga(-8882, 3);
        hn2 = this;
        hn2.b(ga2, true);
        this.a(com.mg.sq.a.l);
    }

    public final void c(int n2) {
        if (n2 == 95) {
            if (this.i != null) {
                this.p = this.o;
                this.i.d(this.h(), -8881);
            }
        } else if (n2 == 97) {
            --this.o;
            if (this.o < 0) {
                this.o = this.q - 1;
            }
            this.e(true);
        } else if (n2 == 96) {
            ++this.o;
            if (this.o >= this.q) {
                this.o = 0;
            }
            this.e(true);
        } else if (n2 == 99) {
            this.o = (this.o - this.n + this.q) % this.q;
            this.e(true);
        } else if (n2 == 98) {
            this.o = (this.o + this.n) % this.q;
            this.e(true);
        }
        this.w();
    }

    public final void w() {
        if (this.o == this.p && this.r == 2) {
            this.a(new bh("H\u1ee7y", -8883));
            return;
        }
        this.o();
    }

    public final void a(int n2, int n3) {
        int n4 = this.a() + this.m;
        int n5 = this.c() + this.m;
        int n6 = 0;
        while (n6 < this.q) {
            int n7 = n4 + n6 % this.n * this.k;
            int n8 = n5 + n6 / this.n * this.l;
            if (n2 >= n7 && n2 <= n7 + this.k && n3 >= n8 && n3 <= n8 + this.l) {
                if (this.o == n6) {
                    if (this.o == this.p && this.r == 2) {
                        if (this.i == null) break;
                        this.i.d(this.h(), -8883);
                        return;
                    }
                    this.c(95);
                    return;
                }
                this.o = n6;
                this.e(true);
                this.w();
                return;
            }
            ++n6;
        }
    }

    public final void c(Graphics graphics) {
        if (this.r == 2) {
            graphics.setColor(0xF0FBFF);
            graphics.fillRect(this.c, this.d, this.f, this.g);
            int n2 = this.c + this.m + this.k / 2;
            int n3 = this.d + this.m + this.l / 2;
            int n4 = n2 + this.o % this.n * this.k - ov.c[this.o] / 2;
            int n5 = n3 + this.o / this.n * this.l - ov.d[this.o] / 2;
            graphics.setColor(16177368);
            graphics.fillRect(n4, n5, ov.c[this.o] + 2, ov.d[this.o] + 2);
            oz.a(graphics, n4 - 1, n5, ov.c[this.o] + 2, ov.d[this.o] + 1, 0xFBB5B5, -1);
            cz.b(graphics, 0xF88989, n4 - 2, n5 - 1, ov.c[this.o] + 4, ov.d[this.o] + 3);
            n4 = 0;
            while (n4 < this.q) {
                ov.a(graphics, n4, n2 + n4 % this.n * this.k, n3 + n4 / this.n * this.l, 3);
                ++n4;
            }
            oz.d(graphics, this.c, this.d, this.f, this.g, -1);
            return;
        }
        oz.d(graphics, this.c, this.d, this.f, this.g, 0xF0FBFF);
        int n6 = this.c + this.m + this.k / 2;
        int n7 = this.d + this.m + this.l / 2;
        int n8 = n6 + this.o % this.n * this.k + t.o[this.o] - t.l[this.o] / 2 - 1;
        int n9 = n7 + this.o / this.n * this.l + t.n[this.o] - t.m[this.o] / 2 - 1;
        graphics.setColor(16177368);
        graphics.fillRect(n8, n9, t.l[this.o] + 2, t.m[this.o] + 2);
        oz.a(graphics, n8, n9, t.l[this.o] + 2, t.m[this.o] + 2, 0xFBB5B5, -1);
        cz.b(graphics, 0xF88989, n8 - 1, n9 - 1, t.l[this.o] + 4, t.m[this.o] + 4);
        n8 = 0;
        while (n8 < 28) {
            cz.a(graphics, t.p, t.j[n8], t.k[n8], t.l[n8], t.m[n8], n6 + n8 % this.n * this.k + t.o[n8], n7 + n8 / this.n * this.l + t.n[n8], 3);
            ++n8;
        }
    }
}

