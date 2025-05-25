<template>
  <div class="container py-4">
    <h3 class="fw-bold mb-4">월별 예산 설정</h3>

    <!-- 월 선택 -->
    <div class="row mb-4">
      <div class="col-md-4">
        <label for="month" class="form-label">예산 월</label>
        <input type="month" v-model="selectedMonth" id="month" class="form-control" />
      </div>
    </div>

    <!-- 총합 요약 표시 -->
    <div class="card p-3 mb-4 bg-light">
      <div class="d-flex justify-content-between fw-semibold">
        <div>수입 예산: <span class="text-success">{{ incomeTotal.toLocaleString() }}원</span></div>
        <div>지출 예산: <span class="text-danger">{{ expenseTotal.toLocaleString() }}원</span></div>
        <div>차액: <span>{{ (incomeTotal - expenseTotal).toLocaleString() }}원</span></div>
      </div>
    </div>

    <!-- 카테고리별 입력 -->
    <div class="table-responsive mb-4">
      <table class="table table-bordered align-middle">
        <thead class="table-light">
        <tr>
          <th>카테고리</th>
          <th>유형</th>
          <th>예산 금액 (원)</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in categoryList" :key="item.id">
          <td>{{ item.name }}</td>
          <td>
              <span :class="item.type === 'INCOME' ? 'text-success' : 'text-danger'">
                {{ item.type === 'INCOME' ? '수입' : '지출' }}
              </span>
          </td>
          <td>
            <input
                type="number"
                class="form-control"
                v-model.number="budgetInputs[item.id]"
                placeholder="0"
                min="0"
            />
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- 저장 버튼 -->
    <button class="btn btn-success w-100" @click="saveBudgets">예산 저장</button>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import api from '@/axios'
import { computed } from 'vue'

const selectedMonth = ref(new Date().toISOString().slice(0, 7)) // 'YYYY-MM'
const categoryList = ref([])
const budgetInputs = ref({}) // { categoryId: amount }

// 카테고리 목록 + 예산 초기화
const fetchCategories = async () => {
  try {
    const res = await api.get('/categories')
    categoryList.value = res.data

    // 월별 예산 불러오기
    await fetchBudgets()
  } catch (e) {
    alert('카테고리 불러오기 실패')
  }
}

// 월별 예산 불러오기
const fetchBudgets = async () => {
  try {
    const res = await api.get('/budget', {
      params: { month: selectedMonth.value }
    })

    // 초기화: categoryId 기준으로 amount 설정
    budgetInputs.value = {}
    res.data.forEach(budget => {
      budgetInputs.value[budget.categoryId] = budget.amount
    })
  } catch (e) {
    alert('예산 불러오기 실패')
  }
}

// 예산 저장
const saveBudgets = async () => {
  try {
    const payload = Object.entries(budgetInputs.value)
        .filter(([_, amount]) => amount > 0)
        .map(([categoryId, amount]) => ({
          categoryId: Number(categoryId),
          amount,
          month: selectedMonth.value
        }))

    await api.post('/budget', payload)
    alert('예산이 저장되었습니다.')
    await fetchBudgets() // 최신화
  } catch (e) {
    alert('예산 저장 실패')
  }
}

const incomeTotal = computed(() =>
    categoryList.value.reduce((sum, cat) => {
      if (cat.type === 'INCOME') {
        return sum + (budgetInputs.value[cat.id] || 0)
      }
      return sum
    }, 0)
)

const expenseTotal = computed(() =>
    categoryList.value.reduce((sum, cat) => {
      if (cat.type === 'EXPENSE') {
        return sum + (budgetInputs.value[cat.id] || 0)
      }
      return sum
    }, 0)
)

// 월 변경 시 예산 다시 불러오기
watch(selectedMonth, () => {
  fetchBudgets()
})

onMounted(fetchCategories)
</script>
