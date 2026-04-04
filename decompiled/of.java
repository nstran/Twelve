/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

final class of
extends au
implements bx {
    private n[] i;
    private short[][] j;
    private short[][] k;
    private Image l;
    private short m;
    private int n;
    private cx o;
    private Image p;
    private d q;
    private Image r;
    private oe s;

    public of(oe oe2) {
        this.s = oe2;
        this.j = new short[][]{{183, 399}, {405, 311}, {26, 432}, {408, 236}, {206, 261}, {304, 220}, {350, 184}, {183, 216}, {80, 181}, {256, 119}, {367, 116}, {229, 100}, {58, 119}, {16, 91}, {34, 317}, {402, 170}, {166, 59}};
        this.k = new short[][]{{149, 363}, {389, 345}, {15, 408}, {411, 266}, {243, 289}, {305, 247}, {341, 161}, {214, 192}, {77, 203}, {284, 150}, {374, 90}, {245, 90}, {84, 93}, {23, 65}, {30, 297}, {414, 155}, {190, 39}};
        this.m = (short)5;
        this.n = -1987;
        cw.b("WorldrMap");
        this.i = new n[17];
        this.i[0] = new n(146, 342, 61, 48);
        this.i[1] = new n(381, 319, 62, 48);
        this.i[2] = new n(9, 386, 64, 48);
        this.i[3] = new n(407, 245, 65, 47);
        this.i[4] = new n(236, 271, 64, 44);
        this.i[5] = new n(300, 227, 63, 44);
        this.i[6] = new n(332, 142, 62, 48);
        this.i[7] = new n(173, 169, 64, 48);
        this.i[8] = new n(68, 185, 62, 48);
        this.i[9] = new n(242, 128, 62, 48);
        this.i[10] = new n(369, 70, 64, 44);
        this.i[11] = new n(229, 74, 63, 45);
        this.i[12] = new n(84, 73, 63, 47);
        this.i[13] = new n(18, 38, 62, 48);
        this.i[14] = new n(34, 271, 52, 46);
        this.i[15] = new n(391, 131, 66, 44);
        this.i[16] = new n(157, 27, 71, 26);
        this.o = new cx(this.i[0].a + this.i[0].c / 2, this.i[0].b + this.i[0].d / 2);
        this.q = new ie(new int[]{0xFF0000, 0xFFFFFF});
        this.l = f.d("/m/m");
        this.p = oz.e;
        this.r = f.d("/m/lock");
    }

    public final boolean f(int n2) {
        switch (n2) {
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                if (this.n == n2) {
                    if (this.m < 50) {
                        this.m = (short)(this.m + 2);
                    }
                } else {
                    this.m = (short)25;
                    this.n = n2;
                }
                if (n2 == 99) {
                    if (this.o.b > 0) {
                        this.o.b -= this.m;
                    }
                } else if (n2 == 98) {
                    if (this.o.b < this.l.getHeight()) {
                        this.o.b += this.m;
                    }
                } else if (n2 == 97) {
                    if (this.o.a > 0) {
                        this.o.a -= this.m;
                    }
                } else if (n2 == 96 && this.o.a < this.l.getWidth()) {
                    this.o.a += this.m;
                }
                if (this.o.a < 0) {
                    this.o.a = 0;
                } else if (this.o.a > this.l.getWidth() - this.p.getWidth()) {
                    this.o.a = this.l.getWidth() - this.p.getWidth();
                }
                if (this.o.b < 0) {
                    this.o.b = 0;
                } else if (this.o.b > this.l.getHeight() - this.p.getHeight()) {
                    this.o.b = this.l.getHeight() - this.p.getHeight();
                }
                int n3 = this.q();
                if (n3 >= 0) {
                    if (oe.a == n3) {
                        if (n2 == 99) {
                            this.o.b = this.i[n3].b - 1;
                        } else if (n2 == 98) {
                            this.o.b = this.i[n3].b + this.i[n3].d + 1;
                        } else if (n2 == 97) {
                            this.o.a = this.i[n3].a - 1;
                        } else if (n2 == 96) {
                            this.o.a = this.i[n3].a + this.i[n3].c + 1;
                        }
                        n3 = -1;
                    } else {
                        this.o.a = this.i[n3].a + this.i[n3].c / 2;
                        this.o.b = this.i[n3].b + this.i[n3].d / 2;
                    }
                }
                if (n3 >= 0 && this.s.b != null) {
                    bh bh2 = new bh("V\u00e0o Th\u00e0nh", -1);
                    oe oe2 = this.s;
                    oe2.a(bh2, true);
                } else {
                    this.s.n();
                }
                oe.a = n3;
                this.a();
                return true;
            }
            case 95: {
                if (this.s.p()) {
                    this.s.d(-1, -1);
                }
                return true;
            }
        }
        return false;
    }

    final void a() {
        bc bc2 = (bc)this.b;
        n n2 = bc2.r();
        int n3 = this.o.a - n2.c / 2;
        int n4 = this.o.b - n2.d / 2;
        if (n3 < 0) {
            n3 = 0;
        } else if (n3 + n2.c > this.l.getWidth()) {
            n3 = this.l.getWidth() - n2.c;
        }
        if (n4 < 0) {
            n4 = 0;
        } else if (n4 + n2.d > this.l.getHeight()) {
            n4 = this.l.getHeight() - n2.d;
        }
        if (n3 != n2.a) {
            bc2.i(n3 - n2.a);
        }
        if (n4 != n2.b) {
            bc2.j(n4 - n2.b);
        }
    }

    private int q() {
        int n2 = this.o.a;
        int n3 = this.o.b;
        int n4 = 0;
        while (n4 < this.i.length) {
            int n5 = this.i[n4].c;
            int n6 = this.i[n4].d;
            if (n2 >= this.i[n4].a && n2 <= this.i[n4].a + n5 && n3 >= this.i[n4].b && n3 <= this.i[n4].b + n6) {
                return n4;
            }
            ++n4;
        }
        return -1;
    }

    public final boolean g(int n2) {
        this.n = -1987;
        this.m = (short)25;
        return false;
    }

    public final boolean c(int n2, int n3) {
        this.o.a = n2;
        this.o.b = n3;
        n2 = this.q();
        if (n2 >= 0 && this.s.b != null) {
            bh bh2 = new bh("V\u00e0o Th\u00e0nh", -1);
            oe oe2 = this.s;
            oe2.a(bh2, true);
            if (n2 == oe.a) {
                oe.a = n2;
                oe.a(this.s);
            }
        } else {
            this.s.n();
        }
        oe.a = n2;
        return true;
    }

    public final m v() {
        return new m(this.l.getWidth(), this.l.getHeight());
    }

    public final int w() {
        return this.m;
    }

    public final void n() {
        this.c = true;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (z.r > this.e() || z.s - be.a > this.f()) {
            graphics.setColor(0);
            graphics.fillRect(0, 0, z.r, z.s - be.a);
        }
        Object object = this;
        object = ((bc)((au)object).b).q();
        cz.a(graphics);
        cz.a(graphics, this.c(), this.d(), this.e(), this.f());
        graphics.drawImage(this.l, this.c() - ((n)object).a, this.d() - ((n)object).b, 0);
        if (this.s.b != null) {
            n3 = 0;
            while (n3 < this.s.b.length) {
                if (n3 < this.k.length && this.s.b[n3].h) {
                    graphics.drawImage(this.r, this.k[n3][0] - ((n)object).a, this.k[n3][1] - ((n)object).b, 20);
                }
                ++n3;
            }
        }
        n3 = 0;
        while (n3 < this.j.length) {
            d d2 = ca.c;
            if (n3 == oe.a) {
                d2 = this.q;
            }
            d2.a(graphics, oe.b(this.s)[n3], this.j[n3][0] - ((n)object).a, this.j[n3][1] - ((n)object).b, 0);
            ++n3;
        }
        n3 = this.o.a - ((n)object).a;
        int n4 = this.o.b - ((n)object).b;
        if (oe.a >= 0) {
            graphics.drawImage(this.p, n3, n4, 0);
        } else {
            graphics.drawImage(oz.f, n3, n4, 0);
        }
        cz.b(graphics);
    }

    static Image a(of of2) {
        return of2.l;
    }

    static void a(of of2, int n2, int n3) {
        int n4 = 0;
        while (n4 < of2.i.length) {
            of2.i[n4].a += n2;
            of2.i[n4].b += n3;
            ++n4;
        }
        n4 = 0;
        while (n4 < of2.j.length) {
            short[] sArray = of2.j[n4];
            sArray[0] = (short)(sArray[0] + n2);
            short[] sArray2 = of2.j[n4];
            sArray2[1] = (short)(sArray2[1] + n3);
            ++n4;
        }
        n4 = 0;
        while (n4 < of2.k.length) {
            short[] sArray = of2.k[n4];
            sArray[0] = (short)(sArray[0] + n2);
            short[] sArray3 = of2.k[n4];
            sArray3[1] = (short)(sArray3[1] + n3);
            ++n4;
        }
    }

    static void b(of of2) {
        v0.p = null;
        of2.r = null;
        of2.l = null;
        of2.i = null;
        of2.k = null;
        System.gc();
    }

    static n[] c(of of2) {
        return of2.i;
    }

    static void a(of of2, cx cx2) {
        of2.o = cx2;
    }
}

