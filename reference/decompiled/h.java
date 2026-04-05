/*
 * Decompiled with CFR 0.152.
 */
public final class h {
    private static int[] a;
    private static int b;
    private static int c;
    private static int d;
    private static int e;
    private static int f;
    private static int g;

    static {
        byte by2;
        char[] cArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        a = new int[360];
        int n2 = 0;
        while (n2 <= 60) {
            by2 = (byte)(n2 * 255 / 60);
            h.a[n2 + 0] = h.a((byte)-1, by2, (byte)0);
            ++n2;
        }
        n2 = 0;
        while (n2 <= 60) {
            by2 = (byte)(255 - n2 * 255 / 60);
            h.a[n2 + 60] = h.a(by2, (byte)-1, (byte)0);
            ++n2;
        }
        n2 = 0;
        while (n2 <= 60) {
            by2 = (byte)(n2 * 255 / 60);
            h.a[n2 + 120] = h.a((byte)0, (byte)-1, by2);
            ++n2;
        }
        n2 = 0;
        while (n2 <= 60) {
            by2 = (byte)(255 - n2 * 255 / 60);
            h.a[n2 + 180] = h.a((byte)0, by2, (byte)-1);
            ++n2;
        }
        n2 = 0;
        while (n2 <= 60) {
            by2 = (byte)(n2 * 255 / 60);
            h.a[n2 + 240] = h.a(by2, (byte)0, (byte)-1);
            ++n2;
        }
        n2 = 0;
        while (n2 < 60) {
            by2 = (byte)(255 - n2 * 255 / 60);
            h.a[n2 + 300] = h.a((byte)-1, (byte)0, by2);
            ++n2;
        }
        b = 1;
        c = 2;
        d = 4;
        e = 8;
        f = 16;
        g = 32;
    }

    private static byte[] a(int n2) {
        byte[] byArray = new byte[3];
        byte[] byArray2 = byArray;
        byArray[0] = (byte)(n2 >> 16 & 0xFF);
        byArray2[1] = (byte)(n2 >> 8 & 0xFF);
        byArray2[2] = (byte)n2;
        return byArray2;
    }

    private static int a(byte by2, byte by3, byte by4) {
        byte[] byArray = new byte[4];
        byArray[1] = by2;
        byArray[2] = by3;
        byArray[3] = by4;
        byte[] byArray2 = byArray;
        return p.c(byArray);
    }

