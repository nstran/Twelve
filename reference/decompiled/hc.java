/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class hc
extends ap
implements b {
    a k;
    int l = -1;
    private bd m = null;
    private bd n = null;
    private int[][] o;

    public hc() {
        super(1);
        this.a((be)null);
        this.k = new a();
    }

    public final void a(int[][] nArray) {
        this.o = nArray;
    }

    public final void a(au au2) {
        this.a(new au[]{au2});
    }

    public final void a(au[] object) {
        int n2 = 0;
        while (n2 < ((au[])object).length) {
            this.k.a(object[n2]);
            ++n2;
        }
        hc hc2 = this;
        object = this.k;
        hc hc3 = hc2;
        int n3 = ((a)object).d();
        boolean bl2 = false;
        Object object2 = this.k;
        if (n3 > 0) {
            Object[] objectArray = object2;
            hc hc4 = hc3;
            int n4 = n3;
            n3 = 0;
            if ((object2 = object2.e()) != null) {
                n4 = n3 + n4;
                ++n3;
                while (n3 < n4) {
                    int n5 = 0;
                    int n6 = n3 - 1;
                    int n7 = n6 / 2;
                    do {
                        int n8;
                        if ((n8 = hc4.a(object2[n7], object2[n3])) < 0) {
                            n5 = n7 + 1;
                        } else if (n8 > 0) {
                            n6 = n7;
                        } else {
                            n5 = n6 = n7 + 1;
                        }
                        n7 = (n6 + n5) / 2;
                    } while (n6 > n5);
                    if (n7 < n3 - 1) {
                        Object object3 = object2[n3];
                        n5 = n3;
                        while (n5 > n7) {
                            k.a(object2, n5, n5 - 1);
                            --n5;
                        }
                        object2[n7] = object3;
                    } else if (hc4.a(object2[n3 - 1], object2[n3]) > 0) {
                        k.a(object2, n3, n3 - 1);
                    }
                    ++n3;
                }
            }
            objectArray.a((Object[])object2);
        }
        n3 = 0;
        while (n3 < this.k.d()) {
            object2 = this.h(n3);
            if (object2.m()) {
                this.l = n3;
                return;
            }
            ++n3;
        }
    }

    public final void e(boolean n2) {
        super.e(n2 != 0);
        if (n2 != 0) {
            n2 = 0;
            while (n2 < this.k.d()) {
                this.h(n2).c(true);
                ++n2;
            }
        }
    }

    public final void a(bd bd2, boolean bl2) {
        bh bh2;
        if (bd2 == null) {
            super.a(bd2, bl2);
            this.m = null;
            return;
        }
        if (bd2 instanceof bh && l.b((bh2 = (bh)bd2).d())) {
            this.m = bd2;
            return;
        }
        super.a(bd2, bl2);
    }

    public final void b(bd bd2, boolean bl2) {
        bh bh2;
        if (bd2 == null) {
            super.b(bd2, bl2);
            this.n = null;
            return;
        }
        if (bd2 instanceof bh && l.b((bh2 = (bh)bd2).d())) {
            this.n = bd2;
            return;
        }
        super.b(bd2, bl2);
    }

    private au h(int n2) {
        return (au)this.k.b(n2);
    }

    public final au e(int n2) {
        int n3 = 0;
        while (n3 < this.k.d()) {
            au au2 = this.h(n3);
            if (au2.b() == n2) {
                return au2;
            }
            ++n3;
        }
        return null;
    }

    public final void f(int n2) {
        this.i(n2);
    }

    private au t() {
        if (this.l < 0) {
            return null;
        }
        return this.h(this.l);
    }

    public final boolean l() {
        if (this.k.d() > 0) {
            int n2 = 0;
            while (n2 < this.k.d()) {
                if (this.h(n2).k()) {
                    return true;
                }
                ++n2;
            }
        }
        return super.l();
    }

    public void c(Graphics graphics) {
        if (this.l()) {
            this.e(true);
            this.a(graphics);
        }
        int n2 = 0;
        while (n2 < this.k.d()) {
            this.h(n2).a(graphics, this.a(), this.c());
            ++n2;
        }
        this.h = false;
    }

    protected void a(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, true);
    }

    protected void g() {
        int n2 = 0;
        while (n2 < this.k.d()) {
            this.h(n2).n();
            ++n2;
        }
    }

    public void a(int n2, int n3) {
        n2 -= this.a();
        n3 -= this.c();
        int n4 = 0;
        while (n4 < this.k.d()) {
            au au2 = this.h(n4);
            if (au2.j() && au2.h().a(n2, n3)) {
                if (au2.j()) {
                    n2 = au2.c(n2, n3) ? 1 : 0;
                    if (n4 == this.l) {
                        if (n2 == 0) {
                            this.u();
                            return;
                        }
                    } else {
                        this.i(n4);
                        if (n2 == 0) {
                            this.u();
                        }
                    }
                }
                return;
            }
            ++n4;
        }
    }

    public final void b(int n2, int n3) {
        this.h(this.l).f(n2, n3);
    }

    public final void c(int n2, int n3) {
        this.h(this.l).e(n2, n3);
    }

    public final void g(int n2) {
        this.g = n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void c(int n2) {
        if (this.l < 0) {
            return;
        }
        int n3 = this.t().f(n2);
        if (n3 != 0) {
            return;
        }
        switch (n2) {
            case 97: {
                hc hc2 = this;
                if (hc2.o != null) {
                    n3 = hc2.o[hc2.l][2];
                    if (n3 < 0) {
                        n3 = hc2.l;
                    }
                    hc2.i(n3);
                    return;
                }
                n3 = hc2.l - 1;
                while (n3 >= 0) {
                    au au2 = hc2.h(n3);
                    if (au2.j()) {
                        hc2.i(n3);
                        return;
                    }
                    --n3;
                }
                return;
            }
            case 96: {
                hc hc3 = this;
                if (hc3.o != null) {
                    n3 = hc3.o[hc3.l][3];
                    if (n3 < 0) {
                        n3 = hc3.l;
                    }
                    hc3.i(n3);
                    return;
                }
                n3 = hc3.l + 1;
                while (n3 < hc3.k.d()) {
                    au au3 = hc3.h(n3);
                    if (au3.j()) {
                        hc3.i(n3);
                        return;
                    }
                    ++n3;
                }
                return;
            }
            case 99: {
                hc hc4 = this;
                if (hc4.o != null) {
                    n3 = hc4.o[hc4.l][0];
                    if (n3 < 0) {
                        n3 = hc4.l;
                    }
                    hc4.i(n3);
                    return;
                }
                au au4 = hc4.t();
                int n4 = au4.d();
                int n5 = au4.c() + au4.e() / 2;
                int n6 = hc4.l - 1;
                while (n6 >= 0) {
                    au4 = hc4.h(n6);
                    if (au4.j() && au4.d() < n4) {
                        int n7 = n6;
                        n4 = n6 - 1;
                        while (n4 >= 0) {
                            au au5 = hc4.h(n4);
                            if (au5.d() != au4.d()) break;
                            if (au4.b() > au5.b()) {
                                n7 = n6;
                            } else if (au4.b() < au5.b()) {
                                au4 = au5;
                                n7 = n4;
                            } else if (Math.abs(au5.c() + au5.e() / 2 - n5) < Math.abs(au4.c() + au4.e() / 2 - n5)) {
                                au4 = au5;
                                n7 = n4;
                            }
                            --n4;
                        }
                        hc4.i(n7);
                        return;
                    }
                    --n6;
                }
                return;
            }
            case 98: {
                hc hc5 = this;
                if (hc5.o != null) {
                    n3 = hc5.o[hc5.l][1];
                    if (n3 < 0) {
                        n3 = hc5.l;
                    }
                    hc5.i(n3);
                    return;
                }
                au au6 = hc5.t();
                int n8 = au6.d();
                int n9 = au6.c() + au6.e() / 2;
                int n10 = au6.f();
                int n11 = hc5.l + 1;
                while (n11 < hc5.k.d()) {
                    au6 = hc5.h(n11);
                    if (au6.j() && au6.d() + au6.f() > n8 + n10) {
                        n8 = n11;
                        int n12 = n11 + 1;
                        while (n12 < hc5.k.d()) {
                            au au7 = hc5.h(n12);
                            if (au7.d() != au6.d()) break;
                            if (au6.b() > au7.b()) {
                                au6 = au7;
                                n8 = n12;
                            } else if (au6.b() < au7.b()) {
                                n8 = n11;
                            } else if (Math.abs(au7.c() + au7.e() / 2 - n9) < Math.abs(au6.c() + au6.e() / 2 - n9)) {
                                au6 = au7;
                                n8 = n12;
                            }
                            ++n12;
                        }
                        hc5.i(n8);
                        return;
                    }
                    ++n11;
                }
                return;
            }
            case 95: {
                this.u();
                return;
            }
            case 94: {
                this.b(this.m);
                return;
            }
            case 93: {
                this.b(this.n);
            }
        }
    }

    public final void d(int n2) {
        if (this.l < 0) {
            return;
        }
        this.t().g(n2);
    }

    private void b(bd bd2) {
        if (bd2 == null || this.i == null) {
            return;
        }
        au au2 = this.t();
        if (au2 == null || !(au2 instanceof ff)) {
            this.i.d(this.e, bd2.a());
        }
    }

    private void u() {
        au au2 = this.t();
        if (au2 == null) {
            return;
        }
        if (this.i != null && au2 instanceof ex) {
            au2 = (ex)au2;
            this.i.d(this.e, ((ex)au2).a());
            return;
        }
    }

    private void i(int n2) {
        if (n2 != this.l) {
            if (this.l >= 0) {
                this.h(this.l).d(false);
            }
            this.l = n2;
            this.h(this.l).d(true);
        }
    }

    public final int a(Object object, Object object2) {
        object = (au)object;
        object2 = (au)object2;
        if (((au)object).d() > ((au)object2).d()) {
            return 1;
        }
        if (((au)object).d() == ((au)object2).d()) {
            if (((au)object).c() > ((au)object2).c()) {
                return 1;
            }
            if (((au)object).c() < ((au)object2).c()) {
                return -1;
            }
            return 0;
        }
        return -1;
    }
}

