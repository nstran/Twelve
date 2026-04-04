/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class et
extends au {
    private lq i;

    public et(ds ds2, int n2) {
        this.i = new lq(ds2.a(), 0, 0, n2, ca.c.a() + 4, 1);
        this.d(n2);
        this.e(this.i.c() + 14);
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
        n3 += this.d() + 10;
        n2 += this.c();
        if (this.g) {
            graphics.setColor(7267055);
            graphics.fillRect(n2, n3, this.e(), this.f() - 10);
        } else {
            graphics.setColor(13931764);
            graphics.fillRect(n2, n3, this.e(), this.f() - 10);
        }
        this.i.a(graphics, n2 + 2, n3 + 2);
        if (this.g) {
            graphics.drawImage(oz.e, n2 + this.e() - 20, n3 + this.f() - 27, 0);
        }
        this.c = false;
    }
}

