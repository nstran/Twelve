/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ac
extends ab {
    private boolean h;

    public final void b() {
        super.b();
        if (this.e % 3 == 0) {
            this.h = !this.h;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        if (this.h) {
            this.g.a(graphics, this.d, 0 + this.a, 0 + this.b, 1);
        }
    }
}

