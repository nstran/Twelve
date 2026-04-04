/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class fa
extends au {
    public boolean i;
    public boolean j;
    public long k;
    private String m;
    private String n;
    public int l = 0;

    public fa(boolean bl2, long l2, String string, String string2, int n2, int n3) {
        this.j = bl2;
        this.k = l2;
        this.m = string;
        this.n = string2;
        this.l = n2;
        this.d(n3);
    }

    public final void d(boolean bl2) {
        if (bl2) {
            this.e(40);
        } else {
            this.e(22);
        }
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        super.d(bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        n3 += this.d();
        n2 += this.c() + 2;
        if (this.g) {
            oz.e(graphics, n2 - 2, n3, this.e(), this.f());
        }
        n3 += 3;
        if (this.j) {
            d d2 = ca.d;
            d2.c(true);
            d2.a(graphics, this.m, n2 + 25, n3 + 3, 0);
            d2.c();
            if (this.g) {
                d2 = ca.c;
                d2.a(graphics, this.n, n2 + 25, n3 + 19, 0);
            }
            if (this.i) {
                oz.d(graphics, -18, n2 + 3, n3 + 4, 0);
            } else {
                oz.d(graphics, -17, n2 + 3, n3 + 4, 0);
            }
        } else {
            ca.d.a(graphics, this.m, n2 + 25, n3 + 4, 0);
            oz.d(graphics, -11, n2 + 3, n3 + 4, 0);
            if (this.g) {
                com.mg.sq.a.g.a(graphics, this.n, n2 + 25, n3 + 18, 0);
            }
        }
        this.c = false;
    }
}

