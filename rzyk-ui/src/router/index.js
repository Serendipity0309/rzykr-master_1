import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from '@/layout'


// 公共路由
export const constantRoutes = [{
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [{
            path: '/redirect/:path(.*)',
            component: () => import('@/views/redirect')
        }]
    },

    {
        path: '/login',
        component: () => import('@/views/login'),
        hidden: true
    },
    {
        path: '/notice',
        component: () => import('@/views/rzykapi/warmprompt/pubnotice'),
        hidden: true
    },
    {
        path: '/register',
        component: () => import('@/views/register'),
        hidden: true
    },
    {
        path: '/404',
        component: () => import('@/views/error/404'),
        hidden: true
    },
    {
        path: '/401',
        component: () => import('@/views/error/401'),
        hidden: true
    },
    {
        path: '/loginByCode',
        component: () => import('@/views/loginByCode'),
        hidden: true
    },
    {
        path: '/turnover',
        component: () => import('@/views/turnover'),
        hidden: true
    },
    {
        path: '',
        component: Layout,
        redirect: 'index',
        children: [{
            path: 'index',
            component: () => import('@/views/index'),
            name: 'Index',
            meta: {
                title: '首页',
                icon: 'dashboard',
                affix: true
            }
        }]
    },
    {
        path: '/schedule',
        component: Layout,
        children: [{
            path: '/schedule/print',
            component: () => import('@/views/rzykapi/schedule/index'),
            name: 'print',
            hidden: true,
            meta: {
                title: '打印安排表',
            }
        }]
    },
    // {
    //     path: '/schedule',
    //     component: Layout,
    //     children: [{
    //         path: '/schedule/test',
    //         component: () => import('@/views/rzykapi/schedule/tester'),
    //         name: 'tester',
    //         meta: {
    //             title: '安排表维护',
    //         }
    //     }]
    // },

    {
        path: '/schedule',
        component: Layout,
        children: [{
            path: '/schedule/warmPormpt',
            component: () => import('@/views/rzykapi/warmprompt/index'),
            name: 'warmpormpt',
            hidden: true,
            meta: {
                title: '提示语管理',
            }
        }]
    },
    {
        path: '/rzykapi/pubnotice',
        component: () => import('@/views/rzykapi/warmprompt/pubnotice'),
        hidden: true,
        permissions: ['code:code:data']
    },
    {
        path: '/teacherInfo/examInfo',
        component: () => import('@/views/rzykapi/teacherInfo/examInfo'),
    },
    {
        path: '/printlist',
        component: Layout,
        children: [{
            path: '/printlist',
            component: () => import('@/views/rzykapi/schedule/printdata'),
            name: 'vueqr',
            hidden: true,
            meta: {
                title: '安排表维护',

            }
        }]
    },
    {
        path: '/user',
        component: Layout,
        hidden: true,
        redirect: 'noredirect',
        children: [{
            path: 'profile',
            component: () => import('@/views/system/user/profile/index'),
            name: 'Profile',
            meta: {
                title: '个人中心',
                icon: 'user'
            }
        }]
    }
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [{
        path: '/system/user-auth',
        component: Layout,
        hidden: true,
        permissions: ['system:user:edit'],
        children: [{
            path: 'role/:userId(\\d+)',
            component: () => import('@/views/system/user/authRole'),
            name: 'AuthRole',
            meta: {
                title: '分配角色',
                activeMenu: '/system/user'
            }
        }]
    },

    {
        path: '/system/role-auth',
        component: Layout,
        hidden: true,
        permissions: ['system:role:edit'],
        children: [{
            path: 'user/:roleId(\\d+)',
            component: () => import('@/views/system/role/authUser'),
            name: 'AuthUser',
            meta: {
                title: '分配用户',
                activeMenu: '/system/role'
            }
        }]
    },
    {
        path: '/system/dict-data',
        component: Layout,
        hidden: true,
        permissions: ['system:dict:list'],
        children: [{
            path: 'index/:dictId(\\d+)',
            component: () => import('@/views/system/dict/data'),
            name: 'Data',
            meta: {
                title: '字典数据',
                activeMenu: '/system/dict'
            }
        }]
    },
    {
        path: '/monitor/job-log',
        component: Layout,
        hidden: true,
        permissions: ['monitor:job:list'],
        children: [{
            path: 'index',
            component: () => import('@/views/monitor/job/log'),
            name: 'JobLog',
            meta: {
                title: '调度日志',
                activeMenu: '/monitor/job'
            }
        }]
    },
    {
        path: '/tool/gen-edit',
        component: Layout,
        hidden: true,
        permissions: ['tool:gen:edit'],
        children: [{
            path: 'index/:tableId(\\d+)',
            component: () => import('@/views/tool/gen/editTable'),
            name: 'GenEdit',
            meta: {
                title: '修改生成配置',
                activeMenu: '/tool/gen'
            }
        }]
    }
]

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push;
Router.prototype.push = function push(location) {
    return routerPush.call(this, location).catch(err => err)
}

export default new Router({
    mode: 'history', // 去掉url中的#
    scrollBehavior: () => ({
        y: 0
    }),
    routes: constantRoutes
})
