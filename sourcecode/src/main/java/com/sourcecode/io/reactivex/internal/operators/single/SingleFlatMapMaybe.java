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

package com.sourcecode.io.reactivex.internal.operators.single;

import com.sourcecode.io.reactivex.Maybe;
import com.sourcecode.io.reactivex.MaybeObserver;
import com.sourcecode.io.reactivex.MaybeSource;
import com.sourcecode.io.reactivex.SingleObserver;
import com.sourcecode.io.reactivex.SingleSource;
import com.sourcecode.io.reactivex.disposables.Disposable;
import com.sourcecode.io.reactivex.exceptions.Exceptions;
import com.sourcecode.io.reactivex.functions.Function;
import com.sourcecode.io.reactivex.internal.disposables.DisposableHelper;
import com.sourcecode.io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleFlatMapMaybe<T, R> extends Maybe<R> {

    final SingleSource<? extends T> source;

    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;

    public SingleFlatMapMaybe(SingleSource<? extends T> source, Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
        this.mapper = mapper;
        this.source = source;
    }

    @Override
    protected void subscribeActual(MaybeObserver<? super R> downstream) {
        source.subscribe(new FlatMapSingleObserver<T, R>(downstream, mapper));
    }

    static final class FlatMapSingleObserver<T, R>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>, Disposable {

        private static final long serialVersionUID = -5843758257109742742L;

        final MaybeObserver<? super R> downstream;

        final Function<? super T, ? extends MaybeSource<? extends R>> mapper;

        FlatMapSingleObserver(MaybeObserver<? super R> actual, Function<? super T, ? extends MaybeSource<? extends R>> mapper) {
            this.downstream = actual;
            this.mapper = mapper;
        }

        @Override
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override
        public boolean isDisposed() {
            return DisposableHelper.isDisposed(get());
        }

        @Override
        public void onSubscribe(Disposable d) {
            if (DisposableHelper.setOnce(this, d)) {
                downstream.onSubscribe(this);
            }
        }

        @Override
        public void onSuccess(T value) {
            MaybeSource<? extends R> ms;

            try {
                ms = ObjectHelper.requireNonNull(mapper.apply(value), "The mapper returned a null MaybeSource");
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                onError(ex);
                return;
            }

            if (!isDisposed()) {
                ms.subscribe(new FlatMapMaybeObserver<R>(this, downstream));
            }
        }

        @Override
        public void onError(Throwable e) {
            downstream.onError(e);
        }
    }

    static final class FlatMapMaybeObserver<R> implements MaybeObserver<R> {

        final AtomicReference<Disposable> parent;

        final MaybeObserver<? super R> downstream;

        FlatMapMaybeObserver(AtomicReference<Disposable> parent, MaybeObserver<? super R> downstream) {
            this.parent = parent;
            this.downstream = downstream;
        }

        @Override
        public void onSubscribe(final Disposable d) {
            DisposableHelper.replace(parent, d);
        }

        @Override
        public void onSuccess(final R value) {
            downstream.onSuccess(value);
        }

        @Override
        public void onError(final Throwable e) {
            downstream.onError(e);
        }

        @Override
        public void onComplete() {
            downstream.onComplete();
        }
    }
}