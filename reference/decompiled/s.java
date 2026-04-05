/*
 * Decompiled with CFR 0.152.
 */
public class s {
    private final Object[] a;
    private int b;
    private int c;
    private int d;

    public s() {
    }

    /*
     * Unable to fully structure code
     */
    public static String a(a var0, int var1_1, int var2_2, boolean var3_3) {
        if (var3_3 == 0) ** GOTO lbl17
        var2_2 = var3_3;
        var3_3 = 0;
        var4_4 = var0.d();
        while (var3_3 < var4_4) {
            var5_6 = (q)var0.b(var3_3);
            if (var5_6.b() == var1_1) {
                return s.b(var0, var1_1, var3_3, (boolean)var2_2);
            }
            ++var3_3;
        }
        return null;
lbl-1000:
        // 1 sources

        {
            var4_5 = (q)var0.b(var2_2);
            if (var4_5.b() != var1_1) {
                ++var2_2;
                break;
            }
            --var2_2;
lbl17:
            // 2 sources

            ** while (var2_2 >= 0)
        }
lbl18:
        // 2 sources

        if (var2_2 < 0) {
            var2_2 = 0;
        }
        return s.b(var0, var1_1, var2_2, (boolean)var3_3);
    }

    private static String b(a a2, int n2, int n3, boolean bl2) {
        String string = "";
        int n4 = a2.d();
        while (n3 < n4) {
            q q2 = (q)a2.b(n3);
            if (q2.b() == n2) {
                string = String.valueOf(string) + q2.a();
            } else if (!bl2) break;
            ++n3;
        }
        return string;
    }

    public static int[] a(a a2, int n2) {
        Object object;
        a a3 = new a();
        int n3 = Integer.MIN_VALUE;
        int n4 = 0;
        while (n4 < a2.d()) {
            object = (q)a2.b(n4);
            if (((q)object).c() == 4 && ((q)object).b() != n3) {
                n3 = ((q)object).b();
                String string = s.a(a2, ((q)object).b(), n4, true);
                if (!l.a(string)) {
                    boolean bl2 = true;
                    a a4 = a3;
                    int n5 = 0;
                    while (n5 < a4.d()) {
                        int n6 = (Integer)a4.b(n5);
                        q q2 = (q)a2.b(n6);
                        String string2 = s.a(a2, q2.b(), n6, true);
                        if (string2.equals(string)) {
                            bl2 = false;
                            break;
                        }
                        ++n5;
                    }
                    if (bl2) {
                        a4.a(new Integer(n4));
                    }
                }
            }
            ++n4;
        }
        int[] nArray = new int[a3.d()];
        object = a3;
        int n7 = 0;
        while (n7 < ((a)object).d()) {
            nArray[n7] = (Integer)((a)object).b(n7);
            ++n7;
        }
        return nArray;
    }

    public static a a(String string) {
        a a2 = new a(15);
        if (!l.b(string)) {
            int n2;
            string = string.toLowerCase();
            while ((n2 = string.indexOf(10)) >= 0) {
                a2.a(string.substring(0, n2));
                a2.a("\n");
                if (!l.b(string = string.substring(n2 + 1))) continue;
            }
            if (!l.b(string)) {
                a2.a(string);
            }
        }
        return a2;
    }

