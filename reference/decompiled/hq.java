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

public final class hq
extends ap
implements bj {
    private n u;
    private cx v = null;
    private cx w;
    private n x;
    private n y;
    private n z;
    private cx A;
    private cx B;
    private cx C;
    private cx D;
    private cx E;
    public static int k;
    public static int l;
    public static int m;
    public static int n;
    public static int o;
    public static int[] p;
    public static int[] q;
    public static boolean r;
    public static int s;
    public static int t;
    private int F = 75;
    private int G;
    private lf H;
    private Image I;
    private Image J;
    private Image K;
    private int L;
    private int M;
    private int N = 3;
    private String O = null;
    private boolean P;
    private String Q;
    private int R;
    private int S;
    private bd T = null;
    private boolean U = true;
    private hh V;
    private hh W;
    private hh X;
    private cs Y;
    private boolean Z = true;
    private int[] aa = new int[]{7930113, 12386818, 13894146, 0xFD0D0D, 0xFD0D0D, 12911106, 12911106, 10289666, 10289666, 9372162, 9372162, 9372162, 9372162, 9372162};
    private int[] ab = new int[]{227841, 375554, 381954, 719882, 719882, 377602, 377602, 3710992, 236033, 236033, 231937, 231937, 231937, 231937};
    private int[] ac = new int[]{14067456, 15579392, 15579392, 16765211, 16765211, 14593280, 14593280, 12095488, 12095488, 12095488, 11109376, 11109376, 11109376, 11109376};
    private Image ad = null;

    static {
        l = 1;
    }

    public hq(int n2, lf lf2) {
        super(1);
        this.a(this);
        this.a(new be());
        this.u = new n(this.f - 184 >> 1, this.g - 186 >> 1, 184, 186);
        this.v = new cx(8, 7);
        this.w = new cx(42, 5);
        this.y = new n(30, 27, 144, 15);
        this.x = new n(30, this.y.b + this.y.d + 4, 144, 15);
        this.z = new n(30, this.x.b + this.x.d + 4, 144, 15);
        this.F = this.z.b + this.z.d + 8;
        this.A = new cx(8, this.F + 4 + 2);
        this.C = new cx(19, this.A.b + 19 + 7);
        this.D = new cx(19, this.C.b + 19);
        this.B = new cx(8, this.D.b + 10);
        this.E = new cx(19, this.B.b + 19 + 10);
        this.I = f.d("/info/gold");
        this.K = f.d("/info/heart");
        this.J = f.d("/info/expicon");
        this.u.d = this.E.b + this.J.getHeight() + 8;
        this.u.b = this.g - this.u.d >> 1;
        this.H = lf2;
        this.G = 0;
        if (!r) {
            this.O = "\u0110i\u1ec3m Ph\u1ea1t";
            this.P = true;
            this.Q = " - ";
        } else {
            this.P = false;
            this.O = "Th\u01b0\u1edfng";
            this.Q = "+";
        }
        this.N = (int)(o.a((long)t) * 7L / 100L);
        this.S = l;
        this.L = p[this.G];
        this.M = q[this.G];
        this.b(-241219);
        if (lf2.G > this.S && com.mg.sq.a.r()) {
            this.Y = new cs("lvu");
        }
        if (r) {
            this.ad = f.d("/strwin");
            return;
        }
        this.ad = f.d("/strlose");
    }

    protected final void s() {
        if (this.Y != null) {
            this.Y.a();
        }
    }

    public final void c(Graphics graphics) {
        oz.a(graphics, this.u.a, this.u.b, this.u.c, this.u.d, z.ac, false);
        if (this.ad != null) {
            cz.a(graphics, this.ad, 0, 0, this.ad.getWidth() / 3, this.ad.getHeight(), this.u.a + this.u.c - 5, this.u.b + this.u.d - 5, 40);
        }
        ca.d.c(true);
        ca.d.a(graphics, "C\u1ea5p: ", this.v.a + this.u.a, this.v.b + this.u.b, 0);
        int n2 = this.v.b + ca.d.b();
        ca.d.c(false);
        ca.d.a(graphics, String.valueOf(this.S), this.w.a + this.u.a, (n2 -= ca.d.b()) + this.u.b, 0);
        oz.a(graphics, this.x.a + this.u.a, this.x.b + this.u.b, this.x.c, this.x.d, 6729912, 16579546);
        oz.a(graphics, this.y.a + this.u.a, this.y.b + this.u.b, this.y.c, this.y.d, 6729912, 16579546);
        oz.a(graphics, this.z.a + this.u.a, this.z.b + this.u.b, this.y.c, this.y.d, 6729912, 16579546);
        oz.a(graphics, this.y, this.H.s * (this.y.c - 2) / this.H.r, this.aa, this.u.a, this.u.b);
        ca.d.a(graphics, String.valueOf(l.a(this.H.s, ".")) + "/" + l.a(this.H.r, "."), this.y.a + (this.y.c >> 1) + this.u.a, this.y.b + this.u.b + 1, 1);
        n2 = k - this.L;
        int n3 = this.M + 1 - this.L;
        if (n2 > n3) {
            n2 = n3;
        }
        oz.a(graphics, this.x, n2 * (this.x.c - 1) / n3, this.ab, this.u.a, this.u.b);
        n2 = n2 * 1000 / n3;
        if (n2 < 0) {
            n2 = 0;
        }
        ca.d.a(graphics, String.valueOf(n2 / 10) + "," + n2 % 10 + "%", this.x.a + (this.x.c >> 1) + this.u.a, this.x.b + this.u.b + 1, 1);
        graphics.drawImage(this.J, this.x.a - 11 + this.u.a, this.x.b + this.x.d + this.u.b, 33);
        graphics.drawImage(this.K, this.y.a - 11 + this.u.a, this.y.b + this.y.d + this.u.b, 33);
        graphics.drawImage(this.I, this.z.a - 11 + this.u.a, this.z.b + this.z.d + this.u.b, 33);
        n2 = m * this.z.c / this.H.I;
        if (n2 > this.z.c) {
            n2 %= this.z.c;
        }
        oz.a(graphics, this.z, n2, this.ac, this.u.a, this.u.b);
        ca.d.a(graphics, String.valueOf(l.a(m, ".")) + "/" + l.a(this.H.I, "."), this.z.a + (this.z.c >> 1) + this.u.a, this.z.b + this.u.b + 1, 1);
        oz.a(graphics, this.u.a + 3, this.u.b + this.F, this.u.c - 6);
        ca.d.c(true);
        ca.d.a(graphics, "\u0110i\u1ec3m Thu Th\u1eadp", this.A.a + this.u.a, this.A.b + this.u.b, 0);
        ca.d.c(false);
        graphics.drawImage(this.J, this.D.a + this.u.a, this.D.b + this.u.b, 33);
        String string = n <= 0 ? "   " : this.Q;
        ca.d.a(graphics, String.valueOf(string) + n, this.D.a + 10 + this.u.a, this.D.b + this.u.b - ca.d.a() + 3, 0);
        graphics.drawImage(this.I, this.C.a + this.u.a, this.C.b + this.u.b, 33);
        string = o <= 0 ? "   " : this.Q;
        ca.d.a(graphics, String.valueOf(string) + o, this.C.a + 10 + this.u.a, this.C.b + this.u.b - ca.d.a() + 3, 0);
        ca.d.c(true);
        ca.d.a(graphics, this.O, this.B.a + this.u.a, this.B.b + this.u.b, 0);
        ca.d.c(false);
        graphics.drawImage(this.J, this.E.a + this.u.a, this.E.b + this.u.b, 33);
        string = t <= 0 ? "   " : this.Q;
        ca.d.a(graphics, String.valueOf(string) + t, this.E.a + 10 + this.u.a, this.E.b + this.u.b - ca.d.a() + 3, 0);
        if (this.W != null) {
            this.W.a(graphics, 0, 0);
        }
        if (this.V != null) {
            this.V.a(graphics, 0, 0);
        }
        if (this.X != null) {
            this.X.a(graphics, 0, 0);
        }
    }

    public final void a(bd bd2) {
        super.a(bd2);
        if (this.T == null) {
            this.T = bd2;
            super.a((bd)null);
        }
    }

    protected final void g() {
        super.g();
        if (this.U) {
            ++this.R;
            if (this.R == 10) {
                this.a(this.T);
                this.U = false;
                this.R = 0;
            }
        } else {
            if (!this.P) {
                int n2 = this.N = n > 1 ? n / 2 : 1;
                if (n > 0) {
                    k += this.N;
                    if ((n -= this.N) < 0) {
                        k += n;
                        n = 0;
                    }
                    if (k > this.M) {
                        ++this.G;
                        this.L = p[this.G];
                        this.M = q[this.G];
                        ++this.S;
                        if (this.W == null) {
                            this.W = new hh(this.u.a + this.x.a + this.x.c / 2, this.u.b + this.x.b + this.x.d / 2);
                            this.X = new hh(this.w.a + this.u.a, this.v.b + ca.d.b() + this.u.b);
                            this.X.a();
                            this.W.a();
                        }
                    }
                }
                int n3 = this.N = o > 1 ? o / 2 : 1;
                if (o > 0) {
                    m += this.N;
                    if ((o -= this.N) < 0) {
                        m += o;
                        o = 0;
                    }
                }
                if (o <= 0 && n <= 0) {
                    this.P = true;
                }
            } else if (r) {
                int n4 = this.N = t > 1 ? t / 2 : 1;
                if (t > 0) {
                    k += this.N;
                    if ((t -= this.N) < 0) {
                        k += t;
                        t = 0;
                    }
                    if (k > this.M) {
                        ++this.G;
                        this.L = p[this.G];
                        this.M = q[this.G];
                        ++this.S;
                        if (this.W == null) {
                            this.W = new hh(this.u.a + this.x.a + this.x.c / 2, this.u.b + this.x.b + this.x.d / 2);
                            this.X = new hh(this.w.a + this.u.a, this.v.b + ca.d.b() + this.u.b);
                            this.X.a();
                            this.W.a();
                        }
                    }
                }
                int n5 = this.N = s > 1 ? s / 2 : 1;
                if (s > 0) {
                    m += this.N;
                    if ((s -= this.N) < 0) {
                        m += s;
                        s = 0;
                    }
                }
            } else {
                int n6 = (int)o.a((long)t);
                int n7 = this.N = n6 > 1 ? n6 / 2 : 1;
                if (t < 0) {
                    k -= this.N;
                    if ((t += this.N) > 0) {
                        k += t;
                        t = 0;
                    }
                    if (k <= this.L) {
                        t = 0;
                        k = this.L;
                    }
                }
                int n8 = this.N = (n6 = (int)o.a((long)s)) > 1 ? n6 / 2 : 1;
                if (s < 0) {
                    m -= this.N;
                    if ((s += this.N) < 0) {
                        m += s;
                        s = 0;
                    }
                }
            }
            if (m >= this.H.I) {
                m -= this.H.I;
                if (this.V == null) {
                    this.V = new hh(this.u.a + this.z.a + this.z.c / 2, this.u.b + this.z.b + this.z.d / 2);
                    this.V.a();
                }
            }
        }
        if (this.W != null) {
            if (this.Z) {
                if (this.Y != null) {
                    this.Y.b();
                }
                this.Z = false;
            }
            this.W.i();
        }
        if (this.V != null) {
            this.V.i();
        }
        if (this.X != null) {
            this.X.i();
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 0: {
                o = 0;
                s = 0;
                t = 0;
                n = 0;
                k = 0;
                l = 0;
                ak.b().e(-241219);
            }
        }
    }

    public static void t() {
        p = null;
        q = null;
    }
}

