/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class fw
extends fv
implements bf {
    private ll a;
    private byte b;
    private lm c;
    private d d;
    private String[] e;
    private boolean f;
    private k g;
    private boolean h;
    private String i;
    private String j;
    private fu k;
    private String s;
    private String[] t = null;
    private String[] u = new String[]{"\u0110\u1ed3 gi\u00e0nh cho NAM", "\u0110\u1ed3 gi\u00e0nh cho N\u1eee", "Gi\u00e0nh cho c\u1ea3 NAM & N\u1eee"};

    public fw(Object object) {
        if (object instanceof lm) {
            this.c = (lm)object;
            this.j = this.c.b;
            this.d = bx.d;
            this.a();
            return;
        }
        if (object instanceof ll) {
            this.a = (ll)object;
            this.j = this.a.d;
            this.b = (byte)this.a.j;
            if (v.t >= v.u && v.t == 320) {
                this.f = true;
            }
            int n2 = 0;
            if (this.a.i > go.k.G) {
                this.i = "Ch\u01b0a \u0111\u1ee7 c\u1ea5p \u0111\u1ed9 y\u00eau c\u1ea7u";
            } else {
                ++n2;
            }
            if (this.a.h == 2 || this.a.h == go.k.f) {
                ++n2;
            } else {
                this.i = this.u[this.a.h == 9 ? 2 : (int)this.a.h];
            }
            this.h = n2 == 2;
            this.d = ll.a(this.a.m);
            this.e = com.mg.sq.a.a(this.a);
            if (this.a.q > 0) {
                n2 = this.a.p * 100 / this.a.q < 30 ? 1 : 0;
                this.s = "\u0110\u1ed9 b\u1ec1n: " + this.a.p + "/" + this.a.q + (this.a.p == 0 ? " (\u0110\u00e3 h\u01b0 ho\u00e0n to\u00e0n)" : (n2 != 0 ? " (\u0110\u00e3 h\u01b0 h\u1ecfng n\u1eb7ng)" : ""));
            }
            this.a();
            return;
        }
        throw new IllegalArgumentException("Object must be instance GameItem or Equipment");
    }

    private void a() {
        if (v.z) {
            this.k = new fu(gb.f, 0);
            byte[] byArray = gb.e[3];
            this.k.b(byArray[0], byArray[1], byArray[2], byArray[3]);
            this.k.b(byArray[2], byArray[3]);
            this.k.a(this);
        }
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        if (this.c != null) {
            this.e = bx.a(this.c.d, this.o - 10, bx.d);
            this.i((this.d.a() << 1) + this.e.length * (bx.d.a() + 1) + 10);
        } else {
            if (this.e != null) {
                this.i((this.f ? (this.e.length + 1) / 2 : this.e.length) * bx.d.a() + (this.h ? 55 : 70) + 15);
            }
            if (this.a.g != null && !this.a.g.equals("")) {
                this.t = bx.a(this.a.g, this.o - 20);
                this.p += this.t.length * (bx.c.a() + 1);
            }
        }
        this.g = new k(5, 5, this.o - 10, this.p - 10);
        if (this.k != null) {
            this.k.a(this.o - this.k.e() - 2, 2, this.k.e(), this.k.f());
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        pc.b(graphics, n2 += this.m, n3 += this.n, this.p(), this.q(), v.aj, true);
        if (this.a != null) {
            int n4;
            if (this.a.f > 0) {
                pc.b(graphics, n2 + 5, n3 + 5 - (pc.h.getHeight() - bx.d.b()), this.a.f);
            }
            if (this.b <= 0) {
                this.d.a(graphics, this.j, n2 + 5 + 15, n3 + 5, 0);
            } else {
                this.d.a(graphics, String.valueOf(this.j) + " +" + this.b, n2 + 5 + 15, n3 + 5, 0);
            }
            int n5 = 5 + (bx.d.a() + 1);
            d d2 = this.a.i > go.k.G ? com.mg.sq.a.h : bx.c;
            d2.a(graphics, "Y\u00eau c\u1ea7u c\u1ea5p: " + this.a.i, n2 + 3, n3 + n5, 0);
            n5 += bx.c.a() + 1;
            d2 = this.a.p < 10 ? com.mg.sq.a.h : bx.c;
            if (this.s != null) {
                d2.a(graphics, this.s, n2 + 3, n3 + n5, 0);
                n5 += bx.c.a() + 1;
            }
            if (this.e != null) {
                n4 = this.e.length;
                d2 = com.mg.sq.a.g;
                if (this.f) {
                    n4 = (this.e.length + 1) / 2;
                    int n6 = n5;
                    int n7 = this.g.c / 2;
                    int n8 = 0;
                    while (n8 < this.e.length) {
                        d2.a(graphics, this.e[n8], n2 + 3 + n8 % 2 * n7, n3 + n6, 0);
                        n6 += n8 % 2 == 0 ? 0 : bx.c.a();
                        ++n8;
                    }
                } else {
                    bx.a(graphics, d2, this.e, n2 + 3, n3 + n5, this.g.c, this.g.d, 0);
                }
                n5 += n4 * d2.a() + 1;
            }
            if (this.t != null) {
                n4 = 0;
                while (n4 < this.t.length) {
                    bx.c.a(graphics, this.t[n4], n2 + 3, n3 + n5, 0);
                    n5 += d2.a() + 1;
                    ++n4;
                }
            }
            if (!this.h) {
                com.mg.sq.a.h.a(graphics, this.i, n2 + 3, n3 + n5, 0);
                n5 += com.mg.sq.a.h.a() + 1;
            }
            if (this.s != null) {
                String string = !this.a.c() ? "Kh\u00f4ng th\u1ec3 s\u1eeda ch\u1eefa" : "C\u1ea7n " + this.a.k + " b\u00faa \u0111\u1ec3 s\u1eeda ch\u1eefa";
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
            bx.a(graphics, bx.c, this.e, n2 + 3, n3 + n9, this.g.c, this.g.d, 0);
            n9 += this.e.length * bx.c.a() + 1;
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
            fw fw2 = this;
            this.r = false;
        }
    }
}

