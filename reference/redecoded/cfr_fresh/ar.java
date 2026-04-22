/*
 * Decompiled with CFR 0.152.
 */
public final class ar {
    private int a;

    public final void a(int n2) {
        block3: {
            block2: {
                void var2_2 = null[this.a];
                if (var2_2 != n2) break block2;
                ++this.a;
                if (this.a < (null).length) break block3;
            }
            this.a = 0;
        }
    }
}

