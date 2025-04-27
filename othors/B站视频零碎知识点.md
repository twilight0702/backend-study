## 如何快速判断几十亿个数中是否存在某数

[原视频](https://www.bilibili.com/list/watchlater?spm_id_from=333.881.0.0&watchlater_cfg=%7B%22viewed%22%3A0,%22key%22%3A%22%22,%22asc%22%3Afalse%7D&oid=114341038791800&bvid=BV1FjdZYCE8c)

1. 遍历

2. 排序+二分

3. 多线程，分段检索

   内存占用问题

4. 位图

   一串数字，看下标，有这个数字的地方置为1，没有的置为0，就可以快速检索，同时内存占用较少

   可以用redis的位图

