/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class fx
extends au {
    private dk i;
    private int[] j;
    private int k;
    private String l;
    private boolean m = false;

    public fx(dk dk2) {
        this.i = dk2;
        this.e(20);
        this.d(z.r);
    }

    public final void a(int[] nArray) {
        this.j = nArray;
        this.k = nArray[2] - nArray[1] - 10;
        this.l = com.mg.sq.a.a(this.i.c, this.k);
    }

    public final void e(boolean bl2) {
        this.m = true;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        n2 += this.c();
        n3 += this.d();
        if (this.g) {
            oz.e(graphics, n2, n3, this.e(), this.f());
        } else if (this.m) {
            int n4 = n2 + 7;
            int n5 = this.e() - 14;
            int n6 = this.f() - 2;
            int n7 = n3 + 1;
            graphics.setColor(12644863);
            graphics.fillRect(n4, n7, n5, n6);
            graphics.setColor(12644849);
            graphics.drawRect(n4, n7, n5, n6);
            graphics.drawRect(n4 - 1, n7 - 1, n5 + 2, n6 + 2);
        }
        ca.c.a(graphics, this.i.b, this.j[0] + n2, n3 += 2, 0);
        ca.c.a(graphics, this.l, this.j[1] + n2, n3, 0);
        ca.c.a(graphics, this.i.d, this.j[2] + n2, n3, 0);
    }
}

