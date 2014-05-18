package com.jbirdvegas.exposedroot;

import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class ByPasser implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log(String.format("App Loaded: %s", lpparam.packageName));
        if (!lpparam.packageName.equals("com.jbirdvegas.rootkit")) {
            return;
        }
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
    }
}
