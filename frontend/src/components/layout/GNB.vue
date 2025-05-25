<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-success px-3">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/ledger">MyBudget</router-link>

      <div class="d-flex align-items-center">
        <router-link
            class="nav-link text-white me-3"
            :class="{ active: isActive('/ledger') }"
            to="/ledger"
        >
          가계부
        </router-link>
        <router-link
            class="nav-link text-white me-3"
            :class="{ active: isActive('/budget') }"
            to="/budget"
        >
          예산 설정
        </router-link>

        <button @click="handleAuthAction" class="btn btn-outline-light btn-sm">
          {{ isLoggedIn ? '로그아웃' : '로그인' }}
        </button>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/store/authStore'
import { computed } from 'vue'

const router = useRouter()
const route = useRoute()
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

// 현재 경로가 일치하면 활성화 표시
const isActive = (path) => route.path === path
</script>
