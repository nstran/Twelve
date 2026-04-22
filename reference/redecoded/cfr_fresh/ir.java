/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ir
extends at {
    private int a;
    private int b;
    private int c;

    public final void j(int n2) {
        ((at)null).j(n2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
    }

    public final void b(Graphics graphics, int n2, int n3) {
        if (!((at)null).m() || this.a < 5) {
            return;
        }
        ((as)null).a(graphics, n2 + null[0], n3 + null[1]);
    }

    public final void c(Graphics graphics, int n2, int n3) {
        if (!((at)null).m() || this.a >= 5) {
            return;
        }
        ((as)null).a(graphics, n2 + null[0], n3 + null[1]);
    }

    public final void i() {
        if (!this.r) {
            return;
        }
        if (this.b > 0) {
            --this.b;
            if (this.b == 0) {
                ((at)null).b(true);
            } else {
                return;
            }
        }
        ((as)null).i();
        if (this.c > 0) {
            --this.c;
            return;
        }
        this.c = 2;
        this.a = (this.a + 1) % (null).length;
    }
}

