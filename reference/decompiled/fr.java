/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class fr
extends au {
    private dp i;
    private boolean j = false;

    public fr(dp dp2, boolean bl2, int n2) {
        this.i = dp2;
        this.j = bl2;
        this.d(n2);
        this.e(dp2.c.c() + 4);
    }

    public final void d(boolean bl2) {
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        super.d(bl2);
    }

    public final boolean f(int n2) {
        int n3 = this.i.d;
        if (n3 < 0) {
            return false;
        }
        switch (n2) {
            case 97: {
                n3 = this.i.c.b(this.i.d);
                break;
            }
            case 96: {
                n3 = this.i.c.c(this.i.d);
                break;
            }
            case 99: {
                n3 = this.i.c.d(this.i.d);
                break;
            }
            case 98: {
                n3 = this.i.c.e(this.i.d);
                break;
            }
            default: {
                return false;
            }
        }
        if (n3 >= 0 && n3 < this.i.c.d().d()) {
            this.i.d = n3;
            this.b.c(true);
            return true;
        }
        return false;
    }

    public final boolean c(int n2, int n3) {
        if (this.i.d < 0) {
            return false;
        }
        n2 -= this.c();
        n3 -= this.d();
        int n4 = this.i.c.d().d();
        int n5 = 0;
        while (n5 < n4) {
            q q2 = this.i.c.a(n5);
            if (n2 >= q2.d() && n2 <= q2.d() + q2.f() && n3 >= q2.e() && n3 <= q2.e() + q2.g()) {
                if (q2.c() == 0 || q2.c() == 2) break;
                this.i.d = n5;
                return false;
            }
            ++n5;
        }
        return false;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        n3 += this.d();
        n2 += this.c();
        if (this.i.b == 0) {
            if (this.g) {
                graphics.setColor(7267055);
            } else {
                graphics.setColor(2401717);
            }
            graphics.fillRect(n2, n3, this.e(), this.f());
            ca.d.c(true);
            this.i.c.a(graphics, n2 + 2, n3 + 2);
            ca.d.c();
        } else if (this.i.b == 1) {
            int n4 = -1;
            if (!this.j) {
                n4 = 13365492;
                graphics.fillRect(n2, n3, this.e(), this.f());
            }
            if (this.g) {
                n4 = 7267055;
            }
            if (n4 >= 0) {
                graphics.setColor(n4);
                graphics.fillRect(n2, n3, this.e(), this.f());
            }
            this.i.c.a(graphics, n2 + 2, n3 + 2);
            if (this.g) {
                graphics.drawImage(oz.e, n2 + this.e() - 20, n3 + this.f() - 18, 0);
            }
        } else {
            if (this.g) {
                graphics.setColor(7267055);
                graphics.fillRect(n2, n3, this.e(), this.f());
            }
            this.i.c.a(graphics, n2 + 2, n3 + 2);
            if (this.g && this.i.d >= 0) {
                q q2 = this.i.c.a(this.i.d);
                graphics.drawImage(oz.e, n2 + q2.d() + q2.f() / 2 + 6, n3 + q2.e() + q2.g() / 2, 17);
            }
        }
        this.c = false;
    }
}

