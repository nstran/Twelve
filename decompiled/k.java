/*
 * Decompiled with CFR 0.152.
 */
public class k {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public String o;
    public String p;
    public String q;
    public String[] r;
    public String[] s;
    public long t;
    public long u;
    public long v;
    public long w;
    public int x;
    public int y;
    public int z;
    public int A;
    public int B;
    public short C;
    public short D;
    public short E;
    public short F;
    public short G;
    public short H;
    public short I;
    public short J;
    public short K;
    public short L;
    public byte[] M;
    public byte[] N;
    public byte[] O;
    public byte P;
    public boolean Q;

    public k() {
    }

    public static void a(Object[] objectArray, int n2, int n3) {
        Object object = objectArray[n2];
        objectArray[n2] = objectArray[n3];
        objectArray[n3] = object;
    }

    public static Object[] a(Object[] objectArray, b b2) {
        b b3 = b2;
        int n2 = objectArray.length;
        boolean bl2 = false;
        Object[] objectArray2 = objectArray;
        if (objectArray != null) {
            boolean bl3 = false;
            n2 += 0;
            int n3 = 1;
            do {
                bl3 = false;
                int n4 = n3;
                while (n4 < n2) {
                    if (b3.a(objectArray2[n4 - 1], objectArray2[n4]) > 0) {
                        k.a(objectArray2, n4 - 1, n4);
                        bl3 = true;
                    }
                    ++n4;
                }
                --n2;
                if (!bl3) break;
                n4 = n2 - 1;
                while (n4 >= n3) {
                    if (b3.a(objectArray2[n4 - 1], objectArray2[n4]) > 0) {
                        k.a(objectArray2, n4 - 1, n4);
                        bl3 = true;
                    }
                    --n4;
                }
                ++n3;
            } while (bl3);
        }
        return objectArray2;
    }

    public static int a(Object[] objectArray, int n2, int n3, Object object, b b2) {
        n2 = 0;
        while (n2 <= n3) {
            int n4 = n2 + n3 >>> 1;
            int n5 = b2.a(objectArray[n4], object);
            if (n5 < 0) {
                n2 = n4 + 1;
                continue;
            }
            if (n5 > 0) {
                n3 = n4 - 1;
                continue;
            }
            return n4;
        }
        return -(n2 + 1);
    }

    public k(short s2) {
        this.I = 0;
        this.J = 0;
        this.K = 0;
        this.Q = false;
        this.C = s2;
    }
}

