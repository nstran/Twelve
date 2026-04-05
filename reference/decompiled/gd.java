/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class gd
extends bd {
    private byte e = (byte)100;
    private String f = "";

    public gd() {
        super(Integer.MIN_VALUE);
        this.c = ca.d.a("77:77");
    }

    public final boolean a(int n2, int n3) {
        return false;
    }

    public final boolean b() {
        return false;
    }

    public final void c() {
        this.e = (byte)(this.e + 1);
        if (this.e >= 100) {
            this.e = 0;
            this.f = l.a();
        }
    }

    public final void a(Graphics graphics) {
        ca.d.a(graphics, this.f, this.a - 40, this.b + 1, 0);
        ca.d.a(graphics, String.valueOf(kq.i / 1024) + "Kb", this.a + 75, this.b + 1, 2);
    }
}

