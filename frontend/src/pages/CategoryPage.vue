<template>
  <div class="container py-4">
    <h3 class="fw-bold mb-4">카테고리 관리</h3>

    <!-- 입력 폼 -->
    <form @submit.prevent="saveCategory" class="row g-2 mb-4">
      <div class="col-md-5">
        <input v-model="form.name" class="form-control" placeholder="카테고리 이름" required />
      </div>
      <div class="col-md-3">
        <select v-model="form.type" class="form-select" required>
          <option value="INCOME">수입</option>
          <option value="EXPENSE">지출</option>
        </select>
      </div>
      <div class="col-md-2">
        <button type="submit" class="btn btn-success w-100">{{ form.id ? '수정' : '추가' }}</button>
      </div>
      <div class="col-md-2" v-if="form.id">
        <button @click="resetForm" type="button" class="btn btn-secondary w-100">취소</button>
      </div>
    </form>

    <!-- 목록 테이블 -->
    <div class="table-responsive">
      <table class="table table-bordered align-middle">
        <thead class="table-light">
        <tr>
          <th>ID</th>
          <th>이름</th>
          <th>유형</th>
          <th>작업</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="cat in categories" :key="cat.id">
          <td>{{ cat.id }}</td>
          <td>{{ cat.name }}</td>
          <td>
              <span :class="cat.type === 'INCOME' ? 'text-success' : 'text-danger'">
                {{ cat.type === 'INCOME' ? '수입' : '지출' }}
              </span>
          </td>
          <td>
            <button @click="editCategory(cat)" class="btn btn-sm btn-outline-primary me-1">수정</button>
            <button @click="deleteCategory(cat.id)" class="btn btn-sm btn-outline-danger">삭제</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/axios'

const categories = ref([])
const form = ref({ id: null, name: '', type: 'EXPENSE' })

const fetchCategories = async () => {
  const res = await api.get('/categories')
  categories.value = res.data
}

const saveCategory = async () => {
  if (form.value.id) {
    await api.put('/categories', form.value)
  } else {
    await api.post('/categories', form.value)
  }
  await fetchCategories()
  resetForm()
}

const editCategory = (cat) => {
  form.value = { ...cat }
}

const deleteCategory = async (id) => {
  if (confirm('삭제하시겠습니까?')) {
    await api.delete(`/categories/${id}`)
    await fetchCategories()
  }
}

const resetForm = () => {
  form.value = { id: null, name: '', type: 'EXPENSE' }
}

onMounted(fetchCategories)
</script>