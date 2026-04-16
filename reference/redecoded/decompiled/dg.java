/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;

public final class dg {
    public int a;
    public String b;
    public int[] c;

    private dg(int n2, int[] nArray) {
        this.a = n2;
        this.c = nArray;
    }

    public dg(int n2, byte[] byArray) {
        this(n2, dg.a(byArray));
    }

    private static int[] a(byte[] object) {
        try {
            int[] nArray = new int[((byte[])object).length / 4];
            object = new DataInputStream(new ByteArrayInputStream((byte[])object));
            int n2 = 0;
            while (n2 < nArray.length) {
                nArray[n2] = ((DataInputStream)object).readInt();
                ++n2;
            }
            ((FilterInputStream)object).close();
            return nArray;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return null;
        }
    }

    public final dg a() {
        Object object = new int[this.c.length];
        System.arraycopy(this.c, 0, object, 0, this.c.length);
        object = new dg(this.a, (int[])object);
        v0.b = this.b;
        return object;
    }

    public final String toString() {
        return this.b;
    }

    public final boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof dg) {
            dg dg2 = (dg)object;
            object = dg2;
            int[] nArray = this.c;
            object = dg2.c;
            if (dg2.c == null || nArray == null) {
                return object == nArray;
            }
            if (((Object)object).length != nArray.length) {
                return false;
            }
            int n2 = 0;
            while (n2 < ((Object)object).length) {
                if (object[n2] != nArray[n2]) {
                    return false;
                }
                ++n2;
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int[] nArray = this.c;
        if (this.c == null) {
            return 0;
        }
        int n2 = 1;
        int n3 = 0;
        while (n3 < nArray.length) {
            n2 = n2 * 31 + nArray[n3];
            ++n3;
        }
        return n2;
    }
}

