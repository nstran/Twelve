/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class hk
extends hc
implements aj,
bj,
bt {
    private bc m;
    private ba n;
    private bv o;
    private bd p;
    private bd q;
    private bd r;
    private String s;

    public hk(String[] object) {
        this.b(241218);
        this.a(this);
        this.a(new be());
        int n2 = this.f - 10 - 10;
        this.g -= be.a;
        bf bf2 = new bf("Danh s\u00e1ch s\u1ed1 \u0111i\u1ec7n tho\u1ea1i c\u00f3 th\u1ec3 n\u1ea1p KEN", n2, ca.d);
        bf2.a_(10, 10);
        this.a(bf2);
        int n3 = 10 + (bf2.f() + 6);
        ff ff2 = new ff("", 20, 4);
        ff2.d(true);
        ff2.a(10, n3, n2 / 2, 20);
        this.a(ff2);
        int n4 = ca.d.a("Th\u00eam") + 30;
        ex ex2 = new ex("Th\u00eam", 1);
        ex2.a(ff2.c() + ff2.e() + 10, n3, n4, 20);
        this.a(ex2);
        this.m = new bc(1);
        n2 = n2 - 3 - 3;
        this.m.a(this.f - n2 >> 1, n3 += ex2.f() + 10 + 3, n2, this.g - n3 - 10);
        this.m.e(false);
        this.n = new ba();
        this.n.a(this);
        this.m.b(this.n);
        this.a((String[])object);
        this.a(this.m);
        this.m.d(false);
        this.m.f();
        this.a(this.c, this.d, this.f, this.g);
        this.p = new ga(2, 0);
        this.r = new ga(3, 2);
        this.q = new ga(0, 3);
        this.a(com.mg.sq.a.l);
        bd bd2 = this.p;
        object = this;
        object.a(bd2, true);
        bd2 = this.q;
        object = this;
        object.b(bd2, true);
        this.f(1);
    }

    public final void d(int n2, int n3) {
        if (this.h(n3)) {
            this.t();
            return;
        }
        switch (n3) {
            case 1: {
                Object object = (ff)this.k.b(1);
                String string = ((ff)object).r();
                if (string == null || string.equals("")) {
                    return;
                }
                ba ba2 = (ba)((bc)this.k.b(3)).w();
                ((ff)object).c("");
                int n4 = 0;
                while (n4 < ba2.a()) {
                    object = ((bf)ba2.o(n4)).a();
                    if (((r)object).i().equals(string)) {
                        return;
                    }
                    ++n4;
                }
                dv.a().f(string);
                com.mg.sq.a.a(null, null);
                return;
            }
            case 4: {
                dv.a().g(this.s);
                this.s = null;
                com.mg.sq.a.a(null, null);
                return;
            }
            case 0: {
                if (this.o != null) {
                    this.t();
                    return;
                }
                ak.b().a(this.h(), false);
                return;
            }
            case 2: {
                hk hk2 = this;
                bv bv2 = new bv();
                hk hk3 = hk2;
                if (hk3.l == 3) {
                    bv2.a(new bu("B\u1ecf ra", 111));
                }
                bv2.a(new bu("Th\u00eam", 113));
                bv2.a(new bu("\u0110\u00f3ng", 112));
                int n5 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                bv2.a_(-n5, hk2.j() - bv2.f() + n5);
                bv2.d(0, z.s - be.a - bv2.f());
                bv2.a(hk2);
                hk2.a(bv2);
                bd bd2 = hk2.q;
                hk hk4 = hk2;
                ((aq)hk4).b(bd2, true);
                bd2 = hk2.r;
                hk4 = hk2;
                ((aq)hk4).a(bd2, true);
                bv2.c(com.mg.sq.a.l);
                return;
            }
            case 3: {
                if (this.o == null) break;
                this.h(this.o.r().c());
            }
        }
    }

    protected final void a(Graphics graphics) {
        super.a(graphics);
        if (this.m != null) {
            int n2 = 7070703;
            if (this.m.m()) {
                n2 = 0xEE5C5C;
                n n3 = this.m.q();
                au au2 = this.n.u();
                if (au2 != null) {
                    graphics.setColor(10223465);
                    graphics.fillRect(this.m.c() - n3.a + this.c - 2, au2.d() - n3.b + this.m.d() + this.d - 2, this.m.e() + 4, au2.f());
                }
            }
            oz.a(graphics, this.m.c() + this.c - 3, this.m.d() + this.d - 6, this.m.e() + 3 + 3, this.m.f() + 3 + 3, n2, -1);
        }
    }

    public final void c(Graphics graphics) {
        super.c(graphics);
        if (this.o != null) {
            this.o.a(graphics, 0, 0);
        }
    }

    private void a(bv bv2) {
        this.o = bv2;
        this.e(true);
    }

    private void t() {
        this.o = null;
        bd bd2 = this.q;
        hk hk2 = this;
        ((aq)hk2).b(bd2, true);
        bd2 = this.p;
        hk2 = this;
        ((aq)hk2).a(bd2, true);
        this.e(true);
    }

    protected final void g() {
        super.g();
        if (this.o != null) {
            this.o.n();
            this.e(true);
        }
    }

    public final void a(ai ai2) {
    }

    public final void b(au object, int n2) {
        object = ((ba)object).o(n2);
        Object object2 = this.m.q();
        object2 = object = new n(this.m.c() - ((n)object2).a + this.c, ((au)object).d() - ((n)object2).b + this.m.d() + this.d, this.m.e(), ((au)object).f());
        object = this;
        bv bv2 = new bv();
        bv2.a(new bu("B\u1ecf ra", 111));
        int n3 = ((n)object2).a + ((ap)object).c + (((n)object2).c - bv2.e()) / 2;
        int n4 = ((n)object2).b + ((ap)object).d + ((n)object2).d;
        if (n4 + bv2.f() > z.s - be.a) {
            n4 = z.s - be.a - bv2.f();
        }
        bv2.a_(((ap)object).c + ((aq)object).f + bv2.e(), n4);
        bv2.d(n3 < ((ap)object).c ? ((ap)object).c : (n3 + bv2.e() > ((ap)object).c + ((aq)object).f ? ((ap)object).c + ((aq)object).f - bv2.e() : n3), n4);
        bv2.a((bj)object);
        bd bd2 = ((hk)object).q;
        Object object3 = object;
        ((aq)object3).b(bd2, true);
        bd2 = ((hk)object).r;
        object3 = object;
        ((aq)object3).a(bd2, true);
        bv2.a_(1);
        super.a(bv2);
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }

    private boolean h(int n2) {
        switch (n2) {
            case 111: {
                r r2 = ((bf)this.n.u()).a();
                this.s = r2.i();
                this.e(true);
                this.d(-1, 4);
                return true;
            }
            case 112: {
                ak.b().a(this.h(), false);
                return true;
            }
            case 113: {
                this.f(1);
                this.e(true);
                return true;
            }
        }
        return false;
    }

    public final void c(int n2) {
        if (this.o != null) {
            boolean bl2;
            int n3 = n2;
            bd[] bdArray = this.o.a();
            hk hk2 = this;
            if (n3 == 94 && bdArray[0] != null && bdArray[0].b()) {
                if (hk2.i != null) {
                    hk2.i.d(-1, bdArray[0].a());
                }
                bl2 = true;
            } else if (n3 == 95 && bdArray[1] != null && bdArray[1].b()) {
                if (hk2.i != null) {
                    hk2.i.d(-1, bdArray[1].a());
                }
                bl2 = true;
            } else if (n3 == 93 && bdArray[2] != null && bdArray[2].b()) {
                if (hk2.i != null) {
                    hk2.i.d(-1, bdArray[2].a());
                }
                bl2 = true;
            } else {
                bl2 = false;
            }
            if (bl2) {
                z.c();
                return;
            }
            if (this.o.f(n2)) {
                return;
            }
            this.t();
            return;
        }
        super.c(n2);
    }

    public final void a(int n2, int n3) {
        if (this.o != null) {
            boolean bl2;
            block6: {
                int n4 = n3;
                int n5 = n2;
                bd[] bdArray = this.o.a();
                hk hk2 = this;
                int n6 = 0;
                while (n6 < bdArray.length) {
                    if (bdArray[n6] != null && bdArray[n6].a(n5, n4)) {
                        if (hk2.i != null) {
                            hk2.i.d(-1, bdArray[n6].a());
                        }
                        bl2 = true;
                        break block6;
                    }
                    ++n6;
                }
                bl2 = false;
            }
            if (bl2) {
                return;
            }
            if (this.o.c(n2, n3)) {
                return;
            }
            this.t();
            return;
        }
        super.a(n2, n3);
    }

    public final void a(String[] stringArray) {
        this.n.q();
        int n2 = 0;
        while (n2 < stringArray.length) {
            this.n.a(stringArray[n2]);
            ++n2;
        }
    }
}

