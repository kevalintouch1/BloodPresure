package com.blood.presure.Utils;

import android.widget.NumberPicker;

import com.blood.presure.Interface.InMobiNetworkValues;
import com.blood.presure.NewBloodApplication;
import com.blood.presure.R;

import java.lang.reflect.Field;

import dalvik.bytecode.Opcodes;

public class MeUtils {
    static int[][] active = {new int[]{128, Opcodes.OP_ARRAY_LENGTH}, new int[]{123, Opcodes.OP_INVOKE_VIRTUAL}, new int[]{117, 174}, new int[]{111, 165}, new int[]{105, 156}, new int[]{99, 147}, new int[]{93, 138}, new int[]{87, 129}, new int[]{81, 120}, new int[]{78, 116}, new int[]{78, 116}, new int[]{78, 116}, new int[]{78, 116}};
    static int[][] resting = {new int[]{78, 93}, new int[]{62, 85}, new int[]{57, 87}, new int[]{57, 87}, new int[]{57, 86}, new int[]{57, 86}, new int[]{56, 85}, new int[]{56, 85}, new int[]{57, 86}, new int[]{57, 86}, new int[]{57, 86}, new int[]{57, 86}, new int[]{57, 86}};

    public static int getAge() {
        int LoadPref = NewSaveLanguageUtils.LoadPref("age", NewBloodApplication.getInstance());
        if (LoadPref == 0) {
            return 25;
        }
        return LoadPref;
    }

    public static void setAge(int i) {
        NewSaveLanguageUtils.saveLanguage("age", i + "", NewBloodApplication.getInstance());
    }

    public static void setWeight(int i) {
        NewSaveLanguageUtils.saveLanguage("weight", i + "", NewBloodApplication.getInstance());
    }

    public static void setHeight(int i) {
        NewSaveLanguageUtils.saveLanguage(InMobiNetworkValues.HEIGHT, i + "", NewBloodApplication.getInstance());
    }

    public static void setGender(int i) {
        NewSaveLanguageUtils.saveLanguage("gender", i + "", NewBloodApplication.getInstance());
    }

    public static int getHeight() {
        int LoadPref = NewSaveLanguageUtils.LoadPref(InMobiNetworkValues.HEIGHT, NewBloodApplication.getInstance());
        return LoadPref == 0 ? 180 : LoadPref;
    }

    public static int getWeight() {
        int LoadPref = NewSaveLanguageUtils.LoadPref("weight", NewBloodApplication.getInstance());
        if (LoadPref == 0) {
            return 70;
        }
        return LoadPref;
    }

    public static int getGender() {
        return NewSaveLanguageUtils.LoadPref("gender", NewBloodApplication.getInstance());
    }

    public static String getGenderText() {
        if (NewSaveLanguageUtils.LoadPref("gender", NewBloodApplication.getInstance()) == 0) {
            return NewBloodApplication.getInstance().getString(R.string.Male);
        }
        return NewBloodApplication.getInstance().getString(R.string.Female);
    }

    public static void setDividerColor(NumberPicker numberPicker, int i) {
        Field[] declaredFields = NumberPicker.class.getDeclaredFields();
        int length = declaredFields.length;
        int i2 = 0;
        while (i2 < length) {
            Field field = declaredFields[i2];
            if (field.getName().equals("mSelectionDivider")) {
                field.setAccessible(true);
                try {
                    field.set(numberPicker, Integer.valueOf(i));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            } else {
                i2++;
            }
        }
    }

    public static String getHeartStateString(int i, int i2, int i3) {
        int heartState = getHeartState(i, i2, i3);
        if (heartState == -1) {
            return NewBloodApplication.getInstance().getString(R.string.slow);
        }
        if (heartState == 0) {
            return NewBloodApplication.getInstance().getString(R.string.normal);
        }
        return NewBloodApplication.getInstance().getString(R.string.Fast);
    }

    public static int getHeartState(int i, int i2, int i3) {
        int[] iArr;
        if (i2 < 10) {
            if (i3 == 0) {
                iArr = resting[0];
            } else {
                iArr = active[0];
            }
        } else if (i2 < 20) {
            if (i3 == 0) {
                iArr = resting[1];
            } else {
                iArr = active[1];
            }
        } else if (i2 < 30) {
            if (i3 == 0) {
                iArr = resting[2];
            } else {
                iArr = active[2];
            }
        } else if (i2 < 40) {
            if (i3 == 0) {
                iArr = resting[3];
            } else {
                iArr = active[3];
            }
        } else if (i2 < 50) {
            if (i3 == 0) {
                iArr = resting[4];
            } else {
                iArr = active[4];
            }
        } else if (i2 < 60) {
            if (i3 == 0) {
                iArr = resting[5];
            } else {
                iArr = active[5];
            }
        } else if (i2 < 70) {
            if (i3 == 0) {
                iArr = resting[6];
            } else {
                iArr = active[6];
            }
        } else if (i2 < 80) {
            if (i3 == 0) {
                iArr = resting[7];
            } else {
                iArr = active[7];
            }
        } else if (i2 < 90) {
            if (i3 == 0) {
                iArr = resting[8];
            } else {
                iArr = active[8];
            }
        } else if (i3 == 0) {
            iArr = resting[9];
        } else {
            iArr = active[9];
        }
        if (i < iArr[0]) {
            return -1;
        }
        if (i > iArr[1]) {
            return 1;
        }
        return 0;
    }
}
