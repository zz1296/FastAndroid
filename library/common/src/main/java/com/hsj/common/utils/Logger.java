/*
 *   Copyright (c) 2017.  HSJ
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.hsj.common.utils;

import android.util.Log;
import com.hsj.common.BuildConfig;
import java.lang.reflect.Method;

/**
 * @Author:HSJ
 * @E-mail:shengjunhu@foxmail.com
 * @Date:2017/5/27 16:39
 * @Class:Logger
 * @Description:日志工具类(调试、错误日志收集、删除)
 */
public class Logger {

    //////////////////////////////////////////////////////////////
    // Logger 功能如下：
    //      1、增加控制台日志打印长度
    //      2、开关控制日志是否打印
    //      3、可添加自定义tag，默认：[Logger]
    //      4、收集错误日志;
    //      5、定时删除错误日志(错误日志删除工作交给TaskManager)
    //////////////////////////////////////////////////////////////

    /**
     * 日志标签
     */
    private static String TAG = "[Logger]";

    /**
     * 日志总开关
     */
    private static final boolean isLogEnable = BuildConfig.isLogEnAble;

    /**
     * 日志打印行数
     */
    private static final int MAX_LENGTH = 5000;

    private Logger() {

    }

    public static void e(Object msg) {
        e(TAG, msg);
    }

    public static void e(String tag, Object msg) {
        e(tag, msg, null);
    }

    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    public static void w(Object msg) {
        w(TAG, msg);
    }

    public static void w(String tag, Object msg) {
        w(tag, msg, null);
    }

    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    public static void d(Object msg) {
        d(TAG, msg);
    }

    public static void d(String tag, Object msg) {
        d(tag, msg, null);
    }

    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    public static void i(Object msg) {
        i(TAG, msg);
    }

    public static void i(String tag, Object msg) {
        i(tag, msg, null);
    }

    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'i');
    }

    public static void v(Object msg) {
        v(TAG, msg);
    }

    public static void v(String tag, Object msg) {
        v(tag, msg, null);
    }

    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     */
    private static void log(String tag, String msg, Throwable tr, char level) {
        if (isLogEnable) {
            if ('e' == level) {
                print("e", tag, msg, tr);
            } else if ('w' == level) {
                print("w", tag, msg, tr);
            } else if ('d' == level) {
                print("d", tag, msg);
            } else if ('i' == level) {
                print("i", tag, msg);
            } else {
                print("v", tag, msg);
            }
        }
    }

    /**
     * 打印日志 d、v、i
     *
     * @param method
     * @param tag
     * @param message
     */
    private static void print(String method, String tag, String message) {
        int strLength = message.length();
        if (strLength == 0) {
            invokePrint(method, tag, message);
        } else {
            for (int i = 0; i < strLength / MAX_LENGTH + (strLength % MAX_LENGTH > 0 ? 1 : 0); i++) {
                int end = (i + 1) * MAX_LENGTH;
                if (strLength >= end) {
                    invokePrint(method, tag, message.substring(end - MAX_LENGTH, end));
                } else {
                    invokePrint(method, tag, message.substring(end - MAX_LENGTH));
                }
            }
        }
    }

    /**
     * 打印日志 e、w
     *
     * @param method
     * @param tag
     * @param message
     * @param e
     */
    private static void print(String method, String tag, String message, Throwable e) {
        invokePrint(method, tag, message, e);
    }

    /**
     * 通过映射获取Log打印日志
     *
     * @param method
     * @param tag
     * @param message
     */
    private static void invokePrint(String method, String tag, String message) {
        try {
            Class<Log> logClass = Log.class;
            Method logMethod = logClass.getMethod(method, String.class, String.class);
            logMethod.setAccessible(true);
            logMethod.invoke(null, tag, message);
        } catch (Exception e) {
            System.out.println(tag + ": " + message);
        }
    }

    /**
     * 通过映射获取Log打印日志
     *
     * @param method
     * @param tag
     * @param message
     * @param e
     */
    private static void invokePrint(String method, String tag, String message, Throwable e) {
        try {
            Class<Log> logClass = Log.class;
            Method logMethod = logClass.getMethod(method, String.class, String.class, Throwable.class);
            logMethod.setAccessible(true);
            logMethod.invoke(null, tag, message, e);
        } catch (Exception e1) {
            System.out.println(tag + ": " + message);
        }
    }

}
