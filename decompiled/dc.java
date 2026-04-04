/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class dc
extends ax {
    private lf a;
    private me b;
    private n c;
    private n d;
    private n e;
    private boolean f;
    private boolean g = true;
    private lf h;
    private String[][] i = new String[][]{{"C\u00f4ng", "C.X\u00e1c", "S.L\u1ef1c"}, {"P.Th\u1ee7", "N.Tr\u00e1nh", "C.M\u1ea1ng"}};

    public dc(lf lf2) {
        this.a(lf2, true);
        this.h = com.mg.sq.a.a(lf2.a());
        this.c = new n(5, 5, 50, 59);
        if (z.r >= z.s && z.r == 320) {
            this.f = true;
        }
        int n2 = this.f ? 60 : 45;
        this.d = new n(this.c.a + this.c.c + 40, 9, n2, 15);
        this.e = new n(this.d.a + this.d.c + n2 + 5, this.d.b, n2, 15);
        this.o = 240;
        this.p = 70;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        oz.c(graphics, this.c.a + n2, this.c.b + n3, this.c.c, this.c.d);
        if (this.g) {
            int n4 = 0;
            int n5 = 0;
            while (n5 < 3) {
                oz.b(graphics, this.d.a + n2, this.d.b + n4 + n3, this.d.c, this.d.d, 1070484, 16579764, 14542575);
                ca.c.a(graphics, this.i[0][n5], this.d.a - 3 + n2, this.d.b + n3 + n4, 2);
                n4 += this.d.d + 3;
                ++n5;
            }
            n5 = this.d.a + this.d.c / 2 + n2;
            n4 = (this.d.d - ca.d.a()) / 2 + 1;
            int n6 = this.a.x - this.h.x;
            d d2 = dc.a(n6);
            d2.a(graphics, String.valueOf(this.a.x), n5, this.d.b + n4 + n3, 1);
            int n7 = this.a.B - this.h.B;
            d d3 = dc.a(n7);
            d3.a(graphics, String.valueOf(this.a.B), n5, this.d.b + (n4 += this.d.d + 3) + n3, 1);
            int n8 = this.a.r - this.h.r;
            d d4 = dc.a(n8);
            d4.a(graphics, String.valueOf(this.a.r), n5, this.d.b + (n4 += this.d.d + 3) + n3, 1);
            n4 = 0;
            n5 = 0;
            while (n5 < 3) {
                oz.b(graphics, this.e.a + n2, this.e.b + n4 + n3, this.e.c, this.e.d, 1070484, 16579764, 14542575);
                ca.c.a(graphics, this.i[1][n5], this.e.a - 3 + n2, this.e.b + n3 + n4, 2);
                n4 += this.e.d + 3;
                ++n5;
            }
            n5 = this.e.a + this.e.c / 2 + n2;
            n4 = (this.e.d - ca.d.a()) / 2 + 1;
            int n9 = this.a.z - this.h.z;
            d d5 = dc.a(n9);
            d5.a(graphics, String.valueOf(this.a.z), n5, this.e.b + n4 + n3, 1);
            int n10 = this.a.A - this.h.A;
            d d6 = dc.a(n10);
            d6.a(graphics, String.valueOf(this.a.A), n5, this.e.b + (n4 += this.e.d + 3) + n3, 1);
            int n11 = this.a.C - this.h.C;
            d d7 = dc.a(n11);
            d7.a(graphics, String.valueOf(this.a.C) + "%", n5, this.e.b + (n4 += this.e.d + 3) + n3, 1);
        }
        if (this.b != null) {
            oz.b(graphics, this.c.a + n2, this.c.b + this.c.d + n3 - 17, this.a.g);
            this.b.a(graphics, n2 + this.c.a, n3 + this.c.b + 4);
        }
    }

    private static d a(int n2) {
        d d2 = n2 > 0 ? com.mg.sq.a.g : (n2 < 0 ? com.mg.sq.a.h : ca.d);
        return d2;
    }

    public final void i() {
        if (this.b != null) {
            this.b.i();
        }
    }

    private void a(lf lf2, boolean bl2) {
        this.a = com.mg.sq.a.a(lf2);
        if (bl2) {
            this.b = lz.a(lf2, false);
            this.b.a(la.a(lf2));
            this.b.a(np.a(lf2));
            this.b.c(2);
            this.g = true;
        }
    }

    public final void a(lj lj2) {
        if (lj2 == null) {
            return;
        }
        lj[] ljArray = this.a.D;
        boolean bl2 = false;
        if (ljArray != null && ljArray.length > 0) {
            int n2 = 0;
            while (n2 < ljArray.length) {
                if (ljArray[n2].d == lj2.d) {
                    if (ljArray[n2].a == lj2.a) {
                        return;
                    }
                    this.a.D[n2] = lj2;
                    bl2 = true;
                    break;
                }
                ++n2;
            }
        }
        if (!bl2) {
            if (this.a.D == null || this.a.D.length == 0) {
                this.a.D = new lj[]{lj2};
            } else {
                lj[] ljArray2 = new lj[this.a.D.length + 1];
                System.arraycopy(this.a.D, 0, ljArray2, 0, this.a.D.length);
                ljArray2[this.a.D.length] = lj2;
                this.a.D = ljArray2;
            }
        }
        this.a(this.a, lj2.d != 3);
    }

    public final void b(lj lj2) {
        if (lj2 == null) {
            return;
        }
        lj[] ljArray = this.a.D;
        if (this.a.D != null && ljArray.length > 0) {
            int n2 = 0;
            while (n2 < ljArray.length) {
                if (ljArray[n2].equals(lj2)) {
                    if (ljArray.length == 1) {
                        this.a.D = new lj[0];
                    } else {
                        this.a.D = new lj[ljArray.length - 1];
                        System.arraycopy(ljArray, 0, this.a.D, 0, n2);
                        System.arraycopy(ljArray, n2 + 1, this.a.D, n2, this.a.D.length - n2);
                    }
                }
                ++n2;
            }
        }
        if (lj2.d != 3) {
            this.b = lz.a(this.a, false);
            this.b.a(la.a(this.a));
            this.b.a(np.a(this.a));
            this.b.c(2);
        }
        this.a = com.mg.sq.a.a(this.a);
    }
}

