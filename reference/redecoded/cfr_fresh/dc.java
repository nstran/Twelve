/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class dc
extends aq {
    public static final Image i = f.d("/broken_heart");
    public int j = 0;
    private Image l = null;
    public Object k;
    private byte m = 0;
    private byte n;
    private int o = 3;
    private int p;
    private int q;
    private int r = 3;
    private d s;

    public dc(Image image, Object object, int n2, d d2) {
        this.k = object;
        this.s = d2;
        int n3 = n2;
        dc dc2 = this;
        this.j = n3;
        if (image != null) {
            this.l = image;
            this.d(this.l.getWidth());
            this.e(this.l.getHeight());
        } else {
            this.d(32);
            this.e(32);
        }
        if (this.k instanceof ll && (((ll)object).m == 4 || ((ll)object).m == 7)) {
            this.m = (byte)cv.a(3);
            this.r = cv.a(3, 6);
            this.p = pc.b.getWidth() / 3;
            this.q = pc.b.getHeight();
        }
    }

    public final void n() {
        this.n = (byte)(this.n + 1);
        if (this.n > this.r) {
            this.n = 0;
            this.m = (byte)(this.m + 1);
            if (this.m >= this.o) {
                this.m = 0;
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        n2 += this.c();
        n3 += this.d();
        switch (this.j) {
            case 0: {
                if (this.l == null) {
                    return;
                }
                graphics.drawImage(this.l, n2, n3, 0);
                if (this.k != null && ((ll)this.k).p == 0) {
                    graphics.drawImage(i, n2 + i.getWidth(), n3 + this.f(), 40);
                }
                if (this.k != null && (((ll)this.k).m == 4 || ((ll)this.k).m == 7 || ((ll)this.k).m == 8)) {
                    cw.a(graphics, pc.b, this.m * this.p, 0, this.p, this.q, n2 + this.l.getWidth(), n3, 24);
                }
                if (this.k == null || ((ll)this.k).j <= 0 || this.s == null) break;
                this.s.a(graphics, "+" + ((ll)this.k).j, n2 + 32, n3 + 32 - bx.c.a(), 2);
                return;
            }
            case 9: {
                graphics.drawImage(this.l, n2, n3, 0);
                bx.c.a(graphics, "0", n2 + this.l.getWidth(), n3 + this.l.getHeight() - bx.c.a(), 2);
                return;
            }
            default: {
                pc.g(graphics, ((lm)this.k).j, n2, n3, 0);
                if (((lm)this.k).l == 1) break;
                bx.c.a(graphics, "" + ((lm)this.k).g, n2 + 32, n3 + 32 - bx.c.a(), 2);
            }
        }
    }

    public final String toString() {
        String string = "type = " + this.j + "    info =  " + this.k;
        return string;
    }
}

