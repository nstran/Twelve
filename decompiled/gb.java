/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gb
extends au {
    private d i;
    private int[] j;
    private String[] k;
    private int l;
    private int m;
    private int n;
    private boolean o;
    private int p;
    private int q;

    private gb(String string, int n2, d d2, int n3) {
        this(string, 0, n2, d2, 1);
    }

    public gb(String string, int n2, d d2) {
        this(string, n2, d2, 1);
    }

    public gb(String string, int n2, int n3, d d2, int n4) {
        int n5;
        block9: {
            block8: {
                this.l = 0;
                this.n = 0;
                this.p = 0;
                this.q = 0;
                if (string == null || string.equals("")) {
                    cw.a("B\u1ea1n ch\u01b0a nh\u1eadp n\u1ed9i dung ");
                    return;
                }
                this.b(false);
                this.n = n2;
                if (n2 == n3) {
                    n2 = 0;
                    this.n = 0;
                }
                this.i = d2;
                if (n2 <= 0 || n2 >= n3 || string == null || !string.equals("")) break block8;
                Object object = ca.a(string, n3 - n2, d2);
                if (((String[])object).length <= 0) break block9;
                n5 = object[0].length();
                object = string.substring(n5);
                object = ca.a((String)object, n3, d2);
                this.k = new String[((String[])object).length + 1];
                this.k[0] = string.substring(0, n5);
                System.arraycopy(object, 0, this.k, 1, ((String[])object).length);
                n2 = 1;
                n5 = 0;
                while (n5 < this.k[0].length()) {
                    if (this.k[0].charAt(n5) == ' ') {
                        n2 = 0;
                        break;
                    }
                    ++n5;
                }
                if (n2 == 0) break block9;
                this.n = 0;
            }
            this.k = ca.a(string, n3, d2);
        }
        this.j = new int[this.k.length - 1];
        int n6 = 0;
        n5 = this.j.length;
        while (n6 < n5) {
            this.j[n6] = n4;
            ++n6;
        }
        this.a(0, 0, n3, this.k.length * d2.a());
    }

    public final void h(int n2) {
        this.l = 1;
        this.m = this.e() >> 1;
    }

    public final int f() {
        if (this.k == null) {
            return 0;
        }
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.j.length) {
            n2 += this.j[n3];
            ++n3;
        }
        return this.i.a() * this.k.length + n2 + this.q;
    }

    public final void i(int n2) {
        this.p = 10;
    }

    public final void j(int n2) {
        this.q = 6;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.k == null) {
            return;
        }
        if (this.f && this.m()) {
            oz.e(graphics, n2 + this.c(), n3 + this.d() + this.q / 2, this.e(), this.f());
        }
        int n4 = 0;
        int n5 = 0;
        int n6 = this.k.length;
        int n7 = this.n;
        this.i.c(this.o);
        while (n5 < n6) {
            this.i.a(graphics, this.k[n5], this.c() + n7 + this.m + this.p + n2, this.d() + n4 + this.q + n3, this.l);
            n4 += this.i.a() + (n5 < this.j.length ? this.j[n5] : 0);
            ++n5;
            n7 = 0;
        }
        this.i.c(false);
    }

    public final String toString() {
        return "sqstringcomponent   " + this.c() + "   " + this.d();
    }

    public final void e(boolean bl2) {
        this.o = true;
    }
}

