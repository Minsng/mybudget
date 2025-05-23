<template>
  <div class="modal-backdrop" @click.self="close">
    <div class="modal-content p-4 bg-white rounded shadow">
      <h5 class="mb-4">가계부 항목 수정</h5>

      <form @submit.prevent="handleUpdate">
        <!-- 항목 구분 -->
        <div class="mb-3">
          <label for="type" class="form-label">항목 구분</label>
          <select id="type" v-model="form.type" class="form-select" required>
            <option disabled value="">선택</option>
            <option value="income">수입</option>
            <option value="expense">지출</option>
          </select>
        </div>

        <!-- 카테고리 선택 -->
        <div class="mb-3">
          <label for="category" class="form-label">카테고리</label>
          <select id="category" v-model="form.categoryId" class="form-select" required>
            <option disabled value="">카테고리 선택</option>
            <option v-for="c in categories" :key="c.id" :value="c.id">
              [{{ c.type === 'INCOME' ? '수입' : '지출' }}] {{ c.name }}
            </option>
          </select>
        </div>

        <!-- 금액 -->
        <div class="mb-3">
          <label for="amount" class="form-label">금액</label>
          <input id="amount" v-model.number="form.amount" type="number" class="form-control" required />
        </div>

        <!-- 날짜 -->
        <div class="mb-3">
          <label for="date" class="form-label">날짜</label>
          <input id="date" v-model="form.date" type="date" class="form-control" required />
        </div>

        <!-- 메모 -->
        <div class="mb-4">
          <label for="memo" class="form-label">메모</label>
          <input id="memo" v-model="form.memo" type="text" class="form-control" placeholder="선택 사항" />
        </div>

        <!-- 버튼 -->
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
  try {
    const res = await api.get('/categories')
    categories.value = res.data
  } catch (err) {
    alert('카테고리 불러오기 실패')
  }
}

watch(() => props.entry, (entry) => {
  if (entry) {
    form.type = entry.type || ''
    form.categoryId = entry.categoryId || ''
    form.amount = entry.amount ?? 0
    form.memo = entry.memo ?? ''
    form.date = entry.date || ''
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
