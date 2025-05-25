<template>
  <div class="container mt-4">
    <h3>📊 지출/수입 통계</h3>

    <!-- 월 선택 -->
    <div class="mb-3">
      <label for="monthSelect" class="form-label">월 선택</label>
      <input type="month" id="monthSelect" v-model="selectedMonth" class="form-control" @change="fetchData" />
    </div>

    <!-- 차트 영역 -->
    <div v-if="chartData.length > 0" class="mt-4">
      <h5>{{ selectedMonth }} 기준 통계</h5>
      <canvas id="ledgerChart"></canvas>
    </div>

    <div v-else class="text-muted">데이터가 없습니다.</div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue'
import api from '@/axios'
import Chart from 'chart.js/auto'

const selectedMonth = ref(new Date().toISOString().slice(0, 7)) // 현재 연월 (YYYY-MM)
const chart = ref(null)
const chartData = ref([])

const fetchData = async () => {
  try {
    const response = await api.get(`/ledger/report?month=${selectedMonth.value}`)
    chartData.value = response.data
    renderChart()
  } catch (error) {
    console.error('❌ Axios 에러:', error)
    chartData.value = []
  }
}

const renderChart = async () => {
  if (chart.value) {
    chart.value.destroy()
  }

  await nextTick() // DOM 렌더링 보장

  const ctx = document.getElementById('ledgerChart')
  if (!ctx) {
    console.error('❌ ledgerChart 캔버스 없음')
    return
  }

  chart.value = new Chart(ctx, {
    type: 'pie',
    data: {
      labels: chartData.value.map(item => item.category),
      datasets: [{
        label: '금액',
        data: chartData.value.map(item => item.amount),
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'bottom'
        }
      }
    }
  })
}

onMounted(fetchData)
</script>
