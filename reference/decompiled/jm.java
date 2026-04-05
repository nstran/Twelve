/*
 * Decompiled with CFR 0.152.
 */
import java.io.UnsupportedEncodingException;

public final class jm {
    public String a;
    public String b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public int i;
    public byte[] j;
    public byte[] k;
    public byte[] l;
    public int m;
    public jl[] n;
    public int[] o;
    public int p;

    public final byte[] a() {
        byte[] byArray = null;
        int n2 = 0;
        byte[] byArray2 = null;
        byte[] byArray3 = null;
        try {
            byArray2 = l.c(this.a);
            n2 = 32 + (byArray2.length + 4);
            byArray3 = l.c(this.b);
            n2 += byArray3.length + 4;
            n2 += this.j.length + 4 + this.k.length + 4 + this.l.length + 4;
            byte[] byArray4 = new byte[this.o.length << 2];
            int n3 = 0;
            int n4 = 0;
            while (n4 < this.o.length) {
                System.arraycopy(p.a(this.o[n4]), 0, byArray4, n3, 4);
                n3 += 4;
                ++n4;
            }
            n2 += byArray4.length;
            n2 += 4;
            byte[][] byArrayArray = new byte[this.n.length][];
            int n5 = 0;
            int n6 = 0;
            while (n6 < byArrayArray.length) {
                byArrayArray[n6] = this.n[n6].a();
                n2 += byArrayArray[n6].length + 4;
                n5 += byArrayArray[n6].length + 4;
                ++n6;
            }
            byArray = new byte[n2 += 4];
            System.arraycopy(p.a(this.h), 0, byArray, 0, 4);
            System.arraycopy(p.a(this.e), 0, byArray, 4, 4);
            System.arraycopy(p.a(this.i), 0, byArray, 8, 4);
            System.arraycopy(p.a(this.c), 0, byArray, 12, 4);
            System.arraycopy(p.a(this.d), 0, byArray, 16, 4);
            System.arraycopy(p.a(this.g), 0, byArray, 20, 4);
            System.arraycopy(p.a(this.m), 0, byArray, 24, 4);
            System.arraycopy(p.a(this.f), 0, byArray, 28, 4);
            System.arraycopy(p.a(byArray2.length), 0, byArray, 32, 4);
            System.arraycopy(byArray2, 0, byArray, 36, byArray2.length);
            n3 = 36 + byArray2.length;
            System.arraycopy(p.a(byArray3.length), 0, byArray, n3, 4);
            System.arraycopy(byArray3, 0, byArray, n3 += 4, byArray3.length);
            System.arraycopy(p.a(this.j.length), 0, byArray, n3 += byArray3.length, 4);
            System.arraycopy(this.j, 0, byArray, n3 += 4, this.j.length);
            System.arraycopy(p.a(this.k.length), 0, byArray, n3 += this.j.length, 4);
            System.arraycopy(this.k, 0, byArray, n3 += 4, this.k.length);
            System.arraycopy(p.a(this.l.length), 0, byArray, n3 += this.k.length, 4);
            System.arraycopy(this.l, 0, byArray, n3 += 4, this.l.length);
            System.arraycopy(p.a(byArray4.length), 0, byArray, n3 += this.l.length, 4);
            System.arraycopy(byArray4, 0, byArray, n3 += 4, byArray4.length);
            System.arraycopy(p.a(n5), 0, byArray, n3 += byArray4.length, 4);
            n3 += 4;
            n6 = 0;
            while (n6 < byArrayArray.length) {
                System.arraycopy(p.a(byArrayArray[n6].length), 0, byArray, n3, 4);
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

