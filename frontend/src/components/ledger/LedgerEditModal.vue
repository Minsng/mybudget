<template>
  <div class="modal-backdrop" @click.self="close">
    <div class="modal-content p-4 bg-white rounded shadow">
      <h5 class="mb-3">가계부 항목 수정</h5>
      <form @submit.prevent="handleUpdate">
        <div class="mb-2">
          <select v-model="form.type" class="form-select" required>
            <option disabled value="">선택</option>
            <option value="income">수입</option>
            <option value="expense">지출</option>
          </select>
        </div>
        <div class="mb-2">
          <input v-model.number="form.amount" type="number" class="form-control" placeholder="금액" required />
        </div>
        <div class="mb-2">
          <input v-model="form.date" type="date" class="form-control" required />
        </div>
        <div class="mb-3">
          <input v-model="form.memo" type="text" class="form-control" placeholder="메모" />
        </div>
        <div class="d-flex gap-2">
          <button type="submit" class="btn btn-primary w-100">수정</button>
          <button type="button" class="btn btn-secondary w-100" @click="close">취소</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, watch } from 'vue'
import api from '@/axios'

const props = defineProps({
  entry: Object, // LedgerPage로부터 전달된 수정할 항목
})

const emit = defineEmits(['close', 'updated'])

const form = reactive({
  id: null,
  type: '',
  amount: null,
  memo: '',
  date: ''
})

// 기존 entry 값으로 form 초기화
watch(() => props.entry, (newEntry) => {
  if (newEntry) {
    form.id = newEntry.id
    form.type = newEntry.type
    form.amount = newEntry.amount
    form.memo = newEntry.memo
    form.date = newEntry.date
  }
}, { immediate: true })

const handleUpdate = async () => {
  try {
    await api.put(`/ledger/${form.id}`, form)
    emit('updated')
    close()
  } catch (e) {
    alert('수정 실패')
  }
}

const close = () => emit('close')
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  display: grid;
  place-items: center;
  z-index: 1000;
}
.modal-content {
  width: 100%;
  max-width: 400px;
}
</style>
