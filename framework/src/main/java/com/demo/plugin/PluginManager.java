//package com.demo.plugin;
//
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//
//import androidx.annotation.Nullable;
//
//import com.google.gson.Gson;
//import com.google.gson.stream.JsonReader;
//import com.qihoo360.replugin.RePlugin;
//import com.qihoo360.replugin.RePluginCallbacks;
//import com.qihoo360.replugin.RePluginEventCallbacks;
//import com.qihoo360.replugin.model.PluginInfo;
//import com.tencent.bugly.crashreport.CrashReport;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//import cn.v6.sixrooms.bean.V6PluginInfo;
//import cn.v6.sixrooms.event.PluginEvent;
//import cn.v6.sixrooms.request.api.DownloadPluginApi;
//import cn.v6.sixrooms.v6library.event.EventManager;
//import cn.v6.sixrooms.v6library.manager.OkHttpManager;
//import cn.v6.sixrooms.v6library.network.CustomGsonConverterFactory;
//import cn.v6.sixrooms.v6library.utils.LogUtils;
//import cn.v6.sixrooms.v6library.utils.MD5Utils;
//import cn.v6.sixrooms.v6library.utils.ToastUtils;
//import cn.v6.sixrooms.v6library.utils.UserInfoUtils;
//import cn.v6.sixrooms.v6library.utils.bitmap.SaveFileUtils;
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.ObservableSource;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//import io.reactivex.functions.Function;
//import io.reactivex.schedulers.Schedulers;
//import okhttp3.ResponseBody;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//
///**
// * Description 插件管理类
// * Author wanghengwei
// * Date   2019/11/26
// */
//public class PluginManager {
//
//    private static final String TAG = "【插件管理类】";
//    /**
//     * 插件安装成功发送的事件
//     */
//    public static final String EVENT_PLUGIN_INSTALL_SUCCESS = "event_plugin_install_success";
//    /**
//     * 插件下载地址
//     */
//    private static final String PLUGIN_BASE_URL =
//            "http://vr0.xiu123.cn/images/app/androidres/plugin/";
//    /**
//     * 配置文件文件名
//     */
//    private static final String PLUGIN_CONFIG_FILE_NAME = "plugin_config.json";
//    /**
//     * 记录正在加载过程的插件集合
//     */
//    private Set<String> mInProcessSet;
//    private Retrofit mRetrofit;
//    private InputStream mInputStream;
//    private OutputStream mOutputStream;
//    private Gson mGson;
//
//    public static PluginManager getInstance() {
//        return PluginManagerHolder.INSTANCE;
//    }
//
//    private Set<String> getInProcessSet() {
//        return mInProcessSet;
//    }
//
//    /**
//     * 加载插件，包含以下过程：
//     * 1.下载插件配置信息{@link #downloadConfig()}
//     * 2.解析插件配置信息{@link #parseConfig(String)}
//     * 3.根据插件配置信息找到宿主对应的插件信息{@link #findMatchedPlugin(String, V6PluginInfo)}
//     * 4.检查该插件是否需要下载{@link #checkPluginNeedDownload(V6PluginInfo.PluginListBean.PluginDetailBean)}
//     * 5.下载插件 {@link #downloadPlugin(V6PluginInfo.PluginListBean.PluginDetailBean)}
//     * 6.检查插件有效性{@link #checkPluginStatus(V6PluginInfo.PluginListBean.PluginDetailBean)}
//     * 7.安装插件 {@link #installPlugin(String)}
//     * 8.预加载插件 {@link #preloadPlugin(PluginInfo)}
//     * <p>
//     * 其中某个环节出错直接退出方法
//     *
//     * @param pluginName 插件名
//     */
//    public void loadPlugin(final String pluginName) {
//        //如果此插件正在加载，则不做任何操作
//        if (mInProcessSet.contains(pluginName)) {
//            return;
//        }
//        printLog("开始加载插件：" + pluginName);
//        Disposable disposable = downloadConfig()
//                .observeOn(Schedulers.io())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) {
//                        mInProcessSet.add(pluginName);
//                        printLog("开始加载插件，插件名：" + pluginName + " , pluginSet容量：" + mInProcessSet.size());
//                    }
//                })
//                .flatMap(new Function<String, ObservableSource<V6PluginInfo>>() {
//                    @Override
//                    public ObservableSource<V6PluginInfo> apply(String s) {
//                        return parseConfig(s);
//                    }
//                })
//                .flatMap(new Function<V6PluginInfo, ObservableSource<V6PluginInfo.PluginListBean.PluginDetailBean>>() {
//                    @Override
//                    public ObservableSource<V6PluginInfo.PluginListBean.PluginDetailBean> apply(V6PluginInfo v6PluginInfo) {
//                        return findMatchedPlugin(pluginName, v6PluginInfo);
//                    }
//                })
//                .flatMap(new Function<V6PluginInfo.PluginListBean.PluginDetailBean, ObservableSource<V6PluginInfo.PluginListBean.PluginDetailBean>>() {
//                    @Override
//                    public ObservableSource<V6PluginInfo.PluginListBean.PluginDetailBean> apply(V6PluginInfo.PluginListBean.PluginDetailBean pluginDetailBean) {
//                        return checkPluginNeedDownload(pluginDetailBean);
//                    }
//                })
//                .flatMap(new Function<V6PluginInfo.PluginListBean.PluginDetailBean,
//                        ObservableSource<V6PluginInfo.PluginListBean.PluginDetailBean>>() {
//                    @Override
//                    public ObservableSource<V6PluginInfo.PluginListBean.PluginDetailBean> apply(
//                            V6PluginInfo.PluginListBean.PluginDetailBean pluginDetailBean) {
//                        return downloadPlugin(pluginDetailBean);
//                    }
//                })
//                .observeOn(Schedulers.io())
//                .flatMap(new Function<V6PluginInfo.PluginListBean.PluginDetailBean, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(V6PluginInfo.PluginListBean.PluginDetailBean detailBean) {
//                        return checkPluginStatus(detailBean);
//                    }
//                })
//                .flatMap(new Function<String, ObservableSource<PluginInfo>>() {
//                    @Override
//                    public ObservableSource<PluginInfo> apply(String s) {
//                        return installPlugin(s);
//                    }
//                })
//                .flatMap(new Function<PluginInfo, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(PluginInfo pluginInfo) {
//                        return preloadPlugin(pluginInfo);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        doEndAction(s, null);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) {
//                        doEndAction(pluginName, throwable);
//                    }
//                });
//
//    }
//
//    /**
//     * 下载插件配置表
//     *
//     * @return Observable
//     */
//    private Observable<String> downloadConfig() {
//        printLog("开始下载插件配置表");
//        return commonDownload(PLUGIN_CONFIG_FILE_NAME, PLUGIN_CONFIG_FILE_NAME);
//    }
//
//    /**
//     * 解析配置文件
//     *
//     * @param filePath 配置文件本地路径
//     * @return Observable
//     */
//    private Observable<V6PluginInfo> parseConfig(final String filePath) {
//        printLog("开始解析配置文件");
//        return Observable.create(new ObservableOnSubscribe<V6PluginInfo>() {
//            @Override
//            public void subscribe(ObservableEmitter<V6PluginInfo> e) throws Exception {
//                V6PluginInfo v6PluginInfo = mGson.fromJson(new JsonReader(new FileReader(filePath)), V6PluginInfo.class);
//                e.onNext(v6PluginInfo);
//            }
//        });
//    }
//
//    /**
//     * 获取匹配的插件信息
//     *
//     * @param pluginName 插件名
//     * @param pluginInfo 插件信息
//     * @return Observable
//     */
//    private Observable<V6PluginInfo.PluginListBean.PluginDetailBean> findMatchedPlugin(final String pluginName, final V6PluginInfo pluginInfo) {
//        printLog("开始从配置列表中寻找符合版本的插件信息");
//        return Observable.create(new ObservableOnSubscribe<V6PluginInfo.PluginListBean.PluginDetailBean>() {
//            @Override
//            public void subscribe(ObservableEmitter<V6PluginInfo.PluginListBean.PluginDetailBean> e) {
//                //从多插件列表里找到对应该插件名的插件版本列表
//                for (V6PluginInfo.PluginListBean pluginListBean : pluginInfo.getPluginList()) {
//                    if (pluginListBean.getPluginName().equals(pluginName)) {
//                        //从插件版本列表中找到插件兼容宿主版本的插件信息
//                        for (V6PluginInfo.PluginListBean.PluginDetailBean pluginDetailBean : pluginListBean.getPluginDetail()) {
//                            if (pluginDetailBean.isMatchedWithHost()) {
//                                e.onNext(pluginDetailBean);
//                                return;
//                            }
//                        }
//                    }
//                }
//                printLog("在配置中未找到符合的插件信息，不管当前宿主里是否已安装同名插件，先卸载");
//                RePlugin.uninstall(pluginName);
//                e.onNext(null);
//            }
//        });
//    }
//
//    /**
//     * 检查插件是否需要下载，检查两点：
//     * 1.插件是否已经安装
//     * 2.安装的插件是否是最新版本
//     *
//     * @param pluginDetail 插件信息
//     * @return Observable
//     */
//    private Observable<V6PluginInfo.PluginListBean.PluginDetailBean> checkPluginNeedDownload(final V6PluginInfo.PluginListBean.PluginDetailBean pluginDetail) {
//        return Observable
//                .create(new ObservableOnSubscribe<V6PluginInfo.PluginListBean.PluginDetailBean>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<V6PluginInfo.PluginListBean.PluginDetailBean> e) {
//                        printLog("开始检查插件是否被安装 " + pluginDetail.toString());
//                        if (RePlugin.isPluginInstalled(pluginDetail.getPluginName())) {
//                            PluginInfo pluginInfo = RePlugin.getPluginInfo(pluginDetail.getPluginName());
//                            if (pluginInfo == null) {
//                                printLog("插件已安装但未得到插件信息(奇怪……)，先卸载该插件");
//                                RePlugin.uninstall(pluginDetail.getPluginName());
//                                e.onNext(pluginDetail);
//                            } else {
//                                printLog("已安装插件信息：" + pluginInfo.toString());
//                                if (pluginDetail.getVersionCode() == pluginInfo.getVersion()) {
//                                    EventManager.getDefault().nodifyObservers(new PluginEvent(EVENT_PLUGIN_INSTALL_SUCCESS), null);
//                                    e.onError(new Throwable("该插件已经安装且是最新版本，无需下载"));
//                                } else {
//                                    printLog("已安装插件和待下载插件信息不一致，为防止旧插件和新宿主不兼容，先卸载该插件");
//                                    RePlugin.uninstall(pluginDetail.getPluginName());
//                                    e.onNext(pluginDetail);
//                                }
//                            }
//                        } else {
//                            e.onNext(pluginDetail);
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 下载插件
//     *
//     * @return Observable
//     */
//    private Observable<V6PluginInfo.PluginListBean.PluginDetailBean> downloadPlugin(final V6PluginInfo.PluginListBean.PluginDetailBean pluginDetailBean) {
//        printLog("开始下载插件 " + pluginDetailBean.toString());
//        return commonDownload(pluginDetailBean.getDownloadUrl(), pluginDetailBean.getPluginName() + ".apk")
//                .map(new Function<String, V6PluginInfo.PluginListBean.PluginDetailBean>() {
//                    @Override
//                    public V6PluginInfo.PluginListBean.PluginDetailBean apply(String localPath) {
//                        pluginDetailBean.setLocalPath(localPath);
//                        return pluginDetailBean;
//                    }
//                });
//    }
//
//    /**
//     * 检查插件状态，检查两点
//     * 1.本地是否存在
//     * 2.md5是否匹配
//     *
//     * @param pluginDetailBean 插件详细信息
//     * @return Observable
//     */
//    private Observable<String> checkPluginStatus(final V6PluginInfo.PluginListBean.PluginDetailBean pluginDetailBean) {
//        return Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) {
//                printLog("开始检查插件是否在本地存在，插件名 :" + pluginDetailBean.getPluginName());
//                File file = new File(pluginDetailBean.getLocalPath());
//                if (file.exists()) {
//                    String fileMD5 = MD5Utils.getFileMD5(file);
//                    printLog("文件md5值：" + fileMD5);
//                    if (pluginDetailBean.getMd5().equals(fileMD5)) {
//                        e.onNext(pluginDetailBean.getLocalPath());
//                    } else {
//                        boolean delete = file.delete();
//                        printLog("文件md5不匹配，删除文件" + (delete ? "成功" : "失败"));
//                        e.onError(new Throwable("插件md5不匹配"));
//                    }
//                } else {
//                    e.onError(new Throwable("插件不存在"));
//                }
//            }
//        });
//    }
//
//    /**
//     * 安装插件
//     *
//     * @param pluginPath 插件本地路径
//     * @return Observable
//     */
//    private Observable<PluginInfo> installPlugin(final String pluginPath) {
//        return Observable
//                .create(new ObservableOnSubscribe<PluginInfo>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<PluginInfo> e) {
//                        printLog("开始安装插件，插件本地路径：" + pluginPath);
//                        PluginInfo info = RePlugin.install(pluginPath);
//                        e.onNext(info);
//                    }
//                });
//    }
//
//    /**
//     * 预加载插件
//     *
//     * @param pluginInfo 插件信息
//     * @return Observable
//     */
//    private Observable<String> preloadPlugin(final PluginInfo pluginInfo) {
//        return Observable
//                .create(new ObservableOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<String> e) {
//                        printLog("开始预加载插件，插件信息：" + pluginInfo.toString());
//                        RePlugin.preload(pluginInfo);
//                        e.onNext(pluginInfo.getName());
//                    }
//                });
//    }
//
//    /**
//     * 做结尾操作
//     *
//     * @param pluginName 插件名
//     * @param e          异常，可为null
//     */
//    private void doEndAction(String pluginName, @Nullable Throwable e) {
//        mInProcessSet.remove(pluginName);
//        printLog("加载插件结束，插件名：" + pluginName + "，pluginSet容量 : " + mInProcessSet.size());
//        printLog(e == null ? "加载插件成功" : "加载插件失败，原因：" + e.getMessage());
//    }
//
//    /**
//     * 打印log，测试用
//     *
//     * @param content log内容
//     */
//    private void printLog(String content) {
//        LogUtils.e(TAG, content + " --- 当前线程：" + Thread.currentThread().getName());
//    }
//
//    /**
//     * 通用下载方法
//     *
//     * @param downloadUrl 文件下载链接(相对路径)
//     * @param fileName    文件名
//     * @return Observable
//     */
//    private Observable<String> commonDownload(String downloadUrl, final String fileName) {
//        return mRetrofit.create(DownloadPluginApi.class)
//                .download(downloadUrl + "?time=" + System.currentTimeMillis())
//                .map(new Function<ResponseBody, String>() {
//                    @Override
//                    public String apply(ResponseBody responseBody) {
//                        printLog("开始保存文件");
//                        //创建保存文件
//                        File file = new File(SaveFileUtils.getPluginPath(), fileName);
//                        if (!file.exists()) {
//                            try {
//                                boolean mkdirs = file.getParentFile().mkdirs();
//                                if (mkdirs) {
//                                    boolean newFile = file.createNewFile();
//                                    if (!newFile) {
//                                        throw new FileNotFoundException("创建文件失败");
//                                    }
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        //拿到数据流，写入文件
//                        mInputStream = responseBody.byteStream();
//
//                        byte[] buffer = new byte[2048];
//
//                        try {
//                            mOutputStream = new FileOutputStream(file);
//                            int len;
//                            while ((len = mInputStream.read(buffer)) != -1) {
//                                mOutputStream.write(buffer, 0, len);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        return file.getPath();
//                    }
//                })
//                .doOnNext(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        if (mInputStream != null) {
//                            try {
//                                mInputStream.close();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (mOutputStream != null) {
//                            try {
//                                mOutputStream.close();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                })
//                .onErrorResumeNext(new ObservableSource<String>() {
//                    @Override
//                    public void subscribe(Observer<? super String> observer) {
//                        observer.onError(new Throwable("下载遇到问题"));
//                    }
//                });
//    }
//
//    private PluginManager() {
//        mInProcessSet = Collections.synchronizedSet(new HashSet<String>());
//        mGson = new Gson();
//        mRetrofit = new Retrofit.Builder()
//                .client(OkHttpManager.getInstance().getOkHttpClient(OkHttpManager.TYPE_NETWORK))
//                .addConverterFactory(CustomGsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
//                .baseUrl(PLUGIN_BASE_URL)
//                .build();
//    }
//
//    private static final class PluginManagerHolder {
//        private static final PluginManager INSTANCE = new PluginManager();
//    }
//
//    public static class SixRoomsRePluginCallbacks extends RePluginCallbacks {
//
//        public SixRoomsRePluginCallbacks(Context context) {
//            super(context);
//        }
//
//        @Override
//        public boolean onPluginNotExistsForActivity(Context context, String plugin, Intent intent,
//                                                    int process) {
//            Set<String> inProcessSet = getInstance().getInProcessSet();
//            if (inProcessSet == null) {
//                return true;
//            }
//            ToastUtils.showToast("正在加载功能……");
//            getInstance().loadPlugin(plugin);
//            //上报信息
//            ComponentName component = intent.getComponent();
//            String msg = "没有找到插件中的Activity(onPluginNotExistsForActivity)，uid = " + UserInfoUtils.getLoginUID() +
//                    "，插件名称：" + plugin +
//                    "，Component信息：" + (component == null ? "null" : component.toString());
//            CrashReport.postCatchedException(new Throwable(msg));
//            return true;
//        }
//    }
//
//    public static class SixRoomsRePluginEventCallbacks extends RePluginEventCallbacks {
//
//        public SixRoomsRePluginEventCallbacks(Context context) {
//            super(context);
//        }
//
//        @Override
//        public void onInstallPluginSucceed(PluginInfo info) {
//            EventManager.getDefault().nodifyObservers(new PluginEvent(EVENT_PLUGIN_INSTALL_SUCCESS), null);
//        }
//
//        @Override
//        public void onInstallPluginFailed(String path, InstallResult code) {
//            //上报信息
//            String msg = "插件安装失败(onInstallPluginFailed)，uid = " + UserInfoUtils.getLoginUID() +
//                    "，插件存放路径：" + path +
//                    "，安装失败原因：" + code.toString();
//            CrashReport.postCatchedException(new Throwable(msg));
//        }
//
//        @Override
//        public void onStartActivityCompleted(String plugin, String activity, boolean result) {
//            if (!result) {
//                //上报信息
//                String msg =
//                        "启动Activity失败(onStartActivityCompleted)，uid = " + UserInfoUtils.getLoginUID() +
//                                "，插件名称：" + plugin +
//                                "，插件Activity名称：" + activity;
//                CrashReport.postCatchedException(new Throwable(msg));
//            }
//        }
//    }
//}
