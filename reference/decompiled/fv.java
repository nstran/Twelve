/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class fv
extends fu
implements bj {
    private lj a;
    private byte b;
    private lk c;
    private d d;
    private String[] e;
    private boolean f;
    private n g;
    private boolean h;
    private String i;
    private String j;
    private ft k;
    private String s;
    private String[] t = null;
    private String[] u = new String[]{"\u0110\u1ed3 gi\u00e0nh cho NAM", "\u0110\u1ed3 gi\u00e0nh cho N\u1eee", "Gi\u00e0nh cho c\u1ea3 NAM & N\u1eee"};

    public fv(Object object) {
        if (object instanceof lk) {
            this.c = (lk)object;
            this.j = this.c.b;
            this.d = ca.d;
            this.a();
            return;
        }
        if (object instanceof lj) {
            this.a = (lj)object;
            this.j = this.a.c;
            this.b = (byte)this.a.i;
            if (z.r >= z.s && z.r == 320) {
                this.f = true;
            }
            int n2 = 0;
            if (this.a.h > gr.j.G) {
                this.i = "Ch\u01b0a \u0111\u1ee7 c\u1ea5p \u0111\u1ed9 y\u00eau c\u1ea7u";
            } else {
                ++n2;
            }
            if (this.a.g == 2 || this.a.g == gr.j.f) {
                ++n2;
            } else {
                this.i = this.u[this.a.g == 9 ? 2 : (int)this.a.g];
            }
            this.h = n2 == 2;
            this.d = lj.a(this.a.l);
            this.e = com.mg.sq.a.a(this.a);
            if (this.a.o > 0) {
                n2 = this.a.n * 100 / this.a.o < 30 ? 1 : 0;
                this.s = "\u0110\u1ed9 b\u1ec1n: " + this.a.n + "/" + this.a.o + (this.a.n == 0 ? " (\u0110\u00e3 h\u01b0 ho\u00e0n to\u00e0n)" : (n2 != 0 ? " (\u0110\u00e3 h\u01b0 h\u1ecfng n\u1eb7ng)" : ""));
            }
            this.a();
            return;
        }
        throw new IllegalArgumentException("Object must be instance GameItem or Equipment");
    }

    private void a() {
        if (z.x) {
            this.k = new ft(ga.f, 0);
            byte[] byArray = ga.e[3];
            this.k.b(byArray[0], byArray[1], byArray[2], byArray[3]);
            this.k.b(byArray[2], byArray[3]);
            this.k.a(this);
        }
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        if (this.c != null) {
            this.e = ca.a(this.c.d, this.o - 10, ca.d);
            this.i((this.d.a() << 1) + this.e.length * (ca.d.a() + 1) + 10);
        } else {
            if (this.e != null) {
                this.i((this.f ? (this.e.length + 1) / 2 : this.e.length) * ca.d.a() + (this.h ? 55 : 70) + 15);
            }
            if (this.a.f != null && !this.a.f.equals("")) {
                this.t = ca.a(this.a.f, this.o - 20);
                this.p += this.t.length * (ca.c.a() + 1);
            }
        }
        this.g = new n(5, 5, this.o - 10, this.p - 10);
        if (this.k != null) {
            this.k.a(this.o - this.k.e() - 2, 2, this.k.e(), this.k.f());
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        oz.b(graphics, n2 += this.m, n3 += this.n, this.p(), this.q(), z.ac, true);
        d d2 = null;
        if (this.a != null) {
            int n4;
            if (this.a.e > 0) {
                oz.b(graphics, n2 + 5, n3 + 5 - (oz.h.getHeight() - ca.d.b()), this.a.e);
            }
            if (this.b <= 0) {
                this.d.a(graphics, this.j, n2 + 5 + 15, n3 + 5, 0);
            } else {
                this.d.a(graphics, String.valueOf(this.j) + " +" + this.b, n2 + 5 + 15, n3 + 5, 0);
            }
            int n5 = 5 + (ca.d.a() + 1);
            d2 = this.a.h > gr.j.G ? com.mg.sq.a.h : ca.c;
            d2.a(graphics, "Y\u00eau c\u1ea7u c\u1ea5p: " + this.a.h, n2 + 3, n3 + n5, 0);
            n5 += ca.c.a() + 1;
            d2 = this.a.n < 10 ? com.mg.sq.a.h : ca.c;
            if (this.s != null) {
                d2.a(graphics, this.s, n2 + 3, n3 + n5, 0);
                n5 += ca.c.a() + 1;
            }
            int n6 = 0;
            if (this.e != null) {
                n4 = this.e.length;
                d2 = com.mg.sq.a.g;
                if (this.f) {
                    n4 = (this.e.length + 1) / 2;
                    n6 = n5;
                    int n7 = this.g.c / 2;
                    int n8 = 0;
                    while (n8 < this.e.length) {
                        d2.a(graphics, this.e[n8], n2 + 3 + n8 % 2 * n7, n3 + n6, 0);
                        n6 += n8 % 2 == 0 ? 0 : ca.c.a();
                        ++n8;
                    }
                } else {
                    ca.a(graphics, d2, this.e, n2 + 3, n3 + n5, this.g.c, this.g.d, 0);
                }
                n5 += n4 * d2.a() + 1;
            }
            if (this.t != null) {
                n4 = 0;
                while (n4 < this.t.length) {
                    ca.c.a(graphics, this.t[n4], n2 + 3, n3 + n5, 0);
                    n5 += d2.a() + 1;
                    ++n4;
                }
            }
            if (!this.h) {
                com.mg.sq.a.h.a(graphics, this.i, n2 + 3, n3 + n5, 0);
                n5 += com.mg.sq.a.h.a() + 1;
            }
            if (this.s != null) {
                String string = !this.a.c() ? "Kh\u00f4ng th\u1ec3 s\u1eeda ch\u1eefa" : "C\u1ea7n " + this.a.j + " b\u00faa \u0111\u1ec3 s\u1eeda ch\u1eefa";
                com.mg.sq.a.h.a(graphics, string, n2 + 3, n3 + n5, 0);
                n5 += n5 + (com.mg.sq.a.h.a() + 1);
            }
            if (!this.a.a()) {
                com.mg.sq.a.h.a(graphics, "Kh\u00f4ng th\u1ec3 giao d\u1ecbch", n2 + 3, n3 + n5, 0);
            }
        } else {
            this.d.c(true);
            this.d.a(graphics, this.j, n2 + 5, n3 + 5, 0);
            this.d.c(false);
            int n9 = 5 + (this.d.a() + 1);
            ca.a(graphics, ca.c, this.e, n2 + 3, n3 + n9, this.g.c, this.g.d, 0);
            n9 += this.e.length * ca.c.a() + 1;
            if (!this.c.a()) {
                com.mg.sq.a.h.a(graphics, "Kh\u00f4ng th\u1ec3 giao d\u1ecbch", n2 + 3, n3 + n9, 0);
            }
            switch (this.c.e) {
                case 3: {
                    break;
                }
                case 2: {
                    break;
                }
                case 0: {
                    com.mg.sq.a.h.a(graphics, "Ch\u1ec9 s\u1eed d\u1ee5ng trong tr\u1eadn \u0111\u1ea5u", n2 + 3, n3 + n9, 0);
                }
            }
        }
        if (this.k != null) {
            this.k.a(graphics, n2, n3);
        }
    }

    public final void d(int n2, int n3) {
        this.r = false;
    }

    public final void g(int n2, int n3) {
        if (this.k != null && this.k.h().a(n2 - this.n(), n3 - this.o())) {
            n2 = this.k.a();
            n2 = -1;
            fv fv2 = this;
            this.r = false;
        }
    }
}

