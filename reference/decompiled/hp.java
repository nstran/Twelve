/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class hp
extends hr
implements bq,
bt,
ik {
    private nq[] k;
    private nq o = null;
    private int p;
    private byte q = 0;
    private boolean r = false;
    private n s;
    private bc t;
    private ba u;
    private bd v;
    private bd w;
    private bd x;
    private bd y;
    private bd z;
    private Object A = null;

    public hp() {
        this.a(this);
        this.b(241204);
        this.a(new be());
        int n2 = z.r;
        int n3 = z.s - be.a;
        this.a(0, 0, n2, n3);
        this.t = new bc();
        this.t.h(1);
        hp hp2 = this;
        this.x = new ga(9, 0);
        hp2.v = new ga(7, 2);
        hp2.w = new ga(8, 3);
        hp2.z = new bh("Nh\u1eadn", 1112);
        hp2.y = new bh("C.Ti\u1ebft", 1113);
        bd bd2 = hp2.x;
        hp hp3 = hp2;
        hp3.a(bd2, true);
        bd2 = hp2.w;
        hp3 = hp2;
        hp3.b(bd2, true);
        hp2.a(hp2.q);
        if (!cv.a.c(140)) {
            ob.h(140);
            gp.m = false;
        }
    }

    public final void a(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, z.r, z.s);
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, true);
    }

    public final void b(Graphics object) {
        switch (this.q) {
            case 0: {
                ca.d.c(true);
                ca.d.a((Graphics)object, "Nhi\u1ec7m V\u1ee5", this.c + 15, this.d + this.t.d() - 20, 0);
                ca.d.c(false);
                oz.a(object, this.c + 10, this.d + this.t.d() - 3, this.f - 20);
                if (this.t != null) {
                    this.t.a((Graphics)object, this.c, this.d);
                }
                this.t.c(true);
                if (this.k != null && this.k.length > 0) break;
                ca.a(object, ca.d, "Ch\u01b0a c\u00f3 nhi\u1ec7m v\u1ee5 m\u1edbi.", this.c + 5, this.d + 30, this.f - 10, this.g - 50, 0);
                return;
            }
            case 1: {
                try {
                    ca.d.c(true);
                    ca.d.a((Graphics)object, "N\u1ed9i Dung", this.c + this.f / 2, this.d + 5, 1);
                    ca.d.c(false);
                    oz.a(object, this.c + 10, this.d + 27, this.f - 20);
                    this.t.a((Graphics)object, 0, 0);
                    this.t.c(true);
                    return;
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                    return;
                }
            }
            case 2: {
                ca.d.c(true);
                ca.d.a((Graphics)object, "N\u1ed9i Dung", this.c + this.f / 2, this.d + 5, 1);
                ca.d.c(false);
                oz.a(object, this.c + 10, this.d + 27, this.f - 20);
                this.t.a((Graphics)object, 0, 0);
                this.t.c(true);
            }
        }
    }

    public final void u() {
        if (this.q != 0) {
            this.t.n();
            return;
        }
        if (this.t != null) {
            this.t.n();
        }
    }

    public final void a(byte by2) {
        this.u = null;
        this.q = by2;
        switch (this.q) {
            case 0: {
                this.p = 0;
                this.t = new bc();
                this.t.h(1);
                this.s = new n(this.c + 4, this.d + 34, this.f - 8, this.g - 44);
                this.t.a(this.s);
                this.u = new ba();
                this.u.a(this);
                this.u.a(this);
                this.u.e(true);
                this.t.b(this.u);
                gb gb2 = null;
                if (this.k != null && this.k.length > 0) {
                    int n2 = 0;
                    while (n2 < this.k.length) {
                        gb2 = new gb(String.valueOf(n2 + 1) + ". " + this.k[n2].b, this.t.e() - 20, ca.d);
                        gb2.i(10);
                        gb2.j(6);
                        gb2.d(this.t.e());
                        gb2.b(true);
                        this.u.a((Object)gb2);
                        ++n2;
                    }
                    this.n[1] = this.y;
                    this.a(this.y);
                } else {
                    this.a(com.mg.sq.a.l);
                }
                this.u.k(this.p);
                return;
            }
            case 1: {
                this.s = new n(this.c + 5, this.d + 34, this.f - 10, this.g - 44);
                this.t = new bc();
                this.t.h(1);
                this.t.a(this.s);
                this.u = new ba();
                this.u.f(true);
                this.u.a(this);
                this.t.b(this.u);
                gb gb3 = new gb(this.o.c, this.s.c - 10, ca.c);
                this.u.a((Object)gb3);
                if (this.o.d != null) {
                    int n3 = 0;
                    while (n3 < this.o.d.length) {
                        gb3 = new gb("- " + this.o.d[n3].a, 10, this.s.c - 10, com.mg.sq.a.g, 2);
                        gb3.j(6);
                        this.u.a((Object)gb3);
                        ++n3;
                    }
                }
                this.a((bd)null);
                return;
            }
            case 2: {
                this.s = new n(this.c + 5, this.d + 34, this.f - 10, this.g - 44);
                this.t = new bc();
                this.t.h(1);
                this.t.a(this.s);
                this.u = new ba();
                this.u.a(this);
                this.u.f(true);
                this.t.b(this.u);
                gb gb4 = new gb(this.o.c, this.s.c - 10, ca.c);
                this.u.a((Object)gb4);
                this.a(this.z);
            }
        }
    }

    public final void e(int n2) {
        switch (n2) {
            case 2: {
                ak.b().a(false);
                return;
            }
            case 3: {
                kq.a().o(this.k[this.p].a);
                com.mg.sq.a.a(null, this);
                return;
            }
            case 8: {
                if (this.l == null) {
                    if (this.q == 2) {
                        this.v();
                        this.z();
                        return;
                    }
                    ak.b().a(this.h(), false);
                    return;
                }
                this.z();
                return;
            }
            case 7: {
                this.l.f(95);
                return;
            }
            case 9: {
                bu[] buArray;
                hp hp2 = this;
                switch (hp2.q) {
                    case 0: {
                        if (hp2.k != null && hp2.k.length > 0) {
                            buArray = new bu[]{new bu("Chi Ti\u1ebft", 1113), new bu("Nh\u1eadn", 1112), new bu("\u0110\u00f3ng", 1114)};
                            break;
                        }
                        buArray = new bu[]{new bu("\u0110\u00f3ng", 1114)};
                        break;
                    }
                    case 1: {
                        buArray = new bu[]{new bu("H\u1ee7y nhi\u1ec7m v\u1ee5", 1115), new bu("\u0110\u00f3ng", 1114)};
                        break;
                    }
                    default: {
                        buArray = new bu[]{new bu("Nh\u1eadn", 1112), new bu("Danh S\u00e1ch Nhi\u1ec7m V\u1ee5", 1116), new bu("\u0110\u00f3ng", 1114)};
                    }
                }
                hp2.a(buArray, hp2.v, (bd)new bh("", hp2.v.a()), hp2.w);
                return;
            }
            case 11: {
                ak.b().a(false);
                kq.a().n(this.o.a);
                com.mg.sq.a.a(null, this);
                if (this.A == null || !(this.A instanceof ok)) break;
                ((ok)this.A).v();
                ns.b();
                ns.c();
                return;
            }
            case 13: {
                ak.b().e(-445566);
                this.z();
                ak.b().a(this.h(), false);
                return;
            }
            case 12: {
                kq.a().m(this.o.a);
                ak.b().e(-445566);
                this.z();
                com.mg.sq.a.a(null, this);
            }
        }
    }

    public final void a(nq[] nqArray) {
        this.k = nqArray;
        if (this.q == 0) {
            if (nqArray == null || nqArray.length <= 0) {
                this.a(com.mg.sq.a.l);
                return;
            }
            this.a(this.y);
        }
    }

    public final void f(int n2) {
        switch (n2) {
            case 98: 
            case 99: {
                if (this.q == 0) {
                    if (this.u == null) break;
                    this.u.f(n2);
                    this.p = this.u.s();
                    return;
                }
                this.t.f(n2);
            }
        }
    }

    public final void t() {
        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "Server b\u1eadn! b\u1ea1n c\u00f3 mu\u1ed1n th\u1eed l\u1ea1i kh\u00f4ng?", "C\u00f3", 3, "Kh\u00f4ng", 2, 1);
        ap2.a(new be());
        ap2.a(this);
        ak.b().a(ap2, false);
    }

    public final void a(nq nq2) {
        this.o = nq2;
    }

    public final void f(int n2, int n3) {
        if (this.q == 0) {
            this.t.c(n2, n3);
        }
    }

    public final void e(int n2, int n3) {
        if (this.t != null) {
            this.t.j(-n3);
        }
    }

    public final boolean g(int n2) {
        switch (n2) {
            case 1112: {
                if (this.q == 0) {
                    kq.a().o(this.k[this.p].a);
                    kq.a().m(this.k[this.p].a);
                } else {
                    kq.a().m(this.o.a);
                }
                com.mg.sq.a.a(null, this);
                if (this.A != null && this.A instanceof ok) {
                    ((ok)this.A).v();
                }
                return true;
            }
            case 1113: {
                if (this.k == null || this.k.length <= 0) {
                    this.z();
                    return true;
                }
                kq.a().o(this.k[this.p].a);
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 1114: {
                if (this.q == 2) {
                    this.v();
                    this.z();
                    return true;
                }
                ak.b().a(this.h(), false);
                return true;
            }
            case 1115: {
                hp hp2 = this;
                ap ap2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 ch\u1eafc mu\u1ed1n h\u1ee7y nhi\u1ec7m v\u1ee5 n\u00e0y kh\u00f4ng?", "C\u00f3", 11, "Kh\u00f4ng", 2, 1);
                ap2.a(new be());
                ap2.a(hp2);
                ak.b().a(ap2, false);
                return true;
            }
            case 1116: {
                this.a((byte)0);
                return true;
            }
        }
        return false;
    }

    private void v() {
        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "Ch\u01b0a nh\u1eadn nhi\u1ec7m v\u1ee5. B\u1ea1n mu\u1ed1n nh\u1eadn nhi\u1ec7m v\u1ee5 n\u00e0y kh\u00f4ng?", "C\u00f3", 12, "Kh\u00f4ng", 13, 1);
        ap2.a(this);
        ap2.b(-445566);
        ak.b().a(ap2, false);
    }

    public final void a(Object object) {
        this.A = object;
    }

    public final au a(ba object, int n2) {
        if ((object = ((ba)object).i(n2)) instanceof gb) {
            return (gb)object;
        }
        return null;
    }

    public final void b(au object, int n2) {
        object = this.u.i(n2);
        if (object instanceof gb && this.q == 0) {
            this.g(1113);
        }
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
        this.p = n3;
    }
}

