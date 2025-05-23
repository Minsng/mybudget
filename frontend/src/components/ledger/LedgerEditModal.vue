<template>
  <div class="modal-backdrop" @click.self="close">
    <div class="modal-content p-4 bg-white rounded shadow">
      <h5 class="mb-3">가계부 항목 수정</h5>
      <form @submit.prevent="handleUpdate">
        <!-- 항목 구분 -->
        <div class="mb-2">
          <select v-model="form.type" class="form-select" required>
            <option disabled value="">항목 구분</option>
            <option value="income">수입</option>
            <option value="expense">지출</option>
          </select>
        </div>

        <!-- 카테고리 선택 -->
        <div class="mb-2">
          <select v-model="form.categoryId" class="form-select" required>
            <option disabled value="">카테고리 선택</option>
            <option v-for="c in categories" :key="c.id" :value="c.id">
              [{{ c.type === 'INCOME' ? '수입' : '지출' }}] {{ c.name }}
            </option>
          </select>
        </div>

        <!-- 금액 / 날짜 / 메모 -->
        <div class="mb-2">
          <input v-model.number="form.amount" type="number" class="form-control" required />
        </div>
        <div class="mb-2">
          <input v-model="form.date" type="date" class="form-control" required />
        </div>
        <div class="mb-3">
          <input v-model="form.memo" type="text" class="form-control" />
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
import { ref, reactive, watch, onMounted } from 'vue'
import api from '@/axios'

const props = defineProps({
  entry: Object
})
const emit = defineEmits(['close', 'updated'])

const form = reactive({
  type: '',
  categoryId: '',
  amount: null,
  memo: '',
  date: ''
})

const categories = ref([])

const loadCategories = async () => {
  const res = await api.get('/categories')
  categories.value = res.data
}

// entry를 받아서 form 초기화
watch(() => props.entry, (newEntry) => {
  if (newEntry) {
    form.type = newEntry.type
    form.categoryId = newEntry.categoryId
    form.amount = newEntry.amount
    form.memo = newEntry.memo
    form.date = newEntry.date
  }
}, { immediate: true })

const handleUpdate = async () => {
  try {
    await api.put(`/ledger/${props.entry.id}`, {
      type: form.type,
      amount: form.amount,
      memo: form.memo,
      date: form.date,
      categoryId: form.categoryId
    })
    emit('updated')
    close()
  } catch (e) {
    alert('수정 실패')
  }
}

const close = () => emit('close')

onMounted(loadCategories)
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
