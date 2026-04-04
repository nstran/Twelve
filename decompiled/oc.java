/*
 * Decompiled with CFR 0.152.
 */
import com.mg.sq.a;

public final class oc
extends nx
implements bj {
    private byte[] b;
    private int c;

    public oc(int n2, byte[] object, byte[] object2) {
        super((byte)0);
        this.c = n2;
        oc oc2 = this;
        this.b = object2;
        this.a(this);
        new cx(10, 22);
        int n3 = this.i();
        this.g -= be.a;
        this.j(true);
        gg gg2 = new gg("\u0110\u0103ng k\u00fd");
        object2 = gg2;
        gg2.a_(-1919);
        ((au)object2).d(n3);
        ((gg)object2).d(5, 5);
        ((au)object2).b(false);
        this.a((au)object2);
        object2 = new gc("T\u00ean \u0111\u0103ng nh\u1eadp");
        ((gc)object2).d(n3);
        this.a((au)object2);
        object2 = new gc("M\u1eadt Kh\u1ea9u");
        ((gc)object2).i(1);
        ((gc)object2).d(n3);
        this.a((au)object2);
        object2 = new gc("Nh\u1eadp l\u1ea1i m\u1eadt kh\u1ea9u");
        ((gc)object2).i(1);
        ((gc)object2).d(n3);
        this.a((au)object2);
        object2 = new gc("H\u1ecd t\u00ean: ");
        ((gc)object2).d(n3);
        this.a((au)object2);
        object2 = new fz("Ng\u00e0y sinh ");
        ((fz)object2).d(n3);
        this.a((au)object2);
        object2 = new gc("S\u1ed1 \u0111i\u1ec7n tho\u1ea1i: ");
        ((gc)object2).h(4);
        ((gc)object2).d(n3);
        this.a((au)object2);
        fw fw2 = new fw("Gi\u1edbi t\u00ednh");
        fw2.a(0, 0, n3, 50);
        object2 = new ey("Nam", false);
        ((ey)object2).h(0);
        fw2.a((ey)object2);
        object2 = new ey("N\u1eef", false);
        ((ey)object2).h(1);
        fw2.a((ey)object2);
        if (!gp.i) {
            fw2.h(0);
        }
        this.a(fw2);
        this.g(1);
        object2 = new gc((byte[])object);
        ((gc)object2).d(n3);
        this.a((au)object2);
        this.b(10);
        this.a(new be());
        object = new bh("\u0110\u0103ng k\u00fd", 1001);
        oc oc3 = this;
        oc3.a((bd)object, true);
        object = new bh("H\u1ee7y", 1000);
        oc3 = this;
        oc3.b((bd)object, true);
        this.d(false);
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 1001: {
                if (this.d().trim().equals("")) {
                    ap ap2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n ch\u01b0a nh\u1eadp t\u00ean nick. Vui l\u00f2ng nh\u1eadp t\u00ean nick!", "\u0110\u00f3ng", 1005, 1);
                    ap2.a(this);
                    ak.b().a(ap2);
                    return;
                }
                aq aq2 = this;
                if (this.e().equals(((gc)((nx)aq2).f(3)).a())) {
                    oc oc2 = this;
                    aq2 = oc2;
                    oc oc3 = this;
                    aq2 = oc3;
                    aq2 = this;
                    Long l2 = new Long(((fz)((nx)aq2).f(5)).a());
                    oc oc4 = this;
                    aq2 = oc4;
                    aq2 = this;
                    kq.a().a(this.d(), y.a(this.b, this.e()), ((gc)oc2.f(4)).a(), ((gc)oc3.f(6)).a(), l2, ((gc)oc4.f(8)).a(), (byte)((fw)((nx)aq2).f(7)).a().q());
                    com.mg.sq.a.a(null, null);
                    return;
                }
                aq2 = ak.b().a("Ch\u00fa \u00fd", "M\u1eadt kh\u1ea9u kh\u00f4ng tr\u00f9ng nhau, vui l\u00f2ng nh\u1eadp l\u1ea1i!", "\u0110\u00f3ng", 1005, 1);
                aq2.a(this);
                ak.b().a((ap)aq2);
                return;
            }
            case 1006: {
                gr.e = this.d();
                gr.f = this.e();
                ak.b().f(this.c);
                com.mg.sq.a.q().k();
                return;
            }
            case 1005: {
                ak.b().a(false);
                return;
            }
        }
        ak.b().f(this.c);
    }

    private String d() {
        return ((gc)this.f(1)).a();
    }

    private String e() {
        return ((gc)this.f(2)).a();
    }

    public final void a(String object) {
        if (this.c == 2) {
            object = ak.b().a("Ch\u00fa \u00fd", (String)(object == null ? "Ch\u00fac m\u1eebng b\u1ea1n \u0111\u00e3 \u0111\u0103ng k\u00fd th\u00e0nh c\u00f4ng!" : object), "\u0110\u00f3ng", 1006, 1);
            ((aq)object).a(this);
            ak.b().a((ap)object);
        }
    }
}

