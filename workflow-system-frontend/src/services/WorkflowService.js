import axios from 'axios';

const API_URL = "http://localhost:8080/api/v1";

class WorkflowService {

    createWorkflow(token, workflow) {
        return axios.post(API_URL + "/createWorkflow", workflow, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Basic ${token}`
            }
        })
    }

    getWorkflow(token) {
        return axios.get(API_URL + '/' + 'getWorkflow', {
            headers: {
                'Authorization': `Basic ${token}`
            }
        });
    }

    getWorkflowUsers(token, workflowName) {
        return axios.get(API_URL + "/getWorkflowUsers" + "/" + workflowName,{
            headers: {
                'Authorization': `Basic ${token}`
            }
        });
    }

    getWorkflowFileNames(token, workflowName) {
        
    }

}

export default new WorkflowService()