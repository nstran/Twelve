/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class fd
extends au
implements bx {
    private int i;
    private int j;
    private int k;
    private int l;
    private bc m;
    private int n;
    private int o;
    private n p = new n();
    private ih q;
    private boolean r = false;
    private int s;

    public fd(int n2, int n3) {
        this.n = n2;
        this.o = n3;
        this.m = new bc();
        this.m.b(this);
        this.m.e(false);
    }

    public final int a() {
        return this.s;
    }

    public final void e(boolean bl2) {
        this.r = true;
    }

    public final void a(ih ih2) {
        this.q = ih2;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        this.j = 4;
        this.i = 4;
        this.m.a(new g(5, 5, 5, 5));
    }

    public final void d(int n2, int n3) {
        this.k = n2;
        this.l = n3;
    }

    /*
     * Unable to fully structure code
     */
    public final void a(Graphics var1_1, int var2_2, int var3_3) {
        if (!this.c) {
            return;
        }
        var2_2 += this.c();
        var3_3 += this.d();
        var4_4 = this.m.q();
        var5_5 = this.m.a();
        if (this.q != null) {
            this.q.b(var1_1, var2_2 - var5_5.b, var3_3 - var5_5.a, this.e() + var5_5.b + var5_5.d, this.f() + var5_5.a + var5_5.c);
        }
        cz.a(var1_1, this.p);
        cz.a(var1_1, this.p, var2_2, var3_3, this.e(), this.f());
        var5_6 = var4_4.b + var4_4.d;
        var2_2 -= var4_4.a;
        var3_3 -= var4_4.b;
        var6_7 = 0;
        while (var6_7 < this.o) {
            block7: {
                block6: {
                    var7_8 = var6_7 / this.n;
                    var8_9 = var6_7 % this.n;
                    if (var4_4.b(var8_9 = (this.i + this.k) * var8_9, var7_8 = (this.j + this.l) * var7_8, this.k, this.l)) break block6;
                    if (var7_8 + this.l > var5_6) {
                        break;
                    }
                    break block7;
                }
                if (this.q == null) break block7;
                var8_9 = var2_2 + var8_9;
                var7_8 = var3_3 + var7_8;
                if (var6_7 != this.s) ** GOTO lbl32
                if (!this.r) {
                    this.q.a(var1_1, var8_9, var7_8, this.k, this.l, var6_7);
                    this.q.a(var1_1, var8_9, var7_8, this.k, this.l);
                } else {
                    this.q.a(var1_1, var8_9, var7_8, this.k, this.l);
lbl32:
                    // 2 sources

                    this.q.a(var1_1, var8_9, var7_8, this.k, this.l, var6_7);
                }
            }
            ++var6_7;
        }
        cz.c(var1_1, this.p);
        this.c = false;
    }

    private int g(int n2, int n3) {
        if (n3 < 0) {
            return 0;
        }
        return n3 * (this.l + this.j);
    }

    private int h(int n2, int n3) {
        if (n3 < 0) {
            return 0;
        }
        return n3 * (this.k + this.i);
    }

    private boolean a(bc au2, n object, int n2, int n3) {
        if (2 == n2) {
            if (n3 < 0) {
                if (this.s >= this.n) {
                    n2 = this.g(0, this.s / this.n);
                    if (n2 > ((n)object).b + this.j) {
                        n3 = this.s - this.n;
                        fd fd2 = this;
                        this.s = n3;
                        this.a((bc)au2, (n)object);
                        return true;
                    }
                    ((bc)au2).d(2, -1);
                    return true;
                }
                if (((bc)au2).t()) {
                    return false;
                }
                ((bc)au2).d(2, -1);
                return true;
            }
            n2 = this.s + this.n;
            if (n2 >= this.o) {
                n2 = this.o - 1;
            }
            if (n2 / this.n != this.s / this.n) {
                n3 = this.g(0, this.s / this.n);
                if (n3 + this.l + this.j < ((n)object).b + ((n)object).d) {
                    n3 = n2;
                    fd fd3 = this;
                    this.s = n3;
                    this.b((bc)au2, (n)object);
                    return true;
                }
                ((bc)au2).d(2, 1);
                return true;
            }
            if (((bc)au2).s()) {
                return false;
            }
            ((bc)au2).d(2, 1);
            return true;
        }
        if (n3 < 0) {
            if (this.s > 0) {
                if (this.s % this.n == 0) {
                    ((bc)au2).i(true);
                    n3 = this.s - 1;
                    fd fd4 = this;
                    this.s = n3;
                    this.a((bc)au2, (n)object);
                    return true;
                }
                n2 = this.h(0, this.s % this.n);
                if (n2 > ((n)object).a + this.i) {
                    n3 = this.s - 1;
                    Object object2 = this;
                    this.s = n3;
                    object2 = object;
                    object = au2;
                    au2 = this;
                    n3 = ((fd)au2).g(0, ((fd)au2).s % ((fd)au2).n);
                    if (n3 <= ((n)object2).a) {
                        int n4 = n3 - ((n)object2).a;
                        if (((fd)au2).k >= ((n)object2).c) {
                            n4 += ((fd)au2).k - ((n)object2).c;
                        } else if (((fd)au2).s % ((fd)au2).n > 0) {
                            n4 = n3 - n4 + ((fd)au2).k + ((fd)au2).i + ((fd)au2).k > ((n)object2).a + ((n)object2).c ? (n4 -= ((n)object2).c - ((fd)au2).k - ((fd)au2).i) : (n4 -= ((fd)au2).k);
                        }
                        ((bc)object).i(n4);
                    }
                    return true;
                }
                ((bc)au2).d(1, -1);
                return true;
            }
            if (((bc)au2).u()) {
                return false;
            }
            ((bc)au2).d(1, -1);
            return true;
        }
        if (this.s < this.o - 1) {
            if (this.s % this.n == this.n - 1) {
                ((bc)au2).h(true);
                n3 = this.s + 1;
                fd fd5 = this;
                this.s = n3;
                this.b((bc)au2, (n)object);
                return true;
            }
            n2 = this.h(0, this.s % this.n);
            if (n2 + this.k + this.i < ((n)object).a + ((n)object).c) {
                n3 = this.s + 1;
                Object object3 = this;
                this.s = n3;
                object3 = object;
                object = au2;
                au2 = this;
                n3 = ((fd)au2).g(0, ((fd)au2).s % ((fd)au2).n);
                if (n3 + ((fd)au2).k + ((fd)au2).i > ((n)object3).a + ((n)object3).c) {
                    int n5 = n3 + ((fd)au2).k + ((fd)au2).i - (((n)object3).a + ((n)object3).c);
                    if (((fd)au2).k >= ((n)object3).c) {
                        n5 -= ((fd)au2).k - ((n)object3).c;
                    } else if (((fd)au2).s % ((fd)au2).n < ((fd)au2).n - 1) {
                        n5 = n3 - n5 - ((fd)au2).k - ((fd)au2).i < ((n)object3).a ? (n5 += ((n)object3).c - ((fd)au2).k - ((fd)au2).i) : (n5 += ((fd)au2).k + ((fd)au2).i);
                    }
                    ((bc)object).i(n5);
                }
                return true;
            }
            ((bc)au2).d(1, 1);
            return true;
        }
        if (((bc)au2).v()) {
            return false;
        }
        ((bc)au2).d(1, 1);
        return true;
    }

    private void a(bc bc2, n n2) {
        int n3 = this.g(0, this.s / this.n);
        if (n3 <= n2.b) {
            int n4 = n3 - n2.b;
            if (this.l >= n2.d) {
                n4 += this.l - n2.d;
            } else if (this.s >= this.n) {
                n4 = n3 - n4 + this.l + this.j + this.l > n2.b + n2.d ? (n4 -= n2.d - this.l - this.j) : (n4 -= this.l);
            }
            bc2.j(n4);
        }
    }

    private void b(bc bc2, n n2) {
        int n3 = this.g(0, this.s / this.n);
        if (n3 + this.l + this.j > n2.b + n2.d) {
            int n4 = n3 + this.l + this.j - (n2.b + n2.d);
            if (this.l >= n2.d) {
                n4 -= this.l - n2.d;
            } else {
                int n5 = this.s + this.n;
                if (n5 >= this.o) {
                    n5 = this.o - 1;
                }
                if (n5 / this.n != this.s / this.n) {
                    n4 = n3 - n4 - this.l - this.j < n2.b ? (n4 += n2.d - this.l - this.j) : (n4 += this.l + this.j);
                }
            }
            bc2.j(n4);
        }
    }

    public final boolean f(int n2) {
        if (n2 >= 96 && n2 <= 99) {
            n n3 = this.m.r();
            switch (n2) {
                case 99: {
                    return this.a(this.m, n3, 2, -1);
                }
                case 98: {
                    return this.a(this.m, n3, 2, 1);
                }
                case 97: {
                    return this.a(this.m, n3, 1, -1);
                }
                case 96: {
                    return this.a(this.m, n3, 1, 1);
                }
            }
        } else if (n2 == 95) {
            if (this.q != null && this.s >= 0 && this.s < this.o) {
                this.q.t();
            }
            return true;
        }
        return false;
    }

    public final boolean c(int n2, int n3) {
        int n4 = n3 - this.d();
        int n5 = n2 - this.c();
        n4 /= this.l + this.j;
        if ((n4 = n4 * this.n + (n5 /= this.k + this.i)) < this.o) {
            if (n4 != this.s) {
                n3 = n4;
                fd fd2 = this;
                this.s = n3;
            } else {
                this.f(95);
            }
            return true;
        }
        return super.c(n2, n3);
    }

    public final au q() {
        return this.m;
    }

    public final m v() {
        int n2 = this.o / this.n + (this.o % this.n != 0 ? 1 : 0);
        return new m(this.n * this.k + (this.n - 1) * this.i, n2 * this.l + (n2 - 1) * this.j);
    }

    public final int w() {
        return 10;
    }
}

