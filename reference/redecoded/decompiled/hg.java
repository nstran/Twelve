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

public final class hg
extends al
implements bf {
    private Image l = null;
    private cu m;
    private cu n;
    private cu o;
    public ll k;
    private d p = bx.d;
    private String[] q;
    private boolean r;
    private String[] s = null;
    private d t;
    private String[] u = new String[]{"\u0110\u1ed3 gi\u00e0nh cho NAM", "\u0110\u1ed3 gi\u00e0nh cho N\u1eee", "Gi\u00e0nh cho c\u1ea3 NAM & N\u1eee"};

    public hg(ll ll2) {
        super(1);
        Object object;
        this.b(241212);
        this.a(this);
        this.a(new ba());
        this.k = ll2;
        this.t = new if(new int[]{0xFF0000, 0xFFFF00});
        try {
            int n2 = mb.a(ll2) + 98;
            object = pa.a();
            this.l = ((pa)object).a(n2, false);
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        if (ll2.h != 2 && ll2.h != go.k.f) {
            this.r = true;
        }
        this.f = 240;
        this.g = 320 - ba.a;
        if (com.mg.sq.a.k == 1) {
            this.f = 320;
            this.g = v.u - ba.a;
        } else if (v.t < 240) {
            this.f = v.t;
            this.g = v.u - ba.a;
        }
        this.f -= 20;
        this.o = new cu(this.f / 2, 10);
        this.m = new cu(10, this.o.b + this.l.getHeight() + 4);
        this.n = new cu(10, this.m.b + bx.d.a() + 5);
        this.q = com.mg.sq.a.a(ll2);
        this.g = this.n.b + 10 - 2 + (bx.c.a() + 2 << 1) + (com.mg.sq.a.g.a() + 2) * this.q.length + (this.r ? bx.c.a() : 0) + 10;
        if (ll2.g != null && !ll2.g.equals("")) {
            this.s = bx.a(ll2.g, this.f - 20);
            this.g += this.s.length * (bx.c.a() + 2);
        }
        this.c = v.t >= this.f ? (v.t - this.f) / 2 : 0;
        this.d = v.u >= this.g ? (v.u - ba.a - this.g) / 2 : 0;
        this.p = ll.a(ll2.m);
        bd bd2 = new bd("B\u1ecf qua", 1000);
        object = this;
        ((am)object).b(bd2, true);
        bd2 = new bd("Nh\u1eb7t", 2000);
        object = this;
        ((am)object).a(bd2, true);
    }

    public final void c(Graphics graphics) {
        pc.a(graphics, this.c, this.d, this.f, this.g, v.aj, true);
        graphics.drawImage(this.l, this.o.a + this.c, this.o.b + this.d, 17);
        if (this.k == null) {
            ct.a("[EquipmentDetailDialog.draw()] Null equipmeny");
            return;
        }
        if (this.k.j > 0 && this.t != null) {
            this.t.a(graphics, "+" + this.k.j, this.o.a + this.c + 16, this.o.b + this.d + 32 - bx.c.a(), 2);
        }
        if (this.k.d != null) {
            int n2 = this.n.a + this.c;
            if (this.k.f > 0) {
                pc.b(graphics, n2, this.m.b + this.d - 4, this.k.f);
            }
            n2 += 15;
            if (this.k.j <= 0) {
                this.p.a(graphics, this.k.d, n2, this.m.b + this.d, 0);
            } else {
                this.p.a(graphics, String.valueOf(this.k.d) + " +" + this.k.j, n2, this.m.b + this.d, 0);
            }
            pc.a(graphics, this.n.a + this.c, this.n.b + this.d, this.f - 10 - 10);
            int n3 = this.d + this.n.b + 10 - 2;
            n2 = this.c + 5;
            pc.a(graphics, n2, n3 + com.mg.sq.a.g.a() / 2);
            if (go.k.G >= this.k.i) {
                bx.c.a(graphics, "Y\u00eau c\u1ea7u c\u1ea5p: " + this.k.i, n2 + 7, n3, 0);
            } else {
                com.mg.sq.a.h.a(graphics, "Y\u00eau c\u1ea7u c\u1ea5p: " + this.k.i, n2 + 7, n3, 0);
            }
            pc.a(graphics, n2, (n3 += bx.c.a() + 2) + com.mg.sq.a.g.a() / 2);
            if (this.k.q > 0) {
                if (this.k.p * 100 / this.k.q >= 30) {
                    bx.c.a(graphics, "\u0110\u1ed9 b\u1ec1n: " + this.k.p + "/" + this.k.q, n2 + 7, n3, 0);
                } else {
                    com.mg.sq.a.h.a(graphics, "\u0110\u1ed9 b\u1ec1n: " + this.k.p + "/" + this.k.q, n2 + 7, n3, 0);
                }
            }
            n3 += bx.c.a() + 2;
            int n4 = 0;
            while (n4 < this.q.length) {
                pc.a(graphics, n2, n3 + bx.c.a() / 2);
                com.mg.sq.a.g.a(graphics, this.q[n4], n2 + 7, n3, 0);
                n3 += com.mg.sq.a.g.a() + 2;
                ++n4;
            }
            if (this.s != null) {
                pc.a(graphics, n2, n3 + bx.c.a() / 2);
                n4 = 0;
                while (n4 < this.s.length) {
                    bx.c.a(graphics, this.s[n4], n2 + 7, n3, 0);
                    n3 += bx.c.a() + 2;
                    ++n4;
                }
            }
            if (this.r) {
                n4 = this.d + this.g - bx.d.a() - 10;
                pc.a(graphics, n2, n4 + com.mg.sq.a.g.a() / 2);
                com.mg.sq.a.h.a(graphics, this.u[this.k.h], n2 + 7, n4, 0);
            }
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 2000: {
                ag.b().a(this.h(), false);
                return;
            }
            case 1000: {
                ag.b().a(this.h(), false);
            }
        }
    }
}

