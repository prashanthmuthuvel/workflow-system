import axios from 'axios';

const SUBJECT_API_BASE_URL = "http://localhost:8080/api/v1/subjects";

class SubjectService {

    getSubjects(){
        return axios.get(SUBJECT_API_BASE_URL);
    }

    getSubjectsToRegister(token){
        return axios.get(SUBJECT_API_BASE_URL + '/' + 'toregister', {
            headers: {
                'Authorization': `Basic ${token}`
            }
        });
    }

    createSubject(subject){
        return axios.post(SUBJECT_API_BASE_URL, subject);
    }

    getSubjectById(subjectId){
        return axios.get(SUBJECT_API_BASE_URL + '/' + subjectId);
    }

    updateSubject(subject, subjectId){
        return axios.put(SUBJECT_API_BASE_URL + '/' + subjectId, subject);
    }

    deleteSubject(subjectId){
        return axios.delete(SUBJECT_API_BASE_URL + '/' + subjectId);
    }

    registerSubject(subjectId, token){
        return axios.get(SUBJECT_API_BASE_URL + '/' + 'register' + '/' + subjectId, {
            headers: {
                'Authorization': `Basic ${token}`
            }
        });
    }

    deregisterSubject(subjectId, token){
        return axios.get(SUBJECT_API_BASE_URL + '/' + 'deregister' + '/' + subjectId, {
            headers: {
                'Authorization': `Basic ${token}`
            }
        });
    }

}

export default new SubjectService()