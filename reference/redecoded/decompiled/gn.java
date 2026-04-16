/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class gn
extends aq {
    private Image i;
    private String[] j;
    private int k = 3;

    public gn(Image image, String string, int n2) {
        this.i = image;
        this.j = bx.a(string, n2 - 4);
        if (image != null) {
            this.k = image.getHeight() + 2;
        }
        this.e(this.k + (this.j.length * bx.d.a() + 5));
        this.d(n2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.m()) {
            graphics.setColor(7267055);
            graphics.fillRect(this.c() + n2, this.d() + n3, this.e(), this.f());
        }
        if (this.i != null) {
            cw.a(graphics, this.i, 0, 0, this.i.getWidth() / 7, this.i.getHeight(), this.c() + this.e() / 2 + n2, this.d() + n3, 17);
        }
        bx.a(graphics, bx.d, this.j, this.c() + 3 + n2, this.d() + n3 + this.k, this.e(), this.f(), 0);
    }
}

