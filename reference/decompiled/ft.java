/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ft
extends ex {
    private int k;
    private Image l;
    private n m;
    private int n;
    private int o;
    private String[] p;
    private int q = 16177368;
    private int r = 0xFBB5B5;
    private int s = 0xF88989;
    private int t = -1;

    public ft(Image image, int n2) {
        super("", n2);
        this.l = image;
        this.k = n2;
        this.e(20);
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        if (this.l == null) {
            this.p = ca.a("Th\u00eam Kul", this.e() - 6);
        }
    }

    public final void a(String string) {
        this.p = ca.a(string, this.e() - 6);
    }

    public final void b(int n2, int n3, int n4, int n5) {
        this.m = new n(n2, n3, n4, n5);
    }

    public final void a(Image image) {
        this.l = image;
    }

    public final void a(int n2, int n3, int n4) {
        this.q = 22523;
        this.r = 9287679;
        this.s = 22523;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4;
        int n5;
        n2 += this.c();
        n3 += this.d();
        if (this.g) {
            n5 = this.f();
            n4 = this.e();
            graphics.setColor(this.q);
            graphics.fillRect(n2, n3, n4, n5);
            oz.a(graphics, n2 - 1, n3 - 1, n4 + 2, n5 + 2, this.r, -1);
            cz.b(graphics, this.s, n2 - 2, n3 - 2, n4 + 4, n5 + 4);
        }
        if (this.l == null) {
            if (!this.g) {
                graphics.setColor(10659250);
                graphics.fillRect(n2, n3, this.e(), this.f());
                graphics.setColor(0xD2D0D2);
                graphics.drawRect(n2, n3, this.e(), this.f());
                graphics.drawRect(n2 - 1, n3 - 1, this.e() + 2, this.f() + 2);
            }
            if (this.p != null) {
                n5 = 10;
                n4 = 0;
                while (n4 < this.p.length) {
                    ca.d.a(graphics, this.p[n4], n2 + this.e() / 2, n3 + n5, 1);
                    n5 += ca.d.a();
                    ++n4;
                }
                return;
            }
        } else {
            try {
                cz.a(graphics, this.l, this.m.a, this.m.b, this.m.c, this.m.d, n2, n3, 20);
                return;
            }
            catch (Exception exception) {
                cw.a("[pictureButon] error in  draw method " + this.m);
            }
        }
    }

    public final int a() {
        return this.k;
    }

    public final void n() {
        ++this.o;
        if (this.o > 5) {
            this.n = -this.n;
            this.o = 0;
        }
    }

    public final int r() {
        return this.t;
    }

    public final void h(int n2) {
        this.t = n2;
    }

    public final String toString() {
        ft ft2 = this;
        return "PictureButton " + ft2.k + "   " + this.j;
    }
}

