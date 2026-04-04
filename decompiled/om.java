/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class om
extends fb
implements bj,
bk,
bx {
    private Image o;
    private bc p;
    private String q;
    private int r = -1;
    private boolean s;
    private boolean t;
    private int[] u;
    private ft v;
    private int w;
    private String[] x = new String[]{"B\u1ea1n \u0111ang l\u00e0m g\u00ec?", "B\u1ea1n \u0111ang ngh\u0129 g\u00ec?", "H\u00f4m nay c\u00f3 g\u00ec HOT?", "Ngo\u00e0i tr\u1eddi \u0111ang...?", "B\u1ea1n \u0111ang xem g\u00ec?", "H\u00f4m nay c\u00f3 g\u00ec vui?", "N\u1ebfu c\u00f3 1 \u0111i\u1ec1u \u01b0\u1edbc..."};

    public om(int n2, int n3, String string) {
        super(108, 6, string);
        this.a(0, 0, 100, 100);
        this.p = new bc(0);
        this.p.h(1);
        this.p.a(0, 0, z.r, z.s - be.a);
        this.p.b(this);
        this.a(new be());
        this.a(new ga(-1, 0));
        this.b(new ga(-2, 1));
        this.c(com.mg.sq.a.l);
        this.a(this);
        this.u = null;
    }

    public final void a(String string) {
        if (com.mg.sq.a.k != null) {
            int n2 = string.hashCode();
            if (pa.c(n2)) {
                this.q = string;
                this.j(n2);
                return;
            }
            dv.a().b(string, (short)this.i);
        }
    }

    private void j(int n2) {
        byte[] byArray = pa.d(n2);
        if (byArray != null) {
            ef ef2 = new ef();
            int n3 = p.c(byArray);
            ef2.a(l.a(byArray, 4, n3));
            ef2.f = new byte[byArray.length - 4 - n3];
            System.arraycopy(byArray, n3 + 4, ef2.f, 0, ef2.f.length);
            this.a(ef2);
        }
    }

    public final void a(ef ef2) {
        block5: {
            this.u = null;
            this.o = null;
            this.r = -1;
            this.t = false;
            this.s = false;
            try {
                this.q = ef2.a();
                this.o = f.a(ef2.f);
                this.p.o();
                if (com.mg.sq.a.k != null) {
                    a a2 = com.mg.sq.a.k.o.o;
                    int n2 = 0;
                    while (n2 < a2.d()) {
                        ef ef3 = (ef)a2.b(n2);
                        if (ef3.a().equals(ef2.a())) {
                            this.r = n2;
                            break;
                        }
                        ++n2;
                    }
                    com.mg.sq.a.k.g(true);
                    return;
                }
            }
            catch (OutOfMemoryError outOfMemoryError) {
                this.o = null;
                this.q = null;
                if (com.mg.sq.a.k == null) break block5;
                com.mg.sq.a.k.D();
                System.gc();
                com.mg.sq.a.k.e_();
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.k()) {
            return;
        }
        if (this.o != null) {
            if (this.o.getWidth() < z.r || this.o.getHeight() < z.s) {
                graphics.setColor(0xFFFFFF);
                graphics.fillRect(0, 0, z.r, z.s);
            }
            n n4 = this.p.q();
            if (!this.s && !this.t) {
                graphics.drawImage(this.o, -n4.a, -n4.b, 0);
            } else {
                n3 = this.s && this.t ? 1 : (this.s ? 2 : 3);
                graphics.drawRegion(this.o, 0, 0, this.o.getWidth(), this.o.getHeight(), n3, -n4.a, -n4.b, 0);
            }
            if (this.r > 0) {
                com.mg.sq.a.h.a(graphics, "<*", 0, (z.s - be.a) / 2, 0);
            }
            if (this.r >= 0 && this.r < com.mg.sq.a.k.o.o.d() - 1) {
                com.mg.sq.a.h.a(graphics, "#>", z.r, (z.s - be.a) / 2, 2);
            }
        } else {
            graphics.setColor(0xFFFFFF);
            graphics.fillRect(0, 0, z.r, z.s);
            ca.d.c(true);
            ca.d.a(graphics, "Ch\u1ecdn h\u00ecnh mu\u1ed1n xem!", z.r / 2, z.s / 2 - be.a, 1);
            ca.d.c();
        }
        this.c = false;
    }

    public final boolean f(int n2) {
        if (this.r >= 0) {
            if (n2 == 142 && this.r > 0) {
                --this.r;
                this.a(((ef)com.mg.sq.a.k.o.o.b(this.r)).a());
                return true;
            }
            if (n2 == 135 && this.r < com.mg.sq.a.k.o.o.d() - 1) {
                ++this.r;
                this.a(((ef)com.mg.sq.a.k.o.o.b(this.r)).a());
                return true;
            }
        }
        return super.f(n2);
    }

    public final boolean c(int n2, int n3) {
        if (this.r < 0) {
            return false;
        }
        n n4 = this.p.r();
        n2 -= n4.a;
        int n5 = (z.s - be.a) / 2 - 30;
        if ((n3 -= n4.b) >= n5 && n3 <= n5 + 60) {
            if (n2 < 40) {
                this.f(142);
                return true;
            }
            if (n2 > z.r - 40) {
                this.f(135);
                return true;
            }
        }
        return super.c(n2, n3);
    }

    protected final au y() {
        return this.p;
    }

    public final void u() {
        this.p.c(true);
    }

    public final void x() {
        this.o = null;
    }

    private static void a(String string, String string2, int n2) {
        dv.a().b(string, string2, (short)n2);
        com.mg.sq.a.C();
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 9991: {
                Object object = (hc)com.mg.sq.a.q().d(-989858);
                if (object == null) {
                    om.a("", null, -1);
                    return;
                }
                String string = ((ff)((hc)object).e(1)).r().toLowerCase().trim();
                int n4 = -1;
                if ((object = ((hc)object).e(4)) != null && object instanceof ft) {
                    n4 = ((ft)object).r() + 1;
                }
                if (l.b(string)) {
                    com.mg.sq.a.u("B\u1ea1n ch\u01b0a nh\u1eadp n\u1ed9i dung ME");
                    return;
                }
                om.a(string, this.q, (short)n4);
                this.v = null;
                this.w = 0;
                com.mg.sq.a.q().a(-989858, false);
                return;
            }
            case 9992: {
                this.v = null;
                this.w = 0;
                com.mg.sq.a.q().a(-989858, false);
                return;
            }
            case 9993: {
                hn hn2 = new hn(2);
                hn2.e(this.v.r());
                hn2.f(this.w);
                hn2.w();
                hn2.a(this);
                ak.b().a(hn2, false);
                return;
            }
            case -8882: {
                ak.b().e(999999223);
                return;
            }
            case -8881: {
                hn hn3 = (hn)com.mg.sq.a.q().d(999999223);
                if (this.v == null || hn3 == null || ov.f == null) break;
                this.w = hn3.v();
                this.v.a(ov.f);
                this.v.h(this.w);
                this.v.b(ov.a[this.w], ov.b[this.w], ov.c[this.w], ov.d[this.w]);
                hn3.e(this.w);
                ak.b().e(999999223);
                return;
            }
            case -8883: {
                hn hn4 = (hn)com.mg.sq.a.q().d(999999223);
                if (hn4 != null) {
                    hn4.u();
                    this.v.a((Image)null);
                    this.v.h(-1);
                }
                ak.b().e(999999223);
                return;
            }
            default: {
                if (n3 == -1) {
                    om om2 = this;
                    bv bv2 = new bv();
                    bv2.a(new ga(1, 2));
                    bv2.b(new ga(2, 3));
                    bu[] buArray = new bu("L\u1eadt", 10802);
                    buArray.a(new bu[]{new bu("D\u1ecdc", 10801), new bu("Ngang", 10803), new bu("Th\u00f4ng th\u01b0\u1eddng", 10800)});
                    bu bu2 = new bu("Thao t\u00e1c", 10804);
                    bu2.a(new bu[]{new bu("G\u1eedi Me", 10809), new bu("G\u1eedi ti\u1ebfp", 10805), new bu("Ch\u00e9p n\u1ed9i dung", 10806)});
                    if (om2.u != null && om2.u.length > 0) {
                        bu bu3 = new bu("H\u00ecnh \u0111\u00e3 t\u1ea3i", 10807);
                        bu[] buArray2 = new bu[om2.u.length];
                        int n5 = 0;
                        while (n5 < buArray2.length) {
                            buArray2[n5] = new bu("H\u00ecnh " + (n5 + 1), 10807);
                            ++n5;
                        }
                        bu3.a(buArray2);
                        buArray = new bu[]{buArray, bu3, bu2, new bu("\u0110\u00f3ng", 10808)};
                    } else {
                        buArray = new bu[]{buArray, bu2, new bu("\u0110\u00f3ng", 10808)};
                    }
                    if (com.mg.sq.a.k != null) {
                        buArray = com.mg.sq.a.k.a(buArray, buArray.length - 1);
                        buArray = og.b(buArray, buArray.length - 1);
                    }
                    bv2.a(buArray);
                    int n6 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                    bv2.a_(-n6, z.s);
                    bv2.d(0, z.s - be.a - bv2.f());
                    bv2.a(om2);
                    om2.a(bv2);
                    return;
                }
                if (n3 == -2) {
                    if (com.mg.sq.a.k == null || com.mg.sq.a.k.l() == null) break;
                    fc fc2 = (fc)com.mg.sq.a.k.l();
                    fc2.a();
                    return;
                }
                if (n3 == 2) {
                    this.t();
                    return;
                }
                if (n3 != 1) break;
                this.l.f(95);
            }
        }
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        Object object2 = (bu)object;
        switch (n3) {
            case 10801: {
                this.t = !this.t;
                this.p.c(true);
                break;
            }
            case 10802: {
                break;
            }
            case 10803: {
                this.s = !this.s;
                this.p.c(true);
                break;
            }
            case 10800: {
                this.s = false;
                this.t = false;
                this.p.c(true);
                break;
            }
            case 10804: {
                break;
            }
            case 10805: {
                com.mg.sq.a.d("Nh\u1eadp nick mu\u1ed1n g\u1eedi", this.q);
                break;
            }
            case 10809: {
                Object object3 = this.x[cy.a(this.x.length)];
                object2 = this;
                object3 = com.mg.sq.a.a((String)object3, null, "Xong", 9991, "H\u1ee7y", 9992, 9993, (bj)object2);
                ((aq)object3).b(-989858);
                ((om)object2).v = (ft)((hc)object3).e(4);
                object2 = object3;
                ak.b().a((ap)object2);
                break;
            }
            case 10806: {
                ff.i = "#\u001b" + this.q + "#";
                break;
            }
            case 10807: {
                int n4;
                n3 = ((bu)object2).b().lastIndexOf(32);
                if (n3 < 0 || (n4 = Integer.parseInt(((bu)object2).b().substring(n3 + 1))) > this.u.length) break;
                this.j(this.u[n4 - 1]);
                break;
            }
            case 10808: {
                this.o = null;
                this.q = null;
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.D();
                break;
            }
            case 11399: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.b(((bu)object2).b());
                break;
            }
            default: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.j(n3);
            }
        }
        this.t();
    }

    public final void a() {
        this.r = -1;
        this.p.c(true);
    }

    public final m v() {
        int n2 = z.r;
        int n3 = z.s - be.a;
        if (this.o != null) {
            if (this.o.getHeight() > n3) {
                n3 = this.o.getHeight();
            }
            if (this.o.getWidth() > n2) {
                n2 = this.o.getWidth();
            }
        }
        return new m(n2, n3);
    }

    public final int w() {
        return 10;
    }
}