    public static void a(a object) {
        if (((a)object).d() <= 0) {
            return;
        }
        try {
            Object object2 = object;
            s.a((a)object2, "rss://", " ", false, 3);
            object2 = object;
            s.a((a)object2, "http://", " ", false, 3);
            object2 = object;
            s.a((a)object2, "www.", " ", false, 3);
            object2 = object;
            s.a((a)object2, "#\u001b", "#", true, 1);
            object2 = object;
            s.a((a)object2, "@", " ", false, 4);
            object2 = object;
            s.a((a)object2, "#", " ", false, 5);
            object2 = object;
            int n2 = 0;
            while (n2 < ((a)object2).d()) {
                String string = (String)((a)object2).b(n2);
                if (string.length() > 0 && string.charAt(0) != '\u001a') {
                    int n3 = 0;
                    int n4 = string.length() - 1;
                    while (n3 < n4) {
                        int n5 = string.length() - n3;
                        char c2 = string.charAt(n3);
                        char c3 = string.charAt(n3 + 1);
                        int n6 = 3;
                        int n7 = -1;
                        switch (c2) {
                            case '<': {
                                if (c3 != '3') break;
                                n6 = 2;
                                n7 = 28;
                                break;
                            }
                            case '@': {
                                if (c3 != ';' || n5 <= 2 || string.charAt(n3 + 2) != '-') break;
                                n7 = 31;
                                break;
                            }
                            case ':': {
                                if (c3 == '-') {
                                    if (n5 <= 2) break;
                                    c2 = string.charAt(n3 + 2);
                                    if (c2 == ')') {
                                        if (n5 > 3) {
                                            char c4 = string.charAt(n3 + 3);
                                            n5 = c4;
                                            if (c4 == ')') {
                                                n6 = 4;
                                                n7 = 3;
                                                break;
                                            }
                                        }
                                        n7 = 0;
                                        break;
                                    }
                                    if (c2 == 'p') {
                                        n7 = 1;
                                        break;
                                    }
                                    if (c2 == 'x') {
                                        n7 = 6;
                                        break;
                                    }
                                    if (c2 == '*') {
                                        n7 = 7;
                                        break;
                                    }
                                    if (c2 == '|') {
                                        n7 = 8;
                                        break;
                                    }
                                    if (c2 == 'd') {
                                        n7 = 9;
                                        break;
                                    }
                                    if (c2 == '/') {
                                        n7 = 13;
                                        break;
                                    }
                                    if (c2 == '<') {
                                        n7 = 14;
                                        break;
                                    }
                                    if (c2 == '&') {
                                        n7 = 15;
                                        break;
                                    }
                                    if (c2 == '(') {
                                        if (n5 > 3) {
                                            char c5 = string.charAt(n3 + 3);
                                            n5 = c5;
                                            if (c5 == '(') {
                                                n6 = 4;
                                                n7 = 22;
                                                break;
                                            }
                                        }
                                        n7 = 16;
                                        break;
                                    }
                                    if (c2 == 'b') {
                                        n7 = 17;
                                        break;
                                    }
                                    if (c2 == 'o') {
                                        n7 = 18;
                                        break;
                                    }
                                    if (c2 == 'h') {
                                        n7 = 19;
                                        break;
                                    }
                                    if (c2 == 's') {
                                        n7 = 20;
                                        break;
                                    }
                                    if (c2 == '?') {
                                        n7 = 21;
                                        break;
                                    }
                                    if (c2 != 'w') break;
                                    n7 = 25;
                                    break;
                                }
                                if (c3 == ')') {
                                    if (n5 > 2 && (c2 = string.charAt(n3 + 2)) == ')') {
                                        n7 = 3;
                                        break;
                                    }
                                    n6 = 2;
                                    n7 = 0;
                                    break;
                                }
                                if (c3 == 'p') {
                                    n6 = 2;
                                    n7 = 1;
                                    break;
                                }
                                if (c3 == 'x') {
                                    n6 = 2;
                                    n7 = 6;
                                    break;
                                }
                                if (c3 == '*') {
                                    n6 = 2;
                                    n7 = 7;
                                    break;
                                }
                                if (c3 == '|') {
                                    n6 = 2;
                                    n7 = 8;
                                    break;
                                }
                                if (c3 == 'd') {
                                    n6 = 2;
                                    n7 = 9;
                                    break;
                                }
                                if (c3 == '(') {
                                    if (n5 > 2 && (c2 = string.charAt(n3 + 2)) == '(') {
                                        n7 = 22;
                                        break;
                                    }
                                    n6 = 2;
                                    n7 = 16;
                                    break;
                                }
                                if (c3 == 'b') {
                                    n6 = 2;
                                    n7 = 17;
                                    break;
                                }
                                if (c3 == 'o') {
                                    n6 = 2;
                                    n7 = 18;
                                    break;
                                }
                                if (c3 == 's') {
                                    n6 = 2;
                                    n7 = 20;
                                    break;
                                }
                                if (c3 != '\"' || n5 <= 2 || (c2 = string.charAt(n3 + 2)) != '>') break;
                                n7 = 4;
                                break;
                            }
                            case '>': {
                                if (n5 <= 2) break;
                                c2 = string.charAt(n3 + 2);
                                if (c3 != ':') break;
                                if (n5 > 3) {
                                    n5 = string.charAt(n3 + 3);
                                    if (c2 == 'd' && n5 == 60) {
                                        n6 = 4;
                                        n7 = 2;
                                        break;
                                    }
                                }
                                if (c2 != ')') break;
                                n7 = 27;
                                break;
                            }
                            case 'b': {
                                if (n5 <= 2) break;
                                c2 = string.charAt(n3 + 2);
                                if (c3 != '-' || c2 != ')') break;
                                n7 = 5;
                                break;
                            }
                            case ';': {
                                if (n5 > 2) {
                                    c2 = string.charAt(n3 + 2);
                                    if (c3 == '-' && c2 == ')') {
                                        n7 = 10;
                                        break;
                                    }
                                }
                                if (c3 != ')') break;
                                n6 = 2;
                                n7 = 10;
                                break;
                            }
                            case '|': {
                                if (n5 <= 2) break;
                                c2 = string.charAt(n3 + 2);
                                if (c3 != '-' || c2 != ')') break;
                                n7 = 11;
                                break;
                            }
                            case '(': {
                                if (n5 <= 2) break;
                                c2 = string.charAt(n3 + 2);
                                if (c3 == ':' && c2 == '|') {
                                    n7 = 12;
                                    break;
                                }
                                if (c3 == 'y' && c2 == ')') {
                                    n7 = 29;
                                    break;
                                }
                                if (c3 == 'n' && c2 == ')') {
                                    n7 = 30;
                                    break;
                                }
                                if (c3 != '*' || c2 != ')') break;
                                n7 = 32;
                                break;
                            }
                            case '=': {
                                if (n5 > 2) {
                                    c2 = string.charAt(n3 + 2);
                                    if (c3 != '(' || c2 != '(') break;
                                    n7 = 23;
                                    break;
                                }
                                if (c3 != ';') break;
                                n6 = 2;
                                n7 = 24;
                                break;
                            }
                            case 'X': 
                            case 'x': {
                                if (n5 > 2) {
                                    c2 = string.charAt(n3 + 2);
                                    if (c3 != '-' || c2 != '(') break;
                                    n7 = 26;
                                    break;
                                }
                                if (c3 != '(') break;
                                n6 = 2;
                                n7 = 26;
                                break;
                            }
                            case 'I': 
                            case 'i': {
                                if (n5 <= 2) break;
                                c2 = string.charAt(n3 + 2);
                                if (c3 != '-' || c2 != ')') break;
                                n7 = 11;
                            }
                        }
                        if (n7 >= 0) {
                            String string2 = string.substring(0, n3);
                            String string3 = string.substring(n3, n3 + n6);
                            string = string.substring(n3 + n6);
                            string3 = "\u001a2" + (n7 < 10 ? "0" + n7 : "" + n7) + string3;
                            if (string2 == null || string2.length() <= 0) {
                                ((a)object2).a(string3, n2);
                            } else {
                                ((a)object2).a(string2, n2);
                                ((a)object2).b(string3, ++n2);
                            }
                            if (string == null || string.length() <= 0) break;
                            ((a)object2).b(string, n2 + 1);
                            break;
                        }
                        ++n3;
                    }
                }
                ++n2;
            }
            return;
        }
        catch (Throwable throwable) {
            object = throwable;
            throwable.printStackTrace();
            return;
        }
    }

