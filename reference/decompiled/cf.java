/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class cf
extends ax {
    private Image a;

    public cf(Image image, int n2, int n3) {
        this.m = n2;
        this.n = n3;
        this.o = image.getWidth();
        this.p = image.getHeight();
        this.a = image;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.a, this.m, this.n, 0);
    }

    public final void i() {
    }
}

