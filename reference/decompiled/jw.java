/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class jw
extends aw {
    private static byte[][] s = new byte[][]{new byte[1]};
    private String t;
    private nn u;
    private long v = 0L;
    private byte w = 0;

    public jw(Image image, int n2) {
        super(image, 1);
        this.a(s);
        this.g = 0;
    }

    public final void k() {
        if (!this.r) {
            return;
        }
        this.w = (byte)(this.w + 1);
        if (this.w > 5) {
            this.w = 0;
            super.k();
            this.b(this.v - this.u.f());
        }
    }

    public final void a(long l2) {
        if (this.u == null) {
            this.u = new nn();
        }
        this.u.a();
        this.v = l2;
        this.b(l2);
    }

    private void b(long l2) {
        String string;
        StringBuffer stringBuffer;
        long l3 = l2 / 1000L;
        long l4 = (int)(l3 / 60L);
        l3 %= 60L;
        int n2 = (int)(l4 / 60L);
        l4 %= 60L;
        int n3 = n2 / 24;
        n2 %= 24;
        if (n3 > 0) {
            stringBuffer = new StringBuffer(String.valueOf(n3)).append("Ng\u00e0y");
            string = " ";
        } else {
            stringBuffer = new StringBuffer(String.valueOf(n2 >= 10 ? String.valueOf(n2) + ":" : (n2 > 0 ? "0" + n2 + ":" : "00:"))).append(l4 >= 10L ? String.valueOf(l4) + ":" : (l4 > 0L ? "0" + l4 + ":" : "00:"));
            string = l3 >= 10L ? String.valueOf(l3) : (l3 > 0L ? "0" + l3 : "00");
        }
        this.t = stringBuffer.append(string).toString();
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.r) {
            return;
        }
        super.a(graphics, n2, n3);
        if (this.t != null) {
            ca.c.a(graphics, this.t, n2 += this.o, n3 += (this.p - ca.c.a()) / 2, 0);
        }
    }
}

