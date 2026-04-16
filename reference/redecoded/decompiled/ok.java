/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class ok
extends fb
implements bf,
bg,
bn,
bq {
    aw p;
    private ay t;
    fa[] q;
    fa[][] r;
    public boolean s = false;
    private ex u;
    private String[] v;
    private int w = 0;

    public ok() {
        super(110, 4, "Kho Game", false);
        this.a(0, 0, v.t, v.u);
        this.v = bx.a("C\u00e0i \u0111\u1eb7t MI\u1ec4N PH\u00cd v\u00f4 s\u1ed1 tr\u00f2 ch\u01a1i v\u00e0 \u1ee9ng d\u1ee5ng c\u1ef1c k\u1ef3 h\u1ea5p d\u1eabn. Ngo\u00e0i ra, b\u1ea1n c\u00f2n c\u00f3 c\u01a1 h\u1ed9i nh\u1eadn ngay c\u00e1c ph\u1ea7n qu\u00e0 GI\u00c1 TR\u1eca t\u1eeb h\u1ec7 th\u1ed1ng.", v.t, bx.d);
        this.u = new ex("Kho Game", -2);
        this.u.a((v.t - 100) / 2, 10 + bx.d.a() + 10 + 10 + bx.d.a() * this.v.length, 100, 20);
        this.t = new ay(0);
        this.t.a(this.c(), bx.d.a() + 10, this.e(), this.f() - bx.d.a() - 10 - ba.a);
        this.p = new aw();
        this.p.a(this);
        this.p.a(this);
        this.p.e(true);
        this.t.b(this.p);
        this.a(new ba());
        this.a(new gb(-1, 0));
        this.b(new gb(-2, 1));
        this.c(com.mg.sq.a.n);
        this.a(this);
        boolean bl2 = false;
        ok ok2 = this;
        this.w = 0;
        ok2.u.d(true);
        ok2.c(true);
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (bl2 && this.w == 4) {
            this.t.c(true);
        }
    }

    public final void x() {
        this.s = false;
        this.c(true);
    }

    public final void y() {
    }

    public final boolean f(int n2) {
        if (n2 == 97 || n2 == 96) {
            return false;
        }
        if (this.w == 0) {
            if (n2 == 95) {
                ok.v();
                return true;
            }
            return false;
        }
        return this.t.f(n2);
    }

    private static void v() {
        com.mg.sq.a.s().a((String)null, (il)null);
        du.a().n();
    }

    public final boolean c(int n2, int n3) {
        if (this.w == 0) {
            if (this.u.h().b(n2, n3)) {
                this.f(95);
                return true;
            }
        } else {
            return this.t.c(n2, n3);
        }
        return super.c(n2, n3);
    }

    public final boolean e(int n2, int n3) {
        if (this.w != 0) {
            return this.t.e(n2, n3);
        }
        return super.e(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        if (this.w != 0) {
            return this.t.f(n2, n3);
        }
        return super.f(n2, n3);
    }

    public final void n() {
        if (this.w != 0) {
            this.t.n();
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.w == 0) {
            if (this.c) {
                this.a(graphics);
                bx.d.c(true);
                bx.d.a(graphics, "Gi\u1edbi thi\u1ec7u", v.t >>> 1, 10, 1);
                bx.d.c();
                n2 = 10 + (10 + bx.d.a());
                bx.a(graphics, bx.d, this.v, 0, n2, v.t, v.u, 1);
                this.u.a(graphics, this.c(), this.d());
                this.c(false);
                return;
            }
        } else {
            boolean bl2 = this.t.k();
            n2 = bl2 ? 1 : 0;
            if (bl2) {
                this.a(graphics);
                pc.a(graphics, 4, 20, v.t - 8);
                bx.d.c(true);
                bx.d.a(graphics, "Kho Game", this.e() / 2, 2, 1);
                bx.d.c();
            }
            this.t.a(graphics, this.c(), this.d());
        }
    }

    private void a(Graphics graphics) {
        graphics.setColor(v.am);
        graphics.fillRect(this.c(), this.d(), this.e(), this.f());
        graphics.drawImage(pc.d, this.c() + this.e(), this.d() + this.f() - ba.a, 40);
    }

    public final void a(ea[] eaArray) {
        if (eaArray == null) {
            com.mg.sq.a.s().v();
            return;
        }
        this.w = 4;
        this.q = new fa[eaArray.length];
        this.r = new fa[eaArray.length][];
        int n2 = 0;
        while (n2 < eaArray.length) {
            this.q[n2] = new fa(true, eaArray[n2].d(), String.valueOf(eaArray[n2].b()) + "(" + eaArray[n2].e() + ")", eaArray[n2].b(), eaArray[n2].e(), this.p.e());
            ++n2;
        }
        this.p.q();
        this.p.a(this.q);
        com.mg.sq.a.s().v();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a() {
        int n2;
        if (this.q == null) {
            return;
        }
        int n3 = 0;
        int n4 = 0;
        while (n4 < this.q.length) {
            ++n3;
            if (this.q[n4].i && this.r[n4] != null && this.r[n4].length > 0) {
                n3 += this.r[n4].length;
                if (this.r[n4].length < this.q[n4].l) {
                    ++n3;
                }
            }
            ++n4;
        }
        Object object = new Object[n3];
        n3 = 0;
        int n5 = 0;
        while (n5 < this.q.length) {
            object[n3++] = this.q[n5];
            if (this.q[n5].i && this.r[n5] != null && this.r[n5].length > 0) {
                n2 = 0;
                while (n2 < this.r[n5].length) {
                    object[n3++] = this.r[n5][n2];
                    ++n2;
                }
                if (this.r[n5].length < this.q[n5].l) {
                    object[n3++] = new dp("Xem th\u00eam", String.valueOf(this.q[n5].k) + '\u001a' + this.r[n5].length);
                }
            }
            ++n5;
        }
        n5 = this.p.s();
        n2 = this.t.r().b;
        aw aw2 = this.p;
        synchronized (aw2) {
            aq aq2;
            this.p.q();
            this.p.a((Object[])object);
            this.p.k(n5);
            n5 = n2;
            object = this;
            aq aq3 = object.p.o(object.p.s());
            if (object.p.s() < object.p.a() - 1 && (aq2 = object.p.o(object.p.s() + 1)).d() + aq2.f() - n5 > object.p.f()) {
                n5 = aq2.d() + aq2.f() - object.p.f();
            }
            if (aq3.d() + aq3.f() - n5 > object.p.f()) {
                n5 = aq3.d() + aq3.f() - object.p.f();
            }
            object.t.k(n5);
            this.c(true);
            return;
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -1: {
                ok ok2 = this;
                bs bs2 = new bs();
                bs2.a(new gb(0, 2));
                bs2.b(new gb(1, 3));
                br[] brArray = ok2.w == 0 ? new br[]{new br("Kho Game", 6), new br("\u0110\u00f3ng", 5)} : new br[]{new br("\u0110\u00f3ng", 5)};
                if (com.mg.sq.a.m != null) {
                    brArray = com.mg.sq.a.m.a(brArray, brArray.length - 1);
                    brArray = oi.b(brArray, brArray.length - 1);
                }
                bs2.a(brArray);
                int n4 = bs2.e() > bs2.f() ? bs2.e() : bs2.f();
                bs2.a_(-n4, v.u);
                bs2.d(0, v.u - ba.a - bs2.f());
                bs2.a(ok2);
                ok2.a(bs2);
                return;
            }
            case 1: {
                this.t();
                return;
            }
            case 0: {
                this.l.f(95);
                return;
            }
            case 5: {
                this.s = true;
                if (this.b == null) break;
                ((fc)this.b).d(this);
                return;
            }
            default: {
                ((fc)this.b).a();
            }
        }
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        br br2 = (br)object;
        switch (n3) {
            case 5: {
                this.s = true;
                if (this.b == null) break;
                ((fc)this.b).d(this);
                break;
            }
            case 6: {
                ok.v();
                break;
            }
            case 11399: {
                if (com.mg.sq.a.m == null) break;
                com.mg.sq.a.m.b(br2.b());
                break;
            }
            default: {
                if (com.mg.sq.a.m == null) break;
                com.mg.sq.a.m.j(n3);
            }
        }
        this.t();
    }

    private static void a(long l2, int n2) {
        com.mg.sq.a.s().a((String)null, (il)null);
        du.a().a(l2, n2);
    }

    public final void b(aq object, int n2) {
        if (this.p.i(n2) instanceof dp) {
            object = (dp)this.p.i(n2);
            String string = (String)((dp)object).b;
            int n3 = string.indexOf("\u001a");
            long l2 = Long.parseLong(string.substring(0, n3));
            n3 = Integer.parseInt(string.substring(n3 + 1));
            ok.a(l2, n3);
            return;
        }
        object = (fa)this.p.i(n2);
        if (((fa)object).j) {
            n2 = 0;
            while (n2 < this.q.length) {
                if (this.q[n2].equals(object)) {
                    if (this.r[n2] != null) {
                        ((fa)object).i = !((fa)object).i;
                        this.a();
                        return;
                    }
                    ok.a(((fa)object).k, 0);
                }
                ++n2;
            }
            return;
        }
        com.mg.sq.a.s().a((String)null, (il)null);
        du.a().b(((fa)object).k);
    }

    public final void a(aq aq2, int n2) {
    }

    public final void a(aq aq2, int n2, int n3) {
    }

    public final aq a(aw aw2, int n2) {
        if (aw2.i(n2) instanceof dp) {
            return new gl(25);
        }
        return (aq)aw2.i(n2);
    }
}

