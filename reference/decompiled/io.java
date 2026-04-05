/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class io
extends ax {
    public int a;
    public boolean b;
    private int c;
    private Image d;
    private int e;
    private int f = 0;
    private int g;

    public io(int n2, boolean bl2, int n3) {
        this.a = n2;
        this.b = bl2;
        this.f = (bl2 ? 0 : 1) * 3 + n2;
        if (!bl2) {
            this.f = 3;
        }
        this.d = mn.a().P;
        this.e = 8;
        this.o = 17;
        this.p = 17;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.e > 0) {
            return;
        }
        cz.a(graphics, this.d, this.f * this.o, 0, this.o, this.p, n2, n3 + this.c / 2, 0);
    }

    public final void i() {
        if (this.e > 0) {
            --this.e;
            return;
        }
        if (this.c > 6) {
            this.c = 0;
            return;
        }
        if (++this.g > 3) {
            ++this.c;
            this.g = 0;
        }
    }
}

