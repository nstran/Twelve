/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bv
extends au {
    private static int i = 1;
    private static int j = 20;
    private int k;
    private boolean l = true;
    private bu[] m;
    private bv n;
    private int o;
    private int p;
    private bj q;
    private bk r;
    private bd[] s = new bd[3];

    public bv() {
    }

    public bv(bu[] buArray) {
        if (buArray != null) {
            this.a(buArray);
        }
    }

    public final void a(bd bd2) {
        boolean bl2 = true;
        bd bd3 = bd2;
        bv bv2 = this;
        bv2.s[0] = bd3;
        if (bd3 != null) {
            bv2.s[0].a(6, z.s - be.a + 3, 20);
        }
    }

    public final void b(bd bd2) {
        boolean bl2 = true;
        bd bd3 = bd2;
        bv bv2 = this;
        bv2.s[2] = bd3;
        if (bd3 != null) {
            bv2.s[2].a(z.r - 6, z.s - be.a + 3, 24);
        }
    }

    public final void c(bd bd2) {
        boolean bl2 = true;
        bd bd3 = bd2;
        bv bv2 = this;
        bv2.s[1] = bd3;
        if (bd3 != null) {
            bv2.s[1].a(z.r / 2, z.s - be.a + 3, 17);
        }
    }

    public final bd[] a() {
        return this.s;
    }

    public final int q() {
        if (this.n != null) {
            return -(this.k << 16 | this.n.q());
        }
        return this.k;
    }

    private int u() {
        while (bv2.n != null) {
            bv bv2 = bv2.n;
        }
        return bv2.m[bv2.k].c();
    }

    public final void a(bj bj2) {
        this.q = bj2;
    }

    public final void a(bk bk2) {
        this.r = bk2;
    }

    public final void a(bu[] buArray) {
        int n2 = 0;
        while (n2 < buArray.length) {
            this.a(buArray[n2]);
            ++n2;
        }
    }

    public final void a(bu bu2) {
        if (this.m != null) {
            bu[] buArray = new bu[this.m.length + 1];
            System.arraycopy(this.m, 0, buArray, 0, this.m.length);
            buArray[this.m.length] = bu2;
            this.e(this.f() + j);
            this.m = buArray;
        } else {
            this.m = new bu[1];
            this.m[0] = bu2;
            this.e(this.m.length * j + 18 + (i << 1));
        }
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.m.length) {
            if (this.m[n3].p() > n2) {
                n2 = this.m[n3].p();
            }
            ++n3;
        }
        if (n2 < 50) {
            n2 = 50;
        }
        this.d(n2 + (i << 1) + 16 + 18);
        this.k = 0;
    }

    public final void a_(int n2, int n3) {
        super.a_(n2, n3);
        this.d(n2, n3);
    }

    public final void d(int n2, int n3) {
        this.o = n2;
        this.p = n3;
    }

    public final void n() {
        while (true) {
            int n2 = 0;
            if (bv2.o != bv2.c()) {
                n2 = bv2.o - bv2.c();
                if (Math.abs(n2) > 4) {
                    n2 /= 2;
                }
                bv2.b_(bv2.c() + n2);
                n2 = 1;
            }
            if (bv2.p != bv2.d()) {
                n2 = bv2.p - bv2.d();
                if (Math.abs(n2) > 4) {
                    n2 /= 2;
                }
                bv2.c(bv2.d() + n2);
                n2 = 1;
            }
            if (n2 != 0) {
                bv2.c(true);
            }
            if (bv2.n == null) break;
            bv bv2 = bv2.n;
        }
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (this.l() != null && bl2) {
            this.l().c(true);
        }
    }

    public final boolean k() {
        if (this.b == null) {
            this.c = true;
        }
        if (this.b != null) {
            return this.b.k() || super.k();
        }
        return super.k();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        while (true) {
            int n4 = bv2.c() + n2;
            int n5 = bv2.d() + n3;
            ak.c().b(graphics, n4, n5, bv2.e(), bv2.f());
            n4 += 4 + i;
            n5 += 10 + i;
            int n6 = bv2.e() - 8 - (i << 1);
            int n7 = 0;
            while (n7 < bv2.m.length) {
                boolean bl2 = n7 == bv2.k;
                if (bl2) {
                    ak.c().c(graphics, n4, n5, n6, j);
                }
                bv2.m[n7].a(graphics, n4 + 14, n5 + 3, bl2);
                if (bv2.m[n7].e()) {
                    if (bl2) {
                        ca.c.a(graphics, ">", n4 + n6 - 3, n5 + 3, 2);
                    } else {
                        ca.d.a(graphics, ">", n4 + n6 - 4, n5 + 2, 2);
                    }
                }
                n5 += j;
                ++n7;
            }
            if (bv2.n == null) break;
            bv bv2 = bv2.n;
        }
    }

    private void v() {
        this.n = new bv(this.m[this.k].d());
        int n2 = this.c() + 8 + i;
        int n3 = this.d() + 10 + i + this.k * j + 5;
        if (n3 + this.n.f() > this.d() + this.f()) {
            n3 = this.d() + this.f() - this.n.f();
        }
        this.n.a_(z.r, n3);
        this.n.d(n2, n3);
        Object object = this.q;
        bv bv2 = this.n;
        this.n.q = object;
        object = this.r;
        bv2 = this.n;
        this.n.r = object;
        this.n.a(this);
    }

    public final boolean f(int n2) {
        if (this.b != null) {
            this.b.c(true);
        }
        if (this.m == null) {
            return false;
        }
        if (this.o != this.c() || this.p != this.d()) {
            this.b_(this.o);
            this.c(this.p);
        }
        if (this.n != null) {
            if (!this.n.f(n2)) {
                this.n = null;
                this.c(true);
                return false;
            }
            return true;
        }
        int n3 = this.k;
        switch (n2) {
            case 99: {
                if (n3 > 0) {
                    --n3;
                    break;
                }
                if (!this.l) break;
                n3 = this.m.length - 1;
                break;
            }
            case 98: {
                if (n3 < this.m.length - 1) {
                    ++n3;
                    break;
                }
                if (!this.l) break;
                n3 = 0;
                break;
            }
            case 97: {
                bv bv2 = this;
                if (bv2.b instanceof bv) {
                    ((bv)bv2.b).n = null;
                    return true;
                }
                return false;
            }
            case 96: {
                if (this.m[this.k].e()) {
                    this.v();
                    return true;
                }
                return false;
            }
            case 95: {
                if (this.m[this.k].e()) {
                    this.v();
                } else {
                    if (this.q != null) {
                        z.c();
                        this.q.d(0, this.u());
                    }
                    if (this.r != null) {
                        z.c();
                        this.r.a(0, this.u(), this.r());
                    }
                }
                return true;
            }
        }
        if (n3 != this.k) {
            this.k = n3;
            this.c(true);
            return true;
        }
        return false;
    }

    public final boolean c(int n2, int n3) {
        if (this.b != null) {
            this.b.c(true);
        }
        if (this.o != this.c() || this.p != this.d()) {
            this.b_(this.o);
            this.c(this.p);
        }
        if (this.n != null) {
            boolean bl2 = this.n.c(n2, n3);
            if (!bl2) {
                this.n = null;
                this.c(true);
                return false;
            }
            return true;
        }
        int n4 = this.c() + 4 + i;
        int n5 = this.d() + 10 + i;
        int n6 = this.e() - 8 - (i << 1);
        int n7 = 0;
        while (n7 < this.m.length) {
            if (n2 >= n4 && n2 <= n4 + n6 && n3 >= n5 && n3 <= n5 + j) {
                this.k = n7;
                this.c(true);
                return this.f(95);
            }
            n5 += j;
            ++n7;
        }
        if (this.d.a(n2, n3)) {
            return true;
        }
        bv bv2 = this.g(n2, n3);
        if (bv2 != null) {
            bv2.n = null;
            return true;
        }
        return false;
    }

    private bv g(int n2, int n3) {
        while (bv2.b instanceof bv) {
            if (!bv2.b.h().a(n2, n3)) {
                bv bv2 = (bv)bv2.b;
                continue;
            }
            return (bv)bv2.b;
        }
        return null;
    }

    public final bu r() {
        return this.m[this.k];
    }

    public final bu[] s() {
        return this.m;
    }

    public final void t() {
        z.c();
        this.n = null;
        this.k = 0;
    }
}

