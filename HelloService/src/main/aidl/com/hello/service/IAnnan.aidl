// IAnnan.aidl
package com.hello.service;

import com.hello.service.IToast;
interface IAnnan {
    boolean join(IToast token,String name);
    boolean leave(IToast token);
    void registerCallback(IToast token);
    void unregisterCallback(IToast token);
}
