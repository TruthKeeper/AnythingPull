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
#### TODO 已完成Scrollview支持，Recyclerview中LinearLayoutManager，GridLayoutManager支持，Listview支持
#### 模式装载
![](https://github.com/TruthKeeper/AnythingPull/blob/master/sample/src/main/res/raw/framework.png)
#### Scrollview效果图
![](https://github.com/TruthKeeper/AnythingPull/blob/master/sample/src/main/res/raw/scrollview.gif)  
<br>
```xml
pullLayout.setOnStatusChangeListener(new AnythingPullLayout.OnStatusChangeListener() {
            @Override
            public void onChange(int status, int direction, float distance) {
                if (direction == AnythingPullLayout.DIRECTION_DOWN) {
                  //设置headerview的distance偏移量
                } else {
                    //设置footview的distance偏移量
                }
                if (status == AnythingPullLayout.REFRESHING) {
                    //开始执行刷新数据
                } else if (status == AnythingPullLayout.LOADING) {
                   //开始执行加载数据
                }
            }
        });
        //TODO 自动下拉刷新
        pullLayout.autoRefresh();
```
