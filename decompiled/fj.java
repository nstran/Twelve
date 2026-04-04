/*
 * Decompiled with CFR 0.152.
 */
public final class fj
extends fm {
    public boolean i;

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.j = new m(n4, 0);
    }

    public final synchronized void b(au au2) {
        au2.a_(0, this.j.b);
        this.j.b += au2.f() + 1;
        super.b(au2);
    }

    public final synchronized void a(au au2, int n2) {
        au2.a_(0, this.j.b);
        this.j.b += au2.f() + 1;
        super.a(au2, n2);
    }

    public final synchronized void c(au au2) {
        this.j.b -= au2.f() + 1;
        super.c(au2);
    }

    public final boolean f(int n2) {
        if (this.l < 0) {
            return false;
        }
        int n3 = this.u().f(n2);
        if (n3 != 0) {
            return true;
        }
        switch (n2) {
            case 99: {
                fj fj2 = this;
                if (fj2.b == null) {
                    if (fj2.i) {
                        n3 = fj2.l - 1;
                        if (fj2.l < 0) {
                            fj2.l = fj2.r() - 1;
                        }
                        fj2.i(n3);
                        return true;
                    }
                    if (fj2.l > 0) {
                        fj2.i(fj2.l - 1);
                        return true;
                    }
                    return false;
                }
                bc bc2 = (bc)fj2.b;
                n n4 = fj2.x();
                return fj2.a(bc2, n4, -1);
            }
            case 98: {
                fj fj3 = this;
                if (fj3.b == null) {
                    if (fj3.i) {
                        n3 = fj3.l + 1;
                        if (n3 >= fj3.r()) {
                            n3 = 0;
                        }
                        fj3.i(n3);
                        return true;
                    }
                    if (fj3.l < fj3.r() - 1) {
                        fj3.i(fj3.l - 1);
                        return true;
                    }
                    return false;
                }
                bc bc3 = (bc)fj3.b;
                n n5 = fj3.x();
                return fj3.a(bc3, n5, 1);
            }
            case 95: {
                if (this.n == null) break;
                this.n.b(this.u(), this.l);
            }
        }
        return false;
    }

    private n x() {
        if (this.b != null) {
            return ((bc)this.b).r();
        }
        return new n(0, 0, this.e(), this.f());
    }

    public final boolean g(int n2) {
        au au2 = this.u();
        if (au2 != null) {
            return au2.g(n2);
        }
        return false;
    }

    public final boolean c(int n2, int n3) {
        n2 -= this.c();
        n3 -= this.d();
        au au2 = null;
        int n4 = 0;
        while (n4 < this.k.d()) {
            au2 = (au)this.k.b(n4);
            if (au2 != null && au2.j() && au2.h().a(n2, n3)) {
                int n5;
                if (n4 != this.l) {
                    this.i(n4);
                    if (this.b != null && this.b instanceof bc) {
                        n5 = 1;
                        int n6 = 0;
                        while (n6 <= this.l) {
                            n5 += this.j(n6).f() + 1;
                            ++n6;
                        }
                        n6 = n5 - this.u().f() - 1;
                        if (n5 > this.m.b + this.m.d) {
                            ((bc)this.l()).j(n5 - (this.m.b + this.m.d));
                        }
                        if (n6 < this.m.b) {
                            ((bc)this.l()).j(n6 - this.m.b);
                        }
                    }
                }
                if ((n5 = au2.c(n2, n3)) != 0) {
                    return true;
                }
                this.f(95);
            }
            ++n4;
        }
        return false;
    }

    public final boolean f(int n2, int n3) {
        n2 -= this.c();
        n3 -= this.d();
        au au2 = this.u();
        if (au2 != null) {
            return au2.f(n2, n3);
        }
        return false;
    }

    public final boolean e(int n2, int n3) {
        if (this.b != null && this.b instanceof bc) {
            ((bc)this.l()).j(n3);
            return true;
        }
        return false;
    }

    public final void a() {
        this.j.b = 0;
        au au2 = null;
        int n2 = 0;
        int n3 = this.k.d();
        while (n2 < n3) {
            au2 = (au)this.k.b(n2);
            au2.a_(0, this.j.b);
            this.j.b += au2.f() + 1;
            ++n2;
        }
        this.c(true);
    }

    private int d(int n2, int n3) {
        int n4 = 0;
        while (n2 <= n3) {
            au au2 = (au)this.k.b(n2++);
            n4 += au2.f() + 1;
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean a(bc bc2, n n2, int n3) {
        int n4;
        block16: {
            block17: {
                int n5;
                int n6;
                block14: {
                    block15: {
                        n6 = this.d(0, this.l - 1);
                        au au2 = (au)this.k.b(this.l);
                        n5 = au2.f();
                        n4 = 0;
                        if (n3 >= 0) break block14;
                        if (this.l <= 0) break block15;
                        au au3 = (au)this.k.b(this.l - 1);
                        n3 = au3.f();
                        int n7 = n6 - 1 - n3;
                        if (n7 + n3 <= n2.b + 5) {
                            bc2.d(2, -1);
                            return true;
                        }
                        this.i(this.l - 1);
                        n6 = this.d(0, this.l - 1);
                        au3 = (au)this.k.b(this.l);
                        int n8 = au3.f();
                        if (n6 <= n2.b) {
                            n4 = n6 - n2.b;
                            if (n8 >= n2.d) {
                                n4 += n8 - n2.d;
                                break block16;
                            } else if (this.l > 0) {
                                au au4 = (au)this.k.b(this.l - 1);
                                n4 = n6 - n4 + au4.f() + n8 > n2.b + n2.d ? (n4 -= n2.d - n8) : (n4 -= au4.f());
                            }
                        }
                        break block16;
                    }
                    if (this.i) {
                        this.i(this.r() - 1);
                        bc2.g(true);
                        return true;
                    }
                    if (bc2.t()) {
                        return false;
                    }
                    bc2.d(2, -1);
                    return true;
                }
                if (this.l >= this.r() - 1) break block17;
                n3 = n6 + n5 + 1;
                if (n3 >= n2.b + n2.d - 5) {
                    bc2.d(2, 1);
                    return true;
                }
                this.i(this.l + 1);
                n6 = this.d(0, this.l - 1);
                au au5 = (au)this.k.b(this.l);
                int n9 = au5.f();
                if (n6 + n9 >= n2.b + n2.d) {
                    n4 = n6 + n9 - n2.b - n2.d;
                    if (n9 >= n2.d) {
                        n4 -= n9 - n2.d;
                        break block16;
                    } else if (this.l < this.r() - 1) {
                        au au6 = (au)this.k.b(this.l + 1);
                        n4 = n6 - n4 - au6.f() < n2.b ? (n4 += n2.d - n9) : (n4 += au6.f());
                    }
                }
                break block16;
            }
            if (this.i) {
                this.i(0);
                bc2.f(true);
                return true;
            }
            if (bc2.s()) {
                return false;
            }
            bc2.d(2, 1);
            return true;
        }
        bc2.j(n4);
        return true;
    }
}

