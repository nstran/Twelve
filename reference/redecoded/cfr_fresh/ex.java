/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class ex
extends aq {
    private int k;
    protected String i;
    protected bf j;

    public ex(String string, int n2) {
        this.i = string;
        this.k = n2;
        this.e(20);
    }

    public final void a(bf bf2) {
        this.j = bf2;
    }

    public int a() {
        return this.k;
    }

    public void a(String string) {
        this.i = string;
    }

    public final String q() {
        return this.i;
    }

    public final boolean c(int n2, int n3) {
        if (!this.j()) {
            return false;
        }
        if (this.h().a(n2, n3)) {
            if (!this.g) {
                this.g = true;
            }
            return this.f(95);
        }
        return false;
    }

    public final boolean f(int n2) {
        if (!this.j()) {
            return false;
        }
        if (n2 == 95 && this.j != null) {
            this.j.d(-1, this.a());
            return true;
        }
        return false;
    }

    public void a(Graphics graphics, int n2, int n3) {
        if (!this.e) {
            return;
        }
        n2 += this.c();
        n3 += this.d();
        if (this.g) {
            pc.f(graphics, n2 - 1, n3 - 1, this.e() + 2, 22);
        }
        ag.c().d(graphics, n2, n3, this.e(), this.f());
        cw.a(graphics);
        cw.a(graphics, n2 + 3, n3, this.e() - 6, this.f());
        if (this.g) {
            bx.c.a(graphics, this.i, n2 + this.e() / 2, n3 + 3, 1);
        } else {
            bx.d.a(graphics, this.i, n2 + this.e() / 2, n3 + 3, 1);
        }
        cw.b(graphics);
    }

    public static final ex a(String string, int n2) {
        ex ex2 = new ex(string, n2);
        ex2.d(bx.d.a(string) + 30);
        ex2.e(bx.d.a() + 6);
        return ex2;
    }
}

