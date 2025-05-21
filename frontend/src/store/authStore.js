// frontend/src/store/authStore.js
import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/axios'

export const useAuthStore = defineStore('auth', () => {
    const token = ref(localStorage.getItem('token') || '')
    const isLoggedIn = ref(!!token.value)
    const email = ref(localStorage.getItem('email') || '')
    const nickname = ref(localStorage.getItem('nickname') || '')

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
