/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public class r {
    private String b;
    protected a a;
    private int c;
    private int d;
    private int e;
    private int f;
    private d g = null;
    private boolean h = false;

    public r() {
    }

    public final void a(boolean bl2) {
        this.h = true;
    }

    public r(d object, String object2, int n2, int n3, int n4, int n5, int n6) {
        int n7 = n6;
        n6 = n5;
        n5 = n4;
        n4 = n3;
        n3 = n2;
        Object object3 = object2;
        object2 = object;
        object = this;
        object3 = ((String)object3).replace('\r', '\n');
        Object object4 = object;
        Object object5 = object2;
        object = object3;
        object2 = s.a((String)object);
        s.a((a)object2);
        int n8 = n7;
        n7 = n6;
        n6 = n5;
        n5 = n4;
        n4 = n3;
        String string = object3;
        object3 = object2;
        object2 = object5;
        object = object4;
        ((r)object4).g = object2;
        ((r)object).b = string;
        ((r)object).a = new a(10);
        ((r)object).e = n4;
        ((r)object).f = n5;
        ((r)object).c = n6;
        if (n6 <= 0 || ((a)object3).d() <= 0) {
            return;
        }
        super.a((a)object3, ((r)object).b, n6, n7, n8);
        if (((r)object).a.d() > 0) {
            object2 = (q)((r)object).a.b(((r)object).a.d() - 1);
            ((r)object).d = ((q)object2).e() + n7;
            object3 = (q)((r)object).a.b(0);
            if (((q)object3).e() == ((q)object2).e()) {
                ((r)object).c = ((q)object2).d() + ((q)object2).f();
            }
        }
    }

    private void a(a a2, String string, int n2, int n3, int n4) {
        n4 = n3 + n4;
        int n5 = 0;
        long l2 = 0L;
        int n6 = 0;
        int n7 = a2.d();
        while (n6 < n7) {
            Object object = (String)a2.b(n6);
            if (this.a.d() > 0) {
                q q2 = (q)this.a.b(this.a.d() - 1);
                long l3 = q2.d() + q2.f();
                l2 = l3 << 32 | (long)q2.e();
            }
            int n8 = 10;
            if (((String)object).length() > 0) {
                n8 = ((String)object).charAt(0);
            }
            int n9 = 0;
            String string2 = null;
            if (n8 == 26) {
                n8 = Integer.parseInt(String.valueOf(((String)object).charAt(1)));
                switch (n8) {
                    case 3: 
                    case 4: 
                    case 5: {
                        n9 = ((String)object).length() - 2;
                        string2 = string.substring(n5, n5 + n9);
                        this.b(string2, n8, n6, l2, n2, n4);
                        break;
                    }
                    case 1: {
                        n9 = ((String)object).length() - 2;
                        string2 = string.substring(n5, n5 + n9);
                        this.a(string2, 1, n6, l2, n4, n2);
                        break;
                    }
                    case 2: {
                        n8 = Integer.parseInt(String.valueOf(((String)object).charAt(2)) + ((String)object).charAt(3));
                        n9 = ((String)object).length() - 4;
                        string2 = string.substring(n5, n5 + n9);
                        int n10 = n2;
                        int n11 = n4;
                        long l4 = l2;
                        int n12 = n6;
                        int n13 = 2;
                        String string3 = string2;
                        object = this;
                        int n14 = (int)(l4 >>> 32);
                        int n15 = (int)l4;
                        if (n14 + t.l[n8] > n10) {
                            n15 += n11;
                            n14 = 0;
                        }
                        ((r)object).a.a(new t(n8, string3, 2, n12, n14, n15, t.l[n8]));
                    }
                }
            } else {
                n9 = ((String)object).length();
                string2 = string.substring(n5, n5 + n9);
                this.b(string2, 0, n6, l2, n2, n4);
            }
            n5 += n9;
            ++n6;
        }
        n6 = this.a.d() - 1;
        while (n6 >= 0) {
            q q3 = (q)this.a.b(n6);
            q3.a(n3);
            --n6;
        }
    }

    public final d a() {
        return this.g;
    }

    public final int b() {
        return this.c;
    }

    public final int c() {
        return this.d;
    }

    public final a d() {
        return this.a;
    }

    public final q a(int n2) {
        return (q)this.a.b(n2);
    }

    public final int e() {
        return this.e;
    }

    public final int f() {
        return this.f;
    }

    public final void a(int n2, int n3) {
        this.e = 4;
        this.f = n3;
    }

    public final String g() {
        return s.a(this.a, 1, this.h);
    }

    public final String h() {
        return s.a(this.a, 3, this.h);
    }

    public final void a(int n2, boolean bl2) {
        block9: {
            if (n2 < 0 || n2 >= this.a.d()) {
                return;
            }
            q q2 = (q)this.a.b(n2);
            int n3 = q2.b();
            if (!this.h) {
                while (n2 >= 0) {
                    q2 = (q)this.a.b(n2);
                    if (q2.b() != n3) {
                        ++n2;
                        break;
                    }
                    --n2;
                }
                if (n2 < 0) {
                    n2 = 0;
                }
                while (n2 < this.a.d()) {
                    q2 = (q)this.a.b(n2);
                    if (q2.b() == n3) {
                        q2.a(bl2);
                        ++n2;
                        continue;
                    }
                    break block9;
                }
                return;
            }
            n2 = 0;
            while (n2 < this.a.d()) {
                q2 = (q)this.a.b(n2);
                if (q2.b() == n3) {
                    q2.a(bl2);
                }
                ++n2;
            }
        }
    }

    public final String b(int n2, int n3) {
        return s.a(this.a, n2, n3, this.h);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        n2 += this.e;
        n3 += this.f;
        int n4 = 0;
        while (n4 < this.a.d()) {
            q q2 = (q)this.a.b(n4);
            q2.a(graphics, n2, n3);
            ++n4;
        }
    }

    public final String i() {
        return this.b;
    }

    public final int b(int n2) {
        return s.b(this.a, n2);
    }

    public final int c(int n2) {
        return s.c(this.a, n2);
    }

    public final int d(int n2) {
        return s.d(this.a, n2);
    }

    public final int e(int n2) {
        return s.e(this.a, n2);
    }

    public final int j() {
        a a2 = this.a;
        int n2 = 0;
        while (n2 < a2.d()) {
            q q2 = (q)a2.b(n2);
            if (s.a(q2.c())) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public final int k() {
        a a2 = this.a;
        int n2 = a2.d() - 1;
        while (n2 >= 0) {
            q q2 = (q)a2.b(n2);
            if (s.a(q2.c())) {
                return n2;
            }
            --n2;
        }
        return -1;
    }

    private void b(String string, int n2, int n3, long l2, int n4, int n5) {
        int n6 = (int)(l2 >>> 32);
        int n7 = (int)l2;
        int n8 = 0;
        int n9 = 0;
        int n10 = n6;
        while (n8 < string.length()) {
            String string2;
            char c2 = string.charAt(n9);
            int n11 = this.g.a(c2);
            ++n9;
            if (c2 == '\n') {
                string2 = string.substring(0, n9 - 1);
                if (string2.length() > 0) {
                    this.a.a(this.a(string2, n2, n3, n6, n7, n10 - n6));
                }
                n7 += n5;
                if (n9 >= string.length()) {
                    this.a.a(this.a("", n2, n3, 0, n7, 0));
                    return;
                }
                string = string.substring(n9);
                n10 = 0;
                n6 = 0;
                n9 = 0;
                n8 = 0;
                continue;
            }
            if (c2 == ' ') {
                n8 = n9;
            }
            if ((n10 += n11) > n4) {
                int n12 = string.indexOf(32, n9);
                String string3 = n12 >= 0 ? string.substring(n8, n12) : string.substring(n8);
                int n13 = this.g.a(string3);
                if (n13 <= n4) {
                    String string4 = string.substring(0, n8);
                    if (string4.length() > 0) {
                        this.a.a(this.a(string4, n2, n3, n6, n7, this.g.a(string4)));
                    }
                    n7 += n5;
                    n6 = 0;
                    string = string.substring(n8);
                    if (n12 < 0) {
                        if (string.length() <= 0) break;
                        this.a.a(this.a(string, n2, n3, 0, n7, this.g.a(string3)));
                        return;
                    }
                    n9 = n8 = n12 - n8;
                    n10 = n13;
                    continue;
                }
                if (c2 != ' ') {
                    --n9;
                }
                n10 -= n11;
                String string5 = string.substring(0, n9);
                if (string5.length() > 0) {
                    this.a.a(this.a(string5, n2, n3, n6, n7, n10 - n6));
                }
                n7 += n5;
                n10 = 0;
                n6 = 0;
                string = string.substring(n9);
                n9 = 0;
                n8 = 0;
                continue;
            }
            if (n9 < string.length()) continue;
            string2 = string;
            if (string2.length() <= 0) break;
            this.a.a(this.a(string2, n2, n3, n6, n7, this.g.a(string2)));
            return;
        }
    }

    protected q a(String string, int n2, int n3, int n4, int n5, int n6) {
        return new u(string, n2, n3, n4, n5, n6, this.g);
    }

    protected void a(String string, int n2, int n3, long l2, int n4, int n5) {
    }
}

