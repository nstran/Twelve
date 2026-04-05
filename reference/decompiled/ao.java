/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class ao
extends aq {
    private int c = 0;
    protected int a = 0;
    private int d = 5;
    private ar k;
    private ah l;
    private boolean m;
    private boolean n;
    protected int b = z.af;

    public ao() {
        this.e = -100001;
        this.a();
    }

    public final void a() {
        this.f = z.r;
        this.g = z.s;
        this.c = 0;
        this.a = 0;
        this.d = 5;
        this.k = null;
        this.m = false;
    }

    public final void a(ar ar2, ah ah2, boolean bl2) {
        if (ar2 != null) {
            this.k = ar2;
            this.l = ah2;
            this.m = true;
            this.n = bl2;
        }
    }

    public final void a(int n2) {
        this.a = 0;
    }

    public void b_() {
        if (this.a >= 100) {
            if (this.k != null) {
                ak.b().a(this.k, this.n, this.l);
                this.k = null;
                return;
            }
        } else {
            if (this.m) {
                this.d += 3;
            }
            this.a += this.d;
            if (this.a > 100) {
                this.a = 100;
            }
        }
    }

    protected void a(Graphics graphics) {
        graphics.setColor(this.b);
        graphics.fillRect(0, 0, this.f, this.g);
    }

    protected void b(Graphics graphics) {
        graphics.setColor(0xFFFFFF);
        graphics.drawRect(0, this.g - 20, this.f, 19);
        int n2 = this.g - 20;
        graphics.setColor(15484506);
        graphics.fillRect(0, n2 + 1, this.a * this.f / 100, 18);
        ca.b.a(graphics, "Vui l\u00f2ng ch\u1edd...", this.f / 2, n2 + 3, 1);
    }

    public final void c(Graphics graphics) {
        if (this.c <= 2) {
            this.a(graphics);
            ++this.c;
        }
        this.b(graphics);
    }
}

