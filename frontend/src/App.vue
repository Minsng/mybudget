<template>
  <div>
    <GNB v-if="isLoggedIn" />
    <router-view />
  </div>
</template>

<script setup>
import { onMounted, computed } from 'vue'
import GNB from '@/components/layout/GNB.vue'
import { useAuthStore } from '@/store/authStore'

const authStore = useAuthStore()

// ✅ 반응성 보장
const isLoggedIn = computed(() => authStore.isLoggedIn)

// ✅ 앱 로드시 localStorage 복원
onMounted(() => {
  authStore.initFromStorage?.()
})
</script>
