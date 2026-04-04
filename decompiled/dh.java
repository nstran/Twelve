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

public final class dh
extends au
implements bx {
    public n i;
    private Image m;
    public Image[] j;
    public Image[] k;
    private cx n;
    private boolean[] o;
    private boolean[] p;
    private int[][] q;
    private int[] r;
    private int[] s;
    public int l;
    private n t;
    private byte[] u;
    private Image v = f.d("/info/increase");
    private Image w = f.d("/info/decrease");
    private boolean x = false;
    private lu[] y = null;
    private bt z;
    private lf A;
    private int B = 0;
    private int[][] C;
    private int D;

    public dh(int n2) {
        int[][] nArrayArray = new int[9][];
        nArrayArray[0] = new int[]{-1, 1, 3, 6};
        int[] nArray = new int[4];
        nArray[1] = 2;
        nArray[2] = 3;
        nArray[3] = 6;
        nArrayArray[1] = nArray;
        nArrayArray[2] = new int[]{1, -1, 4, 7};
        int[] nArray2 = new int[4];
        nArray2[1] = 4;
        nArray2[2] = -1;
        nArray2[3] = 1;
        nArrayArray[3] = nArray2;
        nArrayArray[4] = new int[]{3, 5, -1, 2};
        nArrayArray[5] = new int[]{4, -1, -1, 8};
        int[] nArray3 = new int[4];
        nArray3[1] = 7;
        nArray3[2] = 1;
        nArray3[3] = -1;
        nArrayArray[6] = nArray3;
        nArrayArray[7] = new int[]{6, 8, 2, -1};
        nArrayArray[8] = new int[]{7, -1, 5, -1};
        this.C = nArrayArray;
        dh dh2 = this;
        this.j = new Image[gr.o.length];
        dh2.k = new Image[gr.o.length];
        dh2.m = f.d("/info/skilltree");
        int n3 = 0;
        while (n3 < gr.o.length) {
            int n4 = gr.o[n3].a * 1000;
            byte[] byArray = ox.a().b(n4);
            dh2.j[n3] = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
            h.a(byArray);
            dh2.k[n3] = Image.createImage((byte[])byArray, (int)0, (int)byArray.length);
            ++n3;
        }
        this.n = new cx(28, 9);
        this.i = new n(0, 0, 218, 218);
        this.q = new int[][]{{94, 11}, {94, 101}, {94, 152}, {29, 76}, {29, 127}, {29, 178}, {160, 76}, {160, 127}, {160, 178}};
    }

    public final void a(bt bt2) {
        this.z = bt2;
    }

    public final n a() {
        return new n(this.q[this.l][0] - this.t.a + this.i.a, this.q[this.l][1] - this.t.b + this.i.b, 30, 30);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        try {
            int n4 = this.d.a + n2;
            int n5 = this.d.b + n3;
            oz.a(graphics, this.i.a + n2, this.i.b + n3, this.i.c);
            oz.a(graphics, n4, n5 + this.d.d, this.d.c);
            cz.a(graphics, cz.a);
            cz.a(graphics, n4, n5 + 4, this.d.c, this.d.d - 8);
            if (this.t == null) {
                this.t = ((bc)this.l()).q();
            }
            graphics.drawImage(this.m, this.n.a + (n2 += this.i.a - this.t.a), this.n.b + (n3 += this.i.b - this.t.b), 20);
            graphics.drawRegion(this.m, 0, 0, this.m.getWidth(), this.m.getHeight(), 2, this.n.a + n2 + this.m.getWidth() - 1, this.n.b + n3, 20);
            d d2 = null;
            int n6 = 0;
            while (n6 < this.q.length) {
                Image image = this.k[n6];
                n4 = this.q[n6][0] + n2;
                n5 = this.q[n6][1] + n3;
                if (this.o[n6]) {
                    image = this.j[n6];
                    graphics.drawImage(image, n4, n5, 0);
                    d2 = this.s[n6] > 0 ? com.mg.sq.a.h : ca.c;
                    d2.a(graphics, String.valueOf(this.r[n6]), n4 + image.getWidth(), n5 + image.getHeight() - ca.c.a(), 2);
                } else {
                    graphics.drawImage(image, n4, n5, 0);
                }
                ++n6;
            }
            n6 = 0;
            while (n6 < this.o.length) {
                if (this.p[n6]) {
                    oz.b(graphics, this.q[n6][0] + n2 + 30 - 6, this.q[n6][1] + n3 + 1, 5, 5, 0xFFFFFF, 16069679);
                }
                ++n6;
            }
            if (this.l < 0) {
                return;
            }
            n4 = this.q[this.l][0] + n2;
            n5 = this.q[this.l][1] + n3;
            oz.a(graphics, new n(n4 - 4, n5 - 4, 38, 38), 0);
            if (this.x) {
                int n7 = this.j[0].getHeight();
                n4 += this.j[0].getWidth();
                Graphics graphics2 = graphics;
                dh dh2 = this;
                switch (dh2.u[dh2.l]) {
                    case 2: {
                        graphics2.drawImage(dh2.w, n4 + 5, n5 + n7, 36);
                        break;
                    }
                    case 4: {
                        graphics2.drawImage(dh2.v, n4 + 5, n5, 20);
                        break;
                    }
                    case 3: {
                        graphics2.drawImage(dh2.v, n4 + 5, n5, 20);
                        graphics2.drawImage(dh2.w, n4 + 5, n5 + n7, 36);
                    }
                    default: {
                        break;
                    }
                }
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        cz.c(graphics, cz.a);
    }

    public final void a(lf object) {
        int n2;
        Object object2;
        this.A = object;
        this.D = this.B = ((lf)object).L;
        this.s = new int[gr.o.length];
        this.u = new byte[gr.o.length];
        this.r = new int[gr.o.length];
        this.o = new boolean[gr.o.length];
        this.A = object;
        int n3 = 0;
        while (n3 < ((lf)object).E.length) {
            object2 = ((lf)object).E[n3];
            n2 = ((lb)object2).a % 100;
            this.o[n2] = true;
            this.r[n2] = ((lf)object).E[n3].f;
            ++n3;
        }
        Object[] objectArray = gr.o;
        object = this;
        this.y = objectArray;
        objectArray = ((dh)object).A.E;
        ((dh)object).o = new boolean[((dh)object).y.length];
        n2 = 0;
        while (n2 < objectArray.length) {
            object2 = objectArray[n2];
            int n4 = ((lb)object2).a % 100;
            ((dh)object).o[n4] = true;
            ++n2;
        }
        n2 = 0;
        while (n2 < ((dh)object).y.length) {
            if (!((dh)object).o[n2] && ((dh)object).i(n2)) {
                ((dh)object).o[n2] = true;
            }
            ++n2;
        }
        this.z();
    }

    public final cx h(int n2) {
        if (this.t == null) {
            this.t = ((bc)this.l()).q();
        }
        int n3 = this.i.a - this.t.a;
        int n4 = this.i.b - this.t.b;
        return new cx(this.q[n2][0] + n3, this.q[n2][1] + n4);
    }

    private boolean k(int n2) {
        boolean bl2;
        boolean bl3 = bl2 = this.y[n2].d < 0 || this.r[this.y[n2].d % 100] > 0;
        return this.B > 0 && this.r[n2] < this.y[n2].c.length && this.B >= this.y[n2].c[this.r[n2]].c && this.A.G >= this.y[n2].c[this.r[n2]].b && bl2;
    }

    private void l(int n2) {
        if (this.s[n2] > 0) {
            this.u[n2] = this.r[n2] < this.y[n2].c.length && this.k(n2) ? 3 : 2;
        } else {
            if (this.B >= this.y[n2].c[this.r[n2]].c && this.k(n2)) {
                this.u[n2] = 4;
            }
            n2 = 0;
            while (n2 < this.r.length) {
                if (this.r[n2] == 0) {
                    int n3 = n2;
                    while (n3 < this.r.length) {
                        if (this.y[n3].d == this.y[n2].a) {
                            while (this.r[n3] > 0) {
                                int n4 = n3;
                                this.s[n4] = this.s[n4] - 1;
                                int n5 = n3;
                                this.r[n5] = this.r[n5] - 1;
                                this.B += this.y[n3].c[this.r[n3]].c;
                            }
                        }
                        ++n3;
                    }
                }
                ++n2;
            }
        }
        this.z();
    }

    public final m v() {
        return new m(this.i.c, this.i.d);
    }

    public final int w() {
        return 10;
    }

    public final void e(boolean bl2) {
        this.x = bl2;
        this.l(this.l);
    }

    public final boolean q() {
        return this.x;
    }

    public final boolean c(int n2, int n3) {
        if (this.t == null) {
            this.t = ((bc)this.l()).q();
        }
        n2 -= this.i.a;
        n3 -= this.i.b;
        int n4 = this.q[this.l][1];
        if (this.x) {
            boolean bl2 = false;
            if (new n(this.q[this.l][0] + 30, this.q[this.l][1], 15, 15).a(n2, n3)) {
                this.f(99);
                return true;
            }
            if (new n(this.q[this.l][0] + 30, this.q[this.l][1] + 15, 15, 15).a(n2, n3)) {
                this.f(98);
                return true;
            }
        } else {
            if (new n(this.q[this.l][0], this.q[this.l][1], 30, 30).a(n2, n3)) {
                this.f(95);
                return true;
            }
            int n5 = 0;
            while (n5 < this.q.length) {
                if (new n(this.q[n5][0], this.q[n5][1], 30, 30).a(n2, n3)) {
                    this.l = n5;
                    if (this.z != null) {
                        this.z.a(this, this.l);
                    }
                    this.e(0, n4 - this.q[n5][1]);
                    return true;
                }
                ++n5;
            }
        }
        return false;
    }

    private void z() {
        if (this.p == null) {
            this.p = new boolean[gr.o.length];
        }
        int n2 = 0;
        while (n2 < this.p.length) {
            if (this.r[n2] < this.y[n2].c.length && this.i(n2)) {
                this.o[n2] = true;
            } else if (this.r[n2] == 0) {
                this.o[n2] = false;
            }
            this.p[n2] = this.i(n2) & this.o[n2];
            ++n2;
        }
    }

    public final boolean i(int n2) {
        boolean bl2;
        boolean bl3 = bl2 = this.y[n2].d < 0 || this.r[this.y[n2].d % 100] > 0;
        if (this.B > 0 && this.r[n2] < this.y[n2].c.length && this.B >= this.y[n2].c[this.r[n2]].c && this.A.G >= this.y[n2].c[this.r[n2]].b && bl2) {
            return true;
        }
        return this.s[n2] > 0 && bl2;
    }

    public final boolean f(int n2) {
        int n3 = this.l;
        switch (n2) {
            case 99: {
                if (this.x) {
                    if (this.u[this.l] <= 2) break;
                    this.B -= this.y[this.l].c[this.r[this.l]].c;
                    int n4 = this.l;
                    this.s[n4] = this.s[n4] + 1;
                    int n5 = this.l;
                    this.r[n5] = this.r[n5] + 1;
                    this.l(this.l);
                    break;
                }
                if (this.C[this.l][0] < 0) break;
                this.l = this.C[this.l][0];
                ((bc)this.l()).j(-40);
                break;
            }
            case 98: {
                if (this.x) {
                    if (this.s[this.l] <= 0) break;
                    int n6 = this.l;
                    this.s[n6] = this.s[n6] - 1;
                    int n7 = this.l;
                    this.r[n7] = this.r[n7] - 1;
                    this.B += this.y[this.l].c[this.r[this.l]].c;
                    this.l(this.l);
                    break;
                }
                if (this.C[this.l][1] < 0) break;
                this.l = this.C[this.l][1];
                ((bc)this.l()).j(40);
                break;
            }
            case 97: {
                if (this.x) {
                    return true;
                }
                if (this.C[this.l][2] < 0) break;
                this.l = this.C[this.l][2];
                break;
            }
            case 96: {
                if (this.x) {
                    return true;
                }
                if (this.C[this.l][3] < 0) break;
                this.l = this.C[this.l][3];
                break;
            }
            case 95: {
                if (this.z == null) break;
                this.z.b(this, this.l);
            }
        }
        if (this.z != null && n2 != 95) {
            this.z.a(this, n3, this.l);
        }
        return true;
    }

    public final boolean e(int n2, int n3) {
        bc bc2 = (bc)this.l();
        bc2.j(-n3);
        return false;
    }

    public final int r() {
        return this.r[this.l];
    }

    public final lu[] s() {
        return this.y;
    }

    public final lu t() {
        return this.y[this.l];
    }

    public final lu j(int n2) {
        return this.y[n2];
    }

    public final boolean u() {
        return this.D - this.B > 0;
    }

    public final int[] x() {
        return this.s;
    }

    public final int y() {
        return this.B;
    }
}

