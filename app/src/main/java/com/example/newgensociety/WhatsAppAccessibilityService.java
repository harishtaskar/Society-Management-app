package com.example.newgensociety;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class WhatsAppAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if(getRootInActiveWindow() == null){
            return;
        }

        AccessibilityNodeInfoCompat rootNodeInfo = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());

        List<AccessibilityNodeInfoCompat> messaeNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
        if(messaeNodeList == null || messaeNodeList.isEmpty()){
            return;
        }
        AccessibilityNodeInfoCompat messageField = messaeNodeList.get(0);
        if(messageField == null||messageField.getText().length() == 0 || !messageField.getText().toString().endsWith("   ")){
            return;
        }
        List<AccessibilityNodeInfoCompat> senMessaeNodeList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
        if(senMessaeNodeList == null || messaeNodeList.isEmpty()){
            return;
        }
        AccessibilityNodeInfoCompat sendMessage = senMessaeNodeList.get(0);
        if(sendMessage.isVisibleToUser())
            return;

        sendMessage.performAction(AccessibilityNodeInfo.ACTION_CLICK);

        try {
                Thread.sleep(2000);
                performGlobalAction(GLOBAL_ACTION_BACK);
                Thread.sleep(2000);
        }catch (InterruptedException ignored){}

        performGlobalAction(GLOBAL_ACTION_BACK);
    }

    @Override
    public void onInterrupt() {

    }
}
