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

public final class gv
extends ap
implements bj {
    private Image[] k = null;
    private cx l;
    private cx m;
    private cx[] n;
    private lo[] o;
    private d p = ca.d;
    private String[][] q;
    private int r = 0;
    private long s = 0L;
    private String t;

    public gv(lo[] object) {
        super(1);
        this.b(241212);
        this.a(this);
        this.a(new be());
        this.f = 240;
        this.g = 320 - be.a;
        if (com.mg.sq.a.i == 1) {
            this.f = 320;
            this.g = z.s - be.a;
        } else if (z.r < 240) {
            this.f = z.r;
            this.g = z.s - be.a;
        }
        this.f -= 20;
        int n2 = this.f - 32 * ((lo[])object).length;
        n2 /= ((lo[])object).length + 1;
        this.o = object;
        this.k = new Image[((lo[])object).length];
        this.n = new cx[((lo[])object).length];
        this.q = new String[((lo[])object).length][];
        int n3 = 0;
        try {
            int n4 = 0;
            while (n4 < ((lo[])object).length) {
                lj lj2 = (lj)object[n4].e;
                this.k[n4] = ox.a().a(lz.a(lj2) + 98);
                this.n[n4] = new cx(n4 * (n2 + 32) + n2, 10);
                this.q[n4] = com.mg.sq.a.a(lj2);
                if (n3 < this.q[n4].length) {
                    n3 = this.q[n4].length;
                }
                this.s += (long)object[n4].d;
                ++n4;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.l = new cx(this.f / 2, this.n[0].b + this.k[0].getHeight() + 4);
        this.m = new cx(10, this.l.b + ca.d.a() + 5);
        this.g = this.m.b + 10 - 2 + (ca.c.a() + 2) + (ca.c.a() + 2) * (n3 += 2);
        this.c = z.r >= this.f ? (z.r - this.f) / 2 : 0;
        this.d = z.s >= this.g ? (z.s - be.a - this.g) / 2 : 0;
        this.p = lj.a(((lj)object[this.r].e).l);
        this.t = "T\u1ed5ng ti\u1ec1n: " + com.mg.sq.a.b(this.s);
        bh bh2 = new bh("B\u1ecf qua", 1000);
        object = this;
        object.b(bh2, true);
        bh2 = new bh("Nh\u1eb7t", 2000);
        object = this;
        object.a(bh2, true);
    }

    public final void c(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, true);
        int n2 = 0;
        while (n2 < this.k.length) {
            graphics.drawImage(this.k[n2], this.n[n2].a + this.c, this.n[n2].b + this.d, 20);
            ++n2;
        }
        oz.e(graphics, this.n[this.r].a + this.c, this.n[this.r].b + this.d, 32, 32, 0);
        lj lj2 = (lj)this.o[this.r].e;
        if (lj2.c != null) {
            this.p.a(graphics, lj2.c, this.l.a + this.c, this.l.b + this.d, 1);
            oz.a(graphics, this.m.a + this.c, this.m.b + this.d, this.f - 10 - 10);
            int n3 = this.d + this.m.b + 10 - 2;
            int n4 = this.c + 5;
            oz.a(graphics, n4, n3 + com.mg.sq.a.g.a() / 2);
            if (gr.j.G >= lj2.h) {
                ca.d.a(graphics, "Y\u00eau c\u1ea7u c\u1ea5p: " + lj2.h, n4 + 7, n3, 0);
            } else {
                com.mg.sq.a.h.a(graphics, "Y\u00eau c\u1ea7u c\u1ea5p: " + lj2.h, n4 + 7, n3, 0);
            }
            n3 += ca.d.a() + 2;
            if (this.q[this.r] != null) {
                int n5 = 0;
                while (n5 < this.q[this.r].length) {
                    oz.a(graphics, n4, n3 + ca.c.a() / 2);
                    ca.d.a(graphics, this.q[this.r][n5], n4 + 7, n3, 0);
                    n3 += ca.d.a() + 2;
                    ++n5;
                }
            }
            oz.a(graphics, this.m.a + this.c, n3 += 4, this.f - 10 - 10);
            ca.d.c(true);
            ca.d.a(graphics, this.t, n4 + 7, n3 += 8, 0);
            ca.d.c(false);
        }
    }

    public final void c(int n2) {
        switch (n2) {
            case 96: {
                this.r = this.r == this.o.length - 1 ? 0 : this.r + 1;
                this.p = lj.a(((lj)this.o[this.r].e).l);
                return;
            }
            case 97: {
                this.r = this.r == 0 ? this.o.length - 1 : this.r - 1;
                this.p = lj.a(((lj)this.o[this.r].e).l);
            }
        }
    }

    public final void d(int n2, int n3) {
        ak.b().a(this.h(), false);
    }
}

