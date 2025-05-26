// frontend/src/store/authStore.js
import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/axios'
import { jwtDecode } from 'jwt-decode'

export const useAuthStore = defineStore('auth', () => {
    const token = ref('')
    const isLoggedIn = ref(false)
    const email = ref('')
    const nickname = ref('')
    const expiresAt = ref(null) // ⬅️ 추가

    const loadFromStorage = () => {
        const savedToken = localStorage.getItem('token')
        if (savedToken) {
            token.value = savedToken
            email.value = localStorage.getItem('email') || ''
            nickname.value = localStorage.getItem('nickname') || ''
            expiresAt.value = parseInt(localStorage.getItem('expiresAt')) || null
            isLoggedIn.value = true
        }
    }

    const login = async ({ email: userEmail, password }) => {
        try {
            const response = await api.post('/auth/login', { email: userEmail, password })

            token.value = response.data.token
            email.value = response.data.email
            nickname.value = response.data.nickname

            // 🔐 JWT decode 후 exp 계산
            const decoded = jwtDecode(response.data.token)
            expiresAt.value = decoded.exp * 1000 // 초 → 밀리초

            // 저장
            localStorage.setItem('token', token.value)
            localStorage.setItem('email', email.value)
            localStorage.setItem('nickname', nickname.value)
            localStorage.setItem('expiresAt', expiresAt.value)

            isLoggedIn.value = true
        } catch (error) {
            console.error(error)
            throw new Error('로그인 실패')
        }
    }

    const logout = () => {
        token.value = ''
        isLoggedIn.value = false
        email.value = ''
        nickname.value = ''
        expiresAt.value = null
        localStorage.clear()
    }

    loadFromStorage()

    return {
        token,
        isLoggedIn,
        email,
        nickname,
        expiresAt,
        login,
        logout,
    }
})
