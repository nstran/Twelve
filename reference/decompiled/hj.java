/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class hj
extends ap
implements bj {
    private int k = 0;
    private int l = 0;
    private n m;
    private n n;
    private n[] o;
    private String[] p = null;
    private int[][] q;
    private int r = 4;
    private int s = 0;
    private int t = 0;
    private lk[] u;
    private String v;
    private cx w = null;
    private int x = 2;
    private boolean y;
    private int z = 0;
    private int A = 0;
    private int[] B;
    private bd C;
    private String D = null;
    private bd E;

    public hj(lk[] object, bd bd2, bd bd3, bd bd4) {
        this(null, (lk[])object);
        object = this;
        object.a(bd2, true);
        this.a(bd4);
        bd2 = bd3;
        object = this;
        object.b(bd2, true);
        this.a(this.u);
    }

    public hj(String string, lk lk2) {
        this(string, new lk[]{lk2});
        this.y = true;
        int n2 = this.o[0].a + this.c + this.o[0].c + 2 + ca.d.a(lk2.b) + 5;
        if (n2 > this.m.c) {
            this.m.c += (n2 -= this.m.c);
            this.n.c += n2;
            this.f += n2;
            this.c = (z.r - this.f) / 2;
        }
    }

    public hj(String object, lk[] lkArray) {
        super(1);
        if (lkArray == null) {
            lkArray = new lk[]{};
        }
        this.v = object;
        this.u = lkArray;
        if (z.r > z.s) {
            this.r = 5;
        }
        this.a(new be());
        this.a(this);
        this.b(-241439);
        this.a(this.u);
        if (this.u != null) {
            this.B = new int[this.u.length];
            this.t = this.u.length;
        }
        int n2 = this.f > 240 ? 225 : this.f - 15;
        int n3 = 0;
        int n4 = 7;
        if (object != null) {
            this.w = new cx(7, 7);
            n4 = 7 + (ca.d.a() + 2);
        }
        if (this.t > 0) {
            this.s = this.t / this.r + (this.t % this.r > 0 ? 1 : 0);
            n2 = 32 * this.r + 5 + 5 + 8 + 8;
            n3 = (this.s << 5) + (this.s - 1) * 5 + 8 + 8;
            this.m = new n(7, n4, n2, n3);
            n4 += this.m.d + 5;
            this.o = new n[this.t];
            int n5 = this.m.a + 8;
            n3 = this.m.b + 8;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = this.o.length;
            while (n8 < n9) {
                n6 = n8 % this.r;
                n7 = n8 / this.r;
                this.o[n8] = new n(n5 + (n6 << 5) + n6 * 5, n3 + (n7 << 5) + n7 * 5, 32, 32);
                ++n8;
            }
        }
        this.n = new n(7, n4, n2, ca.d.a() * 3 + 5 + 5);
        this.f = n2 + 7 + 7 + 1;
        this.g = (n4 += this.n.d) + 7;
        this.c = (z.r - this.f) / 2;
        this.d = (z.s - this.g - be.a) / 2;
        object = this;
        if (((hj)object).t > 0) {
            ((hj)object).q = new int[((hj)object).o.length][4];
            n2 = 0;
            n3 = 0;
            while (n3 < ((hj)object).s) {
                n4 = 0;
                while (n4 < ((hj)object).r) {
                    n2 = n3 * ((hj)object).r + n4;
                    if (n2 >= ((hj)object).o.length) break;
                    ((hj)object).q[n2] = new int[]{n2 < ((hj)object).o.length - 1 ? n2 + 1 : ((hj)object).o.length - 1, n2 > 0 ? n2 - 1 : 0, n3 < ((hj)object).s - 1 ? n2 + ((hj)object).r : n2, n3 > 0 ? n2 - ((hj)object).r : n2};
                    ++n4;
                }
                ++n3;
            }
        }
        this.l = 0;
        this.f(this.l);
    }

    public final void b(bd bd2) {
        this.E = bd2;
    }

    public final void a(bd bd2) {
        super.a(bd2);
        this.C = bd2;
    }

    public final void a(lj lj2) {
        if (this.u == null) {
            return;
        }
        int n2 = this.u.length - 1;
        while (n2 >= 0) {
            if (this.u[n2].e == 1) {
                if ((long)this.u[n2].g < lj2.j) {
                    this.B[n2] = -1;
                } else {
                    this.l = n2;
                }
            }
            --n2;
        }
        this.f(this.l);
    }

    public final void a(String string) {
        this.p = ca.a(string, this.n.c - 4, ca.d);
        if (this.x < this.p.length) {
            this.x = this.p.length;
        }
        this.n.d = ca.d.a() * this.x + 5 + 5;
        this.g = 20 + this.n.d + 7;
        if (this.d + this.g > z.s) {
            this.d = (z.s - this.g) / 2;
        }
    }

    public final void e(int n2) {
        this.k = 2;
        this.x();
    }

    private void x() {
        switch (this.k) {
            case 0: {
                return;
            }
            case 2: {
                if (this.B == null || this.B[this.l] < 0) {
                    bd bd2 = null;
                    hj hj2 = this;
                    hj2.a(bd2, true);
                    this.a((bd)null);
                    return;
                }
                bd bd3 = this.E;
                hj hj3 = this;
                hj3.a(bd3, true);
                this.a(new bh("", this.E.a()));
            }
        }
    }

    protected final void g() {
        ++this.A;
        if (this.A > 5) {
            this.z = this.z == 0 ? -2 : 0;
            this.A = 0;
        }
    }

    private void a(lk[] object) {
        if (this.u == null || this.u.length == 0) {
            this.a((bd)null);
            bd bd2 = null;
            object = this;
            object.a(bd2, true);
            return;
        }
        a a2 = new a();
        int n2 = 0;
        while (n2 < ((lk[])object).length) {
            if (object[n2].g > 0) {
                a2.a(object[n2]);
            }
            ++n2;
        }
        n2 = a2.d();
        this.u = new lk[n2];
        int n3 = 0;
        while (n3 < n2) {
            this.u[n3] = (lk)a2.b(n3);
            ++n3;
        }
        if (this.u == null || this.u.length == 0) {
            this.a((bd)null);
            a2 = null;
            hj hj2 = this;
            hj2.a((bd)((Object)a2), true);
        }
    }

    public final void c(Graphics graphics) {
        int n2;
        if (this.h) {
            oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, true);
            if (this.v != null) {
                ca.d.c(true);
                ca.d.a(graphics, this.v, this.w.a + this.c, this.w.b + this.d, 0);
                ca.d.c(false);
            }
        }
        if (this.m != null) {
            oz.b(graphics, this.m.a + this.c, this.m.b + this.d, this.m.c, this.m.d, z.ac, true);
            n2 = 0;
            int n3 = this.o.length;
            while (n2 < n3) {
                oz.b(graphics, this.o[n2].a + this.c, this.o[n2].b + this.d, this.o[n2].c, this.o[n2].d, 6647295, 0xFFFFFF, 8369663);
                oz.g(graphics, this.u[n2].j % 30000, this.o[n2].a + this.c, this.o[n2].b + this.d, 20);
                if (this.B != null && n2 < this.B.length && this.B[n2] < 0) {
                    cz.a(graphics, 0xE7E7E7, this.o[n2].a + this.c, this.o[n2].b + this.d, this.o[n2].c, this.o[n2].d);
                }
                if (this.u[n2].e != 9) {
                    ca.c.a(graphics, "" + this.u[n2].g, this.o[n2].a + this.c + this.o[n2].c - 2, this.o[n2].b + this.o[n2].d - ca.c.a() + this.d + 2, 2);
                }
                if (this.y) {
                    ca.d.c(true);
                    ca.d.a(graphics, this.u[n2].b, this.o[n2].a + this.c + this.o[n2].c + 2, this.o[n2].b + (this.o[n2].d - ca.c.a()) / 2 + this.d, 0);
                    ca.d.c(false);
                }
                ++n2;
            }
            oz.a(graphics, this.o[this.l], this.c, this.d, this.z);
            if (this.h) {
                oz.b(graphics, this.n.a + this.c, this.n.b + this.d, this.n.c, this.n.d, z.ac, true);
            }
        }
        if (this.h && this.p != null) {
            n2 = 0;
            if (this.D != null) {
                ca.d.c(true);
                ca.d.a(graphics, this.D, this.n.a + this.c + 2, this.n.b + this.d + 5, 0);
                ca.d.c(false);
                n2 = 0 + (ca.d.a() + 2);
            }
            ca.a(graphics, ca.d, this.p, this.n.a + this.c + 2, this.n.b + this.d + 5 + n2, this.n.c - 4, this.n.d, 0);
        }
    }

    public final void d(int n2, int n3) {
        ak.b().a(-241439, false);
    }

    private void f(int n2) {
        if (n2 >= this.t) {
            this.p = ca.a("Kh\u00f4ng c\u00f3 v\u1eadt d\u1ee5ng n\u00e0o trong gi\u1ecf.", this.n.c - 4, ca.d);
            return;
        }
        try {
            if (this.t > 0) {
                if (this.u[n2].d != null) {
                    this.p = ca.a(this.u[n2].d, this.n.c - 4, ca.d);
                }
            } else {
                this.p = ca.a("Kh\u00f4ng c\u00f3 v\u1eadt d\u1ee5ng n\u00e0o trong gi\u1ecf.", this.n.c - 4, ca.d);
            }
            this.D = null;
            int n3 = this.m.b + this.m.d + 5;
            if (this.p != null && this.x < this.p.length) {
                this.x = this.p.length;
                if (this.u != null && this.u.length > 0 && this.u[n2].b != null) {
                    ++this.x;
                    this.D = this.u[n2].b;
                }
            }
            this.n.d = ca.d.a() * this.x + 5 + 5;
            this.g = n3 + this.n.d + 7;
            if (this.d + this.g > z.s) {
                this.d = (z.s - this.g) / 2;
            }
            this.x();
            return;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            cw.a("index = " + n2 + "     items leng " + this.u.length);
            arrayIndexOutOfBoundsException.printStackTrace();
            return;
        }
    }

    public final void c(int n2) {
        if (this.t > 0) {
            switch (n2) {
                case 96: 
                case 97: 
                case 98: 
                case 99: {
                    this.l = this.q[this.l][n2 - 96];
                    if (this.l >= this.o.length) {
                        this.l = this.o.length - 1;
                    }
                    this.f(this.l);
                    break;
                }
                case 95: {
                    if (this.C == null) break;
                    this.i.d(-1, this.C.a());
                }
            }
        }
        this.e(true);
    }

    public final void a(int n2, int n3) {
        if (this.t == 0) {
            return;
        }
        int n4 = this.l;
        int n5 = 0;
        while (n5 < this.o.length) {
            if (this.o[n5].a(n2 - this.c, n3 - this.d) && n5 < this.u.length) {
                this.l = n5;
                if (n4 == this.l) {
                    if (this.C != null) {
                        this.i.d(-1, this.C.a());
                    }
                } else {
                    this.f(this.l);
                }
            }
            ++n5;
        }
        this.e(true);
    }

    public final int t() {
        int n2 = -1;
        if (this.l < this.t) {
            n2 = this.u[this.l].a;
        }
        return n2;
    }

    public final int u() {
        return this.t;
    }

    public final lk v() {
        lk lk2 = null;
        if (this.l < this.t) {
            lk2 = this.u[this.l];
        }
        return lk2;
    }

    public final lk[] w() {
        return this.u;
    }
}

