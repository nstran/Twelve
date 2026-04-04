/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class gy
extends ap
implements bj {
    private ax k;
    private ax l;
    private n m;
    private n n;
    private n o;
    private int p = 0;
    private int q = 0;
    private n r;
    private n s;
    private boolean t;
    private int u = 20;
    private int v = 8;
    private int w = 0;
    private int x = 0;
    private boolean y;
    private int z = 20;
    private boolean A = false;
    private lf B;
    private lf C;
    private oj D = null;
    private int E;
    private hg F = null;
    private String G;
    private String H;
    private String I;
    private boolean J = false;
    private int K = 2000;
    private bj L;
    private int M;
    private int N;

    /*
     * WARNING - void declaration
     */
    public gy(ax ax2, ax ax3, boolean bl2, boolean bl3, int n2, bj bj2) {
        super(1);
        try {
            void var4_5;
            void var6_9;
            this.M = 99030;
            gy n3 = this;
            this.L = var6_9;
            this.C = gr.j;
            this.a((be)null);
            this.z = 20;
            this.A = false;
            this.k = ax2;
            this.l = ax3;
            this.y = bl2;
            this.p = this.C.g;
            this.t = var4_5;
            int n4 = (this.f - 200) / 2;
            int n5 = (this.g - 164) / 2;
            this.m = new n(n4 - (this.u << 1), n5, 200, 68);
            this.n = new n((this.f - 20) / 2, n5 + 68 + 4, 20, 20);
            this.o = new n(n4 + (this.u << 1), n5 + 68 + 8 + 20, 200, 68);
            this.w = 4;
            this.x = this.o.c - 58 - this.w;
            this.r = new n(this.w, this.m.b + this.w, 58, 68 - (this.w << 1));
            this.s = new n(this.x, this.o.b + this.w, 58, 68 - (this.w << 1));
            this.H = com.mg.sq.a.a(this.C.b, ca.d, 100);
            if (!this.y) {
                if (var4_5 != false) {
                    ((aw)ax2).c(2);
                    ((kh)ax3).b(2);
                } else {
                    ((aw)ax2).c(0);
                    ((kh)ax3).b(3);
                }
                this.q = ((kh)ax3).f.c;
                this.N = ((kh)ax3).f.e;
                this.G = this.N < 3 ? "Si\u00eau g\u00e0" : (this.N < 7 ? "B\u1eddm" : (this.N < 10 ? "Ma lanh" : (this.N == 11 ? "T\u1ed1c chi\u1ebfn" : "Tuy\u1ec7t \u0111\u1ec9nh")));
                this.I = com.mg.sq.a.a(((kh)ax3).f.b, ca.d, 100);
            } else if (var4_5 != false) {
                ((aw)ax2).c(2);
                ((aw)ax3).c(0);
            } else {
                ((aw)ax2).c(0);
                ((aw)ax3).c(2);
            }
            this.E = ca.d.b() - oz.h.getHeight();
            this.F = new hg();
            this.F.c(this.n.a + this.n.c / 2, this.n.b + this.n.d / 2);
            this.F.a();
            this.b(true);
            this.b(191919);
            return;
        }
        catch (Throwable throwable) {
            cw.b("combat dialog");
            return;
        }
    }

    public gy(lf object, lf object2, boolean bl2, boolean bl3, int n2, bj bj2) {
        super(1);
        this.M = 99030;
        bj bj3 = bj2;
        gy gy2 = this;
        this.L = bj3;
        this.C = object;
        this.B = object2;
        this.a((be)null);
        this.z = 20;
        this.A = false;
        this.k = object = lz.a((lf)object, false);
        this.l = object2 = lz.a((lf)object2, false);
        this.y = true;
        this.p = this.C.g;
        this.t = bl3;
        int n3 = (this.f - 200) / 2;
        int n4 = (this.g - 164) / 2;
        this.m = new n(n3 - (this.u << 1), n4, 200, 68);
        this.n = new n((this.f - 20) / 2, n4 + 68 + 4, 20, 20);
        this.o = new n(n3 + (this.u << 1), n4 + 68 + 8 + 20, 200, 68);
        this.w = 4;
        this.x = this.o.c - 58 - this.w;
        this.r = new n(this.w, this.m.b + this.w, 58, 68 - (this.w << 1));
        this.s = new n(this.x, this.o.b + this.w, 58, 68 - (this.w << 1));
        this.H = com.mg.sq.a.a(this.C.b, ca.d, 100);
        if (bl3) {
            ((aw)object).c(2);
            ((aw)object2).c(0);
        } else {
            ((aw)object).c(0);
            ((aw)object2).c(2);
        }
        this.q = this.B.g;
        this.I = com.mg.sq.a.a(this.B.c, ca.d, 100);
        this.E = ca.d.b() - oz.h.getHeight();
        this.F = new hg();
        this.F.c(this.n.a + this.n.c / 2, this.n.b + this.n.d / 2);
        this.F.a();
        this.b(true);
        this.b(191919);
    }

    public final void a(lf lf2) {
        this.B = lf2;
        this.q = lf2.g;
        this.I = com.mg.sq.a.a(lf2.c, ca.d, 100);
    }

    protected final void g() {
        if (this.J) {
            return;
        }
        if (this.u > 0) {
            this.u -= this.v;
            this.m.a += this.v;
            this.o.a -= this.v;
            --this.v;
            if (this.v <= 0) {
                this.v = 1;
            }
            if (this.u < 0) {
                this.m.a += this.u;
                this.o.a -= this.u;
                this.u = 0;
            }
            this.r.a = this.m.a + this.w;
            this.s.a = this.o.a + this.x;
        } else {
            --this.z;
            if (this.z <= 0) {
                if (this.A) {
                    if (this.D != null) {
                        this.D = null;
                        System.gc();
                    }
                    if (this.L != null) {
                        this.L.d(com.mg.sq.a.q().d().h(), this.M);
                    }
                    ak.b().e(this.h());
                    this.A = false;
                    this.J = true;
                }
                this.z = 0;
            }
        }
        if (this.k != null) {
            this.k.i();
        }
        if (this.l != null) {
            this.l.i();
        }
        if (this.F != null) {
            this.F.i();
        }
        --this.K;
        if (this.K < 0) {
            this.J = true;
            ak.b().b(this, false);
            if (this.D != null && !this.y) {
                oj oj2 = this.D;
                if (oj2.o != null && oj2.o.b() == 1) {
                    ((ok)oj2.o).q();
                }
            }
        }
    }

    public final void c(Graphics graphics) {
        int n2;
        if (this.J) {
            return;
        }
        int n3 = z.ac;
        if (this.N == 11) {
            n3 = 0xFDBDBD;
        }
        oz.c(graphics, this.m.a, this.m.b, this.m.c, this.m.d, n3);
        oz.c(graphics, this.o.a, this.o.b, this.o.c, this.o.d, n3);
        graphics.drawRegion(oz.d, 39, 0, 68, 48, 2, this.m.a + this.m.c - 3, this.r.b + this.r.d, 40);
        graphics.drawRegion(oz.d, 39, 0, 68, 48, 0, this.o.a + 3, this.s.b + this.s.d, 36);
        n n4 = null;
        if (this.k != null) {
            if (!this.t) {
                n4 = this.s;
                n2 = -1;
            } else {
                n4 = this.r;
                n2 = 1;
            }
            if (this.k instanceof kh) {
                ((kh)this.k).b(graphics, n4.a + 4 + (n4.c - this.k.p()) / 2, n4.b + (n4.d - this.k.q()) / 2);
            } else {
                this.k.a(graphics, n4.a + 4 + (n4.c - this.k.p()) / 2, n4.b + (n4.d - this.k.q()) / 2);
            }
            this.a(graphics, n4, this.p, this.C.G, this.H, n2, this.C.S);
        }
        if (this.l != null) {
            if (!this.t) {
                n4 = this.r;
                n2 = 1;
            } else {
                n4 = this.s;
                n2 = -1;
            }
            if (this.l instanceof kh) {
                ((kh)this.l).b(graphics, n4.a + 4 + (n4.c - this.l.p()) / 2, n4.b + (n4.d - this.l.q()) / 2);
            } else {
                this.l.a(graphics, n4.a + 4 + (n4.c - this.l.p()) / 2, n4.b + n4.d - this.l.q() - 3);
            }
            if (!this.y) {
                kh kh2 = (kh)this.l;
                this.a(graphics, n4, this.q, kh2.f.d, this.I, n2, "IQ: " + this.G);
            } else if (this.B != null) {
                this.a(graphics, n4, this.q, this.B.G, this.I, n2, this.B.S);
            }
        }
        this.F.a(graphics);
    }

    private void a(Graphics graphics, n n2, int n3, int n4, String string, int n5, String string2) {
        int n6 = n2.a + n2.c + 4;
        int n7 = n2.b + 5 + 3;
        if (n5 == 1) {
            oz.b(graphics, n6, n7 + this.E, n3);
            ca.d.c(true);
            ca.d.a(graphics, string, n6 + 17, n7, 0);
            ca.d.c(false);
            ca.d.a(graphics, "C\u1ea5p: " + n4, n6, n7 += ca.d.b() + 5, 0);
            ca.d.a(graphics, string2, n6, n7 += ca.d.b() + 5, 0);
            return;
        }
        n6 = n2.a - 4;
        n7 = n2.b + 5 + 3;
        oz.b(graphics, n6 - oz.h.getWidth() / 3, n7 + this.E, n3);
        ca.d.c(true);
        ca.d.a(graphics, string, n6 - 17, n7, 2);
        ca.d.c(false);
        ca.d.a(graphics, "C\u1ea5p: " + n4, n6, n7 += ca.d.b() + 5, 2);
        ca.d.a(graphics, string2, n6, n7 += ca.d.b() + 5, 2);
    }

    public final void d(int n2, int n3) {
    }

    public final void t() {
        this.A = true;
    }

    public final void a(oj oj2) {
        this.D = oj2;
    }
}

