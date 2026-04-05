/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bb
extends ax {
    private a a;
    private int b;
    private ax c;
    private ax d;

    public bb(int n2) {
        this.a = new a(n2);
        this.b = n2;
    }

    public final void a(ax ax2) {
        a a2 = this.a;
        synchronized (a2) {
            if (this.c == null) {
                this.c = ax2;
                return;
            }
            if (this.a.d() >= this.b) {
                this.a.a(0);
            }
            this.a.a(ax2);
            return;
        }
    }

    public final ax a() {
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

    public final void b(ax ax2) {
        this.d = ax2;
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
                    this.c = (ax)this.a.b();
                    this.a.a(0);
                }
            }
        }
    }
}

