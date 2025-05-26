// frontend/src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/store/authStore'

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/pages/LoginPage.vue'),
        meta: { requiresGuest: true },
    },
    {
        path: '/signup',
        name: 'SignUp',
        component: () => import('@/pages/SignUpPage.vue'),
        meta: { requiresGuest: true },
    },
    {
        path: '/ledger',
        name: 'Ledger',
        component: () => import('@/pages/LedgerPage.vue'),
        meta: { requiresAuth: true },
    },
    {
        path: '/budget',
        name: 'BudgetPage',
        component: () => import('@/pages/BudgetPage.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/report',
        name: 'ReportPage',
        component: () => import('@/pages/ReportPage.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/category',
        name: 'CategoryPage',
        component: () => import('@/pages/CategoryPage.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/',
        redirect: '/login',
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

// 보호 라우팅 로직
router.beforeEach((to, from, next) => {
    const authStore = useAuthStore()

    if (to.meta.requiresAuth && !authStore.isLoggedIn) {
        // 로그인이 안 된 경우 보호된 페이지 접근 차단
        next('/login')
    } else if (to.meta.requiresGuest && authStore.isLoggedIn) {
        // 이미 로그인한 사용자가 /login 또는 /signup에 접근 → ledger로 보냄
        next('/ledger')
    } else {
        next()
    }
})

export default router
