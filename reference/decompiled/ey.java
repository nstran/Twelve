/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class ey
extends au {
    private boolean i;
    private String j;
    private d k = ca.d;
    private int l = 0;
    private int m = 0;

    public ey() {
        this("", false);
    }

    public ey(String object, boolean bl2) {
        this.i = bl2;
        String string = object;
        object = this;
        this.j = string;
        if (l.b(string)) {
            ((ey)object).m = 0;
            return;
        }
        ((ey)object).m = ((ey)object).k.a(string);
    }

    public final boolean a() {
        return this.i;
    }

    public void e(boolean bl2) {
        this.i = bl2;
    }

    public final boolean f(int n2) {
        if (n2 == 95) {
            this.e(!this.i);
            return true;
        }
        return false;
    }

    public final boolean c(int n2, int n3) {
        if (n2 > this.c() && n2 < this.c() + this.e() && n3 > this.d() && n3 < this.d() + this.f()) {
            if (this.g) {
                this.f(95);
            } else {
                this.d(true);
            }
            return true;
        }
        return false;
    }

    public void a(Graphics graphics, int n2, int n3) {
        n2 = this.c() + n2;
        n3 = this.d() + n3;
        int n4 = 10323806;
        int n5 = 14273459;
        int n6 = 15722458;
        if (this.g && (this.b == null || this.b.m())) {
            if (this.m > 0) {
                n4 = this.e() + this.m + 5;
                oz.f(graphics, n2, n3, n4, this.f());
            }
            n4 = 22523;
            n5 = 9287679;
            n6 = 13295359;
        }
        graphics.setColor(n5);
        graphics.fillRect(n2 + 2, n3 + 2, this.e() - 4, this.f() - 4);
        graphics.setColor(n4);
        graphics.drawRect(n2, n3, this.e() - 1, this.f() - 1);
        graphics.setColor(n6);
        graphics.drawRect(n2 + 1, n3 + 1, this.e() - 3, this.f() - 3);
        if (this.i) {
            oz.f(graphics, n2, n3, this.e(), this.f(), 0);
        }
        this.k.a(graphics, this.j, n2 + this.e() + 5, n3, 0);
    }

    public final void h(int n2) {
        this.l = n2;
    }

    public final int q() {
        return this.l;
    }
}

