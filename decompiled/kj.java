/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class kj
extends kf {
    private gi j;
    private int k;
    private int s = 3;
    private boolean t = false;
    private String[] u = null;
    public boolean i = false;
    private boolean v = false;
    private int w = 0;

    public kj(Image image, jl jl2, int n2) {
        super(image, 1);
        this.d = jl2;
        this.i();
        this.m = jl2.d + (jl2.f - this.o) / 2;
        this.n = jl2.e + 16 + jl2.g - this.p;
        this.b(jl2.b);
        this.k = ca.c.a() * this.s + 20;
    }

    protected final void a(String string) {
        Graphics graphics = null;
        int n2 = ca.c.a(string);
        int n3 = ca.c.a();
        this.b = this.p;
        if (this.a == null) {
            this.a = Image.createImage((int)n2, (int)n3);
            graphics = this.a.getGraphics();
            graphics.setColor(0xFF0000);
            graphics.fillRect(0, 0, n2, n3);
            ca.c.a(graphics, string, 0, 0, 0);
            this.a = kh.a(this.a, 0xFF0000);
        }
    }

    public final void a(int n2) {
        super.a(n2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        super.a(graphics, n2, n3);
        if (this.a != null) {
            graphics.drawImage(this.a, this.m + this.o / 2 + n2, this.n + n3, 33);
        }
        if (!this.g && this.v) {
            graphics.drawImage(oz.c, this.m + this.o / 2 + n2, this.n + 15 + n3, 33);
        }
    }

    public final void b(Graphics graphics, int n2, int n3) {
        if (this.j != null) {
            this.j.a(graphics, n2, n3);
        }
    }

    public final void c(int n2, int n3) {
        super.c(n2, n3);
        this.m = n2 - this.o / 2;
        this.n = n3 - this.p;
    }

    public final void i() {
        if (this.j != null) {
            this.j.i();
        }
        ++this.w;
        if (this.v) {
            if (this.w >= 40) {
                this.v = false;
                this.w = 0;
            }
        } else if (this.w == 200) {
            this.v = true;
            this.w = 0;
        }
        if (this.g) {
            --this.h;
            if (this.h < 0) {
                this.h = 0;
                this.g = false;
            }
        }
    }

    public final void d() {
        this.j = null;
        this.t = false;
        ok.p = false;
    }
}

