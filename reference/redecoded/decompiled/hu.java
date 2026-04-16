/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class hu
extends ht
implements af {
    private dc p;
    private ex q;
    private ex r;
    private aq[] s;
    private byte[][] t;
    private ff u;
    private byte v = 0;
    public String k;

    public hu(dc dc2, String string, int n2, String string2, int n3) {
        Object object = dc2;
        hu hu2 = this;
        this.p = object;
        this.b(241234);
        int n4 = 1111120;
        int n5 = 1111119;
        object = string;
        hu2 = this;
        if (hu2.f > hu2.g) {
            if (hu2.f >= 320) {
                hu2.f = 320;
            }
        } else if (hu2.f >= 240) {
            hu2.f = 240;
        }
        hu2.f -= 20;
        hu2.g = 120;
        hu2.c = (v.t - hu2.f) / 2;
        hu2.d = (v.u - hu2.g - ba.a) / 2;
        hu2.u = new ff(null, 15, 4);
        hu hu3 = hu2;
        hu2.u.a(15 + bx.d.a("Gi\u00e1 b\u00e1n: ") + 4, hu2.d + hu3.p.f() + 30, 75, 18);
        hu2.u.a(hu2);
        hu2.u.d(true);
        hu2.q = new ex((String)object, n5);
        n5 = bx.d.a("N\u00e2ng c\u1ea5p") + 20;
        int n6 = (hu2.f - n5 - n5) / 4;
        hu2.q.a(hu2.c + n6, hu2.d + hu2.g - 30, n5, 18);
        hu2.q.d(true);
        hu2.r = new ex(string2, n4);
        hu2.r.a(hu2.c + hu2.f - n6 - n5, hu2.d + hu2.g - 30, n5, 18);
        hu hu4 = hu2;
        hu2.t = new byte[3][4];
        hu4.t[0] = new byte[]{-1, -1, 1, -1};
        byte[] byArray = new byte[4];
        byArray[0] = 2;
        byArray[1] = -1;
        byArray[2] = -1;
        hu4.t[1] = byArray;
        byte[] byArray2 = new byte[4];
        byArray2[0] = -1;
        byArray2[1] = 1;
        byArray2[2] = -1;
        hu4.t[2] = byArray2;
        hu2.s = new aq[]{hu2.u, hu2.q, hu2.r};
        if (dc2.j == 0) {
            this.k = ((ll)dc2.k).d;
            return;
        }
        if (dc2.j == 1) {
            this.k = String.valueOf(((lm)dc2.k).g) + " " + ((lm)dc2.k).b;
        }
    }

    protected final void f(int n2) {
        byte by2 = this.v;
        switch (n2) {
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                if (!this.s[this.v].f(n2)) {
                    int n3 = n2 - 96;
                    hu hu2 = this;
                    if (n3 >= 0 && (n3 = hu2.t[hu2.v][n3]) >= 0) {
                        hu2.v = (byte)n3;
                    }
                }
                if (by2 == this.v) break;
                this.s[this.v].d(true);
                this.s[by2].d(false);
                return;
            }
            case 95: {
                if (this.v != 0) {
                    if (!this.s[this.v].m()) break;
                    this.i.d(0, ((ex)this.s[this.v]).a());
                    return;
                }
                this.s[this.v].f(n2);
                return;
            }
            default: {
                if (!this.u.m()) break;
                this.u.f(n2);
            }
        }
    }

    public final void f(int n2, int n3) {
        this.e(true);
        byte by2 = this.v;
        int n4 = 0;
        while (n4 < this.s.length) {
            if (this.s[n4].h().a(n2, n3)) {
                if (n4 != by2) {
                    this.v = (byte)n4;
                    this.s[by2].d(false);
                    this.s[this.v].d(true);
                }
                this.s[n4].c(n2, n3);
                if (this.s[n4].m()) {
                    this.i.d(0, ((ex)this.s[n4]).a());
                }
                return;
            }
            n4 = (byte)(n4 + 1);
        }
    }

    public final long t() {
        long l2 = 0L;
        String string = this.u.r();
        if (!string.equals("")) {
            l2 = Integer.parseInt(string);
        }
        return l2;
    }

    protected final boolean g(int n2) {
        return false;
    }

    protected final void e(int n2) {
    }

    public final void u() {
        hu hu2 = this;
        hu2.p.n();
    }

    public final void b(Graphics graphics) {
        pc.a(graphics, this.c, this.d, this.f, this.g, v.aj, false);
        hu hu2 = this;
        hu hu3 = hu2;
        hu3 = this;
        hu2.p.a(graphics, (this.f - hu3.p.e()) / 2, this.d + 10);
        if (this.k != null) {
            hu3 = this;
            bx.d.a(graphics, this.k, (this.f - bx.d.a(this.k)) / 2, this.d + hu3.p.f() + 13, 0);
        }
        hu3 = this;
        bx.c.a(graphics, "Gi\u00e1 b\u00e1n: ", 15, this.d + hu3.p.f() + 31, 0);
        this.u.a(graphics, 0, 0);
        hu3 = this;
        bx.c.a(graphics, ".000 KEN", 10 + bx.d.a("Gi\u00e1 b\u00e1n: ") + 4 + this.u.e() + 5, this.d + hu3.p.f() + 34, 0);
        this.q.a(graphics, 0, 0);
        this.r.a(graphics, 0, 0);
    }

    public final void a(Graphics graphics) {
    }

    public final dc v() {
        return this.p;
    }

    public final void a(ae ae2) {
        System.out.println("dramap" + ae2.a());
    }
}

