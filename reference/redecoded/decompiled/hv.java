/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class hv
extends al
implements bf {
    private byte k = (byte)113;
    private cu l;
    private boolean m = false;
    private a n = new a(3);
    private String o;
    private int p;
    private String[] q;
    private String[] r;
    private gd s;
    private ex t;
    private ex u;
    private cu v;
    private cu w;
    private String[] x = new String[]{"\u0110\u1ec3 b\u1ea3o v\u1ec7 t\u00e0i kho\u1ea3n, b\u1ea5m \"Kh\u00f3a\" \u0111\u1ec3 ng\u01b0\u1eddi kh\u00e1c kh\u00f4ng th\u1ec3 \u0111\u00e1nh c\u1eafp t\u00e0i kho\u1ea3n c\u1ee7a b\u1ea1n trong tr\u01b0\u1eddng h\u1ee3p b\u1ecb l\u1ed9 m\u1eadt kh\u1ea9u.", "T\u00e0i kho\u1ea3n c\u1ee7a b\u1ea1n \u0111\u00e3 \u0111\u01b0\u1ee3c b\u1ea3o v\u1ec7 theo s\u1ed1 \u0111t tr\u00ean."};
    private String[] y = new String[]{"B\u1ea1n c\u1ea7n x\u00e1c th\u1ef1c s\u1ed1 \u0111t \u0111\u1ec3 \u0111\u01b0\u1ee3c s\u1eed d\u1ee5ng c\u00e1c d\u1ecbch v\u1ee5 KEN ti\u1ec7n l\u1ee3i h\u01a1n.", "T\u00e0i kho\u1ea3n c\u1ee7a b\u1ea1n \u0111\u00e3 x\u00e1c th\u1ef1c v\u1edbi s\u1ed1 \u0111t tr\u00ean."};

    /*
     * WARNING - void declaration
     */
    public hv(String object, boolean bl2, boolean bl3) {
        super(1);
        void var3_5;
        this.m = var3_5;
        this.o = object;
        this.a(this);
        this.l = new cu(this.a() + 10, this.c() + 30);
        int n2 = this.i() - 2 - 2;
        int n3 = this.l.b + 8;
        this.p = bl2 ? 1 : 0;
        this.s = new gd("S\u1ed1 \u0111i\u1ec7n tho\u1ea1i: ");
        this.s.h(4);
        this.s.a(2, n3, n2, this.s.f());
        this.s.a((String)object);
        this.s.b(!bl2);
        this.n.a(this.s);
        this.v = new cu(2, n3 += this.s.f() + 4);
        this.q = bx.a(this.x[bl2 ? 1 : 0], this.i() - 10 - 10);
        n3 += this.q.length * bx.c.a() + 4;
        object = bl2 ? "M\u1edf kh\u00f3a" : "Kh\u00f3a";
        int bd2 = bl2 ? 10 : 11;
        n2 = bx.d.a((String)object) + 30;
        int n4 = bx.d.a() + 2;
        this.t = new ex((String)object, bd2);
        this.t.a(this.f - n2 >> 1, n3, n2, n4);
        this.n.a(this.t);
        this.w = new cu(2, n3 += this.t.f() + 10);
        this.r = bx.a(this.y[var3_5 != false ? 1 : 0], this.i() - 10 - 10);
        this.u = new ex("X\u00e1c th\u1ef1c", 15);
        n2 = bx.d.a("X\u00e1c th\u1ef1c") + 30;
        this.u.a(this.f - n2 >> 1, n3 += this.r.length * bx.c.a() + 4, n2, n4);
        this.k((boolean)var3_5);
        this.b(241216);
        this.a(new ba());
        bd bd3 = new bd("\u0110\u00f3ng", 5);
        object = this;
        ((am)object).b(bd3, true);
        this.d(false);
        ((aq)this.n.b(this.p)).d(true);
    }

    public final void c(Graphics graphics) {
        graphics.setColor(0xFFFFFF);
        graphics.fillRect(this.a(), this.c(), this.i(), this.j());
        graphics.drawImage(pc.d, this.a() + this.i(), this.c() + this.j() - 4, 40);
        pc.a(graphics, this.l.a, this.l.b, this.i() - (this.l.a << 1));
        bx.d.c(true);
        bx.d.a(graphics, "B\u1ea3o M\u1eadt T\u00e0i Kho\u1ea3n", this.a() + this.i() / 2, this.c() + 6, 1);
        bx.d.c(false);
        int n2 = 0;
        while (n2 < this.n.d()) {
            ((aq)this.n.b(n2)).a(graphics, this.a(), this.c());
            ++n2;
        }
        bx.a(graphics, bx.c, this.q, this.v.a + 2, this.v.b, this.i(), this.j(), 0);
        bx.a(graphics, bx.c, this.r, this.w.a + 2, this.w.b, this.i(), this.j(), 0);
        if (this.p == 1) {
            n2 = this.v.b - 4;
            graphics.setColor(7267055);
            graphics.drawRect(this.v.a, n2, this.f - this.v.a - this.v.a - 1, this.t.d() + this.t.f() + 4 + 2 - n2);
            return;
        }
        if (this.p == 2) {
            n2 = this.w.b - 4;
            graphics.setColor(7267055);
            graphics.drawRect(this.w.a, n2, this.f - this.w.a - this.w.a - 1, this.u.d() + this.u.f() + 4 + 2 - n2);
        }
    }

    protected final void g() {
        ((aq)this.n.b(this.p)).n();
    }

    public final void i(boolean bl2) {
        super.i(bl2);
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 0: {
                return;
            }
            case 5: {
                ag.b().a(false);
                return;
            }
            case 11: {
                al al2 = this.s.a() == null ? ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n ch\u01b0a nh\u1eadp s\u1ed1 \u0111i\u1ec7n tho\u1ea1i!", "\u0110\u00f3ng", 13, 1) : ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 ch\u1eafc mu\u1ed1n kh\u00f3a t\u00e0i kho\u1ea3n v\u1edbi s\u1ed1 \u0111t \u0111ang s\u1eed d\u1ee5ng kh\u00f4ng?", "C\u00f3", 12, "Kh\u00f4ng", 13, 1);
                al2.a(this);
                al2.b(this.k);
                ag.b().a(al2, false);
                return;
            }
            case 10: {
                al al3 = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 ch\u1eafc l\u00e0 mu\u1ed1n m\u1edf kh\u00f3a kh\u00f4ng? Ch\u00fa \u00fd khi m\u1edf kh\u00f3a, t\u00e0i kho\u1ea3n c\u1ee7a b\u1ea1n c\u00f3 th\u1ec3 b\u1ecb ng\u01b0\u1eddi kh\u00e1c \u0111\u00e1nh c\u1eafp.", "C\u00f3", 114, "Kh\u00f4ng", 13, 1);
                al3.a(this);
                al3.b(this.k);
                ag.b().a(al3, false);
                return;
            }
            case 15: {
                al al4 = this.s.a() == null ? ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n ch\u01b0a nh\u1eadp s\u1ed1 \u0111i\u1ec7n tho\u1ea1i!", "\u0110\u00f3ng", 13, 1) : ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n c\u00f3 ch\u1eafc mu\u1ed1n x\u00e1c th\u1ef1c t\u00e0i kho\u1ea3n v\u1edbi s\u1ed1 \u0111t \u0111ang s\u1eed d\u1ee5ng kh\u00f4ng?", "C\u00f3", 17, "Kh\u00f4ng", 13, 1);
                al4.a(this);
                al4.b(this.k);
                ag.b().a(al4, false);
                return;
            }
            case 13: {
                ag.b().e(this.k);
                return;
            }
            case 17: {
                com.mg.sq.a.c(new hw(this));
                return;
            }
            case 12: {
                if (this.s.a() == null) break;
                com.mg.sq.a.a(new hx(this));
                return;
            }
            case 114: {
                com.mg.sq.a.b(new hy(this));
                return;
            }
            case 18: {
                com.mg.sq.a.c(new hz(this));
                return;
            }
            default: {
                ag.b().a(this.h(), false);
            }
        }
    }

    public final void d(int n2) {
    }

    public final void c(int n2) {
        if ((aq)this.n.b(this.p) instanceof gd) {
            ((aq)this.n.b(this.p)).f(n2);
        }
        int n3 = this.p;
        switch (n2) {
            case 98: {
                if (this.p >= this.n.d() - 1) break;
                ++this.p;
                break;
            }
            case 99: {
                if (this.p <= 0) break;
                --this.p;
                break;
            }
            case 95: {
                int n4;
                int n5;
                hv hv2;
                aq aq2 = (aq)this.n.b(this.p);
                if (aq2 instanceof ex) {
                    hv2 = this;
                    n5 = -1;
                    n4 = ((ex)aq2).a();
                } else {
                    if (!(aq2 instanceof ey)) break;
                    boolean bl2 = ((ey)aq2).a();
                    hv2 = this;
                    n5 = -1;
                    n4 = bl2 ? 16 : 15;
                }
                hv2.d(n5, n4);
            }
        }
        if (n3 != this.p) {
            Object object = (aq)this.n.b(n3);
            if (object instanceof gd && !((String)(object = ((gd)object).a())).equals(this.o)) {
                ks.a().p((String)object);
                com.mg.sq.a.s().a((String)null, (il)null);
            }
            ((aq)this.n.b(n3)).d(false);
            ((aq)this.n.b(this.p)).d(true);
        }
    }

    public final void a(String string, boolean bl2, boolean n2) {
        this.m = n2;
        this.o = string;
        this.s.a(string);
        this.s.b(!bl2);
        int n3 = this.p;
        this.j(bl2);
        this.k(n2 != 0);
        this.p = n3;
        n2 = 0;
        while (n2 < this.n.d()) {
            aq aq2 = (aq)this.n.b(n2);
            if (n2 == n3) {
                aq2.d(true);
            } else {
                aq2.d(false);
            }
            ++n2;
        }
    }

    public final void j(boolean bl2) {
        this.s.b(!bl2);
        String string = bl2 ? "M\u1edf kh\u00f3a" : "Kh\u00f3a";
        int n2 = bl2 ? 10 : 11;
        int n3 = bx.d.a(string) + 30;
        int n4 = bx.d.a() + 2;
        int n5 = this.s.d() + this.s.f() + 4;
        this.v = new cu(2, n5);
        this.q = bx.a(this.x[bl2 ? 1 : 0], this.i() - 10 - 10);
        this.t = new ex(string, n2);
        this.t.a(this.f - n3 >> 1, n5 += this.q.length * bx.c.a() + 4, n3, n4);
        this.n.a(this.t, 1);
        this.w = new cu(2, n5 += this.t.f() + 10);
        this.k(this.m);
        if (!bl2) {
            ((aq)this.n.b(this.p)).d(false);
            this.p = 0;
        }
        ((aq)this.n.b(this.p)).d(true);
    }

    public final void k(boolean bl2) {
        this.m = bl2;
        this.r = bx.a(this.y[this.m ? 1 : 0], this.i() - 10 - 10);
        int n2 = this.w.b + this.r.length * bx.c.a() + 4;
        this.u.a(this.u.c(), n2, this.u.e(), this.u.f());
        this.u.a(!bl2);
        if (this.u.i()) {
            bl2 = false;
            n2 = 0;
            while (n2 < this.n.d()) {
                if (this.n.b(n2).equals(this.u)) {
                    bl2 = true;
                    break;
                }
                ++n2;
            }
            if (!bl2) {
                this.n.a(this.u);
                return;
            }
        } else {
            this.p = this.p == this.n.d() - 1 ? this.p - 1 : this.p;
            ((aq)this.n.b(this.p)).d(true);
            this.n.b(this.u);
            this.u.d(false);
        }
    }

    public final void a(int n2, int n3) {
        n2 -= this.c;
        n3 -= this.d;
        int n4 = 0;
        while (n4 < this.n.d()) {
            aq aq2 = (aq)this.n.b(n4);
            if (n2 > aq2.c() && n2 < aq2.c() + aq2.e() && n3 > aq2.d() && n3 < aq2.d() + aq2.f()) {
                String string;
                if (this.p == n4) {
                    this.c(95);
                    return;
                }
                aq2 = (aq)this.n.b(this.p);
                if (aq2 instanceof gd && !(string = ((gd)aq2).a()).equals(this.o)) {
                    ks.a().p(string);
                    com.mg.sq.a.s().a((String)null, (il)null);
                }
                ((aq)this.n.b(this.p)).d(false);
                this.p = n4;
                ((aq)this.n.b(this.p)).d(true);
                return;
            }
            ++n4;
        }
    }

    static byte a(hv hv2) {
        return hv2.k;
    }
}

