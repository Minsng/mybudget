<template>
  <div class="container mt-4">
    <h3 class="fw-bold mb-4">{{ nickname }}님의 가계부</h3>

    <!-- 날짜 필터 -->
    <div class="row g-2 align-items-end mb-3">
      <div class="col-md-3">
        <label class="form-label">시작일</label>
        <input v-model="filter.startDate" type="date" class="form-control" />
      </div>
      <div class="col-md-3">
        <label class="form-label">종료일</label>
        <input v-model="filter.endDate" type="date" class="form-control" />
      </div>
      <div class="col-md-3 d-flex gap-2">
        <button @click="fetchEntries" class="btn btn-primary w-100">조회</button>
        <button @click="resetFilter" class="btn btn-secondary w-100">리셋</button>
      </div>
    </div>

    <!-- 수입/지출 합계 -->
    <div v-if="entries.length" class="card p-3 mb-3 bg-light">
      <div class="d-flex justify-content-between fw-semibold">
        <div>수입: <span class="text-success">{{ incomeTotal.toLocaleString() }}원</span></div>
        <div>지출: <span class="text-danger">{{ expenseTotal.toLocaleString() }}원</span></div>
        <div>총합: <span>{{ (incomeTotal - expenseTotal).toLocaleString() }}원</span></div>
      </div>
    </div>

    <!-- 항목 추가 버튼 -->
    <button class="btn btn-success w-100 mb-3" @click="showAddModal = true">
      + 항목 추가
    </button>

    <!-- 항목 리스트 테이블 -->
    <div v-if="entries.length" class="table-responsive mb-5">
      <table class="table table-hover align-middle">
        <thead class="table-light">
        <tr>
          <th>날짜</th>
          <th>카테고리</th>
          <th>메모</th>
          <th class="text-end">금액</th>
          <th>타입</th>
          <th class="text-center"></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="entry in entries" :key="entry.id">
          <td>{{ entry.date }}</td>
          <td>{{ entry.categoryName }}</td>
          <td>{{ entry.memo || '(메모 없음)' }}</td>
          <td class="text-end fw-semibold" :class="entry.type === 'income' ? 'text-success' : 'text-danger'">
            {{ entry.amount.toLocaleString() }}원
          </td>
          <td>
              <span class="badge" :class="entry.type === 'income' ? 'bg-success' : 'bg-danger'">
                {{ entry.type === 'income' ? '수입' : '지출' }}
              </span>
          </td>
          <td class="text-center">
            <button class="btn btn-sm btn-outline-primary me-1" @click="editEntry(entry)">수정</button>
            <button class="btn btn-sm btn-outline-danger" @click="deleteEntry(entry.id)">삭제</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- 항목이 없을 때 -->
    <div v-else class="text-center text-muted py-5">등록된 항목이 없습니다.</div>

    <!-- 모달 컴포넌트 -->
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
