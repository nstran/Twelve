/*
 * Decompiled with CFR 0.152.
 */
import java.io.UnsupportedEncodingException;

public final class jn {
    public String a;
    public String b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public byte[] h;
    public byte[] i;
    public byte[] j;
    public int k;
    public jm[] l;
    public int[] m;
    public int n;

    public final byte[] a() {
        byte[] byArray = null;
        try {
            byte[] byArray2 = i.c(this.a);
            int n2 = 32 + (byArray2.length + 4);
            byte[] byArray3 = i.c(this.b);
            n2 += byArray3.length + 4;
            n2 += this.h.length + 4 + this.i.length + 4 + this.j.length + 4;
            byte[] byArray4 = new byte[this.m.length << 2];
            int n3 = 0;
            int n4 = 0;
            while (n4 < this.m.length) {
                System.arraycopy(m.a(this.m[n4]), 0, byArray4, n3, 4);
                n3 += 4;
                ++n4;
            }
            n2 += byArray4.length;
            n2 += 4;
            byte[][] byArrayArray = new byte[this.l.length][];
            int n5 = 0;
            int n6 = 0;
            while (n6 < byArrayArray.length) {
                byArrayArray[n6] = this.l[n6].a();
                n2 += byArrayArray[n6].length + 4;
                n5 += byArrayArray[n6].length + 4;
                ++n6;
            }
            byArray = new byte[n2 += 4];
            System.arraycopy(m.a(this.f), 0, byArray, 0, 4);
            System.arraycopy(m.a(this.e), 0, byArray, 4, 4);
            System.arraycopy(m.a(this.g), 0, byArray, 8, 4);
            System.arraycopy(m.a(this.c), 0, byArray, 12, 4);
            System.arraycopy(m.a(this.d), 0, byArray, 16, 4);
            System.arraycopy(m.a(this.k), 0, byArray, 20, 4);
            System.arraycopy(m.a(byArray2.length), 0, byArray, 24, 4);
            System.arraycopy(byArray2, 0, byArray, 28, byArray2.length);
            n3 = 28 + byArray2.length;
            System.arraycopy(m.a(byArray3.length), 0, byArray, n3, 4);
            System.arraycopy(byArray3, 0, byArray, n3 += 4, byArray3.length);
            System.arraycopy(m.a(this.h.length), 0, byArray, n3 += byArray3.length, 4);
            System.arraycopy(this.h, 0, byArray, n3 += 4, this.h.length);
            System.arraycopy(m.a(this.i.length), 0, byArray, n3 += this.h.length, 4);
            System.arraycopy(this.i, 0, byArray, n3 += 4, this.i.length);
            System.arraycopy(m.a(this.j.length), 0, byArray, n3 += this.i.length, 4);
            System.arraycopy(this.j, 0, byArray, n3 += 4, this.j.length);
            System.arraycopy(m.a(byArray4.length), 0, byArray, n3 += this.j.length, 4);
            System.arraycopy(byArray4, 0, byArray, n3 += 4, byArray4.length);
            System.arraycopy(m.a(n5), 0, byArray, n3 += byArray4.length, 4);
            n3 += 4;
            n6 = 0;
            while (n6 < byArrayArray.length) {
                System.arraycopy(m.a(byArrayArray[n6].length), 0, byArray, n3, 4);
                System.arraycopy(byArrayArray[n6], 0, byArray, n3 += 4, byArrayArray[n6].length);
                n3 += byArrayArray[n6].length;
                ++n6;
            }
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            UnsupportedEncodingException unsupportedEncodingException2 = unsupportedEncodingException;
            unsupportedEncodingException.printStackTrace();
        }
        return byArray;
    }
}

