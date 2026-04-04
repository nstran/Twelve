/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public final class cc
extends d {
    private Font a = Font.getDefaultFont();

    public final int a() {
        return this.a.getHeight();
    }

    public final int a(String string) {
        return this.a.stringWidth(string);
    }

    public final void a(Graphics graphics, String string, int n2, int n3, int n4) {
        graphics.setColor(this.j);
        if (n4 == 0) {
            graphics.drawString(string, n2, n3, 0);
            return;
        }
        if (n4 == 2) {
            graphics.drawString(string, n2, n3, 24);
            return;
        }
        if (n4 == 1) {
            graphics.drawString(string, n2, n3, 17);
        }
    }

    public final void a(Graphics graphics, String string, int n2, int n3, int n4, int n5, int n6) {
        if (n6 == 0) {
            graphics.drawSubstring(string, 0, n3, n4, n5, 0);
            return;
        }
        if (n6 == 2) {
            graphics.drawSubstring(string, 0, n3, n4, n5, 24);
            return;
        }
        if (n6 == 1) {
            graphics.drawSubstring(string, 0, n3, n4, n5, 17);
        }
    }

    public final int a(char c2) {
        return this.a.charWidth(c2);
    }

    public final int b() {
        return this.a.getBaselinePosition();
    }

    public final void c(boolean bl2) {
        this.a = Font.getFont((int)0, (int)1, (int)0);
    }

    public final void a(boolean bl2) {
    }

    public final void c() {
    }

    public final void b(boolean bl2) {
    }
}

