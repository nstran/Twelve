/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class nn
extends ax {
    public static int a = 15000;
    private boolean b = false;
    private long c;
    private boolean d;
    private long e = 0L;

    public nn() {
        a = 15000;
    }

    public final void a() {
        if (!this.b) {
            this.c = System.currentTimeMillis();
            this.d = true;
        }
        this.b = true;
    }

    public final void b() {
        this.b = false;
    }

    public final boolean c() {
        return this.b;
    }

    public final boolean d() {
        return System.currentTimeMillis() - this.c >= (long)a;
    }

    public final boolean e() {
        boolean bl2 = false;
        if (System.currentTimeMillis() - this.c >= 10000L && this.d) {
            this.d = false;
            bl2 = true;
        }
        return bl2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.b) {
            return;
        }
        ca.b.a(graphics, String.valueOf(this.e), this.m, this.n, 0);
    }

    public final void i() {
        if (!this.b) {
            return;
        }
        long l2 = (long)a - (System.currentTimeMillis() - this.c);
        if (l2 >= 0L) {
            this.e = l2 / 1000L + (long)(l2 % 1000L != 0L ? 1 : 0);
        }
    }

    public final long f() {
        return System.currentTimeMillis() - this.c;
    }

    public final long g() {
        return this.f() / 1000L;
    }
}

