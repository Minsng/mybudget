<template>
  <div class="d-flex justify-content-center align-items-center vh-100 bg-light">
    <div class="card shadow p-4" style="width: 100%; max-width: 400px;">
      <h3 class="text-center fw-bold mb-4">회원가입</h3>

      <form @submit.prevent="handleSignUp">
        <div class="mb-3">
          <label for="email" class="form-label">이메일</label>
          <input v-model="email" type="email" class="form-control" id="email" required />
        </div>

        <div class="mb-3">
          <label for="nickname" class="form-label">닉네임</label>
          <input v-model="nickname" type="text" class="form-control" id="nickname" required />
        </div>

        <div class="mb-3">
          <label for="password" class="form-label">비밀번호</label>
          <input v-model="password" type="password" class="form-control" id="password" required />
        </div>

        <button type="submit" class="btn btn-success w-100">가입하기</button>
      </form>

      <div class="text-center mt-3">
        <router-link to="/login" class="text-decoration-none">이미 계정이 있으신가요?</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/axios'

const email = ref('')
const nickname = ref('')
const password = ref('')
const router = useRouter()

const handleSignUp = async () => {
  try {
    await api.post('/auth/signup', {
      email: email.value,
      nickname: nickname.value,
      password: password.value
    })
    alert('회원가입 완료! 로그인 페이지로 이동합니다.')
    router.push('/login')
  } catch (error) {
    alert('회원가입 실패: ' + (error.response?.data || error.message))
  }
}
</script>
