/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class gz
extends ap
implements bj {
    private String k = null;
    private String[] l = null;
    private n m = null;
    private int n = 20;
    private d o = null;
    private int p = 10;
    private String[][] q = null;
    private int r = 0;
    private boolean s = true;

    public gz(nq nq2, String[] stringArray) {
        super(1);
        this.k = "\u0110\u00e3 ho\u00e0n th\u00e0nh nhi\u1ec7m v\u1ee5: " + nq2.b;
        this.a(this.k, stringArray);
    }

    private void a(String string, String[] stringArray) {
        int n2;
        this.b(-241229);
        this.a(new be());
        this.o = ca.d;
        this.c = this.p;
        this.f -= this.p << 1;
        int n3 = this.f - 20;
        this.l = ca.a(string, n3, this.o);
        int n4 = 0;
        if (stringArray != null) {
            this.q = new String[stringArray.length + 1][];
            this.q[0] = new String[]{"B\u1ea1n nh\u1eadn \u0111\u01b0\u1ee3c: "};
            n2 = 1;
            n4 = 0;
            while (n4 < stringArray.length) {
                this.q[n4 + 1] = ca.a(stringArray[n4], n3 - 4, this.o);
                n2 += this.q[n4 + 1].length;
                ++n4;
            }
            n4 = n2 * (this.o.a() + 5);
        }
        this.r = n2 = this.l.length * this.o.a();
        this.m = new n(10, 30, n3, n2 += n4 + 10);
        this.g = n2 + 65;
        this.d = z.s - this.g >> 1;
    }

    public final void c(Graphics graphics) {
        oz.a(graphics, this.c, this.d, this.f, this.g - this.n, z.ac, this.s);
        com.mg.sq.a.h.a(graphics, "Ch\u00fac m\u1eebng", this.c + (this.f >> 1), this.d + 10, 1);
        oz.a(graphics, this.m.a + this.c, this.m.b + this.d, this.m.c);
        ca.a(graphics, this.o, this.l, this.m.a + 5 + this.c, this.m.b + 5 + this.d, this.m.c - 10, this.m.d, 0);
        if (this.q != null) {
            int n2 = 5;
            int n3 = this.m.a + 5 + this.c;
            int n4 = this.m.b + this.r + this.d;
            int n5 = 0;
            while (n5 < this.q.length) {
                if (n5 > 0) {
                    oz.a(graphics, n3, n4 + n2 + this.o.a() / 2);
                }
                ca.a(graphics, this.o, this.q[n5], n3 + 10, n4 + n2, this.m.c - 10, this.m.d, 0);
                n2 += this.q[n5].length * this.o.a() + 5;
                ++n5;
            }
        }
    }

    public final void d(int n2, int n3) {
        if (n3 == 0) {
            ak.b().a(true);
        }
    }
}