    public static boolean a(byte[] byArray, int[] nArray, int[] nArray2) {
        int n2;
        boolean bl2;
        if (nArray != null && nArray2 != null) {
            bl2 = false;
            n2 = 0;
            while (n2 < nArray.length) {
                if (nArray[n2] != nArray2[n2]) {
                    bl2 = true;
                    break;
                }
                ++n2;
            }
            if (!bl2) {
                return false;
            }
        }
        bl2 = false;
        n2 = 0;
        Exception exception = null;
        int n3 = 0;
        try {
            int n4;
            byte by2;
            byte by3;
            int n5;
            int n6;
            int n7 = 0;
            int n8 = byArray.length - 4;
            while (n7 < n8) {
                if (byArray[n7] == 80 && byArray[n7 + 1] == 76 && byArray[n7 + 2] == 84 && byArray[n7 + 3] == 69) {
                    n3 = n7;
                    n2 = p.a(byArray[n7 - 4], byArray[n7 - 3], byArray[n7 - 2], byArray[n7 - 1]);
                    break;
                }
                ++n7;
            }
            n7 = -1;
            try {
                n8 = n3 + 12 + n2;
                if (byArray[n8] == 116 && byArray[n8 + 1] == 82 && byArray[n8 + 2] == 78 && byArray[n8 + 3] == 83) {
                    n7 = p.a(byArray[n8 - 4], byArray[n8 - 3], byArray[n8 - 2], byArray[n8 - 1]);
                    --n7;
                }
            }
            catch (Exception exception2) {
                n7 = -1;
            }
            if (n7 >= 0) {
                n8 = 0;
                n6 = n3 + 4;
                n5 = n2 + n3 + 4;
                while (n6 < n5) {
                    by3 = byArray[n6];
                    by2 = byArray[n6 + 1];
                    n4 = byArray[n6 + 2];
                    if (by3 == 255 && by2 == 255 && n4 == 255) {
                        ++n8;
                    }
                    n6 += 3;
                }
                if (n8 < 2) {
                    n7 = -1;
                }
            }
            boolean[] blArray = new boolean[n2 / 3];
            n6 = 0;
            while (n6 < blArray.length) {
                blArray[n6] = true;
                ++n6;
            }
            n6 = 0;
            while (n6 < nArray.length) {
                n5 = (byte)(nArray[n6] >> 16 & 0xFF);
                by3 = (byte)(nArray[n6] >> 8 & 0xFF);
                by2 = (byte)nArray[n6];
                int n9 = n4 = n3 + 4;
                int n10 = n2 + n4;
                while (n9 < n10) {
                    if ((n9 - n4) / 3 != n7 && blArray[(n9 - n4) / 3] && byArray[n9] == n5 && byArray[n9 + 1] == by3 && byArray[n9 + 2] == by2) {
                        byArray[n9] = (byte)(nArray2[n6] >> 16 & 0xFF);
                        byArray[n9 + 1] = (byte)(nArray2[n6] >> 8 & 0xFF);
                        byArray[n9 + 2] = (byte)nArray2[n6];
                        blArray[(n9 - n4) / 3] = false;
                        bl2 = true;
                    }
                    n9 += 3;
                }
                ++n6;
            }
            byte[] byArray2 = new byte[n2 + 4];
            n6 = 0;
            while (n6 < n2 + 4) {
                byArray2[n6] = byArray[n3 + n6];
                ++n6;
            }
            e.a();
            long l2 = e.a(byArray2);
            by3 = 3;
            while (by3 >= 0) {
                byArray[n3 + 4 + n2 + 3 - by3] = by2 = (byte)(l2 >> by3 * 8 & 0xFFL);
                --by3;
            }
            return bl2;
        }
        catch (Exception exception3) {
            exception = exception3;
            exception3.printStackTrace();
            return false;
        }
    }

