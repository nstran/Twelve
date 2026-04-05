/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class az {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;

    public az() {
    }

    public void a(Graphics graphics) {
        graphics.setColor(this.a);
        graphics.fillRect(this.b, this.c, this.d, this.e);
    }

    public az(int n2, int n3, int n4, int n5, int n6) {
        this();
        this.a = 0xFFFFFF;
        this.b = 0;
        this.c = 0;
        this.d = n5;
        this.e = n6;
    }
}

