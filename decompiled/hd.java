/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class hd
extends ax {
    private String a = "";
    private int b = 0;
    private byte c;
    private d d = ca.d;

    public hd(String string, byte by2, d d2) {
        this.c = by2;
        if (string != null) {
            this.a = string;
        }
        if (d2 != null) {
            this.d = d2;
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        switch (this.c) {
            case 0: {
                graphics.setColor(16686236);
                break;
            }
            case 1: {
                graphics.setColor(16775619);
                break;
            }
            default: {
                graphics.setColor(14808319);
            }
        }
        graphics.fillRect(n2 + this.b, n3, this.o, this.p);
        this.d.a(graphics, this.a, n2, n3, 0);
    }

    public final void i() {
    }

    public final void a(int n2) {
        this.b = -5;
    }
}

