/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class hl
extends al
implements bf {
    private int k = 0;
    private int l = 0;
    private k m;
    private k n;
    private k[] o;
    private String[] p = null;
    private int[][] q;
    private int r = 4;
    private int s = 0;
    private int t = 0;
    private lm[] u;
    private String v;
    private cu w = null;
    private int x = 2;
    private boolean y;
    private int z = 0;
    private int A = 0;
    private int[] B;
    private az C;
    private String D = null;
    private az E;

    public hl(lm[] object, az az2, az az3, az az4) {
        this(null, (lm[])object);
        object = this;
        object.a(az2, true);
        this.a(az4);
        az2 = az3;
        object = this;
        object.b(az2, true);
        this.a(this.u);
    }

    public hl(String string, lm lm2) {
        this(string, new lm[]{lm2});
        this.y = true;
        int n2 = this.o[0].a + this.c + this.o[0].c + 2 + bx.d.a(lm2.b) + 5;
        if (n2 > this.m.c) {
            this.m.c += (n2 -= this.m.c);
            this.n.c += n2;
            this.f += n2;
            this.c = (v.t - this.f) / 2;
        }
    }

    public hl(String object, lm[] lmArray) {
        super(1);
        int n2;
        if (lmArray == null) {
            lmArray = new lm[]{};
        }
        this.v = object;
        this.u = lmArray;
        if (v.t > v.u) {
            this.r = 5;
        }
        this.a(new ba());
        this.a(this);
        this.b(-241439);
        this.a(this.u);
        if (this.u != null) {
            this.B = new int[this.u.length];
            this.t = this.u.length;
        }
        int n3 = this.f > 240 ? 225 : this.f - 15;
        int n4 = 7;
        if (object != null) {
            this.w = new cu(7, 7);
            n4 = 7 + (bx.d.a() + 2);
        }
        if (this.t > 0) {
            this.s = this.t / this.r + (this.t % this.r > 0 ? 1 : 0);
            n3 = 32 * this.r + 5 + 5 + 8 + 8;
            int n5 = (this.s << 5) + (this.s - 1) * 5 + 8 + 8;
            this.m = new k(7, n4, n3, n5);
            n4 += this.m.d + 5;
            this.o = new k[this.t];
            n5 = this.m.a + 8;
            n2 = this.m.b + 8;
            int n6 = 0;
            int n7 = this.o.length;
            while (n6 < n7) {
                int n8 = n6 % this.r;
                int n9 = n6 / this.r;
                this.o[n6] = new k(n5 + (n8 << 5) + n8 * 5, n2 + (n9 << 5) + n9 * 5, 32, 32);
                ++n6;
            }
        }
        this.n = new k(7, n4, n3, bx.d.a() * 3 + 5 + 5);
        this.f = n3 + 7 + 7 + 1;
        this.g = (n4 += this.n.d) + 7;
        this.c = (v.t - this.f) / 2;
        this.d = (v.u - this.g - ba.a) / 2;
        object = this;
        if (((hl)object).t > 0) {
            ((hl)object).q = new int[((hl)object).o.length][4];
            n4 = 0;
            while (n4 < ((hl)object).s) {
                n2 = 0;
                while (n2 < ((hl)object).r) {
                    n3 = n4 * ((hl)object).r + n2;
                    if (n3 >= ((hl)object).o.length) break;
                    ((hl)object).q[n3] = new int[]{n3 < ((hl)object).o.length - 1 ? n3 + 1 : ((hl)object).o.length - 1, n3 > 0 ? n3 - 1 : 0, n4 < ((hl)object).s - 1 ? n3 + ((hl)object).r : n3, n4 > 0 ? n3 - ((hl)object).r : n3};
                    ++n2;
                }
                ++n4;
            }
        }
        this.l = 0;
        this.f(this.l);
    }

    public final void b(az az2) {
        this.E = az2;
    }

    public final void a(az az2) {
        super.a(az2);
        this.C = az2;
    }

    public final void a(ll ll2) {
        if (this.u == null) {
            return;
        }
        int n2 = this.u.length - 1;
        while (n2 >= 0) {
            if (this.u[n2].e == 1) {
                if ((long)this.u[n2].g < ll2.k) {
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
        this.p = bx.a(string, this.n.c - 4, bx.d);
        if (this.x < this.p.length) {
            this.x = this.p.length;
        }
        this.n.d = bx.d.a() * this.x + 5 + 5;
        this.g = 20 + this.n.d + 7;
        if (this.d + this.g > v.u) {
            this.d = (v.u - this.g) / 2;
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
                    az az2 = null;
                    hl hl2 = this;
                    hl2.a(az2, true);
                    this.a((az)null);
                    return;
                }
                az az3 = this.E;
                hl hl3 = this;
                hl3.a(az3, true);
                this.a(new bd("", this.E.a()));
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

    private void a(lm[] object) {
        if (this.u == null || this.u.length == 0) {
            this.a((az)null);
            az az2 = null;
            object = this;
            object.a(az2, true);
            return;
        }
        a a2 = new a();
        int n2 = 0;
        while (n2 < ((lm[])object).length) {
            if (object[n2].g > 0) {
                a2.a(object[n2]);
            }
            ++n2;
        }
        n2 = a2.d();
        this.u = new lm[n2];
        int n3 = 0;
        while (n3 < n2) {
            this.u[n3] = (lm)a2.b(n3);
            ++n3;
        }
        if (this.u == null || this.u.length == 0) {
            this.a((az)null);
            a2 = null;
            hl hl2 = this;
            hl2.a((az)((Object)a2), true);
        }
    }

    public final void c(Graphics graphics) {
        int n2;
        if (this.h) {
            pc.a(graphics, this.c, this.d, this.f, this.g, v.aj, true);
            if (this.v != null) {
                bx.d.c(true);
                bx.d.a(graphics, this.v, this.w.a + this.c, this.w.b + this.d, 0);
                bx.d.c(false);
            }
        }
        if (this.m != null) {
            pc.b(graphics, this.m.a + this.c, this.m.b + this.d, this.m.c, this.m.d, v.aj, true);
            n2 = 0;
            int n3 = this.o.length;
            while (n2 < n3) {
                pc.b(graphics, this.o[n2].a + this.c, this.o[n2].b + this.d, this.o[n2].c, this.o[n2].d, 6647295, 0xFFFFFF, 8369663);
                pc.g(graphics, this.u[n2].j, this.o[n2].a + this.c, this.o[n2].b + this.d, 20);
                if (this.B != null && n2 < this.B.length && this.B[n2] < 0) {
                    cw.a(graphics, 0xE7E7E7, this.o[n2].a + this.c, this.o[n2].b + this.d, this.o[n2].c, this.o[n2].d);
                }
                if (this.u[n2].e != 9) {
                    bx.c.a(graphics, "" + this.u[n2].g, this.o[n2].a + this.c + this.o[n2].c - 2, this.o[n2].b + this.o[n2].d - bx.c.a() + this.d + 2, 2);
                }
                if (this.y) {
                    bx.d.c(true);
                    bx.d.a(graphics, this.u[n2].b, this.o[n2].a + this.c + this.o[n2].c + 2, this.o[n2].b + (this.o[n2].d - bx.c.a()) / 2 + this.d, 0);
                    bx.d.c(false);
                }
                ++n2;
            }
            pc.a(graphics, this.o[this.l], this.c, this.d, this.z);
            if (this.h) {
                pc.b(graphics, this.n.a + this.c, this.n.b + this.d, this.n.c, this.n.d, v.aj, true);
            }
        }
        if (this.h && this.p != null) {
            n2 = 0;
            if (this.D != null) {
                bx.d.c(true);
                bx.d.a(graphics, this.D, this.n.a + this.c + 2, this.n.b + this.d + 5, 0);
                bx.d.c(false);
                n2 = 0 + (bx.d.a() + 2);
            }
            bx.a(graphics, bx.d, this.p, this.n.a + this.c + 2, this.n.b + this.d + 5 + n2, this.n.c - 4, this.n.d, 0);
        }
    }

    public final void d(int n2, int n3) {
        ag.b().a(-241439, false);
    }

    private void f(int n2) {
        if (n2 >= this.t) {
            this.p = bx.a("Kh\u00f4ng c\u00f3 v\u1eadt d\u1ee5ng n\u00e0o trong gi\u1ecf.", this.n.c - 4, bx.d);
            return;
        }
        try {
            if (this.t > 0) {
                if (this.u[n2].d != null) {
                    this.p = bx.a(this.u[n2].d, this.n.c - 4, bx.d);
                }
            } else {
                this.p = bx.a("Kh\u00f4ng c\u00f3 v\u1eadt d\u1ee5ng n\u00e0o trong gi\u1ecf.", this.n.c - 4, bx.d);
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
            this.n.d = bx.d.a() * this.x + 5 + 5;
            this.g = n3 + this.n.d + 7;
            if (this.d + this.g > v.u) {
                this.d = (v.u - this.g) / 2;
            }
            this.x();
            return;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            ct.a("index = " + n2 + "     items leng " + this.u.length);
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

    public final lm v() {
        lm lm2 = null;
        if (this.l < this.t) {
            lm2 = this.u[this.l];
        }
        return lm2;
    }

    public final lm[] w() {
        return this.u;
    }
}

