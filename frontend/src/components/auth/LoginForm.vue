<template>
  <div class="d-flex justify-content-center align-items-center min-vh-100 bg-light">
    <div class="card shadow p-4" style="width: 100%; max-width: 400px;">
      <h3 class="text-center text-success fw-bold mb-4">로그인</h3>

      <form @submit.prevent="handleLogin">
        <div class="mb-3">
          <label for="email" class="form-label">이메일</label>
          <input
              type="email"
              id="email"
              v-model="email"
              class="form-control"
              placeholder="example@email.com"
              required
          />
        </div>

        <div class="mb-3">
          <label for="password" class="form-label">비밀번호</label>
          <input
              type="password"
              id="password"
              v-model="password"
              class="form-control"
              placeholder="비밀번호를 입력하세요"
              required
          />
        </div>

        <div v-if="error" class="alert alert-danger py-2 small">{{ error }}</div>

        <button type="submit" class="btn btn-success w-100" :disabled="loading">
          {{ loading ? '로그인 중...' : '로그인' }}
        </button>
      </form>

      <div class="text-center mt-3">
        <small>
          계정이 없으신가요?
          <router-link to="/signup" class="text-decoration-none text-primary">회원가입</router-link>
        </small>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/store/authStore'

const router = useRouter()
const authStore = useAuthStore()

const email = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')

const handleLogin = async () => {
  loading.value = true
  error.value = ''
  try {
    await authStore.login({ email: email.value, password: password.value })
    router.push('/ledger')
  } catch (err) {
    error.value = '이메일 또는 비밀번호가 올바르지 않습니다.'
  } finally {
    loading.value = false
  }
}
</script>
