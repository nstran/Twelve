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

public final class he
extends ap
implements bj {
    private Image l = null;
    private cx m;
    private cx n;
    private cx o;
    public lj k;
    private d p = ca.d;
    private String[] q;
    private boolean r;
    private String[] s = null;
    private d t;
    private String[] u = new String[]{"\u0110\u1ed3 gi\u00e0nh cho NAM", "\u0110\u1ed3 gi\u00e0nh cho N\u1eee", "Gi\u00e0nh cho c\u1ea3 NAM & N\u1eee"};

    public he(lj object) {
        super(1);
        Object object2;
        this.b(241212);
        this.a(this);
        this.a(new be());
        this.k = object;
        this.t = new ie(new int[]{0xFF0000, 0xFFFF00});
        try {
            this.l = ox.a().a(lz.a((lj)object) + 98);
        }
        catch (Exception exception) {
            object2 = exception;
            exception.printStackTrace();
        }
        if (((lj)object).g != 2 && ((lj)object).g != gr.j.f) {
            this.r = true;
        }
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
        this.o = new cx(this.f / 2, 10);
        this.m = new cx(10, this.o.b + this.l.getHeight() + 4);
        this.n = new cx(10, this.m.b + ca.d.a() + 5);
        this.q = com.mg.sq.a.a((lj)object);
        this.g = this.n.b + 10 - 2 + (ca.c.a() + 2 << 1) + (com.mg.sq.a.g.a() + 2) * this.q.length + (this.r ? ca.c.a() : 0) + 10;
        if (((lj)object).f != null && !((lj)object).f.equals("")) {
            this.s = ca.a(((lj)object).f, this.f - 20);
            this.g += this.s.length * (ca.c.a() + 2);
        }
        this.c = z.r >= this.f ? (z.r - this.f) / 2 : 0;
        this.d = z.s >= this.g ? (z.s - be.a - this.g) / 2 : 0;
        this.p = lj.a(((lj)object).l);
        object2 = new bh("B\u1ecf qua", 1000);
        object = this;
        ((aq)object).b((bd)object2, true);
        object2 = new bh("Nh\u1eb7t", 2000);
        object = this;
        ((aq)object).a((bd)object2, true);
    }

    public final void c(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, true);
        graphics.drawImage(this.l, this.o.a + this.c, this.o.b + this.d, 17);
        if (this.k == null) {
            cw.a("[EquipmentDetailDialog.draw()] Null equipmeny");
            return;
        }
        if (this.k.i > 0 && this.t != null) {
            this.t.a(graphics, "+" + this.k.i, this.o.a + this.c + 16, this.o.b + this.d + 32 - ca.c.a(), 2);
        }
        if (this.k.c != null) {
            int n2 = this.n.a + this.c;
            if (this.k.e > 0) {
                oz.b(graphics, n2, this.m.b + this.d - 4, this.k.e);
            }
            n2 += 15;
            if (this.k.i <= 0) {
                this.p.a(graphics, this.k.c, n2, this.m.b + this.d, 0);
            } else {
                this.p.a(graphics, String.valueOf(this.k.c) + " +" + this.k.i, n2, this.m.b + this.d, 0);
            }
            oz.a(graphics, this.n.a + this.c, this.n.b + this.d, this.f - 10 - 10);
            int n3 = this.d + this.n.b + 10 - 2;
            n2 = this.c + 5;
            oz.a(graphics, n2, n3 + com.mg.sq.a.g.a() / 2);
            if (gr.j.G >= this.k.h) {
                ca.c.a(graphics, "Y\u00eau c\u1ea7u c\u1ea5p: " + this.k.h, n2 + 7, n3, 0);
            } else {
                com.mg.sq.a.h.a(graphics, "Y\u00eau c\u1ea7u c\u1ea5p: " + this.k.h, n2 + 7, n3, 0);
            }
            oz.a(graphics, n2, (n3 += ca.c.a() + 2) + com.mg.sq.a.g.a() / 2);
            if (this.k.o > 0) {
                if (this.k.n * 100 / this.k.o >= 30) {
                    ca.c.a(graphics, "\u0110\u1ed9 b\u1ec1n: " + this.k.n + "/" + this.k.o, n2 + 7, n3, 0);
                } else {
                    com.mg.sq.a.h.a(graphics, "\u0110\u1ed9 b\u1ec1n: " + this.k.n + "/" + this.k.o, n2 + 7, n3, 0);
                }
            }
            n3 += ca.c.a() + 2;
            int n4 = 0;
            while (n4 < this.q.length) {
                oz.a(graphics, n2, n3 + ca.c.a() / 2);
                com.mg.sq.a.g.a(graphics, this.q[n4], n2 + 7, n3, 0);
                n3 += com.mg.sq.a.g.a() + 2;
                ++n4;
            }
            if (this.s != null) {
                oz.a(graphics, n2, n3 + ca.c.a() / 2);
                n4 = 0;
                while (n4 < this.s.length) {
                    ca.c.a(graphics, this.s[n4], n2 + 7, n3, 0);
                    n3 += ca.c.a() + 2;
                    ++n4;
                }
            }
            if (this.r) {
                n4 = this.d + this.g - ca.d.a() - 10;
                oz.a(graphics, n2, n4 + com.mg.sq.a.g.a() / 2);
                com.mg.sq.a.h.a(graphics, this.u[this.k.g], n2 + 7, n4, 0);
            }
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 2000: {
                ak.b().a(this.h(), false);
                return;
            }
            case 1000: {
                ak.b().a(this.h(), false);
            }
        }
    }
}

