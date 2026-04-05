/*
 * Decompiled with CFR 0.152.
 */
public final class nh
implements mp {
    public static nh a;
    public static nh b;
    public static nh c;
    public static nh d;
    public static nh e;
    public static nh f;
    public static nh g;
    public static nh h;
    public static nh i;
    public static nh j;
    public static nh k;
    public static nh l;
    public static nh m;
    public static nh n;
    public static nh o;
    public static nh p;
    public static nh q;
    public static nh r;
    public static nh s;
    public static nh t;
    public static nh u;
    public static nh x;
    public byte y;
    public byte z;
    public int A;
    public byte B;
    public byte C;

    public nh(byte by2, byte by3, int n2, byte by4, byte by5) {
        this.y = by2;
        this.z = by3;
        this.A = n2;
        this.B = by4;
        this.C = by5;
    }

    public static nh a(int n2) {
        switch (n2) {
            case 0: {
                return a;
            }
            case 1: {
                return b;
            }
            case 2: {
                return c;
            }
            case 3: {
                return d;
            }
            case 4: {
                return e;
            }
            case 5: {
                return f;
            }
            case 10: {
                return g;
            }
            case 11: {
                return h;
            }
            case 12: {
                return i;
            }
            case 13: {
                return j;
            }
            case 14: {
                return k;
            }
            case 15: {
                return l;
            }
            case 20: {
                return m;
            }
            case 21: {
                return n;
            }
            case 22: {
                return o;
            }
            case 23: {
                return p;
            }
            case 24: {
                return q;
            }
            case 25: {
                return r;
            }
            case 70: {
                return s;
            }
            case 71: {
                break;
            }
            case 80: {
                return t;
            }
            case 90: {
                return u;
            }
            case 99: {
                return x;
            }
        }
        cw.a("[NodeChess]==========khoong co id nay " + n2);
        return null;
    }

    public final String toString() {
        return "Nodechess id = " + this.z + "  indexIma = " + this.C + "  mask = " + this.A + " typoe  " + this.B;
    }
}

