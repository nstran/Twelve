/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class ol
extends fb
implements bf,
r {
    private oa u;
    public aq p;
    private int v = -1;
    private int w;
    public int q;
    private az[] x;
    public String r;
    public boolean s = false;
    public boolean t = false;
    private int y;

    public ol(int n2, int n3, String string, oa oa2) {
        super(100, 0, string, true);
        try {
            ct.b("main tab");
            this.u = oa2;
            this.a(this);
            ol ol2 = this;
            if (oa.b == null || oa.c == null) {
                pa.a().a(go.w, oa2);
            } else {
                this.k(1);
            }
            this.d(v.t);
            this.e(v.u);
            this.a(new ba());
            this.m = new ax(3);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (this.p != null) {
            this.p.c(bl2);
        }
    }

    public final void n() {
        if (this.v >= 0) {
            this.k(this.v);
            return;
        }
        if (this.p != null) {
            this.p.n();
        }
    }

    protected final void r() {
        if (this.p != null) {
            switch (this.p.b()) {
                case 1: {
                    ((om)this.p).a();
                    return;
                }
                case 3: {
                    ((os)this.p).a();
                    return;
                }
                case 2: {
                    oq oq2 = (oq)this.p;
                    oq2.n();
                }
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.p != null) {
            this.p.a(graphics, n2, n3);
        }
        this.c(true);
    }

    public final boolean f(int n2) {
        if (this.p != null) {
            this.p.f(n2);
        }
        return true;
    }

    public final boolean g(int n2) {
        if (this.p != null) {
            this.p.g(n2);
        }
        return true;
    }

    public final boolean c(int n2, int n3) {
        if (this.p != null) {
            this.p.c(n2, n3);
        }
        return true;
    }

    public final boolean f(int n2, int n3) {
        if (this.p != null) {
            this.p.f(n2, n3);
        }
        return true;
    }

    public final boolean e(int n2, int n3) {
        return super.e(n2, n3);
    }

    public final void d(int n2, int n3) {
        if (n3 == 0) {
            ag.b().a(false);
        }
    }

    public final void j(int n2) {
        this.v = n2;
    }

    private void k(int n2) {
        this.v = -1;
        this.a((bf)null);
        if (this.p != null) {
            if (this.p.b() != n2) {
                this.q = this.p.b();
            }
            if (n2 == 1) {
                this.r = null;
            }
            switch (this.p.b()) {
                case 1: {
                    ((om)this.p).r();
                    oa.c = null;
                    oa.b = null;
                    ((om)this.p).w();
                    this.r = null;
                    break;
                }
                case 3: {
                    this.r = ((os)this.p).j;
                }
            }
        }
        this.w = n2;
        this.p = null;
        System.gc();
        ct.b("[processChangeView]");
        s.a().a(this);
    }

    public final void y() {
        this.p = null;
    }

    public final void v() {
        this.x = new az[this.k.length];
        int n2 = 0;
        while (n2 < this.x.length) {
            this.x[n2] = this.k[n2];
            ++n2;
        }
    }

    public final void w() {
        this.k = this.x;
    }

    public final void z() {
        int n2 = 0;
        while (n2 < this.k.length) {
            this.k[n2] = null;
            ++n2;
        }
    }

    public final void a() {
        block14: {
            try {
                int n2 = this.p == null ? -1 : this.p.b();
                mb.a();
                switch (this.w) {
                    case 1: {
                        this.t = false;
                        this.p = new om(1, this, this.u);
                        break;
                    }
                    case 2: {
                        this.p = new oq(2, this, this.u);
                        break;
                    }
                    case 3: {
                        this.p = new os(3, this.u, this);
                        if (this.r == null) break;
                        this.a(this.r, null, 0L, this.y);
                    }
                }
                this.a((bf)((Object)this.p));
                if (this.w != 1 && this.w != 3) break block14;
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException interruptedException) {
                    InterruptedException interruptedException2 = interruptedException;
                    interruptedException.printStackTrace();
                }
                this.s = this.u.w();
                if (go.t) {
                    if (go.s == 0L) {
                        com.mg.sq.a.g("B\u1ea1n ch\u01b0a c\u00f3 KEN. B\u1ea1n c\u00f3 mu\u1ed1n n\u1ea1p kh\u00f4ng?");
                        go.t = false;
                    } else if (go.s <= 2000L) {
                        com.mg.sq.a.g("B\u1ea1n s\u1eafp h\u1ebft KEN. B\u1ea1n c\u00f3 mu\u1ed1n n\u1ea1p kh\u00f4ng?");
                        go.t = false;
                    }
                }
                this.u.f();
                if (n2 == 2) {
                    ks.a().d();
                    return;
                }
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
    }

    public final void a(String string, String string2, long l2, int n2) {
        this.t = true;
        this.y = n2;
        if (this.p instanceof os) {
            ((os)this.p).a(string, string2, l2, n2);
        }
    }

    public final void a(lh object, lh lh2, boolean bl2, byte[] object2, byte[] byArray, byte[] byArray2, byte by2, byte by3) {
        ha ha2 = null;
        if (this.p instanceof om) {
            ha2 = ((om)this.p).q;
            ((om)this.p).r();
        } else if (this.p instanceof oq) {
            ((oq)this.p).a((lh)object, lh2, (byte[])object2, byArray, byArray2, by2, by3);
        }
        if (ha2 == null) {
            ag.b().l();
            if (this.p instanceof om) {
                mg mg2 = ((om)this.p).l.e.u();
                object2 = mg2;
                mg2.a(lc.a((lh)object));
                ((mg)object2).a(nr.a((lh)object));
                ((om)this.p).e(false);
            } else {
                mg mg3 = mb.a((lh)object, false);
                object2 = mg3;
                mg3.a(lc.a((lh)object));
                ((mg)object2).a(nr.a((lh)object));
            }
            object = mb.a(lh2, false);
            ((mg)object).a(lc.a(lh2));
            ((mg)object).a(nr.a(lh2));
            ha2 = new ha((at)object2, (at)object, true, bl2, 99030, (bf)this.u);
            ha2.a(this);
            ha2.a(lh2);
            ag.b().a(ha2, false);
        }
        com.mg.sq.a.s().v();
        ha2.t();
        com.mg.sq.a.s().b().b();
    }

    public final void A() {
        oq.v();
        if (this.p != null) {
            switch (this.p.b()) {
                case 1: {
                    ((om)this.p).w();
                    break;
                }
                case 2: {
                    ((oq)this.p).u();
                }
            }
        }
        this.a((bf)null);
        this.p = null;
        this.u = null;
        ct.b("Finish MainTab.destroy()");
    }

    public final void a(int[] nArray, String[] stringArray) {
        boolean bl2 = true;
        ol ol2 = this;
        if (ol2.p != null && ol2.p.b() == 1) {
            ((om)this.p).a(nArray, stringArray);
        }
    }

    public final void x() {
    }

    public final void t() {
        super.t();
        if (this.p != null && this.p.b() == 2) {
            this.a((ba)null);
        }
    }
}