    public static void a(byte[] byArray, int n2) {
        if (n2 == 0) {
            return;
        }
        int n3 = 0;
        Object var3_3 = null;
        int n4 = 0;
        try {
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10 = 0;
            int n11 = byArray.length - 4;
            while (n10 < n11) {
                if (byArray[n10] == 80 && byArray[n10 + 1] == 76 && byArray[n10 + 2] == 84 && byArray[n10 + 3] == 69) {
                    n4 = n10;
                    n3 = p.a(byArray[n10 - 4], byArray[n10 - 3], byArray[n10 - 2], byArray[n10 - 1]);
                    break;
                }
                ++n10;
            }
            n10 = -1;
            try {
                n11 = n4 + 12 + n3;
                if (byArray[n11] == 116 && byArray[n11 + 1] == 82 && byArray[n11 + 2] == 78 && byArray[n11 + 3] == 83) {
                    n10 = p.a(byArray[n11 - 4], byArray[n11 - 3], byArray[n11 - 2], byArray[n11 - 1]);
                    --n10;
                }
            }
            catch (Exception exception) {
                n10 = -1;
            }
            if (n10 >= 0) {
                n11 = 0;
                n9 = n4 + 4;
                n8 = n3 + n4 + 4;
                while (n9 < n8) {
                    n7 = byArray[n9];
                    n6 = byArray[n9 + 1];
                    n5 = byArray[n9 + 2];
                    if (n7 == 255 && n6 == 255 && n5 == 255) {
                        ++n11;
                    }
                    n9 += 3;
                }
                if (n11 < 2) {
                    n10 = -1;
                }
            }
            n11 = n4 + 4;
            n9 = n4 + 4 + n3;
            while (n11 < n9) {
                if (n11 != n10) {
                    int n12;
                    n8 = byArray[n11];
                    n7 = byArray[n11 + 1];
                    n6 = byArray[n11 + 2];
                    int n13 = h.a((byte)n8, (byte)n7, (byte)n6);
                    n7 = n2;
                    n8 = n13;
                    n6 = n13 >> 16 & 0xFF;
                    n5 = n8 >> 8 & 0xFF;
                    int n14 = n8 & 0xFF;
                    int n15 = 0;
                    int n16 = 0;
                    if (n6 == n5 && n5 == n14) {
                        n12 = n8;
                    } else {
                        n8 = 0;
                        if (n6 >= n5 && n5 >= n14) {
                            n15 = n6;
                            n16 = n14;
                            n8 = 60 * (n5 - n14) / (n6 - n14);
                            if (n8 < 0 || n8 > 60) {
                                System.out.println("Co van de khi tinh goc hien t\u1ea1i o truong hop 1");
                            }
                        } else if (n5 > n6 && n6 >= n14) {
                            n15 = n5;
                            n16 = n14;
                            n8 = 120 - 60 * (n6 - n14) / (n5 - n14);
                            if (n8 < 60 || n8 > 120) {
                                System.out.println("Co van de khi tinh goc hien t\u1ea1i o truong hop 2");
                            }
                        } else if (n5 >= n14 && n14 > n6) {
                            n15 = n5;
                            n16 = n6;
                            n8 = 120 + 60 * (n14 - n6) / (n5 - n6);
                            if (n8 < 120 || n8 > 180) {
                                System.out.println("Co van de khi tinh goc hien t\u1ea1i o truong hop 3");
                            }
                        } else if (n14 > n5 && n5 > n6) {
                            n15 = n14;
                            n16 = n6;
                            n8 = 240 - 60 * (n5 - n6) / (n14 - n6);
                            if (n8 < 180 || n8 > 240) {
                                System.out.println("Co van de khi tinh goc hien t\u1ea1i o truong hop 4");
                            }
                        } else if (n14 > n6 && n6 >= n5) {
                            n15 = n14;
                            n16 = n5;
                            n8 = 240 + 60 * (n6 - n5) / (n14 - n5);
                            if (n8 < 240 || n8 > 320) {
                                System.out.println("Co van de khi tinh goc hien t\u1ea1i o truong hop 5");
                            }
                        } else if (n6 >= n14 && n14 > n5) {
                            n15 = n6;
                            n16 = n5;
                            n8 = 360 - 60 * (n14 - n5) / (n6 - n5);
                            if (n8 < 320 || n8 > 360) {
                                System.out.println("Co van de khi tinh goc hien t\u1ea1i o truong hop 6");
                            }
                        }
                        n8 = (n8 + n7 + 360) % 360;
                        n7 = 0;
                        n6 = 0;
                        n5 = 0;
                        if (n8 >= 0 && n8 < 60) {
                            n7 = n15;
                            n5 = n16;
                            n6 = n8 * (n7 - n5) / 60 + n5;
                        } else if (60 <= n8 && n8 < 120) {
                            n6 = n15;
                            n5 = n16;
                            n7 = (n8 - 120) * (n5 - n6) / 60 + n5;
                        } else if (120 <= n8 && n8 < 180) {
                            n6 = n15;
                            n7 = n16;
                            n5 = (n8 - 120) * (n6 - n7) / 60 + n7;
                        } else if (180 <= n8 && n8 < 240) {
                            n5 = n15;
                            n7 = n16;
                            n6 = (n8 - 240) * (n7 - n5) / 60 + n7;
                        } else if (240 <= n8 && n8 < 300) {
                            n5 = n15;
                            n6 = n16;
                            n7 = (n8 - 240) * (n5 - n6) / 60 + n6;
                        } else if (300 <= n8 && n8 < 360) {
                            n7 = n15;
                            n6 = n16;
                            n5 = (n8 - 360) * (n6 - n7) / 60 + n6;
                        }
                        n8 = 0 | n7 << 16;
                        n12 = n8 = (n8 |= n6 << 8) | n5;
                    }
                    byte[] byArray2 = h.a(n12);
                    byArray[n11] = byArray2[0];
                    byArray[n11 + 1] = byArray2[1];
                    byArray[n11 + 2] = byArray2[2];
                }
                n11 += 3;
            }
            byte[] byArray3 = new byte[n3 + 4];
            n11 = 0;
            while (n11 < n3 + 4) {
                byArray3[n11] = byArray[n4 + n11];
                ++n11;
            }
            e.a();
            long l2 = e.a(byArray3);
            n8 = 3;
            while (n8 >= 0) {
                byArray[n4 + 4 + n3 + 3 - n8] = n7 = (int)((byte)(l2 >> n8 * 8 & 0xFFL));
                --n8;
            }
            return;
        }
        catch (Exception exception) {
            System.out.println("Co loi trong qua trinh HUE 1 file PNG");
            exception.printStackTrace();
            return;
        }
    }

