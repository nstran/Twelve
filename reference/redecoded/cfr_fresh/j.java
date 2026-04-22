/*
 * Decompiled with CFR 0.152.
 */
public class j {
    public int a;
    public int b;
    public int c;
    public int d;

    public j(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    public static byte[] a(byte[] byArray, String string) {
        try {
            byte by2 = byArray[0];
            byte[] byArray2 = string.getBytes("UTF-8");
            byte[] byArray3 = new byte[Math.max(byArray.length - 1, byArray2.length + by2) + 1];
            byte[] byArray4 = byArray3;
            byArray3[0] = (byte)byArray2.length;
            int n2 = 0;
            while (n2 < byArray4.length - 1) {
                int n3 = 17;
                int n4 = 0;
                if (n2 + 1 < byArray.length) {
                    n3 = byArray[n2 + 1];
                }
                if (n2 >= by2 && n2 - by2 < byArray2.length) {
                    n4 = byArray2[n2 - by2];
                }
                byArray4[n2 + 1] = (byte)(n3 ^ n4);
                ++n2;
            }
            return byArray4;
        }
        catch (Throwable throwable) {
            try {
                byte by3 = byArray[0];
                byte[] byArray5 = string.getBytes();
                byte[] byArray6 = new byte[Math.max(byArray.length - 1, byArray5.length + by3) + 1];
                byte[] byArray7 = byArray6;
                byArray6[0] = (byte)byArray5.length;
                int n5 = 0;
                while (n5 < byArray7.length - 1) {
                    int n6 = 17;
                    int n7 = 0;
                    if (n5 + 1 < byArray.length) {
                        n6 = byArray[n5 + 1];
                    }
                    if (n5 >= by3 && n5 - by3 < byArray5.length) {
                        n7 = byArray5[n5 - by3];
                    }
                    byArray7[n5 + 1] = (byte)(n6 ^ n7);
                    ++n5;
                }
                return byArray7;
            }
            catch (Throwable throwable2) {
                return null;
            }
        }
    }
}

