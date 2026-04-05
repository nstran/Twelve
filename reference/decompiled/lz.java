/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class lz {
    private int a;
    private int[][][] b;
    private int[] c;
    private byte[][] d;
    private int e;
    private static a f = new a();

    public lz(int n2) {
        this(n2, ox.a().c(n2));
    }

    private lz(int n2, byte[] byArray) {
        this.a = n2;
        this.a(byArray);
    }

    private void a(byte[] object) {
        try {
            object = new ByteArrayInputStream((byte[])object);
            DataInputStream dataInputStream = new DataInputStream((InputStream)object);
            object = dataInputStream;
            dataInputStream.readByte();
            this.e = ((DataInputStream)object).readInt();
            int n2 = ((DataInputStream)object).readByte();
            this.b = new int[n2][][];
            this.d = new byte[n2][];
            this.c = new int[n2];
            n2 = 0;
            while (n2 < this.b.length) {
                byte by2 = ((DataInputStream)object).readByte();
                this.c[n2] = ((DataInputStream)object).readByte();
                int n3 = ((DataInputStream)object).readByte();
                this.b[by2] = new int[n3][2];
                this.d[by2] = new byte[n3];
                int n4 = 0;
                while (n4 < n3) {
                    this.d[by2][n4] = ((DataInputStream)object).readByte();
                    this.b[by2][n4][0] = ((DataInputStream)object).readShort();
                    this.b[by2][n4][1] = ((DataInputStream)object).readShort();
                    ++n4;
                }
                ++n2;
            }
            ((FilterInputStream)object).close();
            return;
        }
        catch (Exception exception) {
            cw.a(String.valueOf(this.a));
            exception.printStackTrace();
            return;
        }
    }

    private static Image b(int n2) {
        return f.a(ox.a().b(n2));
    }

    private static Image a(int n2, di di2) {
        byte[] byArray = ox.a().b(n2);
        if (di2 != null && di2.d != null && di2.e != null && !di2.d.equals(di2.e)) {
            h.a(byArray, di2.d.c, di2.e.c);
        }
        return f.a(byArray);
    }

    private static Image a(int n2, di di2, di di3) {
        byte[] byArray = ox.a().b(n2);
        if (di2 != null && di2.d != null && di2.e != null && !di2.d.equals(di2.e)) {
            h.a(byArray, di2.d.c, di2.e.c);
        }
        if (di3 != null && di3.d != null && di3.e != null && !di3.d.equals(di3.e)) {
            h.a(byArray, di3.d.c, di3.e.c);
        }
        return f.a(byArray);
    }

    private static ly a(lf lf2, lz object, lz object2, lz object3, lz object4, int n2, int n3, boolean bl2) {
        int n4 = lz.a(lf2, (lz)object, (lz)object2, (lz)object3, (lz)object4, n2);
        ly ly2 = lz.c(n4);
        if (ly2 != null) {
            return ly2;
        }
        int n5 = ((lz)object2).c[n2];
        int n6 = ((lz)object).c[n2];
        int n7 = ((lz)object3).c[n2];
        int n8 = ((lz)object4).c[n2];
        Image image = lz.a(n2 + 99000, lf2.Y);
        int n9 = n2;
        Object lz2 = object2;
        Image image2 = lz.a(((lz)lz2).e + n9, lf2.X, lf2.Y);
        n9 = n2;
        lz2 = object;
        lf2 = lz.a(((lz)lz2).e + n9, lf2.W);
        n9 = n2;
        lz2 = object3;
        Image image3 = lz.b(((lz)lz2).e + n9);
        n9 = n2;
        lz2 = object4;
        lz2 = lz.b(((lz)lz2).e + n9);
        n9 = image.getWidth() / n3;
        n5 = image2.getWidth() / n5;
        n7 = image3.getWidth() / n7;
        n8 = lz2.getWidth() / n8;
        n6 = lf2.getWidth() / n6;
        int n10 = image.getHeight();
        int n11 = image2.getHeight();
        int n12 = lf2.getHeight();
        int n13 = image3.getHeight();
        int n14 = lz2.getHeight();
        byte[] byArray = ((lz)object2).d[n2];
        byte[] byArray2 = ((lz)object).d[n2];
        byte[] byArray3 = ((lz)object3).d[n2];
        byte[] byArray4 = ((lz)object4).d[n2];
        object2 = ((lz)object2).b[n2];
        object = ((lz)object).b[n2];
        object3 = ((lz)object3).b[n2];
        object4 = ((lz)object4).b[n2];
        int[][] nArray = new int[n3][2];
        int[][] nArray2 = new int[n3][2];
        m m2 = new m();
        Image[] imageArray = new Image[n3];
        m2.a = n9;
        m2.b = n10;
        int n15 = 0;
        while (n15 < n3) {
            Object object5;
            Object object6;
            Object object7;
            Object object8;
            boolean n16 = false;
            Object object9 = n9;
            int n17 = false;
            Object object10 = n10;
            if (object2[n15][0] < 0) {
                object8 = object2[n15][0];
            }
            if (object[n15][0] < object8) {
                object7 = object[n15][0];
            }
            if (object3[n15][0] < object7) {
                object6 = object3[n15][0];
            }
            if (object4[n15][0] < object6) {
                object5 = object4[n15][0];
            }
            int n18 = Math.abs((int)object5);
            if (n9 < object2[n15][0] + n5) {
                object9 = object2[n15][0] + n5;
            }
            if (object9 < object[n15][0] + n6) {
                object9 = object[n15][0] + n6;
            }
            if (object9 < object3[n15][0] + n7) {
                object9 = object3[n15][0] + n7;
            }
            if (object9 < object4[n15][0] + n8) {
                object9 = object4[n15][0] + n8;
            }
            nArray[n15][0] = -n18;
            nArray[n15][1] = n9 - object9;
            if (object2[n15][1] < 0) {
                n17 = object2[n15][1];
            }
            if (object[n15][1] < n17) {
                n17 = object[n15][1];
            }
            if (object3[n15][1] < n17) {
                n17 = object3[n15][1];
            }
            if (object4[n15][1] < n17) {
                n17 = object4[n15][1];
            }
            n17 = Math.abs(n17);
            if (n10 < object2[n15][1] + n11) {
                object10 = object2[n15][1] + n11;
            }
            if (object10 < object[n15][1] + n12) {
                object10 = object[n15][1] + n12;
            }
            if (object10 < object3[n15][1] + n13) {
                object10 = object3[n15][1] + n13;
            }
            if (object10 < object4[n15][1] + n14) {
                object10 = object4[n15][1] + n14;
            }
            nArray2[n15][0] = -n17;
            nArray2[n15][1] = n10 - object10;
            object9 = n18 + object9;
            object10 = n17 + object10;
            Image image4 = Image.createImage((int)object9, (int)object10);
            Graphics graphics = image4.getGraphics();
            graphics.setColor(0xFF00FF);
            graphics.fillRect(0, 0, object9, object10);
            cz.a(graphics, image, n15 * n9, 0, n9, n10, n18, n17, 0);
            if (image2 != null) {
                cz.a(graphics, image2, byArray[n15] * n5, 0, n5, n11, n18 + object2[n15][0], n17 + object2[n15][1], 0);
            }
            if (lf2 != null) {
                cz.a(graphics, (Image)lf2, byArray2[n15] * n6, 0, n6, n12, n18 + object[n15][0], n17 + object[n15][1], 0);
            }
            if (lz2 != null) {
                cz.a(graphics, (Image)lz2, byArray4[n15] * n8, 0, n8, n14, n18 + object4[n15][0], n17 + object4[n15][1], 0);
            }
            if (image3 != null) {
                cz.a(graphics, image3, byArray3[n15] * n7, 0, n7, n13, n18 + object3[n15][0], n17 + object3[n15][1], 0);
            }
            int[] nArray3 = new int[object9 * object10];
            image4.getRGB(nArray3, 0, object9, 0, 0, object9, object10);
            n17 = 0;
            while (n17 < nArray3.length) {
                if ((nArray3[n17] & 0xFFFFFF) == 0xFF00FF) {
                    nArray3[n17] = 0;
                }
                ++n17;
            }
            imageArray[n15] = Image.createRGBImage((int[])nArray3, (int)object9, (int)object10, (boolean)true);
            ++n15;
        }
        ly ly3 = new ly(n4, imageArray, nArray, nArray2, n3, m2);
        if (bl2) {
            lz.a(ly3);
        }
        return ly3;
    }

    private static void a(ly ly2) {
        a a2 = f;
        synchronized (a2) {
            int n2 = 0;
            while (n2 < f.d()) {
                ly ly3 = (ly)f.b(n2);
                if (ly3.a == ly2.a) {
                    f.a(ly2, n2);
                    return;
                }
                ++n2;
            }
            f.a(ly2);
            return;
        }
    }

    private static ly c(int n2) {
        a a2 = f;
        synchronized (a2) {
            int n3 = 0;
            while (n3 < f.d()) {
                ly ly2 = (ly)f.b(n3);
                if (ly2.a == n2) {
                    return ly2;
                }
                ++n3;
            }
            return null;
        }
    }

    public static void a() {
        f.a();
    }

    public static me a(lf lf2, lz object, lz lz2, lz lz3, lz lz4, boolean bl2) {
        lf2.b();
        object = !lf2.af ? lz.a(lf2, (lz)object, lz2, lz3, lz4, 0, 2, bl2) : lz.a(lf2, (lz)object, lz2, lz3, lz4, 3, 2, bl2);
        object = new me(((ly)object).b, ((ly)object).c, ((ly)object).d, ((ly)object).e);
        if (!lf2.af) {
            ((aw)object).a(me.u);
        } else {
            ((aw)object).a(md.s);
        }
        if (lf2.af) {
            ((aw)object).d(1);
        } else {
            ((aw)object).d(0);
        }
        return object;
    }

    public static me b(lf lf2, lz object, lz lz2, lz lz3, lz lz4, boolean bl2) {
        object = lz.a(lf2, (lz)object, lz2, lz3, lz4, 1, 6, true);
        object = new me(((ly)object).b, ((ly)object).c, ((ly)object).d, ((ly)object).e);
        ((aw)object).a(me.w);
        if (lf2.af) {
            ((aw)object).d(1);
        } else {
            ((aw)object).d(0);
        }
        return object;
    }

    public static me c(lf lf2, lz object, lz lz2, lz lz3, lz lz4, boolean bl2) {
        object = lz.a(lf2, (lz)object, lz2, lz3, lz4, 2, 4, bl2);
        object = new me(((ly)object).b, ((ly)object).c, ((ly)object).d, ((ly)object).e);
        ((aw)object).a(me.v);
        if (lf2.af) {
            ((aw)object).d(1);
        } else {
            ((aw)object).d(0);
        }
        return object;
    }

    public static me d(lf lf2, lz object, lz lz2, lz lz3, lz lz4, boolean bl2) {
        object = lz.a(lf2, (lz)object, lz2, lz3, lz4, 9, 1, true);
        object = new me(((ly)object).b, ((ly)object).c, ((ly)object).d, ((ly)object).e);
        ((aw)object).a(me.z);
        if (lf2.af) {
            ((aw)object).d(1);
        } else {
            ((aw)object).d(0);
        }
        return object;
    }

    public static me e(lf lf2, lz object, lz lz2, lz lz3, lz lz4, boolean bl2) {
        object = lz.a(lf2, (lz)object, lz2, lz3, lz4, 7, 1, true);
        object = new me(((ly)object).b, ((ly)object).c, ((ly)object).d, ((ly)object).e);
        ((aw)object).a(me.x);
        if (lf2.af) {
            ((aw)object).d(1);
        } else {
            ((aw)object).d(0);
        }
        return object;
    }

    public static me f(lf lf2, lz object, lz lz2, lz lz3, lz lz4, boolean bl2) {
        object = lz.a(lf2, (lz)object, lz2, lz3, lz4, 8, 1, true);
        object = new me(((ly)object).b, ((ly)object).c, ((ly)object).d, ((ly)object).e);
        ((aw)object).a(me.y);
        if (lf2.af) {
            ((aw)object).d(1);
        } else {
            ((aw)object).d(0);
        }
        return object;
    }

    public static ma a(lf lf2, Image image, lz object, lz object2, lz object3, lz object4, boolean bl2) {
        int n2 = 3;
        n2 = 6;
        Object object5 = lf2;
        int n3 = lz.a((lf)object5, (lz)object, (lz)object2, (lz)object3, (lz)object4, 6);
        ly ly2 = lz.c(n3);
        if (ly2 == null) {
            int n4 = ((lz)object2).c[6];
            int n5 = ((lz)object).c[6];
            int n6 = ((lz)object3).c[6];
            int n7 = ((lz)object4).c[6];
            Image image2 = lz.a(99006, ((lf)object5).Y);
            int n8 = 6;
            Object lz2 = object2;
            Image image3 = lz.a(((lz)lz2).e + n8, ((lf)object5).X, ((lf)object5).Y);
            n8 = 6;
            lz2 = object;
            object5 = lz.a(((lz)lz2).e + n8, ((lf)object5).W);
            n8 = 6;
            lz2 = object3;
            Image image4 = lz.b(((lz)lz2).e + n8);
            n8 = 6;
            lz2 = object4;
            lz2 = lz.b(((lz)lz2).e + n8);
            n8 = image2.getWidth() / 3;
            n4 = image3.getWidth() / n4;
            n6 = image4.getWidth() / n6;
            n7 = lz2.getWidth() / n7;
            n5 = object5.getWidth() / n5;
            int n9 = image2.getHeight();
            int n10 = image3.getHeight();
            int n11 = object5.getHeight();
            int n12 = image4.getHeight();
            int n13 = lz2.getHeight();
            byte[] byArray = ((lz)object2).d[6];
            byte[] byArray2 = ((lz)object).d[6];
            byte[] byArray3 = ((lz)object3).d[6];
            byte[] byArray4 = ((lz)object4).d[6];
            object2 = ((lz)object2).b[6];
            object = ((lz)object).b[6];
            object3 = ((lz)object3).b[6];
            object4 = ((lz)object4).b[6];
            int[][] nArray = new int[3][2];
            int[][] nArray2 = new int[3][2];
            m m2 = new m();
            Image[] imageArray = new Image[3];
            m2.a = n8;
            m2.b = n9;
            int n14 = 0;
            while (n14 < 3) {
                Object object6;
                Object object7;
                Object object8;
                Object object9;
                boolean n15 = false;
                Object object10 = n8;
                int n16 = false;
                Object object11 = n9;
                if (object2[n14][0] < 0) {
                    object9 = object2[n14][0];
                }
                if (object[n14][0] < object9) {
                    object8 = object[n14][0];
                }
                if (object3[n14][0] < object8) {
                    object7 = object3[n14][0];
                }
                if (object4[n14][0] < object7) {
                    object6 = object4[n14][0];
                }
                int n17 = Math.abs((int)object6);
                if (n8 < object2[n14][0] + n4) {
                    object10 = object2[n14][0] + n4;
                }
                if (object10 < object[n14][0] + n5) {
                    object10 = object[n14][0] + n5;
                }
                if (object10 < object3[n14][0] + n6) {
                    object10 = object3[n14][0] + n6;
                }
                if (object10 < object4[n14][0] + n7) {
                    object10 = object4[n14][0] + n7;
                }
                nArray[n14][0] = -n17;
                nArray[n14][1] = n8 - object10;
                if (object2[n14][1] < 0) {
                    n16 = object2[n14][1];
                }
                if (object[n14][1] < n16) {
                    n16 = object[n14][1];
                }
                if (object3[n14][1] < n16) {
                    n16 = object3[n14][1];
                }
                if (object4[n14][1] < n16) {
                    n16 = object4[n14][1];
                }
                n16 = Math.abs(n16);
                if (n9 < object2[n14][1] + n10) {
                    object11 = object2[n14][1] + n10;
                }
                if (object11 < object[n14][1] + n11) {
                    object11 = object[n14][1] + n11;
                }
                if (object11 < object3[n14][1] + n12) {
                    object11 = object3[n14][1] + n12;
                }
                if (object11 < object4[n14][1] + n13) {
                    object11 = object4[n14][1] + n13;
                }
                nArray2[n14][0] = -n16;
                nArray2[n14][1] = n9 - object11;
                object10 = n17 + object10;
                object11 = n16 + object11;
                Image image5 = Image.createImage((int)object10, (int)object11);
                Graphics graphics = image5.getGraphics();
                graphics.setColor(0xFF00FF);
                graphics.fillRect(0, 0, object10, object11);
                cz.a(graphics, image2, n14 * n8, 0, n8, n9, n17, n16, 0);
                if (image3 != null) {
                    cz.a(graphics, image3, byArray[n14] * n4, 0, n4, n10, n17 + object2[n14][0], n16 + object2[n14][1], 0);
                }
                if (object5 != null) {
                    cz.a(graphics, (Image)object5, byArray2[n14] * n5, 0, n5, n11, n17 + object[n14][0], n16 + object[n14][1], 0);
                }
                if (lz2 != null) {
                    cz.a(graphics, (Image)lz2, byArray4[n14] * n7, 0, n7, n13, n17 + object4[n14][0], n16 + object4[n14][1], 0);
                }
                if (image4 != null) {
                    cz.a(graphics, image4, byArray3[n14] * n6, 0, n6, n12, n17 + object3[n14][0], n16 + object3[n14][1], 0);
                }
                int[] nArray3 = new int[object10 * object11];
                image5.getRGB(nArray3, 0, object10, 0, 0, object10, object11);
                n16 = 0;
                while (n16 < nArray3.length) {
                    if ((nArray3[n16] & 0xFFFFFF) == 0xFF00FF) {
                        nArray3[n16] = 0;
                    }
                    ++n16;
                }
                imageArray[n14] = Image.createRGBImage((int[])nArray3, (int)object10, (int)object11, (boolean)true);
                ++n14;
            }
            ly2 = new ly(n3, imageArray, nArray, nArray2, 3, m2);
            if (bl2) {
                lz.a(ly2);
            }
        }
        object5 = ly2;
        object5 = new ma(((ly)object5).b, ((ly)object5).c, ((ly)object5).d, ((ly)object5).e, image);
        ((aw)object5).a(ma.s);
        return object5;
    }

    public static md g(lf object, lz lz2, lz lz3, lz lz4, lz lz5, boolean bl2) {
        object = lz.a((lf)object, lz2, lz3, lz4, lz5, 3, 2, true);
        object = new md(((ly)object).b, ((ly)object).c, ((ly)object).d, ((ly)object).e);
        ((aw)object).a(md.s);
        return object;
    }

    public static mc h(lf object, lz lz2, lz lz3, lz lz4, lz lz5, boolean bl2) {
        object = lz.a((lf)object, lz2, lz3, lz4, lz5, 4, 3, bl2);
        object = new mc(((ly)object).b, ((ly)object).c, ((ly)object).d, ((ly)object).e);
        ((aw)object).a(mc.s);
        return object;
    }

    public static mb i(lf lf2, lz object, lz object2, lz object3, lz object4, boolean bl2) {
        bl2 = true;
        int n2 = 4;
        n2 = 5;
        Object object5 = lf2;
        int n3 = lz.a((lf)object5, (lz)object, (lz)object2, (lz)object3, (lz)object4, 5);
        ly ly2 = lz.c(n3);
        if (ly2 == null) {
            int n4 = ((lz)object2).c[5];
            int n5 = ((lz)object).c[5];
            int n6 = ((lz)object3).c[5];
            int n7 = ((lz)object4).c[5];
            Image image = lz.a(99005, ((lf)object5).Y);
            int n8 = 5;
            Object lz2 = object2;
            Image image2 = lz.a(((lz)lz2).e + n8, ((lf)object5).X, ((lf)object5).Y);
            n8 = 5;
            lz2 = object;
            object5 = lz.a(((lz)lz2).e + n8, ((lf)object5).W);
            n8 = 5;
            lz2 = object3;
            Image image3 = lz.b(((lz)lz2).e + n8);
            n8 = 5;
            lz2 = object4;
            lz2 = lz.b(((lz)lz2).e + n8);
            n8 = image.getWidth() / 4;
            n4 = image2.getWidth() / n4;
            n6 = image3.getWidth() / n6;
            n7 = lz2.getWidth() / n7;
            n5 = object5.getWidth() / n5;
            int n9 = image.getHeight();
            int n10 = image2.getHeight();
            int n11 = object5.getHeight();
            int n12 = image3.getHeight();
            int n13 = lz2.getHeight();
            byte[] byArray = ((lz)object2).d[5];
            byte[] byArray2 = ((lz)object).d[5];
            byte[] byArray3 = ((lz)object3).d[5];
            byte[] byArray4 = ((lz)object4).d[5];
            object2 = ((lz)object2).b[5];
            object = ((lz)object).b[5];
            object3 = ((lz)object3).b[5];
            object4 = ((lz)object4).b[5];
            int[][] nArray = new int[4][2];
            int[][] nArray2 = new int[4][2];
            m m2 = new m();
            Image[] imageArray = new Image[4];
            m2.a = n8;
            m2.b = n9;
            int n14 = 0;
            while (n14 < 4) {
                Object object6;
                Object object7;
                Object object8;
                Object object9;
                boolean n15 = false;
                Object object10 = n8;
                int n16 = false;
                Object object11 = n9;
                if (object2[n14][0] < 0) {
                    object9 = object2[n14][0];
                }
                if (object[n14][0] < object9) {
                    object8 = object[n14][0];
                }
                if (object3[n14][0] < object8) {
                    object7 = object3[n14][0];
                }
                if (object4[n14][0] < object7) {
                    object6 = object4[n14][0];
                }
                int n17 = Math.abs((int)object6);
                if (n8 < object2[n14][0] + n4) {
                    object10 = object2[n14][0] + n4;
                }
                if (object10 < object[n14][0] + n5) {
                    object10 = object[n14][0] + n5;
                }
                if (object10 < object3[n14][0] + n6) {
                    object10 = object3[n14][0] + n6;
                }
                if (object10 < object4[n14][0] + n7) {
                    object10 = object4[n14][0] + n7;
                }
                nArray[n14][0] = -n17;
                nArray[n14][1] = n8 - object10;
                if (object2[n14][1] < 0) {
                    n16 = object2[n14][1];
                }
                if (object[n14][1] < n16) {
                    n16 = object[n14][1];
                }
                if (object3[n14][1] < n16) {
                    n16 = object3[n14][1];
                }
                if (object4[n14][1] < n16) {
                    n16 = object4[n14][1];
                }
                n16 = Math.abs(n16);
                if (n9 < object2[n14][1] + n10) {
                    object11 = object2[n14][1] + n10;
                }
                if (object11 < object[n14][1] + n11) {
                    object11 = object[n14][1] + n11;
                }
                if (object11 < object3[n14][1] + n12) {
                    object11 = object3[n14][1] + n12;
                }
                if (object11 < object4[n14][1] + n13) {
                    object11 = object4[n14][1] + n13;
                }
                nArray2[n14][0] = -n16;
                nArray2[n14][1] = n9 - object11;
                object10 = n17 + object10;
                object11 = n16 + object11;
                Image image4 = Image.createImage((int)object10, (int)object11);
                Graphics graphics = image4.getGraphics();
                graphics.setColor(0xFF00FF);
                graphics.fillRect(0, 0, object10, object11);
                cz.a(graphics, image, n14 * n8, 0, n8, n9, n17, n16, 0);
                if (image2 != null) {
                    cz.a(graphics, image2, byArray[n14] * n4, 0, n4, n10, n17 + object2[n14][0], n16 + object2[n14][1], 0);
                }
                if (object5 != null) {
                    cz.a(graphics, (Image)object5, byArray2[n14] * n5, 0, n5, n11, n17 + object[n14][0], n16 + object[n14][1], 0);
                }
                if (lz2 != null) {
                    cz.a(graphics, (Image)lz2, byArray4[n14] * n7, 0, n7, n13, n17 + object4[n14][0], n16 + object4[n14][1], 0);
                }
                if (image3 != null) {
                    cz.a(graphics, image3, byArray3[n14] * n6, 0, n6, n12, n17 + object3[n14][0], n16 + object3[n14][1], 0);
                }
                int[] nArray3 = new int[object10 * object11];
                image4.getRGB(nArray3, 0, object10, 0, 0, object10, object11);
                n16 = 0;
                while (n16 < nArray3.length) {
                    if ((nArray3[n16] & 0xFFFFFF) == 0xFF00FF) {
                        nArray3[n16] = 0;
                    }
                    ++n16;
                }
                imageArray[n14] = Image.createRGBImage((int[])nArray3, (int)object10, (int)object11, (boolean)true);
                ++n14;
            }
            ly2 = new ly(n3, imageArray, nArray, nArray2, 4, m2);
            if (bl2) {
                lz.a(ly2);
            }
        }
        object5 = ly2;
        object5 = new mb(((ly)object5).b, ((ly)object5).c, ((ly)object5).d, ((ly)object5).e);
        ((aw)object5).a(mb.s);
        return object5;
    }

    public static lz[] a(lf lf2) {
        int n2;
        lz[] lzArray;
        int[] nArray = null;
        if (lf2.D != null) {
            nArray = new int[4];
            lzArray = null;
            n2 = 0;
            while (n2 < lf2.D.length) {
                lzArray = lf2.D[n2];
                if (lzArray.d < nArray.length) {
                    int n3 = lzArray.m;
                    nArray[lzArray.d] = n3 - n3 % 10;
                }
                ++n2;
            }
        } else {
            cw.a("[BodyPartLoader.loadMetadata()] charaterInfo.equipments is Null");
            return new lz[0];
        }
        lz[] lzArray2 = new lz[4];
        lzArray = lzArray2;
        lzArray2[1] = new lz(lf2.X.a + 99);
        int n4 = lf2.W.a + 99;
        n2 = lf2.f == 1 ? 79999 : 79899;
        lzArray[0] = nArray[0] > 0 && !lf2.ab ? new lz(nArray[0] + 99) : new lz(n4);
        lzArray[2] = nArray[1] > 0 ? new lz(nArray[1] + 99) : new lz(n2);
        lzArray[3] = nArray[2] > 0 ? new lz(nArray[2] + 99) : new lz(89999);
        return lzArray;
    }

    public static me a(lf lf2, boolean bl2) {
        lz[] lzArray = lz.a(lf2);
        return lz.a(lf2, lzArray[0], lzArray[1], lzArray[3], lzArray[2], false);
    }

    private static StringBuffer a(StringBuffer stringBuffer, int[] nArray) {
        if (nArray != null && nArray.length > 0) {
            int n2 = 0;
            while (n2 < nArray.length) {
                stringBuffer.append(nArray[n2]);
                ++n2;
            }
        }
        return stringBuffer;
    }

    private static int a(lf object, lz lz2, lz lz3, lz lz4, lz lz5, int n2) {
        int n3;
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append(n2).append(':');
        lz.a(stringBuffer, ((lf)object).Y.e.c).append(':');
        if (lz3 != null) {
            n3 = n2;
            stringBuffer.append(lz3.e + n3).append(':');
            if (((lf)object).X != null) {
                lz.a(stringBuffer, ((lf)object).X.e.c).append(':');
            }
        }
        if (lz2 != null) {
            n3 = n2;
            lz3 = lz2;
            stringBuffer.append(lz3.e + n3).append(':');
            if (((lf)object).W != null) {
                lz.a(stringBuffer, ((lf)object).W.e.c).append(':');
            }
        }
        if (lz4 != null) {
            n3 = n2;
            lz3 = lz4;
            stringBuffer.append(lz3.e + n3).append(':');
        }
        if (lz5 != null) {
            n3 = n2;
            lz3 = lz5;
            stringBuffer.append(lz3.e + n3).append(':');
        }
        object = stringBuffer.toString();
        return ((String)object).hashCode();
    }

    public static int a(lj lj2) {
        int n2 = lj2.m;
        return n2 - n2 % 10;
    }

    public static int a(int n2) {
        return n2 - n2 % 10 + 98;
    }
}

