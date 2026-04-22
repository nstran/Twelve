/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public abstract class ht
extends al
implements bf {
    protected a l;
    protected bs m;
    protected boolean n = false;
    private k k = new k();
    az[] o = new az[3];

    public ht() {
        super(1);
        this.l = com.mg.sq.a.s();
    }

    public final void d(int n2, int n3) {
        if (this.g(n3)) {
            this.A();
            return;
        }
        this.e(n3);
    }

    protected final void g() {
        if (this.m != null) {
            this.m.n();
        }
        this.u();
    }

    public final void c(Graphics graphics) {
        this.a(graphics);
        cw.a(graphics, this.k);
        cw.a(graphics, this.c, this.d, this.f + 1, this.g + 1);
        this.b(graphics);
        if (this.m != null && this.n) {
            this.m.a(graphics, 0, 0);
        }
        cw.c(graphics, this.k);
        if (this.m != null && !this.n) {
            this.m.a(graphics, 0, 0);
        }
    }

    protected abstract boolean g(int var1);

    protected abstract void e(int var1);

    public abstract void u();

    public abstract void b(Graphics var1);

    public abstract void a(Graphics var1);

    public final void c(int n2) {
        if (this.m != null) {
            boolean bl2;
            int n3 = n2;
            az[] azArray = this.m.a();
            ht ht2 = this;
            if (n3 == 94 && azArray[0] != null && azArray[0].b()) {
                if (ht2.i != null) {
                    ht2.i.d(-1, azArray[0].a());
                }
                bl2 = true;
            } else if (n3 == 95 && azArray[1] != null && azArray[1].b()) {
                if (ht2.i != null) {
                    ht2.i.d(-1, azArray[1].a());
                }
                bl2 = true;
            } else if (n3 == 93 && azArray[2] != null && azArray[2].b()) {
                if (ht2.i != null) {
                    ht2.i.d(-1, azArray[2].a());
                }
                bl2 = true;
            } else {
                bl2 = false;
            }
            if (bl2) {
                v.c();
                return;
            }
            if (this.m.f(n2)) {
                return;
            }
            this.A();
            return;
        }
        this.f(n2);
    }

    protected void f(int n2) {
    }

    protected final void a(bs bs2, az az2, az az3, az az4) {
        this.m = bs2;
        this.o = new az[this.j.length];
        int n2 = 0;
        while (n2 < this.j.length) {
            this.o[n2] = this.j[n2];
            ++n2;
        }
        ht ht2 = this;
        ht2.a(az2, true);
        az2 = az4;
        ht2 = this;
        ht2.b(az2, true);
        this.a(az3);
        this.e(true);
    }

    protected final void A() {
        if (this.m == null) {
            return;
        }
        this.m = null;
        this.n = false;
        int n2 = 0;
        while (n2 < this.o.length) {
            this.j[n2] = this.o[n2];
            ++n2;
        }
        this.e(true);
    }

    public final void d(int n2) {
        if (this.m != null) {
            this.m.g(n2);
            return;
        }
    }

    public final void c(int n2, int n3) {
        if (this.m != null) {
            this.m.e(n2, n3);
            return;
        }
        this.e(n2, n3);
    }

    public void e(int n2, int n3) {
    }

    public final void a(int n2, int n3) {
        if (this.m != null) {
            boolean bl2;
            block6: {
                int n4 = n3;
                int n5 = n2;
                az[] azArray = this.m.a();
                ht ht2 = this;
                int n6 = 0;
                while (n6 < azArray.length) {
                    if (azArray[n6] != null && azArray[n6].a(n5, n4)) {
                        if (ht2.i != null) {
                            ht2.i.d(-1, azArray[n6].a());
                        }
                        bl2 = true;
                        break block6;
                    }
                    ++n6;
                }
                bl2 = false;
            }
            if (bl2) {
                return;
            }
            if (this.m.c(n2, n3)) {
                return;
            }
            this.A();
            return;
        }
        this.f(n2, n3);
    }

    public void f(int n2, int n3) {
    }

    public final void b(int n2, int n3) {
        if (this.m != null) {
            this.m.f(n2, n3);
            return;
        }
        this.g(n2, n3);
    }

    public void g(int n2, int n3) {
    }

    public final void a(br[] brArray, az az2, az az3, az az4) {
        bs bs2 = new bs();
        bs2.a(brArray);
        int n2 = bs2.e() > bs2.f() ? bs2.e() : bs2.f();
        bs2.a_(-n2, this.j() - bs2.f() + n2);
        bs2.d(0, v.u - ba.a - bs2.f());
        bs2.a(this);
        this.a(bs2, az2, az3, az4);
    }

    public void a(k k2) {
    }
}

