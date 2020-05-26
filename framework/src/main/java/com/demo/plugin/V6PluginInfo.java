//package com.demo.plugin;
//
//import java.util.List;
//
//import cn.v6.sixrooms.v6library.utils.AppInfoUtils;
//import cn.v6.sixrooms.v6library.utils.ChannelUtil;
//
///**
// * Description 六间房插件化模块的插件配置信息
// * Author wanghengwei
// * Date   2019/12/6
// */
//public class V6PluginInfo {
//
//    private List<PluginListBean> pluginList;
//
//    public List<PluginListBean> getPluginList() {
//        return pluginList;
//    }
//
//    public void setPluginList(List<PluginListBean> pluginList) {
//        this.pluginList = pluginList;
//    }
//
//    public static class PluginListBean {
//
//        private String pluginName;
//        private List<PluginDetailBean> pluginDetail;
//
//        public String getPluginName() {
//            return pluginName;
//        }
//
//        public void setPluginName(String pluginName) {
//            this.pluginName = pluginName;
//        }
//
//        public List<PluginDetailBean> getPluginDetail() {
//            return pluginDetail;
//        }
//
//        public void setPluginDetail(List<PluginDetailBean> pluginDetail) {
//            this.pluginDetail = pluginDetail;
//        }
//
//        public static class PluginDetailBean {
//            /**
//             * 如果hostTargetVersion是0，说明该插件可以无限向上兼容宿主版本
//             */
//            private static final int UNLIMITED = 0;
//            /**
//             * 该插件状态：正式发布状态
//             */
//            private static final int STATUS_ACTIVE = 0;
//            /**
//             * 该插件状态：灰度发布状态
//             */
//            private static final int STATUS_GRAY = 1;
//            /**
//             * 该插件状态：废弃状态
//             */
//            private static final int STATUS_OBSOLETE = 2;
//            /********************************* 服务器字段 ******************************************/
//            private String pluginName;
//            private String packageName;
//            private String versionName;
//            private int versionCode;
//            private int hostMinVersion;
//            private int hostTargetVersion;
//            private int status;
//            private List<String> grayReleaseChannels;
//            private String downloadUrl;
//            private String md5;
//
//            /********************************* 本地字段 ******************************************/
//            private String localPath;
//
//            public String getPluginName() {
//                return pluginName;
//            }
//
//            public void setPluginName(String pluginName) {
//                this.pluginName = pluginName;
//            }
//
//            public String getPackageName() {
//                return packageName;
//            }
//
//            public void setPackageName(String packageName) {
//                this.packageName = packageName;
//            }
//
//            public String getVersionName() {
//                return versionName;
//            }
//
//            public void setVersionName(String versionName) {
//                this.versionName = versionName;
//            }
//
//            public int getVersionCode() {
//                return versionCode;
//            }
//
//            public void setVersionCode(int versionCode) {
//                this.versionCode = versionCode;
//            }
//
//            public int getHostMinVersion() {
//                return hostMinVersion;
//            }
//
//            public void setHostMinVersion(int hostMinVersion) {
//                this.hostMinVersion = hostMinVersion;
//            }
//
//            public int getHostTargetVersion() {
//                return hostTargetVersion;
//            }
//
//            public void setHostTargetVersion(int hostTargetVersion) {
//                this.hostTargetVersion = hostTargetVersion;
//            }
//
//            public int getStatus() {
//                return status;
//            }
//
//            public void setStatus(int status) {
//                this.status = status;
//            }
//
//            public List<String> getGrayReleaseChannels() {
//                return grayReleaseChannels;
//            }
//
//            public void setGrayReleaseChannels(List<String> grayReleaseChannels) {
//                this.grayReleaseChannels = grayReleaseChannels;
//            }
//
//            public String getDownloadUrl() {
//                return downloadUrl;
//            }
//
//            public void setDownloadUrl(String downloadUrl) {
//                this.downloadUrl = downloadUrl;
//            }
//
//            public String getMd5() {
//                return md5;
//            }
//
//            public void setMd5(String md5) {
//                this.md5 = md5;
//            }
//
//            public String getLocalPath() {
//                return localPath;
//            }
//
//            public void setLocalPath(String localPath) {
//                this.localPath = localPath;
//            }
//
//            /**
//             * 当前插件是否与当前宿主匹配
//             *
//             * @return true --> 匹配，false --> 不匹配
//             */
//            public boolean isMatchedWithHost() {
//                switch (status) {
//                    case STATUS_ACTIVE: //正式发布状态
//                        return isVersionMatched();
//                    case STATUS_GRAY: //灰度发布状态
//                        //遍历插件需要灰度的渠道号数组
//                        for (String channel : grayReleaseChannels) {
//                            String hostChannel = ChannelUtil.getChannelNum();
//                            //如果当前宿主渠道号在数组里，且版本匹配，则返回true
//                            if (channel.equals(hostChannel) && isVersionMatched()) {
//                                return true;
//                            }
//                        }
//                        //当前宿主渠道号不在数组里，则返回false
//                        return false;
//                    case STATUS_OBSOLETE: //过期状态
//                    default:
//                        return false;
//                }
//            }
//
//            /**
//             * 判断当前插件信息版本是否符合宿主，判断条件
//             * 1.如果hostTargetVersion == UNLIMITED，则 该宿主版本号 >= 该插件支持的最小宿主版本号
//             * 2.如果hostTargetVersion有具体数字，则在满足第一条的情况下，且 该宿主版本号 <= 该插件支持的最大宿主版本号
//             *
//             * @return true-->插件支持该宿主，false-->插件不支持该宿主
//             */
//            private boolean isVersionMatched() {
//                int hostVersionCode = AppInfoUtils.getAppCode();
//                if (hostTargetVersion == UNLIMITED) {
//                    return hostVersionCode >= hostMinVersion;
//                } else {
//                    return hostVersionCode >= hostMinVersion && hostVersionCode <= hostTargetVersion;
//                }
//            }
//
//            @Override
//            public String toString() {
//                return "PluginDetailBean{" +
//                        "pluginName='" + pluginName + '\'' +
//                        ", packageName='" + packageName + '\'' +
//                        ", versionName='" + versionName + '\'' +
//                        ", versionCode=" + versionCode +
//                        ", hostMinVersion=" + hostMinVersion +
//                        ", hostTargetVersion=" + hostTargetVersion +
//                        ", status=" + status +
//                        ", grayReleaseChannels=" + grayReleaseChannels +
//                        ", downloadUrl='" + downloadUrl + '\'' +
//                        ", md5='" + md5 + '\'' +
//                        '}';
//            }
//        }
//
//        @Override
//        public String toString() {
//            return "PluginListBean{" +
//                    "pluginName='" + pluginName + '\'' +
//                    ", pluginDetail=" + pluginDetail +
//                    '}';
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "V6PluginInfo{" +
//                "pluginList=" + pluginList +
//                '}';
//    }
//}
