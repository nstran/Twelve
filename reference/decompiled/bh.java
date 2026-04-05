/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bh
extends bd {
    private String e;

    public final String d() {
        return this.e;
    }

    public bh(String string, int n2) {
        super(n2);
        this.e = string;
        this.c = ca.b.a(string);
    }

    public final void a(Graphics graphics) {
        if (this.d > 0) {
            ca.b.a(graphics, this.e, this.a, this.b + 1, 0);
            return;
        }
        ca.c.a(graphics, this.e, this.a, this.b, 0);
    }
}

