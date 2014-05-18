package com.jbirdvegas.exposedroot;

import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class ByPasser implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log(String.format("App Loaded: %s", lpparam.packageName));
        // Bypass my root detection
        XposedHelpers.findAndHookMethod("com.jbirdvegas.rootkit.RootKit",
                lpparam.classLoader,
                "isDeviceRooted",
                Context.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return false;
                    }
                });
        // Bypass RootTools root detection
        XposedHelpers.findAndHookMethod("com.stericson.RootTools.RootTools",
                lpparam.classLoader,
                "isRootAvailable",
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return false;
                    }
                });
    }
}
