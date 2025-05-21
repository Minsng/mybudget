<!-- frontend/src/components/layout/GNB.vue -->
<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-success px-4">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/ledger">MyBudget</router-link>

      <div class="d-flex">
        <router-link class="nav-link text-white me-3" to="/ledger">가계부</router-link>
        <button @click="handleAuthAction" class="btn btn-outline-light btn-sm">
          {{ isLoggedIn ? '로그아웃' : '로그인' }}
        </button>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/store/authStore'
import { computed } from 'vue'

const router = useRouter()
const authStore = useAuthStore()

const isLoggedIn = computed(() => authStore.isLoggedIn)

const handleAuthAction = () => {
  if (isLoggedIn.value) {
    authStore.logout()
    location.reload()
  } else {
    router.push('/login')
  }
}
</script>

