# OrderSmooth

## 系统描述
二维码点餐系统

## 竞品分析
https://orderr.biz/?gclid=Cj0KCQjw1aOpBhCOARIsACXYv-fn6H6azT6iwSaPC_rkMmDAk1v_2yUNhe5yjBXrOm6a9EJtwM4EZuAaAmrkEALw_wcB
https://squareup.com/jp/ja/townsquare/qrcode-ordering-system-basics
http://47.91.87.150/public/portal/index.html

## 开发策略
- 快速原型

## 需求分析
- 拒绝复杂 所见即所得 易于使用
- 信息的实时性非常重要（下单完成 菜品更新等都需要及时反映）
- 几乎永久的保留登陆状态

- 系统开源可自行部署
- 也可由第三方运营但要平摊运营成本（收月/年费） 可免费试用
- 若没能力部署可收费上门部署

- 客户端对应手机和平板的UI（响应式布局）
- 兼容点餐和包餐模式
- 多语言（国际化）

- 良好的可扩展性
- 注意对应多种支付流程（支付功能将在以后实现）

## 用例分析
- 用户端
    - 扫码：用户首先扫描QR码登入系统（同时携带位置信息以免系统被恶意利用）后进行点餐
    - 浏览餐品 点餐 下单 浏览餐品 ...
    - 结账：
        - 1.用餐结束后直接起身结账（与系统无互动） 
        - 2.或拿起小票起身结账（与系统无互动）
        - 3.或在系统中结账？（有不同的支付选择 现金 信用卡 paypay等）

- 管理端
    - 注册：使用邮箱或手机号（支持多国家）注册
    - 登陆：支持Google wechat LINE 手机验证码等登陆方式
    - 员工管理
    - 权限控制
    - 上传/更新菜品
    - 打印QR码
    - 支付月/年费

- 厨房端
    - 登陆：通过管理者添加账户
    - 制作餐品：按列表制作餐品
    - 周知送餐：餐品制作完成时周知送餐

- 收银端
    - 登陆：通过管理者添加账户
    - 收银：对应1 2情况 3只需查看支付情况

## 技术选型
- 前后端分离 前端VUE 后端Java 框架Spring 实现restfulAPI
- vscode docker maven git

## 盈利方式
- 付费制（每月/每年）

采用中心化的管理方式 店家甚至不需要服务器架设 初期成本非常小 易于推广