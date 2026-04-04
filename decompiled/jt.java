/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class jt
extends jz {
    public int s = 7;
    public int t = 0;
    public int u = 2;
    private nn x = new nn();
    private boolean y = true;
    private int z = 60;

    public jt(Image image, int n2) {
        super(image, 1);
        this.a(image, 1);
        this.a((byte)0);
        this.u = cy.a(4);
        this.s = cy.a(3, 7);
        this.x.a();
        this.g = 0;
    }

    public final void l(int n2) {
        this.z = 60;
    }

    public final void a(byte by2) {
        this.e = by2;
        switch (by2) {
            case 0: {
                return;
            }
            case 1: {
                this.t = this.s;
            }
        }
    }

    public final void k() {
        super.k();
        if (this.x.g() > (long)this.z) {
            this.b(false);
        }
        if (this.x.g() > (long)(this.z - 1)) {
            this.y = !this.y;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.y) {
            return;
        }
        super.a(graphics, n2, n3);
    }
}

