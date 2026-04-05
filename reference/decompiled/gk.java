/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class gk
extends au {
    private int i;

    public gk(int n2) {
        this.i = n2;
        this.e(22);
        this.d(z.r);
    }

    public final void d(boolean bl2) {
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        super.d(bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        n2 += this.c();
        n3 += this.d();
        if (this.g) {
            oz.e(graphics, n2 - 2, n3, this.e(), this.f());
        }
        com.mg.sq.a.g.c(true);
        com.mg.sq.a.g.a(graphics, "Xem th\u00eam...", n2 + this.i, n3 + 4, 0);
        com.mg.sq.a.g.c();
        this.c = false;
    }
}

