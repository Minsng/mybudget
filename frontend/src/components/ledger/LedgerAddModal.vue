<template>
  <div class="modal-backdrop" @click.self="close">
    <div class="modal-content p-4 bg-white rounded shadow">
      <h5 class="mb-3">가계부 항목 추가</h5>
      <form @submit.prevent="handleAdd">
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
          <button type="submit" class="btn btn-success w-100">등록</button>
          <button type="button" class="btn btn-secondary w-100" @click="close">취소</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '@/axios'

const emit = defineEmits(['close', 'added'])

const form = ref({
  type: '',
  amount: null,
  memo: '',
  date: '',
})

const handleAdd = async () => {
  try {
    await api.post('/ledger', form.value)
    emit('added')
    close()
  } catch (e) {
    alert('등록 실패')
  }
}

const close = () => emit('close')
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: grid;
  place-items: center;
  z-index: 999;
}
.modal-content {
  width: 100%;
  max-width: 400px;
}
</style>
