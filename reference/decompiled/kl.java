/*
 * Decompiled with CFR 0.152.
 */
public final class kl
extends ju {
    private boolean[] a = new boolean[5];
    private int b = 0;
    private int c = 0;

    /*
     * Unable to fully structure code
     */
    public final void a(ax var1_1, kg var2_2) {
        block114: {
            if ((var1_1 = (kk)var1_1) == null) break block114;
            var1_1.i();
            var3_3 = 0;
            var4_9 = 0;
            var5_10 = 0;
            var5_10 = 0;
            var6_11 = (ke)var2_2.a(1);
            var7_12 = var1_1.s;
            switch (var1_1.b()) {
                case 5: {
                    var7_12.b -= var1_1.k;
                    var1_1.v += var1_1.k;
                    --var1_1.k;
                    if (var1_1.k == 0) {
                        var1_1.a(6);
                        break;
                    }
                    var3_3 = (var1_1.o() + 20) / 32;
                    if (!kg.a(var6_11.b(var3_3, var4_9 = (var7_12.a + var7_12.c / 2) / 32))) {
                        var1_1.a(6);
                    }
                    this.a((kk)var1_1, var7_12, var6_11, var2_2);
                    break;
                }
                case 6: {
                    var7_12.b += var1_1.k;
                    var1_1.k += 2;
                    if (var1_1.k > var1_1.a) {
                        var1_1.k = var1_1.a;
                    }
                    var8_13 = (var7_12.b + var7_12.d - var1_1.a) / 32;
                    var3_3 = (var7_12.b + var7_12.d) / 32;
                    var4_9 = var7_12.a / 32;
                    var5_10 = (var7_12.a + var7_12.c) / 32;
                    if (kg.l(var6_11.b(var3_3, var5_10))) {
                        var4_9 = var1_1.j;
                        if (var4_9 == 4) {
                            var4_9 = var1_1.j | 2;
                        } else if (var4_9 == 8) {
                            var4_9 = var1_1.j | 1;
                        }
                        var1_1.a(7, var4_9);
                        var5_10 = 32 - (var7_12.a + var7_12.c) % 32;
                        var1_1.c(var7_12.a, (var3_3 << 5) - var7_12.d + var5_10);
                        break;
                    }
                    if (kg.d(var6_11.b(var3_3, var4_9))) {
                        var4_9 = var1_1.j;
                        if (var4_9 == 4) {
                            var4_9 = var1_1.j | 1;
                        } else if (var4_9 == 8) {
                            var4_9 = var1_1.j | 8;
                        }
                        var1_1.a(7, var4_9);
                        var1_1.c(var7_12.a, (var3_3 << 5) - var7_12.d + var7_12.a % 32);
                        break;
                    }
                    var4_9 = (var7_12.a + 5) / 32;
                    var5_10 = (var7_12.a + var7_12.c - 5) / 32;
                    if (kg.c(var6_11.b(var3_3, var4_9))) {
                        if (var8_13 == var3_3) break;
                        var1_1.a(7);
                        var1_1.c(var7_12.a, (var3_3 - 1 << 5) - (var7_12.d - 32));
                        break;
                    }
                    if (kg.c(var6_11.b(var3_3, var5_10))) {
                        if (var8_13 == var3_3) break;
                        var1_1.a(7);
                        var1_1.c(var7_12.a, (var3_3 - 1 << 5) - (var7_12.d - 32));
                        break;
                    }
                    this.a((kk)var1_1, var7_12, var6_11, var2_2);
                    break;
                }
                case 7: {
                    if (var1_1.a()) {
                        var1_1.a(0);
                    }
                    this.a(var7_12, var6_11, var2_2, (kk)var1_1);
                    if (!((var1_1.j & 4) != 0 ? kl.a(var7_12, (kk)var1_1, var6_11, var2_2, true) != false : (var1_1.j & 8) != 0 && kl.b(var7_12, (kk)var1_1, var6_11, var2_2, true) != false)) break;
                    return;
                }
                case 8: {
                    if (var1_1.j != 1 || kg.b(var6_11.b(var3_3 = (var7_12.b + 10) / 32, var4_9 = (var7_12.a + var7_12.c / 2) / 32))) break;
                    this.b = var7_12.b + var7_12.d - (var3_3 + 1 << 5);
                    this.c = this.b / 3;
                    break;
                }
                case 2: {
                    if (this.a[0]) {
                        var1_1.u = true;
                        if (var1_1.j == 2) {
                            var1_1.j = 1;
                            var1_1.f.s();
                        }
                        var1_1.b(0, var1_1.h * kk.c[1]);
                        var3_3 = (var7_12.b + 10) / 32;
                        var4_9 = (var7_12.a + var7_12.c / 2) / 32;
                        if (!kg.b(var6_11.b(var3_3, var4_9))) {
                            this.b = var7_12.b + var7_12.d - (var3_3 + 1 << 5);
                            this.c = this.b / 3;
                            var1_1.a(3);
                        } else {
                            var3_3 = (var7_12.b + var7_12.d) / 32;
                            var5_10 = var7_12.b / 32;
                            if (kg.b(var6_11.b(var5_10, var4_9)) && kg.c(var6_11.b(var3_3, var4_9))) {
                                this.b = var7_12.b + var7_12.d - (var3_3 + 1 << 5);
                                this.c = this.b / 3;
                                var1_1.a(3);
                            }
                        }
                    } else if (this.a[1]) {
                        var1_1.u = true;
                        if (var1_1.j == 1) {
                            var1_1.j = 2;
                            var1_1.f.t();
                        }
                        var1_1.b(0, var1_1.h * kk.c[2]);
                        var3_3 = (var7_12.b + var7_12.d) / 32;
                        var4_9 = (var7_12.a + var7_12.c / 2) / 32;
                        if (!kg.b(var6_11.b(var3_3, var4_9))) {
                            var1_1.g(var3_3 - 1 << 5);
                            var1_1.a(3);
                        } else if (kg.c(var6_11.b(var3_3, var4_9))) {
                            var1_1.g(var3_3 - 1 << 5);
                            var1_1.a(3);
                        }
                    } else {
                        var1_1.u = false;
                    }
                    if (this.a[2]) {
                        this.a[1] = false;
                        this.a[0] = false;
                        var1_1.f(var7_12.a - 16);
                        var1_1.a(6, 4);
                        break;
                    }
                    if (!this.a[3]) break;
                    this.a[1] = false;
                    this.a[0] = false;
                    var1_1.f(var7_12.a + 16);
                    var1_1.a(6, 8);
                    break;
                }
                case 3: {
                    if (var1_1.j == 1) {
                        var7_12.b += this.c * kk.c[var1_1.j];
                    }
                    if (!var1_1.a()) break;
                    this.c = 0;
                    this.b = 0;
                    var3_3 = var7_12.b / 32;
                    var1_1.g(var3_3 << 5);
                    var1_1.a(0);
                    var3_4 = this;
                    var3_4.a();
                    z.c();
                    var3_5 = (var7_12.b + var7_12.d) / 32;
                    var4_9 = (var7_12.a + var7_12.c / 2) / 32;
                    if (kg.c(var6_11.b(var3_5, var4_9))) break;
                    var1_1.a(6);
                    break;
                }
                case 1: {
                    if (this.a[0]) {
                        if (!var1_1.x) {
                            var3_3 = (var7_12.b + var7_12.d / 2) / 32;
                            var4_9 = (var7_12.a + var7_12.c / 2) / 32;
                            if (kg.b(var6_11.b(var3_3, var4_9))) {
                                var1_1.f((var4_9 << 5) + (32 - var7_12.c) / 2);
                                var1_1.a(8, 1);
                                this.a[3] = false;
                                this.a[2] = false;
                            } else {
                                var3_3 = (var1_1.o() + 20) / 32;
                                if (kg.a(var6_11.b(var3_3, var4_9 = (var7_12.a + var7_12.c / 2) / 32))) {
                                    var1_1.a(5);
                                    this.a[0] = false;
                                }
                            }
                        } else if (var1_1.j != 1) {
                            var1_1.a(1, 1);
                        }
                    } else if (this.a[1]) {
                        var3_3 = (var7_12.b + var7_12.d + 8) / 32;
                        var4_9 = (var7_12.a + var7_12.c / 2) / 32;
                        if (var1_1.x) {
                            if (!kg.c(var6_11.b(var3_3, var4_9)) || kg.b(var6_11.b(var3_3, var4_9))) {
                                if (var1_1.j != 2) {
                                    var1_1.a(1, 2);
                                }
                            } else {
                                var1_1.a(0);
                            }
                        }
                    }
                    if (!this.a[2]) ** GOTO lbl236
                    if (var1_1.j != 4) ** GOTO lbl198
                    if (kl.a(var7_12, (kk)var1_1, var6_11, var2_2, true)) break;
                    var3_3 = (var7_12.b + var7_12.d) / 32;
                    var4_9 = (var7_12.a + var7_12.c - 5) / 32;
                    var5_10 = (var7_12.a + 5) / 32;
                    if (!kg.c(var6_11.b(var3_3, var5_10)) && !kg.c(var6_11.b(var3_3, var4_9))) {
                        if (!var1_1.x) {
                            var1_1.a(6);
                        }
                    } else {
                        var3_3 = (var7_12.b + var7_12.d) / 32;
                        var4_9 = (var7_12.a + var7_12.c - var1_1.h) / 32;
                        v0 = var6_11.b(var3_3, var4_9);
                        var5_10 = v0;
                        if (kg.l(v0)) {
                            var1_1.c((var4_9 + 1 << 5) - var7_12.c, (var3_3 << 5) - var7_12.d);
                            var1_1.a(1, 6);
                        } else {
                            var3_3 = (var7_12.b + var7_12.d - var1_1.h) / 32;
                            var4_9 = (var7_12.a - var1_1.h) / 32;
                            v1 = var6_11.b(var3_3, var4_9);
                            var5_10 = v1;
                            if (kg.d(v1)) {
                                var1_1.c(var4_9 + 1 << 5, (var3_3 << 5) - (var7_12.d - 32));
                                var1_1.a(1, 5);
                            }
                        }
                    }
                    ** GOTO lbl298
lbl198:
                    // 1 sources

                    if (var1_1.j != 5) ** GOTO lbl213
                    var3_3 = (var7_12.b + var7_12.d) / 32;
                    var4_9 = var7_12.a / 32;
                    var5_10 = (var7_12.b + var7_12.d - var1_1.h) / 32;
                    var8_14 = (var7_12.a - var1_1.h) / 32;
                    if (var3_3 != var5_10) {
                        if (var4_9 == var8_14) {
                            if (!kg.d(var6_11.b(var5_10, var8_14 - 1)) && !kg.d(var6_11.b(var5_10, var8_14))) {
                                var1_1.c(var8_14 << 5, (var5_10 + 1 << 5) - var7_12.d);
                                var1_1.a(1, 4);
                            }
                        } else if (!kg.d(var6_11.b(var5_10, var8_14))) {
                            var1_1.c(var4_9 << 5, (var5_10 + 1 << 5) - var7_12.d);
                            var1_1.a(1, 4);
                        }
                    }
                    ** GOTO lbl298
lbl213:
                    // 1 sources

                    if (var1_1.j != 6) ** GOTO lbl228
                    var3_3 = (var7_12.b + var7_12.d) / 32;
                    var4_9 = (var7_12.a + var7_12.c) / 32;
                    var5_10 = (var7_12.b + var7_12.d + var1_1.h) / 32;
                    var8_14 = (var7_12.a + var7_12.c - var1_1.h) / 32;
                    if (var3_3 != var5_10) {
                        if (var4_9 == var8_14) {
                            if (!kg.l(var6_11.b(var5_10, var8_14 - 1)) && !kg.l(var6_11.b(var5_10, var8_14))) {
                                var1_1.c((var8_14 << 5) - var7_12.c, (var5_10 << 5) - var7_12.d);
                                var1_1.a(1, 4);
                            }
                        } else if (!kg.l(var6_11.b(var5_10, var8_14))) {
                            var1_1.c((var8_14 + 1 << 5) - var7_12.c, (var5_10 << 5) - var7_12.d);
                            var1_1.a(1, 4);
                        }
                    }
                    ** GOTO lbl298
lbl228:
                    // 1 sources

                    if (var1_1.j == 10) {
                        var1_1.a(1, 5);
                        break;
                    }
                    if (var1_1.j == 9) {
                        var1_1.a(1, 6);
                        break;
                    }
                    var1_1.a(1, 4);
                    break;
lbl236:
                    // 1 sources

                    if (!this.a[3]) ** GOTO lbl296
                    if (kl.b(var7_12, (kk)var1_1, var6_11, var2_2, true)) break;
                    if (var1_1.j == 8) {
                        var3_3 = (var7_12.b + var7_12.d) / 32;
                        var4_9 = (var7_12.a + 5) / 32;
                        var5_10 = (var7_12.a + var7_12.c - 5) / 32;
                        if (!kg.c(var6_11.b(var3_3, var5_10)) && !kg.c(var6_11.b(var3_3, var4_9))) {
                            if (!var1_1.x) {
                                var1_1.a(6);
                            }
                        } else {
                            var3_3 = (var7_12.b + var7_12.d + var1_1.h) / 32;
                            var4_9 = (var7_12.a + var1_1.h) / 32;
                            v2 = var6_11.b(var3_3, var4_9);
                            var5_10 = v2;
                            if (kg.d(v2)) {
                                var1_1.c((var4_9 << 5) + 1, (var3_3 << 5) - var7_12.d + 1);
                                var1_1.a(1, 10);
                            } else {
                                var3_3 = (var7_12.b + var7_12.d - var1_1.h) / 32;
                                var4_9 = (var7_12.a + var7_12.c + var1_1.h) / 32;
                                if (kg.l(var6_11.b(var3_3, var4_9))) {
                                    var1_1.c((var4_9 << 5) - var7_12.c, (var3_3 << 5) + (32 - var7_12.d));
                                    var1_1.a(1, 9);
                                }
                            }
                        }
                    } else if (var1_1.j == 10) {
                        var3_3 = (var7_12.b + var7_12.d) / 32;
                        var4_9 = var7_12.a / 32;
                        var5_10 = (var7_12.b + var7_12.d + var1_1.h) / 32;
                        var8_14 = (var7_12.a + var1_1.h) / 32;
                        if (var3_3 != var5_10) {
                            if (var4_9 != var8_14 && !kg.d(var6_11.b(var5_10, var8_14))) {
                                var1_1.c(var8_14 << 5, (var5_10 << 5) - var7_12.d);
                                var1_1.a(1, 8);
                            }
                        } else if (var4_9 != var8_14 && !kg.d(var6_11.b(var5_10, var8_14)) && !kg.d(var6_11.b(var5_10 + 1, var8_14))) {
                            var1_1.c(var8_14 << 5, (var5_10 + 1 << 5) - var7_12.d);
                            var1_1.a(1, 8);
                        }
                    } else if (var1_1.j == 9) {
                        var3_3 = (var7_12.b + var7_12.d) / 32;
                        var4_9 = (var7_12.a + var7_12.c) / 32;
                        var5_10 = (var7_12.b + var7_12.d - var1_1.h) / 32;
                        var8_14 = (var7_12.a + var7_12.c + var1_1.h) / 32;
                        if (var3_3 != var5_10) {
                            if (var4_9 != var8_14 && !kg.l(var6_11.b(var5_10, var8_14))) {
                                var1_1.c((var8_14 << 5) - var7_12.c, (var5_10 << 5) + (32 - var7_12.d));
                                var1_1.a(1, 8);
                            }
                        } else if (var4_9 != var8_14 && !kg.l(var6_11.b(var3_3, var8_14)) && !kg.l(var6_11.b(var3_3 - 1, var8_14))) {
                            var1_1.c((var8_14 << 5) - var7_12.c, (var5_10 << 5) - var7_12.d);
                            var1_1.a(1, 8);
                        }
                    } else {
                        if (var1_1.j == 6) {
                            var1_1.a(1, 9);
                            break;
                        }
                        if (var1_1.j == 5) {
                            var1_1.a(1, 10);
                            break;
                        }
                        var1_1.a(1, 8);
                        break;
lbl296:
                        // 1 sources

                        if (!var1_1.x) {
                            var1_1.a(0);
                        }
                    }
lbl298:
                    // 17 sources

                    var3_6 = this;
                    var4_9 = 0;
                    while (var4_9 < var3_6.a.length) {
                        if (!var3_6.a[var4_9]) ** GOTO lbl304
                        v3 = true;
                        ** GOTO lbl307
lbl304:
                        // 1 sources

                        ++var4_9;
                    }
                    v3 = false;
lbl307:
                    // 2 sources

                    if (!v3) {
                        var1_1.a(0);
                    }
                    if (var1_1.b() != 1) break;
                    var1_1.b(kk.b[var1_1.j] * var1_1.h, kk.c[var1_1.j] * var1_1.h);
                    if (var1_1.j == 4) {
                        var3_7 = (var7_12.b + var7_12.d - 1) / 32;
                        var4_9 = (var7_12.a - 1) / 32;
                        if (!var2_2.n(var6_11.b(var3_7, var4_9))) break;
                        var1_1.f(var4_9 + 1 << 5);
                        break;
                    }
                    if (var1_1.j != 8 || !var2_2.n(var6_11.b(var3_8 = (var7_12.b + var7_12.d - 1) / 32, var4_9 = (var7_12.a + var7_12.c) / 32))) break;
                    var1_1.f((var4_9 - 1 << 5) - (var7_12.c - 32));
                    break;
                }
                case 0: {
                    if (this.a[0]) {
                        if (!var1_1.x) {
                            var3_3 = (var7_12.b + var7_12.d / 2) / 32;
                            var4_9 = (var7_12.a + var7_12.c / 2) / 32;
                            if (kg.b(var6_11.b(var3_3, var4_9))) {
                                var1_1.f((var4_9 << 5) + (32 - var7_12.c) / 2);
                                var1_1.a(8, 1);
                            } else {
                                var3_3 = (var1_1.o() + 20) / 32;
                                if (kg.a(var6_11.b(var3_3, var4_9 = (var7_12.a + var7_12.c / 2) / 32))) {
                                    var1_1.a(5);
                                    this.a[0] = false;
                                }
                            }
                        } else {
                            var1_1.a(1, 1);
                        }
                    } else if (this.a[1]) {
                        var3_3 = (var7_12.b + var7_12.d + 8) / 32;
                        var4_9 = (var7_12.a + var7_12.c / 2) / 32;
                        if (!var1_1.x) {
                            if (kg.b(var6_11.b(var3_3, var4_9))) {
                                var1_1.c((var4_9 << 5) + (32 - var7_12.c) / 2, var3_3 << 5);
                                var1_1.a(8, 2);
                                break;
                            }
                        } else if (!kg.c(var6_11.b(var3_3, var4_9)) || kg.b(var6_11.b(var3_3, var4_9))) {
                            var1_1.a(1, 2);
                        } else {
                            var1_1.a(0);
                        }
                    }
                    if (!var1_1.x) {
                        if (var1_1.j == 4) {
                            var3_3 = (var7_12.b + var7_12.d) / 32;
                            var4_9 = (var7_12.a + var7_12.c - 5) / 32;
                            var5_10 = (var7_12.a + 5) / 32;
                            if (!kg.c(var6_11.b(var3_3, var5_10)) && !kg.c(var6_11.b(var3_3, var4_9))) {
                                if (var1_1.x) break;
                                var1_1.a(6);
                                break;
                            }
                        } else if (var1_1.j == 8) {
                            var3_3 = (var7_12.b + var7_12.d) / 32;
                            var4_9 = (var7_12.a + 5) / 32;
                            var5_10 = (var7_12.a + var7_12.c - 5) / 32;
                            if (!(kg.c(var6_11.b(var3_3, var5_10)) || kg.c(var6_11.b(var3_3, var4_9)) || kg.d(var6_11.b(var3_3 = (var7_12.b + var7_12.d) / 32, var4_9 = var7_12.a / 32)))) {
                                if (var1_1.x) break;
                                var1_1.a(6);
                                break;
                            }
                        }
                    }
                    this.a(var7_12, var6_11, var2_2, (kk)var1_1);
                }
            }
            var2_2.b(var7_12);
        }
    }

    private static boolean a(n n2, kk kk2, ke ke2, kg kg2, boolean bl2) {
        int n3 = (n2.b + n2.d / 2) / 32;
        int n4 = (n2.a - kk2.h) / 32;
        if (kg2.n(ke2.b(n3, n4))) {
            if (bl2) {
                kk2.f(n4 + 1 << 5);
            }
            return true;
        }
        return false;
    }

    private static boolean b(n n2, kk kk2, ke ke2, kg kg2, boolean bl2) {
        int n3 = (n2.b + n2.d / 2) / 32;
        int n4 = (n2.a + n2.c + kk2.h) / 32;
        if (kg2.m(ke2.b(n3, n4))) {
            if (bl2) {
                kk2.f((n4 - 1 << 5) - (n2.c - 32));
            }
            return true;
        }
        return false;
    }

    private void a(n n2, ke ke2, kg kg2, kk kk2) {
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        if (this.a[2]) {
            n3 = (n2.b + n2.d) / 32;
            n4 = n2.a / 32;
            n5 = (n2.a + n2.c) / 32;
            if (!kk2.x) {
                if (!kg.c(ke2.b(n3, n5)) && kg.d(ke2.b(n3, n4))) {
                    kk2.a(1, 5);
                    return;
                }
                if (!kg.c(ke2.b(n3, n4)) && kg.l(ke2.b(n3, n5))) {
                    kk2.a(1, 6);
                    return;
                }
                if (kg.l(ke2.b(n3, n5))) {
                    kk2.a(1, 6);
                    return;
                }
                if (kg.d(ke2.b(n3, n4))) {
                    kk2.a(1, 5);
                    return;
                }
                kk2.a(1, 4);
                return;
            }
            kk2.a(1, 4);
            return;
        }
        if (this.a[3]) {
            n3 = (n2.b + n2.d) / 32;
            n4 = n2.a / 32;
            n5 = (n2.a + n2.c) / 32;
            if (!kk2.x) {
                if (!kg.c(ke2.b(n3, n5)) && kg.d(ke2.b(n3, n4))) {
                    kk2.a(1, 10);
                    return;
                }
                n4 = (n2.a + n2.c) / 32;
                n5 = n2.a / 32;
                if (!kg.c(ke2.b(n3, n5)) && kg.l(ke2.b(n3, n4))) {
                    kk2.a(1, 9);
                    return;
                }
                if (kg.l(ke2.b(n3, n4))) {
                    kk2.a(1, 9);
                    return;
                }
                if (kg.d(ke2.b(n3, n5))) {
                    kk2.a(1, 10);
                    return;
                }
                kk2.a(1, 8);
                return;
            }
            kk2.a(1, 8);
        }
    }

    private void a(kk kk2, n n2, ke ke2, kg kg2) {
        int n3 = (n2.b + n2.d) / 32;
        int n4 = 0;
        if (this.a[2]) {
            n4 = (n2.a - kk2.h) / 32;
            if ((kk2.j & 4) == 0) {
                kk2.b(4);
            } else if (!kl.a(n2, kk2, ke2, kg2, false) && !kg2.n(ke2.b(n3, n4))) {
                n2.a += kk2.h * kk.b[4];
            }
        } else if (this.a[3]) {
            n4 = (n2.a + n2.c + kk2.h) / 32;
            if ((kk2.j & 8) == 0) {
                kk2.b(8);
            } else if (!kl.b(n2, kk2, ke2, kg2, false) && !kg2.m(ke2.b(n3, n4))) {
                n2.a += kk2.h * kk.b[8];
            }
        }
        if (this.a[0] || this.a[1] || this.a[4]) {
            n4 = (n2.a + n2.c / 2) / 32;
            n3 = (n2.b + n2.d - 5) / 32;
            int n5 = (n2.b + 10) / 32;
            if (kg.b(ke2.b(n3, n4)) && kg.b(ke2.b(n5, n4))) {
                kk2.f((n4 << 5) + (32 - n2.c) / 2);
                kk2.a(8, this.a[1] ? 2 : 1);
                this.a[3] = false;
                this.a[2] = false;
            }
        }
    }

    public final void a(int n2, kk kk2) {
        switch (n2) {
            case 99: 
            case 150: {
                this.a[0] = true;
                this.a[1] = false;
                return;
            }
            case 98: 
            case 156: {
                this.a[1] = true;
                this.a[0] = false;
                return;
            }
            case 97: 
            case 152: {
                this.a[2] = true;
                this.a[3] = false;
                return;
            }
            case 96: 
            case 154: {
                this.a[3] = true;
                this.a[2] = false;
                return;
            }
            case 95: 
            case 153: {
                if (kk2.b() == 0 || kk2.b() == 1) {
                    kk2.a(4);
                }
                this.a();
                return;
            }
            case 149: {
                this.a[2] = true;
                this.a[0] = true;
                return;
            }
            case 151: {
                this.a[3] = true;
                this.a[0] = true;
            }
        }
    }

    public final void a(int n2) {
        switch (n2) {
            case 99: 
            case 150: {
                this.a[0] = false;
                return;
            }
            case 98: 
            case 156: {
                this.a[1] = false;
                return;
            }
            case 97: 
            case 152: {
                this.a[2] = false;
                return;
            }
            case 96: 
            case 154: {
                this.a[3] = false;
                return;
            }
            case 95: 
            case 153: {
                return;
            }
            case 149: {
                this.a[2] = false;
                this.a[0] = false;
                return;
            }
            case 151: {
                this.a[3] = false;
                this.a[0] = false;
            }
        }
    }

    public final void a() {
        this.a[4] = false;
        this.a[1] = false;
        this.a[0] = false;
        this.a[3] = false;
        this.a[2] = false;
    }
}

