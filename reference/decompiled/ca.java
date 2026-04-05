/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ca {
    private static ca f = null;
    public static d a;
    public static d b;
    public static d c;
    private static d g;
    public static d d;
    public static d e;
    private static int h;
    private static String[] i;
    private static char j;
    private static char k;
    private static char l;
    private static char m;
    private static char n;
    private static char o;
    private static char p;
    private static char q;

    static {
        h = -1;
        i = null;
        j = (char)41;
        k = (char)41;
        l = (char)41;
        m = (char)41;
        n = (char)40;
        o = (char)40;
        p = (char)40;
        q = (char)40;
    }

    private ca() {
        Object object;
        a = new cc();
        try {
            object = f.c("/_fontcap");
            c = new cd(f.a(object));
            h.a(object, new int[]{0xFFFFFF}, new int[]{0xFFFF68});
            b = new cd(f.a(object));
        }
        catch (Exception exception) {
            // empty catch block
        }
        d d2 = b;
        object = d2;
        g = d2;
    }

    public static void a(d d2) {
        g = d2;
    }

    public static void a() {
        if (f == null) {
            f = new ca();
        }
    }

    public static String[] a(String string, int n2) {
        return ca.a(string, n2, g);
    }

    public static String[] a(String string, int n2, d d2) {
        int n3;
        int n4;
        a a2 = new a(10);
        int n5 = 0;
        int n6 = -1;
        String string2 = "";
        block0: while (n5 < string.length()) {
            if (n6 == n5) {
                ++n5;
            }
            while (true) {
                if (n5 >= string.length() || string.charAt(n5) != ' ') {
                    if (n5 <= 0 || a2.d() != 0 || n5 * d2.a(' ') >= n2) break;
                    String string3 = "";
                    n4 = 0;
                    while (n4 < n5) {
                        string3 = String.valueOf(string3) + ' ';
                        ++n4;
                    }
                    a2.a(string3);
                    break;
                }
                n6 = n5++;
            }
            while (true) {
                if (n5 >= string.length() || string.charAt(n5) == ' ' || string.charAt(n5) == '\r') {
                    if (string2.length() > 0) {
                        if (n5 < string.length() && string.charAt(n5) == '\r') {
                            string2 = String.valueOf(string2) + '\r';
                            n6 = n5++;
                        } else {
                            string2 = String.valueOf(string2) + " ";
                        }
                        int n7 = d2.a(string2);
                        if (n7 < n2) {
                            a2.a(string2);
                        } else {
                            n4 = 0;
                            String string4 = "";
                            n3 = 0;
                            while (n4 < string2.length()) {
                                if (n3 + d2.a(string2.charAt(n4)) < n2) {
                                    n3 += d2.a(string2.charAt(n4));
                                    string4 = String.valueOf(string4) + string2.charAt(n4);
                                    if (++n4 < string2.length() || string4.length() <= 0) continue;
                                    a2.a(string4);
                                    break;
                                }
                                a2.a(string4);
                                n3 = 0;
                                string4 = "";
                            }
                        }
                    } else if (n5 < string.length() && string.charAt(n5) == '\r') {
                        n6 = n5++;
                    }
                    string2 = "";
                    continue block0;
                }
                string2 = String.valueOf(string2) + string.charAt(n5);
                n6 = n5++;
            }
        }
        String[] stringArray = new String[a2.d()];
        n4 = 0;
        while (n4 < stringArray.length) {
            stringArray[n4] = (String)a2.b(n4);
            ++n4;
        }
        a2 = new a(10);
        String string5 = "";
        int n8 = 0;
        n5 = 0;
        while (true) {
            if (n5 >= stringArray.length) {
                if (string5.length() <= 0) break;
                a2.a(string5);
                break;
            }
            n3 = d2.a(stringArray[n5]);
            if (n8 + n3 - d2.a(' ') < n2) {
                n8 += n3;
                string5 = String.valueOf(string5) + stringArray[n5];
                if (stringArray[n5].length() > 0 && stringArray[n5].charAt(stringArray[n5].length() - 1) == '\r') {
                    a2.a(string5);
                    string5 = "";
                    n8 = 0;
                }
                ++n5;
                continue;
            }
            if (n3 - d2.a(' ') >= n2) {
                a2.a(stringArray[n5]);
                ++n5;
            } else {
                a2.a(string5);
            }
            string5 = "";
            n8 = 0;
        }
        String[] stringArray2 = new String[a2.d()];
        int n9 = 0;
        while (n9 < stringArray2.length) {
            stringArray2[n9] = (String)a2.b(n9);
            ++n9;
        }
        return stringArray2;
    }

    public static void a(Graphics graphics, d d2, String string, int n2, int n3, int n4, int n5, int n6) {
        String[] stringArray;
        if (string.length() == h && string.length() > 8 && j == string.charAt(1) && k == string.charAt(2) && l == string.charAt(3) && m == string.charAt(4) && n == string.charAt(string.length() - 1) && o == string.charAt(string.length() - 2) && p == string.charAt(string.length() - 3) && q == string.charAt(string.length() - 4)) {
            stringArray = i;
        } else {
            stringArray = ca.a(string, n4, d2);
            if (string.length() > 4) {
                h = string.length();
                i = stringArray;
                j = string.charAt(1);
                k = string.charAt(2);
                l = string.charAt(3);
                m = string.charAt(4);
                q = string.charAt(string.length() - 4);
                p = string.charAt(string.length() - 3);
                o = string.charAt(string.length() - 2);
                n = string.charAt(string.length() - 1);
            }
        }
        ca.a(graphics, d2, stringArray, n2, n3, n4, n5, 0);
    }

    public static void a(Graphics graphics, d d2, String[] stringArray, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9 = n4;
        if (n8 == 1) {
            n9 = n4 + n6 / 2;
        } else if (n8 == 2) {
            n9 = n4 + n6;
        }
        n3 = n2 + n3;
        if (n3 > stringArray.length) {
            n3 = stringArray.length;
        }
        n4 = 0;
        while (n2 < n3) {
            d2.a(graphics, stringArray[n2], n9, n5 + n4, n8);
            if ((n4 += d2.a()) > n7) break;
            ++n2;
        }
    }

    public static void a(Graphics graphics, d d2, String[] stringArray, int n2, int n3, int n4, int n5, int n6) {
        ca.a(graphics, d2, stringArray, 0, stringArray.length, n2, n3, n4, n5, n6);
    }
}

