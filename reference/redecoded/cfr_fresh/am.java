/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class am {
    protected int e;
    protected int f;
    protected int g;
    protected boolean h = true;
    private boolean a = true;
    protected bf i;
    private ba b = new ba();
    protected az[] j = new az[3];
    private int[] c = new int[3];
    private int d;
    private int k;
    private int l = 13;
    private boolean m = true;
    private boolean n = false;

    public am() {
        this.f = v.t;
        this.g = v.u;
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

    public void a(bf bf2) {
        this.i = bf2;
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

    public final void a(ba ba2) {
        this.b = ba2;
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
            am am2 = this;
            if (v.z) {
                int n4;
                int n5;
                int n6;
                n2 = v.A;
                int n7 = v.B;
                if (n2 >= 0 && n7 >= 0) {
                    n6 = ag.b;
                    ag.c = false;
                    n5 = n7;
                    n7 = n2;
                    am am3 = am2;
                    n4 = 0;
                    am3.d = n5;
                    am3.k = n7;
                    if (am3.p() && am3.j[0].a(n7, n5)) {
                        v.c[94] = 2;
                        n4 = 1;
                    }
                    if (am3.a() && am3.j[2].a(n7, n5)) {
                        v.c[93] = 2;
                        n4 = 1;
                    }
                    if (am3.q() && am3.j[1].a(n7, n5)) {
                        v.c[95] = 2;
                        n4 = 1;
                    }
                    if (n4 == 0) {
                        am3.a(n7, n5);
                    }
                    v.B = -1;
                    v.A = -1;
                    if (n6 != 0 && !ag.c) {
                        ag.a().e();
                    }
                } else {
                    n2 = v.E;
                    n7 = v.F;
                    if (n2 >= 0 && n7 >= 0) {
                        n5 = n7;
                        n7 = n2;
                        am am4 = am2;
                        n4 = n7 - am4.k;
                        n6 = n5 - am4.d;
                        am4.d = n5;
                        am4.k = n7;
                        am4.c(n4, n6);
                        v.F = -1;
                        v.E = -1;
                    }
                }
                int n8 = v.C;
                n7 = v.D;
                if (n8 >= 0 && n7 >= 0) {
                    n5 = n7;
                    n7 = n8;
                    am am5 = am2;
                    am5.b(n7, n5);
                    v.D = -1;
                    v.C = -1;
                }
            }
            am2 = this;
            int n9 = 299;
            while (n9 >= 0) {
                if (v.c[n9] != 0) {
                    if (v.c[n9] == 1) {
                        am2.d(n9);
                        v.c[n9] = 0;
                    } else if (v.c[n9] == 2) {
                        if (!am2.a(n9)) {
                            am2.c(n9);
                        }
                        v.c[n9] = 0;
                    } else if (v.c[n9] >= 3) {
                        if (!(v.c[n9] <= am2.l && v.c[n9] != 3 || am2.a(n9) || !am2.m && v.c[n9] != 3)) {
                            am2.c(n9);
                        }
                        if (v.c[n9] < Integer.MAX_VALUE) {
                            int n10 = n9;
                            v.c[n10] = v.c[n10] + 1;
                        }
                    }
                    if (!am2.n) break;
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

    public void a(az az2, boolean bl2) {
        this.j[0] = az2;
        this.c[0] = 0;
        if (az2 == null) {
            return;
        }
        if (bl2) {
            this.j[0].a(6, v.u - ba.a + 3, 20);
        }
    }

    public void b(az az2, boolean bl2) {
        this.j[2] = az2;
        this.c[2] = 0;
        if (az2 == null) {
            return;
        }
        if (bl2) {
            this.j[2].a(v.t - 6, v.u - ba.a + 3, 24);
        }
    }

    public void a(az az2) {
        boolean bl2 = true;
        az az3 = az2;
        am am2 = this;
        am2.j[1] = az3;
        am2.c[1] = 0;
        if (az3 != null) {
            am2.j[1].a(v.t / 2, v.u - ba.a + 3, 17);
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

