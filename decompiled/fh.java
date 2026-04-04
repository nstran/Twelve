/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class fh
extends au {
    private dn i;
    private String j;
    private String[] k;

    public fh(dn dn2) {
        this.i = dn2;
        this.j = String.valueOf(dn2.c()) + " (" + dn2.a() + ")";
        this.k = new String[0];
        this.e(22);
        this.d(z.r);
    }

    public final void d(boolean bl2) {
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        if (bl2) {
            this.e(22 + this.k.length * ca.d.a() + 5);
        } else {
            this.e(22);
        }
        super.d(bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        n3 += this.d();
        n2 += this.c() + 2;
        d d2 = ca.d;
        d2.c(true);
        if (this.g) {
            oz.e(graphics, n2 - 2, n3, this.e(), this.f());
            if (this.k != null) {
                ca.a(graphics, com.mg.sq.a.g, this.k, n2 + 25, n3 + 6 + d2.a(), this.e(), this.f(), 0);
            }
        }
        d2.a(graphics, this.j, n2 + 25, n3 + 4, 0);
        d2.c();
        if (this.i.a) {
            oz.d(graphics, -18, n2 + 3, n3 + 6, 0);
        } else {
            oz.d(graphics, -17, n2 + 3, n3 + 4, 0);
        }
        this.c = false;
    }
}