    public static void b(byte[] byArray, int n2) {
        if (n2 == 0) {
            return;
        }
        int n3 = 0;
        Object var3_3 = null;
        int n4 = 0;
        try {
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            int n10 = 0;
            int n11 = byArray.length - 4;
            while (n10 < n11) {
                if (byArray[n10] == 80 && byArray[n10 + 1] == 76 && byArray[n10 + 2] == 84 && byArray[n10 + 3] == 69) {
                    n4 = n10;
                    n3 = p.a(byArray[n10 - 4], byArray[n10 - 3], byArray[n10 - 2], byArray[n10 - 1]);
                    break;
                }
                ++n10;
            }
            n10 = -1;
            try {
                n11 = n4 + 12 + n3;
                if (byArray[n11] == 116 && byArray[n11 + 1] == 82 && byArray[n11 + 2] == 78 && byArray[n11 + 3] == 83) {
                    n10 = p.a(byArray[n11 - 4], byArray[n11 - 3], byArray[n11 - 2], byArray[n11 - 1]);
                    --n10;
                }
            }
            catch (Exception exception) {
                n10 = -1;
            }
            if (n10 >= 0) {
                n11 = 0;
                n9 = n4 + 4;
                n8 = n3 + n4 + 4;
                while (n9 < n8) {
                    n7 = byArray[n9];
                    n6 = byArray[n9 + 1];
                    n5 = byArray[n9 + 2];
                    if (n7 == 255 && n6 == 255 && n5 == 255) {
                        ++n11;
                    }
                    n9 += 3;
                }
                if (n11 < 2) {
                    n10 = -1;
                }
            }
            n11 = n4 + 4;
            n9 = n4 + 4 + n3;
            while (n11 < n9) {
                if (n11 != n10) {
                    int n12;
                    n8 = byArray[n11];
                    n7 = byArray[n11 + 1];
                    n6 = byArray[n11 + 2];
                    int n13 = h.a((byte)n8, (byte)n7, (byte)n6);
                    n7 = n2;
                    n8 = n13;
                    if (n7 <= -100) {
                        n12 = 0;
                    } else if (n7 >= 100) {
                        n12 = 0xFFFFFF;
                    } else {
                        n6 = n8 >> 16 & 0xFF;
                        n5 = n8 >> 8 & 0xFF;
                        n8 &= 0xFF;
                        if (n7 < 0) {
                            n6 = n6 * (n7 + 100) / 100;
                            n5 = n5 * (n7 + 100) / 100;
                            n8 = n8 * (n7 + 100) / 100;
                        } else {
                            n6 += (255 - n6) * n7 / 100;
                            n5 += (255 - n5) * n7 / 100;
                            n8 += (255 - n8) * n7 / 100;
                        }
                        n7 = 0 | n6 << 16;
                        n12 = n7 = (n7 |= n5 << 8) | n8;
                    }
                    byte[] byArray2 = h.a(n12);
                    byArray[n11] = byArray2[0];
                    byArray[n11 + 1] = byArray2[1];
                    byArray[n11 + 2] = byArray2[2];
                }
                n11 += 3;
            }
            byte[] byArray3 = new byte[n3 + 4];
            n11 = 0;
            while (n11 < n3 + 4) {
                byArray3[n11] = byArray[n4 + n11];
                ++n11;
            }
            e.a();
            long l2 = e.a(byArray3);
            n8 = 3;
            while (n8 >= 0) {
                byArray[n4 + 4 + n3 + 3 - n8] = n7 = (int)((byte)(l2 >> n8 * 8 & 0xFFL));
                --n8;
            }
            return;
        }
        catch (Exception exception) {
            System.out.println("Co loi trong qua trinh HUE 1 file PNG");
            exception.printStackTrace();
            return;
        }
    }

