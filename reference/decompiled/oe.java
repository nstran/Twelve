/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class oe
extends ar
implements bj,
ij {
    private bc c;
    private of d;
    public static int a = -1;
    jl[] b;
    private String[] k = new String[]{"Hoa L\u01b0", "K\u1ef3 B\u1ed1", "B\u00ecnh Ki\u1ec1u", "\u0110\u1eb1ng Ch\u00e2u", "\u0110\u1ed7 \u0110\u1ed9ng Giang", "T\u1ebf Giang", "Si\u00eau Lo\u1ea1i", "T\u00e2y Ph\u00f9 Li\u1ec7t", "\u0110\u01b0\u1eddng L\u00e2m", "C\u1ed5 Loa", "Ti\u00ean Du", "Tam \u0110\u00e1i", "Phong Ch\u00e2u", "H\u1ed3i H\u1ed3", "Luy\u1ec7n Ng\u1ee5c", "Thi\u00ean M\u00f4n", "M\u1eabu S\u01a1n"};

    public oe() {
        super(4);
        bh bh2 = new bh("\u0110\u0103ng Xu\u1ea5t", -2);
        oe oe2 = this;
        oe2.b(bh2, true);
        this.a(new be());
        this.a(this);
        ak.b().k();
        try {
            this.e();
            return;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            if (com.mg.sq.a.k != null) {
                com.mg.sq.a.k.G();
            }
            try {
                this.e();
                return;
            }
            catch (OutOfMemoryError outOfMemoryError2) {
                com.mg.sq.a.j(1);
                return;
            }
        }
    }

    private void e() {
        this.d = new of(this);
        this.c = new bc(2);
        this.c.h(1);
        int n2 = 0;
        int n3 = 0;
        int n4 = z.r;
        int n5 = z.s - be.a;
        if (n4 > of.a(this.d).getWidth()) {
            n2 = (n4 - of.a(this.d).getWidth()) / 2;
            n4 = of.a(this.d).getWidth();
        }
        if (n5 > of.a(this.d).getHeight()) {
            n3 = (n5 - of.a(this.d).getHeight()) / 2;
            n5 = of.a(this.d).getHeight();
        }
        this.c.a(n2, n3, n4, n5);
        of.a(this.d, n2, n3);
        this.c.b(this.d);
        this.d.a();
    }

    protected final void r() {
        kq.a().c(gr.e);
    }

    protected final void a(Graphics graphics) {
        this.c.a(graphics, 0, 0);
    }

    protected final void c() {
        this.c.n();
    }

    protected final void a(int n2) {
        this.c.f(n2);
    }

    protected final void e(int n2) {
        this.c.g(n2);
    }

    protected final void g(int n2, int n3) {
        this.c.e(n2, n3);
    }

    protected final void e(int n2, int n3) {
        this.c.c(n2, n3);
    }

    protected final void f(int n2, int n3) {
        this.c.f(n2, n3);
    }

    protected final void s() {
        this.b = null;
        this.c = null;
        this.i = null;
        of.b(this.d);
        this.d = null;
    }

    public final void d(int n2, int n3) {
        if (n3 == -2) {
            com.mg.sq.a.s();
            return;
        }
        if (n3 == -1) {
            oe.f();
        }
    }

    private static void f() {
        com.mg.sq.a.b(null, null, 5000);
        kq.a().b("M99", a);
    }

    public final void a(String string, String string2, int n2) {
        cw.a("[Worldmapscreen]  receiveCurrentLocation   " + string + "      " + string2 + "       " + n2);
        if (string2.equals("M99")) {
            a = n2;
            of.a(this.d, new cx(of.c((of)this.d)[oe.a].a + of.c((of)this.d)[oe.a].c / 2, of.c((of)this.d)[oe.a].b + of.c((of)this.d)[oe.a].d / 2));
            this.d.a();
            ox.a().a("M99", this);
            return;
        }
        ny.d = n2;
        ny.c = string2;
        ak.b().a(5, new Object[]{string2});
    }

    public static void a(String string, int n2, int n3) {
        if (n3 != 0) {
            return;
        }
        if (string.equals("M99")) {
            return;
        }
        ny.d = n2;
        ny.c = string;
        ak.b().a(5, new Object[]{string});
    }

    public static void a(String object) {
        com.mg.sq.a.t();
        object = ak.b().a("", (String)object, "\u0110\u00f3ng", 2, 1);
        ((aq)object).a(ak.b());
        ak.b().a((ap)object);
    }

    public final void d() {
    }

    public final void a(jm object, byte[][] object2) {
        com.mg.sq.a.t();
        if (object2 == null || ((byte[][])object2).length <= 0) {
            try {
                this.b = ((jm)object).n;
                if (a >= 0 && this.b != null && !this.b[oe.a].h) {
                    object2 = new bh("V\u00e0o Th\u00e0nh", -1);
                    object = this;
                    ((aq)object).a((bd)object2, true);
                    return;
                }
                this.n();
                return;
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
                return;
            }
        }
    }

    static void a(oe oe2) {
        oe.f();
    }

    static String[] b(oe oe2) {
        return oe2.k;
    }
}

