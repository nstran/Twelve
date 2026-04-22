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
implements bf,
bg,
bn,
bq {
    private aw p;
    private ay q;
    private op r;

    public oj(int n2, int n3, String string) {
        super(105, 2, string, false);
        this.a(0, 0, v.t, v.u);
        this.p = new aw();
        this.p.a(this);
        this.p.a(this);
        this.q = new ay(2);
        this.q.a(this.c(), this.d() + 22, this.e(), this.f() - 22 - ba.a);
        this.q.b(this.p);
        this.p.e(true);
        this.a(new ba());
        this.a(new gb(-1, 0));
        this.b(new gb(-2, 1));
        this.c(com.mg.sq.a.n);
        this.a(this);
        this.r = new op(this, 106, 3, "\u0110\u00e0m \u0110\u1ea1o");
    }

    public final boolean a() {
        return this.p.a() > 0;
    }

    public final op v() {
        return this.r;
    }

    public final void x() {
        this.q.c(true);
    }

    public final void y() {
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (bl2) {
            this.q.c(true);
        }
    }

    public final int a(String string) {
        int n2 = 0;
        while (n2 < this.p.a()) {
            di di2 = (di)this.p.i(n2);
            if (di2.b().a().equals(string)) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    private di b(String string) {
        di di2 = null;
        int n2 = this.a(string);
        if (n2 >= 0) {
            di2 = (di)this.p.i(n2);
        }
        return di2;
    }

    public final void a(ds ds2) {
        int n2 = this.a(ds2.a());
        if (n2 >= 0) {
            di di2 = (di)this.p.i(n2);
            di2.a(ds2);
            this.p.b(di2, n2);
        }
    }

    public final void a(di object) {
        try {
            this.p.b(object);
            if (this.b != null) {
                object = (fc)this.b;
                if (this.p.a() <= 0) {
                    ((fc)object).d(this);
                }
                ((fc)object).d(this.r);
                this.r.a();
                return;
            }
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
    }

    public final di a(String object, String string, int n2) {
        if ((object = this.b((String)object)) != null) {
            boolean bl2 = ((di)object).a(string, 0);
            if (this.r.p != null && ((di)object).b().a().equals(this.r.p.b().a())) {
                this.r.a(((di)object).a(((di)object).d() - 1), bl2);
            }
        }
        return object;
    }

    public final di b(String object, String string, int n2) {
        if ((object = this.b((String)object)) != null) {
            boolean bl2 = ((di)object).b(string, n2);
            if (this.r.p != null && ((di)object).b().a().equals(this.r.p.b().a())) {
                this.r.a(((di)object).a(((di)object).d() - 1), bl2);
            }
        }
        return object;
    }

    public final di a(ds object, String string, int n2, boolean bl2) {
        object = new di((ds)object, this.p.e() - 20, bl2, com.mg.sq.a.m.K());
        this.p.a(object);
        ((di)object).b(string, n2);
        if (this.p.a() < 2) {
            this.a((di)object, false);
        }
        return object;
    }

    public final void a(ds ds2, boolean bl2, boolean bl3) {
        di di2 = this.b(ds2.a());
        if (di2 == null) {
            di2 = new di(ds2, this.p.e() - 20, bl2, com.mg.sq.a.m.K());
            this.p.a(di2);
            du.a().b(ds2.a());
        }
        if (bl3) {
            this.a(di2, true);
        }
    }

    private void a(di object, boolean bl2) {
        if (this.r.p == null || !((di)object).b().a().equals(this.r.p.b().a())) {
            this.r.a((di)object);
        }
        if (this.b == null) {
            return;
        }
        object = (fc)this.l();
        if (!((fc)object).b(this.r)) {
            ((fc)object).c(this.r);
        }
        if (bl2) {
            ((fc)object).a(this.r);
        }
    }

    public final boolean f(int n2) {
        if (n2 == 97 || n2 == 96) {
            return false;
        }
        return this.q.f(n2);
    }

    public final boolean e(int n2, int n3) {
        return this.q.e(n2, n3);
    }

    public final boolean c(int n2, int n3) {
        return this.q.c(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        return this.q.f(n2, n3);
    }

    public final void n() {
        this.q.n();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        boolean bl2 = this.q.k();
        n2 = bl2 ? 1 : 0;
        if (bl2) {
            graphics.setColor(v.am);
            graphics.fillRect(this.c(), this.d(), this.e(), this.f());
            graphics.drawImage(pc.d, this.c() + this.e(), this.d() + this.f() - ba.a, 40);
            pc.a(graphics, 4, 20, v.t - 8);
            bx.d.c(true);
            bx.d.a(graphics, "\u0110\u00e0m \u0110\u1ea1o", this.e() / 2, 2, 1);
            bx.d.c();
        }
        this.q.a(graphics, this.c(), this.d());
    }

    public final String[] w() {
        String[] stringArray = new String[this.p.a()];
        int n2 = 0;
        while (n2 < stringArray.length) {
            di di2 = (di)this.p.i(n2);
            stringArray[n2] = di2.b().a();
            ++n2;
        }
        return stringArray;
    }

    public final ds[] z() {
        ds[] dsArray = new ds[this.p.a()];
        int n2 = 0;
        while (n2 < dsArray.length) {
            di di2 = (di)this.p.i(n2);
            dsArray[n2] = di2.b();
            ++n2;
        }
        return dsArray;
    }

    public final aq a(aw object, int n2) {
        object = ((aw)object).i(n2);
        object = (di)object;
        return new eu(((di)object).b(), this.q.e());
    }

    public final void b(aq object, int n2) {
        object = this.p.i(n2);
        this.a((di)object, true);
    }

    public final void a(aq aq2, int n2) {
        if (this.p.s() == n2) {
            this.p.k(n2);
        }
    }

    public final void a(aq aq2, int n2, int n3) {
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -1: {
                oj oj2 = this;
                bs bs2 = new bs();
                bs2.a(new gb(0, 2));
                bs2.b(new gb(1, 3));
                br[] brArray = new br[]{new br("\u0110\u00f3ng", 10001), new br("\u0110\u00f3ng H\u1ebft", 10000)};
                if (com.mg.sq.a.m != null) {
                    brArray = oi.b(brArray, 2);
                }
                bs2.a(brArray);
                int n4 = bs2.e() > bs2.f() ? bs2.e() : bs2.f();
                bs2.a_(-n4, v.u);
                bs2.d(0, v.u - ba.a - bs2.f());
                bs2.a(oj2);
                oj2.a(bs2);
                return;
            }
            case 1: {
                this.t();
                return;
            }
            case 0: {
                this.l.f(95);
                return;
            }
        }
        ((fc)this.b).a();
    }

    public final void a(int n2, int n3, Object object) {
        switch (n3) {
            case 10001: {
                if (this.p.a() > 0) {
                    di di2 = (di)this.p.t();
                    fc fc2 = null;
                    if (this.b != null) {
                        fc2 = (fc)this.b;
                    }
                    if (this.r.p != null && this.r.p.equals(di2)) {
                        if (fc2 != null) {
                            fc2.d(this.r);
                        }
                        this.r.a();
                    }
                    this.p.b(di2);
                    if (this.p.a() <= 0 && fc2 != null) {
                        fc2.d(this);
                    }
                }
                this.t();
                break;
            }
            case 10000: {
                this.p.q();
                if (this.b != null) {
                    fc fc3 = (fc)this.b;
                    fc3.d(this);
                    fc3.d(this.r);
                    this.r.a();
                }
                this.t();
                break;
            }
            default: {
                if (com.mg.sq.a.m == null || this.l == null) break;
                com.mg.sq.a.m.j(n3);
            }
        }
        this.t();
    }

    public final void A() {
        if (this.b == null) {
            return;
        }
        fc fc2 = (fc)this.l();
        oj oj2 = this;
        boolean bl2 = false;
        int n2 = 0;
        while (n2 < oj2.p.a()) {
            di di2 = (di)oj2.p.i(n2);
            eu eu2 = (eu)oj2.p.o(n2);
            if (di2.a) {
                bl2 = true;
            }
            eu2.k = di2.a;
            ++n2;
        }
        if (bl2) {
            fc2.e(this);
            this.c(true);
            return;
        }
        fc2.f(this);
    }
}

