import api from "@/services/api.service";
import { ref } from "vue";

export async function useGet(url, queryParams) {
  const data = ref(null);
  const err = ref(null);
  const load = ref(true);

  const response = await api.get(url);

  data.value = response.data;
  err.value = response.error;
  load.value = false;
  // .then((res) => {
  //   data.value = res.data;
  // })
  // .catch((error) => {
  //   err.value = error;
  // })
  // .finally(() => {
  //   load.value = false;
  // });

  return { data, err, load };
}

// export function useFetch(url) {
//   const loading = ref(false);
//   const data = ref(null);
//   const error = ref(null);

//   fetch(url)
//     .then((res) => {
//       data.value = res.data;
//     })
//     .catch((err) => {
//       error.value = err;
//     })
//     .finally(() => {
//       loading.value = false;
//     });

//   return { loading, data, error };
// }
