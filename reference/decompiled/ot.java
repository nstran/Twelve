/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ot {
    private static int[][] a;
    private static int[][] b;
    private static int[][] c;
    private static int[][] d;
    private static int[][] e;
    private static int[][] f;

    static {
        int[][] nArrayArray = new int[13][];
        nArrayArray[0] = new int[2];
        nArrayArray[1] = new int[]{229, 1};
        nArrayArray[2] = new int[]{229, 254};
        nArrayArray[3] = new int[]{228, 255};
        nArrayArray[4] = new int[]{145, 255};
        nArrayArray[5] = new int[]{144, 254};
        nArrayArray[6] = new int[]{144, 230};
        nArrayArray[7] = new int[]{85, 230};
        nArrayArray[8] = new int[]{85, 254};
        nArrayArray[9] = new int[]{84, 255};
        nArrayArray[10] = new int[]{1, 255};
        int[] nArray = new int[2];
        nArray[1] = 254;
        nArrayArray[11] = nArray;
        int[] nArray2 = new int[2];
        nArray2[1] = 1;
        nArrayArray[12] = nArray2;
        a = nArrayArray;
        b = new int[][]{{1, 229}, {1, 253}, {2, 254}, {83, 254}, {84, 253}, {84, 229}};
        c = new int[][]{{228, 229}, {228, 253}, {227, 254}, {146, 254}, {145, 253}, {145, 229}};
        int[][] nArrayArray2 = new int[12][];
        int[] nArray3 = new int[2];
        nArray3[0] = 1;
        nArrayArray2[0] = nArray3;
        int[] nArray4 = new int[2];
        nArray4[0] = 280;
        nArrayArray2[1] = nArray4;
        nArrayArray2[2] = new int[]{281, 1};
        nArrayArray2[3] = new int[]{281, 84};
        nArrayArray2[4] = new int[]{280, 85};
        nArrayArray2[5] = new int[]{255, 85};
        nArrayArray2[6] = new int[]{255, 229};
        nArrayArray2[7] = new int[]{26, 229};
        nArrayArray2[8] = new int[]{26, 85};
        nArrayArray2[9] = new int[]{1, 85};
        int[] nArray5 = new int[2];
        nArray5[1] = 84;
        nArrayArray2[10] = nArray5;
        int[] nArray6 = new int[2];
        nArray6[1] = 1;
        nArrayArray2[11] = nArray6;
        d = nArrayArray2;
        e = new int[][]{{26, 1}, {2, 1}, {1, 2}, {1, 83}, {2, 84}, {27, 84}};
        f = new int[][]{{255, 1}, {279, 1}, {280, 2}, {280, 83}, {279, 84}, {255, 84}};
    }

    public static void a(int n2, int n3, Graphics graphics) {
        ot.c((n2 += 5) + 3, ++n3 + 3, graphics);
        graphics.setColor(0x5A5A5A);
        graphics.fillRect(n2 + 3, n3 + 230, 81, 24);
        graphics.fillRect(n2 + 147, n3 + 230, 81, 24);
        graphics.setColor(0x9933FF);
        ot.a(n2, n3, a, graphics);
        graphics.setColor(52479);
        graphics.drawRect(n2 + 1, n3 + 1, 228, 228);
        ot.a(n2, n3, b, graphics);
        ot.a(n2, n3, c, graphics);
        graphics.setColor(230911);
        graphics.drawRect(n2 + 2, n3 + 2, 226, 226);
        graphics.drawRect(n2 + 2, n3 + 229, 82, 25);
        graphics.drawRect(n2 + 146, n3 + 229, 82, 25);
        ot.a(n2 + 5, n3 + 232, graphics, false);
        ot.a(n2 + 5, n3 + 239, graphics, true);
        ot.a(n2 + 5, n3 + 246, graphics, false);
        ot.a(n2 + 149, n3 + 232, graphics, false);
        ot.a(n2 + 149, n3 + 239, graphics, true);
        ot.a(n2 + 149, n3 + 246, graphics, false);
    }

    public static void b(int n2, int n3, Graphics graphics) {
        ot.c((n2 += 19) + 29, ++n3 + 3, graphics);
        graphics.setColor(0x5A5A5A);
        graphics.fillRect(n2 + 3, n3 + 3, 24, 81);
        graphics.fillRect(n2 + 256, n3 + 3, 24, 81);
        graphics.setColor(0x9933FF);
        ot.a(n2, n3, d, graphics);
        graphics.setColor(52479);
        graphics.drawRect(n2 + 27, n3 + 1, 228, 228);
        ot.a(n2, n3, e, graphics);
        ot.a(n2, n3, f, graphics);
        graphics.setColor(230911);
        graphics.drawRect(n2 + 28, n3 + 2, 226, 226);
        graphics.drawRect(n2 + 2, n3 + 2, 25, 82);
        graphics.drawRect(n2 + 255, n3 + 2, 25, 82);
        ot.b(n2 + 5, n3 + 5, graphics, false);
        ot.b(n2 + 12, n3 + 5, graphics, true);
        ot.b(n2 + 19, n3 + 5, graphics, false);
        ot.b(n2 + 258, n3 + 5, graphics, false);
        ot.b(n2 + 265, n3 + 5, graphics, true);
        ot.b(n2 + 272, n3 + 5, graphics, false);
    }

    private static void a(int n2, int n3, Graphics graphics, boolean bl2) {
        graphics.drawLine(n2 + 1, n3, n2 + 74, n3);
        graphics.drawLine(n2 + 75, n3 + 1, n2 + 75, n3 + 3);
        graphics.drawLine(n2 + 1, n3 + 4, n2 + 74, n3 + 4);
        graphics.drawLine(n2, n3 + 1, n2, n3 + 3);
        if (bl2) {
            graphics.drawLine(n2 + 18, n3 + 1, n2 + 18, n3 + 3);
            graphics.drawLine(n2 + 37, n3 + 1, n2 + 37, n3 + 3);
            graphics.drawLine(n2 + 56, n3 + 1, n2 + 56, n3 + 3);
        }
    }

    private static void b(int n2, int n3, Graphics graphics, boolean bl2) {
        graphics.drawLine(n2, n3 + 1, n2, n3 + 74);
        graphics.drawLine(n2 + 1, n3 + 75, n2 + 3, n3 + 75);
        graphics.drawLine(n2 + 4, n3 + 1, n2 + 4, n3 + 74);
        graphics.drawLine(n2 + 1, n3, n2 + 3, n3);
        if (bl2) {
            graphics.drawLine(n2 + 1, n3 + 18, n2 + 3, n3 + 18);
            graphics.drawLine(n2 + 1, n3 + 37, n2 + 3, n3 + 37);
            graphics.drawLine(n2 + 1, n3 + 56, n2 + 3, n3 + 56);
        }
    }

    private static void c(int n2, int n3, Graphics graphics) {
        int n4 = 0;
        while (n4 < 8) {
            int n5 = 0;
            while (n5 < 8) {
                if (n4 % 2 == 0 && n5 % 2 == 0 || n4 % 2 == 1 && n5 % 2 == 1) {
                    graphics.setColor(0x474747);
                } else {
                    graphics.setColor(0x3A3A3A);
                }
                graphics.fillRect(n2 + n4 * 28, n3 + n5 * 28, 28, 28);
                ++n5;
            }
            ++n4;
        }
    }

    private static void a(int n2, int n3, int[][] nArray, Graphics graphics) {
        int n4 = 0;
        while (n4 < nArray.length - 1) {
            graphics.drawLine(n2 + nArray[n4][0], n3 + nArray[n4][1], n2 + nArray[n4 + 1][0], n3 + nArray[n4 + 1][1]);
            ++n4;
        }
    }
}

