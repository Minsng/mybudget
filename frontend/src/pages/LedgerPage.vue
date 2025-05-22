<template>
  <div class="container mt-4">
    <h2>{{ nickname }}님의 가계부</h2>

    <!-- ✅ 항목 추가 폼 -->
    <form @submit.prevent="handleAdd" class="border rounded p-3 shadow-sm mb-4 bg-light">
      <div class="row mb-2">
        <div class="col">
          <select v-model="form.type" class="form-select" required>
            <option disabled value="">선택</option>
            <option value="income">수입</option>
            <option value="expense">지출</option>
          </select>
        </div>
        <div class="col">
          <input
              v-model.number="form.amount"
              type="number"
              class="form-control"
              placeholder="금액"
              required
          />
        </div>
        <div class="col">
          <input
              v-model="form.date"
              type="date"
              class="form-control"
              required
          />
        </div>
      </div>
      <div class="mb-2">
        <input
            v-model="form.memo"
            type="text"
            class="form-control"
            placeholder="메모 (예: 점심 식사)"
        />
      </div>
      <button type="submit" class="btn btn-success w-100">항목 추가</button>
    </form>

    <!-- ✅ 항목 리스트 -->
    <ul class="list-group" v-if="entries.length">
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
    <div v-else class="text-muted mt-3">등록된 항목이 없습니다.</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/axios'
import { useAuthStore } from '@/store/authStore'

const authStore = useAuthStore()
const nickname = authStore.nickname

const entries = ref([])

const form = ref({
  type: '',
  amount: null,
  memo: '',
  date: '',
})

// ✅ 항목 불러오기
const fetchEntries = async () => {
  try {
    const res = await api.get('/ledger')
    entries.value = res.data
  } catch (err) {
    alert('불러오기 실패')
  }
}

// ✅ 항목 추가
const handleAdd = async () => {
  try {
    await api.post('/ledger', form.value)
    await fetchEntries()
    // 입력 초기화
    form.value = {
      type: '',
      amount: null,
      memo: '',
      date: '',
    }
  } catch (err) {
    alert('등록 실패')
  }
}

onMounted(fetchEntries)
</script>
