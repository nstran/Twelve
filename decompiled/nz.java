/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.MGMIDlet;
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class nz
extends ar
implements bj {
    private int[] a;
    private Image b;
    private au[] c;
    private int d;
    private boolean k;

    public nz() {
        super(2);
        int[] nArray = new int[4];
        nArray[2] = 234;
        nArray[3] = 125;
        this.a = nArray;
        this.d(false);
        this.b(true);
        this.a(false);
        z.X = true;
        this.b = f.d("/bklogin");
        int n2 = z.r - this.a[2] >> 1;
        int n3 = z.s - 166;
        this.a[0] = n2;
        this.a[1] = n3;
        this.c = new au[4];
        this.c[0] = new ff("", 100, 2);
        this.c[0].a(this.a[0] + 83, this.a[1] + 35, 119, 16);
        ((ff)this.c[0]).a("Nh\u1eadp nick ola");
        this.c[1] = new ff("", 100, 3);
        this.c[1].a(this.a[0] + 83, this.a[1] + 54, 119, 16);
        ((ff)this.c[1]).a("Nh\u1eadp m\u1eadt kh\u1ea9u ola");
        this.c[2] = new ey("", gp.e);
        this.c[2].a(this.a[0] + 83, this.a[1] + 73, 11, 11);
        this.c[3] = new ey("", false);
        this.c[3].a(this.a[0] + 83, this.a[1] + 90, 11, 11);
        nz nz2 = this;
        Object object = cv.a.a(107);
        if (object != null) {
            object = new String((byte[])object);
            ((ff)nz2.c[0]).c((String)object);
        }
        if ((object = cv.a.a(108)) != null) {
            object = new String((byte[])object);
            ((ff)nz2.c[1]).c((String)object);
        }
        if (cv.a.c(109)) {
            object = cv.a.a(109);
            ((ey)nz2.c[2]).e(object[0] == 1);
        }
        if (cv.a.c(110)) {
            object = cv.a.a(110);
            ((ey)nz2.c[3]).e(object[0] == 1);
        }
        this.a(com.mg.sq.a.l);
        this.a(new be());
        com.mg.sq.a.q().k();
        kq.a().a(com.mg.sq.a.q());
        ox.a();
        this.c[0].d(true);
        com.mg.sq.a.q().c();
        nz2 = this;
        bu bu2 = new bu("H\u1ed7 tr\u1ee3", -1);
        object = bu2;
        bu2.a(new bu[]{new bu("Qu\u00ean m\u1eadt kh\u1ea9u", 202), new bu("T\u1ed5ng \u0111\u00e0i", 203), new bu("C\u00e0i \u0111\u1eb7t", 204)});
        bv bv2 = new bv(new bu[]{new bu("\u0110\u0103ng nh\u1eadp", 200), new bu("\u0110\u0103ng k\u00fd", 201), object, new bu("Tho\u00e1t", 205)});
        object = bv2;
        bv2.a(nz2);
        nz2.a((bv)object);
    }

    protected final void r() {
        boolean bl2 = true;
        boolean bl3 = ((ey)this.c[3]).a();
        if (gp.f && bl3) {
            bl2 = false;
            this.f();
        } else if (com.mg.sq.a.j != null && com.mg.sq.a.j.length > 0) {
            com.mg.sq.a.b(com.mg.sq.a.j);
            com.mg.sq.a.j = null;
        }
        gp.f = false;
        if (bl2) {
            com.mg.sq.a.a(this);
        }
        cw.b("init SQLoginScreen");
    }

    protected final void e(int n2, int n3) {
        if (this.k) {
            return;
        }
        int n4 = 0;
        while (n4 < this.c.length) {
            if (this.c[n4].c(n2, n3)) {
                if (this.d == n4) break;
                this.c[this.d].d(false);
                this.d = n4;
                this.c[this.d].d(true);
                return;
            }
            ++n4;
        }
    }

    protected final void a(int n2) {
        if (this.k) {
            return;
        }
        int n3 = this.d--;
        switch (n2) {
            case 99: {
                if (this.d < 0) {
                    this.d = 0;
                }
            }
            case 98: {
                if (n2 == 98) {
                    ++this.d;
                    if (this.d > this.c.length - 1) {
                        this.d = this.c.length - 1;
                    }
                }
                this.c[n3].d(false);
                this.c[this.d].d(true);
                return;
            }
        }
        this.c[this.d].f(n2);
    }

    protected final void e(int n2) {
        if (this.k) {
            return;
        }
        switch (n2) {
            case 98: 
            case 99: {
                return;
            }
        }
        this.c[this.d].g(n2);
    }

    protected final void c() {
        int n2 = 0;
        while (n2 < this.c.length) {
            this.c[n2].n();
            ++n2;
        }
    }

    protected final void a(Graphics graphics) {
        if (z.s > this.b.getHeight() || z.r > this.b.getWidth()) {
            graphics.setColor(0);
            graphics.fillRect(0, 0, z.r, z.s);
        }
        graphics.drawImage(this.b, z.r / 2, this.a[1] + 166, 33);
        int n2 = 0;
        while (n2 < this.c.length) {
            this.c[n2].a(graphics, 0, 0);
            ++n2;
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 6: {
                String string = com.mg.sq.a.k(241220).toLowerCase().trim();
                cl.b(string);
                com.mg.sq.a.q().k();
                break;
            }
            case 7: {
                com.mg.sq.a.q().a(false);
                break;
            }
            case 200: {
                this.f();
                break;
            }
            case 201: {
                com.mg.sq.a.J();
                break;
            }
            case 202: {
                hc hc2 = com.mg.sq.a.a("Nh\u1eadp nick mu\u1ed1n l\u1ea5y l\u1ea1i m\u1eadt kh\u1ea9u v\u00e0o b\u00ean d\u01b0\u1edbi", null, "Xong", 6, "\u0110\u00f3ng", 7);
                hc2.a(this);
                ak.b().a(hc2);
                break;
            }
            case 203: {
                MGMIDlet.b("1900588883");
                break;
            }
            case 204: {
                com.mg.sq.a.D();
                break;
            }
            case 205: {
                MGMIDlet.f().d();
            }
        }
        this.c(false);
    }

    private void f() {
        String string = ((ff)this.c[0]).r();
        String string2 = ((ff)this.c[1]).r();
        if (!l.a(string) && !l.a(string2)) {
            nz nz2 = this;
            ey ey2 = (ey)nz2.c[2];
            ff ff2 = (ff)nz2.c[1];
            if (ey2.a()) {
                cv.a.a(108, ff2.r().getBytes());
                cv.a.a(109, new byte[]{1});
            } else {
                cv.a.a(109, new byte[1]);
                cv.a.b(108);
            }
            ey2 = (ey)nz2.c[3];
            if (ey2.a()) {
                cv.a.a(110, new byte[]{1});
            } else {
                cv.a.a(110, new byte[1]);
            }
            ff2 = (ff)nz2.c[0];
            cv.a.a(107, ff2.r().getBytes());
            cv.a.a();
            gr.e = string = string.trim().toLowerCase();
            gr.f = string2;
            com.mg.sq.a.a(null, null);
            kq kq2 = kq.a();
            pa.k();
            kq2.a(string, string2, gr.a);
        }
        gr.q = true;
        gr.q = pa.G() == 0;
        oz.d();
    }

    public static void d() {
        gr.c = pa.x();
        Object object = pa.r();
        if (object != null && !((String)object).equals(gr.e)) {
            pa.s();
        }
        object = new ea();
        new ea().b = "vn";
        ((ea)object).e = z.r;
        ((ea)object).f = z.s;
        ((ea)object).g = 0;
        ((ea)object).c = "123456789";
        ((ea)object).d = "patriot";
        String string = System.getProperty("microedition.platform");
        String string2 = ((ea)object).a = string == null ? " " : string;
        if (com.mg.sq.a.k == null) {
            com.mg.sq.a.k = new og();
        }
        dv.a().a(com.mg.sq.a.k);
        dv.a().a((ea)object);
        og.q = System.currentTimeMillis();
        com.mg.sq.a.m = false;
    }

    protected final void s() {
        this.b = null;
        this.c = null;
        System.gc();
    }

    public final void e() {
        this.k = true;
        gw gw2 = new gw();
        gw2.b(241222);
        gw2.t();
        ak.b().a(gw2);
        cr.b().a("lv1", -1);
        cr.b().e();
    }
}

