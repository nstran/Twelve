/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bu
extends ax {
    private String a;
    private int b;
    private bu[] c;
    private Object d;

    public bu(String string, int n2) {
        this.a = string;
        this.b = n2;
        this.h(ca.c.a(string));
    }

    public final Object a() {
        return this.d;
    }

    public final void a(Object object) {
        this.d = object;
    }

    public final String b() {
        return this.a;
    }

    public final int c() {
        return this.b;
    }

    public final void a(bu[] buArray) {
        this.c = buArray;
    }

    public final bu[] d() {
        return this.c;
    }

    public final boolean e() {
        return this.c != null && this.c.length > 0;
    }

    public final void a(Graphics graphics, int n2, int n3, boolean bl2) {
        if (bl2) {
            ca.c.a(graphics, this.a, n2, n3, 0);
        } else {
            ca.d.a(graphics, this.a, n2, n3, 0);
        }
        this.f(n2);
        this.g(n3);
    }

    public final void a(Graphics graphics, int n2, int n3) {
    }

    public final void i() {
    }

    public final String toString() {
        return "popupItem id= " + this.l + "     caption= " + this.a + "      command= " + this.b;
    }
}

