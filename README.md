# AnythingPull
### 目标：万能拖拽viewgroup
<br>
<br>
#### 将传统的下拉刷新和上拉加载集合弹性滑动
#### 目前支持8种模式
1.下拉刷新
<br>
2.上拉加载
<br>
3.下拉刷新上拉加载
<br>
4.下拉弹性滚动
<br>
5.上拉弹性滚动
<br>
6.下拉弹性滚动，上拉弹性滚动
<br>
7.下拉刷新，上拉弹性滚动
<br>
8.下拉弹性滚动，上拉加载
<br>
#### TODO 已完成Scrollview支持，Recyclerview支持，Listview支持，normal layout支持，刷新体需实现IPullable，在Sample目录下有笔者默认实现，拿来即用(=・ω・=)
#### 模式装载
![](https://github.com/TruthKeeper/AnythingPull/blob/master/framework.jpg)
#### Scrollview效果图
![](https://github.com/TruthKeeper/AnythingPull/blob/master/scrollview.gif)  
<br>
#### Recyclerview效果图
![](https://github.com/TruthKeeper/AnythingPull/blob/master/recyclerview.gif)  
<br>
####监听回调
```xml
  pullLayout.setOnPullListener(new AnythingPullLayout.OnPullListener() {
            @Override
            public void refreshing() {
              //弹性滑动时不回调该方法
              //刷新完毕ui线程调用，会调用iPullDown的refreshOver
              pullLayout.setRefreshResult(boolean success);
                
            }

            @Override
            public void loading() {
              //弹性滑动时不回调该方法
              //刷新完毕ui线程调用，会调用IPullUp的loadOver
              pullLayout.setLoadResult(boolean success);
            }
        });
```
####刷新头和底部加载头需实现IPullDown，IPullUp。
```xml
这里就贴个IPullDown，IPullUp雷同

 /**
     * 触摸时回调
     *
     * @param distance
     */
    void pull(float distance);

    /**
     * 触摸时，滑动距离满足 放开刷新 时回调（弹性滑动时不回调）
     */
    void releaseToRefresh();

    /**
     * 触摸时，滑动距离满足 放开变回原样 时回调（弹性滑动时不回调）
     */
    void releaseToInit();

    /**
     * 放开，触发刷新时回调（弹性滑动时不回调）
     */
    void refreshing();

    /**
     * AnythingPull结束刷新时回调
     *
     * @param isSuccess
     */
    void refreshOver(boolean isSuccess);

    /**
     * 开始滑动,初始化
     */
    void startPull();
```
```xml
IPullable：影响加载的pull模式
   /**
     * 是否可以下拉刷新
     *
     * @param hasHead 携带刷新头
     * @return
     */
    boolean canPullDown(boolean hasHead);

    /**
     * 是否可以上拉加载
     *
     * @param hasFoot 携带加载底部
     * @return
     */
    boolean canPullUp(boolean hasFoot);
```
###详情见sample-debug.apk
