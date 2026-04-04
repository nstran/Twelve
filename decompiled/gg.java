/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gg
extends au {
    private int i = 0;
    private int j = 0;
    private String k = "";

    public gg(String string) {
        this.k = string;
        this.e(ca.d.a() + this.j + this.i);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        if (this.m()) {
            graphics.setColor(7267055);
            graphics.fillRect(this.c() + n2, this.d() + n3 + this.i - 1, this.e(), ca.d.a() + 2);
        }
        cz.a(graphics);
        cz.a(graphics, this.c() + n2, this.d() + n3 + this.i - 1, this.e(), ca.d.a());
        ca.d.c(true);
        ca.d.b(true);
        ca.d.a(graphics, this.k, this.c() + this.e() / 2 + n2, this.d() + n3 + this.i, 1);
        ca.d.c();
        cz.b(graphics);
        this.c(true);
    }

    public final void d(int n2, int n3) {
        this.i = n2;
        this.j = 5;
        this.e(ca.d.a() + this.j + this.i);
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5 + this.i + this.j);
    }
}

