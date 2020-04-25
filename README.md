# log-parser
parse the log file and get the desired output


Problem statement
Given:

Log File: Android bug report
Sample Line: 08-27 18:52:45.942  4667  4966 I art     : Waiting for a blocking GC Alloc
Col #1: Date
Col #2: Time
Col #3 Processid
Col #5: Log Level
Last Column: Log Message

Inputs to the program:

Process id(Use 4667)
List of Strings to search
WARNING
OOM
OutOfMemoryError


Expected Output


Find all FATAL crashes(will have the string, "FATAL EXCEPTION" followed by Exception message and Stacktrace)
List “Unique Exception messages” with the number of occurrences
List stack trace for every unique Fatal exception
Find all errors(Errors are lines with log level E)
List Unique errors with the number of occurrences
For the given Strings
List unique lines containing that string and the number of occurrences


Sample Output:



Below is just a sample output. Please feel free to change the format.


FATAL EXCEPTION

==============


Exception Message| # of Occurrences

java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState | 2

java.lang.OutOfMemoryError: OutOfMemoryError thrown while trying to throw OutOfMemoryError; no stack trace available| 1



Stacktrace:

========


#1) java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState

        at android.support.v4.app.FragmentManagerImpl.checkStateLoss(Unknown Source:10)

        at android.support.v4.app.FragmentManagerImpl.enqueueAction(Unknown Source:2)

        at android.support.v4.app.BackStackRecord.a(Unknown Source:78)

        at android.support.v4.app.BackStackRecord.commit(Unknown Source:1)

        at glance.ui.sdk.activity.GlanceHomeActivity.showBingeContainer(Unknown Source:19)

        at glance.ui.sdk.activity.GlanceHomeActivity.e(Unknown Source:0)

        at glance.ui.sdk.activity.GlanceHomeActivity$3.onTabSelected(Unknown Source:30)

        at android.support.design.widget.TabLayout.dispatchTabSelected(Unknown Source:19)

        at android.support.design.widget.TabLayout.a(Unknown Source:55)

        at android.support.design.widget.TabLayout.a(Unknown Source:1)

        at android.support.design.widget.TabLayout$Tab.select(Unknown Source:14)

        at android.support.design.widget.TabLayout$TabView.performClick(Unknown Source:16)

        at android.view.View.performClickInternal(View.java:6585)

        at android.view.View.access$3100(View.java:785)

        at android.view.View$PerformClick.run(View.java:25921)

        at android.os.Handler.handleCallback(Handler.java:873)

        at android.os.Handler.dispatchMessage(Handler.java:99)

        at android.os.Looper.loop(Looper.java:201)

        at android.app.ActivityThread.main(ActivityThread.java:6810)

        at java.lang.reflect.Method.invoke(Native Method)

        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:547)

        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:873)



#2) java.lang.OutOfMemoryError: OutOfMemoryError thrown while trying to throw OutOfMemoryError; no stack trace available



Errors

=====


Error Message| # of Occurrences

java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState | 2

java.lang.OutOfMemoryError: OutOfMemoryError thrown while trying to throw OutOfMemoryError; no stack trace available| 1



Matching Strings

=============


Matching String| # of Occurrences

JNI WARNING: java.lang.OutOfMemoryError thrown while calling printStackTrace|1

Throwing OutOfMemoryError "Failed to allocate a 8204 byte allocation with 0 free bytes and -2MB until OOM”|10


