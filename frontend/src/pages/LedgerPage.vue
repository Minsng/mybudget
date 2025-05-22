<!-- frontend/src/pages/LedgerPage.vue -->
<template>
  <div class="container mt-4">
    <h2>{{ nickname }}님의 가계부</h2>

    <ul class="list-group mt-3" v-if="entries.length">
      <li
          v-for="entry in entries"
          :key="entry.id"
          class="list-group-item d-flex justify-content-between"
      >
        <div>
          <strong>{{ entry.date }}</strong>
          <div>{{ entry.memo }}</div>
        </div>
        <span :class="entry.type === 'income' ? 'text-success' : 'text-danger'">
          {{ entry.amount.toLocaleString() }}원
        </span>
      </li>
    </ul>

    <div v-else class="text-muted mt-4">등록된 항목이 없습니다.</div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '@/axios'
import { useAuthStore } from '@/store/authStore'

const authStore = useAuthStore()
const nickname = authStore.nickname

const entries = ref([])

onMounted(async () => {
  try {
    const res = await api.get('/ledger') // ✅ JWT 토큰 자동 첨부
    entries.value = res.data
  } catch (err) {
    alert('가계부 데이터를 불러올 수 없습니다.')
    console.error(err)
  }
})
</script>
