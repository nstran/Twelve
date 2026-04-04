/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class hs
extends hr
implements aj {
    private dg o;
    private ex p;
    private ex q;
    private au[] r;
    private byte[][] s;
    private ff t;
    private byte u = 0;
    public String k;

    public hs(dg dg2, String string, int n2, String string2, int n3) {
        Object object = dg2;
        hs hs2 = this;
        this.o = object;
        this.b(241234);
        int n4 = 1111120;
        int n5 = 1111119;
        object = string;
        hs2 = this;
        if (hs2.f > hs2.g) {
            if (hs2.f >= 320) {
                hs2.f = 320;
            }
        } else if (hs2.f >= 240) {
            hs2.f = 240;
        }
        hs2.f -= 20;
        hs2.g = 120;
        hs2.c = (z.r - hs2.f) / 2;
        hs2.d = (z.s - hs2.g - be.a) / 2;
        hs2.t = new ff(null, 15, 4);
        hs hs3 = hs2;
        hs2.t.a(15 + ca.d.a("Gi\u00e1 b\u00e1n: ") + 4, hs2.d + hs3.o.f() + 30, 75, 18);
        hs2.t.a(hs2);
        hs2.t.d(true);
        hs2.p = new ex((String)object, n5);
        n5 = ca.d.a("N\u00e2ng c\u1ea5p") + 20;
        int n6 = (hs2.f - n5 - n5) / 4;
        hs2.p.a(hs2.c + n6, hs2.d + hs2.g - 30, n5, 18);
        hs2.p.d(true);
        hs2.q = new ex(string2, n4);
        hs2.q.a(hs2.c + hs2.f - n6 - n5, hs2.d + hs2.g - 30, n5, 18);
        hs hs4 = hs2;
        hs2.s = new byte[3][4];
        hs4.s[0] = new byte[]{-1, -1, 1, -1};
        byte[] byArray = new byte[4];
        byArray[0] = 2;
        byArray[1] = -1;
        byArray[2] = -1;
        hs4.s[1] = byArray;
        byte[] byArray2 = new byte[4];
        byArray2[0] = -1;
        byArray2[1] = 1;
        byArray2[2] = -1;
        hs4.s[2] = byArray2;
        hs2.r = new au[]{hs2.t, hs2.p, hs2.q};
        if (dg2.j == 0) {
            this.k = ((lj)dg2.k).c;
            return;
        }
        if (dg2.j == 1) {
            this.k = String.valueOf(((lk)dg2.k).g) + " " + ((lk)dg2.k).b;
        }
    }

    protected final void f(int n2) {
        byte by2 = this.u;
        switch (n2) {
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                if (!this.r[this.u].f(n2)) {
                    int n3 = n2 - 96;
                    hs hs2 = this;
                    if (n3 >= 0 && (n3 = hs2.s[hs2.u][n3]) >= 0) {
                        hs2.u = (byte)n3;
                    }
                }
                if (by2 == this.u) break;
                this.r[this.u].d(true);
                this.r[by2].d(false);
                return;
            }
            case 95: {
                if (this.u != 0) {
                    if (!this.r[this.u].m()) break;
                    this.i.d(0, ((ex)this.r[this.u]).a());
                    return;
                }
                this.r[this.u].f(n2);
                return;
            }
            default: {
                if (!this.t.m()) break;
                this.t.f(n2);
            }
        }
    }

    public final void f(int n2, int n3) {
        this.e(true);
        byte by2 = this.u;
        int n4 = 0;
        while (n4 < this.r.length) {
            if (this.r[n4].h().a(n2, n3)) {
                if (n4 != by2) {
                    this.u = (byte)n4;
                    this.r[by2].d(false);
                    this.r[this.u].d(true);
                }
                this.r[n4].c(n2, n3);
                if (this.r[n4].m()) {
                    this.i.d(0, ((ex)this.r[n4]).a());
                }
                return;
            }
            n4 = (byte)(n4 + 1);
        }
    }

    public final long t() {
        long l2 = 0L;
        String string = this.t.r();
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
        hs hs2 = this;
        hs2.o.n();
    }

    public final void b(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, false);
        hs hs2 = this;
        hs hs3 = hs2;
        hs3 = this;
        hs2.o.a(graphics, (this.f - hs3.o.e()) / 2, this.d + 10);
        if (this.k != null) {
            hs3 = this;
            ca.d.a(graphics, this.k, (this.f - ca.d.a(this.k)) / 2, this.d + hs3.o.f() + 13, 0);
        }
        hs3 = this;
        ca.c.a(graphics, "Gi\u00e1 b\u00e1n: ", 15, this.d + hs3.o.f() + 31, 0);
        this.t.a(graphics, 0, 0);
        hs3 = this;
        ca.c.a(graphics, ".000 KEN", 10 + ca.d.a("Gi\u00e1 b\u00e1n: ") + 4 + this.t.e() + 5, this.d + hs3.o.f() + 34, 0);
        this.p.a(graphics, 0, 0);
        this.q.a(graphics, 0, 0);
    }

    public final void a(Graphics graphics) {
    }

    public final dg v() {
        return this.o;
    }

    public final void a(ai ai2) {
        System.out.println("dramap" + ai2.a());
    }
}

