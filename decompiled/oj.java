/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class oj
extends fb
implements bj,
v {
    private ny t;
    public au o;
    private int u = -1;
    private int v;
    public int p;
    private bd[] w;
    public String q;
    public boolean r = false;
    public boolean s = false;
    private int x;

    public oj(int n2, int n3, String string, ny ny2) {
        super(100, 0, string);
        try {
            cw.b("main tab");
            this.t = ny2;
            this.a(this);
            oj oj2 = this;
            if (ny.k == null || ny.l == null) {
                ox.a().a(ny.c, ny2);
            } else {
                this.k(1);
            }
            this.d(z.r);
            this.e(z.s);
            this.a(new be());
            this.m = new bb(3);
            this.e(true);
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
        if (this.o != null) {
            this.o.c(bl2);
        }
    }

    public final void n() {
        if (this.u >= 0) {
            this.k(this.u);
            return;
        }
        if (this.o != null) {
            this.o.n();
        }
    }

    protected final void r() {
        if (this.o != null) {
            switch (this.o.b()) {
                case 1: {
                    ((ok)this.o).a();
                    return;
                }
                case 3: {
                    ((or)this.o).a();
                    return;
                }
                case 2: {
                    op op2 = (op)this.o;
                    op2.n();
                }
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.o != null) {
            this.o.a(graphics, n2, n3);
        }
        this.c(true);
    }

    public final boolean f(int n2) {
        if (this.o != null) {
            this.o.f(n2);
        }
        return true;
    }

    public final boolean g(int n2) {
        if (this.o != null) {
            this.o.g(n2);
        }
        return true;
    }

    public final boolean c(int n2, int n3) {
        if (this.o != null) {
            this.o.c(n2, n3);
        }
        return true;
    }

    public final boolean f(int n2, int n3) {
        if (this.o != null) {
            this.o.f(n2, n3);
        }
        return true;
    }

    public final boolean e(int n2, int n3) {
        return super.e(n2, n3);
    }

    public final void d(int n2, int n3) {
        if (n3 == 0) {
            ak.b().a(false);
        }
    }

    public final void j(int n2) {
        this.u = n2;
    }

    private void k(int n2) {
        this.u = -1;
        this.a((bj)null);
        if (this.o != null) {
            if (this.o.b() != n2) {
                this.p = this.o.b();
            }
            if (n2 == 1) {
                this.q = null;
            }
            switch (this.o.b()) {
                case 1: {
                    ((ok)this.o).r();
                    ny.l = null;
                    ny.k = null;
                    ((ok)this.o).w();
                    this.q = null;
                    break;
                }
                case 3: {
                    this.q = ((or)this.o).j;
                }
            }
        }
        this.v = n2;
        this.o = null;
        System.gc();
        cw.b("[processChangeView]");
        w.a().a(this);
    }

    public final void x() {
        this.o = null;
    }

    public final void v() {
        this.w = new bd[this.k.length];
        int n2 = 0;
        while (n2 < this.w.length) {
            this.w[n2] = this.k[n2];
            ++n2;
        }
    }

    public final void w() {
        this.k = this.w;
    }

    public final void A() {
        int n2 = 0;
        while (n2 < this.k.length) {
            this.k[n2] = null;
            ++n2;
        }
    }

    protected final au y() {
        return this;
    }

    public final void a() {
        block15: {
            try {
                Object object;
                int n2 = this.o == null ? -1 : this.o.b();
                lz.a();
                switch (this.v) {
                    case 1: {
                        this.s = false;
                        this.o = new ok(1, this, this.t);
                        break;
                    }
                    case 2: {
                        this.o = new op(2, this, this.t);
                        break;
                    }
                    case 3: {
                        this.o = new or(3, this.t, this);
                        if (this.q == null) break;
                        this.a(this.q, null, 0L, this.x);
                    }
                }
                this.a((bj)((Object)this.o));
                if (this.v != 1 && this.v != 3) break block15;
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException interruptedException) {
                    object = interruptedException;
                    interruptedException.printStackTrace();
                }
                this.r = this.t.u();
                if (gr.q) {
                    if (gr.p == 0L) {
                        com.mg.sq.a.g("B\u1ea1n ch\u01b0a c\u00f3 KEN. B\u1ea1n c\u00f3 mu\u1ed1n n\u1ea1p kh\u00f4ng?");
                        gr.q = false;
                    } else if (gr.p <= 2000L) {
                        com.mg.sq.a.g("B\u1ea1n s\u1eafp h\u1ebft KEN. B\u1ea1n c\u00f3 mu\u1ed1n n\u1ea1p kh\u00f4ng?");
                        gr.q = false;
                    }
                }
                object = this.t;
                if (ny.m && (hq.q != null || hq.p != null)) {
                    hq hq2 = new hq(1, gr.j);
                    hq2.a((bj)object);
                    hq2.a(new bh("\u0110\u00f3ng", 113));
                    bd bd2 = null;
                    object = hq2;
                    ((aq)object).a(bd2, true);
                    bd2 = null;
                    object = hq2;
                    ((aq)object).b(bd2, true);
                    ak.b().a(hq2);
                }
                if (n2 == 2) {
                    kq.a().d();
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
        this.s = true;
        this.x = n2;
        if (this.o instanceof or) {
            ((or)this.o).a(string, string2, l2, n2);
        }
    }

    public final void a(lf object, lf lf2, boolean bl2, byte[] object2, byte[] byArray, byte[] byArray2, byte by2, byte by3) {
        gy gy2 = null;
        if (this.o instanceof ok) {
            gy2 = ((ok)this.o).q;
            ((ok)this.o).r();
        } else if (this.o instanceof op) {
            ((op)this.o).a((lf)object, lf2, (byte[])object2, byArray, byArray2, by2, by3);
        }
        if (gy2 == null) {
            ak.b().k();
            if (this.o instanceof ok) {
                me me2 = ((ok)this.o).l.d.u();
                object2 = me2;
                me2.a(la.a((lf)object));
                ((me)object2).a(np.a((lf)object));
                ((ok)this.o).e(false);
            } else {
                me me3 = lz.a((lf)object, false);
                object2 = me3;
                me3.a(la.a((lf)object));
                ((me)object2).a(np.a((lf)object));
            }
            object = lz.a(lf2, false);
            ((me)object).a(la.a(lf2));
            ((me)object).a(np.a(lf2));
            gy2 = new gy((ax)object2, (ax)object, true, bl2, 99030, (bj)this.t);
            gy2.a(this);
            gy2.a(lf2);
            ak.b().a(gy2, false);
        }
        com.mg.sq.a.t();
        gy2.t();
        com.mg.sq.a.q().b().b();
    }

    public final void B() {
        op.v();
        if (this.o != null) {
            switch (this.o.b()) {
                case 1: {
                    ((ok)this.o).w();
                    break;
                }
                case 2: {
                    ((op)this.o).u();
                }
            }
        }
        this.a((bj)null);
        this.o = null;
        this.t = null;
        cw.b("Finish MainTab.destroy()");
    }

    public final void u() {
    }

    public final void t() {
        super.t();
        if (this.o != null && this.o.b() == 2) {
            this.a((be)null);
        }
    }
}

