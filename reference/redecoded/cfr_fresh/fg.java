/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class fg
extends aq
implements bu {
    private byte l = (byte)6;
    private byte m = (byte)6;
    private int n = 2;
    private int o = 32;
    private int p = 32;
    private int q = 20;
    private int r = 0;
    private int s = 0;
    private k[] t;
    private int u = 0;
    private int[][] v;
    public k i;
    private k w = null;
    private a x = new a();
    public boolean j = false;
    private int y = 0;
    private int z;
    private db A;
    public Image k = null;
    private bq B;
    private Image C;
    private boolean D;
    private boolean E = false;
    private static b F = new fh(0);

    public fg(boolean bl2) {
        this.D = bl2;
        this.k = f.d("/slotlock");
        this.C = f.d("/m/lock2");
    }

    public final void a(aq aq2) {
        super.a(aq2);
        if (this.w == null && aq2 instanceof ay) {
            this.w = ((ay)aq2).q();
        }
    }

    public final void a(bq bq2) {
        this.B = bq2;
    }

    public final void a(db db2) {
        this.A = db2;
    }

    public final void e(boolean bl2) {
        this.E = true;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        pc.b(graphics, this.d.a + n2, this.d.b + n3, this.d.c, this.d.d, v.aj, false);
        cw.a(graphics, cw.a);
        cw.a(graphics, this.d.a + n2, this.d.b + 1 + n3, this.d.c, this.d.d - 2);
        if (this.w == null) {
            this.w = ((ay)this.l()).q();
        }
        int n4 = this.i.a - this.w.a;
        int n5 = this.i.b - this.w.b;
        n2 += n4 + this.m;
        n3 += n5 + this.l;
        int n6 = this.x.d();
        int n7 = 0;
        while (n7 < this.t.length) {
            if (this.t[n7].b(this.w.a, this.w.b, this.w.c, this.w.d)) {
                Object object;
                n4 = this.t[n7].a + n2;
                n5 = this.t[n7].b + n3;
                if (n7 < this.q) {
                    if (n7 >= go.n) {
                        pc.b(graphics, n4, n5, this.t[n7].c, this.t[n7].d, 0xFF0000, 0xFFFFFF, 15385573);
                    } else {
                        pc.b(graphics, n4, n5, this.t[n7].c, this.t[n7].d, 6647295, 0xFFFFFF, 8369663);
                    }
                } else if (this.k != null) {
                    graphics.drawImage(this.k, n4, n5, 0);
                } else {
                    pc.b(graphics, n4, n5, this.t[n7].c, this.t[n7].d, 0x787881, 0xFFFFFF, 11382450);
                }
                if (n7 < n6 && n7 < this.q && (object = this.x.b(n7)) != null) {
                    ((aq)object).a(graphics, n4, n5);
                    if (n7 >= go.n) {
                        graphics.drawImage(this.C, n4, n5, 0);
                    }
                }
            }
            ++n7;
        }
        try {
            if (this.g && this.u >= 0) {
                pc.a(graphics, this.t[this.u], n2, n3, this.z);
                if (this.A != null) {
                    this.A.a(graphics, this.t[this.u].a + n2, this.t[this.u].b + this.t[this.u].d + n3);
                }
            }
        }
        catch (Exception exception) {
            System.err.println(this.u);
        }
        cw.c(graphics, cw.a);
    }

    public final void h(int n2) {
        this.q = n2;
    }

    public final int a() {
        return this.q;
    }

    public final void d(int n2, int n3) {
        this.q = n2;
        this.s = this.i.c / (this.o + this.n);
        this.r = n2 / this.s + (n2 % this.s > 0 ? 1 : 0) + n3;
        this.t = new k[this.s * this.r];
        n2 = 0;
        while (n2 < this.r) {
            n3 = 0;
            while (n3 < this.s) {
                this.t[n2 * this.s + n3] = new k(n3 * (this.o + this.n), n2 * (this.p + this.n), this.o, this.p);
                ++n3;
            }
            ++n2;
        }
        this.i.d = this.r * (this.p + this.n) + (this.l << 1);
        this.m = (byte)((this.i.c - this.s * (this.o + this.n)) / 2);
        fg fg2 = this;
        this.v = new int[fg2.r * fg2.s][5];
        if (fg2.D) {
            int n4 = 0;
            while (n4 < fg2.r) {
                int n5 = 0;
                while (n5 < fg2.s) {
                    n3 = n4 * fg2.s + n5;
                    int[] nArray = new int[4];
                    int n6 = nArray[0] = n3 < fg2.v.length - 1 ? n3 + 1 : -1;
                    nArray[1] = n5 == 0 ? -2 : (n3 > 0 ? n3 - 1 : -1);
                    nArray[2] = n4 < fg2.r - 1 ? n3 + fg2.s : -1;
                    nArray[3] = n4 > 0 ? n3 - fg2.s : -2;
                    fg2.v[n3] = nArray;
                    ++n5;
                }
                ++n4;
            }
            return;
        }
        int n7 = 0;
        while (n7 < fg2.r) {
            int n8 = 0;
            while (n8 < fg2.s) {
                n3 = n7 * fg2.s + n8;
                fg2.v[n3] = new int[]{n3 < fg2.v.length - 1 ? n3 + 1 : -1, n3 > 0 ? n3 - 1 : -1, n7 < fg2.r - 1 ? n3 + fg2.s : -1, n7 > 0 ? n3 - fg2.s : -2};
                ++n8;
            }
            ++n7;
        }
    }

    public final void i(int n2) {
        this.u = n2;
        if (n2 < 0 || this.w == null || this.i == null) {
            return;
        }
        n2 = this.u / this.s;
        int n3 = this.i.b - this.w.b;
        if ((n2 = n2 * (this.p + this.n) + this.l + n3) < this.d.b) {
            ((ay)this.l()).j(n2 - this.d.b - this.l);
        }
        if (this.B != null) {
            this.B.a(this.r(), this.u);
        }
    }

    public final void n() {
        ++this.y;
        if (this.y >= 5) {
            this.z = this.z == 0 ? -2 : 0;
            this.y = 0;
        }
        int n2 = 0;
        int n3 = this.x.d();
        while (n2 < n3) {
            if (this.x.b(n2) != null) {
                dc dc2 = (dc)this.x.b(n2);
                dc2.n();
            }
            ++n2;
        }
    }

    public final boolean f(int n2) {
        switch (n2) {
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                int n3;
                int n4 = n2 - 96;
                fg fg2 = this;
                if (n4 == -1) {
                    n3 = -1;
                } else if (fg2.u < 0) {
                    fg2.i(0);
                    n3 = 0;
                } else {
                    int n5;
                    int n6 = fg2.v[fg2.u][n4];
                    if (n6 == -2) {
                        if (!fg2.j) {
                            fg2.d(false);
                        }
                    } else if (n6 != -1) {
                        int n7 = fg2.u;
                        fg2.u = n6;
                        int n8 = fg2.u / fg2.s;
                        if ((n7 /= fg2.s) < n8) {
                            n4 = fg2.i.b - fg2.w.b;
                            if ((n4 = n8 * (fg2.p + fg2.n) + fg2.l + fg2.p + n4) > fg2.d.b + fg2.d.d - 3 * fg2.o) {
                                ((ay)fg2.l()).j(fg2.o + fg2.n);
                            }
                        } else if (n7 > n8) {
                            n4 = fg2.i.b - fg2.w.b;
                            if ((n4 = n8 * (fg2.p + fg2.n) + fg2.l + n4) < fg2.d.b + fg2.o) {
                                ((ay)fg2.l()).j(-(fg2.o + fg2.n));
                            }
                        } else if (n4 == 2) {
                            ((ay)fg2.l()).j(-(fg2.o + fg2.n));
                        }
                        if (fg2.u >= fg2.r * fg2.s) {
                            fg2.u = fg2.r * fg2.s - 1;
                        }
                        fg2.i(fg2.u);
                    }
                    n3 = n5 = n6;
                }
                if (n3 >= 0) break;
                return false;
            }
            case 95: {
                if (this.B == null) break;
                this.B.b(this.r(), this.u);
            }
        }
        return true;
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        if (this.g) {
            this.i(-1);
        }
    }

    public final g v() {
        return new g(this.i.c, this.i.d);
    }

    public final int w() {
        return 10;
    }

    public final boolean c(int n2, int n3) {
        if (this.w == null) {
            this.w = ((ay)this.l()).q();
        }
        n2 -= this.i.a + this.m;
        n3 -= this.i.b + this.l;
        int n4 = 0;
        while (n4 < this.t.length) {
            if (this.t[n4].a(n2, n3)) {
                n3 = this.u;
                this.u = n4;
                n2 = n3 / this.s;
                n4 = this.u / this.s;
                if (n2 < n4) {
                    n2 = this.i.b - this.w.b;
                    if ((n2 = n4 * (this.p + this.n) + this.l + this.p + n2) > this.d.b + this.d.d - 3 * this.o) {
                        ((ay)this.l()).j(this.o + this.n);
                    }
                } else if (n2 > n4) {
                    n2 = this.i.b - this.w.b;
                    if ((n2 = n4 * (this.p + this.n) + this.l + n2) < this.d.b) {
                        ((ay)this.l()).j(-(this.o + this.n));
                    }
                }
                if (n3 != this.u) {
                    this.i(this.u);
                } else {
                    this.f(95);
                }
                return true;
            }
            ++n4;
        }
        return false;
    }

    public final boolean e(int n2, int n3) {
        ay ay2 = (ay)this.l();
        ay2.j(-n3);
        return true;
    }

    public final int q() {
        return this.u;
    }

    public final void j(int n2) {
        this.u = n2;
    }

    public final void a(Object object) {
        this.x.a(object);
        if (this.E) {
            b b2 = F;
            object = this.x;
            b b3 = b2;
            int n2 = ((a)object).d();
            boolean bl2 = false;
            a a2 = object;
            if (n2 > 0) {
                a2.a(g.a(a2.e(), 0, n2, b3));
            }
            this.x = a2;
        }
    }

    public final aq r() {
        return (aq)this.x.b(this.u);
    }

    public final aq k(int n2) {
        return (aq)this.x.b(n2);
    }

    public final void a(dc dc2) {
        this.x.b(dc2);
    }

    public final int s() {
        return this.x.d();
    }

    public final void a(Object object, int n2) {
        this.x.a(object, n2);
    }

    public final int b(Object object) {
        return this.x.c(object);
    }

    public final void t() {
        this.x.a();
    }

    public final k u() {
        return new k(this.t[this.u].a - this.w.a + this.i.a, this.t[this.u].b - this.w.b + this.i.b, this.o, this.p);
    }

    public final void l(int n2) {
        this.x.a(n2);
    }

    public final dc a(lm lm2) {
        int n2 = 0;
        while (n2 < this.x.d()) {
            dc dc2 = (dc)this.k(n2);
            if (dc2.j == 1 && dc2.k != null && lm2.a == ((lm)dc2.k).a) {
                return dc2;
            }
            ++n2;
        }
        return null;
    }

    public final dc a(String string) {
        int n2 = 0;
        while (n2 < this.x.d()) {
            dc dc2 = (dc)this.k(n2);
            if (dc2.j == 0 && dc2.k != null && string.equals(((ll)dc2.k).c)) {
                return dc2;
            }
            ++n2;
        }
        return null;
    }
}

