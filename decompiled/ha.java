/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ha
extends hr {
    private String k;
    private String[] o;
    private ex p;
    private ex q;
    private au[] r;
    private byte s = 0;
    private byte[][] t;
    private lc u = null;
    private d v;

    public ha(String object, String string, int n2, String string2, int n3) {
        this.k = object;
        n2 = 7;
        object = this;
        if (((aq)object).f > ((aq)object).g) {
            if (((aq)object).f >= 320) {
                ((aq)object).f = 320;
            }
        } else if (((aq)object).f >= 240) {
            ((aq)object).f = 240;
        }
        ((aq)object).f -= 20;
        ((ha)object).o = ca.a(((ha)object).k, ((aq)object).f - 16, ca.b);
        ((aq)object).g = 100 + ca.c.a() * ((ha)object).o.length;
        ((ap)object).c = (z.r - ((aq)object).f) / 2;
        ((ap)object).d = (z.s - ((aq)object).g - be.a) / 2;
        ((ha)object).u = new lc();
        ((ha)object).p = new ex(string, n2);
        int n4 = ca.d.a("N\u00e2ng c\u1ea5p") + 20;
        n2 = (((aq)object).f - n4 - n4) / 4;
        ((ha)object).p.a(((ap)object).c + n2, ((ap)object).d + ((aq)object).g - 30, n4, 18);
        ((ha)object).p.d(true);
        ((ha)object).q = new ex(string2, n3);
        ((ha)object).q.a(((ap)object).c + ((aq)object).f - n2 - n4, ((ap)object).d + ((aq)object).g - 30, n4, 18);
        Object object2 = object;
        ((ha)object).t = new byte[2][4];
        ((ha)object2).t[0] = new byte[]{1, 1, -1, -1};
        byte[] byArray = new byte[4];
        byArray[2] = -1;
        byArray[3] = -1;
        ((ha)object2).t[1] = byArray;
        ((ha)object).r = new au[]{((ha)object).p, ((ha)object).q};
        ((ha)object).v = new cb(0xFF0000);
    }

    protected final void s() {
        this.u = null;
    }

    public final void f(int n2, int n3) {
        this.e(true);
        byte by2 = this.s;
        int n4 = 0;
        while (n4 < this.r.length) {
            if (this.r[n4].h().a(n2, n3)) {
                if (n4 != by2) {
                    this.s = (byte)n4;
                    this.r[by2].d(false);
                    this.r[this.s].d(true);
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

    protected final void f(int n2) {
        byte by2 = this.s;
        switch (n2) {
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                if (!this.r[this.s].f(n2)) {
                    int n3 = n2 - 96;
                    ha ha2 = this;
                    if (n3 >= 0 && (n3 = ha2.t[ha2.s][n3]) >= 0) {
                        ha2.s = (byte)n3;
                    }
                }
                if (by2 == this.s) break;
                this.r[this.s].d(true);
                this.r[by2].d(false);
                return;
            }
            case 95: {
                if (!this.r[this.s].m()) break;
                this.i.d(0, ((ex)this.r[this.s]).a());
            }
        }
    }

    public final void u() {
        if (this.u != null) {
            this.u.i();
        }
    }

    public final void b(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, false);
        if (this.u != null) {
            this.u.a(graphics, this.c + (this.f - this.u.p()) / 2, this.d + 10);
        }
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.o.length) {
            this.v.a(graphics, this.o[n3], this.c + this.f / 2, this.d + 15 + this.u.q() + n2, 1);
            n2 += ca.b.a();
            ++n3;
        }
        this.p.a(graphics, 0, 0);
        this.q.a(graphics, 0, 0);
    }

    public final void a(Graphics graphics) {
    }

    protected final boolean g(int n2) {
        return false;
    }

    protected final void e(int n2) {
    }
}

