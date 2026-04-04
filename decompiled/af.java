/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class af
extends ae {
    private boolean j;

    public final void a(int n2, int n3, int n4, int n5, int n6, int n7) {
        if (n7 < 18) {
            n7 = 18;
        }
        if (n6 < 18) {
            n6 = 18;
        }
        this.a = n2 + n6 / 2;
        this.b = n3 + n7 / 2;
        this.c = n4 + n6 / 2;
        this.d = n5;
        this.g = 18;
        this.h = 18;
        this.e = n6;
        this.f = n7;
        this.c = this.a;
        this.d = this.b;
        this.j = true;
        this.i = false;
    }

    public final void b() {
        if (this.i) {
            return;
        }
        if (this.j) {
            int n2;
            if (this.e != this.g) {
                n2 = this.g - this.e;
                this.e = Math.abs(n2) < 10 ? (this.e += n2) : (this.e += n2 / 2);
            }
            if (this.f != this.h) {
                n2 = this.h - this.f;
                this.f = Math.abs(n2) < 10 ? (this.f += n2) : (this.f += n2 / 2);
            }
            this.i = this.f == this.h && this.e == this.g;
            return;
        }
        int n3 = this.d;
        if (Math.abs(n3 -= this.b) < 2) {
            this.b += n3;
            this.i = true;
            return;
        }
        this.b += n3 / 2;
    }

    public final void a(Graphics graphics) {
        ak.c().a(graphics, this.c(), this.d(), this.e(), this.f());
    }
}

