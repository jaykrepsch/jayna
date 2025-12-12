import axios from 'axios';

const baseApiUrl = '/api/documents';

export default class DocumentService {
  find(id, relations = false) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}?relations=${relations}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  retrieve(page, relations = false) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseApiUrl}?page=${page.page}&size=${page.size}&relations=${relations}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  create(entity) {
    const payload = { ...entity };
    // Entferne Felder, die die Backend-Entity nicht kennt oder zirkulär sind
    delete payload.mainCategory;
    delete payload.grouping;
    delete payload.documentType;
    // Nur groupType-ID senden, wenn vorhanden (ManyToOne)
    if (entity.groupType && (entity.groupType.id || typeof entity.groupType === 'number')) {
      const id = typeof entity.groupType === 'number' ? entity.groupType : entity.groupType.id;
      payload.groupType = { id: Number(id) };
    } else if (entity.documentType && typeof entity.documentType === 'number') {
      // Fallback: documentType-ID als groupType-ID interpretieren, falls so übergeben
      payload.groupType = { id: Number(entity.documentType) };
    } else {
      delete payload.groupType;
    }
    return axios.post(`${baseApiUrl}`, payload, {
      headers: { 'Content-Type': 'application/json' }
    }).then(res => res.data);
  }

  update(entity) {
    const payload = { ...entity };
    // Entferne Felder, die die Backend-Entity nicht kennt oder zirkulär sind
    delete payload.mainCategory;
    delete payload.grouping;
    delete payload.documentType;
    // Nur groupType-ID senden, wenn vorhanden
    if (entity.groupType && (entity.groupType.id || typeof entity.groupType === 'number')) {
      const id = typeof entity.groupType === 'number' ? entity.groupType : entity.groupType.id;
      payload.groupType = { id: Number(id) };
    } else {
      delete payload.groupType;
    }
    return axios
      .put(`${baseApiUrl}/${entity.id}`, payload, { headers: { 'Content-Type': 'application/json' } })
      .then(res => res.data);
  }

  partialUpdate(entity) {
    return new Promise((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  delete(id) {
    return new Promise((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  upload(documentId, file) {
    return new Promise((resolve, reject) => {
      const formData = new FormData();
      formData.append('file', file);

      const url = `${baseApiUrl}/${documentId}/upload`;

      axios
        .post(url, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
          onUploadProgress: (progressEvent) => {
            const percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total);
            // Optional: Emit progress event
            if (this.onUploadProgress) {
              this.onUploadProgress(percentCompleted);
            }
          },
        })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          console.error('Upload failed:', err);
          reject(err);
        });
    });
  }

  download(documentId) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${documentId}/download`, {
          responseType: 'blob',
        })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  listFiles(documentId) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${documentId}/files`)
        .then(res => resolve(res.data))
        .catch(err => reject(err));
    });
  }

  downloadFile(documentId, fileName) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${documentId}/files/${encodeURIComponent(fileName)}`, { responseType: 'blob' })
        .then(res => resolve(res.data))
        .catch(err => reject(err));
    });
  }

  deleteFileByName(documentId, fileName) {
    return new Promise((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${documentId}/files/${encodeURIComponent(fileName)}`)
        .then(res => resolve(res.data))
        .catch(err => reject(err));
    });
  }

  deleteFile(documentId) {
    return new Promise((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${documentId}/file`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  search(query) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${baseApiUrl}`, { params: query })
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
