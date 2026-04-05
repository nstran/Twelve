/*
 * Decompiled with CFR 0.152.
 */
public final class mm
extends mo {
    private int x;
    private int y;
    private int z;
    private int A;
    private int B;

    public mm(mq mq2, mr mr2, op op2) {
        super(mq2, mr2, op2);
        this.o = 6;
        cw.a("[playingController]=======================Fighting========================");
    }

    public final void b(int n2) {
        if (this.r) {
            if (n2 >= 148 && n2 <= 157) {
                this.r = false;
                this.e(n2 - 148);
                return;
            }
        } else if (z.a() && n2 == 142 || !z.a() && n2 == 135) {
            this.r = true;
        }
        mr mr2 = this.b;
        if (mr2.d.i()) {
            if (n2 == 99) {
                this.b.b();
                return;
            }
            if (n2 == 95) {
                this.b.a(this.b.d.r());
                this.b.d.c("");
                this.b.b();
                return;
            }
            this.b.d.f(n2);
            return;
        }
        if (z.ab && (n2 < 93 || n2 > 99) || n2 >= 148 && n2 <= 157) {
            this.b.a();
            this.b.d.f(n2);
            return;
        }
        if (op.o == 1) {
            if (this.h == 0 && !this.s) {
                this.c[this.h].a(n2);
                return;
            }
        } else if (this.d == 8 && this.h == 0 && !this.s) {
            this.c[this.h].a(n2);
        }
    }

    public final void c(int n2) {
        if (this.r && (z.a() && n2 == 142 || !z.a() && n2 == 135)) {
            this.r = false;
        }
        mr mr2 = this.b;
        if (mr2.d.i()) {
            this.b.d.g(n2);
        }
    }

    public final void a(int n2, int n3) {
        if (this.b.d.h().b(n2, n3)) {
            mr mr2 = this.b;
            if (mr2.d.i()) {
                this.b.d.g(false);
                return;
            }
            this.b.a();
            return;
        }
        mr mr3 = this.b;
        if (mr3.d.i()) {
            if (!this.b.d.h().b(n2, n3)) {
                this.b.b();
            }
            return;
        }
        if (op.o == 1) {
            if (this.h == 0 && !this.s) {
                this.c[this.h].a(n2, n3);
                return;
            }
        } else if (this.d == 8 && this.h == 0 && !this.s) {
            this.c[this.h].a(n2, n3);
        }
    }

    public final void b(int n2, int n3) {
        if (this.d == 8 && this.h == 0 && !this.s) {
            this.c[this.h].b(n2, n3);
        }
    }

    protected final void a(int n2, int n3, int n4, int n5, int n6) {
        cw.a("[playingcontroller ]  actionOpponentSwapChess");
        if (this.k) {
            this.b.a(n2, this.i, this.j, n3, n4);
            this.x = 20;
            this.y = n3;
            this.z = n5;
            this.A = n4;
            this.B = n6;
            this.p = n2;
            return;
        }
        super.a(n2, n3, n4, n5, n6);
    }

    public final void a(boolean bl2) {
        cw.a(String.valueOf(gr.j.b) + "    receiveTurn = " + bl2);
        if (!bl2 && this.k) {
            this.b.a(1, this.i, this.j);
        }
        super.a(bl2);
        if (op.o != 1 && !bl2 && this.k) {
            kq.a().c(this.m);
        }
    }

    protected final void c(int n2, int n3) {
        block4: {
            if (n2 != 0) break block4;
            lk[] lkArray = this.b.g.w();
            a a2 = new a();
            int n4 = 0;
            while (n4 < lkArray.length) {
                block6: {
                    block5: {
                        if (lkArray[n4].a != n3) break block5;
                        --lkArray[n4].g;
                        if (lkArray[n4].g <= 0) break block6;
                    }
                    a2.a(lkArray[n4]);
                }
                ++n4;
            }
            lkArray = new lk[a2.d()];
            n4 = 0;
            while (n4 < lkArray.length) {
                lkArray[n4] = (lk)a2.b(n4);
                ++n4;
            }
            this.b.a(lkArray);
        }
        super.c(n2, n3);
    }

    protected final void e(int n2, int n3) {
        if (this.d != 8 || this.h != 1) {
            return;
        }
        super.e(n2, n3);
    }

    public final void b() {
        if (this.x > 0) {
            --this.x;
            if (this.x == 0) {
                super.a(this.p, this.y, this.A, this.z, this.B);
            }
            return;
        }
        super.b();
    }

    protected final void c() {
        switch (this.g) {
            case 2: {
                ny.m = false;
                break;
            }
            default: {
                ny.m = true;
            }
        }
        this.p();
    }

    protected final void d() {
        if (this.h == 0) {
            Object object = this.b;
            if (((mr)object).e.d() && op.o == 0) {
                this.d(15);
                return;
            }
            if (this.b.e.e()) {
                this.e = this.a(this.a);
                if (this.e != null) {
                    object = (mw)this.e.b(cy.a(this.e.d()));
                    this.b.a((mw)object);
                }
                this.e = null;
            }
        }
    }

    protected final void e() {
        if (!this.f) {
            kq.a().u();
            this.d(15);
            this.b.c("\u0110ang \u0111\u1ee3i \u0111\u1ed1i th\u1ee7!");
            return;
        }
        this.d(7);
    }

    public final boolean f() {
        return this.h == 0 && this.d == 8;
    }

    protected final boolean b(no no2) {
        if (this.o != -1) {
            if (this.m == no2.b && this.o != no2.c) {
                if (op.o != 1) {
                    this.o = -1;
                    this.l.c();
                    kq.a().v();
                    return true;
                }
                if (this.o == 2) {
                    this.b.b.b(0);
                } else if (this.o == 0) {
                    this.d(2);
                }
                this.o = -1;
                return false;
            }
            if (this.m != no2.b) {
                this.l.c();
                kq.a().v();
                this.o = -1;
            }
        } else if (this.n > this.m + 1) {
            this.l.c();
            kq.a().v();
            return true;
        }
        return false;
    }

    public final boolean g() {
        return this.a(this.i, this.j, this.i - 1, this.j);
    }

    public final boolean h() {
        return this.a(this.i, this.j, this.i + 1, this.j);
    }

    public final boolean i() {
        return this.a(this.i, this.j, this.i, this.j - 1);
    }

    public final boolean j() {
        return this.a(this.i, this.j, this.i, this.j + 1);
    }

    public final boolean a(int n2, int n3, int n4, int n5) {
        if (this.q) {
            this.q = false;
            this.b.b(false);
        }
        if (!mo.a(this.a.c(), n4, n5)) {
            return false;
        }
        if (this.a.b()) {
            if (mo.a(this.a.l, n4, n5)) {
                boolean bl2 = this.a(n2, n3, n4, n5, false);
                this.o = 0;
                kq.a().a(n2, n3, n4, n5, this.m);
                ++this.m;
                int n6 = n5;
                int n7 = n4;
                int n8 = n3;
                int n9 = n2;
                mr mr2 = this.b;
                nb nb2 = mr2.a.a(n9 - 2, n8 - 2);
                nb nb3 = mr2.a.a(n7 - 2, n6 - 2);
                n7 = nb2.n() - (nb2.n() - nb3.n() >> 1) - (mr2.f.p() >> 1);
                int n10 = nb2.o() - (nb2.o() - nb3.b() >> 1) - (mr2.f.q() >> 1);
                mr2.f.c(n7, n10);
                mr2.f.b(true);
                if (op.o == 1) {
                    this.a.b(false);
                    this.b.a(0, n4, n5, n2, n3);
                    this.i = n4;
                    this.j = n5;
                }
                return bl2;
            }
            return false;
        }
        if (n4 < 2) {
            n4 = 9;
        } else if (n4 >= 10) {
            n4 = 2;
        }
        if (n5 < 2) {
            n5 = 9;
        } else if (n5 >= 10) {
            n5 = 2;
        }
        this.g(n4, n5);
        this.b.a(0, this.i, this.j, n4, n5);
        this.i = n4;
        this.j = n5;
        return true;
    }

    public final void a(no no2) {
        while (this.o != -1) {
            if (this.m == no2.b) {
                if (this.u != null) {
                    this.l.a(no2);
                    this.n = no2.b;
                    this.o = -1;
                    no2 = this.u;
                    this.u = null;
                    continue;
                }
                if (this.o == no2.c) break;
                if (this.o == 2) {
                    this.b.b.b(0);
                    break;
                }
                if (this.o != 0) break;
                this.d(2);
                break;
            }
            if (this.m + 1 == no2.b) {
                this.u = no2;
                return;
            }
            this.l.c();
            kq.a().v();
            this.o = -1;
            break;
        }
        this.l.a(no2);
        if (no2.b >= 0) {
            this.n = no2.b;
        }
        this.o = -1;
    }
}

