/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class gh
extends ax {
    private String a = "";
    private int b = 60;

    public gh(String string) {
        this.a = string;
        this.m = z.r;
        this.n = 0;
        this.o = ca.d.a(string);
        this.b = 40;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        oz.b(graphics, 0, 0, z.r, 20, false);
        ca.d.a(graphics, this.a, this.m, 4, 0);
    }

    public final void i() {
        if (!this.r) {
            return;
        }
        if (this.m > z.r / 3) {
            this.m /= 10;
            return;
        }
        if (this.b > 0) {
            --this.b;
            return;
        }
        if (this.m > 0 || this.m > -this.o) {
            this.m -= 2;
            return;
        }
        this.r = false;
        com.mg.sq.a.q().f();
    }
}

