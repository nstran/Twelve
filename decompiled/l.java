/*
 * Decompiled with CFR 0.152.
 */
import java.util.Calendar;
import java.util.Date;

public final class l {
    private static Date b = new Date();
    public static Calendar a = Calendar.getInstance();
    private static String[] c = new String[]{"dd", "MM", "yyyy", "hh", "mm", "ss"};

    public static boolean a(String string) {
        if (string == null) {
            return true;
        }
        return (string = string.trim()) == null || string.length() == 0;
    }

    public static boolean b(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean a(String string, String string2) {
        if (string == null || string2 == null) {
            return false;
        }
        return (string = string.toUpperCase()).equals(string2 = string2.toUpperCase());
    }

    public static String[] b(String string, String string2) {
        return l.a(string, string2, -1, false);
    }

    public static String[] a(String string, String string2, int n2, boolean bl2) {
        if (n2 == 0 || l.b(string)) {
            return null;
        }
        if (string2 == null) {
            return new String[]{string};
        }
        a a2 = new a();
        String string3 = string;
        int n3 = string2.length();
        int n4 = bl2 ? string3.toLowerCase().indexOf(string2.toLowerCase()) : string3.indexOf(string2);
        if (n4 >= 0) {
            while (n4 >= 0) {
                if (n2 == 1) {
                    if (!l.b(string3)) {
                        a2.a(string3);
                    }
                    return l.a(a2);
                }
                if (n4 > 0) {
                    string = string3.substring(0, n4);
                    a2.a(string);
                    if (n2 > 0) {
                        --n2;
                    }
                }
                string3 = string3.substring(n4 + n3);
                n4 = bl2 ? string3.toLowerCase().indexOf(string2.toLowerCase()) : string3.indexOf(string2);
            }
            if (!l.a(string3)) {
                a2.a(string3);
            }
            return l.a(a2);
        }
        return new String[]{string};
    }

    private static String[] a(a a2) {
        if (a2 == null || a2.d() == 0) {
            return null;
        }
        int n2 = a2.d();
        String[] stringArray = new String[n2];
        int n3 = 0;
        while (n3 < n2) {
            stringArray[n3] = (String)a2.b(n3);
            ++n3;
        }
        return stringArray;
    }

    public static byte[] c(String string) {
        return l.c(string, null);
    }

    public static byte[] c(String string, String string2) {
        if (string2 == null) {
            string2 = "UTF-8";
        }
        if (l.b(string)) {
            return null;
        }
        return string.getBytes(string2);
    }

    private static String a(byte[] byArray, int n2, int n3, String string) {
        if (byArray == null) {
            return null;
        }
        if (n3 <= 0) {
            return "";
        }
        try {
            if (string != null) {
                return new String(byArray, n2, n3, string);
            }
        }
        catch (Throwable throwable) {}
        return new String(byArray, n2, n3);
    }

    public static String a(byte[] byArray, int n2, int n3) {
        return l.a(byArray, n2, n3, "UTF-8");
    }

    public static String a(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        return l.a(byArray, 0, byArray.length);
    }

    public static String a(String string, String string2, String[] stringArray) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while ((n3 = string.indexOf(string2, n2)) >= 0) {
            stringBuffer.append(string.substring(n2, n3));
            stringBuffer.append(stringArray[n4++]);
            n2 = n3 + string2.length();
            if (n4 >= stringArray.length) break;
        }
        stringBuffer.append(string.substring(n2));
        return stringBuffer.toString();
    }

    public static String a(long l2) {
        return l.a(l2, ",");
    }

    public static String a(long l2, String string) {
        if (l2 < 1000L) {
            return String.valueOf(l2);
        }
        String string2 = "";
        while (l2 >= 1000L) {
            long l3 = l2 % 1000L;
            string2 = l3 < 10L ? String.valueOf(string) + "00" + l3 + string2 : (l3 < 100L ? String.valueOf(string) + "0" + l3 + string2 : String.valueOf(string) + l3 + string2);
            l2 /= 1000L;
        }
        string2 = String.valueOf(l2) + string2;
        return string2;
    }

    public static String a() {
        long l2 = System.currentTimeMillis();
        b.setTime(l2);
        a.setTime(b);
        return String.valueOf(l.a(a.get(11))) + ":" + l.a(a.get(12));
    }

    private static String a(int n2) {
        if (n2 < 10) {
            return "0" + n2;
        }
        return String.valueOf(n2);
    }

    public static String b(long l2, String string) {
        b.setTime(l2);
        a.setTime(b);
        int[] nArray = new int[]{a.get(5), a.get(2) + 1, a.get(1), a.get(11), a.get(12), a.get(13)};
        int n2 = 0;
        while (n2 < c.length) {
            int n3 = string.indexOf(c[n2]);
            if (n3 >= 0) {
                string = String.valueOf(string.substring(0, n3)) + l.a(nArray[n2]) + string.substring(n3 + c[n2].length());
            }
            ++n2;
        }
        return string;
    }

    public static String b(long l2) {
        b.setTime(l2);
        a.setTime(b);
        return String.valueOf(a.get(5)) + "/" + (a.get(2) + 1) + "/" + a.get(1) + " " + a.get(11) + ":" + a.get(12) + ":" + a.get(13);
    }

    public static Calendar c(long l2) {
        b.setTime(l2);
        a.setTime(b);
        return a;
    }
}

