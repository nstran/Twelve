/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class fi
extends aq {
    private dk i;
    private String j;
    private String[] k;

    public fi(dk dk2) {
        this.i = dk2;
        this.j = String.valueOf(dk2.c()) + " (" + dk2.a() + ")";
        this.k = new String[0];
        this.e(22);
        this.d(v.t);
    }

    public final void d(boolean bl2) {
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        if (bl2) {
            this.e(22 + this.k.length * bx.d.a() + 5);
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
        d d2 = bx.d;
        d2.c(true);
        if (this.g) {
            pc.e(graphics, n2 - 2, n3, this.e(), this.f());
            if (this.k != null) {
                bx.a(graphics, com.mg.sq.a.g, this.k, n2 + 25, n3 + 6 + d2.a(), this.e(), this.f(), 0);
            }
        }
        d2.a(graphics, this.j, n2 + 25, n3 + 4, 0);
        d2.c();
        if (this.i.a) {
            pc.d(graphics, -18, n2 + 3, n3 + 6, 0);
        } else {
            pc.d(graphics, -17, n2 + 3, n3 + 4, 0);
        }
        this.c = false;
    }
}

