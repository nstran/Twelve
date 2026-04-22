/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class gu
extends al
implements bf {
    private dc k;
    private int l = 1;
    private k m;
    private Image n = f.d("/info/increase");
    private Image o = f.d("/info/decrease");
    private int p = 0;
    private int q = 0;
    private int r = 50;
    private boolean s = false;

    public gu(dc object) {
        super(1);
        bd bd2;
        this.k = object;
        object = (lm)((dc)object).k;
        this.a(this);
        this.f = 200;
        this.g = 100;
        this.c = (v.t - this.f) / 2;
        this.d = (v.u - this.g) / 2;
        this.b(241213);
        this.a(new ba());
        this.m = new k(bx.d.a("S\u1ed1 l\u01b0\u1ee3ng:   "), 0, 20, 18);
        this.q = this.p = (int)(((lm)object).h / 1000L);
        if (go.s < ((lm)object).h) {
            bd2 = null;
            object = this;
            ((am)object).a(bd2, true);
        } else {
            bd2 = new bd("Mua", 0);
            object = this;
            ((am)object).a(bd2, true);
        }
        bd2 = new bd("H\u1ee7y", 0);
        object = this;
        ((am)object).b(bd2, true);
    }

    public final void c(Graphics graphics) {
        pc.a(graphics, this.c, this.d, this.f, this.g, v.aj, false);
        int n2 = this.c + 10;
        int n3 = this.d + 10;
        this.k.a(graphics, n2, n3);
        bx.d.c(true);
        bx.d.a(graphics, ((lm)this.k.k).b, n2 + this.k.e() + 5, n3, 0);
        bx.d.c(false);
        n2 = this.c + 10;
        bx.d.b(true);
        bx.d.a(graphics, "S\u1ed1 l\u01b0\u1ee3ng: ", n2, n3 += this.k.f() + 3, 0);
        bx.d.b(false);
        if (this.s) {
            n2 += 55;
        } else {
            pc.b(graphics, n2, this.m.b + (n3 += bx.d.a() + 3), this.m.c, this.m.d, 1070484, 16579764, 14542575);
        }
        bx.d.a(graphics, "" + this.l, n2 + 3, n3 + 2, 0);
        n2 += this.m.c;
        if (this.l < this.r) {
            graphics.drawImage(this.n, n2 + 3, n3 + this.m.d / 2, 36);
        }
        if (this.l > 1) {
            graphics.drawImage(this.o, n2 + 3, n3 + this.m.d / 2, 20);
        }
        if (!this.s) {
            bx.d.a(graphics, " x " + this.p + ".000 KEN = " + this.q + ".000 KEN", n2 += this.o.getWidth() + 3, n3, 0);
            n2 = this.c + 10;
            com.mg.sq.a.g.a(graphics, "Ken c\u1ee7a b\u1ea1n: " + go.s + " KEN", n2, n3 += bx.d.a() + 10, 0);
        }
    }

    protected final void g() {
        super.g();
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 0: {
                ag.b().a(this.h(), false);
                return;
            }
        }
        ag.b().a(this.h(), false);
    }

    public final void c(int n2) {
        switch (n2) {
            case 99: {
                if (this.l >= this.r) {
                    return;
                }
                gu gu2 = this;
                if (gu2.s) {
                    ++this.l;
                    return;
                }
                int n3 = this.l + 1;
                long l2 = (long)n3 * ((lm)this.k.k).h;
                if (l2 > go.s) break;
                this.l = n3;
                this.q = this.p * this.l;
                return;
            }
            case 98: {
                if (this.l <= 1) break;
                --this.l;
                gu gu3 = this;
                if (!gu3.s) break;
                this.q = this.p * this.l;
            }
        }
    }

    public final void a(int n2, int n3) {
        if (this.l < this.r && new k(this.c + 88, this.d + 42, 12, 12).a(n2, n3)) {
            this.c(99);
            return;
        }
        if (this.l > 1 && new k(this.c + 88, this.d + 54, 12, 12).a(n2, n3)) {
            this.c(98);
        }
    }

    public final void e(int n2) {
        this.r = n2;
    }

    public final lm t() {
        return (lm)this.k.k;
    }

    public final int u() {
        return this.l;
    }

    public final void j(boolean bl2) {
        this.s = true;
        this.g = 80;
        this.m.b = 80;
    }
}

