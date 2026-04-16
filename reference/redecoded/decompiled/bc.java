/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bc
extends au {
    private String c = null;
    private String[] d;
    private int e;
    private d f;
    private int g = 41377;
    private d h;

    public final void a(d d2) {
        this.f = d2;
    }

    public bc(String object, int n2) {
        this.c = object;
        this.e = 0;
        object = this;
        this.f = bx.c;
        ((bc)object).h = bx.b;
    }

    public final void a(k k2) {
        super.a(k2);
        if (this.c != null) {
            this.d = bx.a(this.c, k2.c, this.f);
        }
        if (this.d != null) {
            this.b = this.d.length * 15;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.d != null) {
            int n4 = this.a.b + this.a.d;
            int n5 = 0;
            while (n5 < this.d.length) {
                if (n3 > n4) break;
                this.f.a(graphics, this.d[n5], n2, n3, this.e);
                n3 += 15;
                ++n5;
            }
        }
    }
}

