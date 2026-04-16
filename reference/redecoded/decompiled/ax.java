/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ax
extends at {
    private a a;
    private int b;
    private at c;
    private at d;

    public ax(int n2) {
        this.a = new a(n2);
        this.b = n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(at at2) {
        a a2 = this.a;
        synchronized (a2) {
            if (this.c == null) {
                this.c = at2;
                return;
            }
            if (this.a.d() >= this.b) {
                this.a.a(0);
            }
            this.a.a(at2);
            return;
        }
    }

    public final at a() {
        return this.c;
    }

    public final void b() {
        this.a.a();
        this.c = null;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.d != null) {
            this.d.a(graphics);
        }
        if (this.c != null) {
            this.c.a(graphics);
        }
    }

    public final void b(at at2) {
        this.d = at2;
    }

    public final void i() {
        if (this.d != null) {
            this.d.i();
        }
        if (this.c != null) {
            this.c.i();
            if (!this.c.m()) {
                this.c = null;
                if (this.a.d() > 0) {
                    this.c = (at)this.a.b();
                    this.a.a(0);
                }
            }
        }
    }
}

