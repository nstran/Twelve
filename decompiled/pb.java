/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class pb {
    private static pb b;
    public ep[] a = null;

    private pb() {
    }

    public static pb a() {
        if (b == null) {
            b = new pb();
        }
        return b;
    }

    public static void a(pb pb2) {
        b = null;
    }

    public final void a(ep[] epArray) {
        k.a(epArray, new pc(this));
        this.a = epArray;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        n2 = this.d(n2);
        try {
            if (this.a == null || n2 < 0) {
                oz.d(graphics, -20, n3, n4, 0);
                return;
            }
            graphics.drawImage(this.a[n2].a, n3, n4, 0);
            return;
        }
        catch (Exception exception) {
            cw.a("[VipIconUtil] Exception in drawVipIcon");
            exception.printStackTrace();
            oz.d(graphics, -20, n3, n4, 0);
            return;
        }
    }

    private int d(int n2) {
        if (this.a != null) {
            return k.a(this.a, 0, this.a.length - 1, new Integer(n2), new pd(this));
        }
        return -1;
    }

    public final int a(int n2) {
        if ((n2 = this.d(n2)) < 0 || this.a[n2] == null || n2 >= this.a.length) {
            return 16;
        }
        return this.a[n2].a.getWidth();
    }

    public final int b(int n2) {
        if ((n2 = this.d(n2)) < 0 || this.a[n2] == null || n2 >= this.a.length) {
            return 16;
        }
        return this.a[n2].a.getHeight();
    }

    public final String c(int n2) {
        if ((n2 = this.d(n2)) < 0 || this.a[n2] == null || n2 >= this.a.length || this.a[n2].b == null) {
            return "";
        }
        return this.a[n2].b;
    }
}

