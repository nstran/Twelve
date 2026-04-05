/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class cz {
    public static n a;

    static {
        byte[][] byArrayArray = new byte[][]{{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        byte[][] byArrayArray2 = new byte[5][];
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 1;
        byArrayArray2[0] = byArray;
        byArrayArray2[1] = new byte[]{1, 3, 5, 3, 1};
        byArrayArray2[2] = new byte[]{2, 5, 9, 5, 2};
        byArrayArray2[3] = new byte[]{1, 3, 5, 3, 1};
        byte[] byArray2 = new byte[5];
        byArray2[1] = 1;
        byArray2[2] = 2;
        byArray2[3] = 1;
        byArrayArray2[4] = byArray2;
        a = new n(0, 0, z.r, z.s);
    }

    public static void a(Graphics graphics) {
        cz.a(graphics, a);
    }

    public static void a(Graphics graphics, n n2) {
        n2.a = graphics.getClipX();
        n2.b = graphics.getClipY();
        n2.c = graphics.getClipWidth();
        n2.d = graphics.getClipHeight();
    }

    public static void b(Graphics graphics, n n2) {
        cz.a(graphics, n2, a);
    }

    public static void a(Graphics graphics, n n2, n n3) {
        cz.a(graphics, n2.a, n2.b, n2.c, n2.d, n3.a, n3.b, n3.c, n3.d);
    }

    public static void a(Graphics graphics, n n2, int n3, int n4, int n5, int n6) {
        cz.a(graphics, n2.a, n2.b, n2.c, n2.d, n3, n4, n5, n6);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        cz.a(graphics, graphics.getClipX(), graphics.getClipY(), graphics.getClipWidth(), graphics.getClipHeight(), n2, n3, n4, n5);
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        int n10 = n2;
        int n11 = n3;
        long l2 = n2;
        l2 += (long)n4;
        long l3 = n3;
        l3 += (long)n5;
        long l4 = n6;
        l4 += (long)n8;
        long l5 = n7;
        l5 += (long)n9;
        if (n2 < n6) {
            n10 = n6;
        }
        if (n3 < n7) {
            n11 = n7;
        }
        if (l2 > l4) {
            l2 = l4;
        }
        if (l3 > l5) {
            l3 = l5;
        }
        l3 -= (long)n11;
        if ((l2 -= (long)n10) < Integer.MIN_VALUE) {
            l2 = Integer.MIN_VALUE;
        }
        if (l3 < Integer.MIN_VALUE) {
            l3 = Integer.MIN_VALUE;
        }
        graphics.setClip(n10, n11, (int)l2, (int)l3);
    }

    public static void c(Graphics graphics, n n2) {
        graphics.setClip(n2.a, n2.b, n2.c, n2.d);
    }

    public static void b(Graphics graphics) {
        cz.c(graphics, a);
    }

    public static final void a(Graphics graphics, Image image, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        if ((n8 & 1) > 0) {
            n6 -= n4 >> 1;
        } else if ((n8 & 8) > 0) {
            n6 -= n4;
        }
        if ((n8 & 2) > 0) {
            n7 -= n5 >> 1;
        } else if ((n8 & 0x20) > 0) {
            n7 -= n5;
        }
        graphics.drawRegion(image, n2, n3, n4, n5, 0, n6, n7, 0);
    }

    public static void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        graphics.setColor(0xE7E7E7);
        n2 = n3;
        int n7 = n4;
        int n8 = n3;
        int n9 = n4;
        while (true) {
            graphics.drawLine(n2, n7, n8, n9);
            if (n7 < n4 + n6) {
                n7 += 2;
            } else if ((n2 += 2) > n3 + n5) break;
            if (n8 < n3 + n5) {
                n8 += 2;
                continue;
            }
            if ((n9 += 2) > n4 + n6) break;
        }
    }

    public static void b(Graphics graphics, int n2, int n3, int n4, int n5, int n6) {
        graphics.setColor(n2);
        graphics.fillRect(n3 + 2, n4, n5 - 4, 1);
        graphics.fillRect(n3 + 2, n4 + n6 - 1, n5 - 4, 1);
        graphics.fillRect(n3, n4 + 2, 1, n6 - 4);
        graphics.fillRect(n3 + n5 - 1, n4 + 2, 1, n6 - 4);
        graphics.fillRect(n3 + 1, n4 + 1, 1, 1);
        graphics.fillRect(n3 + 1, n4 + n6 - 2, 1, 1);
        graphics.fillRect(n3 + n5 - 2, n4 + 1, 1, 1);
        graphics.fillRect(n3 + n5 - 2, n4 + n6 - 2, 1, 1);
    }

    public static final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        cz.a(graphics, n2, n3, n4, n5, n6, false, n7, n8);
    }

    public static final void a(Graphics graphics, int n2, int n3, int n4, int n5, int n6, boolean bl2, int n7, int n8) {
        if (n7 >= 0) {
            graphics.setColor(n7);
            graphics.fillRect(n2 + 1, n3 + 2, n4 - 2, n5 - 4);
            graphics.fillRect(n2 + 4, n3 + 1, n4 - 8, 1);
            graphics.fillRect(n2 + 4, n3 + n5 - 2, n4 - 8, 1);
        }
        graphics.setColor(n8);
        graphics.fillRect(n2 + 4, n3, n4 - 8, 1);
        graphics.fillRect(n2 + 4, n3 + n5 - 1, n4 - 8, 1);
        graphics.fillRect(n2 + 2, n3 + 1, 2, 1);
        graphics.fillRect(n2 + n4 - 4, n3 + 1, 2, 1);
        graphics.fillRect(n2 + 2, n3 + n5 - 2, 2, 1);
        graphics.fillRect(n2 + n4 - 4, n3 + n5 - 2, 2, 1);
        graphics.fillRect(n2, n3 + 4, 1, n5 - 8);
        graphics.fillRect(n2 + n4 - 1, n3 + 4, 1, n5 - 8);
        graphics.fillRect(n2 + 1, n3 + 2, 1, 2);
        graphics.fillRect(n2 + 1, n3 + n5 - 4, 1, 2);
        graphics.fillRect(n2 + n4 - 2, n3 + 2, 1, 2);
        graphics.fillRect(n2 + n4 - 2, n3 + n5 - 4, 1, 2);
        if (n6 >= 0) {
            graphics.setColor(n7);
            if (bl2) {
                graphics.fillTriangle(n6 - 5, n3 + 1, n6 + 5, n3 + 1, n6, n3 - 7);
                graphics.setColor(n8);
                graphics.drawLine(n6, n3 - 7, n6 - 5, n3 + 1);
                graphics.drawLine(n6, n3 - 7, n6 + 5, n3 + 1);
                return;
            }
            graphics.fillTriangle(n6 - 5, n3 + n5 - 1, n6 + 5, n3 + n5 - 1, n6, n3 + n5 + 7);
            graphics.setColor(n8);
            graphics.drawLine(n6, n3 + n5 + 7, n6 - 5, n3 + n5 - 1);
            graphics.drawLine(n6, n3 + n5 + 7, n6 + 5, n3 + n5 - 1);
        }
    }
}

