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

package com.sourcecode.io.reactivex.internal.observers;

import com.baselibrary.utils.LogUtil;

import com.sourcecode.io.reactivex.Observer;
import com.sourcecode.io.reactivex.disposables.Disposable;
import com.sourcecode.io.reactivex.exceptions.Exceptions;
import com.sourcecode.io.reactivex.internal.disposables.DisposableHelper;
import com.sourcecode.io.reactivex.internal.fuseable.QueueDisposable;
import com.sourcecode.io.reactivex.plugins.RxJavaPlugins;

/**
 * Base class for a fuseable intermediate observer.
 * @param <T> the upstream value type
 * @param <R> the downstream value type
 */
public abstract class BasicFuseableObserver<T, R> implements Observer<T>, QueueDisposable<R> {

    /**
     * The downstream subscriber.
     *
     * 下游observer，用于在本observer的onNext方法里处理完消息后交给下游observer的onNext方法继续处理
     */
    protected final Observer<? super R> downstream;

    /**
     * The upstream subscription.
     *
     * 上游Disposable，当执行{@link #onSubscribe(Disposable)}时，会把参数(上游Disposable)保存到此变量
     */
    protected Disposable upstream;

    /** The upstream's QueueDisposable if not null. */
    protected QueueDisposable<T> qd;

    /** Flag indicating no further onXXX event should be accepted. */
    protected boolean done;

    /** Holds the established fusion mode of the upstream. */
    protected int sourceMode;

    /**
     * Construct a BasicFuseableObserver by wrapping the given subscriber.
     * @param downstream the subscriber, not null (not verified)
     */
    public BasicFuseableObserver(Observer<? super R> downstream) {
        this.downstream = downstream;
        //add by whw
        LogUtil.printObserver(getClass(), "构造函数，downStream类型-->" + downstream.getClass().getSimpleName());
        //add by whw
    }

    // final: fixed protocol steps to support fuseable and non-fuseable upstream
    @SuppressWarnings("unchecked")
    @Override
    public final void onSubscribe(Disposable d) {
        if (DisposableHelper.validate(this.upstream, d)) {

            //add by whw
            LogUtil.printObserver(getClass(),"onSubscribe(Disposable d),upStream类型-->"+d.getClass().getSimpleName());
            //add by whw

            this.upstream = d;
            if (d instanceof QueueDisposable) {
                this.qd = (QueueDisposable<T>)d;
            }

            if (beforeDownstream()) {

                downstream.onSubscribe(this);

                afterDownstream();
            }

        }
    }

    /**
     * Override this to perform actions before the call {@code actual.onSubscribe(this)} happens.
     * @return true if onSubscribe should continue with the call
     */
    protected boolean beforeDownstream() {
        return true;
    }

    /**
     * Override this to perform actions after the call to {@code actual.onSubscribe(this)} happened.
     */
    protected void afterDownstream() {
        // default no-op
    }

    // -----------------------------------
    // Convenience and state-aware methods
    // -----------------------------------

    @Override
    public void onError(Throwable t) {
        if (done) {
            RxJavaPlugins.onError(t);
            return;
        }
        done = true;
        downstream.onError(t);
    }

    /**
     * Rethrows the throwable if it is a fatal exception or calls {@link #onError(Throwable)}.
     * @param t the throwable to rethrow or signal to the actual subscriber
     */
    protected final void fail(Throwable t) {
        Exceptions.throwIfFatal(t);
        upstream.dispose();
        onError(t);
    }

    @Override
    public void onComplete() {
        if (done) {
            return;
        }
        done = true;
        downstream.onComplete();
    }

    /**
     * Calls the upstream's QueueDisposable.requestFusion with the mode and
     * saves the established mode in {@link #sourceMode} if that mode doesn't
     * have the {@link QueueDisposable#BOUNDARY} flag set.
     * <p>
     * If the upstream doesn't support fusion ({@link #qd} is null), the method
     * returns {@link QueueDisposable#NONE}.
     * @param mode the fusion mode requested
     * @return the established fusion mode
     */
    protected final int transitiveBoundaryFusion(int mode) {
        QueueDisposable<T> qd = this.qd;
        if (qd != null) {
            if ((mode & BOUNDARY) == 0) {
                int m = qd.requestFusion(mode);
                if (m != NONE) {
                    sourceMode = m;
                }
                return m;
            }
        }
        return NONE;
    }

    // --------------------------------------------------------------
    // Default implementation of the RS and QS protocol (can be overridden)
    // --------------------------------------------------------------

    @Override
    public void dispose() {
        upstream.dispose();
    }

    @Override
    public boolean isDisposed() {
        return upstream.isDisposed();
    }

    @Override
    public boolean isEmpty() {
        return qd.isEmpty();
    }

    @Override
    public void clear() {
        qd.clear();
    }

    // -----------------------------------------------------------
    // The rest of the Queue interface methods shouldn't be called
    // -----------------------------------------------------------

    @Override
    public final boolean offer(R e) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override
    public final boolean offer(R v1, R v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }
}
