/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.API;

import java.util.Map;

import com.sun.jna.platform.win32.Advapi32Util;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;
import com.sun.jna.platform.win32.VersionHelpers;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinError;
import com.sun.jna.platform.win32.WinReg;

import oshi.annotation.concurrent.Immutable;
import oshi.driver.windows.wmi.Win32VideoController;
import oshi.driver.windows.wmi.Win32VideoController.VideoControllerProperty;
import oshi.hardware.GraphicsCard;
import oshi.hardware.common.AbstractGraphicsCard;
import oshi.util.Constants;
import oshi.util.ParseUtil;
import oshi.util.Util;
import oshi.util.platform.windows.WmiUtil;
import oshi.util.tuples.Triplet;

public class StartUp {
    public static final String STARTUP_CURRENT_USER_REGISTRY = "Software\\Microsoft\\Windows\\CurrentVersion\\Run\\";
    public static void main(String[] args) {
         Map <String, Object> appStartUp = Advapi32Util.registryGetValues(HKEY_LOCAL_MACHINE, STARTUP_CURRENT_USER_REGISTRY);
         System.out.println("Hello");

        for (String value : appStartUp.keySet()) {

            System.out.println(value);

        }
    }
}