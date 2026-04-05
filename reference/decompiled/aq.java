/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class aq {
    protected int e;
    protected int f;
    protected int g;
    protected boolean h = true;
    private boolean a = true;
    protected bj i;
    private be b = new be();
    protected bd[] j = new bd[3];
    private int[] c = new int[3];
    private int d;
    private int k;
    private int l = 13;
    private boolean m = true;
    private boolean n = false;

    public aq() {
        this.f = z.r;
        this.g = z.s;
    }

    public final int h() {
        return this.e;
    }

    public final void b(int n2) {
        this.e = n2;
    }

    public final int i() {
        return this.f;
    }

    public final int j() {
        return this.g;
    }

    public void a(bj bj2) {
        this.i = bj2;
    }

    public final boolean k() {
        return this.a;
    }

    public final void d(boolean bl2) {
        this.a = false;
    }

    public boolean l() {
        return this.h;
    }

    public void e(boolean bl2) {
        this.h = bl2;
    }

    public final void f(boolean bl2) {
        this.n = bl2;
    }

    public final void g(boolean bl2) {
        this.m = false;
    }

    public final void a(be be2) {
        this.b = be2;
    }

    public final void m() {
        int n2 = 0;
        while (n2 < this.j.length) {
            this.j[n2] = null;
            this.c[n2] = 0;
            ++n2;
        }
    }

    public final void n() {
        this.j[0] = null;
        this.c[0] = 0;
    }

    public final void o() {
        this.j[1] = null;
        this.c[1] = 0;
    }

    protected void d(Graphics graphics) {
        if (this.b != null) {
            this.b.a(graphics);
        }
        int n2 = 0;
        while (n2 < this.j.length) {
            if (this.j[n2] != null) {
                this.j[n2].a(graphics);
            }
            ++n2;
        }
    }

    public final void a(Graphics graphics, boolean bl2) {
        this.c(graphics);
        if (bl2) {
            this.d(graphics);
        }
    }

    public final void h(boolean bl2) {
        int n2 = 0;
        while (n2 < this.j.length) {
            if (this.j[n2] != null) {
                this.j[n2].c();
                if (this.c[n2] > 0) {
                    int n3 = n2;
                    this.c[n3] = this.c[n3] - 1;
                    if (this.c[n2] == 0 && !this.j[n2].b(this.e) && this.i != null) {
                        this.i.d(this.e, this.j[n2].a());
                    }
                }
            }
            ++n2;
        }
        if (bl2) {
            aq aq2 = this;
            if (z.x) {
                int n4;
                int n5;
                int n6;
                n2 = z.y;
                int n7 = z.z;
                if (n2 >= 0 && n7 >= 0) {
                    n6 = ak.b;
                    ak.c = false;
                    n5 = n7;
                    n7 = n2;
                    aq aq3 = aq2;
                    n4 = 0;
                    aq3.d = n5;
                    aq3.k = n7;
                    if (aq3.p() && aq3.j[0].a(n7, n5)) {
                        z.c[94] = 2;
                        n4 = 1;
                    }
                    if (aq3.a() && aq3.j[2].a(n7, n5)) {
                        z.c[93] = 2;
                        n4 = 1;
                    }
                    if (aq3.q() && aq3.j[1].a(n7, n5)) {
                        z.c[95] = 2;
                        n4 = 1;
                    }
                    if (n4 == 0) {
                        aq3.a(n7, n5);
                    }
                    z.z = -1;
                    z.y = -1;
                    if (n6 != 0 && !ak.c) {
                        ak.a().f();
                    }
                } else {
                    n2 = z.C;
                    n7 = z.D;
                    if (n2 >= 0 && n7 >= 0) {
                        n5 = n7;
                        n7 = n2;
                        aq aq4 = aq2;
                        n4 = n7 - aq4.k;
                        n6 = n5 - aq4.d;
                        aq4.d = n5;
                        aq4.k = n7;
                        aq4.c(n4, n6);
                        z.D = -1;
                        z.C = -1;
                    }
                }
                int n8 = z.A;
                n7 = z.B;
                if (n8 >= 0 && n7 >= 0) {
                    n5 = n7;
                    n7 = n8;
                    aq aq5 = aq2;
                    aq5.b(n7, n5);
                    z.B = -1;
                    z.A = -1;
                }
            }
            aq2 = this;
            int n9 = 299;
            while (n9 >= 0) {
                if (z.c[n9] != 0) {
                    if (z.c[n9] == 1) {
                        aq2.d(n9);
                        z.c[n9] = 0;
                    } else if (z.c[n9] == 2) {
                        if (!aq2.a(n9)) {
                            aq2.c(n9);
                        }
                        z.c[n9] = 0;
                    } else if (z.c[n9] >= 3) {
                        if (!(z.c[n9] <= aq2.l && z.c[n9] != 3 || aq2.a(n9) || !aq2.m && z.c[n9] != 3)) {
                            aq2.c(n9);
                        }
                        if (z.c[n9] < Integer.MAX_VALUE) {
                            int n10 = n9;
                            z.c[n10] = z.c[n10] + 1;
                        }
                    }
                    if (!aq2.n) break;
                }
                --n9;
            }
        }
        this.b_();
    }

    public final boolean p() {
        return this.j[0] != null;
    }

    private boolean a() {
        return this.j[2] != null;
    }

    public final boolean q() {
        return this.j[1] != null;
    }

    public void a(bd bd2, boolean bl2) {
        this.j[0] = bd2;
        this.c[0] = 0;
        if (bd2 == null) {
            return;
        }
        if (bl2) {
            this.j[0].a(6, z.s - be.a + 3, 20);
        }
    }

    public void b(bd bd2, boolean bl2) {
        this.j[2] = bd2;
        this.c[2] = 0;
        if (bd2 == null) {
            return;
        }
        if (bl2) {
            this.j[2].a(z.r - 6, z.s - be.a + 3, 24);
        }
    }

    public void a(bd bd2) {
        boolean bl2 = true;
        bd bd3 = bd2;
        aq aq2 = this;
        aq2.j[1] = bd3;
        aq2.c[1] = 0;
        if (bd3 != null) {
            aq2.j[1].a(z.r / 2, z.s - be.a + 3, 17);
        }
    }

    private boolean a(int n2) {
        boolean bl2 = false;
        if (this.p() && n2 == 94) {
            if (this.c[0] == 0) {
                bl2 = this.j[0].b();
                this.c[0] = 3;
            }
        } else if (this.a() && n2 == 93) {
            if (this.c[2] == 0) {
                bl2 = this.j[2].b();
                this.c[2] = 3;
            }
        } else if (this.q() && n2 == 95 && this.c[1] == 0) {
            bl2 = this.j[1].b();
            this.c[1] = 3;
        }
        return bl2;
    }

    public void c(int n2) {
    }

    public void d(int n2) {
    }

    public void a(int n2, int n3) {
    }

    public void b(int n2, int n3) {
    }

    public void c(int n2, int n3) {
    }

    protected void r() {
    }

    protected void s() {
    }

    public void i(boolean bl2) {
        this.e(true);
    }

    public abstract void b_();

    public abstract void c(Graphics var1);
}

