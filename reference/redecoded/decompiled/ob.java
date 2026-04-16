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

public final class ob
extends an
implements bf {
    private int[] a;
    private Image b;
    private aq[] c;
    private int d;
    private boolean k;

    public ob() {
        super(2);
        int[] nArray = new int[4];
        nArray[2] = 234;
        nArray[3] = 125;
        this.a = nArray;
        this.d(false);
        this.b(true);
        this.a(false);
        v.ae = true;
        this.b = f.d("/bklogin");
        int n2 = v.t - this.a[2] >> 1;
        int n3 = v.u - 166;
        this.a[0] = n2;
        this.a[1] = n3;
        this.c = new aq[4];
        this.c[0] = new ff("", 100, 2);
        this.c[0].a(this.a[0] + 83, this.a[1] + 35, 119, 16);
        ((ff)this.c[0]).a("Nh\u1eadp nick ola");
        this.c[1] = new ff("", 100, 3);
        this.c[1].a(this.a[0] + 83, this.a[1] + 54, 119, 16);
        ((ff)this.c[1]).a("Nh\u1eadp m\u1eadt kh\u1ea9u ola");
        this.c[2] = new ey("", gr.e);
        this.c[2].a(this.a[0] + 83, this.a[1] + 73, 11, 11);
        this.c[3] = new ey("", false);
        this.c[3].a(this.a[0] + 83, this.a[1] + 90, 11, 11);
        ob ob2 = this;
        Object object = cs.a.a(107);
        if (object != null) {
            object = new String((byte[])object);
            ((ff)ob2.c[0]).c((String)object);
        }
        if ((object = cs.a.a(108)) != null) {
            object = new String((byte[])object);
            ((ff)ob2.c[1]).c((String)object);
        }
        if (cs.a.c(109)) {
            object = cs.a.a(109);
            ((ey)ob2.c[2]).e(object[0] == 1);
        }
        if (cs.a.c(110)) {
            object = cs.a.a(110);
            ((ey)ob2.c[3]).e(object[0] == 1);
        }
        this.a(com.mg.sq.a.n);
        this.a(new ba());
        com.mg.sq.a.s().l();
        ks.a().a(com.mg.sq.a.s());
        pa.a();
        this.c[0].d(true);
        com.mg.sq.a.s().c();
        ob2 = this;
        br br2 = new br("H\u1ed7 tr\u1ee3", -1);
        object = br2;
        br2.a(new br[]{new br("Qu\u00ean m\u1eadt kh\u1ea9u", 202), new br("T\u1ed5ng \u0111\u00e0i", 203), new br("C\u00e0i \u0111\u1eb7t", 204)});
        bs bs2 = new bs(new br[]{new br("\u0110\u0103ng nh\u1eadp", 200), new br("\u0110\u0103ng k\u00fd", 201), object, new br("Tho\u00e1t", 205)});
        object = bs2;
        bs2.a(ob2);
        ob2.a((bs)object);
    }

    protected final void r() {
        boolean bl2 = true;
        boolean bl3 = ((ey)this.c[3]).a();
        if (gr.f && bl3) {
            bl2 = false;
            this.f();
        } else if (com.mg.sq.a.l != null && com.mg.sq.a.l.length > 0) {
            com.mg.sq.a.b(com.mg.sq.a.l);
            com.mg.sq.a.l = null;
        }
        gr.f = false;
        if (bl2) {
            com.mg.sq.a.a(this);
        }
        ct.b("init SQLoginScreen");
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
        if (v.u > this.b.getHeight() || v.t > this.b.getWidth()) {
            graphics.setColor(0);
            graphics.fillRect(0, 0, v.t, v.u);
        }
        graphics.drawImage(this.b, v.t / 2, this.a[1] + 166, 33);
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
                ci.b(string);
                com.mg.sq.a.s().l();
                break;
            }
            case 7: {
                com.mg.sq.a.s().a(false);
                break;
            }
            case 200: {
                this.f();
                break;
            }
            case 201: {
                com.mg.sq.a.s().L();
                break;
            }
            case 202: {
                he he2 = com.mg.sq.a.a("Nh\u1eadp nick mu\u1ed1n l\u1ea5y l\u1ea1i m\u1eadt kh\u1ea9u v\u00e0o b\u00ean d\u01b0\u1edbi", null, "Xong", 6, "\u0110\u00f3ng", 7);
                he2.a(this);
                ag.b().a(he2);
                break;
            }
            case 203: {
                MGMIDlet.d();
                MGMIDlet.b("1900588883");
                break;
            }
            case 204: {
                com.mg.sq.a.F();
                break;
            }
            case 205: {
                MGMIDlet mGMIDlet = MGMIDlet.d();
                mGMIDlet.notifyDestroyed();
            }
        }
        this.c(false);
    }

    private void f() {
        String string = ((ff)this.c[0]).r();
        String string2 = ((ff)this.c[1]).r();
        if (!i.a(string) && !i.a(string2)) {
            ob ob2 = this;
            ey ey2 = (ey)ob2.c[2];
            ff ff2 = (ff)ob2.c[1];
            if (ey2.a()) {
                cs.a.a(108, ff2.r().getBytes());
                cs.a.a(109, new byte[]{1});
            } else {
                cs.a.a(109, new byte[1]);
                cs.a.b(108);
            }
            ey2 = (ey)ob2.c[3];
            if (ey2.a()) {
                cs.a.a(110, new byte[]{1});
            } else {
                cs.a.a(110, new byte[1]);
            }
            ff2 = (ff)ob2.c[0];
            cs.a.a(107, ff2.r().getBytes());
            cs.a.a();
            go.e = string = string.trim().toLowerCase();
            go.f = string2;
            com.mg.sq.a.s().a((String)null, (il)null);
            ks ks2 = ks.a();
            pd.k();
            ks2.a(string, string2, go.a);
        }
        go.t = true;
        go.t = pd.G() == 0;
        pc.d();
    }

    public static void d() {
        go.c = pd.x();
        Object object = pd.r();
        if (object != null && !((String)object).equals(go.e)) {
            pd.s();
        }
        object = new dz();
        new dz().b = "vn";
        ((dz)object).e = v.t;
        ((dz)object).f = v.u;
        ((dz)object).g = 0;
        ((dz)object).c = "123456789";
        ((dz)object).d = "patriot";
        String string = System.getProperty("microedition.platform");
        String string2 = ((dz)object).a = string == null ? " " : string;
        if (com.mg.sq.a.m == null) {
            com.mg.sq.a.m = new oi();
        }
        du.a().a(com.mg.sq.a.m);
        du.a().a((dz)object);
        oi.r = System.currentTimeMillis();
        com.mg.sq.a.o = false;
    }

    protected final void s() {
        this.b = null;
        this.c = null;
        System.gc();
    }

    public final void e() {
        this.k = true;
        gy gy2 = new gy();
        gy2.b(241222);
        this.h();
        gy2.t();
        ag.b().a(gy2);
        co.b().a("lv1", -1);
        co.b().e();
    }
}

