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

package com.sourcecode.rxjava2.io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicReference;

import com.sourcecode.rxjava2.io.reactivex.*;
import com.sourcecode.rxjava2.io.reactivex.disposables.Disposable;
import com.sourcecode.rxjava2.io.reactivex.exceptions.Exceptions;
import com.sourcecode.rxjava2.io.reactivex.functions.Function;
import com.sourcecode.rxjava2.io.reactivex.internal.disposables.*;
import com.sourcecode.rxjava2.io.reactivex.internal.functions.ObjectHelper;
import com.sourcecode.rxjava2.io.reactivex.subjects.PublishSubject;

/**
 * Shares a source Observable for the duration of a selector function.
 * @param <T> the input value type
 * @param <R> the output value type
 */
public final class ObservablePublishSelector<T, R> extends AbstractObservableWithUpstream<T, R> {

    final Function<? super Observable<T>, ? extends ObservableSource<R>> selector;

    public ObservablePublishSelector(final ObservableSource<T> source,
                                              final Function<? super Observable<T>, ? extends ObservableSource<R>> selector) {
        super(source);
        this.selector = selector;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        PublishSubject<T> subject = PublishSubject.create();

        ObservableSource<? extends R> target;

        try {
            target = ObjectHelper.requireNonNull(selector.apply(subject), "The selector returned a null ObservableSource");
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, observer);
            return;
        }

        TargetObserver<T, R> o = new TargetObserver<T, R>(observer);

        target.subscribe(o);

        source.subscribe(new SourceObserver<T, R>(subject, o));
    }

    static final class SourceObserver<T, R> implements Observer<T> {

        final PublishSubject<T> subject;

        final AtomicReference<Disposable> target;

        SourceObserver(PublishSubject<T> subject, AtomicReference<Disposable> target) {
            this.subject = subject;
            this.target = target;
        }

        @Override
        public void onSubscribe(Disposable d) {
            DisposableHelper.setOnce(target, d);
        }

        @Override
        public void onNext(T value) {
            subject.onNext(value);
        }

        @Override
        public void onError(Throwable e) {
            subject.onError(e);
        }

        @Override
        public void onComplete() {
            subject.onComplete();
        }
    }

    static final class TargetObserver<T, R>
    extends AtomicReference<Disposable> implements Observer<R>, Disposable {
        private static final long serialVersionUID = 854110278590336484L;

        final Observer<? super R> downstream;

        Disposable upstream;

        TargetObserver(Observer<? super R> downstream) {
            this.downstream = downstream;
        }

        @Override
        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.upstream, d)) {
                this.upstream = d;

                downstream.onSubscribe(this);
            }
        }

        @Override
        public void onNext(R value) {
            downstream.onNext(value);
        }

        @Override
        public void onError(Throwable e) {
            DisposableHelper.dispose(this);
            downstream.onError(e);
        }

        @Override
        public void onComplete() {
            DisposableHelper.dispose(this);
            downstream.onComplete();
        }

        @Override
        public void dispose() {
            upstream.dispose();
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return upstream.isDisposed();
        }
    }
}