/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class nk
extends ad {
    private ax h;

    public final void a(ax ax2) {
        this.h = ax2;
    }

    public final void a(String string, int n2, int n3) {
        this.d = string;
        this.a = n2;
        this.b = n3;
        this.c = false;
        this.e = 0;
    }

    public final void b() {
        if (this.c || this.d == null) {
            return;
        }
        ++this.e;
        if (this.e >= 30) {
            this.c = true;
            this.d = null;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.d == null || this.c) {
            return;
        }
        int n4 = this.a;
        int n5 = this.b;
        if (this.h != null) {
            n4 = this.h.n() + this.h.p() / 2;
            n5 = this.h.o();
        }
        if (this.g != null) {
            this.g.a(graphics, this.d, n4 + n2, n5 + n3 - this.e, 1);
        }
    }
}