    public static void a(byte[] byArray) {
        int n2 = 0;
        Object var2_2 = null;
        int n3 = 0;
        try {
            int n4;
            int n5;
            int n6;
            int n7;
            int n8 = 0;
            int n9 = byArray.length - 4;
            while (n8 < n9) {
                if (byArray[n8] == 80 && byArray[n8 + 1] == 76 && byArray[n8 + 2] == 84 && byArray[n8 + 3] == 69) {
                    n3 = n8;
                    n2 = p.a(byArray[n8 - 4], byArray[n8 - 3], byArray[n8 - 2], byArray[n8 - 1]);
                    break;
                }
                ++n8;
            }
            n8 = -1;
            try {
                n9 = n3 + 12 + n2;
                if (byArray[n9] == 116 && byArray[n9 + 1] == 82 && byArray[n9 + 2] == 78 && byArray[n9 + 3] == 83) {
                    n8 = p.a(byArray[n9 - 4], byArray[n9 - 3], byArray[n9 - 2], byArray[n9 - 1]);
                    --n8;
                }
            }
            catch (Exception exception) {
                n8 = -1;
            }
            if (n8 >= 0) {
                n9 = 0;
                n7 = n3 + 4;
                n6 = n2 + n3 + 4;
                while (n7 < n6) {
                    n5 = byArray[n7];
                    n4 = byArray[n7 + 1];
                    byte by2 = byArray[n7 + 2];
                    if (n5 == 255 && n4 == 255 && by2 == 255) {
                        ++n9;
                    }
                    n7 += 3;
                }
                if (n9 < 2) {
                    n8 = -1;
                }
            }
            n9 = n3 + 4;
            n7 = n3 + 4 + n2;
            while (n9 < n7) {
                if (n8 != n9) {
                    n6 = byArray[n9];
                    n5 = byArray[n9 + 1];
                    n4 = byArray[n9 + 2];
                    n6 = h.a((byte)n6, (byte)n5, (byte)n4);
                    n5 = n6 >> 16 & 0xFF;
                    n4 = n6 >> 8 & 0xFF;
                    n4 = n5 = (n5 + n4 + (n6 &= 0xFF)) / 3;
                    n6 = 0 | n5 << 16;
                    n6 |= n5 << 8;
                    byte[] byArray2 = h.a(n6 |= n4);
                    byArray[n9] = byArray2[0];
                    byArray[n9 + 1] = byArray2[1];
                    byArray[n9 + 2] = byArray2[2];
                }
                n9 += 3;
            }
            byte[] byArray3 = new byte[n2 + 4];
            n9 = 0;
            while (n9 < n2 + 4) {
                byArray3[n9] = byArray[n3 + n9];
                ++n9;
            }
            e.a();
            long l2 = e.a(byArray3);
            n6 = 3;
            while (n6 >= 0) {
                byArray[n3 + 4 + n2 + 3 - n6] = n5 = (int)((byte)(l2 >> n6 * 8 & 0xFFL));
                --n6;
            }
            return;
        }
        catch (Exception exception) {
            System.out.println("Co loi trong qua trinh GRAY 1 file PNG");
            exception.printStackTrace();
            return;
        }
    }
}

