<!-- frontend/src/App.vue -->
<template>
  <div>
    <GNB v-if="isLoggedIn" />
    <router-view />
  </div>
</template>

<script setup>
import GNB from '@/components/layout/GNB.vue'
import { useAuthStore } from '@/store/authStore'

const { isLoggedIn } = useAuthStore()
</script>
