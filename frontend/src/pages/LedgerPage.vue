<template>
  <div class="container mt-4">
    <h2>{{ nickname }}님의 가계부</h2>

    <!-- 날짜 필터 -->
    <div class="d-flex gap-2 mb-3">
      <input v-model="filter.startDate" type="date" class="form-control" />
      <input v-model="filter.endDate" type="date" class="form-control" />
      <button @click="fetchEntries" class="btn btn-outline-primary">조회</button>
      <button @click="resetFilter" class="btn btn-outline-secondary">리셋</button>
    </div>

    <!-- 수입/지출 합계 -->
    <div v-if="entries.length" class="mb-3">
      <div class="d-flex justify-content-between">
        <div>수입: <strong class="text-success">{{ incomeTotal.toLocaleString() }}원</strong></div>
        <div>지출: <strong class="text-danger">{{ expenseTotal.toLocaleString() }}원</strong></div>
        <div>총합: <strong>{{ (incomeTotal - expenseTotal).toLocaleString() }}원</strong></div>
      </div>
    </div>

    <!-- 항목 추가 버튼 -->
    <button class="btn btn-primary w-100 my-3" @click="showAddModal = true">항목 추가</button>

    <!-- 항목 리스트 -->
    <ul class="list-group" v-if="entries.length">
      <li v-for="entry in entries" :key="entry.id" class="list-group-item d-flex justify-content-between align-items-center">
        <div>
          <strong>{{ entry.date }}</strong>
          <div>{{ entry.memo }}</div>
        </div>
        <div class="text-end">
          <div :class="entry.type === 'income' ? 'text-success' : 'text-danger'">
            {{ entry.amount.toLocaleString() }}원
          </div>
          <div class="btn-group mt-1">
            <button class="btn btn-sm btn-outline-secondary" @click="editEntry(entry)">수정</button>
            <button class="btn btn-sm btn-outline-danger" @click="deleteEntry(entry.id)">삭제</button>
          </div>
        </div>
      </li>
    </ul>
    <div v-else class="text-muted mt-3">등록된 항목이 없습니다.</div>

    <!-- 모달 -->
    <LedgerAddModal v-if="showAddModal" @close="showAddModal = false" @added="fetchEntries" />
    <LedgerEditModal v-if="showEditModal" :entry="editingEntry" @close="showEditModal = false" @updated="fetchEntries" />
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import api from '@/axios'
import { useAuthStore } from '@/store/authStore'
import LedgerAddModal from '@/components/ledger/LedgerAddModal.vue'
import LedgerEditModal from '@/components/ledger/LedgerEditModal.vue'

const authStore = useAuthStore()
const nickname = authStore.nickname

const entries = ref([])

const showAddModal = ref(false)
const showEditModal = ref(false)
const editingEntry = ref(null)

const filter = reactive({
  startDate: '',
  endDate: ''
})

const resetFilter = () => {
  filter.startDate = ''
  filter.endDate = ''
  fetchEntries()
}

const incomeTotal = computed(() =>
    entries.value.filter(e => e.type === 'income').reduce((sum, e) => sum + e.amount, 0)
)
const expenseTotal = computed(() =>
    entries.value.filter(e => e.type === 'expense').reduce((sum, e) => sum + e.amount, 0)
)

const fetchEntries = async () => {
  try {
    const params = {}
    if (filter.startDate) params.start = filter.startDate
    if (filter.endDate) params.end = filter.endDate

    const res = await api.get('/ledger', { params })
    entries.value = res.data
  } catch (err) {
    alert('불러오기 실패')
  }
}

const deleteEntry = async (id) => {
  if (!confirm('정말 삭제하시겠습니까?')) return
  try {
    await api.delete(`/ledger/${id}`)
    await fetchEntries()
  } catch (err) {
    alert('삭제 실패')
  }
}

const editEntry = (entry) => {
  editingEntry.value = { ...entry }
  showEditModal.value = true
}

onMounted(fetchEntries)
</script>
