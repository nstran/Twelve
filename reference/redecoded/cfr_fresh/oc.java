/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class oc
extends ak {
    private Image c;
    private int d;
    private int k;
    private int[] l;
    private int[] m;
    private k n;
    private Image o;

    public oc() {
        this.e = -100001;
        oc oc2 = this;
        if (oc2.o == null) {
            oc2.o = f.d("/offline/avatardownloadscreen");
        }
        oc2.b = 0;
        oc2.c = pc.b;
        oc2.n = new k((oc2.f - 70) / 2, (oc2.g - 70) / 2, 70, 70);
        int n2 = oc2.n.a + oc2.n.c / 2;
        int n3 = oc2.n.b + oc2.n.d / 2;
        int n4 = -90;
        oc2.l = new int[8];
        oc2.m = new int[8];
        int n5 = 0;
        while (n5 < 8) {
            oc2.l[n5] = n2 + (25 * l.b(n4) >> 14);
            oc2.m[n5] = n3 + (25 * l.a(n4) >> 14);
            n4 += 45;
            ++n5;
        }
        this.a((ba)null);
    }

    public final void b_() {
        try {
            super.b_();
            this.k = this.d;
            this.d = (this.d + 1) % this.l.length;
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    protected final void b(Graphics graphics) {
        int n2 = 0;
        if (this.o != null && v.u > this.o.getHeight()) {
            n2 = (v.u - this.o.getHeight()) / 2;
        }
        graphics.drawImage(this.o, this.f / 2, this.g - n2, 33);
        bx.b.a(graphics, String.valueOf(this.a), this.n.a + (this.n.c >> 1), this.n.b - 4 + (this.n.d >> 1), 1);
        n2 = this.c.getWidth() / 3;
        int n3 = this.c.getHeight();
        cw.a(graphics, this.c, 0, 0, n2, n3, this.l[(this.k - 1 + this.l.length) % this.l.length], this.m[(this.k - 1 + this.l.length) % this.l.length], 3);
        cw.a(graphics, this.c, n2, 0, n2, n3, this.l[this.k], this.m[this.k], 3);
        cw.a(graphics, this.c, n2 + n2, 0, n2, n3, this.l[this.d], this.m[this.d], 3);
    }

    protected final void a(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, v.t, v.u);
    }
}

