/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class lg
extends ax {
    private String a = "";

    public lg(int n2, int n3) {
        this.a = String.valueOf(n2 / 1024) + "/" + n3 / 1024 + "KB";
        this.o = ca.d.a(this.a) + 8;
        this.m = (z.r - this.o) / 2;
        this.n = 2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        oz.a(graphics, this.m, 0, this.o, 18, 0xEF0000, 16177368);
        ca.d.a(graphics, this.a, this.m + 4, this.n + 2, 0);
    }

    public final void i() {
    }
}

