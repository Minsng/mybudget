<template>
  <div class="container mt-4">
    <h3>📊 수입 / 지출 통계</h3>

    <!-- 월 선택 -->
    <div class="mb-3">
      <label for="monthSelect" class="form-label">월 선택</label>
      <input type="month" id="monthSelect" v-model="selectedMonth" class="form-control" @change="fetchData" />
    </div>

    <!-- 차트 영역 -->
    <div v-if="incomeData.length || expenseData.length" class="row mt-4">
      <div class="col-md-6" v-if="incomeData.length">
        <h5>수입</h5>
        <canvas id="incomeChart"></canvas>
      </div>
      <div class="col-md-6" v-if="expenseData.length">
        <h5>지출</h5>
        <canvas id="expenseChart"></canvas>
      </div>
    </div>

    <div v-else class="text-muted">데이터가 없습니다.</div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import api from '@/axios'
import Chart from 'chart.js/auto'

const selectedMonth = ref(new Date().toISOString().slice(0, 7)) // YYYY-MM
const incomeData = ref([])
const expenseData = ref([])

const incomeChart = ref(null)
const expenseChart = ref(null)

const fetchData = async () => {
  try {
    const response = await api.get(`/ledger/report?month=${selectedMonth.value}`)
    const allData = response.data || []

    incomeData.value = allData.filter(item => item.type === 'income')
    expenseData.value = allData.filter(item => item.type === 'expense')

    renderCharts()
  } catch (error) {
    console.error('❌ Axios 에러:', error)
    incomeData.value = []
    expenseData.value = []
  }
}

const renderCharts = async () => {
  await nextTick()

  // 이전 차트 제거
  if (incomeChart.value) incomeChart.value.destroy()
  if (expenseChart.value) expenseChart.value.destroy()

  const incomeCtx = document.getElementById('incomeChart')
  const expenseCtx = document.getElementById('expenseChart')

  if (incomeCtx && incomeData.value.length) {
    incomeChart.value = new Chart(incomeCtx, {
      type: 'pie',
      data: {
        labels: incomeData.value.map(item => item.category),
        datasets: [{
          label: '수입',
          data: incomeData.value.map(item => item.amount),
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { position: 'bottom' } }
      }
    })
  }

  if (expenseCtx && expenseData.value.length) {
    expenseChart.value = new Chart(expenseCtx, {
      type: 'pie',
      data: {
        labels: expenseData.value.map(item => item.category),
        datasets: [{
          label: '지출',
          data: expenseData.value.map(item => item.amount),
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: { legend: { position: 'bottom' } }
      }
    })
  }
}

onMounted(fetchData)
</script>
