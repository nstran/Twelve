/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ip
extends il {
    private static byte[][] s;
    private Image t;

    static {
        byte[][] byArrayArray = new byte[3][];
        byte[] byArray = new byte[5];
        byArray[2] = 1;
        byArray[3] = 1;
        byArray[4] = 1;
        byArrayArray[0] = byArray;
        byArrayArray[1] = new byte[]{2, 2, 2, 3, 3, 3};
        byArrayArray[2] = new byte[]{4, 5, 5};
        s = byArrayArray;
    }

    public ip(Image image, Image image2) {
        this.a(image, 6);
        this.t = image2;
        this.a(s);
        this.r = false;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.c(n2, n3);
        this.d(0);
        this.r = true;
    }

    public final void k() {
        switch (this.e) {
            case 0: {
                if (!this.j()) break;
                this.d(1);
                return;
            }
            case 2: {
                if (!this.j()) break;
                this.r = false;
                return;
            }
            case 1: {
                if (!this.j()) break;
                this.d(2);
            }
        }
    }

    public final void b(Graphics graphics) {
        if (!this.r || this.g < 0) {
            return;
        }
        int n2 = this.f[this.e][this.g];
        cz.a(graphics, this.b, n2 * this.o, 0, this.o, this.p, this.m, this.n, 36);
        graphics.drawRegion(this.b, n2 * this.o, 0, this.o, this.p, 2, this.m + 1, this.n, 40);
    }

    public final void c(Graphics graphics) {
        if (!this.r || this.g < 0) {
            return;
        }
        int n2 = this.f[this.e][this.g];
        if (n2 == 2) {
            graphics.drawRegion(this.t, 0, 0, this.t.getWidth(), this.t.getHeight(), 5, this.m + 5, this.n - 5, 40);
            graphics.drawRegion(this.t, 0, 0, this.t.getWidth(), this.t.getHeight(), 5, this.m - 1, this.n - 7, 36);
            graphics.drawImage(this.t, this.m + 2, this.n - 9 - this.t.getHeight(), 33);
            return;
        }
        if (n2 == 3) {
            graphics.drawImage(this.t, this.m, this.n - 8, 33);
            graphics.drawImage(this.t, this.m + 4, this.n - 3 - this.t.getHeight(), 33);
        }
    }
}

