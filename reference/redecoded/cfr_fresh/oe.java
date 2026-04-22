/*
 * Decompiled with CFR 0.152.
 */
import com.mg.sq.a;

public final class oe
extends nz
implements bf {
    private byte[] b;
    private int c;

    public oe(int n2, byte[] object, byte[] object2) {
        super((byte)0);
        this.c = n2;
        oe oe2 = this;
        this.b = object2;
        this.a(this);
        new cu(10, 22);
        int n3 = this.i();
        this.g -= ba.a;
        this.j(true);
        gh gh2 = new gh("\u0110\u0103ng k\u00fd");
        object2 = gh2;
        gh2.a_(-1919);
        ((aq)object2).d(n3);
        ((gh)object2).d(5, 5);
        ((aq)object2).b(false);
        this.a((aq)object2);
        object2 = new gd("T\u00ean \u0111\u0103ng nh\u1eadp");
        ((gd)object2).d(n3);
        this.a((aq)object2);
        object2 = new gd("M\u1eadt Kh\u1ea9u");
        ((gd)object2).i(1);
        ((gd)object2).d(n3);
        this.a((aq)object2);
        object2 = new gd("Nh\u1eadp l\u1ea1i m\u1eadt kh\u1ea9u");
        ((gd)object2).i(1);
        ((gd)object2).d(n3);
        this.a((aq)object2);
        object2 = new gd("H\u1ecd t\u00ean: ");
        ((gd)object2).d(n3);
        this.a((aq)object2);
        object2 = new ga("Ng\u00e0y sinh ");
        ((ga)object2).d(n3);
        this.a((aq)object2);
        object2 = new gd("S\u1ed1 \u0111i\u1ec7n tho\u1ea1i: ");
        ((gd)object2).h(4);
        ((gd)object2).d(n3);
        this.a((aq)object2);
        fx fx2 = new fx("Gi\u1edbi t\u00ednh");
        fx2.a(0, 0, n3, 50);
        object2 = new ey("Nam", false);
        ((ey)object2).h(0);
        fx2.a((ey)object2);
        object2 = new ey("N\u1eef", false);
        ((ey)object2).h(1);
        fx2.a((ey)object2);
        if (!gr.i) {
            fx2.h(0);
        }
        this.a(fx2);
        this.g(1);
        object2 = new gd((byte[])object);
        ((gd)object2).d(n3);
        this.a((aq)object2);
        this.b(10);
        this.a(new ba());
        object = new bd("\u0110\u0103ng k\u00fd", 1001);
        oe oe3 = this;
        oe3.a((az)object, true);
        object = new bd("H\u1ee7y", 1000);
        oe3 = this;
        oe3.b((az)object, true);
        this.d(false);
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 1001: {
                if (this.d().trim().equals("")) {
                    al al2 = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n ch\u01b0a nh\u1eadp t\u00ean nick. Vui l\u00f2ng nh\u1eadp t\u00ean nick!", "\u0110\u00f3ng", 1005, 1);
                    al2.a(this);
                    ag.b().a(al2);
                    return;
                }
                am am2 = this;
                if (this.e().equals(((gd)((nz)am2).f(3)).a())) {
                    oe oe2 = this;
                    am2 = oe2;
                    oe oe3 = this;
                    am2 = oe3;
                    am2 = this;
                    Long l2 = new Long(((ga)((nz)am2).f(5)).a());
                    oe oe4 = this;
                    am2 = oe4;
                    am2 = this;
                    ks.a().a(this.d(), g.a(this.b, this.e()), ((gd)oe2.f(4)).a(), ((gd)oe3.f(6)).a(), l2, ((gd)oe4.f(8)).a(), (byte)((fx)((nz)am2).f(7)).a().q());
                    com.mg.sq.a.s().a((String)null, (il)null);
                    return;
                }
                am2 = ag.b().a("Ch\u00fa \u00fd", "M\u1eadt kh\u1ea9u kh\u00f4ng tr\u00f9ng nhau, vui l\u00f2ng nh\u1eadp l\u1ea1i!", "\u0110\u00f3ng", 1005, 1);
                am2.a(this);
                ag.b().a((al)am2);
                return;
            }
            case 1006: {
                go.e = this.d();
                go.f = this.e();
                ag.b().f(this.c);
                com.mg.sq.a.s().l();
                return;
            }
            case 1005: {
                ag.b().a(false);
                return;
            }
        }
        ag.b().f(this.c);
    }

    private String d() {
        return ((gd)this.f(1)).a();
    }

    private String e() {
        return ((gd)this.f(2)).a();
    }

    public final void a(String object) {
        if (this.c == 2) {
            object = ag.b().a("Ch\u00fa \u00fd", (String)(object == null ? "Ch\u00fac m\u1eebng b\u1ea1n \u0111\u00e3 \u0111\u0103ng k\u00fd th\u00e0nh c\u00f4ng!" : object), "\u0110\u00f3ng", 1006, 1);
            ((am)object).a(this);
            ag.b().a((al)object);
        }
    }
}

