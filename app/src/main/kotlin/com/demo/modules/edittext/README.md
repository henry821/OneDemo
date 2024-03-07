# EditText Demo

## 1.windowSoftInputMode属性
| 属性名称               | 解释                                                |
|--------------------|---------------------------------------------------|
| stateUnspecified   | 软键盘的状态并没有指定，系统将选择一个合适的状态或依赖于主题的设置                 |
| stateUnchanged     | 当这个activity出现时，软键盘将一直保持在上一个activity里的状态，无论是隐藏还是显示 |
| stateHidden        | 用户选择activity时，软键盘总是被隐藏                            |
| stateAlwaysHidden  | 当该Activity主窗口获取焦点时，软键盘也总是被隐藏的                     |
| stateVisible       | 软键盘通常是可见的                                         |
| stateAlwaysVisible | 用户选择activity时，软键盘总是显示的状态                          |
| adjustUnspecified  | 默认设置，通常由系统自行决定是隐藏还是显示                             |
| adjustResize       | 该Activity总是调整屏幕的大小以便留出软键盘的空间                      |
| adjustPan          | 当前窗口的内容将自动移动以便当前焦点从不被键盘覆盖和用户能总是看到输入内容的部分          |

## 2.软键盘动画适配
[实现边到边的体验 | 让您的软键盘动起来 (一)](https://zhuanlan.zhihu.com/p/277342333)
[响应视窗属性动画 | 让您的软键盘动起来 (二)](https://zhuanlan.zhihu.com/p/343022200)