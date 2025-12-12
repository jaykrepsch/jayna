import axios from 'axios';

export default {
  async getKpis() {
    const { data } = await axios.get('/api/overview/kpis');
    return data;
  },
  async getCashflowTrend() {
    const { data } = await axios.get('/api/overview/cashflow-trend');
    return data;
  }
};


