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

public final class gs
extends ap
implements bj {
    private dg k;
    private int l = 1;
    private n m;
    private Image n = f.d("/info/increase");
    private Image o = f.d("/info/decrease");
    private int p = 0;
    private int q = 0;
    private int r = 50;
    private boolean s = false;

    public gs(dg object) {
        super(1);
        bh bh2;
        this.k = object;
        object = (lk)((dg)object).k;
        this.a(this);
        this.f = 200;
        this.g = 100;
        this.c = (z.r - this.f) / 2;
        this.d = (z.s - this.g) / 2;
        this.b(241213);
        this.a(new be());
        this.m = new n(ca.d.a("S\u1ed1 l\u01b0\u1ee3ng:   "), 0, 20, 18);
        this.q = this.p = (int)(((lk)object).h / 1000L);
        if (gr.p < ((lk)object).h) {
            bh2 = null;
            object = this;
            ((aq)object).a(bh2, true);
        } else {
            bh2 = new bh("Mua", 0);
            object = this;
            ((aq)object).a(bh2, true);
        }
        bh2 = new bh("H\u1ee7y", 0);
        object = this;
        ((aq)object).b(bh2, true);
    }

    public final void c(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, false);
        int n2 = this.c + 10;
        int n3 = this.d + 10;
        this.k.a(graphics, n2, n3);
        ca.d.c(true);
        ca.d.a(graphics, ((lk)this.k.k).b, n2 + this.k.e() + 5, n3, 0);
        ca.d.c(false);
        n2 = this.c + 10;
        ca.d.b(true);
        ca.d.a(graphics, "S\u1ed1 l\u01b0\u1ee3ng: ", n2, n3 += this.k.f() + 3, 0);
        ca.d.b(false);
        if (this.s) {
            n2 += 55;
        } else {
            oz.b(graphics, n2, this.m.b + (n3 += ca.d.a() + 3), this.m.c, this.m.d, 1070484, 16579764, 14542575);
        }
        ca.d.a(graphics, "" + this.l, n2 + 3, n3 + 2, 0);
        n2 += this.m.c;
        if (this.l < this.r) {
            graphics.drawImage(this.n, n2 + 3, n3 + this.m.d / 2, 36);
        }
        if (this.l > 1) {
            graphics.drawImage(this.o, n2 + 3, n3 + this.m.d / 2, 20);
        }
        if (!this.s) {
            ca.d.a(graphics, " x " + this.p + ".000 KEN" + " = " + this.q + ".000 KEN", n2 += this.o.getWidth() + 3, n3, 0);
            n2 = this.c + 10;
            com.mg.sq.a.g.a(graphics, "Ken c\u1ee7a b\u1ea1n: " + gr.p + "KEN", n2, n3 += ca.d.a() + 10, 0);
        }
    }

    protected final void g() {
        super.g();
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 0: {
                ak.b().a(this.h(), false);
                return;
            }
        }
        ak.b().a(this.h(), false);
    }

    public final void c(int n2) {
        switch (n2) {
            case 99: {
                if (this.l >= this.r) {
                    return;
                }
                gs gs2 = this;
                if (gs2.s) {
                    ++this.l;
                    return;
                }
                int n3 = this.l + 1;
                long l2 = (long)n3 * ((lk)this.k.k).h;
                if (l2 > gr.p) break;
                this.l = n3;
                this.q = this.p * this.l;
                return;
            }
            case 98: {
                if (this.l <= 1) break;
                --this.l;
                gs gs3 = this;
                if (!gs3.s) break;
                this.q = this.p * this.l;
            }
        }
    }

    public final void a(int n2, int n3) {
        if (this.l < this.r && new n(this.c + 88, this.d + 42, 12, 12).a(n2, n3)) {
            this.c(99);
            return;
        }
        if (this.l > 1 && new n(this.c + 88, this.d + 54, 12, 12).a(n2, n3)) {
            this.c(98);
        }
    }

    public final void e(int n2) {
        this.r = n2;
    }

    public final lk t() {
        return (lk)this.k.k;
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

