/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class og
extends an
implements bf,
ik {
    private ay b;
    private oh c;
    jm[] a;
    private String[] d = new String[]{"Hoa L\u01b0", "K\u1ef3 B\u1ed1", "B\u00ecnh Ki\u1ec1u", "\u0110\u1eb1ng Ch\u00e2u", "\u0110\u1ed7 \u0110\u1ed9ng Giang", "T\u1ebf Giang", "Si\u00eau Lo\u1ea1i", "T\u00e2y Ph\u00f9 Li\u1ec7t", "\u0110\u01b0\u1eddng L\u00e2m", "C\u1ed5 Loa", "Ti\u00ean Du", "Tam \u0110\u00e1i", "Phong Ch\u00e2u", "H\u1ed3i H\u1ed3", "Luy\u1ec7n Ng\u1ee5c", "Thi\u00ean M\u00f4n", "M\u1eabu S\u01a1n"};

    public og() {
        super(4);
        bd bd2 = new bd("\u0110\u0103ng Xu\u1ea5t", -2);
        og og2 = this;
        og2.b(bd2, true);
        this.a(new ba());
        this.a(this);
        ag.b().l();
        try {
            this.e();
            return;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            if (com.mg.sq.a.m != null) {
                com.mg.sq.a.m.G();
            }
            try {
                this.e();
                return;
            }
            catch (OutOfMemoryError outOfMemoryError2) {
                com.mg.sq.a.s().j(1);
                return;
            }
        }
    }

    private void e() {
        this.c = new oh(this);
        this.b = new ay(2);
        this.b.h(1);
        int n2 = 0;
        int n3 = 0;
        int n4 = v.t;
        int n5 = v.u - ba.a;
        if (n4 > oh.a(this.c).getWidth()) {
            n2 = (n4 - oh.a(this.c).getWidth()) / 2;
            n4 = oh.a(this.c).getWidth();
        }
        if (n5 > oh.a(this.c).getHeight()) {
            n3 = (n5 - oh.a(this.c).getHeight()) / 2;
            n5 = oh.a(this.c).getHeight();
        }
        this.b.a(n2, n3, n4, n5);
        oh.a(this.c, n2, n3);
        this.b.b(this.c);
        this.c.a();
    }

    protected final void r() {
        go.x = 0;
        oh.a(this.c, new cu(oh.b((oh)this.c)[0].a + oh.b((oh)this.c)[0].c / 2, oh.b((oh)this.c)[0].b + oh.b((oh)this.c)[0].d / 2));
        this.c.a();
        pa.a().a("M99", this);
    }

    protected final void a(Graphics graphics) {
        this.b.a(graphics, 0, 0);
    }

    protected final void c() {
        this.b.n();
    }

    protected final void a(int n2) {
        this.b.f(n2);
    }

    protected final void e(int n2) {
        this.b.g(n2);
    }

    protected final void g(int n2, int n3) {
        this.b.e(n2, n3);
    }

    protected final void e(int n2, int n3) {
        this.b.c(n2, n3);
    }

    protected final void f(int n2, int n3) {
        this.b.f(n2, n3);
    }

    protected final void s() {
        this.a = null;
        this.b = null;
        this.i = null;
        oh.c(this.c);
        this.c = null;
    }

    public final void d(int n2, int n3) {
        if (n3 == -2) {
            com.mg.sq.a.u();
            return;
        }
        if (n3 == -1) {
            og.f();
        }
    }

    private static void f() {
        com.mg.sq.a.s().a((String)null, (il)null, 5000);
        ks.a().b("M99", go.x);
    }

    public final void d() {
    }

    public final void a(jn object, byte[][] object2) {
        com.mg.sq.a.s().v();
        if (object2 == null || ((byte[][])object2).length <= 0) {
            try {
                this.a = ((jn)object).l;
                if (go.x >= 0 && this.a != null && !this.a[go.x].h) {
                    object2 = new bd("V\u00e0o Th\u00e0nh", -1);
                    object = this;
                    ((am)object).a((az)object2, true);
                    return;
                }
                this.n();
                return;
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
        }
    }

    static void a(og og2) {
        og.f();
    }

    static String[] b(og og2) {
        return og2.d;
    }
}

