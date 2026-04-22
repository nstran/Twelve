/*
 * Decompiled with CFR 0.152.
 */
public final class ku {
    public int a;
    public int b;
    public kt[] c;

    public final boolean a(short s2) {
        if (this.c == null) {
            return false;
        }
        int n2 = 0;
        while (n2 < this.c.length) {
            if (this.c[n2].a == s2) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final int b(short s2) {
        int n2 = 0;
        if (this.c == null) {
            return 0;
        }
        int n3 = 0;
        while (n3 < this.c.length) {
            if (this.c[n3].a == s2) {
                ++n2;
            }
            ++n3;
        }
        return n2;
    }

    public final int a(short s2, int n2, int n3) {
        int n4 = 0;
        if (this.c == null) {
            return 0;
        }
        if (n2 < 0) {
            n2 = -1;
        } else if (n2 >= this.c.length) {
            n2 = this.c.length - 1;
        }
        if (n3 < 0) {
            n3 = this.c.length;
        }
        if (n2 + 1 >= n3) {
            return 0;
        }
        ++n2;
        while (n2 < this.c.length && n2 < n3) {
            if (this.c[n2].a == s2) {
                ++n4;
            }
            ++n2;
        }
        return n4;
    }

    public final int a(short s2, int n2) {
        if (this.c == null) {
            return -1;
        }
        if (n2 < 0) {
            n2 = -1;
        } else if (n2 >= this.c.length) {
            n2 = this.c.length - 1;
        }
        ++n2;
        while (n2 < this.c.length) {
            if (this.c[n2].a == s2) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public final int b(short s2, int n2, int n3) {
        if (this.c == null) {
            return -1;
        }
        if (n2 < 0) {
            n2 = -1;
        } else if (n2 >= this.c.length) {
            n2 = this.c.length - 1;
        }
        if (n3 < 0) {
            n3 = this.c.length;
        }
        if (n2 + 1 >= n3) {
            return -1;
        }
        ++n2;
        while (n2 < this.c.length && n2 < n3) {
            if (this.c[n2].a == s2) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public final int b(short s2, int n2) {
        if (this.c == null) {
            return -1;
        }
        int n3 = 0;
        while (n3 < this.c.length) {
            if (this.c[n3].a == s2) {
                if (n2 == 0) {
                    return n3;
                }
                --n2;
            }
            ++n3;
        }
        return -1;
    }

    public final byte[] c(short s2) {
        if (this.c == null) {
            return null;
        }
        int n2 = 0;
        while (n2 < this.c.length) {
            if (this.c[n2].a == s2) {
                return this.c[n2].b;
            }
            ++n2;
        }
        return null;
    }

    public final String d(short s2) {
        return ku.a(this.c(s2));
    }

    public final long a(short s2, long l2) {
        return ku.a(this.c(s2), l2);
    }

    public final int c(short s2, int n2) {
        return ku.a(this.c(s2), n2);
    }

    public final byte a(short s2, byte by2) {
        return ku.a(this.c(s2), by2);
    }

    public final byte[] c(short s2, int n2, int n3) {
        int n4 = this.b(s2, n2, n3);
        s2 = (short)n4;
        if (n4 >= 0) {
            return this.a((int)s2);
        }
        return null;
    }

    public final String d(short s2, int n2, int n3) {
        int n4 = this.b(s2, n2, n3);
        s2 = (short)n4;
        if (n4 >= 0) {
            return this.b((int)s2);
        }
        return null;
    }

    public final long a(short s2, int n2, int n3, long l2) {
        int n4 = this.b(s2, n2, n3);
        s2 = (short)n4;
        if (n4 >= 0) {
            long l3 = 0L;
            n2 = s2;
            ku ku2 = this;
            return ku.a(ku2.a(n2), l3);
        }
        return 0L;
    }

    public final int a(short s2, int n2, int n3, int n4) {
        int n5 = this.b(s2, n2, n3);
        s2 = (short)n5;
        if (n5 >= 0) {
            return this.a((int)s2, n4);
        }
        return n4;
    }

    public final byte a(short s2, int n2, int n3, byte by2) {
        int n4 = this.b(s2, n2, n3);
        s2 = (short)n4;
        if (n4 >= 0) {
            return this.a((int)s2, by2);
        }
        return by2;
    }

    public final byte[] a(int n2) {
        if (this.c == null || n2 < 0 || n2 >= this.c.length) {
            return null;
        }
        return this.c[n2].b;
    }

    public final String b(int n2) {
        return ku.a(this.a(n2));
    }

    public final int a(int n2, int n3) {
        return ku.a(this.a(n2), n3);
    }

    public final byte a(int n2, byte by2) {
        return ku.a(this.a(n2), by2);
    }

    private static String a(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        try {
            return new String(byArray, 0, byArray.length, "UTF-8");
        }
        catch (Throwable throwable) {
            return new String(byArray);
        }
    }

    private static long a(byte[] byArray, long l2) {
        if (byArray == null) {
            return l2;
        }
        return m.d(byArray);
    }

    private static int a(byte[] byArray, int n2) {
        if (byArray == null) {
            return n2;
        }
        return m.c(byArray);
    }

    private static byte a(byte[] byArray, byte by2) {
        if (byArray == null) {
            return by2;
        }
        return byArray[0];
    }

    public final String toString() {
        String string = "";
        string = String.valueOf(string) + " [Service]: " + this.b + " [Length]: " + this.a + " [Number Of Keys]: " + (this.c != null ? String.valueOf(this.c.length) : "Null");
        if (this.c != null) {
            string = String.valueOf(string) + "\n";
            int n2 = 0;
            while (n2 < this.c.length) {
                string = String.valueOf(string) + "  - [Key]: " + this.c[n2].a + " [Value length]: " + this.c[n2].b.length + "\n";
                ++n2;
            }
        }
        return string;
    }
}

