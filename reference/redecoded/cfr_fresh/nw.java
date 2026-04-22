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

public final class nw
extends an
implements bf,
bg {
    private Image a;
    private Image b;
    private int[] c;
    private int[] d;
    private by k;
    private d l;
    private gg[] m;
    private int n;
    private mg o;
    private df[][] p;
    private df[][] q;
    private df[] r;
    private mb s;
    private mb t;
    private int u;
    private mb[][] v;
    private mb[][] w;
    private mb[] x;
    private boolean y;
    private gg z;
    private String[] A;

    public nw() {
        super(3);
        int[] nArray = new int[4];
        nArray[2] = 135;
        nArray[3] = 220;
        this.c = nArray;
        this.d = new int[2];
        this.A = new String[]{"H\u1ecfa", "L\u00f4i", "Th\u1ee7y"};
        this.a = f.d("/createcs/bk");
        this.b = f.d("/createcs/stone");
        this.k = new by();
        this.k.c(true);
        this.l = bx.c;
        int n2 = v.t - this.c[2] - this.b.getWidth();
        int n3 = 5;
        if (n2 > 80) {
            n3 = 50;
        }
        n2 = n2 - n3 >> 1;
        this.c[0] = n2 + this.b.getWidth() + n3;
        this.c[1] = (v.u - ba.a - this.c[3]) / 2;
        this.d[0] = n2;
        this.d[1] = this.c[1] + this.c[3] - 60;
        Object object = this;
        String[] stringArray = new String[]{"Gi\u1edbi T\u00ednh", "H\u1ec7", "Khu\u00f4n M\u1eb7t", "Ki\u1ec3u T\u00f3c", "M\u00e0u T\u00f3c", "M\u00e0u Da"};
        int[] nArray2 = new int[]{100, 101, 102, 103, 104, 105};
        ((nw)object).m = new gg[6];
        int n4 = ((nw)object).c[0] + 8;
        int n5 = ((nw)object).c[1] + 25;
        int n6 = 0;
        while (n6 < ((nw)object).m.length) {
            ((nw)object).m[n6] = new gg(stringArray[n6]);
            ((nw)object).m[n6].h(nArray2[n6]);
            ((nw)object).m[n6].a(((nw)object).l);
            ((nw)object).m[n6].a_(n6);
            ((nw)object).m[n6].a(n4, n5, 120, 16);
            ((nw)object).m[n6].a((bg)object);
            n5 += 33;
            ++n6;
        }
        ((nw)object).m[0].a(new String[]{"Nam", "N\u1eef"});
        ((nw)object).m[1].a(((nw)object).A);
        ((nw)object).m[0].d(true);
        this.g(false);
        this.f(false);
        this.a(com.mg.sq.a.n);
        object = new bs(new br[]{new br("B\u1eaft \u0111\u1ea7u", 301), new br("\u0110\u0103ng Xu\u1ea5t", 302)});
        ((bs)object).a(this);
        this.a((bs)object);
    }

    private void e() {
        this.t = this.x[this.m[0].a()];
        int n2 = this.m[0].a();
        lh lh2 = new lh(0);
        new lh(0).W = this.r[n2];
        lh2.U = this.p[n2][this.m[3].a()];
        this.o = mb.a(lh2, this.w[n2][this.m[3].a()], this.v[n2][this.m[2].a()], this.s, this.t, false);
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
        if (v.u > this.a.getHeight()) {
            graphics.setColor(555256);
            graphics.fillRect(0, 0, v.t, v.u - this.a.getHeight());
        }
        int n2 = 0;
        while (n2 < v.t) {
            graphics.drawImage(this.a, n2, v.u, 36);
            n2 += this.a.getWidth();
        }
        if (this.y) {
            return;
        }
        graphics.drawImage(this.b, this.d[0], this.d[1], 0);
        pc.a(graphics, this.c[0], this.c[1], this.c[2], this.c[3], 0xF0FBFF, true);
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
                nw nw2 = this;
                switch (nw2.z.b()) {
                    case 0: {
                        nw2.g();
                        break;
                    }
                    case 3: {
                        Object object = nw2;
                        df df2 = (df)((nw)object).m[3].q();
                        ((nw)object).m[4].a(df2.f);
                        ((nw)object).f();
                        break;
                    }
                    case 4: {
                        nw2.f();
                        break;
                    }
                    case 5: {
                        Object object = nw2;
                        df df2 = ((nw)object).r[((nw)object).m[0].a()];
                        df2.e = object = (dg)((nw)object).m[5].q();
                    }
                }
                nw2.e();
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
        df df2 = (df)this.m[3].q();
        ((df)this.m[3].q()).e = (dg)this.m[4].q();
    }

    private void g() {
        Object[] objectArray = this.p[this.m[0].a()];
        Object[] objectArray2 = this.q[this.m[0].a()];
        df df2 = this.r[this.m[0].a()];
        this.m[0].a();
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
        df2.e = df2.d;
        this.m[2].a(objectArray2);
        this.m[3].a(objectArray);
        this.m[4].a(((df)objectArray[0]).f);
        this.m[5].a(df2.f);
        this.e();
    }

    public final void a(df[] dfArray, df[] dfArray2, df[] dfArray3, df[] dfArray4, df[] dfArray5, df[] dfArray6) {
        this.p = new df[2][];
        this.p[0] = dfArray;
        this.p[1] = dfArray4;
        this.q = new df[2][];
        this.q[0] = dfArray2;
        this.q[1] = dfArray5;
        this.r = new df[2];
        this.r[0] = dfArray3[0];
        this.r[1] = dfArray6[0];
        this.s = new mb(89999);
        this.x = new mb[2];
        this.x[1] = new mb(79999);
        this.x[0] = new mb(79899);
        this.v = new mb[2][];
        int n2 = 0;
        while (n2 < 2) {
            this.v[n2] = new mb[this.q[n2].length];
            int n3 = 0;
            while (n3 < this.v[n2].length) {
                this.v[n2][n3] = new mb(this.q[n2][n3].a + 99);
                ++n3;
            }
            ++n2;
        }
        this.w = new mb[2][];
        n2 = 0;
        while (n2 < 2) {
            this.w[n2] = new mb[this.p[n2].length];
            int n4 = 0;
            while (n4 < this.w[n2].length) {
                this.w[n2][n4] = new mb(this.p[n2][n4].a + 99);
                ++n4;
            }
            ++n2;
        }
        this.g();
        com.mg.sq.a.s().v();
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 301: {
                com.mg.sq.a.s().a((String)null, (il)null);
                int[] nArray = new int[]{1, 2, 4};
                ks.a().a(this.m[0].a(), nArray[this.m[1].a()], this.p[this.m[0].a()][this.m[3].a()], this.q[this.m[0].a()][this.m[2].a()], this.r[this.m[0].a()]);
                break;
            }
            case 302: {
                com.mg.sq.a.u();
            }
        }
        this.c(false);
    }

    public final void d() {
        this.y = true;
        gy gy2 = new gy();
        gy2.b(241222);
        this.h();
        gy2.t();
        ag.b().a(gy2);
        co.b().a("lv1", -1);
        co.b().e();
    }

    public final void a(int n2, int n3, Object object) {
        this.u = 5;
        this.z = (gg)object;
    }
}

