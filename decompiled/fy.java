/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class fy
extends au {
    public lp i;
    private static Image j;
    private static Image k;
    private static Image l;

    public fy(lp lp2) {
        this.i = lp2;
        this.e(22 + ca.d.a());
        this.d(z.r);
        if (lp2.a() && j == null) {
            j = f.d("/m/lock");
        }
        if (l == null) {
            l = f.d("/m/arena");
        }
        if (k == null) {
            k = f.d("/m/room");
        }
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
        n3 += this.d();
        n2 += this.c() + 2;
        d d2 = ca.d;
        d2.c(true);
        if (this.g) {
            oz.e(graphics, n2 - 2, n3, this.e(), this.f());
        }
        if (this.i.e != null) {
            ca.a(graphics, com.mg.sq.a.g, this.i.e, n2 + 50, n3 + 6 + d2.a(), this.e(), this.f(), 0);
        }
        d2.a(graphics, this.i.c, n2 + 50, n3 + 4, 0);
        d2.c();
        if (this.i.g == 2) {
            if (l != null) {
                graphics.drawImage(l, n2 + 3, n3 + 3, 0);
            }
        } else if (k != null) {
            graphics.drawImage(k, n2 + 3, n3 + 3, 0);
        }
        if (this.i.a() && j != null) {
            graphics.drawImage(j, n2 + z.r - j.getWidth() - 4, n3 + 6, 0);
        }
        this.c = false;
    }
}

