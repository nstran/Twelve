/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class lm
extends q {
    public ef i;
    public int j;
    private int k;

    public lm(String string, int n2, int n3, int n4, int n5, int n6) {
        this.g = string;
        this.a = n2;
        this.f = n3;
        this.b = n4;
        this.c = n5;
        this.d = 16;
        this.e = 16;
        this.i = new ef();
        this.i.a(string.substring(2, string.length() - 1));
        this.j = this.i.d == 1 ? -10 : (this.i.d == 3 ? -8 : (this.i.d == 2 ? -9 : -7));
        if (this.i.e == '0') {
            this.k = -24;
            return;
        }
        if (this.i.e == '2') {
            this.k = -6;
            return;
        }
        if (this.i.e == '1') {
            this.k = -5;
            return;
        }
        this.k = 1000;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        oz.d(graphics, this.j, n2 + this.d(), n3 + this.e(), 0);
        if (this.k <= 0) {
            oz.d(graphics, this.k, n2 + this.d() + this.f(), n3 + this.e() - 2, 24);
        }
    }

    public static String a(ef ef2) {
        short s2 = ef2.d;
        if (s2 == 1) {
            return "Xem \u1ea2nh";
        }
        if (s2 == 3) {
            return "Nghe audio";
        }
        if (s2 == 2) {
            return "Xem video";
        }
        return null;
    }
}

