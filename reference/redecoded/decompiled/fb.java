/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class fb
extends aq {
    public final int i;
    public String j;
    private bf p;
    protected az[] k;
    private ba q;
    public bs l;
    protected ax m;
    public final int n;
    public final boolean o;

    public fb(int n2, int n3, String string, boolean bl2) {
        this.i = n2;
        this.n = n3;
        this.j = string;
        this.o = bl2;
        this.k = new az[3];
    }

    public final void a(ax ax2) {
        this.m = ax2;
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
        az[] azArray = this.k;
        int n2 = 0;
        while (n2 < azArray.length) {
            if (azArray[n2] != null) {
                azArray[n2].c();
            }
            ++n2;
        }
        this.n();
    }

    protected void r() {
    }

    final void b(Graphics graphics, int n2, int n3) {
        this.a(graphics, n2, n3);
        if (this.l != null) {
            this.l.a(graphics, n2, n3);
        }
        if (this.q != null) {
            this.q.a(graphics);
        }
        Graphics graphics2 = graphics;
        fb fb2 = this;
        az[] azArray = fb2.k;
        if (fb2.l != null) {
            azArray = fb2.l.a();
        }
        int n4 = 0;
        while (n4 < azArray.length) {
            if (azArray[n4] != null) {
                azArray[n4].a(graphics2);
            }
            ++n4;
        }
        if (this.m != null) {
            this.m.a(graphics, n2, n3);
        }
        this.c(false);
    }

    private boolean a(az[] azArray, int n2) {
        if (n2 == 94 && azArray[0] != null && azArray[0].b()) {
            if (this.p != null) {
                this.p.d(-1, azArray[0].a());
            }
            return true;
        }
        if (n2 == 95 && azArray[1] != null && azArray[1].b()) {
            if (this.p != null) {
                this.p.d(-1, azArray[1].a());
            }
            return true;
        }
        if (n2 == 93 && azArray[2] != null && azArray[2].b()) {
            if (this.p != null) {
                this.p.d(-1, azArray[2].a());
            }
            return true;
        }
        return false;
    }

    final boolean h(int n2) {
        if (this.m != null && this.m.a() != null) {
            this.m.a();
        }
        if (this.l != null) {
            if (this.a(this.l.a(), n2)) {
                v.c();
                return true;
            }
            if (this.l.f(n2)) {
                return true;
            }
            this.t();
            return true;
        }
        if (this.a(this.k, n2)) {
            v.c();
            return true;
        }
        return this.f(n2);
    }

    final boolean i(int n2) {
        if (this.m != null && this.m.a() != null) {
            this.m.a();
        }
        if (this.l != null && this.l.g(n2)) {
            return true;
        }
        return this.g(n2);
    }

    public final az s() {
        return this.k[1];
    }

    private boolean a(az[] azArray, int n2, int n3) {
        int n4 = 0;
        while (n4 < azArray.length) {
            if (azArray[n4] != null && azArray[n4].a(n2, n3)) {
                if (this.p != null) {
                    this.p.d(-1, azArray[n4].a());
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
        return this.c(n2, n3);
    }

    final boolean h(int n2, int n3) {
        if (this.m != null && this.m.a() != null) {
            this.m.a().h(n2, n3);
        }
        if (this.l != null && this.l.f(n2, n3)) {
            return true;
        }
        return this.f(n2, n3);
    }

    public final void a(bf bf2) {
        this.p = bf2;
    }

    public final void a(az az2) {
        boolean bl2 = true;
        az az3 = az2;
        fb fb2 = this;
        fb2.k[0] = az3;
        if (az3 != null) {
            fb2.k[0].a(6, v.u - ba.a + 3, 20);
        }
    }

    public final void b(az az2) {
        boolean bl2 = true;
        az az3 = az2;
        fb fb2 = this;
        fb2.k[2] = az3;
        if (az3 != null) {
            fb2.k[2].a(v.t - 6, v.u - ba.a + 3, 24);
        }
    }

    public final void c(az az2) {
        boolean bl2 = true;
        az az3 = az2;
        fb fb2 = this;
        fb2.k[1] = az3;
        if (az3 != null) {
            fb2.k[1].a(v.t / 2, v.u - ba.a + 3, 17);
        }
    }

    public final void a(ba ba2) {
        this.q = ba2;
    }

    public void a(bs bs2) {
        v.c();
        this.l = bs2;
        if (bs2 != null) {
            bs2.a(this);
        }
        this.c(true);
    }

    public void t() {
        v.c();
        this.l = null;
        this.c(true);
    }

    public void u() {
    }

    public void x() {
    }

    public void y() {
    }

    public String toString() {
        return "GameTab[" + this.a + ", " + this.j + "]";
    }
}

