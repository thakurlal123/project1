package com.thakur.project1;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            CharSequence eventText = (CharSequence) event.getText();
            if (eventText != null && eventText.toString().contains("your_keyword_here")) {

               new MainActivity().showAlertDialogToCloseTab();
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Handle interruption
    }
}

