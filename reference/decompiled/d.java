/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public abstract class d {
    protected int j;

    public abstract int a();

    public abstract int a(String var1);

    public void a(Graphics graphics, String string, int n2, int n3, int n4) {
        this.a(graphics, string, 0, string.length(), n2, n3, n4);
    }

    public abstract void a(Graphics var1, String var2, int var3, int var4, int var5, int var6, int var7);

    public abstract int a(char var1);

    public abstract int b();

    public abstract void a(boolean var1);

    public abstract void b(boolean var1);

    public abstract void c(boolean var1);

    public abstract void c();

    public final void a(int n2) {
        this.j = n2;
    }
}

