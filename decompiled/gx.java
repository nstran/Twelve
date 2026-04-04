/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class gx
extends ap
implements b,
bj {
    private lf k;
    private Image[] l;
    private Image[] m;
    private n[] n;
    private int o;
    private int p;
    private int[] q;
    private int[][] r;
    private int s = 0;
    private String[][] t;
    private String u;
    private lt[] v;
    private bj w;
    private int x = 6;
    private boolean y = false;

    public gx(lf object) {
        super(1);
        Object object2;
        int n2;
        this.b(241205);
        this.a(new be());
        this.a(this);
        this.k = object;
        this.v = (lt[])k.a(((lf)object).E, this);
        object = this;
        this.l = new Image[((gx)object).v.length];
        ((gx)object).m = new Image[((gx)object).v.length];
        int n3 = 0;
        while (n3 < ((gx)object).l.length) {
            n2 = ((gx)object).v[n3].a * 1000;
            byte[] byArray = ox.a().b(n2);
            ((gx)object).l[n3] = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
            h.a(byArray);
            ((gx)object).m[n3] = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
            ++n3;
        }
        ((gx)object).o = ((gx)object).l[0].getWidth();
        ((gx)object).p = ((gx)object).l[0].getHeight();
        int n4 = this.k.E.length;
        this.t = new String[n4][0];
        this.n = new n[n4];
        n3 = 3;
        if (com.mg.sq.a.i == 1) {
            if (z.r >= 320) {
                this.f = 300;
            }
            n3 = 5;
        } else if (z.r >= 240) {
            this.f = 220;
        }
        n2 = (this.f - n3 * this.o) / 3 / (n3 - 1);
        n3 = n3 > n4 ? n4 : n3;
        int n5 = n4 / n3 + (n4 % n3 != 0 ? 1 : 0);
        int n6 = this.o * n3 + n2 * (n3 - 1);
        n6 = (this.f - n6) / 2;
        int n7 = 0;
        int n8 = 0;
        while (n8 < n5) {
            int n9 = 0;
            while (n9 < n3) {
                n7 = n8 * n3 + n9;
                if (n7 >= n4) break;
                this.n[n7] = new n((n2 + this.o) * n9 + n6, (5 + this.p) * n8 + 10, this.o, this.p);
                int n10 = -1;
                int n11 = 0;
                while (n11 < gr.o.length) {
                    object2 = this.v[n7];
                    if (gr.o[n11].a == ((lb)object2).a) {
                        n10 = n11;
                        break;
                    }
                    ++n11;
                }
                if (n10 >= 0) {
                    this.t[n7] = ca.a(gr.o[n10].c[this.v[n7].f - 1].e, this.f - 10 - 10);
                }
                ++n9;
            }
            ++n8;
        }
        n2 = this.n.length;
        Object object3 = this;
        this.r = new int[n2][4];
        n6 = 0;
        n7 = 0;
        while (n7 < n5) {
            n8 = 0;
            while (n8 < n3) {
                n6 = n7 * n3 + n8;
                if (n6 >= n2) break;
                int[] nArray = new int[4];
                nArray[0] = n6 < n2 - 1 ? n6 + 1 : -1;
                int n12 = nArray[1] = n6 > 0 ? n6 - 1 : -1;
                nArray[2] = n7 < n5 - 1 ? (n6 + n3 >= n2 ? n2 - 1 : n6 + n3) : -1;
                nArray[3] = n7 > 0 ? n6 - n3 : -1;
                ((gx)object3).r[n6] = nArray;
                ++n8;
            }
            ++n7;
        }
        this.q = new int[]{10, this.n[this.n.length - 1].b + this.n[this.n.length - 1].d + 10};
        this.g = this.q[1] + 10 + 5 + ca.d.a() * this.x;
        this.c = (z.r - this.f) / 2;
        this.d = (z.s - this.g - be.a) / 2;
        this.e(this.s);
        object3 = new bh("\u0110\u00f3ng", 0);
        object2 = this;
        ((aq)object2).b((bd)object3, true);
        this.c(true);
        this.b(true);
    }

    public final void b(bj bj2) {
        this.w = bj2;
        this.s = 0;
        this.e(this.s);
        ak.b().a(this, false);
    }

    public final void c(Graphics graphics) {
        oz.d(graphics, this.c, this.d, this.f, this.g, z.ac);
        int n2 = 0;
        while (n2 < this.n.length) {
            graphics.fillRect(this.n[n2].a + this.c, this.n[n2].b + this.d, this.n[n2].c, this.n[n2].d);
            Image image = this.k.u >= this.v[n2].e ? this.l[n2] : this.m[n2];
            graphics.drawImage(image, this.n[n2].a + this.c, this.n[n2].b + this.d, 0);
            ++n2;
        }
        oz.a(graphics, this.q[0] + this.c, this.q[1] + this.d, this.f - 10 - 10);
        n2 = this.q[1] + this.d + 5;
        if (this.u != null) {
            ca.d.c(true);
            ca.d.a(graphics, this.u, this.q[0] + this.c, n2, 0);
            ca.d.c(false);
        }
        if (this.t[this.s] != null) {
            ca.a(graphics, ca.c, this.t[this.s], this.q[0] + this.c, n2 += ca.d.a() + 2, this.f, this.g, 0);
        }
        oz.e(graphics, this.n[this.s].a - 4 + this.c, this.n[this.s].b - 4 + this.d, this.n[this.s].c + 8, this.n[this.s].d + 8, 1);
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 0: {
                ak.b().a(this.h(), false);
                return;
            }
        }
        gx gx2 = this;
        if (gx2.k.u >= gx2.v[gx2.s].e) {
            if (gx2.w != null) {
                gx2.w.d(gx2.v[gx2.s].a, 999999);
            }
            ak.b().a(gx2.h(), false);
        }
    }

    public final void c(int n2) {
        switch (n2) {
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                int n3 = n2 - 96;
                gx gx2 = this;
                if (n3 < 0) break;
                gx2.s = gx2.r[gx2.s][n3] >= 0 ? gx2.r[gx2.s][n3] : gx2.s;
                gx2.e(gx2.s);
            }
        }
    }

    public final void a(int n2, int n3) {
        n2 -= this.c;
        n3 -= this.d;
        int n4 = 0;
        while (n4 < this.v.length) {
            if (this.n[n4].a(n2, n3)) {
                if (this.s != n4) {
                    this.s = n4;
                    this.e(this.s);
                    return;
                }
                this.d(-1, 1);
                return;
            }
            ++n4;
        }
    }

    private void e(int n2) {
        int n3 = 0;
        int n4 = 0;
        while (n4 < gr.o.length) {
            if (gr.o[n4].a == this.v[n2].a) {
                n3 = n4;
                break;
            }
            ++n4;
        }
        this.u = gr.o[n3].b;
        if (gr.o[n3].c[0].e != null) {
            if (this.t[n2].length > this.x) {
                this.g = (this.t[n2].length - this.x) * ca.d.a();
                this.d = (z.s - this.g - be.a) / 2;
                this.y = true;
            } else if (this.y) {
                this.y = false;
                this.g = this.q[1] + 10 + 5 + ca.d.a() * this.x;
                this.d = (z.s - this.g - be.a) / 2;
            }
        }
        if (this.k.u >= this.v[n2].e) {
            bh bh2 = new bh("D\u00f9ng", 1);
            gx gx2 = this;
            gx2.a(bh2, true);
            this.a(new bh("", 1));
            return;
        }
        bd bd2 = null;
        gx gx3 = this;
        gx3.a(bd2, true);
        this.a((bd)null);
    }

    public final int a(Object object, Object object2) {
        if (object instanceof lt) {
            object = (lt)object;
            object2 = (lt)object2;
            return ((lt)object).e - ((lt)object2).e;
        }
        object = (lu)object;
        object2 = (lu)object2;
        return ((lu)object).a - ((lu)object2).a;
    }
}

