/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class kf
extends ax {
    private String i = null;
    protected Image a = null;
    protected int b = 64;
    protected int c = 1;
    public jl d;
    public ll e = null;
    public Image f;
    protected boolean g = false;
    protected int h = 0;

    public kf(Image image, int n2) {
        this.f = image;
        if (image == null) {
            cw.a("location object truyen h\u00ecnh vao null");
            return;
        }
        this.o = this.f.getWidth();
        this.p = image.getHeight() / n2;
    }

    public kf(ll ll2, jl jl2) {
        this.e = ll2;
        this.d = jl2;
        if (ll2 != null) {
            this.m = this.d.d + (this.d.f - ll2.p()) / 2;
            this.n = this.d.e + (this.d.g - ll2.q()) / 2;
        } else {
            this.m = this.d.d;
            this.n = this.d.e;
        }
        this.j(20);
        this.b(jl2.b);
        this.h(this.d.f);
        this.i(this.d.g);
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (this.f != null) {
            graphics.drawImage(this.f, this.m + n2, this.n + n3, this.q);
        }
        if (this.e != null) {
            this.e.a(graphics, this.m + n2, this.n + n3);
            if (this.i != null) {
                ca.c.a(graphics, this.i, this.m + this.e.p() / 2 + n2, this.n - this.b + n3, 1);
            }
        }
    }

    public final void b(String string) {
        this.i = string;
        this.a(string);
    }

    public final String a() {
        return this.i;
    }

    protected void a(String string) {
    }

    public void a(int n2) {
        this.c = n2;
    }

    public final int b() {
        return this.c;
    }

    public void i() {
    }

    public final void a(boolean bl2, int n2) {
        this.g = false;
        this.h = 0;
    }

    public final boolean c() {
        return this.g;
    }

    public String toString() {
        return String.valueOf(this.i) + "   x = " + this.m + "  y = " + this.n + "   w = " + this.o + "  h= " + this.p;
    }
}

