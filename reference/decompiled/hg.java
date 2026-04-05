/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class hg
extends ax {
    private Image a = f.d("/m/fsw");
    private Image b = oz.b;
    private int c;
    private int d = this.b.getWidth() / 3;
    private int e = this.b.getHeight();
    private int f;
    private int g;
    private int h;
    private boolean i;
    private boolean j;
    private cs k;

    public hg() {
        try {
            this.k = new cs("vs");
            this.k.b();
            return;
        }
        catch (Exception exception) {
            if (this.k != null) {
                this.k.a();
                this.k = null;
            }
            cw.a("[FightEffect]  Loi am thanh fighteffect");
            return;
        }
    }

    public final void a() {
        this.g = 15;
        this.h = 0;
        this.j = false;
        this.i = false;
        this.c = 0;
    }

    public final void i() {
        if (this.i) {
            return;
        }
        ++this.c;
        if (this.c % 2 == 0) {
            return;
        }
        if (this.g > 0) {
            this.g -= 3;
        }
        if (!this.j) {
            ++this.f;
            if (this.f >= 2) {
                this.f = 2;
                this.j = true;
                return;
            }
        } else {
            this.h += 5;
            --this.f;
            if (this.f < 0) {
                this.f = 0;
                this.i = true;
                if (this.k != null) {
                    this.k.a();
                    this.k = null;
                }
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.drawImage(this.a, n2 + this.m + this.g, n3 + this.n + this.g, 3);
        graphics.drawRegion(this.a, 0, 0, 24, 24, 2, n2 + this.m - this.g, n3 + this.n + this.g, 3);
        if (this.i) {
            return;
        }
        if (!this.j) {
            cz.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.m, n3 + this.n, 3);
            return;
        }
        cz.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.m + this.h, n3 + this.n, 3);
        cz.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.m, n3 + this.n + this.h, 3);
        cz.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.m - this.h, n3 + this.n, 3);
        cz.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.m, n3 + this.n - this.h, 3);
        cz.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.m + this.h, n3 + this.n + this.h, 3);
        cz.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.m + this.h, n3 + this.n - this.h, 3);
        cz.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.m - this.h, n3 + this.n + this.h, 3);
        cz.a(graphics, this.b, this.d * this.f, 0, this.d, this.e, n2 + this.m - this.h, n3 + this.n - this.h, 3);
    }
}

