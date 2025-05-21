// frontend/src/store/authStore.js
import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/axios'

export const useAuthStore = defineStore('auth', () => {
    const token = ref('')
    const isLoggedIn = ref(false)
    const email = ref('')
    const nickname = ref('')

    // ✅ 정의 즉시 localStorage에서 로그인 상태 복원
    const loadFromStorage = () => {
        const savedToken = localStorage.getItem('token')
        if (savedToken) {
            token.value = savedToken
            email.value = localStorage.getItem('email') || ''
            nickname.value = localStorage.getItem('nickname') || ''
            isLoggedIn.value = true
        }
    }

    loadFromStorage() // ✅ store 정의 시 즉시 실행됨

    const login = async ({ email: userEmail, password }) => {
        try {
            const response = await api.post('/auth/login', { email: userEmail, password })
            token.value = response.data.token
            email.value = response.data.email
            nickname.value = response.data.nickname

            localStorage.setItem('token', token.value)
            localStorage.setItem('email', email.value)
            localStorage.setItem('nickname', nickname.value)

            isLoggedIn.value = true
        } catch (error) {
            throw new Error('로그인 실패')
        }
    }

    const logout = () => {
        token.value = ''
        isLoggedIn.value = false
        email.value = ''
        nickname.value = ''
        localStorage.clear()
    }

    return {
        token,
        isLoggedIn,
        email,
        nickname,
        login,
        logout,
    }
})