    private static void a(a a2, String string, String string2, boolean bl2, int n2) {
        int n3 = 0;
        while (n3 < a2.d()) {
            String string3 = (String)a2.b(n3);
            if (string3.length() > 0 && string3.charAt(0) != '\u001a') {
                int n4 = -1;
                while ((n4 = string3.indexOf(string, n4 + 1)) >= 0) {
                    String string4;
                    String string5 = null;
                    String string6 = string3.substring(0, n4);
                    int n5 = string3.indexOf(string2, n4 + string.length());
                    if (n5 >= 0) {
                        if (bl2) {
                            ++n5;
                        }
                        string4 = string3.substring(n4, n5);
                        string5 = string3.substring(n5);
                    } else {
                        if (bl2) continue;
                        string4 = string3.substring(n4);
                    }
                    if (string4.length() <= string.length()) continue;
                    try {
                        if (n2 == 5 || n2 == 4) {
                            char c2;
                            n5 = string.length();
                            int n6 = string4.length();
                            while (n5 < n6) {
                                c2 = string4.charAt(n5);
                                if (!(c2 >= 'a' && c2 <= 'z' || c2 >= '0' && c2 <= '9' || c2 == '.') && c2 != '_') break;
                                ++n5;
                            }
                            if (n5 < string4.length()) {
                                if (n5 <= string.length()) {
                                    n4 += string.length() - 1;
                                    continue;
                                }
                                string4 = string3.substring(n4, n5 + n4);
                                string5 = string3.substring(n5 + n4);
                            }
                            if (string5 != null && string5.length() > 0 && string4.length() - string.length() > 1) {
                                n6 = string4.charAt(string4.length() - 1);
                                c2 = string5.charAt(0);
                                if (n6 == 46 && c2 == ' ') {
                                    string4 = string3.substring(n4, --n5 + n4);
                                    string5 = string3.substring(n5 + n4);
                                }
                            }
                        }
                    }
                    catch (Exception exception) {
                        Exception exception2 = exception;
                        exception.printStackTrace();
                    }
                    string4 = "\u001a" + n2 + string4;
                    if (string6 == null || string6.length() <= 0) {
                        a2.a(string4, n3);
                    } else {
                        a2.a(string6, n3);
                        a2.b(string4, ++n3);
                    }
                    if (string5 == null || string5.length() <= 0) break;
                    a2.b(string5, n3 + 1);
                    break;
                }
            }
            ++n3;
        }
    }

