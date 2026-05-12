import axios from 'axios';
import { bearerInterceptor, unAuthenticated, authenticated } from './axios-interceptor';

const axiosInstance = axios.create({
  timeout: 60000,
});

axiosInstance.interceptors.request.use(bearerInterceptor);
axiosInstance.interceptors.response.use(authenticated, unAuthenticated);

export default axiosInstance;
export { axiosInstance };
