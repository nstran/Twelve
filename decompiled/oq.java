/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class oq
extends fb
implements bj,
bk,
bq,
bt {
    private int o = 10;
    private int p = 5;
    private dk[] q;
    private ba r;
    private bc s;
    private int[] t;
    private int[] u = new int[]{10, 70, 190};
    private ga v;
    private ga w;
    private ga x;
    private String y = "";
    private ga z;
    private ny A;

    public oq(dk[] object, ny ny2) {
        super(103, 8, "X\u1ebfp H\u1ea1ng");
        this.A = ny2;
        this.a(this);
        this.a(new be());
        int n2 = z.r;
        int n3 = z.s - be.a;
        this.d(n2);
        this.e(n3);
        this.q = object;
        n2 = this.e() / 10;
        this.u = new int[]{10, (n2 << 1) + 10, 10 + (n2 << 3)};
        this.s = new bc(0);
        this.s.a(this.c(), this.d() + 50, this.e(), this.f() - 60);
        this.s.h(1);
        this.r = new ba();
        this.r.a(this);
        this.r.a(this);
        this.r.e(true);
        this.s.b(this.r);
        dk[] dkArray = object;
        object = this;
        if (((oq)object).q == null) {
            ((oq)object).q = new dk[0];
        }
        Object object2 = new dk[((oq)object).q.length + dkArray.length];
        System.arraycopy(((oq)object).q, 0, object2, 0, ((oq)object).q.length);
        System.arraycopy(dkArray, 0, object2, ((oq)object).q.length, dkArray.length);
        ((oq)object).q = object2;
        object2 = ((oq)object).r;
        synchronized (object2) {
            ((oq)object).r.b(((oq)object).r.r());
            fx fx2 = null;
            int n4 = 0;
            while (n4 < dkArray.length) {
                fx2 = new fx(dkArray[n4]);
                fx2.a(((oq)object).u);
                if (n4 % 2 == 0) {
                    fx2.e(true);
                }
                ((oq)object).r.a((Object)fx2);
                ++n4;
            }
            ((au)object).c(true);
        }
        this.t = new int[]{this.o, 25};
        this.z = new ga(5, 1);
        this.v = new ga(3, 0);
        this.w = new ga(2, 2);
        this.x = new ga(1, 3);
        this.c(com.mg.sq.a.l);
        this.a(this.v);
        this.b(this.z);
    }

    protected final au y() {
        return this;
    }

    public final void u() {
    }

    public final void x() {
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 5: {
                ((fc)this.b).a();
                return;
            }
            case 1: {
                if (this.l != null) {
                    this.t();
                    return;
                }
                this.A.w();
                return;
            }
            case 3: {
                oq oq2 = this;
                bv bv2 = new bv();
                bv2.a(new bu[]{new bu("\u0110\u00f3ng", 8)});
                int n4 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                bv2.a_(-n4, oq2.f() - bv2.f() + n4);
                bv2.d(0, z.s - be.a - bv2.f());
                bv2.a(oq2);
                oq2.a(bv2);
                bv2.c(com.mg.sq.a.l);
                bv2.a(oq2.w);
                bv2.b(oq2.x);
                return;
            }
            case 2: {
                this.l.f(95);
                this.t();
            }
        }
    }

    public final au a(ba object, int n2) {
        if ((object = ((ba)object).i(n2)) instanceof fx) {
            return (fx)object;
        }
        if (object instanceof dr) {
            return new gk(15);
        }
        return null;
    }

    public final void b(au object, int n2) {
        object = this.r.i(n2);
        if (object instanceof fx) {
            this.y = this.q[n2].c;
            object = this;
            bv bv2 = new bv();
            if (com.mg.sq.a.k != null && com.mg.sq.a.m) {
                bv2.a(new bu[]{new bu("Xem ME", 6), new bu("Chi Ti\u1ebft", 7)});
            } else {
                bv2.a(new bu[]{new bu("Chi Ti\u1ebft", 7)});
            }
            int n3 = ((oq)object).r.s();
            au au2 = ((oq)object).r.o(n3);
            n n4 = ((oq)object).s.r();
            int n5 = (z.r - bv2.e()) / 2;
            int n6 = ((oq)object).s.d() + au2.d() - n4.b;
            if (n6 + bv2.f() > z.s - be.a) {
                n6 = z.s - be.a - bv2.f();
            }
            bv2.a_(z.r + bv2.e(), n6);
            bv2.d(n5, n6);
            bv2.a((bk)object);
            bv2.a(((oq)object).w);
            bv2.b(((oq)object).x);
            bv2.c(com.mg.sq.a.l);
            bv2.a_(1);
            ((oq)object).a(bv2);
        }
    }

    public final void a(bv bv2) {
        z.c();
        this.l = bv2;
        this.c(true);
    }

    public final void t() {
        z.c();
        this.l = null;
        this.c(true);
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(z.af);
        graphics.fillRect(this.c(), this.d(), this.e(), this.f());
        ca.d.c(true);
        ca.d.a(graphics, "X\u1ebfp H\u1ea1ng", this.c() + this.e() / 2, this.d() + this.p, 1);
        ca.d.c(false);
        oz.a(graphics, this.t[0] + this.c(), this.t[1] + this.d(), this.e() - 20);
        n2 = this.t[1] + this.p + this.d();
        com.mg.sq.a.g.c(true);
        com.mg.sq.a.g.a(graphics, "H\u1ea0NG", this.u[0] - 5 + this.c(), n2, 0);
        com.mg.sq.a.g.a(graphics, "NH\u00c2N V\u1eacT", this.u[1] + this.c(), n2, 0);
        com.mg.sq.a.g.a(graphics, "C\u1ea4P", this.u[2] + this.c(), n2, 0);
        com.mg.sq.a.g.c(false);
        this.s.c(true);
        this.s.a(graphics, this.c(), this.d());
        oz.d(graphics, this.c(), this.d(), this.e(), this.f(), -1);
    }

    public final void n() {
        this.s.n();
    }

    public final boolean f(int n2) {
        return this.s.f(n2);
    }

    public final boolean c(int n2, int n3) {
        return this.s.c(n2, n3);
    }

    public final boolean e(int n2, int n3) {
        return this.s.e(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        return this.s.f(n2, n3);
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        bu bu2 = (bu)object;
        switch (n3) {
            case 7: {
                kq.a().a(this.y, (byte)103);
                com.mg.sq.a.a(null, null);
                break;
            }
            case 8: {
                this.A.w();
                break;
            }
            case 6: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.a(this.y, 0L);
                break;
            }
            case 11399: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.b(bu2.b());
                break;
            }
            default: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.j(n3);
            }
        }
        this.t();
    }
}

