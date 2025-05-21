// frontend/src/store/authStore.js
import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/axios'

export const useAuthStore = defineStore('auth', () => {
    const token = ref(localStorage.getItem('token') || '')
    const isLoggedIn = ref(!!token.value)

    const login = async ({ email, password }) => {
        try {
            // ✅ baseURL: '/api'라면 경로에 /auth/login
            const response = await api.post('/auth/login', { email, password })

            // ✅ JWT 토큰 응답 확인
            if (!response.data.token) throw new Error('토큰 없음')

            token.value = response.data.token
            localStorage.setItem('token', token.value)
            isLoggedIn.value = true
        } catch (error) {
            console.error('로그인 실패:', error.response?.data || error.message)
            throw new Error('로그인 실패')
        }
    }

    const logout = () => {
        token.value = ''
        isLoggedIn.value = false
        localStorage.removeItem('token')
    }

    return {
        token,
        isLoggedIn,
        login,
        logout,
    }
})
