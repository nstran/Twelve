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

public final class ia
extends ap
implements bj {
    private cx k;
    private n l = null;
    private int m;
    private aw n;
    private String[] o = null;
    private cx p;
    private n q;
    private n r;
    private int s = 3;
    private int t = 3;
    private int u = 0;
    private String v;
    private Image w = f.d("/push");
    private int x = 1;
    private int y;

    public ia(int n2, int n3, n n4, aw aw2, String string, String object) {
        super(1);
        int n5;
        this.v = object;
        object = this;
        this.f = z.r - 20;
        ((aq)object).g = ca.d.a() * ((ia)object).s + 20;
        ((ap)object).c = z.r - ((aq)object).f >> 1;
        ((ia)object).r = new n(35, 20, ((aq)object).f - 35, ((aq)object).g);
        if (((ia)object).v != null) {
            int n6 = ca.d.a(((ia)object).v) + 20;
            if (n6 > (n5 = ((aq)object).f - 30)) {
                n6 = n5;
                com.mg.sq.a.a(((ia)object).v, ca.d, n6);
            }
            ((ia)object).q = new n(5, 0, n6 + 10, 18);
        } else {
            ((ia)object).q = new n(5, 0, ((aq)object).f - 30, 18);
        }
        ((ia)object).p = new cx(5, 10);
        n n7 = n4;
        object = this;
        this.l = n7;
        if (n7 != null && ((ia)object).k != null) {
            ((ia)object).m = ((ia)object).k.a - n7.a;
        }
        n5 = n3;
        int n8 = n2;
        object = this;
        this.k = new cx(n8, n5);
        if (((ia)object).l != null) {
            ((ia)object).m = ((ia)object).k.a - ((ia)object).l.a;
            n2 = ((ia)object).k.b - ((ia)object).l.b;
            if (((aq)object).g > n2) {
                ((aq)object).g = n2;
                ((ap)object).d = 0;
                if (((aq)object).g < ca.d.a() * ((ia)object).s - 20) {
                    n3 = (((aq)object).g - 20) / ca.d.a();
                    Object object2 = object;
                    if (n3 == 0) {
                        n3 = 1;
                    }
                    ((ia)object2).s = n3;
                    ((ia)object2).t = n3;
                }
            } else {
                ((ap)object).d = n2 - ((aq)object).g;
            }
        }
        Object object3 = aw2;
        object = this;
        this.n = object3;
        if (object3 != null) {
            ((ia)object).n.i();
            ((aq)object).g = ((ax)object3).q() > ((aq)object).g ? ((ax)object3).q() + 20 : ((aq)object).g;
            ((ia)object).r.a = ((ia)object).p.a + ((ax)object3).p() + 3;
            ((ia)object).r.c = ((aq)object).f - ((ia)object).r.a - 5;
            ((ia)object).p.b = ((aq)object).g - ((ax)object3).q();
        }
        object3 = string;
        object = this;
        this.o = ca.a((String)object3, ((ia)object).r.c);
        ((ia)object).t = ((ia)object).o.length < ((ia)object).s ? ((ia)object).o.length : ((ia)object).s;
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
        cz.a(graphics, n3, n2 + 9, object.f, object.g, object.m + n3, false, 14808319, 152707);
        if (object.n != null) {
            object.n.a(graphics, n3 + object.p.a, n2 + object.p.b);
        }
        oz.a(graphics, n3 + object.q.a, n2 + object.q.b, object.q.c, object.q.d, 8023552, 16775619);
        if (object.v != null) {
            ca.d.c(true);
            ca.d.a(graphics, object.v, object.q.a + n3 + 3, object.q.b + n2 + 2, 0);
            ca.d.c(false);
        }
        if (object.o != null) {
            ca.a(graphics, ca.c, object.o, object.u, object.t, object.r.a + n3, object.r.b + n2, object.r.c, object.r.d, 0);
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
            ak.b().a(false);
            com.mg.sq.a.L();
            return;
        }
        if (n2 >= this.o.length - this.s) {
            this.t = this.o.length - n2;
        }
        this.u = n2;
    }

    public final void a(int n2, int n3) {
        if (new n(this.c + this.p.a, this.d + this.p.b, this.n.p(), this.n.q()).a(n2, n3) || new n(this.c + this.f - 25, this.d + this.g - 25, 25, 25).a(n2, n3)) {
            this.c(95);
        }
    }
}

