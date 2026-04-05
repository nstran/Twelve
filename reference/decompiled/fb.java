/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class fb
extends au {
    public int i;
    public String j;
    private bj o;
    protected bd[] k;
    private be p;
    public bv l;
    protected bb m;
    public int n;
    private boolean q;

    public final void a(bb bb2) {
        this.m = bb2;
    }

    public fb(int n2, int n3, String string) {
        this.i = n2;
        this.n = n3;
        this.j = string;
        this.k = new bd[3];
    }

    final void q() {
        if (this.m != null) {
            this.m.i();
        }
        if (this.l != null) {
            this.l.n();
            this.r();
            return;
        }
        bd[] bdArray = this.k;
        int n2 = 0;
        while (n2 < bdArray.length) {
            if (bdArray[n2] != null) {
                bdArray[n2].c();
            }
            ++n2;
        }
        this.y().n();
    }

    protected void r() {
    }

    final void b(Graphics graphics, int n2, int n3) {
        this.y().a(graphics, n2, n3);
        if (this.l != null) {
            this.l.a(graphics, n2, n3);
        }
        if (this.p != null) {
            this.p.a(graphics);
        }
        Graphics graphics2 = graphics;
        fb fb2 = this;
        bd[] bdArray = fb2.k;
        if (fb2.l != null) {
            bdArray = fb2.l.a();
        }
        int n4 = 0;
        while (n4 < bdArray.length) {
            if (bdArray[n4] != null) {
                bdArray[n4].a(graphics2);
            }
            ++n4;
        }
        if (this.m != null) {
            this.m.a(graphics, n2, n3);
        }
        this.c(false);
    }

    private boolean a(bd[] bdArray, int n2) {
        if (n2 == 94 && bdArray[0] != null && bdArray[0].b()) {
            if (this.o != null) {
                this.o.d(-1, bdArray[0].a());
            }
            return true;
        }
        if (n2 == 95 && bdArray[1] != null && bdArray[1].b()) {
            if (this.o != null) {
                this.o.d(-1, bdArray[1].a());
            }
            return true;
        }
        if (n2 == 93 && bdArray[2] != null && bdArray[2].b()) {
            if (this.o != null) {
                this.o.d(-1, bdArray[2].a());
            }
            return true;
        }
        return false;
    }

    final boolean h(int n2) {
        if (this.l != null) {
            if (this.a(this.l.a(), n2)) {
                z.c();
                return true;
            }
            if (this.l.f(n2)) {
                return true;
            }
            this.t();
            return true;
        }
        if (this.a(this.k, n2)) {
            z.c();
            return true;
        }
        return this.y().f(n2);
    }

    final boolean i(int n2) {
        if (this.l != null && this.l.g(n2)) {
            return true;
        }
        return this.y().g(n2);
    }

    public final bd s() {
        return this.k[1];
    }

    private boolean a(bd[] bdArray, int n2, int n3) {
        int n4 = 0;
        while (n4 < bdArray.length) {
            if (bdArray[n4] != null && bdArray[n4].a(n2, n3)) {
                if (this.o != null) {
                    this.o.d(-1, bdArray[n4].a());
                }
                return true;
            }
            ++n4;
        }
        return false;
    }

    final boolean g(int n2, int n3) {
        if (this.m != null && this.m.a() != null) {
            this.m.a().g(n2, n3);
        }
        if (this.l != null) {
            if (this.a(this.l.a(), n2, n3)) {
                return true;
            }
            if (this.l.c(n2, n3)) {
                return true;
            }
            this.t();
            return true;
        }
        if (this.a(this.k, n2, n3)) {
            return true;
        }
        return this.y().c(n2, n3);
    }

    final boolean h(int n2, int n3) {
        if (this.m != null && this.m.a() != null) {
            this.m.a().h(n2, n3);
        }
        if (this.l != null && this.l.f(n2, n3)) {
            return true;
        }
        return this.y().f(n2, n3);
    }

    final boolean i(int n2, int n3) {
        if (this.l != null && this.l.e(n2, n3)) {
            return true;
        }
        return this.y().e(n2, n3);
    }

    public final void a(bj bj2) {
        this.o = bj2;
    }

    public final void a(bd bd2) {
        boolean bl2 = true;
        bd bd3 = bd2;
        fb fb2 = this;
        fb2.k[0] = bd3;
        if (bd3 != null) {
            fb2.k[0].a(6, z.s - be.a + 3, 20);
        }
    }

    public final void b(bd bd2) {
        boolean bl2 = true;
        bd bd3 = bd2;
        fb fb2 = this;
        fb2.k[2] = bd3;
        if (bd3 != null) {
            fb2.k[2].a(z.r - 6, z.s - be.a + 3, 24);
        }
    }

    public final void c(bd bd2) {
        boolean bl2 = true;
        bd bd3 = bd2;
        fb fb2 = this;
        fb2.k[1] = bd3;
        if (bd3 != null) {
            fb2.k[1].a(z.r / 2, z.s - be.a + 3, 17);
        }
    }

    public final void a(be be2) {
        this.p = be2;
    }

    public void a(bv bv2) {
        z.c();
        this.l = bv2;
        if (bv2 != null) {
            bv2.a(this);
        }
        this.c(true);
    }

    public void t() {
        z.c();
        this.l = null;
        this.c(true);
    }

    public abstract void u();

    public abstract void x();

    protected abstract au y();

    public final boolean z() {
        return this.q;
    }

    public final void e(boolean bl2) {
        this.q = true;
    }

    public String toString() {
        return "GameTab id = " + this.a + "    " + this.j;
    }
}

