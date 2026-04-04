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

public final class ic
extends ap
implements bj {
    private static int k;
    private static int l;
    private int[] m;
    private int[] n;
    private int o = 60;
    private static int p;
    private int q = 38;
    private String r = "Vui l\u00f2ng ch\u1edd...";
    private boolean s;
    private String t = "";
    private String[] u;
    private nn v;
    private Image w = oz.b;
    private Image x = null;
    private int y = -1;

    static {
        p = 10;
    }

    public ic(String string) {
        this(null, string);
    }

    private ic(Image image, String string) {
        super(0);
        this.v = new nn();
        this.a((be)null);
        if (string != null) {
            this.r = string;
        }
        this.t();
    }

    private void t() {
        this.q = 30;
        if (this.s) {
            this.q = 36;
        }
        if (this.f > this.g) {
            if (this.f >= 320) {
                this.f = 320;
            }
        } else if (this.f >= 240) {
            this.f = 240;
        }
        this.f -= 20;
        this.u = ca.a(this.r, this.f - 16, ca.b);
        this.g = this.q + p + p + p + ca.c.a() * this.u.length;
        this.c = (z.r - this.f) / 2;
        this.d = (z.s - this.g - be.a) / 2;
        int n2 = this.f / 2;
        int n3 = this.d + p + this.q / 2;
        int n4 = this.q / 2;
        int n5 = -90;
        this.m = new int[9];
        this.n = new int[9];
        int n6 = 0;
        while (n6 < 9) {
            this.m[n6] = n2 + (n4 * o.b(n5) >> 14) + this.c;
            this.n[n6] = n3 + (n4 * o.a(n5) >> 14);
            n5 += 40;
            ++n6;
        }
    }

    public final void c(Graphics graphics) {
        if (this.x != null) {
            graphics.fillRect(0, 0, z.r, z.s);
            graphics.drawImage(this.x, z.r >> 1, z.s >> 1, 3);
        } else {
            oz.a(graphics, this.c, this.d, this.f, this.g, z.ac, false);
        }
        if (this.s) {
            com.mg.sq.a.h.a(graphics, this.t, this.c + this.f / 2, this.d + p + this.q / 2 - 5, 1);
        }
        Graphics graphics2 = graphics;
        ic ic2 = this;
        int n2 = ic2.w.getWidth() / 3;
        int n3 = ic2.w.getHeight();
        int n4 = (l - 2 + ic2.m.length) % ic2.m.length;
        int n5 = 0;
        while (n5 < 2) {
            int n6 = (n4 + n5 + ic2.m.length) % ic2.m.length;
            cz.a(graphics2, ic2.w, 0, 0, n2, n3, ic2.m[n6], ic2.n[n6], 3);
            ++n5;
        }
        if (ic2.y >= 0) {
            ca.c.a(graphics2, String.valueOf(ic2.y) + "%", ic2.c + ic2.f / 2, ic2.d + (p + ic2.q) / 2, 3);
        }
        cz.a(graphics2, ic2.w, n2, 0, n2, n3, ic2.m[l], ic2.n[l], 3);
        cz.a(graphics2, ic2.w, n2 + n2, 0, n2, n3, ic2.m[k], ic2.n[k], 3);
        int n7 = 0;
        int n8 = 0;
        while (n8 < this.u.length) {
            ca.b.a(graphics, this.u[n8], this.c + this.f / 2 + 5, this.d + p + p + this.q + n7, 1);
            n7 += ca.b.a();
            ++n8;
        }
    }

    protected final void g() {
        if (this.s) {
            int n2 = (int)((long)this.o - this.v.g());
            if (n2 <= 0) {
                n2 = 0;
                com.mg.sq.a.q().m();
            }
            this.t = "" + n2;
        }
        l = k;
        k = (k + 1) % this.m.length;
    }

    public final void d(int n2, int n3) {
    }

    public final void a(int n2) {
        super.a(n2);
        this.o = n2;
    }

    public final void e(int n2) {
        this.o = 30;
        this.v.a();
    }

    public final void j(boolean bl2) {
        this.s = true;
        this.t();
    }
}

