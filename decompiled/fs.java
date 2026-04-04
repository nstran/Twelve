/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class fs
extends ax {
    private int a = 50;
    private long b = 0L;
    private long c = 0L;
    private long d = 0L;
    private String e = "";
    private int f = 0;

    public fs(long l2) {
        this.b = l2;
        this.e = String.valueOf(l2) + "Ken";
        this.m = z.r - 5;
        this.n = z.s - be.a - ca.c.a();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        n2 = ca.c.a(this.e) + 5;
        oz.a(graphics, this.m - n2, this.n - 2, n2 + 5, ca.c.a() + 2, 0, 0xFFFFFF);
        ca.c.a(graphics, this.e, this.m, this.n, 2);
    }

    public final void i() {
        if (this.b == -1L) {
            return;
        }
        if (!this.r) {
            return;
        }
        if (this.b != this.c) {
            this.c += this.d;
            this.e = String.valueOf(l.a(this.c, ".")) + "Ken";
            long l2 = this.b - this.c;
            this.d = l2 / 2L + l2 % 2L;
            return;
        }
        if (this.f < this.a) {
            ++this.f;
            return;
        }
        this.b(false);
    }

    public final void a(long l2) {
        this.c = this.b;
        this.b = l2;
        if (this.b == -1L) {
            this.e = "?Ken";
            return;
        }
        if (this.c != this.b) {
            long l3 = this.b - this.c;
            this.d = l3 / 2L + l3 % 2L;
        }
        this.e = String.valueOf(l.a(this.c, ".")) + "Ken";
        this.b(true);
        this.f = 0;
    }
}

