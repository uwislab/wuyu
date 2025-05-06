module.exports = {
  title: '五育后台管理系统',

  /**
   * @type {boolean} true | false
   * @description 设置是否显示控制面板，设置为false则不显示
   */
  showSettings: true, 

  /**
   * @type {boolean} true | false
   * @description 是我们打开某个页面是否有页面标签
   */
  tagsView: true,  

  /**
   * @type {boolean} true | false
   * @description  内容页面向下滑动时头部是否固定，false是不固定， true是固定
   */
  fixedHeader: false, 

  /**
   * @type {boolean} true | false
   * @description # 控制菜单栏上方是否显示图标
   */
  sidebarLogo: false,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production'
}
