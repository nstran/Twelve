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

public final class nu
extends ar
implements bj,
bk {
    private Image a;
    private Image b;
    private int[] c;
    private int[] d;
    private cb k;
    private d l;
    private gf[] m;
    private int n;
    private me o;
    private di[][] p;
    private di[][] q;
    private di[] r;
    private lz s;
    private lz t;
    private int u;
    private lz[][] v;
    private lz[][] w;
    private lz[] x;
    private boolean y;
    private gf z;
    private String[] A;

    public nu() {
        super(3);
        int[] nArray = new int[4];
        nArray[2] = 135;
        nArray[3] = 220;
        this.c = nArray;
        this.d = new int[2];
        this.A = new String[]{"H\u1ecfa", "L\u00f4i", "Th\u1ee7y"};
        this.a = f.d("/createcs/bk");
        this.b = f.d("/createcs/stone");
        this.k = new cb();
        this.k.c(true);
        this.l = ca.c;
        int n2 = z.r - this.c[2] - this.b.getWidth();
        int n3 = 5;
        if (n2 > 80) {
            n3 = 50;
        }
        n2 = n2 - n3 >> 1;
        this.c[0] = n2 + this.b.getWidth() + n3;
        this.c[1] = (z.s - be.a - this.c[3]) / 2;
        this.d[0] = n2;
        this.d[1] = this.c[1] + this.c[3] - 60;
        Object object = this;
        String[] stringArray = new String[]{"Gi\u1edbi T\u00ednh", "H\u1ec7", "Khu\u00f4n M\u1eb7t", "Ki\u1ec3u T\u00f3c", "M\u00e0u T\u00f3c", "M\u00e0u Da"};
        int[] nArray2 = new int[]{100, 101, 102, 103, 104, 105};
        ((nu)object).m = new gf[6];
        int n4 = ((nu)object).c[0] + 8;
        int n5 = ((nu)object).c[1] + 25;
        int n6 = 0;
        while (n6 < ((nu)object).m.length) {
            ((nu)object).m[n6] = new gf(stringArray[n6]);
            ((nu)object).m[n6].h(nArray2[n6]);
            ((nu)object).m[n6].a(((nu)object).l);
            ((nu)object).m[n6].a_(n6);
            ((nu)object).m[n6].a(n4, n5, 120, 16);
            ((nu)object).m[n6].a((bk)object);
            n5 += 33;
            ++n6;
        }
        ((nu)object).m[0].a(new String[]{"Nam", "N\u1eef"});
        ((nu)object).m[1].a(((nu)object).A);
        ((nu)object).m[0].d(true);
        this.g(false);
        this.f(false);
        this.a(com.mg.sq.a.l);
        object = new bv(new bu[]{new bu("B\u1eaft \u0111\u1ea7u", 301), new bu("\u0110\u0103ng Xu\u1ea5t", 302)});
        ((bv)object).a(this);
        this.a((bv)object);
    }

    private void e() {
        this.t = this.x[this.m[0].a()];
        int n2 = this.m[0].a();
        lf lf2 = new lf(0);
        new lf(0).Y = this.r[n2];
        lf2.W = this.p[n2][this.m[3].a()];
        this.o = lz.a(lf2, this.w[n2][this.m[3].a()], this.v[n2][this.m[2].a()], this.s, this.t, false);
        this.o.c(2);
        this.o.c(this.d[0] + 30, this.d[1] - this.o.q() + 5);
    }

    protected final void e(int n2, int n3) {
        int n4 = 0;
        while (n4 < this.m.length) {
            boolean bl2 = this.m[n4].c(n2, n3);
            if (bl2) {
                if (this.n != n4) {
                    this.m[this.n].d(false);
                    this.n = n4;
                    this.m[this.n].d(true);
                    return;
                }
            } else {
                this.u = 5;
            }
            ++n4;
        }
    }

    protected final void a(int n2) {
        int n3 = this.m[this.n].f(n2);
        if (n3 == 0) {
            n3 = this.n--;
            if (n2 == 99) {
                if (this.n < 0) {
                    this.n = this.m.length - 1;
                }
            } else if (n2 == 98) {
                ++this.n;
                if (this.n >= this.m.length) {
                    this.n = 0;
                }
            }
            if (n3 != this.n) {
                this.m[n3].d(false);
                this.m[this.n].d(true);
            }
        }
    }

    protected final void a(Graphics graphics) {
        if (z.s > this.a.getHeight()) {
            graphics.setColor(555256);
            graphics.fillRect(0, 0, z.r, z.s - this.a.getHeight());
        }
        int n2 = 0;
        while (n2 < z.r) {
            graphics.drawImage(this.a, n2, z.s, 36);
            n2 += this.a.getWidth();
        }
        if (this.y) {
            return;
        }
        graphics.drawImage(this.b, this.d[0], this.d[1], 0);
        oz.a(graphics, this.c[0], this.c[1], this.c[2], this.c[3], 0xF0FBFF, true);
        if (this.o != null) {
            this.o.a(graphics);
        }
        n2 = 0;
        while (n2 < this.m.length) {
            this.m[n2].a(graphics, 0, 0);
            ++n2;
        }
    }

    protected final void c() {
        if (this.u > 0) {
            --this.u;
            if (this.u == 0) {
                nu nu2 = this;
                switch (nu2.z.b()) {
                    case 0: {
                        nu2.g();
                        break;
                    }
                    case 3: {
                        Object object = nu2;
                        di di2 = (di)((nu)object).m[3].q();
                        ((nu)object).m[4].a(di2.f);
                        ((nu)object).f();
                        break;
                    }
                    case 4: {
                        nu2.f();
                        break;
                    }
                    case 5: {
                        Object object = nu2;
                        di di2 = ((nu)object).r[((nu)object).m[0].a()];
                        di2.e = object = (dj)((nu)object).m[5].q();
                    }
                }
                nu2.e();
            }
        }
        if (this.o != null) {
            this.o.i();
        }
        int n2 = 0;
        while (n2 < this.m.length) {
            this.m[n2].n();
            ++n2;
        }
    }

    private void f() {
        di di2 = (di)this.m[3].q();
        ((di)this.m[3].q()).e = (dj)this.m[4].q();
    }

    private void g() {
        Object[] objectArray = this.p[this.m[0].a()];
        Object[] objectArray2 = this.q[this.m[0].a()];
        di di2 = this.r[this.m[0].a()];
        int n2 = 0;
        while (n2 < objectArray.length) {
            objectArray[n2].e = objectArray[n2].d;
            ++n2;
        }
        n2 = 0;
        while (n2 < objectArray2.length) {
            objectArray2[n2].e = objectArray2[n2].d;
            ++n2;
        }
        di2.e = di2.d;
        this.m[2].a(objectArray2);
        this.m[3].a(objectArray);
        this.m[4].a(((di)objectArray[0]).f);
        this.m[5].a(di2.f);
        this.e();
    }

    public final void a(di[] diArray, di[] diArray2, di[] diArray3, di[] diArray4, di[] diArray5, di[] diArray6) {
        this.p = new di[2][];
        this.p[0] = diArray;
        this.p[1] = diArray4;
        this.q = new di[2][];
        this.q[0] = diArray2;
        this.q[1] = diArray5;
        this.r = new di[2];
        this.r[0] = diArray3[0];
        this.r[1] = diArray6[0];
        this.s = new lz(89999);
        this.x = new lz[2];
        this.x[1] = new lz(79999);
        this.x[0] = new lz(79899);
        this.v = new lz[2][];
        int n2 = 0;
        while (n2 < 2) {
            this.v[n2] = new lz[this.q[n2].length];
            int n3 = 0;
            while (n3 < this.v[n2].length) {
                this.v[n2][n3] = new lz(this.q[n2][n3].a + 99);
                ++n3;
            }
            ++n2;
        }
        this.w = new lz[2][];
        n2 = 0;
        while (n2 < 2) {
            this.w[n2] = new lz[this.p[n2].length];
            int n4 = 0;
            while (n4 < this.w[n2].length) {
                this.w[n2][n4] = new lz(this.p[n2][n4].a + 99);
                ++n4;
            }
            ++n2;
        }
        this.g();
        com.mg.sq.a.t();
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 301: {
                com.mg.sq.a.a(null, null);
                int[] nArray = new int[]{1, 2, 4};
                kq.a().a(this.m[0].a(), nArray[this.m[1].a()], this.p[this.m[0].a()][this.m[3].a()], this.q[this.m[0].a()][this.m[2].a()], this.r[this.m[0].a()]);
                break;
            }
            case 302: {
                com.mg.sq.a.s();
            }
        }
        this.c(false);
    }

    public final void d() {
        this.y = true;
        gw gw2 = new gw();
        gw2.b(241222);
        gw2.t();
        ak.b().a(gw2);
        cr.b().a("lv1", -1);
        cr.b().e();
    }

    public final void a(int n2, int n3, Object object) {
        this.u = 5;
        this.z = (gf)object;
    }
}

