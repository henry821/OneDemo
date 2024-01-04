package com.demo.modules.fresco

import android.util.Log
import com.facebook.common.references.SharedReference
import com.facebook.imagepipeline.debug.CloseableReferenceLeakTracker

class ClosableReferenceLeakTrackerImpl : CloseableReferenceLeakTracker {
    override val isSet: Boolean
        get() = true

    override fun setListener(listener: CloseableReferenceLeakTracker.Listener?) = Unit

    override fun trackCloseableReferenceLeak(
        reference: SharedReference<Any?>,
        stacktrace: Throwable?,
    ) {
        Log.e(TAG_MONITOR_FRESCO, "Fresco.Closable泄露", stacktrace)
    }
}