    private static q f(a a2, int n2) {
        return (q)a2.b(n2);
    }

    public static boolean a(int n2) {
        return n2 != 0 && n2 != 2;
    }

    public static int b(a a2, int n2) {
        q q2 = s.f(a2, n2);
        --n2;
        while (n2 >= 0) {
            q q3 = s.f(a2, n2);
            if (s.a(q3.c()) && q3.b() != q2.b()) {
                return n2;
            }
            --n2;
        }
        return -1;
    }

    public static int c(a a2, int n2) {
        q q2 = s.f(a2, n2);
        ++n2;
        while (n2 < a2.d()) {
            q q3 = s.f(a2, n2);
            if (s.a(q3.c()) && q3.b() != q2.b()) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public static int d(a a2, int n2) {
        q q2 = s.f(a2, n2);
        --n2;
        while (n2 >= 0) {
            q q3 = s.f(a2, n2);
            if (s.a(q3.c()) && q3.e() < q2.e() && q3.b() != q2.b()) {
                int n3 = Math.abs(q2.d() - q3.d());
                int n4 = n2 - 1;
                while (n4 >= 0) {
                    q q4 = s.f(a2, n4);
                    if (q3.e() != q4.e()) {
                        return n2;
                    }
                    if (s.a(q4.c()) && q4.b() != q3.b()) {
                        int n5 = Math.abs(q2.d() - q4.d());
                        if (n5 > n3) {
                            return n2;
                        }
                        if (n5 < n3) {
                            n2 = n4;
                            n3 = n5;
                        }
                    }
                    --n4;
                }
                return n2;
            }
            --n2;
        }
        return -1;
    }

    public static int e(a a2, int n2) {
        q q2 = s.f(a2, n2);
        ++n2;
        while (n2 < a2.d()) {
            q q3 = s.f(a2, n2);
            if (s.a(q3.c()) && q3.e() > q2.e() && q3.b() != q2.b()) {
                int n3 = Math.abs(q2.d() - q3.d());
                int n4 = n2 + 1;
                while (n4 < a2.d()) {
                    q q4 = s.f(a2, n4);
                    if (q3.e() != q4.e()) {
                        return n2;
                    }
                    if (s.a(q4.c()) && q4.b() != q3.b()) {
                        int n5 = Math.abs(q2.d() - q4.d());
                        if (n5 > n3) {
                            return n2;
                        }
                        if (n5 < n3) {
                            n2 = n4;
                            n3 = n5;
                        }
                    }
                    ++n4;
                }
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public static String a(a a2, int n2, boolean bl2) {
        int n3 = 0;
        while (n3 < a2.d()) {
            q q2 = (q)a2.b(n3);
            if (q2.c() == n2) {
                return s.b(a2, q2.b(), n3, bl2);
            }
            ++n3;
        }
        return null;
    }

    public s(int n2) {
        this.a = new Object[16];
        this.d = 0;
        this.b = 0;
        this.c = 0;
    }

    public boolean a() {
        return this.d == 0;
    }

    public void a(Object object) {
        if (this.d >= this.a.length) {
            throw new ArrayIndexOutOfBoundsException("Queue reach maximum size");
        }
        if (object == null) {
            throw new NullPointerException("Value can not be NULL");
        }
        this.a[this.c] = object;
        this.c = (this.c + 1) % this.a.length;
        ++this.d;
    }

    public Object b() {
        if (this.d == 0) {
            return null;
        }
        Object object = this.a[this.b];
        this.a[this.b] = null;
        this.b = (this.b + 1) % this.a.length;
        --this.d;
        return object;
    }

    public void c() {
        this.d = 0;
        this.b = 0;
        this.c = 0;
    }
}

