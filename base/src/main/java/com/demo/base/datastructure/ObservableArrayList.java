package com.demo.base.datastructure;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description 可观察数据变化的ArrayList
 * Author henry
 * Date   2023/1/7
 */
public class ObservableArrayList<T> extends ArrayList<T> {
    private static final String TAG = "ObservableArrayList";
    private final List<OnListChangeListener<T>> mListeners = new ArrayList<>();

    public void addOnListChangedListener(OnListChangeListener<T> listener) {
        if (mListeners != null) {
            mListeners.add(listener);
        }
    }

    public void removeOnListChangedListener(OnListChangeListener<T> listener) {
        if (mListeners != null) {
            mListeners.remove(listener);
        }
    }

    @Override
    public boolean add(T object) {
        super.add(object);
        notifyAdd(size() - 1, 1);
        return true;
    }

    @Override
    public void add(int index, T object) {
        super.add(index, object);
        notifyAdd(index, 1);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> collection) {
        int oldSize = size();
        boolean added = super.addAll(collection);
        if (added) {
            notifyAdd(oldSize, size() - oldSize);
        }
        return added;
    }


    @Override
    public void clear() {
        int oldSize = size();
        super.clear();
        if (oldSize != 0) {
            notifyRemove(0, oldSize);
        }
    }

    @Override
    public T remove(int index) {
        T val = super.remove(index);
        notifyRemove(index, 1);
        return val;
    }

    @Override
    public boolean remove(Object object) {
        int index = indexOf(object);
        if (index >= 0) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public T set(int index, T object) {
        T val = super.set(index, object);
        if (mListeners != null) {
            for (OnListChangeListener<T> listener : mListeners) {
                listener.onChange(this, index, 1);
            }
        }
        return val;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        notifyRemove(fromIndex, toIndex - fromIndex);
    }

    private void notifyAdd(int start, int count) {
        if (mListeners != null) {
            for (OnListChangeListener<T> listener : mListeners) {
                listener.onAdd(this, start, count);
            }
        }
    }

    private void notifyRemove(int start, int count) {
        if (mListeners != null) {
            for (OnListChangeListener<T> listener : mListeners) {
                listener.onRemove(this, start, count);
            }
        }
    }

    public interface OnListChangeListener<T> {

        void onChange(ArrayList<T> list, int index, int count);

        void onAdd(ArrayList<T> list, int start, int count);

        void onRemove(ArrayList<T> list, int start, int count);
    }

    public static abstract class LoggableListener<T> implements OnListChangeListener<T> {
        @Override
        public void onChange(ArrayList<T> list, int index, int count) {
            Log.i(TAG, String.format("onChange -- list:%s, index:%s, count:%s", list.toString(), index, count));
        }

        @Override
        public void onAdd(ArrayList<T> list, int start, int count) {
            Log.i(TAG, String.format("onAdd -- list:%s, index:%s, count:%s", list.toString(), start, count));
        }

        @Override
        public void onRemove(ArrayList<T> list, int start, int count) {
            Log.i(TAG, String.format("onRemove -- list:%s, index:%s, count:%s", list.toString(), start, count));

        }
    }
}