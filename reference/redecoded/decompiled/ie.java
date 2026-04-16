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

public final class ie
extends al
implements bf {
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
    private np v;
    private Image w = pc.b;
    private Image x = null;
    private int y = -1;

    static {
        p = 10;
    }

    public ie(String string) {
        this(null, string);
    }

    private ie(Image image, String string) {
        super(0);
        this.v = new np();
        this.a((ba)null);
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
        this.u = bx.a(this.r, this.f - 16, bx.b);
        this.g = this.q + p + p + p + bx.c.a() * this.u.length;
        this.c = (v.t - this.f) / 2;
        this.d = (v.u - this.g - ba.a) / 2;
        int n2 = this.f / 2;
        int n3 = this.d + p + this.q / 2;
        int n4 = this.q / 2;
        int n5 = -90;
        this.m = new int[9];
        this.n = new int[9];
        int n6 = 0;
        while (n6 < 9) {
            this.m[n6] = n2 + (n4 * l.b(n5) >> 14) + this.c;
            this.n[n6] = n3 + (n4 * l.a(n5) >> 14);
            n5 += 40;
            ++n6;
        }
    }

    public final void c(Graphics graphics) {
        if (this.x != null) {
            graphics.fillRect(0, 0, v.t, v.u);
            graphics.drawImage(this.x, v.t >> 1, v.u >> 1, 3);
        } else {
            pc.a(graphics, this.c, this.d, this.f, this.g, v.aj, false);
        }
        if (this.s) {
            com.mg.sq.a.h.a(graphics, this.t, this.c + this.f / 2, this.d + p + this.q / 2 - 5, 1);
        }
        Graphics graphics2 = graphics;
        ie ie2 = this;
        int n2 = ie2.w.getWidth() / 3;
        int n3 = ie2.w.getHeight();
        int n4 = (l - 2 + ie2.m.length) % ie2.m.length;
        int n5 = 0;
        while (n5 < 2) {
            int n6 = (n4 + n5 + ie2.m.length) % ie2.m.length;
            cw.a(graphics2, ie2.w, 0, 0, n2, n3, ie2.m[n6], ie2.n[n6], 3);
            ++n5;
        }
        if (ie2.y >= 0) {
            bx.c.a(graphics2, String.valueOf(ie2.y) + "%", ie2.c + ie2.f / 2, ie2.d + (p + ie2.q) / 2, 3);
        }
        cw.a(graphics2, ie2.w, n2, 0, n2, n3, ie2.m[l], ie2.n[l], 3);
        cw.a(graphics2, ie2.w, n2 + n2, 0, n2, n3, ie2.m[k], ie2.n[k], 3);
        int n7 = 0;
        int n8 = 0;
        while (n8 < this.u.length) {
            bx.b.a(graphics, this.u[n8], this.c + this.f / 2 + 5, this.d + p + p + this.q + n7, 1);
            n7 += bx.b.a();
            ++n8;
        }
    }

    protected final void g() {
        if (this.s) {
            int n2 = (int)((long)this.o - this.v.g());
            if (n2 <= 0) {
                n2 = 0;
                com.mg.sq.a.s().o();
            }
            this.t = String.valueOf(n2);
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

