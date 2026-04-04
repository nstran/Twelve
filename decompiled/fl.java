/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class fl
extends ex {
    public fl(String string, int n2) {
        super(string, -10);
    }

    public final void d(boolean bl2) {
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        super.d(bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.e || !this.c) {
            return;
        }
        n2 += this.c();
        n3 += this.d();
        if (this.g) {
            graphics.setColor(7267055);
        } else {
            graphics.setColor(14722016);
        }
        int n4 = this.f() - 2;
        graphics.fillRect(n2 + 1, n3 + 1, this.e() - 2, n4);
        oz.a(graphics, n2, n3, this.e(), this.f(), 2401717, -1);
        if (this.g) {
            com.mg.sq.a.h.a(graphics, this.i, n2 + this.e() / 2, n3 + 3, 1);
            graphics.drawImage(oz.e, n2 + this.e() / 2, n3 + 9, 17);
        } else {
            ca.c.a(graphics, this.i, n2 + this.e() / 2, n3 + 3, 1);
        }
        this.c = false;
    }
}

