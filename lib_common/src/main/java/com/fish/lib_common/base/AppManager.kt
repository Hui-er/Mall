package com.fish.lib_common.base

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * Created by hpw on 16/10/28.
 */

class AppManager private constructor() {

    val isAppExit: Boolean
        get() = activityStack == null || activityStack!!.isEmpty()

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return if (activityStack == null || activityStack!!.size == 0) {
            null
        } else activityStack!!.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        if (activityStack == null || activityStack!!.size == 0) {
            return
        }
        if (activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        //Java ConcurrentModificationException异常原因和解决方法
        if (activityStack == null || activityStack!!.size == 0) {
            return
        }
        val iterator = activityStack?.iterator()
        while (null != iterator && iterator.hasNext()) {
            val activity = iterator.next()
            if (activity.javaClass == cls) {
                iterator.remove()
                activity.finish()
            }
        }

        /*  for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }*/
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: String) {
        //Java ConcurrentModificationException异常原因和解决方法
        if (activityStack == null || activityStack!!.size == 0) {
            return
        }
        val iterator = activityStack?.iterator()
        while (null != iterator && iterator.hasNext()) {
            val activity = iterator.next()
            if (activity.javaClass.name == cls) {
                iterator.remove()
                activity.finish()
            }
        }

        /*  for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }*/
    }


    /**
     * 获取指定类名的Activity
     */
    fun getActivity(cls: Class<*>): Activity? {
        for (activity in activityStack!!) {
            if (activity.javaClass == cls) {
                return activity
            }
        }
        return null
    }

    /**
     * 获取指定类名的Activity
     */
    fun getActivity(className: String): Activity? {
        for (activity in activityStack!!) {
            if (activity.javaClass.simpleName == className) {
                return activity
            }
        }
        return null
    }

    /**
     * 除指定的Activity结束其他的Activity
     */
    fun finishAllActivity(cls: Class<*>) {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                val activity = activityStack!![i]
                if (activity.javaClass != cls) {
                    activityStack!!.remove(activity)
                    activity.finish()
                }
            }
            i++
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                activityStack!![i].finish()
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        try {
            finishAllActivity()
            val activityMgr = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityMgr.killBackgroundProcesses(context.packageName)
            //保存友盟统计数据
            System.exit(0)
        } catch (e: Exception) {
        }

    }

    companion object {
        private val lock = Any()
        private var activityStack: Stack<Activity>? = null
        private var instance: AppManager? = null

        /**
         * 单一实例
         */
        val appManager: AppManager
            get() {
                if (instance == null) {
                    synchronized(lock) {
                        if (instance == null) {
                            instance = AppManager()
                        }
                    }
                }
                return instance!!
            }
    }
}
