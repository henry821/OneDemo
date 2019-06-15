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

package com.sourcecode.io.reactivex.internal.operators.completable;

import com.sourcecode.io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;

import com.sourcecode.io.reactivex.*;
import com.sourcecode.io.reactivex.exceptions.Exceptions;
import com.sourcecode.io.reactivex.internal.disposables.EmptyDisposable;

public final class CompletableErrorSupplier extends Completable {

    final Callable<? extends Throwable> errorSupplier;

    public CompletableErrorSupplier(Callable<? extends Throwable> errorSupplier) {
        this.errorSupplier = errorSupplier;
    }

    @Override
    protected void subscribeActual(CompletableObserver observer) {
        Throwable error;

        try {
            error = ObjectHelper.requireNonNull(errorSupplier.call(), "The error returned is null");
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            error = e;
        }

        EmptyDisposable.error(error, observer);
    }

}