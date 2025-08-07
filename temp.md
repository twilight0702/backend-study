1. 后端mass-travel-tanji
![[Pasted image 20250716095857.png]]
组件注册到nacos的问题

- 如果把注册设置为true，会显示找不到redis服务，依旧指向192.168.1.126，配置文件是不是还需要修改

更换了maven的仓库
报错内存不足
idea调整内存
（每一个项目都要手动重新调整吗）
还改了idea的一个配置文件，但是没有生效？


2. 前端mass-ui-tanji

控制台没有报错，但是页面打开f12显示

```js
vue.runtime.esm.js:619 [Vue warn]: Unknown custom element: <router-view> - did you register the component correctly? For recursive components, make sure to provide the "name" option.

found in

---> <App> at src/tanji/App.vue
       <Root>
```
路由找不到
依赖中没有vue-router，使用npm add之后依旧存在相同报错
找项目文件中有vue3的路由定义，但是整体项目是vue2的

1. 是否需要依赖基座项目
2. 前端项目结构不太了解，是否有文档说明

-> 需要依赖基座项目

是否有更详细的文档，包括前后端，以及各个项目文件之间的交互

