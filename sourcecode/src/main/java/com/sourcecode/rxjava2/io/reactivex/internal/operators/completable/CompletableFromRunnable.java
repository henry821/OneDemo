/**
 * Copyright (c) 2016-present, RxJava Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package com.sourcecode.rxjava2.io.reactivex.internal.operators.completable;

import com.sourcecode.rxjava2.io.reactivex.Completable;
import com.sourcecode.rxjava2.io.reactivex.CompletableObserver;
import com.sourcecode.rxjava2.io.reactivex.disposables.Disposable;
import com.sourcecode.rxjava2.io.reactivex.disposables.Disposables;
import com.sourcecode.rxjava2.io.reactivex.exceptions.Exceptions;

public final class CompletableFromRunnable extends Completable {

    final Runnable runnable;

    public CompletableFromRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    protected void subscribeActual(CompletableObserver observer) {
        Disposable d = Disposables.empty();
        observer.onSubscribe(d);
        try {
            runnable.run();
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            if (!d.isDisposed()) {
                observer.onError(e);
            }
            return;
        }
        if (!d.isDisposed()) {
            observer.onComplete();
        }
    }
}
