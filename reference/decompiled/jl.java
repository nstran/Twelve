/*
 * Decompiled with CFR 0.152.
 */
import java.io.UnsupportedEncodingException;

public final class jl {
    public int a;
    public String b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public boolean h;
    public int i;

    public final byte[] a() {
        int n2 = 0;
        byte[] byArray = null;
        byte[] byArray2 = null;
        try {
            byArray2 = l.c(this.b);
            n2 = 12 + byArray2.length + 16;
            byArray = new byte[n2];
            System.arraycopy(p.a(this.a), 0, byArray, 0, 4);
            System.arraycopy(p.a(this.c), 0, byArray, 4, 4);
            System.arraycopy(p.a(this.d), 0, byArray, 8, 4);
            System.arraycopy(p.a(this.e), 0, byArray, 12, 4);
            System.arraycopy(p.a(this.f), 0, byArray, 16, 4);
            System.arraycopy(p.a(this.g), 0, byArray, 20, 4);
            System.arraycopy(p.a(byArray2.length), 0, byArray, 24, 4);
            System.arraycopy(byArray2, 0, byArray, 28, byArray2.length);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            UnsupportedEncodingException unsupportedEncodingException2 = unsupportedEncodingException;
            unsupportedEncodingException.printStackTrace();
        }
        return byArray;
    }

    public final String toString() {
        String string = " x = " + this.d + " y= " + this.e + "       w = " + this.f + " h = " + this.g + "        " + this.b + "   type = " + this.a;
        return string;
    }
}

