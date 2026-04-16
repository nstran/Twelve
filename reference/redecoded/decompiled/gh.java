/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gh
extends aq {
    private int i = 0;
    private int j = 0;
    private String k = "";

    public gh(String string) {
        this.k = string;
        this.e(bx.d.a() + this.j + this.i);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        if (this.m()) {
            graphics.setColor(7267055);
            graphics.fillRect(this.c() + n2, this.d() + n3 + this.i - 1, this.e(), bx.d.a() + 2);
        }
        cw.a(graphics);
        cw.a(graphics, this.c() + n2, this.d() + n3 + this.i - 1, this.e(), bx.d.a());
        bx.d.c(true);
        bx.d.b(true);
        bx.d.a(graphics, this.k, this.c() + this.e() / 2 + n2, this.d() + n3 + this.i, 1);
        bx.d.c();
        cw.b(graphics);
        this.c(true);
    }

    public final void d(int n2, int n3) {
        this.i = n2;
        this.j = 5;
        this.e(bx.d.a() + this.j + this.i);
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5 + this.i + this.j);
    }
}

