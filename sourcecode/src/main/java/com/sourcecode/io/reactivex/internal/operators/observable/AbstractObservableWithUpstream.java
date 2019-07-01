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
import com.sourcecode.io.reactivex.internal.fuseable.HasUpstreamObservableSource;

/**
 * Base class for operators with a source consumable.
 *
 * @param <T> the input source type
 * @param <U> the output type
 */
abstract class AbstractObservableWithUpstream<T, U> extends Observable<U> implements HasUpstreamObservableSource<T> {

    /**
     * The source consumable Observable.
     *
     * 上游Observable，用于在subscribe(observer)后把封装好的observer一次层层传递给源Observable
     */
    protected final ObservableSource<T> source;

    /**
     * Constructs the ObservableSource with the given consumable.
     * @param source the consumable Observable
     */
    AbstractObservableWithUpstream(ObservableSource<T> source) {
        this.source = source;
        //add by whw
        LogUtil.printObservable(getClass(), "构造函数，source类型-->" + source.getClass().getSimpleName());
        //add by whw
    }

    @Override
    public final ObservableSource<T> source() {
        //add by whw
        LogUtil.printObservable(getClass(), "source()");
        //add by whw
        return source;
    }

}
