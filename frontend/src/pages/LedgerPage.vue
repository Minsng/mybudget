<template>
  <div class="container mt-4">
    <h2>{{ nickname }}님의 가계부</h2>

    <!-- ✅ 항목 추가 버튼 -->
    <button class="btn btn-primary w-100 my-3" @click="showModal = true">항목 추가</button>

    <!-- ✅ 항목 리스트 -->
    <ul class="list-group" v-if="entries.length">
      <li
          v-for="entry in entries"
          :key="entry.id"
          class="list-group-item d-flex justify-content-between align-items-center"
      >
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

    <!-- ✅ 추가 모달 -->
    <LedgerAddModal v-if="showModal" @close="showModal = false" @added="handleAdded" />
    <LedgerEditModal
        v-if="showEditModal"
        :entry="editingEntry"
        @close="showEditModal = false"
        @updated="handleUpdated"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/axios'
import { useAuthStore } from '@/store/authStore'
import LedgerAddModal from '@/components/ledger/LedgerAddModal.vue'
import LedgerEditModal from '@/components/ledger/LedgerEditModal.vue'

const authStore = useAuthStore()
const nickname = authStore.nickname

const entries = ref([])
const showModal = ref(false)

const fetchEntries = async () => {
  try {
    const res = await api.get('/ledger')
    entries.value = res.data
  } catch (err) {
    alert('불러오기 실패')
  }
}

const handleAdded = async () => {
  await fetchEntries()
  showModal.value = false
}

const deleteEntry = async (id) => {
  if (!confirm('정말 삭제하시겠습니까?')) return;
  try {
    await api.delete(`/ledger/${id}`)
    await fetchEntries()
  } catch (e) {
    alert('삭제 실패')
  }
}

const showEditModal = ref(false)
const editingEntry = ref(null)

const editEntry = (entry) => {
  editingEntry.value = { ...entry }
  showEditModal.value = true
}

const handleUpdated = async () => {
  await fetchEntries()
  showEditModal.value = false
}

onMounted(fetchEntries)
</script>
