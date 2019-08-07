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

package com.sourcecode.rxjava2.io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicReference;

import com.sourcecode.rxjava2.io.reactivex.*;
import com.sourcecode.rxjava2.io.reactivex.disposables.Disposable;
import com.sourcecode.rxjava2.io.reactivex.exceptions.*;
import com.sourcecode.rxjava2.io.reactivex.functions.Function;
import com.sourcecode.rxjava2.io.reactivex.internal.disposables.DisposableHelper;
import com.sourcecode.rxjava2.io.reactivex.internal.functions.ObjectHelper;
import com.sourcecode.rxjava2.io.reactivex.internal.observers.ResumeSingleObserver;

public final class SingleResumeNext<T> extends Single<T> {
    final SingleSource<? extends T> source;

    final Function<? super Throwable, ? extends SingleSource<? extends T>> nextFunction;

    public SingleResumeNext(SingleSource<? extends T> source,
            Function<? super Throwable, ? extends SingleSource<? extends T>> nextFunction) {
        this.source = source;
        this.nextFunction = nextFunction;
    }

    @Override
    protected void subscribeActual(final SingleObserver<? super T> observer) {
        source.subscribe(new ResumeMainSingleObserver<T>(observer, nextFunction));
    }

    static final class ResumeMainSingleObserver<T> extends AtomicReference<Disposable>
    implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = -5314538511045349925L;

        final SingleObserver<? super T> downstream;

        final Function<? super Throwable, ? extends SingleSource<? extends T>> nextFunction;

        ResumeMainSingleObserver(SingleObserver<? super T> actual,
                Function<? super Throwable, ? extends SingleSource<? extends T>> nextFunction) {
            this.downstream = actual;
            this.nextFunction = nextFunction;
        }

        @Override
        public void onSubscribe(Disposable d) {
            if (DisposableHelper.setOnce(this, d)) {
                downstream.onSubscribe(this);
            }
        }

        @Override
        public void onSuccess(T value) {
            downstream.onSuccess(value);
        }

        @Override
        public void onError(Throwable e) {
            SingleSource<? extends T> source;

            try {
                source = ObjectHelper.requireNonNull(nextFunction.apply(e), "The nextFunction returned a null SingleSource.");
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                downstream.onError(new CompositeException(e, ex));
                return;
            }

            source.subscribe(new ResumeSingleObserver<T>(this, downstream));
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }
    }
}