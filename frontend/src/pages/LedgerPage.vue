<template>
  <div class="container mt-4">
    <h2>가계부 페이지</h2>
    <p class="text-muted">👋 {{ nickname }} 님 환영합니다!</p>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/store/authStore'
const { nickname } = useAuthStore()
</script>
