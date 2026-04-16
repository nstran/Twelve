/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gv
extends al
implements bf,
ii {
    private String[] k;
    private aq[] l;
    private int m = 0;
    private int[][] n;
    private short[] o;
    private String p;
    private fd q;
    private int[] r;
    private int[] s = new int[2];
    private int t = 10;

    public gv(String string, String string2, int[] nArray, long[] lArray, short[] object) {
        super(1);
        int n2;
        int n3;
        this.p = string2;
        this.a(this);
        this.a((ba)null);
        this.f = v.t >= 240 ? v.t - 20 : 174;
        this.g = 125;
        this.r = nArray;
        this.o = object;
        this.k = bx.a(string, this.f - 10);
        this.l = new aq[2];
        int n4 = 5 + this.k.length * bx.c.a() + 6;
        int n5 = pe.a().a(object[0]);
        int n6 = pe.a().b(object[0]);
        int n7 = 1;
        while (n7 < ((short[])object).length) {
            n3 = pe.a().a(object[n7]);
            n2 = pe.a().b(object[n7]);
            if (n3 > n5) {
                n5 = n3;
            }
            if (n2 > n6) {
                n6 = n2;
            }
            ++n7;
        }
        n7 = (n6 + 4 << 2) + 5 + 5 - 10;
        n3 = this.f - 5 - 5;
        n2 = n3 / (n5 + 4);
        n3 = n2 * (n5 + 4) + 5 + 5;
        this.f = n3 + 10 + 10;
        int n8 = this.f - n3 >> 1;
        this.q = new fd(n2, ((short[])object).length);
        this.q.q().a(n8, n4, n3, n7);
        this.q.e(true);
        this.q.a(this);
        this.q.d(n5, n6);
        this.q.a(5, 5, 5, 5, 4, 4);
        this.l[0] = this.q.q();
        this.s[0] = 5;
        this.s[1] = n4 += this.l[0].f() + 6;
        Object object2 = new fx("B\u1ea3ng gi\u00e1:");
        ((fx)object2).d(false);
        ((fx)object2).a(5, n4 += 30, this.f - 10, 70);
        this.l[1] = object2;
        n6 = 0;
        while (n6 < nArray.length) {
            ey ey2 = new ey(String.valueOf(nArray[n6]) + "ng\u00e0y <" + lArray[n6] + "KEN>", false);
            object = ey2;
            ey2.h(n6);
            ((fx)object2).a((ey)object);
            ++n6;
        }
        ((fx)object2).h(0);
        this.g = (n4 += this.l[1].f() + 6) + 5;
        this.c = v.t - this.f >> 1;
        this.d = v.u - ba.a - this.g >> 1;
        int[][] nArrayArray = new int[2][];
        int[] nArray2 = new int[4];
        nArray2[0] = 1;
        nArray2[2] = 1;
        nArrayArray[0] = nArray2;
        int[] nArray3 = new int[4];
        nArray3[0] = 1;
        nArray3[1] = 1;
        nArray3[2] = 1;
        nArrayArray[1] = nArray3;
        this.n = nArrayArray;
        this.l[this.m].d(true);
        this.a(new ba());
        object2 = new bd("Mua", 1);
        gv gv2 = this;
        gv2.a((az)object2, true);
        object2 = new bd("\u0110\u00f3ng", 0);
        gv2 = this;
        gv2.b((az)object2, true);
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        if (bl2) {
            this.t = 10;
        }
    }

    public final void c(Graphics graphics) {
        if (!this.h) {
            return;
        }
        pc.a(graphics, this.c, this.d, this.f, this.g, v.aj, true);
        bx.a(graphics, bx.c, this.k, this.c + 10, this.d + 5, this.f - 10 - 10, this.k.length * bx.c.a(), 0);
        int n2 = 0;
        while (n2 < this.l.length) {
            if (this.l[n2] != null) {
                this.l[n2].a(graphics, this.c, this.d);
                this.l[n2].c(true);
            }
            ++n2;
        }
        n2 = this.s[0] + this.c;
        int n3 = this.s[1] + this.d;
        pe.a().a(graphics, this.o[this.q.a()], n2, n3, 0);
        bx.d.a(graphics, pe.a().c(this.o[this.q.a()]), n2 + pe.a().a(this.o[this.q.a()]) + 5, n3, 0);
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 0: {
                ag.b().a(false);
                return;
            }
            case 1: {
                ag.b().a(false);
                if (!(this.l[1] instanceof fx)) break;
                du.a().a(this.p, this.r[((fx)this.l[1]).a().q()], this.o[this.q.a()]);
            }
        }
    }

    protected final void g() {
        int n2 = 0;
        while (n2 < this.l.length) {
            if (this.l[n2] != null) {
                this.l[n2].n();
            }
            ++n2;
        }
        --this.t;
        if (this.t == 0) {
            this.e(false);
        }
    }

    public final void c(int n2) {
        this.e(true);
        int n3 = this.m;
        switch (n2) {
            case 95: {
                this.l[this.m].f(n2);
                break;
            }
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                if (this.l[this.m].f(n2)) break;
                this.m = this.n[this.m][n2 - 96];
            }
        }
        if (this.m != n3) {
            this.l[n3].d(false);
            this.l[this.m].d(true);
        }
        this.h = true;
    }

    public final void a(int n2, int n3) {
        this.e(true);
        n2 -= this.c;
        n3 -= this.d;
        int n4 = 0;
        while (n4 < this.l.length) {
            if (n2 > this.l[n4].c() && n2 < this.l[n4].c() + this.l[n4].e() && n3 > this.l[n4].d() && n3 < this.l[n4].d() + this.l[n4].f()) {
                if (this.m == n4) {
                    this.l[n4].c(n2, n3);
                    return;
                }
                this.l[this.m].d(false);
                this.m = n4;
                this.l[this.m].d(true);
                return;
            }
            ++n4;
        }
    }

    public final void b(int n2, int n3) {
        n2 -= this.c;
        n3 -= this.d;
        int n4 = 0;
        while (n4 < this.l.length) {
            this.l[n4].f(n2, n3);
            ++n4;
        }
    }

    public final void c(int n2, int n3) {
        this.e(true);
        int n4 = 0;
        while (n4 < this.l.length) {
            this.l[n4].e(n2, n3);
            ++n4;
        }
    }

    public final void t() {
        this.m = 1;
        this.l[0].d(false);
        this.l[this.m].d(true);
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        graphics.setColor(16177368);
        graphics.fillRect(n2 + 2, n3, n4, n5);
        pc.a(graphics, n2 + 2, n3, n4, n5 + 1, 0xFBB5B5, -1);
    }

    public final void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        int n6 = v.aj;
        int n7 = 10126946;
        if (this.l[0].m()) {
            n7 = 22523;
            n6 = 14479097;
        }
        pc.b(graphics, n2, n3, n4, n5, n7, 0xFFFFFF, n6);
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        pe.a().a(graphics, this.o[n6], n2 + (n4 - pe.a().a(this.o[n6])) / 2 + 2, n3 + (n5 - pe.a().b(this.o[n6])) / 2, 0);
    }
}

