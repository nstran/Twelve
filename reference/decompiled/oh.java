/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class oh
extends fb
implements bj,
bk,
bq,
bt {
    private ba o;
    private bc p;
    private on q;

    public oh(int n2, int n3, String string) {
        super(105, 2, string);
        this.a(0, 0, z.r, z.s);
        this.o = new ba();
        this.o.a(this);
        this.o.a(this);
        this.p = new bc(2);
        this.p.a(this.c(), this.d() + 22, this.e(), this.f() - 22 - be.a);
        this.p.b(this.o);
        this.o.e(true);
        this.a(new be());
        this.a(new ga(-1, 0));
        this.b(new ga(-2, 1));
        this.c(com.mg.sq.a.l);
        this.a(this);
        this.q = new on(this, 106, 3, "\u0110\u00e0m \u0110\u1ea1o");
    }

    public final boolean a() {
        return this.o.a() > 0;
    }

    public final on v() {
        return this.q;
    }

    public final void u() {
        this.p.c(true);
    }

    public final void x() {
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (bl2) {
            this.p.c(true);
        }
    }

    public final int a(String string) {
        int n2 = 0;
        while (n2 < this.o.a()) {
            dl dl2 = (dl)this.o.i(n2);
            if (dl2.b().a().equals(string)) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    private dl b(String string) {
        dl dl2 = null;
        int n2 = this.a(string);
        if (n2 >= 0) {
            dl2 = (dl)this.o.i(n2);
        }
        return dl2;
    }

    public final void a(du du2) {
        int n2 = this.a(du2.a());
        if (n2 >= 0) {
            dl dl2 = (dl)this.o.i(n2);
            dl2.a(du2);
            this.o.b(dl2, n2);
        }
    }

    public final void a(dl object) {
        try {
            this.o.b(object);
            if (this.b != null) {
                object = (fc)this.b;
                if (this.o.a() <= 0) {
                    ((fc)object).d(this);
                }
                ((fc)object).d(this.q);
                this.q.a();
                return;
            }
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
    }

    public final dl a(String object, String string, int n2) {
        if ((object = this.b((String)object)) != null) {
            boolean bl2 = ((dl)object).a(string, 0);
            if (this.q.o != null && ((dl)object).b().a().equals(this.q.o.b().a())) {
                this.q.a(((dl)object).a(((dl)object).d() - 1), bl2);
            }
        }
        return object;
    }

    public final dl b(String object, String string, int n2) {
        if ((object = this.b((String)object)) != null) {
            boolean bl2 = ((dl)object).b(string, n2);
            if (this.q.o != null && ((dl)object).b().a().equals(this.q.o.b().a())) {
                this.q.a(((dl)object).a(((dl)object).d() - 1), bl2);
            }
        }
        return object;
    }

    public final dl a(du object, String string, int n2, boolean bl2) {
        object = new dl((du)object, this.o.e() - 20, bl2, com.mg.sq.a.k.K());
        this.o.a(object);
        ((dl)object).b(string, n2);
        if (this.o.a() < 2) {
            this.a((dl)object, false);
        }
        return object;
    }

    public final void a(du du2, boolean bl2, boolean bl3) {
        dl dl2 = this.b(du2.a());
        if (dl2 == null) {
            dl2 = new dl(du2, this.o.e() - 20, bl2, com.mg.sq.a.k.K());
            this.o.a(dl2);
            dv.a().b(du2.a());
        }
        if (bl3) {
            this.a(dl2, true);
        }
    }

    private void a(dl object, boolean bl2) {
        if (this.q.o == null || !((dl)object).b().a().equals(this.q.o.b().a())) {
            this.q.a((dl)object);
        }
        if (this.b == null) {
            return;
        }
        object = (fc)this.l();
        if (!((fc)object).b(this.q)) {
            ((fc)object).c(this.q);
        }
        if (bl2) {
            ((fc)object).a(this.q);
        }
    }

    public final boolean f(int n2) {
        if (n2 == 97 || n2 == 96) {
            return false;
        }
        return this.p.f(n2);
    }

    public final boolean e(int n2, int n3) {
        return this.p.e(n2, n3);
    }

    public final boolean c(int n2, int n3) {
        return this.p.c(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        return this.p.f(n2, n3);
    }

    public final void n() {
        this.p.n();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        boolean bl2 = this.p.k();
        n2 = bl2 ? 1 : 0;
        if (bl2) {
            graphics.setColor(z.af);
            graphics.fillRect(this.c(), this.d(), this.e(), this.f());
            graphics.drawImage(oz.d, this.c() + this.e(), this.d() + this.f() - be.a, 40);
            oz.a(graphics, 4, 20, z.r - 8);
            ca.d.c(true);
            ca.d.a(graphics, "\u0110\u00e0m \u0110\u1ea1o", this.e() / 2, 2, 1);
            ca.d.c();
        }
        this.p.a(graphics, this.c(), this.d());
    }

    public final String[] w() {
        String[] stringArray = new String[this.o.a()];
        int n2 = 0;
        while (n2 < stringArray.length) {
            dl dl2 = (dl)this.o.i(n2);
            stringArray[n2] = dl2.b().a();
            ++n2;
        }
        return stringArray;
    }

    public final du[] A() {
        du[] duArray = new du[this.o.a()];
        int n2 = 0;
        while (n2 < duArray.length) {
            dl dl2 = (dl)this.o.i(n2);
            duArray[n2] = dl2.b();
            ++n2;
        }
        return duArray;
    }

    public final au a(ba object, int n2) {
        object = ((ba)object).i(n2);
        object = (dl)object;
        return new eu(((dl)object).b(), this.p.e());
    }

    public final void b(au object, int n2) {
        object = this.o.i(n2);
        this.a((dl)object, true);
    }

    public final void a(au au2, int n2) {
        if (this.o.s() == n2) {
            this.o.k(n2);
        }
    }

    public final void a(au au2, int n2, int n3) {
    }

    protected final au y() {
        return this;
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -1: {
                oh oh2 = this;
                bv bv2 = new bv();
                bv2.a(new ga(0, 2));
                bv2.b(new ga(1, 3));
                bu[] buArray = new bu[]{new bu("\u0110\u00f3ng", 10001), new bu("\u0110\u00f3ng H\u1ebft", 10000)};
                if (com.mg.sq.a.k != null) {
                    buArray = og.b(buArray, 2);
                }
                bv2.a(buArray);
                int n4 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                bv2.a_(-n4, z.s);
                bv2.d(0, z.s - be.a - bv2.f());
                bv2.a(oh2);
                oh2.a(bv2);
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
                if (this.o.a() > 0) {
                    dl dl2 = (dl)this.o.t();
                    fc fc2 = null;
                    if (this.b != null) {
                        fc2 = (fc)this.b;
                    }
                    if (this.q.o != null && this.q.o.equals(dl2)) {
                        if (fc2 != null) {
                            fc2.d(this.q);
                        }
                        this.q.a();
                    }
                    this.o.b(dl2);
                    if (this.o.a() <= 0 && fc2 != null) {
                        fc2.d(this);
                    }
                }
                this.t();
                break;
            }
            case 10000: {
                this.o.q();
                if (this.b != null) {
                    fc fc3 = (fc)this.b;
                    fc3.d(this);
                    fc3.d(this.q);
                    this.q.a();
                }
                this.t();
                break;
            }
            default: {
                if (com.mg.sq.a.k == null || this.l == null) break;
                com.mg.sq.a.k.j(n3);
            }
        }
        this.t();
    }

    public final void B() {
        if (this.b == null) {
            return;
        }
        fc fc2 = (fc)this.l();
        oh oh2 = this;
        boolean bl2 = false;
        int n2 = 0;
        while (n2 < oh2.o.a()) {
            dl dl2 = (dl)oh2.o.i(n2);
            eu eu2 = (eu)oh2.o.o(n2);
            if (dl2.a) {
                bl2 = true;
            }
            eu2.k = dl2.a;
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

