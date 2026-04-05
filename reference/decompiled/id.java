/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class id
extends ap
implements bj {
    private byte k = 0;
    private lf l = null;
    private String m = null;
    private n n;
    private n o;
    private n p;
    private me q;
    private String[] r = new String[]{"Danh Hi\u1ec7u", "Bang", "C\u1ea5p: "};
    private cx s;
    private n t;
    private String[] u;
    private String v;
    private nn w;
    private String x;
    private int y;
    private boolean z;
    private boolean A;

    public id(lf lf2, String string, boolean bl2, boolean bl3) {
        this(lf2, 0, bl2, bl3);
        this.x = string;
        this.z = bl2;
    }

    public final String t() {
        return this.x;
    }

    /*
     * WARNING - void declaration
     */
    public id(lf lf2, byte by2, boolean bl2, boolean n2) {
        super(1);
        int n3;
        int[] nArray = new int[]{7037769, 12628615, 15720104, 12628615, 7037769};
        this.y = 50;
        this.z = false;
        this.A = false;
        byte by3 = by2;
        id id2 = this;
        this.k = by3;
        if (by3 == 0) {
            bh bh2 = new bh("Kh\u00f4ng", 24);
            id2.b(bh2, true);
        } else {
            id2.a(new bh("\u0110\u00f3ng", 87));
        }
        this.b(-241209);
        this.a(new be());
        this.a(this);
        this.z = bl2;
        this.A = n3;
        lf lf3 = lf2;
        id2 = this;
        if (lf3 != null) {
            void var3_12;
            int n4;
            int n42 = 220;
            id2.l = lf3;
            id2.o = new n(10, 10, 60, 70);
            int n5 = id2.o.b + id2.o.d + 4;
            id2.p = new n(id2.o.a + id2.o.c + 65, id2.o.b + ca.d.a() + 4, 75, 16);
            n3 = ca.c.a(lf3.S) + 4;
            if (n3 > id2.p.c) {
                n4 = n3 - id2.p.c;
                id2.p.c = n3;
                n42 = n4 + 220;
            }
            if (id2.k == 0) {
                int n6;
                int n7 = n5 + (4 + ca.d.a() + 4);
                if (id2.z) {
                    n6 = n7 + (4 + ca.d.a() + 4);
                }
                if (id2.A) {
                    void var3_9 = n6 + (4 + ca.d.a() + 4);
                }
                if (lf3.Z > 0L) {
                    void var3_11;
                    id2.m = l.a(lf3.Z, ".");
                    id2.s = new cx(id2.o.a, (int)(var3_10 += 4));
                    n3 = ca.c.a("\u0110\u1eb7t C\u01b0\u1ee3c:");
                    n4 = ca.c.a(id2.m);
                    id2.t = new n(id2.o.a + 4 + 2 + n3, (int)(var3_11 += 6), n4, 16);
                    var3_12 = var3_11 + (id2.t.d + 4);
                }
            }
            void var3_13 = var3_12 + 4 + 4;
            id2.n = new n((id2.f - n42) / 2, (id2.g - var3_13 - be.a) / 2, n42, (int)var3_13);
            id2.u = new String[3];
            id2.u[0] = lf3.S;
            id2.u[1] = lf3.T;
            id2.u[2] = String.valueOf(lf3.G);
            id2.v = com.mg.sq.a.a(id2.l.b, id2.f / 2 - 5);
            id2.q = lz.a(lf3, false);
            id2.q.a(la.a(lf3));
            id2.q.a(np.a(lf3));
            id2.q.c(2);
        }
        this.w = new nn();
        this.w.a();
    }

    public final void c(Graphics graphics) {
        oz.d(graphics, this.n.a, this.n.b, this.n.c, this.n.d, z.ac);
        int n2 = this.o.a + this.n.a;
        int n3 = this.n.b + this.o.b;
        oz.c(graphics, n2, n3, this.o.c, this.o.d);
        if (this.q != null) {
            this.q.a(graphics, n2 + 4, n3 + this.o.d - 4 - this.q.q());
        }
        int n4 = 0;
        n4 = n2 + this.o.c + 4;
        oz.b(graphics, n4, n3 + ca.d.b() - oz.h.getHeight(), this.l.g);
        ca.d.c(true);
        ca.d.a(graphics, this.v, n4 += 15, n3, 0);
        ca.d.c(false);
        n4 = n3 + (ca.d.a() + 4);
        n3 = 0;
        while (n3 < this.r.length) {
            oz.b(graphics, this.p.a + this.n.a, n4, this.p.c, this.p.d, 1070484, 16579764, 14542575);
            ca.d.a(graphics, this.r[n3], this.p.a + this.n.a - 4, n4 + 1, 2);
            ca.c.a(graphics, this.u[n3], this.p.a + this.n.a + this.p.c / 2, n4 + 1, 1);
            n4 += this.p.d + 4;
            ++n3;
        }
        if (this.k == 0) {
            com.mg.sq.a.h.a(graphics, "Mu\u1ed1n khi\u00eau chi\u1ebfn v\u1edbi b\u1ea1n!", this.n.a + this.n.c / 2, n4 + 2, 1);
            n4 += 4 + com.mg.sq.a.h.a();
            if (this.z) {
                com.mg.sq.a.g.a(graphics, "Ki\u1ec3u quy\u1ebft \u0111\u1ea5u: 1 chi\u1ec1u", n2, n4 + 2, 0);
                n4 += 4 + com.mg.sq.a.g.a();
            }
            if (this.A) {
                com.mg.sq.a.g.a(graphics, "Kh\u00f4ng ch\u01a1i Tuy\u1ec7t Chi\u00eau", n2, n4 + 2, 0);
                com.mg.sq.a.g.a();
            }
            if (this.m != null) {
                oz.a(graphics, this.s.a + this.n.a, this.s.b + this.n.b, this.n.c - 20);
                n4 = this.t.b + this.n.b + 2;
                ca.d.c(true);
                ca.d.a(graphics, "\u0110\u1eb7t C\u01b0\u1ee3c:", n2, n4 + 2, 0);
                ca.d.c(false);
                n2 = this.t.a + this.n.a + 4;
                com.mg.sq.a.h.a(graphics, this.m, n2, n4 + 2, 0);
                ca.d.a(graphics, "KEN", n2 + this.t.c + 2, n4 + 2, 0);
                com.mg.sq.a.h.a();
            }
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 24: {
                com.mg.sq.a.a(false, null);
                return;
            }
            case 12: {
                com.mg.sq.a.a(true, null);
                return;
            }
        }
        ak.b().a(this.h(), false);
    }

    protected final void g() {
        if (this.q != null) {
            this.q.i();
        }
        if (this.k == 0) {
            if (this.y > 0) {
                --this.y;
                if (this.y == 1) {
                    bh bh2 = new bh("\u0110\u1ed3ng \u00fd", 12);
                    id id2 = this;
                    id2.a(bh2, true);
                    return;
                }
            }
            if (this.w.g() > 29L) {
                com.mg.sq.a.a(false, null);
                this.w.b();
            }
        }
    }

    public final long u() {
        return this.l.Z;
    }
}

