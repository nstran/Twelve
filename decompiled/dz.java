/*
 * Decompiled with CFR 0.152.
 */
public final class dz {
    public int a;
    public int b;
    public dy[] c;

    public final byte[] a(short s2) {
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

    public final String b(short s2) {
        return l.a(this.a(s2));
    }

    public final int a(short s2, int n2) {
        if (this.c == null) {
            return -1;
        }
        int n3 = n2;
        if (n2 < 0) {
            n3 = -1;
        } else if (n2 >= this.c.length) {
            n3 = this.c.length - 1;
        }
        n2 = n3 + 1;
        while (n2 < this.c.length) {
            if (this.c[n2].a == s2) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public final int a(short s2, int n2, int n3) {
        if (this.c == null) {
            return -1;
        }
        int n4 = n2;
        int n5 = n3;
        if (n2 < 0) {
            n4 = -1;
        } else if (n2 >= this.c.length) {
            n4 = this.c.length - 1;
        }
        if (n3 < 0) {
            n5 = this.c.length;
        }
        if (n4 + 1 >= n5) {
            return -1;
        }
        n2 = n4 + 1;
        while (n2 < this.c.length && n2 < n5) {
            if (this.c[n2].a == s2) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public final String b(short s2, int n2, int n3) {
        int n4 = this.a(s2, n2, n3);
        s2 = (short)n4;
        if (n4 >= 0) {
            return this.b((int)s2);
        }
        return null;
    }

    public final long a(short s2, int n2, int n3, long l2) {
        int n4 = this.a(s2, n2, n3);
        s2 = (short)n4;
        if (n4 >= 0) {
            return this.a((int)s2, 0L);
        }
        return 0L;
    }

    public final int a(short s2, int n2, int n3, int n4) {
        int n5 = this.a(s2, n2, n3);
        s2 = (short)n5;
        if (n5 >= 0) {
            return this.a((int)s2, 0);
        }
        return 0;
    }

    public final byte a(short s2, int n2, int n3, byte by2) {
        int n4 = this.a(s2, n2, n3);
        s2 = (short)n4;
        if (n4 >= 0) {
            return this.a((int)s2, by2);
        }
        return by2;
    }

    public final byte a(short s2, int n2, int n3, short s3) {
        return this.a(s2, n2, n3, (byte)s3);
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

    public final int c(short s2) {
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

    public final byte[] a(int n2) {
        if (this.c == null || n2 < 0 || n2 >= this.c.length) {
            return null;
        }
        return this.c[n2].b;
    }

    public final String b(int n2) {
        return l.a(this.a(n2));
    }

    public final long a(int n2, long l2) {
        long l3 = l2;
        byte[] byArray = this.a(n2);
        if (byArray == null) {
            return l3;
        }
        return p.d(byArray);
    }

    public final int a(int n2, int n3) {
        byte[] byArray = this.a(n2);
        if (byArray == null) {
            return n3;
        }
        if (byArray.length == 1) {
            return p.a(byArray[0]);
        }
        if (byArray.length == 2) {
            return p.a(byArray);
        }
        if (byArray.length == 3) {
            return p.b(byArray);
        }
        return p.c(byArray);
    }

    public final byte a(int n2, byte by2) {
        byte[] byArray = this.a(n2);
        if (byArray == null) {
            return by2;
        }
        return byArray[0];
    }

    public final byte a(int n2, short s2) {
        return this.a(n2, (byte)s2);
    }

    public final String toString() {
        String string = "";
        string = String.valueOf(string) + "[Service]: " + this.b + " [Length]: " + this.a + " [Number Of Keys]: " + (this.c != null ? String.valueOf(this.c.length) : "Null");
        if (this.c != null) {
            string = String.valueOf(string) + "\n";
            int n2 = 0;
            while (n2 < this.c.length) {
                string = String.valueOf(string) + " [Key]: " + this.c[n2].a + " [Value length]: " + this.c[n2].b.length;
                ++n2;
            }
        }
        return string;
    }
}

