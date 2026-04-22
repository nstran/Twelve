/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class fu
extends ex {
    private int k;
    private Image l;
    private k m;
    private int n;
    private int o;
    private String[] p;
    private int q = 16177368;
    private int r = 0xFBB5B5;
    private int s = 0xF88989;
    private int t = -1;

    public fu(Image image, int n2) {
        super("", n2);
        this.l = image;
        this.k = n2;
        this.e(20);
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        if (this.l == null) {
            this.p = bx.a("Th\u00eam Kul", this.e() - 6);
        }
    }

    public final void a(String string) {
        this.p = bx.a(string, this.e() - 6);
    }

    public final void b(int n2, int n3, int n4, int n5) {
        this.m = new k(n2, n3, n4, n5);
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
            n5 = n2;
            n4 = n3;
            int n6 = this.f();
            int n7 = this.e();
            graphics.setColor(this.q);
            graphics.fillRect(n5, n4, n7, n6);
            pc.a(graphics, n5 - 1, n4 - 1, n7 + 2, n6 + 2, this.r, -1);
            cw.b(graphics, this.s, n5 - 2, n4 - 2, n7 + 4, n6 + 4);
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
                    bx.d.a(graphics, this.p[n4], n2 + this.e() / 2, n3 + n5, 1);
                    n5 += bx.d.a();
                    ++n4;
                }
                return;
            }
        } else {
            try {
                cw.a(graphics, this.l, this.m.a, this.m.b, this.m.c, this.m.d, n2, n3, 20);
                return;
            }
            catch (Exception exception) {
                ct.a("[pictureButon] error in  draw method " + this.m);
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
        fu fu2 = this;
        return "PictureButton " + fu2.k + "   " + this.j;
    }
}

