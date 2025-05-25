<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-success px-3">
    <div class="container-fluid d-flex justify-content-between align-items-center">

      <!-- 좌측: 로고 -->
      <router-link class="navbar-brand" to="/ledger">MyBudget</router-link>

      <!-- 가운데: 메뉴 -->
      <div class="d-flex gap-3 mx-auto">
        <router-link
            class="nav-link text-white"
            :class="{ active: isActive('/ledger') }"
            to="/ledger"
        >
          가계부
        </router-link>
        <router-link
            class="nav-link text-white"
            :class="{ active: isActive('/budget') }"
            to="/budget"
        >
          예산 설정
        </router-link>
        <router-link
            class="nav-link text-white"
            :class="{ active: isActive('/report') }"
            to="/report"
        >
          통계
        </router-link>
      </div>

      <!-- 우측: 로그인 정보 + 버튼 -->
      <div class="d-flex align-items-center">
        <div v-if="isLoggedIn" class="text-white me-3 text-end small">
          <div>{{ authStore.nickname }} 님</div>
          <div>{{ remainingTime }}</div>
        </div>
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
import { computed, ref, onMounted, onUnmounted } from 'vue'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const isLoggedIn = computed(() => authStore.isLoggedIn)

// 현재 경로가 일치하면 활성화 표시
const isActive = (path) => route.path === path

const handleAuthAction = () => {
  if (isLoggedIn.value) {
    authStore.logout()
    location.reload()
  } else {
    router.push('/login')
  }
}

// 세션 만료 시간 표시용 계산
const remainingTime = ref('')
let intervalId = null

const updateRemainingTime = () => {
  if (!authStore.expiresAt) {
    remainingTime.value = ''
    return
  }
  const diff = authStore.expiresAt - Date.now()
  if (diff <= 0) {
    remainingTime.value = '세션 만료됨'
    authStore.logout()
    location.reload()
  } else {
    const min = Math.floor(diff / 1000 / 60)
    const sec = Math.floor((diff / 1000) % 60)
    remainingTime.value = `${min}분 ${sec}초 남음`
  }
}

onMounted(() => {
  updateRemainingTime()
  intervalId = setInterval(updateRemainingTime, 1000)
})

onUnmounted(() => {
  clearInterval(intervalId)
})
</script>
