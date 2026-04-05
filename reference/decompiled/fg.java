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
extends au
implements bx {
    private byte l = (byte)6;
    private int m = 2;
    private int n = 32;
    private int o = 32;
    private int p = 20;
    private int q = 0;
    private int r = 0;
    private n[] s;
    private int t = 0;
    private int[][] u;
    public n i;
    private n v = null;
    private a w = new a();
    public boolean j = false;
    private int x = 0;
    private int y;
    private df z;
    public Image k = null;
    private bt A;
    private boolean B = false;
    private Image C;

    public fg(boolean bl2) {
        this.B = bl2;
        this.k = f.d("/slotlock");
        this.C = f.d("/m/lock2");
    }

    public final void a(au au2) {
        super.a(au2);
        if (this.v == null && au2 instanceof bc) {
            this.v = ((bc)au2).q();
        }
    }

    public final void a(bt bt2) {
        this.A = bt2;
    }

    public final void a(df df2) {
        this.z = df2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        oz.b(graphics, this.d.a + n2, this.d.b + n3, this.d.c, this.d.d, z.ac, false);
        cz.a(graphics, cz.a);
        cz.a(graphics, this.d.a + n2, this.d.b + 1 + n3, this.d.c, this.d.d - 2);
        if (this.v == null) {
            this.v = ((bc)this.l()).q();
        }
        int n4 = this.i.a - this.v.a;
        int n5 = this.i.b - this.v.b;
        n2 += n4 + this.l;
        n3 += n5 + 6;
        Object object = null;
        int n6 = this.w.d();
        int n7 = 0;
        while (n7 < this.s.length) {
            if (this.s[n7].b(this.v.a, this.v.b, this.v.c, this.v.d)) {
                n4 = this.s[n7].a + n2;
                n5 = this.s[n7].b + n3;
                if (n7 < this.p) {
                    if (n7 >= gr.m) {
                        oz.b(graphics, n4, n5, this.s[n7].c, this.s[n7].d, 0xFF0000, 0xFFFFFF, 15385573);
                    } else {
                        oz.b(graphics, n4, n5, this.s[n7].c, this.s[n7].d, 6647295, 0xFFFFFF, 8369663);
                    }
                } else if (this.k != null) {
                    graphics.drawImage(this.k, n4, n5, 0);
                } else {
                    oz.b(graphics, n4, n5, this.s[n7].c, this.s[n7].d, 0x787881, 0xFFFFFF, 11382450);
                }
                if (n7 < n6 && n7 < this.p && (object = this.w.b(n7)) != null) {
                    ((au)object).a(graphics, n4, n5);
                    if (n7 >= gr.m) {
                        graphics.drawImage(this.C, n4, n5, 0);
                    }
                }
            }
            ++n7;
        }
        try {
            if (this.g && this.t >= 0) {
                oz.a(graphics, this.s[this.t], n2, n3, this.y);
                if (this.z != null) {
                    this.z.a(graphics, this.s[this.t].a + n2, this.s[this.t].b + this.s[this.t].d + n3);
                }
            }
        }
        catch (Exception exception) {
            System.err.println(this.t);
        }
        cz.c(graphics, cz.a);
    }

    public final void h(int n2) {
        this.p = n2;
    }

    public final int a() {
        return this.p;
    }

    public final void d(int n2, int n3) {
        this.p = n2;
        this.r = this.i.c / (this.n + this.m);
        this.q = n2 / this.r + (n2 % this.r > 0 ? 1 : 0) + n3;
        this.s = new n[this.r * this.q];
        n2 = 0;
        while (n2 < this.q) {
            n3 = 0;
            while (n3 < this.r) {
                this.s[n2 * this.r + n3] = new n(n3 * (this.n + this.m), n2 * (this.o + this.m), this.n, this.o);
                ++n3;
            }
            ++n2;
        }
        this.i.d = this.q * (this.o + this.m) + 12;
        this.l = (byte)((this.i.c - this.r * (this.n + this.m)) / 2);
        fg fg2 = this;
        this.u = new int[fg2.q * fg2.r][5];
        n3 = 0;
        if (fg2.B) {
            int n4 = 0;
            while (n4 < fg2.q) {
                int n5 = 0;
                while (n5 < fg2.r) {
                    n3 = n4 * fg2.r + n5;
                    int[] nArray = new int[4];
                    int n6 = nArray[0] = n3 < fg2.u.length - 1 ? n3 + 1 : -1;
                    nArray[1] = n5 == 0 ? -2 : (n3 > 0 ? n3 - 1 : -1);
                    nArray[2] = n4 < fg2.q - 1 ? n3 + fg2.r : -1;
                    nArray[3] = n4 > 0 ? n3 - fg2.r : -2;
                    fg2.u[n3] = nArray;
                    ++n5;
                }
                ++n4;
            }
            return;
        }
        int n7 = 0;
        while (n7 < fg2.q) {
            int n8 = 0;
            while (n8 < fg2.r) {
                n3 = n7 * fg2.r + n8;
                fg2.u[n3] = new int[]{n3 < fg2.u.length - 1 ? n3 + 1 : -1, n3 > 0 ? n3 - 1 : -1, n7 < fg2.q - 1 ? n3 + fg2.r : -1, n7 > 0 ? n3 - fg2.r : -2};
                ++n8;
            }
            ++n7;
        }
    }

    public final void i(int n2) {
        this.t = n2;
        if (n2 < 0 || this.v == null || this.i == null) {
            return;
        }
        n2 = this.t / this.r;
        int n3 = this.i.b - this.v.b;
        if ((n2 = n2 * (this.o + this.m) + 6 + n3) < this.d.b) {
            ((bc)this.l()).j(n2 - this.d.b - 6);
        }
        if (this.A != null) {
            this.A.a(this.r(), this.t);
        }
    }

    public final void n() {
        ++this.x;
        if (this.x >= 5) {
            this.y = this.y == 0 ? -2 : 0;
            this.x = 0;
        }
        int n2 = 0;
        int n3 = this.w.d();
        while (n2 < n3) {
            if (this.w.b(n2) != null) {
                dg dg2 = (dg)this.w.b(n2);
                dg2.n();
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
                } else if (fg2.t < 0) {
                    fg2.i(0);
                    n3 = 0;
                } else {
                    int n5;
                    int n6 = fg2.u[fg2.t][n4];
                    if (n6 == -2) {
                        if (!fg2.j) {
                            fg2.d(false);
                        }
                    } else if (n6 != -1) {
                        int n7 = 0;
                        n7 = 0;
                        n7 = fg2.t;
                        fg2.t = n6;
                        int n8 = fg2.t / fg2.r;
                        if ((n7 /= fg2.r) < n8) {
                            n7 = fg2.i.b - fg2.v.b;
                            if ((n7 = n8 * (fg2.o + fg2.m) + 6 + fg2.o + n7) > fg2.d.b + fg2.d.d - 3 * fg2.n) {
                                ((bc)fg2.l()).j(fg2.n + fg2.m);
                            }
                        } else if (n7 > n8) {
                            n7 = fg2.i.b - fg2.v.b;
                            if ((n7 = n8 * (fg2.o + fg2.m) + 6 + n7) < fg2.d.b + fg2.n) {
                                ((bc)fg2.l()).j(-(fg2.n + fg2.m));
                            }
                        } else if (n4 == 2) {
                            ((bc)fg2.l()).j(-(fg2.n + fg2.m));
                        }
                        if (fg2.t >= fg2.q * fg2.r) {
                            fg2.t = fg2.q * fg2.r - 1;
                        }
                        fg2.i(fg2.t);
                    }
                    n3 = n5 = n6;
                }
                if (n3 >= 0) break;
                return false;
            }
            case 95: {
                if (this.A == null) break;
                this.A.b(this.r(), this.t);
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

    public final m v() {
        return new m(this.i.c, this.i.d);
    }

    public final int w() {
        return 10;
    }

    public final boolean c(int n2, int n3) {
        if (this.v == null) {
            this.v = ((bc)this.l()).q();
        }
        n2 -= this.i.a + this.l;
        n3 -= this.i.b + 6;
        int n4 = 0;
        while (n4 < this.s.length) {
            if (this.s[n4].a(n2, n3)) {
                n2 = 0;
                n2 = 0;
                n3 = this.t;
                this.t = n4;
                n2 = n3 / this.r;
                n4 = this.t / this.r;
                if (n2 < n4) {
                    n2 = this.i.b - this.v.b;
                    if ((n2 = n4 * (this.o + this.m) + 6 + this.o + n2) > this.d.b + this.d.d - 3 * this.n) {
                        ((bc)this.l()).j(this.n + this.m);
                    }
                } else if (n2 > n4) {
                    n2 = this.i.b - this.v.b;
                    if ((n2 = n4 * (this.o + this.m) + 6 + n2) < this.d.b) {
                        ((bc)this.l()).j(-(this.n + this.m));
                    }
                }
                if (n3 != this.t) {
                    this.i(this.t);
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
        bc bc2 = (bc)this.l();
        bc2.j(-n3);
        return true;
    }

    public final int q() {
        return this.t;
    }

    public final void j(int n2) {
        this.t = n2;
    }

    public final void a(Object object) {
        this.w.a(object);
    }

    public final au r() {
        return (au)this.w.b(this.t);
    }

    public final au k(int n2) {
        return (au)this.w.b(n2);
    }

    public final void a(dg dg2) {
        this.w.b(dg2);
    }

    public final int s() {
        return this.w.d();
    }

    public final void a(Object object, int n2) {
        this.w.a(object, n2);
    }

    public final int b(Object object) {
        return this.w.c(object);
    }

    public final void t() {
        this.w.a();
    }

    public final n u() {
        return new n(this.s[this.t].a - this.v.a + this.i.a, this.s[this.t].b - this.v.b + this.i.b, this.n, this.o);
    }

    public final void l(int n2) {
        this.w.a(n2);
    }

    public final dg a(lk lk2) {
        int n2 = 0;
        while (n2 < this.w.d()) {
            dg dg2 = (dg)this.k(n2);
            if (dg2.j == 1 && dg2.k != null && lk2.a == ((lk)dg2.k).a) {
                return dg2;
            }
            ++n2;
        }
        return null;
    }

    public final dg a(String string) {
        int n2 = 0;
        while (n2 < this.w.d()) {
            dg dg2 = (dg)this.k(n2);
            if (dg2.j == 0 && dg2.k != null && string.equals(((lj)dg2.k).b)) {
                return dg2;
            }
            ++n2;
        }
        return null;
    }
}

