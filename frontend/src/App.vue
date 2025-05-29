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

const isLoggedIn = computed(() => authStore.isLoggedIn)

onMounted(() => {
  authStore.initFromStorage?.()
})
</script>
