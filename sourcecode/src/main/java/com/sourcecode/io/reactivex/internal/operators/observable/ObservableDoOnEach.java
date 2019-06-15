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

package com.sourcecode.io.reactivex.internal.operators.observable;

import com.baselibrary.utils.LogUtil;

import com.sourcecode.io.reactivex.*;
import com.sourcecode.io.reactivex.disposables.Disposable;
import com.sourcecode.io.reactivex.exceptions.*;
import com.sourcecode.io.reactivex.functions.*;
import com.sourcecode.io.reactivex.internal.disposables.DisposableHelper;
import com.sourcecode.io.reactivex.plugins.RxJavaPlugins;

public final class ObservableDoOnEach<T> extends AbstractObservableWithUpstream<T, T> {
    final Consumer<? super T> onNext;
    final Consumer<? super Throwable> onError;
    final Action onComplete;
    final Action onAfterTerminate;

    public ObservableDoOnEach(ObservableSource<T> source, Consumer<? super T> onNext,
                              Consumer<? super Throwable> onError,
                              Action onComplete,
                              Action onAfterTerminate) {
        super(source);
        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = onComplete;
        this.onAfterTerminate = onAfterTerminate;
    }

    @Override
    public void subscribeActual(Observer<? super T> t) {
        //add by whw
        LogUtil.printObservable(getClass(), "subscribeActual");
        //add by whw
        source.subscribe(new DoOnEachObserver<T>(t, onNext, onError, onComplete, onAfterTerminate));
    }

    static final class DoOnEachObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> downstream;
        final Consumer<? super T> onNext;
        final Consumer<? super Throwable> onError;
        final Action onComplete;
        final Action onAfterTerminate;

        Disposable upstream;

        boolean done;

        DoOnEachObserver(
                Observer<? super T> actual,
                Consumer<? super T> onNext,
                Consumer<? super Throwable> onError,
                Action onComplete,
                Action onAfterTerminate) {
            this.downstream = actual;
            this.onNext = onNext;
            this.onError = onError;
            this.onComplete = onComplete;
            this.onAfterTerminate = onAfterTerminate;
            //add by whw
            LogUtil.printObserver(getClass(), "构造函数，downStream类型：" + downstream.getClass().getSimpleName());
            //add by whw
        }

        @Override
        public void onSubscribe(Disposable d) {
            //add by whw
            LogUtil.printObserver(getClass(), "onSubscribe(Disposable d)");
            //add by whw
            if (DisposableHelper.validate(this.upstream, d)) {
                this.upstream = d;
                downstream.onSubscribe(this);
            }
        }

        @Override
        public void dispose() {
            upstream.dispose();
        }

        @Override
        public boolean isDisposed() {
            return upstream.isDisposed();
        }

        @Override
        public void onNext(T t) {
            //add by whw
            LogUtil.printObserver(getClass(), "onNext");
            //add by whw
            if (done) {
                return;
            }
            try {
                onNext.accept(t);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                upstream.dispose();
                onError(e);
                return;
            }

            downstream.onNext(t);
        }

        @Override
        public void onError(Throwable t) {
            if (done) {
                RxJavaPlugins.onError(t);
                return;
            }
            done = true;
            try {
                onError.accept(t);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                t = new CompositeException(t, e);
            }
            downstream.onError(t);

            try {
                onAfterTerminate.run();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(e);
            }
        }

        @Override
        public void onComplete() {
            //add by whw
            LogUtil.printObserver(getClass(), "onComplete()");
            //add by whw
            if (done) {
                return;
            }
            try {
                onComplete.run();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                onError(e);
                return;
            }

            done = true;
            downstream.onComplete();

            try {
                onAfterTerminate.run();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(e);
            }
        }
    }
}