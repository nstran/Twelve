/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.wireless.messaging.MessageConnection
 */
import java.util.Vector;
import javax.wireless.messaging.MessageConnection;

public final class ci
implements bf,
cg {
    public static String a = "TESTGAME";
    public static String b = "3";
    public static String c = "1";
    public static String d = "19003";
    public static int e = 0;
    public static int f = 6;
    public static int g = 5;
    public static int h = 5;
    public static int i = 5;
    public static int j = 1;
    public static String k = String.valueOf(a) + b;
    public static String l = null;
    private static ci m;

    protected ci() {
    }

    public static ci a() {
        if (m == null) {
            m = new ci();
        }
        return m;
    }

    public static void a(String string, int n2, be be2) {
        try {
            String string2 = System.getProperty("microedition.platform");
            if (string2 == null) {
                string2 = "Unknown";
            } else if (string2.length() >= 80) {
                string2 = string2.substring(0, 80);
            }
            string2 = " \"m" + string2 + "\" e" + String.valueOf(System.currentTimeMillis());
            String string3 = String.valueOf(k) + " v" + "2" + " a2 o" + String.valueOf(n2) + " d" + b + " p" + l;
            if (string != null) {
                string3 = String.valueOf(string3) + " r" + string;
            }
            string3 = String.valueOf(string3) + string2;
            ch.a(string3, String.valueOf(h), new cj(be2));
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static void b(String string) {
        cr.a();
        string = "RESET " + string;
        ch.a(string, "0", new ck());
    }

    public static void a(String string, String string2, be be2) {
        cr.a();
        ch.a(string, String.valueOf(string2), new cl(be2));
    }

    public static void a(String string, String string2, String string3) {
        string = String.valueOf(string) + " " + string2 + " " + c + " " + cv.a(100);
        ci.a(string, string3, null);
    }

    public final void a(String stringArray) {
        String[] stringArray2;
        int n2 = -1;
        String string = "\"";
        Object object = "\"";
        Object object2 = " ";
        String[] stringArray3 = stringArray;
        boolean bl2 = false;
        int n3 = 0;
        Vector<String> vector = new Vector<String>();
        int n4 = 0;
        while (n4 < stringArray3.length() - ((String)object2).length() + 1) {
            if (((String)object2).equals(stringArray3.substring(n4, n4 + ((String)object2).length())) && !bl2) {
                vector.addElement(stringArray3.substring(n3, n4));
                n3 = n4 + ((String)object2).length();
                n4 = n3 - 1;
            } else if (((String)object).equals(stringArray3.substring(n4, n4 + ((String)object).length())) && !bl2) {
                vector.addElement(stringArray3.substring(n3, n4));
                n3 = n4 + ((String)object).length();
                n4 = n3 - 1;
                bl2 = true;
            } else if (string.equals(stringArray3.substring(n4, n4 + string.length())) && bl2) {
                vector.addElement(stringArray3.substring(n3, n4));
                n3 = n4 + string.length();
                n4 = n3 - 1;
                bl2 = false;
            }
            ++n4;
        }
        if (n3 < stringArray3.length()) {
            vector.addElement(stringArray3.substring(n3));
        }
        if (vector.size() > 0) {
            stringArray3 = new String[vector.size()];
            n4 = 0;
            while (n4 < vector.size()) {
                stringArray3[n4] = (String)vector.elementAt(n4);
                ++n4;
            }
            stringArray2 = stringArray3;
        } else {
            stringArray2 = stringArray3 = null;
        }
        if (!stringArray2[0].toUpperCase().equals(k.toUpperCase())) {
            return;
        }
        try {
            int n5 = 0;
            object = null;
            string = null;
            String string2 = null;
            n3 = 1;
            while (n3 < stringArray3.length) {
                if (stringArray3[n3].length() != 0) {
                    char c2 = stringArray3[n3].charAt(0);
                    String string3 = null;
                    if (stringArray3[n3].length() > 1) {
                        string3 = stringArray3[n3].substring(1);
                    }
                    switch (c2) {
                        case 'a': {
                            n5 = Integer.parseInt(string3);
                            break;
                        }
                        case 'l': {
                            object = string3;
                            break;
                        }
                        case 'c': {
                            string = string3;
                            break;
                        }
                        case 'q': {
                            break;
                        }
                        case 'r': {
                            string2 = string3;
                            break;
                        }
                        case 'i': {
                            try {
                                Integer.parseInt(string3);
                                break;
                            }
                            catch (Exception exception) {}
                        }
                    }
                }
                ++n3;
            }
            if (ag.b() != null) {
                String string4;
                n3 = n5;
                Object object3 = object;
                String string5 = string;
                stringArray3 = string2;
                if (string2 != null && string2.length() >= 4 && (string4 = string2.substring(0, 4).toLowerCase()).equals("reco")) {
                    ag.b().a(n3, (String)object3, string5, (String)stringArray3);
                }
                cn.a().d(0, 3);
                cr.g();
                return;
            }
        }
        catch (Exception exception) {
            object2 = exception;
            exception.printStackTrace();
            object = ag.b().a("C\u00f3 l\u1ed7i", "Tin sai c\u00fa ph\u00e1p", "C\u00f3", 3, 1);
            ((am)object).b(-10008);
            ((am)object).a(ci.a());
            ag.b().a((al)object);
        }
    }

    public final void d(int n2, int n3) {
        if (n3 == 1) {
            ag.b().a(-10007, false);
            return;
        }
        if (n3 == 2) {
            ag.b().a(-10009, false);
            return;
        }
        if (n3 == 3) {
            ag.b().a(-10008, false);
        }
    }

    public static void a(MessageConnection messageConnection) {
        ch.a(messageConnection);
    }
}

