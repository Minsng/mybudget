<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-success px-4">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/ledger">MyBudget</router-link>

      <div class="d-flex">
        <router-link class="nav-link text-white me-3" to="/ledger">가계부</router-link>
        <button @click="logout" class="btn btn-outline-light btn-sm">로그아웃</button>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/store/authStore'

const router = useRouter()
const authStore = useAuthStore()

const logout = () => {
  authStore.logout()
  router.push('/login')
}
</script>
