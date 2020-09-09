Project for showing android bug 168035412
=========================================

On some Android 10 devices, setting `WindowManager.LayoutParams.screenBrightness`
to `WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF` doesn't work. Instead, the brightness
is changed as if this parameter was set to `WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE`.


Reproducible on
---------------

The issue was successfully reproduced on:
* Google Pixel; Android 10 (Build number QP1A.190711.020)


Buggy behavior
--------------

The `screenBrightness` property of the `WindowManager.LayoutParams` set to the
window of an activity controls the screen brightness when that activity is visible.  
The documentation<sup>[1](https://developer.android.com/reference/android/view/WindowManager.LayoutParams#screenBrightness)</sup> of this property states that:

> This can be used to override the user's preferred brightness of the screen. A value of less than 0, the default, means to use the preferred screen brightness. 0 to 1 adjusts the brightness from dark to full bright.

Additionally, the following constants are defined for use with this property:
* `BRIGHTNESS_OVERRIDE_NONE = -1.0f`
* `BRIGHTNESS_OVERRIDE_OFF = 0.0f`
* `BRIGHTNESS_OVERRIDE_FULL = 1.0f`

However, when this property is set to `BRIGHTNESS_OVERRIDE_OFF`, the screen is
not set to its lowest possible brightness, but rather to the default system
brightness, as if `BRIGHTNESS_OVERRIDE_NONE` was used instead. Further
experimentation shows that any value `< 0.004f` causes this unexpected behavior.


Expected behavior
-----------------

`WindowManager.LayoutParams.screenBrightness` works as documented for values in
the inclusive range `[0.0f, 0.004f]`.


Steps to reproduce
------------------

1. Build and install the app in this project
2. Play around with the slider to change the current value of the
  `screenBrightness` of the activity window.
3. Notice the buggy behavior when this value is less than `0.004f`.


For details, see [https://issuetracker.google.com/issues/168035412](https://issuetracker.google.com/issues/168035412).

References
----------
[1] [https://developer.android.com/reference/android/view/WindowManager.LayoutParams#screenBrightness](https://developer.android.com/reference/android/view/WindowManager.LayoutParams#screenBrightness)
