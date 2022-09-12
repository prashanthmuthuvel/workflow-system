import http from "../http-common";
import axios from 'axios';

const API_URL = "http://localhost:8080/api/v1";

const LOGIN_API_LOGIN_URL = "http://localhost:8080/api/v1";

class UploadFilesService {

  upload(token, ticketId, file, onUploadProgress) {
    let formData = new FormData();

    formData.append("file", file);
    return axios.post(API_URL + "/ticket" + "/" + ticketId + "/file" + "/upload", formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': `Basic ${token}`
        },
        onUploadProgress,
    });
  }

  getFiles(token, ticketId) {
    return axios.get(API_URL + "/ticket" + "/" + ticketId + "/files", {
        headers: {
            'Content-type': 'application/json',
            'Authorization': `Basic ${token}`
        }
    });
  }

  deleteFile(token, fileId) {
    return http.delete(API_URL + "/file/delete"  + '/' + fileId);
  }

  getWorkflowForms(token, ticketId) {
    return axios.get(API_URL + "/ticket" + "/" + ticketId + '/getWorkflowForms', {
      headers: {
          'Authorization': `Basic ${token}`
      }
    });
  }

  linkFileWithForm(token, ticketId, formName, fileId) {
    return axios.post(API_URL + "/ticket" + "/" + ticketId + '/linkFileWithForm', {"formName" : formName, "fileId" : fileId}, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${token}`
        }
    });
  }

  extractData(token, ticketId) {
    return axios.post(API_URL + "/extractData" + "/" + ticketId, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${token}`
        }
    });
  }
}

export default new UploadFilesService()
