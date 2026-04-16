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

public final class ic
extends al
implements bf {
    private cu k;
    private k l = null;
    private int m;
    private as n;
    private String[] o = null;
    private cu p;
    private k q;
    private k r;
    private int s = 3;
    private int t = 3;
    private int u = 0;
    private String v;
    private Image w = f.d("/push");
    private int x = 1;
    private int y;
    private a z;

    public ic(int n2, int n3, k k2, as as2, String string, String object) {
        super(1);
        int n4;
        this.v = object;
        object = this;
        this.f = v.t - 20;
        ((am)object).g = bx.d.a() * ((ic)object).s + 20;
        ((al)object).c = v.t - ((am)object).f >> 1;
        ((ic)object).r = new k(35, 20, ((am)object).f - 35, ((am)object).g);
        if (((ic)object).v != null) {
            int n5 = bx.d.a(((ic)object).v) + 20;
            if (n5 > (n4 = ((am)object).f - 30)) {
                n5 = n4;
                com.mg.sq.a.a(((ic)object).v, bx.d, n4);
            }
            ((ic)object).q = new k(5, 0, n5 + 10, 18);
        } else {
            ((ic)object).q = new k(5, 0, ((am)object).f - 30, 18);
        }
        ((ic)object).p = new cu(5, 10);
        k k3 = k2;
        object = this;
        this.l = k3;
        if (k3 != null && ((ic)object).k != null) {
            ((ic)object).m = ((ic)object).k.a - k3.a;
        }
        n4 = n3;
        int n6 = n2;
        object = this;
        this.k = new cu(n6, n4);
        if (((ic)object).l != null) {
            ((ic)object).m = ((ic)object).k.a - ((ic)object).l.a;
            n2 = ((ic)object).k.b - ((ic)object).l.b;
            if (((am)object).g > n2) {
                ((am)object).g = n2;
                ((al)object).d = 0;
                if (((am)object).g < bx.d.a() * ((ic)object).s - 20) {
                    n3 = (((am)object).g - 20) / bx.d.a();
                    Object object2 = object;
                    if (n3 == 0) {
                        n3 = 1;
                    }
                    ((ic)object2).s = n3;
                    ((ic)object2).t = n3;
                }
            } else {
                ((al)object).d = n2 - ((am)object).g;
            }
        }
        Object object3 = as2;
        object = this;
        this.n = object3;
        if (object3 != null) {
            ((ic)object).n.i();
            ((am)object).g = ((at)object3).q() > ((am)object).g ? ((at)object3).q() + 20 : ((am)object).g;
            ((ic)object).r.a = ((ic)object).p.a + ((at)object3).p() + 3;
            ((ic)object).r.c = ((am)object).f - ((ic)object).r.a - 5;
            ((ic)object).p.b = ((am)object).g - ((at)object3).q();
        }
        object3 = string;
        object = this;
        this.o = bx.a((String)object3, ((ic)object).r.c);
        ((ic)object).t = ((ic)object).o.length < ((ic)object).s ? ((ic)object).o.length : ((ic)object).s;
        this.z = com.mg.sq.a.s();
    }

    protected final void g() {
        if (this.n != null) {
            this.n.i();
        }
        ++this.y;
        if (this.y > 0) {
            this.y = 0;
            ++this.x;
            if (this.x > 6) {
                this.x = 0;
                this.y = -6;
            }
        }
    }

    public final void d(int n2, int n3) {
    }

    public final void c(Graphics object) {
        int n2 = 0;
        int n3 = 0;
        Graphics graphics = object;
        object = this;
        n3 = 0 + object.c;
        n2 = 0 + object.d;
        cw.a(graphics, n3, n2 + 9, object.f, object.g, object.m + n3, false, 14808319, 152707);
        if (object.n != null) {
            object.n.a(graphics, n3 + object.p.a, n2 + object.p.b);
        }
        pc.a(graphics, n3 + object.q.a, n2 + object.q.b, object.q.c, object.q.d, 8023552, 16775619);
        if (object.v != null) {
            bx.d.c(true);
            bx.d.a(graphics, object.v, object.q.a + n3 + 3, object.q.b + n2 + 2, 0);
            bx.d.c(false);
        }
        if (object.o != null) {
            bx.a(graphics, bx.c, object.o, object.u, object.t, object.r.a + n3, object.r.b + n2, object.r.c, object.r.d, 0);
        }
        int n4 = object.w.getWidth() >> 1;
        n3 = n3 + object.f - 10;
        n2 = n2 + object.g - 5;
        graphics.drawRegion(object.w, 0, 0, n4, object.w.getHeight(), 0, n3, n2 + object.x, 33);
        graphics.drawRegion(object.w, n4, 0, n4, object.w.getHeight(), 0, n3, n2 + 10, 33);
    }

    public final void c(int n2) {
        n2 = this.u + this.s;
        if (n2 >= this.o.length) {
            this.z.a(false);
            this.z.M();
            return;
        }
        if (n2 >= this.o.length - this.s) {
            this.t = this.o.length - n2;
        }
        this.u = n2;
    }

    public final void a(int n2, int n3) {
        if (new k(this.c + this.p.a, this.d + this.p.b, this.n.p(), this.n.q()).a(n2, n3) || new k(this.c + this.f - 25, this.d + this.g - 25, 25, 25).a(n2, n3)) {
            this.c(95);
        }
    }
}

