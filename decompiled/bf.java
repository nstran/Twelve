/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bf
extends au {
    private r i;
    private boolean j = false;

    private bf(String string, int n2, d d2, boolean bl2, int n3) {
        this.i = new r(d2, string, 0, 0, n2 - 2, d2.a(), n3);
        d2.c();
        this.a(1, 1, n2, this.i.c() + 2);
        this.b(false);
    }

    private bf(String string, int n2, d d2, int n3) {
        this(string, n2, d2, false, 1);
    }

    public bf(String string, int n2, d d2) {
        this(string, n2, d2, 1);
    }

    public final r a() {
        return this.i;
    }

    public final void d(boolean bl2) {
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        super.d(bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.c) {
            if (this.j) {
                this.i.a().c(true);
            }
            this.i.a(graphics, n2 + this.c(), n3 + this.d());
            this.c = false;
            if (this.j) {
                this.i.a().c();
            }
        }
    }
}

