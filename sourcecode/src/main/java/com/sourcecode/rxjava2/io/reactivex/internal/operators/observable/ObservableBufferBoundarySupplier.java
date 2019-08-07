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

import com.sourcecode.rxjava2.io.reactivex.internal.functions.ObjectHelper;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import com.sourcecode.rxjava2.io.reactivex.*;
import com.sourcecode.rxjava2.io.reactivex.disposables.Disposable;
import com.sourcecode.rxjava2.io.reactivex.exceptions.Exceptions;
import com.sourcecode.rxjava2.io.reactivex.internal.disposables.*;
import com.sourcecode.rxjava2.io.reactivex.internal.observers.QueueDrainObserver;
import com.sourcecode.rxjava2.io.reactivex.internal.queue.MpscLinkedQueue;
import com.sourcecode.rxjava2.io.reactivex.internal.util.QueueDrainHelper;
import com.sourcecode.rxjava2.io.reactivex.observers.*;
import com.sourcecode.rxjava2.io.reactivex.plugins.RxJavaPlugins;

public final class ObservableBufferBoundarySupplier<T, U extends Collection<? super T>, B>
extends AbstractObservableWithUpstream<T, U> {
    final Callable<? extends ObservableSource<B>> boundarySupplier;
    final Callable<U> bufferSupplier;

    public ObservableBufferBoundarySupplier(ObservableSource<T> source, Callable<? extends ObservableSource<B>> boundarySupplier, Callable<U> bufferSupplier) {
        super(source);
        this.boundarySupplier = boundarySupplier;
        this.bufferSupplier = bufferSupplier;
    }

    @Override
    protected void subscribeActual(Observer<? super U> t) {
        source.subscribe(new BufferBoundarySupplierObserver<T, U, B>(new SerializedObserver<U>(t), bufferSupplier, boundarySupplier));
    }

    static final class BufferBoundarySupplierObserver<T, U extends Collection<? super T>, B>
    extends QueueDrainObserver<T, U, U> implements Observer<T>, Disposable {

        final Callable<U> bufferSupplier;
        final Callable<? extends ObservableSource<B>> boundarySupplier;

        Disposable upstream;

        final AtomicReference<Disposable> other = new AtomicReference<Disposable>();

        U buffer;

        BufferBoundarySupplierObserver(Observer<? super U> actual, Callable<U> bufferSupplier,
                                                Callable<? extends ObservableSource<B>> boundarySupplier) {
            super(actual, new MpscLinkedQueue<U>());
            this.bufferSupplier = bufferSupplier;
            this.boundarySupplier = boundarySupplier;
        }

        @Override
        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.upstream, d)) {
                this.upstream = d;

                Observer<? super U> actual = this.downstream;

                U b;

                try {
                    b = ObjectHelper.requireNonNull(bufferSupplier.call(), "The buffer supplied is null");
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    cancelled = true;
                    d.dispose();
                    EmptyDisposable.error(e, actual);
                    return;
                }

                buffer = b;

                ObservableSource<B> boundary;

                try {
                    boundary = ObjectHelper.requireNonNull(boundarySupplier.call(), "The boundary ObservableSource supplied is null");
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    cancelled = true;
                    d.dispose();
                    EmptyDisposable.error(ex, actual);
                    return;
                }

                BufferBoundaryObserver<T, U, B> bs = new BufferBoundaryObserver<T, U, B>(this);
                other.set(bs);

                actual.onSubscribe(this);

                if (!cancelled) {
                    boundary.subscribe(bs);
                }
            }
        }

        @Override
        public void onNext(T t) {
            synchronized (this) {
                U b = buffer;
                if (b == null) {
                    return;
                }
                b.add(t);
            }
        }

        @Override
        public void onError(Throwable t) {
            dispose();
            downstream.onError(t);
        }

        @Override
        public void onComplete() {
            U b;
            synchronized (this) {
                b = buffer;
                if (b == null) {
                    return;
                }
                buffer = null;
            }
            queue.offer(b);
            done = true;
            if (enter()) {
                QueueDrainHelper.drainLoop(queue, downstream, false, this, this);
            }
        }

        @Override
        public void dispose() {
            if (!cancelled) {
                cancelled = true;
                upstream.dispose();
                disposeOther();

                if (enter()) {
                    queue.clear();
                }
            }
        }

        @Override
        public boolean isDisposed() {
            return cancelled;
        }

        void disposeOther() {
            DisposableHelper.dispose(other);
        }

        void next() {

            U next;

            try {
                next = ObjectHelper.requireNonNull(bufferSupplier.call(), "The buffer supplied is null");
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                dispose();
                downstream.onError(e);
                return;
            }

            ObservableSource<B> boundary;

            try {
                boundary = ObjectHelper.requireNonNull(boundarySupplier.call(), "The boundary ObservableSource supplied is null");
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                cancelled = true;
                upstream.dispose();
                downstream.onError(ex);
                return;
            }

            BufferBoundaryObserver<T, U, B> bs = new BufferBoundaryObserver<T, U, B>(this);

            if (DisposableHelper.replace(other, bs)) {
                U b;
                synchronized (this) {
                    b = buffer;
                    if (b == null) {
                        return;
                    }
                    buffer = next;
                }

                boundary.subscribe(bs);

                fastPathEmit(b, false, this);
            }
        }

        @Override
        public void accept(Observer<? super U> a, U v) {
            downstream.onNext(v);
        }

    }

    static final class BufferBoundaryObserver<T, U extends Collection<? super T>, B>
    extends DisposableObserver<B> {
        final BufferBoundarySupplierObserver<T, U, B> parent;

        boolean once;

        BufferBoundaryObserver(BufferBoundarySupplierObserver<T, U, B> parent) {
            this.parent = parent;
        }

        @Override
        public void onNext(B t) {
            if (once) {
                return;
            }
            once = true;
            dispose();
            parent.next();
        }

        @Override
        public void onError(Throwable t) {
            if (once) {
                RxJavaPlugins.onError(t);
                return;
            }
            once = true;
            parent.onError(t);
        }

        @Override
        public void onComplete() {
            if (once) {
                return;
            }
            once = true;
            parent.next();
        }
    }
}